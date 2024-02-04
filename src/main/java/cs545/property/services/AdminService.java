package cs545.property.services;


import cs545.property.dto.AdminDashboard;
import cs545.property.dto.UserDto;
import cs545.property.repository.CustomerRepo;
import cs545.property.repository.PropertyTransactionRepo;
import cs545.property.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {

    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PropertyTransactionRepo transactionRepo;

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
}
