

package se.nackademin.admin;

import java.sql.SQLException;
import java.util.Scanner;

public class View3b {
    Scanner scanner = new Scanner(System.in);
    Controller_A controllerA = new Controller_A();

    public View3b() throws SQLException {
        start();
    }

    //METHODS
    public void start() throws SQLException {
        System.out.println();
        System.out.println("                    *****CustomerXXX, LoanXXX *****");
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
                 /*   controllerA.showAllAccount();
                    controllerA.showAllLoan();*/
                    break;
                case "2":
                 /*   System.out.print("Add amount you want to withdraw: ");
                    String input2 = scanner.nextLine().trim();
                    Double withdrawal = Double.parseDouble(input2);

                    System.out.print("Add account Nr: ");
                    String accountNr = scanner.nextLine().trim();

                    controllerA.selectAndWitdraw(withdrawal, accountNr);
                    break;*/
               case "3":

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