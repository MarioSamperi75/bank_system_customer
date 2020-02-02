package se.nackademin;

import java.sql.SQLException;
import java.util.Scanner;

public class View {
    Scanner scanner = new Scanner(System.in);
    Controller controller = new Controller();

    public View() throws SQLException {
    }

    //METHODS
    public void start() {
        System.out.println();
        System.out.println("                    *****Bank System Customer*****");
        System.out.println();
        controller.login();
    }



    public void menu (){
        while (true) {
            System.out.println();
            System.out.println("Overview(1) - Withdraw(2) - History(3) - Exit(4)");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    controller.showAllAccount();
                    controller.showAllLoan();
                    break;
                case "2":
                    System.out.print("Add amount you want to withdraw: ");
                    String input2 = scanner.nextLine().trim();
                    Double withdrawal = Double.parseDouble(input2);

                    System.out.print("Add account Nr: ");
                    String accountNr = scanner.nextLine().trim();

                    controller.selectAndWitdraw(withdrawal, accountNr);
                    break;
               case "3":
                   System.out.println("Vill du få VG :)?");
                    break;

                case "4":
                    controller.exit();
                    break;
                default:
                    System.out.println("Input error. Retry");
            }
       }
    }




    public static void main(String[] args) throws SQLException {
        View view = new View();
        view.start();

        view.menu();
    }
}



//quando si scrive "" nel modello


//visa flera av samma