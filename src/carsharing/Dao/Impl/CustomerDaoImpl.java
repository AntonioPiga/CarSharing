package carsharing.Dao.Impl;

import carsharing.Menu.Connession;
import carsharing.Dao.Service.CustomerDao;
import carsharing.Entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    static Connection connection = null;
    static PreparedStatement ptmt = null;
    static Statement stmt = null;
    static ResultSet resultSet = null;


    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        try {
            String getAllCustomers = "SELECT * FROM CUSTOMER";
            connection = Connession.createConnection();
            ptmt = connection.prepareStatement(getAllCustomers);
            resultSet = ptmt.executeQuery();
            if (resultSet == null) {return new ArrayList<>();};
            while(resultSet.next()) {
                customerList.add(new Customer(resultSet.getInt("ID"),resultSet.getString("NAME"), resultSet.getInt("RENTED_CAR_ID")));
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        //System.out.println(companyList.get(0).getId());
        return customerList;
    }

    @Override
    public List<Customer> getCustomerById(int id) throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        try {
            String getCustomerById = "SELECT * FROM CUSTOMER WHERE ID =" + id;
            connection = Connession.createConnection();
            ptmt = connection.prepareStatement(getCustomerById);
            resultSet = ptmt.executeQuery();
            if (resultSet == null) {
                return new ArrayList<>();
            }
            while (resultSet.next()) {
                customerList.add(new Customer(resultSet.getInt("ID"), resultSet.getString("NAME"),resultSet.getInt("RENTED_CAR_ID")));
            }
        } catch (
                SQLException s) {
            s.printStackTrace();
        }
        //System.out.println(carList.size());
        return customerList;
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException{

        String addCustomer ="INSERT INTO CUSTOMER(NAME, RENTED_CAR_ID) values("+"'"+customer.getName()+"'"+","+customer.getRentedCarId()+")";
        connection = Connession.createConnection();
        ptmt = connection.prepareStatement(addCustomer);
        ptmt.executeUpdate();

    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public void deleteCustomer(Customer customer) {

    }
    public void returnACar(String customerName, int idCar) {
        try {
            String returnCar = "UPDATE CUSTOMER ";
            String setRented = "SET RENTED_CAR_ID = NULL ";
            String condition = "WHERE NAME = ";
            String custName = "" +"'"+ customerName + "' ";

            String query = returnCar + setRented + condition + custName;
            connection = Connession.createConnection();
            ptmt = connection.prepareStatement(query);
            ptmt.executeUpdate();

            String updateCar = "UPDATE CAR ";
            String setIsRented = "SET IS_RENTED = 'FALSE'";
            String whereCond = " WHERE ID = " + idCar;

            String query2 = updateCar + setIsRented + whereCond;
            connection = Connession.createConnection();
            ptmt = connection.prepareStatement(query2);
            ptmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Non corretto");
        }
    }
}
