package carsharing.Menu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connession {
    static final String DATABASEFILEPATH = "./src/carsharing/db/carsharing";
    static final String DB_URL = "jdbc:h2:";
    private static final String JDBC_DRIVER = "org.h2.Driver";

    static Connection conn;
    static Statement stmt = null;

    public static Connection createConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL + DATABASEFILEPATH);
            stmt = conn.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Impossible to create connection");
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }

    public static  void letsGo() throws SQLException {

        dropTbl("CUSTOMER");
        dropTbl("CAR");
        dropTbl("COMPANY");
        createOrUpdateTables();
        alterTablesIndexRestart();

        FirstMenu.logInOrExit();
    }

    public static void createOrUpdateTables() throws SQLException{

        updateTableCompany();
        updateTableCar();
        updateTableCustomer();

    }

    public static void dropTbl(String tableName) throws SQLException {
        Connession.createConnection();
        stmt = conn.createStatement();
        String createTblOrUpdateCompany = "DROP TABLE IF EXISTS " + tableName;
        stmt.executeUpdate(createTblOrUpdateCompany);
        Connession.closeConnection();
    }

    public static void alterTablesIndexRestart() throws SQLException{
        alterTblCompanyRestartIndex();
        alterTblCarRestartIndex();
        alterTblCustomerRestartIndex();
    }

    public static void alterTblCompanyRestartIndex() throws SQLException {
        Connession.createConnection();
        stmt = conn.createStatement();
        String alterTblCompany="ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1";
        stmt.executeUpdate(alterTblCompany);
        Connession.closeConnection();
    }

    public static void alterTblCarRestartIndex() throws SQLException {
        Connession.createConnection();
        stmt = conn.createStatement();
        String alterTblCar="ALTER TABLE CAR ALTER COLUMN ID RESTART WITH 1";
        stmt.executeUpdate(alterTblCar);
        Connession.closeConnection();
    }

    public static void alterTblCustomerRestartIndex() throws SQLException {
        Connession.createConnection();
        stmt = conn.createStatement();
        String alterTblCustomer="ALTER TABLE CUSTOMER ALTER COLUMN ID RESTART WITH 1";
        stmt.executeUpdate(alterTblCustomer);
        Connession.closeConnection();
    }

    public static void updateTableCompany() throws SQLException {

        Connession.createConnection();
        stmt = conn.createStatement();
        String createTblOrUpdateCompany = "" +
                "CREATE TABLE IF NOT EXISTS COMPANY"
                + "(" + "ID INT AUTO_INCREMENT, " +
                "NAME VARCHAR(50) UNIQUE NOT NULL,"
                + "PRIMARY KEY (ID))";
        stmt.executeUpdate(createTblOrUpdateCompany);
        Connession.closeConnection();
    }

    public static void updateTableCustomer() throws SQLException {

        Connession.createConnection();
        stmt = conn.createStatement();
        String createTblOrUpdateCustomer = "" +
                "CREATE TABLE IF NOT EXISTS CUSTOMER" + "(" + "ID INT AUTO_INCREMENT, "
                + "NAME VARCHAR(50) UNIQUE NOT NULL,"+"RENTED_CAR_ID INT,"
                +"FOREIGN KEY(RENTED_CAR_ID) REFERENCES CAR(ID),"
                + "PRIMARY KEY (ID))";
        stmt.executeUpdate(createTblOrUpdateCustomer);
        Connession.closeConnection();
    }
    public static void customerRentCar(String name, Integer idCar) throws SQLException {
        Connession.createConnection();
        stmt = conn.createStatement();
        String updateTableCustomer = "UPDATE CUSTOMER ";
        String setColumn ="SET RENTED_CAR_ID = ";
        String setToIdCar = "" + idCar;

        String whereCondition = " WHERE NAME = ";
        String nameCustomer = "'" + name + "' ";

        String query = updateTableCustomer + setColumn + setToIdCar + whereCondition + nameCustomer;

        stmt.executeUpdate(query);
        Connession.closeConnection();
    }

    public static void updateTableCar() throws SQLException {
        Connession.createConnection();
        stmt = conn.createStatement();
        String createTblCar = "" +
                "CREATE TABLE IF NOT EXISTS CAR" + "(" + "ID INT AUTO_INCREMENT, "
                + "NAME VARCHAR(50) UNIQUE NOT NULL,"
                +"COMPANY_ID INT NOT NULL,"+"COMPANY VARCHAR(255), "+
                "IS_RENTED BOOLEAN DEFAULT FALSE, "
                +"FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(ID),"
                +"FOREIGN KEY(COMPANY)REFERENCES COMPANY(NAME),"
                + "PRIMARY KEY (ID))";
        stmt.executeUpdate(createTblCar);
        Connession.closeConnection();
    }
}
