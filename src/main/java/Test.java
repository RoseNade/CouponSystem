import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import connections.ConnectionPool;
import db.JDBCUtils;
import dbDAO.*;
import exceptions.NotFound;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        CustomersDAO customersDAO = new CustomerDBDAO();
        CouponsDAO couponsDAO = new CouponsDBDAO();
        CategoriesDAO categoriesDAO = new CategoriesDBDAO();

//        Date date = new Date();

        try {
            // Start the system
            JDBCUtils.start();

            // Create new companies


            Company company1 = new Company("Elbit", "GIL@GMAIL.COM", "1234", null);
            Company company2 = new Company("Toyota", "OZ@GMAIL.COM", "1234", null);
            Company company3 = new Company("Baz", "TOMER@GMAIL.COM", "1234", null);

            // Create new customers
            Customer customer1 = new Customer("Tomer", "Shmueli", "Sat@gmail.com", "1234");
            Customer customer2 = new Customer("Ohad", "Lineti", "asd@gmail.com", "123");
            Customer customer3 = new Customer("LoDoodoo", "Doodoo", "Chiko@gmail.com", "4434");

            // Create new coupon
            Coupon coupon1 = new Coupon();

            System.out.println("@@@@@@@@@@ADDING CUSTOMERS@@@@@@@@@@");
            customersDAO.addCustomer(customer1);
            customersDAO.addCustomer(customer2);
            customersDAO.addCustomer(customer3);

            System.out.println("@@@@@@@@@@Check if customers exist@@@@@@@@@@");
            System.out.println(customersDAO.isCustomerExists("Sat@gmail.com", "1234"));
            System.out.println(customersDAO.isCustomerExists("Sat@gmail.com", "1233"));

            System.out.println("@@@@@@@@@@ADDING COMPANIES@@@@@@@@@@");
            companiesDAO.addCompany(company1);
            companiesDAO.addCompany(company2);
            companiesDAO.addCompany(company3);

            coupon1.amount(1)
                    .image("Porno")
                    .category(Category.FOOD)
                    .startDate(new Date(2022, 6, 5))
                    .endDate(new Date(2022, 7, 8))
                    .title("BBW")
                    .description("Hardcore white chick getting pounded")
                    .companyID(1)
                    .price(4_316);
//            System.out.println(coupon1.getStartDate());
//            System.out.println(coupon1.getEndDate());
            couponsDAO.addCoupon(coupon1);

            System.out.println("@@@@@@@@@@Check if companies exist@@@@@@@@@@");
//            ((CompaniesDBDAO) companiesDAO).isCompanyExist(8);
            System.out.println(companiesDAO.isCompanyExists("GIL@GMAIL.COM", "1234"));
            System.out.println(companiesDAO.isCompanyExists("GIL@GMAIL.COM", "12334"));

            couponsDAO.addCouponPurchase(1, 1);

            System.out.println("@@@@@@@@@@Get all customers @@@@@@@@@@");
            List<Customer> customers = customersDAO.getAllCustomers();
            for (Customer customer : customers) {
                System.out.println(customer);
            }

            System.out.println("@@@@@@@@@@Get one company@@@@@@@@@@");
//            System.out.println(companiesDAO.getOneCompany(1));

            System.out.println("@@@@@@@@@@ALL COMPANIES@@@@@@@@@@");
            companiesDAO.getAllCompanies().forEach(System.out::println);

            System.out.println("@@@@@@@@@@Update companies@@@@@@@@@@");
            Company toUpdate = new Company("Levi", "ABA@GMAIL.COM", "2222", null);
            companiesDAO.updateCompany(3, toUpdate);
            companiesDAO.deleteCompany(2);

            System.out.println("@@@@@@@@@@Companies after update@@@@@@@@@@");
            companiesDAO.getAllCompanies().forEach(System.out::println);

            System.out.println("@@@@@@@@@@GET ALL COUPONS@@@@@@@@@@");
            couponsDAO.getAllCoupons().forEach(System.out::println);

            couponsDAO.deleteCouponPurchase(1, 1);
            couponsDAO.deleteCoupon(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NotFound e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            ConnectionPool.getInstance().closeAllConnection();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
