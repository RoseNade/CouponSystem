package dbDAO;

import beans.Coupon;
import beans.Customer;
import exceptions.AlreadyExists;
import exceptions.NotFound;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomersDAO {

//    List<Coupon> getAllCouponsBoughtByCustomer(int customerID) throws SQLException, InterruptedException;

    boolean isCouponOwnedByCustomer(int customerID, int couponID) throws SQLException, InterruptedException;

    int returnCustomerID(String email, String password) throws SQLException, InterruptedException;

    boolean isCustomerExists(String email) throws SQLException, InterruptedException;

    boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException;
    void addCustomer(Customer customer) throws SQLException, InterruptedException, AlreadyExists;
    void updateCustomer(int customerID, Customer customer) throws SQLException, InterruptedException, NotFound;
    void deleteCustomer(int customerID) throws SQLException, InterruptedException, NotFound;
    ArrayList<Customer> getAllCustomers() throws SQLException, InterruptedException;
    Customer getOneCustomer(int customerID) throws SQLException, InterruptedException, NotFound;

}
