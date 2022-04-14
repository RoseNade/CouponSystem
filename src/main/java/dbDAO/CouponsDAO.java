package dbDAO;

import beans.Category;
import beans.Coupon;
import exceptions.NotFound;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public interface CouponsDAO {
    List<Coupon> getAllCouponsCustomerOwnsUnderCertainPrice(int customerID, double price) throws SQLException, InterruptedException;

    List<Coupon> getAllCouponsCustomerOwnsWithSpecificCategory(int customerID, Category category) throws SQLException, InterruptedException;

    boolean isCouponExpired(int couponID) throws SQLException, InterruptedException;

    boolean isMoreThanOneCoupon(int couponID) throws SQLException, InterruptedException;

    boolean isCouponExistByTitleAndID(int companyID, String title) throws SQLException, InterruptedException;

    List<Integer> getAllCouponByCustomerIDAsInteger(int customerID) throws SQLException, InterruptedException;

    void addCoupon(Coupon coupon) throws SQLException, InterruptedException;
    void updateCoupon(int couponID, Coupon coupon) throws SQLException, InterruptedException, NotFound;
    void deleteCoupon(int couponID) throws SQLException, InterruptedException, NotFound;
    int getCouponID(int companyID, String title) throws SQLException, InterruptedException;
    void deleteCouponPurchaseByCouponID(int couponID) throws NotFound, SQLException, InterruptedException;
    Coupon getOneCoupon(int couponID) throws SQLException, InterruptedException, NotFound, ParseException;
    ArrayList<Coupon> getAllCoupons() throws SQLException, InterruptedException, ParseException;
    List<Coupon> getAllCouponsByCompanyID(int companyID) throws SQLException, InterruptedException;

    List<Coupon> getAllCouponsByCompanyIDAndUnderPrice(int companyID, double price) throws SQLException, InterruptedException;

    List<Coupon> getAllCouponsByCompanyIDAndCategory(int companyID, Category category) throws SQLException, InterruptedException;

    void addCouponPurchase(int customerID, int couponID) throws SQLException, InterruptedException, NotFound;
    void deleteCouponPurchase(int customerID, int couponID) throws NotFound, SQLException, InterruptedException;
}
