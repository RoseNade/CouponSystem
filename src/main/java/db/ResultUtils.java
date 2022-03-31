package db;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResultUtils {
    //private String email;
    //    private String password;
    //    private ArrayList<Coupon> coupons;
    public static Company fromHashMapReturnCompany(Map<String, Object> row){
        int id = (int) row.get("ID");
        String name = (String) row.get("NAME");
        String email = (String) row.get("EMAIL");
        String password = (String) row.get("PASSWORD");

        return new Company(id, name, email, password, null);
    }

    public static Customer fromHashMapReturnCustomer(Map<String, Object> row){
        int id = (int) row.get("ID");
        String firstName = (String) row.get("FIRST_NAME");
        String lastName = (String) row.get("LAST_NAME");
        String email = (String) row.get("EMAIL");
        String password = (String) row.get("PASSWORD");

        return new Customer(id, firstName, lastName, email, password);
    }

    public static Coupon fromHashMapReturnCoupon(Map<String, Object> row){
        int id = (int) row.get("ID");
        int companyID = (int) row.get("COMPANY_ID");
        Category category = (Category) row.get("CATEGORY");
        String title = (String) row.get("TITLE");
        String description = (String) row.get("DESCRIPTION");
        Date startDate = (Date) row.get("START_DATE");
        Date endDate = (Date) row.get("END_DATE");
        int amount = (int) row.get("AMOUNT");
        double price = (double) row.get("PRICE");
        String image = (String) row.get("IMAGE");
        return new Coupon(id, companyID, category, title, description, startDate, endDate, amount, price
        , image);
    }

//    public static boolean exists(HashMap<String, Object> row){
//        long result = (long) row.get("res");
//        return result == 1;
//    }

}
