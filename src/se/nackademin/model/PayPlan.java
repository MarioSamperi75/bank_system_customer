package se.nackademin.model;

public class PayPlan extends Loan{
    int id;
    int years;
    double finalAmount;

    public PayPlan(int id, String loanNr, double capital, double intrest, int years, double finalAmount) {
        super(id, loanNr, capital, intrest);
        this.years = years;
        this.finalAmount = finalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public void print() {
        System.out.println("Loannr: " + getLoanNr() +" Capital: " + getCapital() +" intrest: " + getIntrest() +  "Years: " + getYears() +"  Final amount: " + getFinalAmount());
    }
}
