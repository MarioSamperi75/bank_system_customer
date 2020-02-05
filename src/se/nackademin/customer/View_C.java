

package se.nackademin.customer;

import java.sql.SQLException;
import java.util.Scanner;

public class View_C {
    Scanner scanner = new Scanner(System.in);
    Controller_C controllerC = new Controller_C();

    public View_C() throws SQLException {
    }

    //METHODS
    public void start() {
        System.out.println();
        System.out.println("                    *****Bank System Customer*****");
        System.out.println();
        controllerC.login();
    }



    public void menu (){
        while (true) {
            System.out.println();
            System.out.println("Overview(1) - Withdraw(2) - History(3) - Exit(4)");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    controllerC.showAllAccount();
                    controllerC.showAllLoan();
                    break;
                case "2":
                    System.out.print("Add amount you want to withdraw: ");
                    String input2 = scanner.nextLine().trim();
                    Double withdrawal = Double.parseDouble(input2);

                    System.out.print("Add account Nr: ");
                    String accountNr = scanner.nextLine().trim();

                    controllerC.selectAndWitdraw(withdrawal, accountNr);
                    break;
               case "3":
                   System.out.println("Vill du få VG :)?");
                    break;

                case "4":
                    controllerC.exit();
                    break;
                default:
                    System.out.println("Input error. Retry");
            }
       }
    }




    public static void main(String[] args) throws SQLException {
        View_C viewC = new View_C();
        viewC.start();

        viewC.menu();
    }
}



//quando si scrive "" nel modello


//visa flera av samma