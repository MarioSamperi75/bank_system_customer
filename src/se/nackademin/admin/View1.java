

package se.nackademin.admin;

import se.nackademin.model.Customer;

import java.sql.SQLException;
import java.util.Scanner;

public class View1 {
    Scanner scanner = new Scanner(System.in);
    Controller_A controllerA = new Controller_A();

    public View1() throws SQLException {
        start();
    }

    //METHODS
    public void start() throws SQLException {
        System.out.println();
        System.out.println("                    *****Bank System Admin*****");
        System.out.println();
        controllerA.login();
        menu();
    }



    public void menu () throws SQLException {
        while (true) {
            System.out.println();
            System.out.println("New Customer(1) - Customer List(2) - Select Customer(3) - Exit(4)");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    controllerA.addNewCustomer();
                    break;
                case "2":
                    controllerA.showAllCustomer();
                 /*   System.out.print("Add amount you want to withdraw: ");
                    String input2 = scanner.nextLine().trim();
                    Double withdrawal = Double.parseDouble(input2);

                    System.out.print("Add account Nr: ");
                    String accountNr = scanner.nextLine().trim();

                    controllerA.selectAndWitdraw(withdrawal, accountNr);*/
                    break;
               case "3":
                   Customer customer = controllerA.selectCustomer();
                   View2 v2 = new View2(customer);
                   break;

                case "4":
                    System.out.println("See you soon!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Input error. Retry");
            }
       }
    }




    public static void main(String[] args) throws SQLException {
        View1 view1 = new View1();
        view1.start();

        view1.menu();
    }
}



//quando si scrive "" nel modello


//visa flera av samma