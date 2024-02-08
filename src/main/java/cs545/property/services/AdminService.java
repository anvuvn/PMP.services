package cs545.property.services;


import cs545.property.dto.*;
import cs545.property.repository.CustomerRepo;
import cs545.property.repository.OfferHistoryRepo;
import cs545.property.repository.PropertyTransactionRepo;
import cs545.property.repository.UserRepository;
import cs545.property.util.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminService {
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PropertyTransactionRepo transactionRepo;

    @Autowired
    OfferHistoryRepo offerHistoryRepo;
    @Autowired
    ListMapper listMapper;

    public AdminDashboard getAdminDashBoard(){
        var dashboard = new AdminDashboard();
        dashboard.setRecentCustomers( customerRepo.findTop10ByOrderByCreatedDateDesc());
        dashboard.setRecentRented(transactionRepo.findTop10ByOrderByTransactionDateDesc());
        return dashboard;
    }

    public UserDto approveOwner(Long userId) {
        var user = userRepository.getReferenceById(userId);
        if(!user.isOwner()){
            throw new RuntimeException("Only Owner need approval to post Properties");
        }
        user.setIsPendingApproval(false);
        userRepository.save(user);
        return new UserDto(user);
    }

    public List<OfferHistoryResponse> history(){
        return offerHistoryRepo.findAll().stream().map(m -> new OfferHistoryResponse(m)).toList();
    }
}
