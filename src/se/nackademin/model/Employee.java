package se.nackademin.model;

public class Employee extends Person{
    int id;
    String pin;


    // ---------------------------------------------------------------------


    public Employee() {}

    public Employee(int id) {
        this.id = id;
    }

    public Employee(int id, String firstname, String lastname, String personalNumber, String pin) {
        super(firstname, lastname, personalNumber);
        this.id = id;
        this.pin = pin;

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
