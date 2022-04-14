package beans;

import dbDAO.CompaniesDAO;
import dbDAO.CompaniesDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons;

    CompaniesDAO companiesDAO = new CompaniesDBDAO();

    public Company() {
    }

    public Company(int id, String name, String email, String password) {
        this(name, email, password);
        this.id = id;
    }

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Company(String name, String email, String password, ArrayList<Coupon> coupons) {
        this(name, email, password);
        this.coupons = coupons;
    }

    public Company(int id, String name, String email, String password, ArrayList<Coupon> coupons) {
        this(name, email, password, coupons);
        this.id = id;
    }

    public int getId() throws SQLException, InterruptedException {
        this.id = companiesDAO.returnCompanyID(this.getEmail(), this.getPassword());
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(ArrayList<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
