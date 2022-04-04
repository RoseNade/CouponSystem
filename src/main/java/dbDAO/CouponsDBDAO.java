package dbDAO;

import beans.Category;
import beans.Coupon;
import db.JDBCUtils;
import db.ResultUtils;
import exceptions.NotFound;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponsDBDAO implements CouponsDAO {
    private static final String QUERY_INSERT = "INSERT INTO `couponsystem`.`coupons` (`TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`, `COMPANY_ID`, `CATEGORY_ID`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String QUERY_UPDATE = "UPDATE `couponsystem`.`coupons` SET `TITLE` = ?, `DESCRIPTION` = ?, `START_DATE` = ?, `END_DATE` = ?, `AMOUNT` = ?, `PRICE` = ?, `IMAGE` = ?, `COMPANY_ID` = ?, `CATEGORY_ID` = ? WHERE (`ID` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `couponsystem`.`coupons` WHERE (`ID` = ?);";
    private static final String QUERY_SELECT_SINGLE_COUPON = "SELECT * FROM couponsystem.coupons WHERE id = ?;";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM couponsystem.coupons";
    private static final String QUERY_INSERT_INTO_COSTUMERS_VS_COUPONS = "INSERT INTO `couponsystem`.`customers_vs_coupons` (`CUSTOMER_ID`, `COUPON_ID`) VALUES (?, ?);";
    private static final String QUERY_COUPON_EXISTS_BY_ID = "select exists(select * FROM couponsystem.coupons WHERE `id` = ?) as res;";
    private static final String QUERY_CUSTOMER_EXISTS_BY_ID = "select exists(select * FROM couponsystem.customers WHERE `id` = ?) as res;";
    private static final String QUERY_DELETE_CUSTOMER_VS_COUPON = "DELETE FROM `couponsystem`.`customers_vs_coupons` WHERE (`CUSTOMER_ID` = ?) and (`COUPON_ID` = ?);";


    private boolean isCustomerExistByID(int id) throws SQLException, InterruptedException {
        boolean flag = false;

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, id);

        List<?> rows = JDBCUtils.executeResult(QUERY_CUSTOMER_EXISTS_BY_ID, params);

        for (Object row : rows) {
            flag = ResultUtils.exists((HashMap<String, Object>) row);
            break;
        }

        return flag;
    }

    private boolean isCouponExist(int id) throws SQLException, InterruptedException {
        boolean flag = false;

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, id);

        List<?> rows = JDBCUtils.executeResult(QUERY_COUPON_EXISTS_BY_ID, params);

//        return rows.size() > 0;
        for (Object row : rows) {
            flag = ResultUtils.exists((HashMap<String, Object>) row);
            break;
        }

//        System.out.println(flag);
        return flag;
    }

    @Override
    public void addCoupon(Coupon coupon) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();

        int yearStartDate = coupon.getStartDate().getYear() - 1_900;
        int yearEndDate = coupon.getEndDate().getYear() - 1_900;
        int monthStartDate = coupon.getStartDate().getMonth() - 1;
        int monthEndDate = coupon.getEndDate().getMonth() - 1;
        coupon.getStartDate().setYear(yearStartDate);
        coupon.getEndDate().setYear(yearEndDate);
        coupon.getStartDate().setMonth(monthStartDate);
        coupon.getEndDate().setMonth(monthEndDate);


        params.put(1, coupon.getTitle());
        params.put(2, coupon.getDescription());
        params.put(3, coupon.getStartDate());
        params.put(4, coupon.getEndDate());
        params.put(5, coupon.getAmount());
        params.put(6, coupon.getPrice());
        params.put(7, coupon.getImage());
        params.put(8, coupon.getCompanyID());
        params.put(9, Category.valueOf(coupon.getCategory().toString()).ordinal() + 1);

        JDBCUtils.execute(QUERY_INSERT, params);
    }

    @Override
    public void updateCoupon(int couponID, Coupon coupon) throws SQLException, InterruptedException, NotFound {

        if (!isCouponExist(couponID)) {
            throw new NotFound("Coupon not found");
        }

        Map<Integer, Object> params = new HashMap<>();

        int yearStartDate = coupon.getStartDate().getYear() - 1_900;
        int yearEndDate = coupon.getEndDate().getYear() - 1_900;
        int monthStartDate = coupon.getStartDate().getMonth() - 1;
        int monthEndDate = coupon.getEndDate().getMonth() - 1;
        coupon.getStartDate().setYear(yearStartDate);
        coupon.getEndDate().setYear(yearEndDate);
        coupon.getStartDate().setMonth(monthStartDate);
        coupon.getEndDate().setMonth(monthEndDate);

        params.put(1, coupon.getTitle());
        params.put(2, coupon.getDescription());
        params.put(3, coupon.getStartDate());
        params.put(4, coupon.getEndDate());
        params.put(5, coupon.getAmount());
        params.put(6, coupon.getPrice());
        params.put(7, coupon.getImage());
        params.put(8, coupon.getCompanyID());
        params.put(9, Category.valueOf(coupon.getCategory().toString()).ordinal() + 1);
        params.put(10, couponID);

        JDBCUtils.execute(QUERY_UPDATE, params);
    }

    @Override
    public void deleteCoupon(int couponID) throws SQLException, InterruptedException, NotFound {

        if (!isCouponExist(couponID)) {
            throw new NotFound("Coupon not found");
        }

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, couponID);

        JDBCUtils.execute(QUERY_DELETE, params);
    }

    @Override
    public Coupon getOneCoupon(int couponID) throws SQLException, InterruptedException, NotFound, ParseException {

        if (!isCouponExist(couponID)) {
            throw new NotFound("Coupon not found");
        }

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
    public void addCouponPurchase(int customerID, int couponID) throws SQLException, InterruptedException, NotFound {
        if (!isCouponExist(couponID) || !isCustomerExistByID(customerID)) {
            throw new NotFound("Coupon not found");
        }

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);

        JDBCUtils.execute(QUERY_INSERT_INTO_COSTUMERS_VS_COUPONS, params);
    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) throws NotFound, SQLException, InterruptedException {

        if (!isCouponExist(couponID) || !isCustomerExistByID(customerID)) {
            throw new NotFound("Coupon or costumer not found");
        }

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, customerID);
        params.put(2, couponID);

        JDBCUtils.execute(QUERY_DELETE_CUSTOMER_VS_COUPON, params);
    }
}
