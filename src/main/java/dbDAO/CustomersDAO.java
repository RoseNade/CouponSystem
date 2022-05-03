package dbDAO;

import beans.Customer;
import exceptions.CustomExceptions;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomersDAO {

//    List<Coupon> getAllCouponsBoughtByCustomer(int customerID) throws SQLException, InterruptedException;

    boolean isCouponOwnedByCustomer(int customerID, int couponID) throws SQLException, InterruptedException;

    int returnCustomerID(String email, String password) throws SQLException, InterruptedException;

    boolean isCustomerExists(String email) throws SQLException, InterruptedException;

    boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException;
    void addCustomer(Customer customer) throws SQLException, InterruptedException, CustomExceptions;
    void updateCustomer(int customerID, Customer customer) throws SQLException, InterruptedException;
    void deleteCustomer(int customerID) throws SQLException, InterruptedException;
    ArrayList<Customer> getAllCustomers() throws SQLException, InterruptedException;
    Customer getOneCustomer(int customerID) throws SQLException, InterruptedException;

    boolean isCustomerExistByID(int customerID) throws SQLException, InterruptedException;
}
