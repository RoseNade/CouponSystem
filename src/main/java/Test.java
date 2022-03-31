import beans.Company;
import connections.ConnectionPool;
import db.JDBCUtils;
import dbDAO.CompaniesDAO;
import dbDAO.CompaniesDBDAO;

import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        try {
            JDBCUtils.start();
            Company company1 = new Company("Elbit", "GIL@GMAIL.COM", "1234", null);
            Company company2 = new Company("Toyota", "OZ@GMAIL.COM", "1234", null);
            Company company3 = new Company("Baz", "TOMER@GMAIL.COM", "1234", null);
            companiesDAO.addCompany(company1);
            companiesDAO.addCompany(company2);
            companiesDAO.addCompany(company3);
            List<Company> companies = companiesDAO.getAllCompanies();
//            System.out.println(companiesDAO.getOneCompany(1));
            for (Company company : companies) {
                System.out.println(company);
            }
            System.out.println(companiesDAO.isCompanyExists("GIL@GMAIL.COM", "1234"));
            Company toUpdate = new Company("Levi", "ABA@GMAIL.COM", "2222", null);
            companiesDAO.updateCompany(3, toUpdate);
            companiesDAO.deleteCompany(2);

            companies = companiesDAO.getAllCompanies();
//            System.out.println(companiesDAO.getOneCompany(1));
            for (Company company : companies) {
                System.out.println(company);
            }
            ConnectionPool.getInstance().closeAllConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
