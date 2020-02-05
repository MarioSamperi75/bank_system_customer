package se.nackademin.admin;

import se.nackademin.model.*;

import java.io.FileInputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

    public void add_newCustomer( String firstnameInp, String lastnameInp, String personNrInp, String pinInp) {
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
            System.out.println( "Error. Operation aborted.");
            System.exit(0);

        }
        System.out.println( "A New Customer was added");
    }


    public Map<Integer, Customer> getAllCustomer() {
        Map<Integer, Customer> allCustomer = new HashMap<>();
        //List<Category> categoryList = new ArrayList<>();
        int mapCounter = 1;
        int checkShoesId = 0;
        String query =  "SELECT customer.id, firstname, lastname, personalnumber FROM bankdb.person\n" +
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
                                                 result.getString("personalnumber"));

                allCustomer.put(mapCounter, customer);
                mapCounter++;

            } }catch (Exception e) {
            e.printStackTrace();
        }
        return allCustomer;
    }

    public Customer getCustomer(String pesnumInp) {
        String query =  "SELECT customer.id, person.firstname, person.lastname, person.personalnumber FROM bankdb.customer\n" +
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
                        result.getString("personalnumber"));

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
        String query =  "SELECT employee.id, person.firstname, person.lastname, person.personalNumber, employee.pin\n" +
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
        String query =  "DELETE person.* FROM person\n" +
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

/*
//hej

    public Map<Integer, Account> getAllAccount(int customerIDInp) {
        Map<Integer, Account> allAccount = new HashMap<>();
        //List<Category> categoryList = new ArrayList<>();
        int mapCounter = 1;
        int checkShoesId = 0;
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
*//*                if (checkShoesId == shoesFound.getInt("IDshoes")) {
                    Category category = new Category(shoesFound.getInt("categoryID"), shoesFound.getString("Category"));
                    categoryList.add (category);*//*
                    Account account = new Account(result.getInt("accountID"),
                                                  result.getString("accountNr"),
                                                  result.getDouble("balance"),
                                                  result.getDouble("intrest"));
                    allAccount.put(mapCounter, account);
                    mapCounter++;
               *//* }
                else {
                    categoryList = new ArrayList<>();
                    Brand brand = new Brand(shoesFound.getInt("brandID"), shoesFound.getString("Brand"));
                    Color color = new Color(shoesFound.getInt("colorID"), shoesFound.getString("Color"));
                    Account. category = new Category(shoesFound.getInt("categoryID"), shoesFound.getString("Category"));
                    categoryList.add(category);
                    Shoes shoes = new Shoes(shoesFound.getInt("IDshoes"), shoesFound.getString("model"),
                            shoesFound.getString("size"), shoesFound.getInt("price"),
                            shoesFound.getInt("storage"));
                    shoes.setBrand(brand);
                    shoes.setColor(color);
                    shoes.setCategoryList(categoryList);
                    allshoes.put(mapCounter, shoes);

                    checkShoesId = shoesFound.getInt("IDshoes");
                    mapCounter++;

                }
            }*//*
        } }catch (Exception e) {
            e.printStackTrace();
        }
        return allAccount;
    }


    public Map<Integer, Loan> getAllLoan(int customerIDInp) {
        Map<Integer, Loan> allLoan = new HashMap<>();
        //List<Category> categoryList = new ArrayList<>();
        int mapCounter = 1;
        int checkShoesId = 0;
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
                allLoan.put(mapCounter, loan);
                mapCounter++;
               *//* }
                else {
                    categoryList = new ArrayList<>();
                    Brand brand = new Brand(shoesFound.getInt("brandID"), shoesFound.getString("Brand"));
                    Color color = new Color(shoesFound.getInt("colorID"), shoesFound.getString("Color"));
                    Account. category = new Category(shoesFound.getInt("categoryID"), shoesFound.getString("Category"));
                    categoryList.add(category);
                    Shoes shoes = new Shoes(shoesFound.getInt("IDshoes"), shoesFound.getString("model"),
                            shoesFound.getString("size"), shoesFound.getInt("price"),
                            shoesFound.getInt("storage"));
                    shoes.setBrand(brand);
                    shoes.setColor(color);
                    shoes.setCategoryList(categoryList);
                    allshoes.put(mapCounter, shoes);

                    checkShoesId = shoesFound.getInt("IDshoes");
                    mapCounter++;

                }
            }*//*
            } }catch (Exception e) {
            e.printStackTrace();
        }
        return allLoan;
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

            result = stmt.executeQuery(); //che fai con sta variabile

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "Error. Operation aborted.");
            System.exit(0);

        }
        System.out.println( "Transaction Complete");
    }

*//*

    public int getLastOrdersID(int customerIdInp) {
        String query =  "SELECT ordereditem.ordersID FROM ordereditem\n" +
                        "inner join orders\n" +
                        "on orders.id = ordereditem.ordersid\n" +
                        "and orders.customerId = ?\n" +
                        "order by ordereditem.id desc\n" +
                        "limit 1;";

        ResultSet result = null;
        int customerIdFound = 0;
        int resultCounter = 0;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, customerIdInp);
            result = stmt.executeQuery();

            while (result.next()) {
                customerIdFound = result.getInt("orderedItem.ordersID");
                resultCounter++;
            }
            // det kan inte hända
            if (resultCounter == 0)
                System.out.println("Product not found");

            if (resultCounter > 1) {
                System.out.println("Sorry, an error occurred. Contact the Administrator.");
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerIdFound;
    }




    public List<Shoes> getProductsInTheCart(int customerIdInp, int orderIdInp) {
        List<Shoes> shoesList = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();
        int checkOrderedItemId = 0;
        String query =  "SELECT * from all_the_orderedItems" +
                        " where IDcustomer = ? and IDorder = ?;";
        ResultSet shoesFound = null;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, customerIdInp);
            stmt.setInt(2, orderIdInp);
            shoesFound = stmt.executeQuery();

            while (shoesFound.next()) {
                if (checkOrderedItemId == shoesFound.getInt("IDorderedItem") *//*
*//*|| checkShoesId == shoesFound.getInt("IDShoes")*//**//*
) {
                    Category category = new Category(shoesFound.getInt("categoryID"), shoesFound.getString("Category"));
                    categoryList.add (category);
                }
                else {
                    categoryList = new ArrayList<>();
                    Brand brand = new Brand(shoesFound.getInt("brandID"), shoesFound.getString("Brand"));
                    Color color = new Color(shoesFound.getInt("colorID"), shoesFound.getString("Color"));
                    Category category = new Category(shoesFound.getInt("categoryID"), shoesFound.getString("Category"));
                    categoryList.add(category);
                    Shoes shoes = new Shoes(shoesFound.getInt("IDshoes"), shoesFound.getString("model"),
                                            shoesFound.getString("size"), shoesFound.getInt("price"),
                                            shoesFound.getInt("storage"));
                    shoes.setBrand(brand);
                    shoes.setColor(color);
                    shoes.setCategoryList(categoryList);
                    shoesList.add(shoes);

                    checkOrderedItemId = shoesFound.getInt("IDorderedItem");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shoesList;
    }
*//*
*/
}



