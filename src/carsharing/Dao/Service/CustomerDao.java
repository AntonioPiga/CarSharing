package carsharing.Dao.Service;

import carsharing.Entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao {

    public List<Customer> getAllCustomers() throws SQLException;


    public List<Customer> getCustomerById(int id) throws SQLException;
    public void addCustomer(Customer customer) throws SQLException;
    public void updateCustomer(Customer customer);
    public void deleteCustomer(Customer customer);

}
