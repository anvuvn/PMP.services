package cs545.property.services;

import cs545.property.config.UserDetailDto;
import cs545.property.constant.OfferStatus;
import cs545.property.constant.PropertyStatus;
import cs545.property.constant.PropertyTransactionStatus;
import cs545.property.domain.Offer;
import cs545.property.domain.Owner;
import cs545.property.domain.Property;
import cs545.property.domain.Users;
import cs545.property.dto.AcceptOfferRequest;
import cs545.property.dto.CustomerOfferListDto;
import cs545.property.dto.OfferDto;
import cs545.property.dto.request.ChangeOfferStatusRequest;
import cs545.property.dto.request.CreateOfferRequest;
import cs545.property.dto.response.GenericActivityResponse;
import cs545.property.exceptions.ErrorException;
import cs545.property.repository.CustomerRepo;
import cs545.property.repository.OfferRepo;
import cs545.property.repository.PropertyRepo;
import cs545.property.repository.UserRepository;
import cs545.property.util.ListMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class OfferService {
    private final PropertyRepo propertyRepository;
    private final CustomerRepo customerRepository;
    private final OfferRepo offerRepository;
    private final ListMapper listMapper;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    public List<OfferDto> findByCustomerId(long id) {
        return listMapper.map(offerRepository.findByCustomerId(id), OfferDto.class);
    }

    public List<OfferDto> findAll() {
        return listMapper.map(offerRepository.findAll(), OfferDto.class);
    }


    public List<Offer> findByPropertyId(long propertyId) {
        Property property = propertyRepository.findById(propertyId).get();
        return listMapper.map(property.getOffers(), Offer.class);
    }

    public List<OfferDto> findCustomerOffersByPropertyId(Users user, long propertyId) {
        return listMapper.map(offerRepository.findByCustomerIdAndPropertyId(user.getId(), propertyId), OfferDto.class);
    }


    public Offer getCompletedOfferIfExists(long propertyId) {
        return offerRepository.getCompletedOfferIfExists(propertyId);
    }


    public GenericActivityResponse create(CreateOfferRequest offerRequest, long propertyId) {

        var existingOffers = offerRepository.findByCustomerIdAndPropertyId(offerRequest.getUserId(), propertyId);
        if(existingOffers!= null && existingOffers.size()>0){
            return new GenericActivityResponse(false, "Offer is existing");
        }
        Offer offer = new Offer();
        offer.setAmount(offerRequest.getAmount());
        offer.setMessage(offerRequest.getMessage());
        offer.setDate(new Date());

        var customer = userRepository.getReferenceById(offerRequest.getUserId());

        var property = propertyRepository.findById(propertyId).get();

        try {
            validateOfferCreate(property);
        } catch (ErrorException e) {
            return new GenericActivityResponse(false, e.getMessage());
        }

        offer.setStatus(OfferStatus.created);
        offer.setProperty(property);
        offer.setCustomer(customer);

        property.setStatus(PropertyStatus.Pending);

        offerRepository.save(offer);
        return new GenericActivityResponse(true, "Offer created.", offer);
    }

    private void syncPropertyStatusOnCreate(Property property) {
//        property.setStatus(PropertyStatus.Pending);
//
//        propertyRepository.save(property);
    }

    private void validateOfferCreate(Property property) {
        List<PropertyTransactionStatus> allowedStatus = Arrays.asList(PropertyTransactionStatus.Available, PropertyTransactionStatus.Pending);

        if (property == null || !allowedStatus.contains(property.getStatus()))
            return;
        //throw new ErrorException("Not Allowed");
    }


    public GenericActivityResponse changeStatus(Users user, long id, ChangeOfferStatusRequest request) {
        Offer offer = offerRepository.findById(id).get();

        if (offer == null) return new GenericActivityResponse(false, "No offer found.");

        try {
            validateStatusChange(user, offer, request.getStatus());
        } catch (ErrorException e) {
            return new GenericActivityResponse(false, e.getMessage());
        }
        offer.setStatus(request.getStatus());
        syncPropertyStatusOnEdit(offer);
        offerRepository.save(offer);

        return new GenericActivityResponse(true, "Status updated", offer);
    }

    private void syncPropertyStatusOnEdit(Offer offer) {
        Property property = offer.getProperty();
        List<Offer> allPropertyOffers = property.getOffers();

        if (offer.getStatus() == OfferStatus.cancelled || offer.getStatus() == OfferStatus.rejected) {
            boolean hasOtherActiveOffers = allPropertyOffers
                    .stream()
                    .anyMatch(o -> o.getStatus() == OfferStatus.created);

            property.setStatus(hasOtherActiveOffers ? PropertyStatus.Pending : PropertyStatus.Available);
        } else if (offer.getStatus() == OfferStatus.contingent) {
            property.setStatus(PropertyStatus.Contingent);
        }

        propertyRepository.save(property);
    }

    private void validateStatusChange(Users user, Offer offer, OfferStatus status) {

        List<OfferStatus> allowedForOwner = Arrays.asList(OfferStatus.contingent, OfferStatus.rejected);
        List<OfferStatus> allowedForCustomer = Arrays.asList(OfferStatus.cancelled);

        boolean isCurrentUsersProperty = offer.getProperty().getOwner().getId() == user.getId();
        boolean isCurrentUsersOffer = offer.getProperty().getOwner().getId() != user.getId();

        boolean isAllowed = (user.getRoles().contains("Owner") && isCurrentUsersProperty && allowedForOwner.contains(status) && offer.getProperty().getStatus() == PropertyStatus.Pending)
                ||
                (user.getRoles().contains("Customer") && isCurrentUsersOffer && allowedForCustomer.contains(status) && offer.getStatus() != OfferStatus.contingent && offer.getProperty().getStatus() != PropertyStatus.Pending);

        if (!isAllowed)
            throw new ErrorException("Cannot perform given status change");
    }

    private Offer changeOfferStatus(Long offerId, OfferStatus status) {
        var user = (UserDetailDto) SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (!user.isOwner()) {
            throw new RuntimeException("Permission dennied! - Only Owner can accept Offer");
        }

        var offer = (offerRepository.findById(offerId)).get();
        offer.setStatus(status);
        offerRepository.save(offer);
        return offer;
    }

    public GenericActivityResponse ownerAcceptOffer(AcceptOfferRequest model) {
        var offer = changeOfferStatus(model.getOfferId(), OfferStatus.OwnerAccepted);
        var property = offer.getProperty();
        property.setStatus(PropertyStatus.Pending);
        propertyRepository.save(property);
        return new GenericActivityResponse(true, "Offer Accepted", offer);
    }

    public GenericActivityResponse ownerAcceptOfferAfterCustomer(AcceptOfferRequest model) {
        var offer = changeOfferStatus(model.getOfferId(), OfferStatus.completed);
        var property = offer.getProperty();
        property.setStatus(PropertyStatus.Sold);
        propertyRepository.save(property);
        return new GenericActivityResponse(true, "Offer Completed", offer);
    }

    public GenericActivityResponse customerAcceptOffer(AcceptOfferRequest model) {
        var offer = (offerRepository.findById(model.getOfferId())).get();
        var user = (UserDetailDto) SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(!offer.getCustomer().getId().equals(user.getUserId())){//make sure customer can accept his offer only
            throw new RuntimeException("Customer can only accept his offer only");
        }
        if(!offer.getStatus().equals(OfferStatus.OwnerAccepted)){
            throw new RuntimeException("Customer can only accept once owner is accepted");
        }
        offer.setStatus(OfferStatus.CustomerAccepted);
        var property = offer.getProperty();
        property.setStatus(PropertyStatus.Contingent);
        propertyRepository.save(property);
        offerRepository.save(offer);
        return new GenericActivityResponse(true, "Offer Accepted", offer);
    }
    public GenericActivityResponse ownerCancelOffer(AcceptOfferRequest model) {
        var offer = changeOfferStatus(model.getOfferId(), OfferStatus.cancelled);
        return new GenericActivityResponse(true, "Offer Cancelled", offer);
    }


    public OfferDto acceptOffer(Long id) {
        var offer = offerRepository.getReferenceById(id);
        offer.setStatus(OfferStatus.contingent);
        offerRepository.save(offer);
        return new OfferDto(offer);
    }

    public OfferDto cancelOffer(Long id) {
        var offer = offerRepository.getReferenceById(id);
        offer.setStatus(OfferStatus.cancelled);
        offerRepository.save(offer);
        return new OfferDto(offer);
    }

    public List<CustomerOfferListDto> ownerGetPropertyOffers(Long propertyId){
        return offerRepository.findByPropertyId(propertyId).stream().map(o->new CustomerOfferListDto(o)).toList();
    }

    public List<CustomerOfferListDto> ownerGetMyOfferByStatus(OfferStatus status){
        var user = (UserDetailDto)SecurityContextHolder.getContext().getAuthentication().getDetails();
        var userId = user.getUserId();
        return offerRepository.findByPropertyOwnerIdAndStatus(userId, status).stream().map(o->new CustomerOfferListDto(o)).toList();
    }
}
