package se.nackademin.model;

public class Employee extends Person{
    int id;


    // ---------------------------------------------------------------------


    public Employee() {}

    public Employee(int id) {
        this.id = id;
    }

    public Employee(int id, String firstname, String lastname, String personalNumber, int id1) {
        super(id, firstname, lastname, personalNumber);
        this.id = id1;
    }

    // ---------------------------------------------------------------------


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
