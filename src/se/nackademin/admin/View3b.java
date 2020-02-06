

package se.nackademin.admin;

import se.nackademin.model.Customer;
import se.nackademin.model.Employee;

import java.sql.SQLException;
import java.util.Scanner;

public class View3b {
    Scanner scanner = new Scanner(System.in);
    Controller_A controllerA = new Controller_A();
    Customer customer = new Customer();
    Employee employee = new Employee();

    public View3b(Customer customer, Employee employee) throws SQLException {
        this.customer = customer;
        this.employee = employee;
        start();
    }

    //METHODS
    public void start() throws SQLException {
        System.out.println();
        System.out.println("                                    ***** Loan *****");
        System.out.println("                ***** Employee: "+ employee.getFirstname() + " " + employee.getLastname()+
                                            "  Customer: " + customer.getFirstname() + " " + customer.getLastname()+ " *****");
        System.out.println();
        menu();
    }



    public void menu () throws SQLException {
        while (true) {
            System.out.println();
            System.out.println("Change intrest(1) - Show Payment Program(2) - Change Payment Program(3) -  Main(4)");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    controllerA.changeLoanInterest(customer);
                    break;
                case "2":
                    controllerA.showAllLoans(customer);
                    System.out.print("which loan do you want to examinate?");
                    String loannr = scanner.nextLine().trim();
                    controllerA.calculateShowPaymentPlan(loannr);
                break;
                case "3":
                   controllerA.updatePlan(customer);
                    break;


                case "4":
                    View1 v1 = new View1();
                    break;

                default:
                    System.out.println("Input error. Retry");
            }
       }
    }

}






//visa flera av samma