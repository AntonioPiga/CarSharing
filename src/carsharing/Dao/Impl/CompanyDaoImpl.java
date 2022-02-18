package carsharing.Dao.Impl;

import carsharing.Dao.Service.CompanyDao;
import carsharing.Entity.Company;
import carsharing.Menu.Connession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {

    Connection connection = null;
    PreparedStatement ptmt = null;
    Statement stmt = null;
    ResultSet resultSet = null;




    @Override
    public List<Company> getAllCompanies() {
        List<Company> companyList = new ArrayList<>();
        try {
            String getAllComp = "SELECT * FROM COMPANY";
            connection = Connession.createConnection();
            ptmt = connection.prepareStatement(getAllComp);
            resultSet = ptmt.executeQuery();
            if (resultSet == null) {return new ArrayList<>();};
            while(resultSet.next()) {
                companyList.add(new Company(resultSet.getInt("ID"),resultSet.getString("NAME")));
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        //System.out.println(companyList.get(0).getId());
        return companyList;
    }
    @Override
    public Company getCompanyById(int id) throws SQLException {
        String companyQuery = "SELECT * FROM COMPANY WHERE ID ="+id;
        connection = Connession.createConnection();
        ptmt = connection.prepareStatement(companyQuery);
        resultSet = ptmt.executeQuery();
        while(resultSet.next()){

            Company company = new Company(resultSet.getInt("ID"), resultSet.getString("NAME"));
            return company;
        }
        return null;//da sistemare
    }
    public String nameCompanyById(int id) throws SQLException {
        String companyQuery = "SELECT NAME FROM COMPANY WHERE id ="+id;
        connection = Connession.createConnection();
        ptmt = connection.prepareStatement(companyQuery);
        resultSet = ptmt.executeQuery();
        while (resultSet.next()) {

            String name = new Company(resultSet.getInt("ID"), resultSet.getString("NAME")).getName();
            System.out.println("name: è"+name);
            return name;
        }
        return null;//da sistemare
    }

    @Override
    public void addCompany(Company company) {
        try {
            String addCompany ="INSERT INTO COMPANY(NAME) values("+"'"+company.getName()+"')";
            connection = Connession.createConnection();
            ptmt = connection.prepareStatement(addCompany);
            ptmt.executeUpdate();
        } catch (Exception e) {

        }
    }

    @Override
    public void updateCompany(Company company) {

    }

    @Override
    public void deleteCompany(Company company) {

    }

}
