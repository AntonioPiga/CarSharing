package carsharing.Logic;

import carsharing.Dao.Impl.CarDaoImpl;
import carsharing.Dao.Impl.CompanyDaoImpl;
import carsharing.Entity.Car;
import carsharing.Entity.Company;
import carsharing.Menu.FirstMenu;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CompanyLogic {

    static Scanner scanner = new Scanner(System.in);
    static String choiceCompany = "";
    static Integer idCar = 0;


    public static void managerMenu() throws SQLException {
        String choice = "";
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
        choice = scanner.nextLine();
        if (choice.equals("0")) {
            FirstMenu.logInOrExit();
        } else {

        }
        if(choice.equals("1")) {

            CompanyDaoImpl companyDao = new CompanyDaoImpl();

            if(companyDao.getAllCompanies().size() != 0) {
                System.out.println("Choose the company:");
                for(int i = 0; i < companyDao.getAllCompanies().size(); i++) {
                    System.out.println(companyDao.getAllCompanies().get(i).getId()+". "+companyDao.getAllCompanies().get(i).getName());
                }
                //companyDao.getAllCompanies().forEach(System.out::println);
                System.out.println("0. Back");
                chooseCompany();
            } else {
                System.out.println("The company list is empty!");
                System.out.println();
                managerMenu();
            }
        } else if (choice.equals("2")) {
            CompanyDaoImpl companyDao = new CompanyDaoImpl();
            System.out.println("Enter the company name");
            String name = scanner.nextLine();
            Company company = new Company();
            company.setName(name);
            companyDao.addCompany(company);
            System.out.println("The company was created!");
            System.out.println();
            //System.out.println("ID  = "+companyDao.getAllCompanies().get(0).getId());
            managerMenu();
        } else if (choice.equals("0")) {
            FirstMenu.logInOrExit();
        }
    }
    public static void chooseCompany() throws SQLException {
        //System.out.println("Choose the company");
        String scelta = scanner.nextLine();
        choiceCompany = scelta;
        if(scelta.equals("0")) managerMenu();
        else{
            CompanyDaoImpl companyDao2 = new CompanyDaoImpl();
            companyDao2.getCompanyById(Integer.parseInt(scelta)).getId();
            String companyName = companyDao2.getCompanyById(Integer.parseInt(scelta)).getName();
            System.out.println("'"+companyName+"'"+" company");
            carMenu();
        }
    }
    public static void insertCar(Integer id,Integer idCompany, String carName, String nameCompany, String isRented) throws SQLException {

        CarDaoImpl carDaoImpl = new CarDaoImpl();
        Car car = new Car(id, carName, idCompany, nameCompany, isRented);
        carDaoImpl.addCar(car);
        carMenu();

    }

    public static void carMenu() throws SQLException {
        CompanyDaoImpl companyDao = new CompanyDaoImpl();
        CarDaoImpl carDao = new CarDaoImpl();
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
        String scelta = scanner.nextLine();

        if(scelta.equals("1")) {
            if(carDao.getAllCarsByCompanyId(Integer.parseInt(CompanyLogic.choiceCompany)).size() == 0) {
                System.out.println("The car list is empty!");
                carMenu();
            } else {
                List<Car> carList = carDao.getAllCarsByCompanyId(Integer.parseInt(CompanyLogic.choiceCompany));
                if(carList.size() == 0) {
                    String companyName = ""+ companyDao.getCompanyById(Integer.parseInt(CompanyLogic.choiceCompany)).getName() ;
                    System.out.println("No available cars in the '"+ companyName +"' company");
                } else {
                    carDao.getAllCars();
                    System.out.println("Car list:");
                    for (int i = 0; i < carList.size(); i++) {
                        System.out.println(i + 1 + ". " + carList.get(i).getName());
                        //System.out.println(carDao.getAllCarsByCompanyId(Integer.parseInt(sceltaCompagnia)).get(i).getId()+". "+carDao.getAllCarsByCompanyId(Integer.parseInt(sceltaCompagnia)).get(i).getName());
                        //System.out.println(sceltaCompagnia);
                    }
                    //carDao.getAllCars().forEach(System.out::println);
                    System.out.println();
                    carMenu();
                }
            }
        } else if(scelta.equals("2")){

            Scanner scanner = new Scanner(System.in);
            //CompanyDaoImpl companyDao1 = new CompanyDaoImpl();
            System.out.println("Enter the car name:");
            String carName = scanner.nextLine();
            System.out.println("The car was added!");
            idCar++;
            Integer companyId= Integer.parseInt(choiceCompany);
            //System.out.println("scelta compagnia: " + sceltaCompagnia);
            String companyName = companyDao.getCompanyById(Integer.parseInt(choiceCompany)).getName();
            //System.out.println("company name: "+companyName);
            //System.out.println("company id: "+companyId);
            insertCar(idCar, companyId,carName,companyName, "FALSE");
        } else CompanyLogic.managerMenu();
    }

}
