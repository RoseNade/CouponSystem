package dbDAO;

import beans.Company;
import exceptions.AlreadyExists;
import exceptions.NotFound;

import java.sql.SQLException;
import java.util.List;

public interface CompaniesDAO {
    boolean isCompanyExists(String email, String password) throws SQLException, InterruptedException;
    boolean isCompanyExistByID(int id) throws SQLException, InterruptedException;

    boolean isCompanyExistByTitle(String title) throws SQLException, InterruptedException;

    int returnCompanyID(String email, String password) throws SQLException, InterruptedException;

    boolean isCompanyExistByEmail(String email) throws SQLException, InterruptedException;
    boolean isCompanyExistByName(String name) throws SQLException, InterruptedException;
    void addCompany(Company company) throws SQLException, InterruptedException, AlreadyExists;
    void updateCompany(int companyID, Company company) throws SQLException, InterruptedException, NotFound;
    void updateCompanyWithoutName(int companyID, Company company) throws SQLException, InterruptedException, NotFound;
    void deleteCompany(int companyID) throws SQLException, InterruptedException, NotFound;
    List<Company> getAllCompanies() throws SQLException, InterruptedException;
    Company getOneCompany(int companyID) throws InterruptedException, SQLException, NotFound;
}
