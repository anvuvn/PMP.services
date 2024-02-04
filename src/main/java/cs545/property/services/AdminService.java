package cs545.property.services;


import cs545.property.dto.AdminDashboard;
import cs545.property.repository.CustomerRepo;
import cs545.property.repository.PropertyTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {

    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    PropertyTransactionRepo transactionRepo;

    public AdminDashboard getAdminDashBoard(){
        var dashboard = new AdminDashboard();
        dashboard.setRecentCustomers( customerRepo.findTop10ByOrderByCreatedDateDesc());
        dashboard.setRecentRented(transactionRepo.findTop10ByOrderByTransactionDateDesc());
        return dashboard;
    }
}
