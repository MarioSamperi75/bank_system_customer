package se.nackademin.admin;

import se.nackademin.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Controller_A {

    Repository_A rep = new Repository_A();
    Customer customer=null;
    Employee employee=null;



    public Controller_A() throws SQLException {}

    public Controller_A(Customer customer, Employee employee) throws SQLException {
        this.customer = customer;
        this.employee = employee;
    }

    public Employee login() {
            String personalNumber;
            String pin;
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("personalNumber(AAMMDD-XXXX): ");
                personalNumber = sc.nextLine().trim();
                System.out.println("pin: ");
                pin = sc.nextLine().trim();
                this.employee = rep.checkpassword(personalNumber, pin);
                if (employee != null)
                    break;
            }
            return this.employee;
        }


        public void addNewCustomer() {
            Scanner sc = new Scanner(System.in);
            System.out.println("Insert firstname: ");
            String firstname = sc.nextLine().trim();
            System.out.println("Insert lastname: ");
            String lastname = sc.nextLine().trim();
            System.out.println("Insert personal number: ");
            String personalnumber = sc.nextLine().trim();
            System.out.println("Insert pin: "); //check om det finns
            String pin = sc.nextLine().trim();
            rep.add_newCustomer(firstname, lastname, personalnumber,pin);
        }


        public void showAllCustomer() {
            System.out.println("all customers: ");
            rep.getAllCustomer().forEach((k, v) -> v.print());
            System.out.println();
        }


        public Customer selectCustomer() {
            Scanner sc = new Scanner(System.in);
            System.out.println("Insert customer personal Number: ");
            String pernumInp = sc.nextLine().trim();
            this.customer = rep.getCustomer(pernumInp);
            return this.customer;
            }

        public void deleteCustomer(Customer customer) {
        rep.deleteCustomer(customer.getPersonalNumber());
    }


    public void updateCustomerLastname(){
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the new lastname? ");
        String newLastname = sc.nextLine().trim();
        rep.updateCustomerLastname(newLastname,customer.getPersonalNumber());
    }

    public void updateCustomerFirstname(){
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the new firstname? ");
        String newfirstname = sc.nextLine().trim();
        rep.updateCustomerFirstname(newfirstname,customer.getPersonalNumber());
    }

    public void updateCustomerPersonalNumber(){
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the new personalNumber? ");
        String newPersonalNumber = sc.nextLine().trim();
        rep.updateCustomerPersonalNumber(newPersonalNumber,customer.getPersonalNumber());

    }

    public void changeCustomerPin(){
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the new password? ");
        String newPassword = sc.nextLine().trim();
        rep.changeCustomerPin(newPassword,customer.getId());

    }

    public void deleteAccount(Customer customer) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert account number: ");
        String accountNr = sc.nextLine().trim();
        rep.deleteAccount(accountNr,customer.getId());
    }


    public void selectAndWitdraw(Double withdrawal, String accountNr ) {
        int accountId = rep.getAccountID(accountNr);
        if (accountId!=0) {
            rep.withdraw(accountId, withdrawal);
        }
    }

    public void selectAndDeposit(Double amount, String accountNr ) {
        int accountId = rep.getAccountID(accountNr);
        if (accountId!=0) {
            rep.deposit(accountId, amount);
        }
    }



    public void showAllAccount(Customer customer) {
        List<Account> accountList = rep.getAllAccount(customer.getId());
        accountList.stream().forEach(a -> a.print());
    }



    public void showAllLoans(Customer customer) {
        List<Loan> loanList = rep.getAllLoan(customer.getId());
        loanList.stream().forEach(loan -> loan.print());
    }

    public void changeAccountInterest(Customer customer) {
        showAllAccount(customer);
        Scanner sc = new Scanner(System.in);
        System.out.println("Which Account do you want to choose? ");
        int accountId = sc.nextInt();
        System.out.println("What is the new insterest now? ");
        double newInterest = sc.nextDouble();
        rep.changeAccountInterest(newInterest, accountId);
    }


    public void newLoan(Customer customer, Employee employee) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert a loanNumber");
        String loanNr = sc.nextLine().trim();
        System.out.println("Insert a capital");
        double capital = sc.nextDouble();
        System.out.println("Insert a interest rate");
        int interestRate = sc.nextInt();
        rep.createNewLoan(loanNr, capital, interestRate, customer.getId(), employee.getId());

    }




    public void changeLoanInterest(Customer customer) {
        showAllLoans(customer);
        Scanner sc = new Scanner(System.in);
        System.out.println("Which loan do you want to choose? ");
        int loanId = sc.nextInt();
        System.out.println("What is the new insterest now? ");
        double newInterest = sc.nextDouble();
        rep.changeLoanInterest(newInterest,loanId);

    }

        public Customer newAccount(Customer customer) {

            Double interestInp=null;
            Double balance=null;

            Scanner sc = new Scanner(System.in);
            System.out.println("Insert a name for the account: ");
            String accountNrInp = sc.nextLine().trim();

            System.out.println("Insert a intrest rate: ");
            String input = sc.nextLine().trim();
            try {
                interestInp = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            System.out.println("Insert a initial deposit: ");
            input = sc.nextLine().trim();
            try {
                balance= Double.parseDouble(input);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            int customerIdInp = customer.getId();

            rep.createAccount(accountNrInp, balance,  interestInp,  customerIdInp );
            return this.customer;
        }


    public void calculateShowPaymentPlan(String loanNr) {
        int loanId = rep.getLoanID(loanNr);
        rep.setFinalPayment(loanId);
        PayPlan payplan= rep.showPayPlan(loanNr);
        payplan.print();


    }

    public void updatePlan(Customer customer){
        showAllLoans(customer);
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the new lenght? (years) ");
        String input = sc.nextLine().trim();
        int newyears= Integer.parseInt(input);
        System.out.println("What is the Loannr? ");
        String loannr = sc.nextLine().trim();
        rep.updatePlan(newyears,loannr);

    }

}
