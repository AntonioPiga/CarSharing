package carsharing.Dao.Service;

import carsharing.Entity.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDao {

    public List<Company> getAllCompanies();
    public Company getCompanyById(int id) throws SQLException;
    public void addCompany(Company company);
    public void updateCompany(Company company);
    public void deleteCompany(Company company);

}
