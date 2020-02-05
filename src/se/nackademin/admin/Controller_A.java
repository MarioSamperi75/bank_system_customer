package se.nackademin.admin;

import se.nackademin.model.Customer;
import se.nackademin.model.Employee;

import java.sql.SQLException;
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
            System.out.println("Insert lastname: ");
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

        public void deleteCustomer(Customer customer, Employee employee) {
            rep.deleteCustomer(customer.getPersonalNumber());
    }


/*
        public void showAllProductsInYourCart() {
            System.out.println("Your cart: ");
            rep.getProductsInTheCart(customer.getId(), orderId).forEach(c -> c.print());
            System.out.println();
        }


        public void newOrder() {
            orderId = 0;
            System.out.println("New order - Your cart is empty.");
        }


    public void exit() {
        System.out.println("See you soon!");
        System.exit(0);
    }

*/

}
