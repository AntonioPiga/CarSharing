package carsharing.Logic;

import carsharing.Dao.Impl.CarDaoImpl;
import carsharing.Dao.Impl.CompanyDaoImpl;
import carsharing.Dao.Impl.CustomerDaoImpl;
import carsharing.Entity.Car;
import carsharing.Entity.Customer;
import carsharing.Menu.Connession;
import carsharing.Menu.FirstMenu;

import java.sql.SQLException;
import java.util.*;

public class CustomerLogic {

    static CustomerDaoImpl customerDao = new CustomerDaoImpl();
    static Scanner scanner = new Scanner(System.in);
    static Integer idCustomer = 0;
    static CompanyDaoImpl companyDao = new CompanyDaoImpl();
    static CarDaoImpl carDao = new CarDaoImpl();


    static int persistentIdCar = 0;

    public static void printAllCustomers() throws SQLException {

        if(customerDao.getAllCustomers().size() == 0) {
            System.out.println("The customer list is empty!");
            FirstMenu.logInOrExit();
        } else {
            System.out.println("The Customer list:");
            for (int i = 0; i < customerDao.getAllCustomers().size(); i++) {
                System.out.println(i+1 + ". " + customerDao.getAllCustomers().get(i).getName());
            }
            System.out.println("0. Back");
            String choice = scanner.nextLine();
            idCustomer = Integer.parseInt(choice);
            if(choice.equals("0")) {
                FirstMenu.logInOrExit();
            } else customerMenu(idCustomer);
        }
    }

    public static void createCustomer() throws SQLException {

        String customerName = "";
        System.out.println("Enter the customer name:");
        customerName = scanner.nextLine();
        CustomerDaoImpl customerDao = new CustomerDaoImpl();
        idCustomer++;
        customerDao.addCustomer(new Customer(idCustomer, customerName, null));
        System.out.println("The customer was added!");
        FirstMenu.logInOrExit();

    }

    public static void customerMenu(Integer idCustomer) throws SQLException {
        String choice ="";
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
        choice = scanner.nextLine();
        if (choice.equals("0")) {
            printAllCustomers();
        } else if(choice.equals("1")) {
            if (customerDao.getCustomerById(idCustomer).get(0).getRentedCarId() == 0) rentACar(); // bisogna gestire a false il rentable quando il customer possiede un auto
            else {System.out.println("You've already rented a car!");
                  customerMenu(idCustomer);
            }
        } else if(choice.equals("2")) {

            int provaId = idCustomer;
            if (customerDao.getCustomerById(idCustomer).get(0).getRentedCarId() == 0) {
                System.out.println("You didn't rent a car!");
                customerMenu(null);
            } else {
                int idCust = customerDao.getCustomerById(idCustomer).get(0).getId();
                int idCar = customerDao.getCustomerById(idCustomer).get(0).getRentedCarId();

                customerDao.returnACar(customerDao.getCustomerById(idCustomer).get(0).getName(), idCar);
                persistentIdCar = 0;
                //bug, se esci e rientri nel menu potresti confondere gli id dei customer e della persistent idCar
                System.out.println("You've returned a rented car!");
                //a questo punto settare a null l'id rented_car del customer
                customerMenu(idCustomer);
            }
        }
        else if(choice.equals("3")) {
            if(customerDao.getCustomerById(idCustomer).get(0).getRentedCarId() == 0) {
                System.out.println("You didn't rent a car!");
                customerMenu(idCustomer);
            } else {
                System.out.println("Your rented car:");
                int carId = customerDao.getCustomerById(idCustomer).get(0).getRentedCarId();
                String carName = carDao.getCarById(carId).getName();
                System.out.println(carName);
                System.out.println("Company:");
                String companyName = carDao.getCarById(carId).getCompany();
                System.out.println(companyName);

                customerMenu(idCustomer);
            }

        }
    }

    private static void printRentedCars(int persistentIdCar) throws SQLException {

        carDao.getCarById(persistentIdCar);

    }

    private static int rentACar() throws SQLException {
        int realIdCar = 0;
        //choose a company
        System.out.println("Choose a company:");
        for(int i = 0; i < companyDao.getAllCompanies().size(); i++) {
            System.out.println(companyDao.getAllCompanies().get(i).getId()+". " + companyDao.getAllCompanies().get(i).getName());
        }
        System.out.println("0. Back");

        String choiceCompany = scanner.nextLine();

        if(choiceCompany.equals("0")) {
            customerMenu(null);
        } else{
            List<Car> getAllCars = carDao.getAllCars();
            List<Car> carListOfTheCompany = carDao.availableCars(Integer.parseInt(choiceCompany));
            String companyName = companyDao.getCompanyById(Integer.parseInt(choiceCompany)).getName();
            if (carListOfTheCompany.size() == 0) {
                System.out.println("No available cars in the '"+ companyName+ "' company");
                customerMenu(idCustomer);
            } else {

                System.out.println("Choose a car:");
                int contatoreChoiceCar = 1;
                Map<Integer, Integer> mappaContatoreIdCar = new HashMap<Integer, Integer>();
                //System.out.println(choiceCompany);
                for (int i = 0; i < carListOfTheCompany.size(); i++) {

                    System.out.println(i + 1 + ". " + carListOfTheCompany.get(i).getName());
                    mappaContatoreIdCar.put(contatoreChoiceCar, carListOfTheCompany.get(i).getId());
                    contatoreChoiceCar++;
                }
                System.out.println("0. Back");
                String choice = scanner.nextLine();
                if (choice.equals("0")) {
                    customerMenu(null);
                }
                realIdCar = mappaContatoreIdCar.get(Integer.parseInt(choice));
                persistentIdCar = realIdCar;
                Connession.customerRentCar(customerDao.getCustomerById(idCustomer).get(0).getName(), realIdCar);
                carDao.updateCar(carDao.getCarById(realIdCar));
                //carDao.getCarById(realIdCar).setRented("TRUE");
                String carName = carDao.getCarById(realIdCar).getName();
                Boolean canRent = false;
                System.out.println("You rented " + "'" + carName + "'");
                customerMenu(idCustomer);
            }
        }
        return realIdCar;
    }
}
