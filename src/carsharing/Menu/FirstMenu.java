package carsharing.Menu;

import carsharing.Logic.CustomerLogic;
import carsharing.Logic.CompanyLogic;

import java.sql.SQLException;
import java.util.Scanner;

public class FirstMenu {

    static Scanner scanner = new Scanner(System.in);

    public static void logInOrExit() throws SQLException {

        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
        String scelta = "";
        scelta = scanner.nextLine();

            switch (scelta) {
                case "0":

                    break;
                case "1":
                    CompanyLogic.managerMenu();
                    break;
                case "2":

                    CustomerLogic.printAllCustomers();

                    break;
                case "3":
                    CustomerLogic.createCustomer();
                    break;
            }
    }


}
