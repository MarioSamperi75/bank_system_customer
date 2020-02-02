package se.nackademin.model;

public class Loan {

    int id;
    double capital;
    double intrest;

    // ---------------------------------------------------------------------


    public Loan() {}

    public Loan(int id, double capital, double intrest) {
        this.id = id;
        this.capital = capital;
        this.intrest = intrest;
    }

    // ---------------------------------------------------------------------


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public double getIntrest() {
        return intrest;
    }

    public void setIntrest(double intrest) {
        this.intrest = intrest;
    }

    public void print() {
        System.out.println(" Capital: " + getCapital() + "KR  Intrest: " + getIntrest()+"%");
    }
}
