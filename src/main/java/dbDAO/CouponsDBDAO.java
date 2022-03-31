package dbDAO;

import beans.Coupon;
import db.JDBCUtils;
import db.ResultUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponsDBDAO implements CouponsDAO{
    private static final String QUERY_INSERT = "INSERT INTO `couponsystem`.`coupons` (`TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`, `COMPANY_ID`, `CATEGORY_ID`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);\n";
    private static final String QUERY_UPDATE = "UPDATE `couponsystem`.`coupons` SET `TITLE` = ?, `DESCRIPTION` = ?, `START_DATE` = ?, `END_DATE` = ?, `AMOUNT` = ?, `PRICE` = ?, `IMAGE` = ?, `COMPANY_ID` = ?, `CATEGORY_ID` = ? WHERE (`ID` = ?);\n";
    private static final String QUERY_DELETE = "DELETE FROM `couponsystem`.`coupons` WHERE (`ID` = ?);\n";
    private static final String QUERY_SELECT_SINGLE_COUPON = "SELECT * FROM couponsystem.coupons WHERE id = ?;";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM couponsystem.coupons";

    @Override
    public void addCoupon(Coupon coupon) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, coupon.getTitle());
        params.put(2, coupon.getDescription());
        params.put(3, coupon.getStartDate());
        params.put(4, coupon.getEndDate());
        params.put(5, coupon.getAmount());
        params.put(6, coupon.getPrice());
        params.put(7, coupon.getImage());
        params.put(8, coupon.getCompanyID());
        params.put(9, coupon.getCategory());

        JDBCUtils.execute(QUERY_INSERT, params);
    }

    @Override
    public void updateCoupon(int couponID, Coupon coupon) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, coupon.getTitle());
        params.put(2, coupon.getDescription());
        params.put(3, coupon.getStartDate());
        params.put(4, coupon.getEndDate());
        params.put(5, coupon.getAmount());
        params.put(6, coupon.getPrice());
        params.put(7, coupon.getImage());
        params.put(8, coupon.getCompanyID());
        params.put(9, coupon.getCategory());
        params.put(10, couponID);

        JDBCUtils.execute(QUERY_UPDATE, params);
    }

    @Override
    public void deleteCoupon(int couponID) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, couponID);

        JDBCUtils.execute(QUERY_DELETE, params);
    }

    @Override
    public Coupon getOneCoupon(int couponID) throws SQLException, InterruptedException {
        Coupon coupon = null;

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, couponID);

        List<?> rows = JDBCUtils.executeResult(QUERY_SELECT_SINGLE_COUPON, params);

        for (Object row : rows) {
            coupon = ResultUtils.fromHashMapReturnCoupon((HashMap<String, Object>) row);
            break;
        }

        return coupon;
    }

    @Override
    public ArrayList<Coupon> getAllCoupons() throws SQLException, InterruptedException {
        ArrayList<Coupon> coupons = new ArrayList<>();
        List<?> rows = JDBCUtils.executeResults(QUERY_SELECT_ALL);

        for (Object row : rows) {
            coupons.add(ResultUtils.fromHashMapReturnCoupon((HashMap<String, Object>) row));
        }

        return coupons;
    }

    @Override
    public void addCouponPurchase(int customerID, int couponID) {

    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {

    }
}
