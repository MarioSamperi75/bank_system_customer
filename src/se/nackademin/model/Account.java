package se.nackademin.model;

public class Account {

    int id;
    double balance;
    double intrest;

    // ---------------------------------------------------------------------

    public Account() {}

    public Account(int id, double balance, double intrest) {
        this.id = id;
        this.balance = balance;
        this.intrest = intrest;
    }

    // ---------------------------------------------------------------------


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getIntrest() {
        return intrest;
    }

    public void setIntrest(double intrest) {
        this.intrest = intrest;
    }

    public void print() {
        System.out.println(" Balance: " + getBalance() + "KR  Intrest: " + getIntrest()+"%");
    }
}
