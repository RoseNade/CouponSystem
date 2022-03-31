package dbDAO;

import beans.Coupon;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponsDAO {
    void addCoupon(Coupon coupon) throws SQLException, InterruptedException;
    void updateCoupon(int couponID, Coupon coupon) throws SQLException, InterruptedException;
    void deleteCoupon(int couponID) throws SQLException, InterruptedException;
    Coupon getOneCoupon(int couponID) throws SQLException, InterruptedException;
    ArrayList<Coupon> getAllCoupons() throws SQLException, InterruptedException;
    void addCouponPurchase(int customerID, int couponID);
    void deleteCouponPurchase(int customerID, int couponID);
}
