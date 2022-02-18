package carsharing.Entity;

public class Customer {

    private Integer id;

    private String name;

    private Integer rentedCarId;

    public Customer(Integer id, String name, Integer rentedCarId) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rentedCarId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRentedCarId() {
        return rentedCarId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRentedCarId(Integer rentedCarId) {
        this.rentedCarId = rentedCarId;
    }


}
