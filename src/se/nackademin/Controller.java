package se.nackademin;

import se.nackademin.model.Customer;

import java.sql.SQLException;
import java.util.Scanner;

public class Controller {

    Repository rep = new Repository();
    Customer customer;

    int customerId = 0;
    int orderId = 0;

    public Controller() throws SQLException {}

        public void login() {
            String personalNumber;
            String pin;
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("personalNumber(AAMMDD-XXXX): ");
                personalNumber = sc.nextLine().trim();
                System.out.println("pin: ");
                pin = sc.nextLine().trim();
                customer = rep.checkpassword(personalNumber, pin);
                if (customer != null)
                    break;
            }
        }


        public void showAllAccount() {
            System.out.println("Your accounts: ");
            rep.getAllAccount(customer.getId()).forEach((k, v) -> v.print());
            System.out.println();
        }

/*
        public void addItemInYourCart(String model, String color, String size ) {
            shoesId = rep.getOneProductID(model, color, size);
            if (shoesId!=0) {
                rep.addToCart(customer.getId(), orderId, shoesId);
                orderId = (rep.getLastOrdersID(customer.getId()));
            }
        }


        public void showAllProductsInYourCart() {
            System.out.println("Your cart: ");
            rep.getProductsInTheCart(customer.getId(), orderId).forEach(c -> c.print());
            System.out.println();
        }


        public void newOrder() {
            orderId = 0;
            System.out.println("New order - Your cart is empty.");
        }

*/
    public void exit() {
        System.out.println("See you soon!");
        System.exit(0);
    }


}
