package se.nackademin.model;

public class Loan {

    int id;
    String loanNr;
    double capital;
    double intrest;

    // ---------------------------------------------------------------------


    public Loan() {}

    public Loan(int id, String loanNr, double capital, double intrest) {
        this.id = id;
        this.loanNr = loanNr;
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

    public String getLoanNr() {
        return loanNr;
    }

    public void setLoanNr(String loanNr) {
        this.loanNr = loanNr;
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
        System.out.println("Loan Nr:    " + getLoanNr() + "\tCapital: " + getCapital() + "KR  \tIntrest: " + getIntrest()+"%");
    }
}
