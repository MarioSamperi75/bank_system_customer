

package se.nackademin.admin;

import java.sql.SQLException;
import java.util.Scanner;

public class View3a {
    Scanner scanner = new Scanner(System.in);
    Controller_A controllerA = new Controller_A();

    public View3a() throws SQLException {
        start();
    }

    //METHODS
    public void start() throws SQLException {
        System.out.println();
        System.out.println("                    *****CustomerXXX, AccountXXX *****");
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
                    /*controllerA.showAllAccount();
                    controllerA.showAllLoan();*/
                    break;
                case "2":
                   /* System.out.print("Add amount you want to withdraw: ");
                    String input2 = scanner.nextLine().trim();
                    Double withdrawal = Double.parseDouble(input2);

                    System.out.print("Add account Nr: ");
                    String accountNr = scanner.nextLine().trim();

                    controllerA.selectAndWitdraw(withdrawal, accountNr);*/
                    break;
               case "3":

                    break;

                case "4":

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