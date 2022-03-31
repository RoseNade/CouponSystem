package dbDAO;

import beans.Company;
import beans.Customer;
import db.JDBCUtils;
import db.ResultUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDBDAO implements CustomersDAO{
//    private ConnectionPool connectionPool;
    private static final String QUERY_INSERT = "INSERT INTO `couponsystem`.`customers` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES (?, ?, ?, ?);";
    private static final String QUERY_DELETE = "DELETE FROM `couponsystem`.`customers` WHERE (`ID` = ?);";
    private static final String QUERY_UPDATE = "UPDATE `couponsystem`.`customers` SET `FIRST_NAME` = ?, `LAST_NAME` = ?, `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?);";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM couponsystem.customers;";
    private static final String QUERY_SELECT_SINGLE_CUSTOMER = "SELECT * FROM couponsystem.customers where id = ?;";
    private static final String QUERY_EXISTS = "select exists(select * FROM couponsystem.customers WHERE `email` = '?' AND `password` = '?') as res;";

    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, email);
        params.put(2, password);

        List<?> rows = JDBCUtils.executeResult(QUERY_EXISTS, params);

        return rows.size() > 0;
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());

        JDBCUtils.execute(QUERY_INSERT, params);
    }

    @Override
    public void updateCustomer(int customerID, Customer customer) throws SQLException, InterruptedException {

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, customerID);

        JDBCUtils.execute(QUERY_UPDATE, params);
    }

    @Override
    public void deleteCustomer(int customerID) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, customerID);

        JDBCUtils.execute(QUERY_DELETE, params);
    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException, InterruptedException {
        ArrayList<Customer> customers = new ArrayList<>();
        List<?> rows = JDBCUtils.executeResults(QUERY_SELECT_ALL);

        for (Object row : rows) {
            customers.add(ResultUtils.fromHashMapReturnCustomer((HashMap<String, Object>) row));
        }

        return customers;
    }

    @Override
    public Customer getOneCustomer(int customerID) throws SQLException, InterruptedException {
        Customer customer = null;

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, customerID);

        List<?> rows = JDBCUtils.executeResult(QUERY_SELECT_SINGLE_CUSTOMER, params);

        for (Object row : rows) {
            customer = ResultUtils.fromHashMapReturnCustomer((HashMap<String, Object>) rows.get(0));
            break;
        }

        return customer;
    }
}
