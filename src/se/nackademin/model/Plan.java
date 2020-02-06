package se.nackademin.model;

public class Plan {
    int id;
    int years;
    double finalAmount;

    public Plan(int id, int years, double aDouble) {
        this.id = id;
        this.years = years;
        this.finalAmount = getFinalAmount();
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
        System.out.println("Years: " + getYears() +  "  Final amount: " + getFinalAmount());
    }
}
