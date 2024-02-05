package cs545.property.dto;

import cs545.property.domain.Customer;
import cs545.property.domain.PropertyTransaction;
import lombok.Data;

import java.util.List;

@Data
public class AdminDashboard {
    private List<PropertyTransaction> recentRented;
    private List<Customer> recentCustomers;
}
