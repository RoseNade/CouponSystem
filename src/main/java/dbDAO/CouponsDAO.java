package dbDAO;

import beans.Coupon;
import exceptions.NotFound;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public interface CouponsDAO {
    void addCoupon(Coupon coupon) throws SQLException, InterruptedException;
    void updateCoupon(int couponID, Coupon coupon) throws SQLException, InterruptedException, NotFound;
    void deleteCoupon(int couponID) throws SQLException, InterruptedException, NotFound;
    Coupon getOneCoupon(int couponID) throws SQLException, InterruptedException, NotFound, ParseException;
    ArrayList<Coupon> getAllCoupons() throws SQLException, InterruptedException, ParseException;

    void addCouponPurchase(int customerID, int couponID) throws SQLException, InterruptedException, NotFound;
    void deleteCouponPurchase(int customerID, int couponID) throws NotFound, SQLException, InterruptedException;
}
