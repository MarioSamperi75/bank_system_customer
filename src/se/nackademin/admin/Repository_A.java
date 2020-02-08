package se.nackademin.admin;

import se.nackademin.model.*;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class Repository_A {

    private Properties p = new Properties();

    public Repository_A() throws SQLException {
        try {
            p.load(new FileInputStream("resources/settings.properties"));
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add_newCustomer(String firstnameInp, String lastnameInp, String personNrInp, String pinInp) {
        String query = "call add_newCustomer(?,?,?,?);";
        ResultSet result = null;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {

            stmt.setString(1, firstnameInp);
            stmt.setString(2, lastnameInp);
            stmt.setString(3, personNrInp);
            stmt.setString(4, pinInp);

            result = stmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error. Operation aborted.");
            System.exit(0);

        }
        System.out.println("A New Customer was added");
    }


    public Map<Integer, Customer> getAllCustomer() {
        Map<Integer, Customer> allCustomer = new HashMap<>();
        //List<Category> categoryList = new ArrayList<>();
        int mapCounter = 1;
        int checkShoesId = 0;
        String query = "SELECT customer.id, firstname, lastname, personalnumber, customer.pin FROM bankdb.person\n" +
                "join customer\n" +
                "where customer.personid = person.id;";
        ResultSet result = null;


        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {
            result = stmt.executeQuery();

            while (result.next()) {
                Customer customer = new Customer(result.getInt("customer.id"),
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getString("personalnumber"),
                        result.getString("customer.pin"));

                allCustomer.put(mapCounter, customer);
                mapCounter++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allCustomer;
    }

    public Customer getCustomer(String pesnumInp) {
        String query = "SELECT customer.id, person.firstname, person.lastname, person.personalnumber, customer.pin FROM bankdb.customer\n" +
                "inner join person\n" +
                "on person.id = customer.personid\n" +
                "where person.personalnumber = ?;";

        ResultSet result = null;
        Customer customerFound = null;
        int resultCounter = 0;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, pesnumInp);
            result = stmt.executeQuery();

            while (result.next()) {
                customerFound = new Customer(
                        result.getInt("customer.id"),
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getString("personalnumber"),
                        result.getString("customer.pin"));

                resultCounter++;

                // skapa objekt och returnera objekt, och sen get id
            }
            // det kan inte hända
            if (resultCounter == 0)
                System.out.println("Customer not found");

            if (resultCounter > 1) {
                System.out.println("Sorry, an error occurred. Contact the Administrator.");
                return null; //eller exit, men det kan inte hända
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerFound;
    }


    public Employee checkpassword(String personalNumberInp, String pinInp) {
        Employee employee = null;
        ResultSet result = null;
        int resultcounter = 0;
        String query = "SELECT employee.id, person.firstname, person.lastname, person.personalNumber, employee.pin\n" +
                "                        from person\n" +
                "                        inner join employee\n" +
                "                        on employee.personId = person.id\n" +
                "                        where person.personalNumber = ? \n" +
                "                        and employee.pin = ? ;";
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, personalNumberInp);
            stmt.setString(2, pinInp);
            result = stmt.executeQuery();

            while (result.next()) {
                employee = new Employee(result.getInt("employee.id"),
                        result.getString("person.firstname"),
                        result.getString("person.lastname"),
                        result.getString("person.personalNumber"),
                        result.getString("employee.pin"));
                resultcounter++;
            }
            if (resultcounter == 0) {
                System.out.println("Invalid lastname or password");
                System.out.println();
                return null;
            }
            if (resultcounter > 1) {
                System.out.println("Invalid lastname or password");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Undefined Error");
            return null;
        }
        System.out.println("Welcome, " + employee.getFirstname() + " " + employee.getLastname());
        return employee;
    }


    public void deleteCustomer(String personalNumberID) {
        String query = "DELETE person.* FROM person\n" +
                "inner join customer\n" +
                "on person.id = customer.personid\n" +
                "where personalnumber= ? ;";
        ResultSet result = null;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, personalNumberID);
            stmt.executeUpdate();
            System.out.println("Delete successfull");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateCustomerLastname(String newLastname, String personalNr){
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"));

             CallableStatement stmt = con.prepareCall("update person set person.lastname = ? where person.personalnumber = ?;")) {

            stmt.setString(1, newLastname);
            stmt.setString(2, personalNr);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCustomerFirstname(String newFirstname, String personalNr){
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"));

             CallableStatement stmt = con.prepareCall("update person set person.firstname = ? where person.personalnumber = ?;")) {

            stmt.setString(1, newFirstname);
            stmt.setString(2, personalNr);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCustomerPersonalNumber(String newPersonnumber, String personalNr){
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"));

             CallableStatement stmt = con.prepareCall("update person set person.personalnumber = ? where person.personalnumber = ?;")) {

            stmt.setString(1, newPersonnumber);
            stmt.setString(2, personalNr);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeCustomerPin(String newCustomerPin, int customerIdInp){
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"));

             CallableStatement stmt = con.prepareCall("update customer set customer.pin = ? where customer.id = ?;")) {

            stmt.setString(1, newCustomerPin);
            stmt.setInt(2, customerIdInp);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void createNewLoan(String loanNr, double capital, int interestRate, int customerId, int employeeId) {
        String query = "call new_loan(?,?,?,?,?);";

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {

            stmt.setString (1, loanNr);
            stmt.setDouble (2, capital);
            stmt.setInt (3, interestRate);
            stmt.setInt (5, customerId);
            stmt.setInt (4, employeeId);

            stmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "Error. Operation aborted.");
            System.exit(0);

        }
        System.out.println( "New loan created");
    }






    public void createAccount( String accountNrInp, double balanceInp,  double interestInp,  int customerIdInp ) {
        String query = "call create_new_account(?,?, ?, ?);";

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {

            stmt.setString (1, accountNrInp);
            stmt.setDouble (2, balanceInp);
            stmt.setDouble (3, interestInp);
            stmt.setInt (4, customerIdInp);

            stmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "Error. Operation aborted.");
            System.exit(0);

        }
        System.out.println( "New Account created");
    }

    public List<Account> getAllAccount(int customerIDInp) {
        List<Account> accountList = new ArrayList<>();

        String query = "SELECT * from account_view where customerID = ? group by accountID;";
        ResultSet result = null;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, customerIDInp);
            result = stmt.executeQuery();

            while (result.next()) {
                Account account = new Account(result.getInt("accountID"),
                        result.getString("accountNr"),
                        result.getDouble("balance"),
                        result.getDouble("intrest"));
                accountList.add(account);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return accountList;
    }



    public void deleteAccount(String accountNr, int customeridInp) throws SQLException {
        deleteManageAccount(accountNr);

        String query = "delete from account where customerid = ? and accountnr = ?;";
        ResultSet result = null;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, customeridInp);
            stmt.setString(2, accountNr);
            stmt.executeUpdate();
            System.out.println("Delete from account successfull");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteManageAccount(String accountNr) {
        String query = "delete from manageaccount where accountid = (select id from account where accountnr = ? );";
        ResultSet result = null;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, accountNr);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public int getAccountID(String accountNrInp) {
        String query = "SELECT account.id FROM bankdb.account\n" +
                "where accountNr = ? \n" +
                "limit 1;";
        ResultSet result = null;
        int accountIdFound = 0;
        int resultCounter = 0;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, accountNrInp);
            result = stmt.executeQuery();

            while (result.next()) {
                accountIdFound = result.getInt("account.id");
                resultCounter++;
            }

            if (resultCounter == 0)
                System.out.println("Account not found");

            if (resultCounter > 1) {
                System.out.println("Sorry, an error occurred. Contact the Administrator.");
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountIdFound;
    }


    public int getLoanID(String loanNrInp) {
        String query = "SELECT loan.id FROM bankdb.loan\n" +
                "where loanNr = ? \n" +
                "limit 1;";
        ResultSet result = null;
        int loanIdFound = 0;
        int resultCounter = 0;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, loanNrInp);
            result = stmt.executeQuery();

            while (result.next()) {
                loanIdFound = result.getInt("loan.id");
                resultCounter++;
            }

            if (resultCounter == 0)
                System.out.println("loan not found");

            if (resultCounter > 1) {
                System.out.println("Sorry, an error occurred. Contact the Administrator.");
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loanIdFound;
    }



    public void withdraw( int accountId, double withdrawal) {
        String query = "call withdraw(?,?);";
        ResultSet result = null;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {

            stmt.setInt(1, accountId);
            stmt.setDouble(2, withdrawal);

            result = stmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "Error. Operation aborted.");
            System.exit(0);

        }
        System.out.println( "Transaction Complete");
    }



    public void deposit( int accountId, double amount) {
        String query = "call deposit(?,?);";
        ResultSet result = null;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {

            stmt.setInt(1, accountId);
            stmt.setDouble(2, amount);

            result = stmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "Error. Operation aborted.");
            System.exit(0);

        }
        System.out.println( "Transaction Complete");
    }








    public List<Loan> getAllLoan(int customerIDInp) {
        List<Loan> loanList = new ArrayList<>();

        String query = "SELECT * from loan_view where customerID = ? group by loanID;";
        ResultSet result = null;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, customerIDInp);
            result = stmt.executeQuery();

            while (result.next()) {
                Loan loan = new Loan(result.getInt("loanID"),
                        result.getString("loanNr"),
                        result.getDouble("capital"),
                        result.getDouble("intrest"));
                loanList.add(loan);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return loanList;
    }


    public void changeAccountInterest(double newInterest,int accountIdInp){
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"));

             CallableStatement stmt = con.prepareCall("update account set account.intrest = ? where account.id = ?;")) {

            stmt.setDouble(1, newInterest);
            stmt.setInt(2, accountIdInp);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    public void changeLoanInterest(double newInterest,int loanIdInp){
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"));

             CallableStatement stmt = con.prepareCall("update loan set loan.intrest = ? where loan.id = ?;")) {

            stmt.setDouble(1, newInterest);
            stmt.setInt(2, loanIdInp);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setFinalPayment( int loanId) {
        String query = "call set_final_payement(?);";
        ResultSet result = null;
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {

            stmt.setInt(1, loanId);

            result = stmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "Error. Operation aborted.");
            System.exit(0);

        }

    }









    public PayPlan showPayPlan(String loanNrInp) {
        PayPlan payPlan = null;
        String query =  "SELECT loanid, loannr, capital, intrest, years, finalamount FROM bankdb.payplan " +
                        "where payplan.loannr = ?;";

        ResultSet result = null;
        int resultCounter = 0;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, loanNrInp);
            result = stmt.executeQuery();

            while (result.next()) {
                payPlan = new PayPlan(
                        result.getInt("payplan.loanid"),
                        result.getString("payplan.loannr"),
                        result.getDouble("payplan.capital"),
                        result.getDouble("payplan.intrest"),
                        result.getInt("payplan.years"),
                        result.getDouble("payplan.finalAmount")
                );

                resultCounter++;
            }
            // det kan inte hända
            if (resultCounter == 0)
                System.out.println("Customer not found");

            if (resultCounter > 1) {
                System.out.println("Sorry, an error occurred. Contact the Administrator.");
                return null; //eller exit, men det kan inte hända
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payPlan;
    }

    /*public void getFinalAmount(int loanIdInp){
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("call set_final_payement (?)")) {

            stmt.setInt(1,loanIdInp );
            stmt.executeQuery();


    } catch (SQLException e) {
            e.printStackTrace();
        }

    }*/






        public void updatePlan(int newyears, String loannr){
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"));

             CallableStatement stmt = con.prepareCall(
                     "update payplan set payplan.years = ? where loannr = ?;")) {

            stmt.setInt(1, newyears);
            stmt.setString(2, loannr);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("The payment program is uppdated");
    }

   /* public void changeLoanInterest(double newInterest,int loanIdInp){
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"));

             CallableStatement stmt = con.prepareCall("update loan set loan.intrest = ? where loan.id = ?;")) {

            stmt.setDouble(1, newInterest);
            stmt.setInt(2, loanIdInp);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    */



}
