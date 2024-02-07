package cs545.property.services;

import cs545.property.constant.OfferStatus;
import cs545.property.constant.PropertyStatus;
import cs545.property.constant.PropertyTransactionStatus;
import cs545.property.domain.Customer;
import cs545.property.domain.Offer;
import cs545.property.domain.Property;
import cs545.property.domain.Users;
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
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OfferService {
    private final PropertyRepo propertyRepository;
    private final CustomerRepo customerRepository;
    private final OfferRepo offerRepository;
    private final ListMapper listMapper;
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


    public GenericActivityResponse save(CreateOfferRequest offerRequest, long propertyId) {
        Offer offer = new Offer();
        offer.setDate(new Date());
        offer.setAmount(offerRequest.getAmount());
        offer.setMessage(offerRequest.getMessage());

        Property property = propertyRepository.findById(offerRequest.getPropertyId()).orElse(null);
        Users user = userRepository.findById(offerRequest.getCustomerId()).orElse(null);

        try {
            validateOfferCreate(property);
        } catch (ErrorException e) {
            return new GenericActivityResponse(false, e.getMessage());
        }

        offer.setStatus(OfferStatus.created);

        offer.setProperty(property);
        offer.setCustomer(user);

        property.setStatus(PropertyStatus.Pending);

        offerRepository.save(offer);
        return new GenericActivityResponse(true, "Offer created.");
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

        return new GenericActivityResponse(true, "Status updated");
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

        List<OfferStatus> allowedForOwner =Arrays.asList(OfferStatus.contingent, OfferStatus.rejected);
        List<OfferStatus> allowedForCustomer =Arrays.asList(OfferStatus.cancelled);

        boolean isCurrentUsersProperty = offer.getProperty().getOwner().getId() == user.getId();
        boolean isCurrentUsersOffer = offer.getProperty().getOwner().getId() != user.getId();

        boolean isAllowed = (user.getRoles().contains("Owner") && isCurrentUsersProperty && allowedForOwner.contains(status) && offer.getProperty().getStatus() == PropertyStatus.Pending)
                ||
                (user.getRoles().contains("Customer") && isCurrentUsersOffer && allowedForCustomer.contains(status) && offer.getStatus() != OfferStatus.contingent && offer.getProperty().getStatus() != PropertyStatus.Pending);

        if (!isAllowed)
            throw new ErrorException("Cannot perform given status change");
    }
}
