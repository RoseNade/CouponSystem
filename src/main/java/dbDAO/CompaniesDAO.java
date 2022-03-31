package dbDAO;

import beans.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompaniesDAO {
    boolean isCompanyExists(String email, String password) throws SQLException, InterruptedException;
    void addCompany(Company company) throws SQLException, InterruptedException;
    void updateCompany(int companyID, Company company) throws SQLException, InterruptedException;
    void deleteCompany(int companyID) throws SQLException, InterruptedException;
    List<Company> getAllCompanies() throws SQLException, InterruptedException;
    Company getOneCompany(int companyID) throws InterruptedException, SQLException;
}
