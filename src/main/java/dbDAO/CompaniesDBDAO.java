package dbDAO;

import beans.Company;
import db.JDBCUtils;
import db.ResultUtils;
import exceptions.NotFound;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompaniesDBDAO implements CompaniesDAO{
//    private ConnectionPool connectionPool;
    private static final String QUERY_INSERT = "INSERT INTO `couponsystem`.`companies` (`NAME`, `EMAIL`, `PASSWORD`) VALUES (?, ?, ?);";
    private static final String QUERY_DELETE = "DELETE FROM `couponsystem`.`companies` WHERE (`ID` = ?);";
    private static final String QUERY_UPDATE = "UPDATE `couponsystem`.`companies` SET `NAME` = ?, `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?);";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM couponsystem.companies;";
    private static final String QUERY_SELECT_SINGLE_COMPANY = "SELECT * FROM couponsystem.companies where id = ?;";
    private static final String QUERY_EXISTS = "select exists(select * FROM couponsystem.companies WHERE `email` = ? AND `password` = ?) as res;";
    private static final String QUERY_EXISTS_BY_ID = "select exists(select * FROM couponsystem.companies WHERE `id` = ?) as res;";


    private boolean isCompanyExistByID(int id) throws SQLException, InterruptedException {
        boolean flag = false;

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, id);

        List<?> rows = JDBCUtils.executeResult(QUERY_EXISTS_BY_ID, params);

//        return rows.size() > 0;
        for (Object row : rows) {
            flag = ResultUtils.exists((HashMap<String, Object>) row);
            break;
        }

//        System.out.println(flag);
        return flag;
    }

    @Override
    public boolean isCompanyExists(String email, String password) throws SQLException, InterruptedException {
        boolean flag = false;

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, email);
        params.put(2, password);

        List<?> rows = JDBCUtils.executeResult(QUERY_EXISTS, params);

//        return rows.size() > 0;
        for (Object row : rows) {
            flag = ResultUtils.exists((HashMap<String, Object>) row);
            break;
        }

        return flag;
    }

    @Override
    public void addCompany(Company company) throws SQLException, InterruptedException {

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());

        JDBCUtils.execute(QUERY_INSERT, params);

    }

    @Override
    public void updateCompany(int companyID, Company company) throws SQLException, InterruptedException, NotFound {
        if (!isCompanyExistByID(companyID)) {
            throw new NotFound("Company id not found in database");
        }

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        params.put(4, companyID);

        JDBCUtils.execute(QUERY_UPDATE, params);
    }

    @Override
    public void deleteCompany(int companyID) throws SQLException, InterruptedException, NotFound {
        if(!isCompanyExistByID(companyID)){
            throw new NotFound("Company not found");
        }

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, companyID);

        JDBCUtils.execute(QUERY_DELETE, params);
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException, InterruptedException {
        List<Company> companies = new ArrayList<>();
        List<?> rows = JDBCUtils.executeResults(QUERY_SELECT_ALL);

        for (Object row : rows) {
            companies.add(ResultUtils.fromHashMapReturnCompany((HashMap<String, Object>) row));
        }

        return companies;
    }

    @Override
    public Company getOneCompany(int companyID) throws InterruptedException, SQLException, NotFound {

        if(!isCompanyExistByID(companyID)){
            throw new NotFound("Company not found");
        }

        Company company = null;

        Map<Integer, Object> params = new HashMap<>();

        params.put(1, companyID);

        List<?> rows = JDBCUtils.executeResult(QUERY_SELECT_SINGLE_COMPANY, params);
        
        for (Object row : rows) {
            company = ResultUtils.fromHashMapReturnCompany((HashMap<String, Object>) row);
            break;
        }

        return company;
    }
}
