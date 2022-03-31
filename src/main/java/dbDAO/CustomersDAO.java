package dbDAO;

import beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomersDAO {

    boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException;
    void addCustomer(Customer customer) throws SQLException, InterruptedException;
    void updateCustomer(int customerID, Customer customer) throws SQLException, InterruptedException;
    void deleteCustomer(int customerID) throws SQLException, InterruptedException;
    ArrayList<Customer> getAllCustomers() throws SQLException, InterruptedException;
    Customer getOneCustomer(int customerID) throws SQLException, InterruptedException;

}
