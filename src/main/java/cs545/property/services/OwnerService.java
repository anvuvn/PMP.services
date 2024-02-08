package cs545.property.services;

import cs545.property.domain.Offer;
import cs545.property.dto.CustomerOfferListDto;

import cs545.property.repository.OwnerRepo;
import cs545.property.util.ListMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerService {

    @Autowired
    OwnerRepo ownerRepo;


    @Autowired
    ListMapper listMapper;

//    public List<CustomerOfferListDto> getCustomerOffersByProperty(long id){
//        return  listMapper.map(ownerRepo.getCustomerOffersByProperty(id),CustomerOfferListDto.class);
//
//    }

    //offer accept

//    @Transactional
//    public List<CustomerOfferListDto> getAllCustomerOffers(long id) {
//        //return userRepo.findAll().stream().map(u -> new UserDto(u)).toList();
//        System.out.println("inside service");
//        List<Offer> offers=new ArrayList<Offer>();
//
//       // offers=ownerRepo.getAllCustomerOffers(id);
//        //return listMapper.map(ownerRepo.getAllCustomerOffers(id), CustomerOfferListDto.class);
//    return null;
//
//    }



}
