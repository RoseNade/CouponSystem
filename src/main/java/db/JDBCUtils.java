package db;

import beans.Category;
import connections.ConnectionPool;
import dbDAO.CategoriesDAO;
import dbDAO.CategoriesDBDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtils {
    // login info
//    private static final String URL = "jdbc:mysql://localhost:3306/?createDatabaseIfNotExist=FALSE&useTimezone=TRUE&serverTimezone=UTC";
//    private static final String USER = "root";
//    private static final String PASSWORD = "193746825Ss";

    // actions to do when the program first starts
    private static final String CREATE_SCHEMA = "create schema CouponSystem";

    private static final String DROP_SCHEMA = "DROP DATABASE `couponsystem`;";

    private static final String CREATE_COMPANY_TABLE = "CREATE TABLE `couponsystem`.`companies` (\n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `NAME` VARCHAR(45) NOT NULL,\n" +
            "  `EMAIL` VARCHAR(45) NOT NULL,\n" +
            "  `PASSWORD` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`));\n";

    private static final String CREATE_CUSTOMERS_TABLE = "CREATE TABLE `couponsystem`.`customers` (\n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `FIRST_NAME` VARCHAR(45) NOT NULL,\n" +
            "  `LAST_NAME` VARCHAR(45) NOT NULL,\n" +
            "  `EMAIL` VARCHAR(45) NOT NULL,\n" +
            "  `PASSWORD` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`));\n";

    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE `couponsystem`.`categories` (\n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `NAME` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`));\n";

    private static final String CREATE_COUPONS_TABLE = "CREATE TABLE `couponsystem`.`coupons` (\n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `COMPANY_ID` INT NOT NULL,\n" +
            "  `CATEGORY_ID` INT NOT NULL,\n" +
            "  `TITLE` VARCHAR(45) NOT NULL,\n" +
            "  `DESCRIPTION` VARCHAR(45) NOT NULL,\n" +
            "  `START_DATE` DATE NOT NULL,\n" +
            "  `END_DATE` DATE NOT NULL,\n" +
            "  `AMOUNT` INT NOT NULL,\n" +
            "  `PRICE` DOUBLE NOT NULL,\n" +
            "  `IMAGE` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`),\n" +
            "  INDEX `COMPANY_ID_idx` (`COMPANY_ID` ASC) VISIBLE,\n" +
            "  INDEX `CATEGORY_ID_idx` (`CATEGORY_ID` ASC) VISIBLE,\n" +
            "  CONSTRAINT `COMPANY_ID`\n" +
            "    FOREIGN KEY (`COMPANY_ID`)\n" +
            "    REFERENCES `couponsystem`.`companies` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `CATEGORY_ID`\n" +
            "    FOREIGN KEY (`CATEGORY_ID`)\n" +
            "    REFERENCES `couponsystem`.`categories` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";


    private static final String CREATE_CUSTOMERS_VS_COUPONS_TABLE = "CREATE TABLE `couponsystem`.`customers_vs_coupons` (\n" +
            "  `CUSTOMER_ID` INT NOT NULL,\n" +
            "  `COUPON_ID` INT NOT NULL,\n" +
            "  PRIMARY KEY (`CUSTOMER_ID`, `COUPON_ID`),\n" +
            "  INDEX `COUPON_ID_idx` (`COUPON_ID` ASC) VISIBLE,\n" +
            "  CONSTRAINT `CUSTOMER_ID`\n" +
            "    FOREIGN KEY (`CUSTOMER_ID`)\n" +
            "    REFERENCES `couponsystem`.`customers` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `COUPON_ID`\n" +
            "    FOREIGN KEY (`COUPON_ID`)\n" +
            "    REFERENCES `couponsystem`.`coupons` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";


    public static void start() throws SQLException, InterruptedException {
        try {
            execute(DROP_SCHEMA);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        execute(CREATE_SCHEMA);
        execute(CREATE_COMPANY_TABLE);
        execute(CREATE_CATEGORIES_TABLE);
        execute(CREATE_CUSTOMERS_TABLE);
        execute(CREATE_COUPONS_TABLE);
        execute(CREATE_CUSTOMERS_VS_COUPONS_TABLE);

        CategoriesDAO categoriesDAO = new CategoriesDBDAO();
        for (Category category : Category.values()) {
            String name = category.toString();
            categoriesDAO.addCategory(name);
        }
    }

    private static void closePreparedStatement(PreparedStatement statement) throws SQLException {
        statement.close();
    }

    private static void closeResultSet(ResultSet resultSet) throws SQLException {
        resultSet.close();
    }

    private static void closeStatementReturnConnection(Connection connection, PreparedStatement statement) throws SQLException {
        closePreparedStatement(statement);
        ConnectionPool.getInstance().restoreConnection(connection);
    }

    private static void closeResourcesReturnConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        closeResultSet(resultSet);
        closePreparedStatement(statement);
        ConnectionPool.getInstance().restoreConnection(connection);
    }

    public static void execute(String query, Map<Integer, Object> map) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        for (Map.Entry<Integer, Object> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Object obj = entry.getValue();

            if (obj instanceof Integer) {
                statement.setInt(key, (int) obj);
            } else if (obj instanceof String) {
                statement.setString(key, (String) obj);
            } else if (obj instanceof Double) {
                statement.setDouble(key, (double) obj);
            } else if (obj instanceof Date) {
                statement.setDate(key, (Date) obj);
            }

        }

        statement.execute();

        closeStatementReturnConnection(connection, statement);
    }

    private static List<?> resultSetToArrayList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData(); // meta data --> ID אוסף של נתונים COMPANY_NAME, COMPANY_EMAIL, COMPANY_PASSWORD

        int columns = md.getColumnCount(); // עמודות

        List<HashMap<String, Object>> list = new ArrayList<>(); // רשימה של מפות, STRING "ID" = 1

        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; i++) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }

    public static List<?> executeResults(String query) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        List<?> list = resultSetToArrayList(resultSet);

        closeResourcesReturnConnection(connection, statement, resultSet);

        return list;
    }

    public static void execute(String query) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.execute();

        closeStatementReturnConnection(connection, statement);
    }

    public static List<?> executeResult(String query, Map<Integer, Object> params) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        for (Map.Entry<Integer, Object> entry : params.entrySet()) {
            Integer key = entry.getKey();
            Object obj = entry.getValue();

            if (obj instanceof Integer) {
                statement.setInt(key, (int) obj);
            } else if (obj instanceof String) {
                statement.setString(key, (String) obj);
            } else if (obj instanceof Double) {
                statement.setDouble(key, (double) obj);
            } else if (obj instanceof Date) {
                statement.setDate(key, (Date) obj);
            }
        }

        ResultSet resultSet = statement.executeQuery();

        List<?> list = resultSetToArrayList(resultSet);

        closeResourcesReturnConnection(connection, statement, resultSet);

        return list;
    }

//    public static boolean exists(String query, int id){
//
//        Map<Integer, Integer> param = new HashMap<>();
//        if();
//    }
}
