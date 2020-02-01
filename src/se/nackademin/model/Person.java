package se.nackademin.model;

public class Person {

    int id;
    private String firstname;
    private String lastname;
    private String personalNumber;

    // ---------------------------------------------------------------------


    public Person() {}

    public Person(int id, String firstname, String lastname, String personalNumber) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.personalNumber = personalNumber;
    }

    // ---------------------------------------------------------------------


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }
}
