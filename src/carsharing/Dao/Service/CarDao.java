package carsharing.Dao.Service;

import carsharing.Entity.Car;

import java.sql.SQLException;
import java.util.List;

public interface CarDao {
    public List<Car> getAllCars();
    public Car getCarById(int id);
    public List<Car> getAllCarsByCompanyId(Integer id);
    public void addCar(Car car);
    public void updateCar(Car car) throws SQLException;
    public void deleteCar(Car car);

}
