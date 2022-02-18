package carsharing.Entity;


public class Car {
    Integer id;

    String name;

    Integer company_id;

    String company;

    String isRented;

    public Car(Integer id, String name, Integer company_id, String company, String is_rented) {
        this.id = id;
        this.name = name;
        this.company_id = company_id;
        this.company = company;
        this.isRented = is_rented;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public String getCompany() {
        return company;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setRented(String rented) {
        isRented = rented;
    }

    public String isRented() {
        return isRented;
    }
}
