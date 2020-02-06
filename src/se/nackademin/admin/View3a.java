

package se.nackademin.admin;

import se.nackademin.model.Customer;
import se.nackademin.model.Employee;

import java.sql.SQLException;
import java.util.Scanner;

public class View3a {
    Scanner scanner = new Scanner(System.in);
    Controller_A controllerA = new Controller_A();
    Customer customer= new Customer();
    Employee employee = new Employee();

    public View3a(Customer customer, Employee employee) throws SQLException {
        this.customer = customer;
        this.employee= employee;
        start();
    }

    //METHODS
    public void start() throws SQLException {
        System.out.println();
        System.out.println("                                   ***** Account *****");
        System.out.println("                ***** Employee: "+ employee.getFirstname() + " " + employee.getLastname()+
                                    "  Customer: " + customer.getFirstname() + " " + customer.getLastname()+ " *****");
        System.out.println();
        menu();

    }



    public void menu () throws SQLException {
        while (true) {
            System.out.println();
            System.out.println("Delete Account(1) - Deposit(2) - Withdraw(3) - Change Intrest(4) - Main(5)");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    controllerA.deleteAccount(customer);
                    break;
                case "2":
                    System.out.print("Add amount you want to deposit: ");
                    String input2 = scanner.nextLine().trim();
                    Double amount = Double.parseDouble(input2);

                    System.out.print("Add account Nr: ");
                    String accountNr = scanner.nextLine().trim();

                    controllerA.selectAndDeposit(amount, accountNr);
                    break;
               case "3":
                   System.out.print("Add amount you want to withdraw: ");
                   input2 = scanner.nextLine().trim();
                   Double withdrawal = Double.parseDouble(input2);

                   System.out.print("Add account Nr: ");
                   accountNr = scanner.nextLine().trim();

                   controllerA.selectAndWitdraw(withdrawal, accountNr);
                   break;

                case "4":
                    controllerA.changeAccountInterest(customer);
                    break;

                case "5":
                    View1 v1 = new View1();
                    break;

                default:
                    System.out.println("Input error. Retry");
            }
       }
    }

}



//quando si scrive "" nel modello


//visa flera av samma