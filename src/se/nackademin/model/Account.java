package se.nackademin.model;

public class Account {

    int id;
    String accountNr;
    double balance;
    double intrest;

    // ---------------------------------------------------------------------

    public Account() {}

    public Account(int id, String accountNr, double balance, double intrest) {
        this.id = id;
        this.accountNr = accountNr;
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

    public String getAccountNr() {
        return accountNr;
    }

    public void setAccountNr(String accountNr) {
        this.accountNr = accountNr;
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
        System.out.println(getId() + " Account Nr: " + getAccountNr() + "\tBalance: " + getBalance() + "KR  \tIntrest: " + getIntrest()+"%");
    }
}
