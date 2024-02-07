package cs545.property.services;

import cs545.property.domain.Offer;
import cs545.property.domain.OfferHistory;
import cs545.property.domain.Users;
import cs545.property.dto.OfferHistoryCreateRequest;
import cs545.property.dto.OfferHistoryResponse;
import cs545.property.repository.OfferHistoryRepo;
import cs545.property.repository.OfferRepo;
import cs545.property.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferHistoryService {

    @Autowired
    OfferHistoryRepo offerHistoryRepo;
    @Autowired
    OfferRepo offerRepo;

    @Autowired
    UserRepository userRepository;

    public OfferHistoryResponse create(OfferHistoryCreateRequest request) {
        var domain = new OfferHistory();
        var offer = offerRepo.findById(request.getOfferId());

        Users createUser =null;
        if(request.getCreatedUserId()>0)
         createUser = userRepository.getReferenceById(request.getCreatedUserId());
        Users modifyUser =null;
        if(request.getCreatedUserId()>0)
         modifyUser = userRepository.getReferenceById(request.getCreatedUserId());

        domain.setStatus(request.getStatus());
        domain.setCreatedDate(LocalDateTime.now());
//        domain.setUpdatedDate(request.getUpdatedDate());
//        domain.setModifyUser(modifyUser);
        domain.setCreatedUser(createUser);
        domain.setOffer(offer.get());
        offerHistoryRepo.save(domain);
        return new OfferHistoryResponse(domain);
    }

    public List<OfferHistoryResponse> getHistoryByOffer(Long offerId){
        return offerHistoryRepo.findByOfferId(offerId).stream().map(o->new OfferHistoryResponse(o)).toList();
    }





}
