package se.nackademin.admin;
import se.nackademin.model.Customer;
import se.nackademin.model.Employee;
import java.sql.SQLException;
import java.util.Scanner;

public class ViewCustomerDataUpdate {
    Scanner scanner = new Scanner(System.in);
    Controller_A controllerA;
    Customer customer;
    Employee employee;


    public ViewCustomerDataUpdate(Customer customer, Employee employee) throws SQLException {
        this.customer = customer;
        this.employee = employee;
        controllerA = new Controller_A(customer,employee);
        start();
    }


    public void start() {
        System.out.println();
        System.out.println("                    ***** Employee: " + employee.getLastname() +
                "             Customer: "+ customer.getFirstname() + " " + customer.getLastname() + " *****");
        System.out.println();
        menu();
    }

    public void menu (){
        while (true) {
            System.out.println();
            System.out.println("Update last name (1) - Update first name(2) - Update personalnumber(3) - Update password(4) - back to submenu(5)");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    controllerA.updateCustomerLastname();
                    break;
                case "2":
                    controllerA.updateCustomerFirstname();
                    break;
                case "3":
                    controllerA.updateCustomerPersonalNumber();
                    break;
                case "4":
                    controllerA.changeCustomerPin();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Input error. Retry");
            }
        }
    }



}

