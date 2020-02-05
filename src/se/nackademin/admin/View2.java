

package se.nackademin.admin;

import se.nackademin.model.Customer;
import se.nackademin.model.Employee;

import java.sql.SQLException;
import java.util.Scanner;

public class View2 {
    Scanner scanner = new Scanner(System.in);
    Controller_A controllerA = new Controller_A();
    Customer customer;
    Employee employee;


    public View2(Customer customer, Employee employee) throws SQLException {
        this.customer = customer;
        this.employee = employee;
        Controller_A controllerA = new Controller_A(customer,employee);
        start();
    }

    /*public View2() throws SQLException {
        start();
    }*/

    //METHODS
    public void start() throws SQLException {
        System.out.println();
        System.out.println("                    ***** Employee: " + employee.getLastname() +
                           "             Customer: "+ customer.getFirstname() + " " + customer.getLastname() + " *****");
        System.out.println();
        menu();
    }



    public void menu () throws SQLException {
        while (true) {
            System.out.println();
            System.out.println("Delete Customer(1) - Data Update(2) - New account(3) - Account List(4) -  New Loan(5) - Loan List(6) \n" +
                               "        Select Menu Account (7)     -     Select Menu Loan (8)     -     Main(9)");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    controllerA.deleteCustomer(customer);
                    View1 v1 = new View1();
                    break;
                case "2":

                    break;
               case "3":
                   controllerA.newAccount(customer);
                    break;

                case "4":
                    System.out.println(customer.getId() + customer.getFirstname());
                    break;

                case "5":

                    break;

                case "6":

                    break;

                case "7":
                    View3a v3a = new View3a();
                    break;

                case "8":
                    View3b v3b= new View3b();
                    break;

                case "9":
                    v1 = new View1();
                    break;

                default:
                    System.out.println("Input error. Retry");
            }
       }
    }



}



//quando si scrive "" nel modello


//visa flera av samma