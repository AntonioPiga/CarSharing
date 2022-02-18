package carsharing.Dao.Impl;

import carsharing.Dao.Service.CarDao;
import carsharing.Entity.Car;
import carsharing.Menu.Connession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {

    Connection connection = null;
    PreparedStatement ptmt = null;
    Statement stmt = null;
    ResultSet resultSet = null;



    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();
        try {
            String getAllComp = "SELECT * FROM CAR";
            connection = Connession.createConnection();
            ptmt = connection.prepareStatement(getAllComp);
            resultSet = ptmt.executeQuery();
            if (resultSet == null) {
                return new ArrayList<>();
            }

            while (resultSet.next()) {
                carList.add(new Car(resultSet.getInt("ID"), resultSet.getString("NAME"),resultSet.getInt("COMPANY_ID"),resultSet.getString("COMPANY"),resultSet.getString("IS_RENTED")));
            }
        } catch (
                SQLException s) {
            s.printStackTrace();
        }
        //System.out.println(carList.size());
        return carList;
    }
    public List<Car> getAllCarsByCompanyId(Integer id) {
        List<Car> carList = new ArrayList<>();
        try {
            String getAllComp = "SELECT * FROM CAR WHERE COMPANY_ID ="+id;
            connection = Connession.createConnection();
            ptmt = connection.prepareStatement(getAllComp);
            resultSet = ptmt.executeQuery();
            if (resultSet == null) {
                return new ArrayList<>();
            }

            while (resultSet.next()) {
                carList.add(new Car(resultSet.getInt("ID"), resultSet.getString("NAME"),resultSet.getInt("COMPANY_ID"),resultSet.getString("COMPANY"), resultSet.getString("IS_RENTED")));
            }
        } catch (
                SQLException s) {
            s.printStackTrace();
        }
        //System.out.println(carList.size());
        return carList;
    }

    @Override
    public Car getCarById(int id) {
        List<Car> carList = new ArrayList<>();
        try {
            String getAllCars= "SELECT * FROM CAR WHERE ID ="+id;
            connection = Connession.createConnection();
            ptmt = connection.prepareStatement(getAllCars);
            resultSet = ptmt.executeQuery();
            if (resultSet == null) {
                return null;
            }

            while (resultSet.next()) {
                carList.add(new Car(resultSet.getInt("ID"), resultSet.getString("NAME"),resultSet.getInt("COMPANY_ID"),resultSet.getString("COMPANY"), resultSet.getString("IS_RENTED")));
            }
        } catch (
                SQLException s) {
            s.printStackTrace();
        }
        //System.out.println(carList.size());
        return carList.get(0);
    }
    @Override
    public void addCar(Car car) {
        try {
           String addCar ="INSERT INTO CAR(NAME, COMPANY_ID, COMPANY, IS_RENTED) values("+"'"+car.getName()+"'"+","+car.getCompany_id()+","+"'"+car.getCompany()+"'"+",";
           String notRented = "'FALSE')";

           connection = Connession.createConnection();
           ptmt = connection.prepareStatement(addCar + notRented);
           ptmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Non corretto");
        }
    }
    @Override
    public void updateCar(Car car) throws SQLException {

        String update = "UPDATE CAR ";
        String setColumn ="SET ";
        String setIsRented ="IS_RENTED";
        String yesRented ="= 'TRUE'";
        String whereCond ="WHERE ID = "+ car.getId();
        connection = Connession.createConnection();
        ptmt = connection.prepareStatement(update + setColumn+ setIsRented + yesRented + whereCond);
        ptmt.executeUpdate();

    }
    @Override
    public void deleteCar(Car car) {

    }
    public List<Car> availableCars(int companyId) throws SQLException {
        List <Car> carAvailableList = new ArrayList<>();


        String select = "SELECT * FROM CAR " ;
        String condition1 ="WHERE COMPANY_ID = " + companyId;
        String isRented = "'FALSE'";
        String condition2 = " AND IS_RENTED = " + isRented;
        String query = select + condition1 + condition2;
        connection = Connession.createConnection();
        ptmt = connection.prepareStatement(query);
        resultSet = ptmt.executeQuery();
        if (resultSet == null) {
            return null;
        }

        while (resultSet.next()) {
            carAvailableList.add(new Car(resultSet.getInt("ID"), resultSet.getString("NAME"),resultSet.getInt("COMPANY_ID"),resultSet.getString("COMPANY"), resultSet.getString("IS_RENTED")));
        }
        Connession.closeConnection();
        return carAvailableList;
    }

}
