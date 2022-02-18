package carsharing.Entity;


public class Company {

    private Integer id;
    private String name;

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Company() {

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

