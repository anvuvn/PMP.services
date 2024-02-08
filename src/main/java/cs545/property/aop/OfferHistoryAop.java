package cs545.property.aop;

import cs545.property.config.UserDetailDto;
import cs545.property.constant.OfferStatus;
import cs545.property.domain.Offer;
import cs545.property.dto.OfferHistoryCreateRequest;
import cs545.property.dto.request.CreateOfferRequest;
import cs545.property.dto.response.GenericActivityResponse;
import cs545.property.services.OfferHistoryService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OfferHistoryAop {

    @Autowired
    OfferHistoryService service;

    public void createNewRecord(GenericActivityResponse returnValue){
        var offer = (Offer) returnValue.getData();
        if(offer == null)
            return ;
//        var user = (UserDetailDto) SecurityContextHolder.getContext().getAuthentication().getDetails();
//        var userId = user.getUserId();
        var request = new OfferHistoryCreateRequest();
        request.setStatus(offer.getStatus());
        request.setCreatedUserId(offer.getCustomer().getId());
        request.setOfferId(offer.getId());
        service.create(request);
    }
    //when offer change, keep record to offer history - create/changeStatus
    @AfterReturning(pointcut = "execution(* cs545.property.services.OfferService.create(..))", returning = "returnValue")
    public void onOfferCreated(JoinPoint point, GenericActivityResponse returnValue) {
        createNewRecord(returnValue);
    }
    @AfterReturning(pointcut = "execution(* cs545.property.services.OfferService.changeStatus(..))", returning = "returnValue")
    public void onOfferChangeStatus(JoinPoint point, GenericActivityResponse returnValue) {
        createNewRecord(returnValue);
    }

}
