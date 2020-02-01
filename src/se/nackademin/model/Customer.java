package se.nackademin.model;

import java.util.LinkedList;
import java.util.List;

public class Customer extends Person {
    int id;
    String pin;
    List<Account> accountlist = new LinkedList<>();
    List<Loan> loanList = new LinkedList<>();


    // ---------------------------------------------------------------------

    public Customer() {}

    public Customer(String pin) {
        this.pin = pin;
    }

    public Customer(int id, String firstname, String lastname, String personalNumber, String pin) {
        super(id, firstname, lastname, personalNumber);
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public List<Account> getAccountlist() {
        return accountlist;
    }

    public void setAccountlist(List<Account> accountlist) {
        this.accountlist = accountlist;
    }

    public List<Loan> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<Loan> loanList) {
        this.loanList = loanList;
    }
}
