package se.nackademin;

import se.nackademin.model.*;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class Repository {

    private Properties p = new Properties();

    public Repository() throws SQLException {
        try {
            p.load(new FileInputStream("resources/settings.properties"));
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Customer checkpassword(String personalNumberInp, String pinInp) {
        Customer customer = new Customer();
        ResultSet result = null;
        int resultcounter = 0;
        String query =  "SELECT customer.id, person.firstname, person.lastname, person.personalNumber, customer.pin\n" +
                        "from person\n" +
                        "inner join customer\n" +
                        "on customer.personId = person.id\n" +
                        "where person.personalNumber = ? \n" +
                        "and customer.pin = ?;";
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, personalNumberInp);
            stmt.setString(2, pinInp);
            result = stmt.executeQuery();

            while (result.next()) {
                customer = new Customer(result.getInt("customer.id"),
                        result.getString("person.firstname"),
                        result.getString("person.lastname"),
                        result.getString("person.personalNumber"),
                        result.getString("customer.pin"));
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
        System.out.println("Welcome, " + customer.getFirstname() + " " + customer.getLastname());
        return customer;
    }

/*

    public Map<Integer, Shoes> getAllProducts() {
        Map<Integer, Shoes> allshoes = new HashMap<>();
        List<Category> categoryList = new ArrayList<>();
        int mapCounter = 1;
        int checkShoesId = 0;
        String query = "SELECT * from all_the_shoes";

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement stmt = con.createStatement();
             ResultSet shoesFound = stmt.executeQuery(query)) {

            while (shoesFound.next()) {
                if (checkShoesId == shoesFound.getInt("IDshoes")) {
                    Category category = new Category(shoesFound.getInt("categoryID"), shoesFound.getString("Category"));
                    categoryList.add (category);
                    mapCounter++;
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
                    allshoes.put(mapCounter, shoes);

                    checkShoesId = shoesFound.getInt("IDshoes");
                    mapCounter++;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allshoes;
    }


    public int getOneProductID(String shoesModelInp, String shoesColorInp, String shoesSizeInp) {
        String query = "SELECT * FROM shoesdb.all_the_shoes \n" +
                        "where model = ?\n" +
                        "and color = ?\n" +
                        "and size = ?\n" +
                        "limit 1;";
        ResultSet result = null;
        int shoesIdFound = 0;
        int resultCounter = 0;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, shoesModelInp);
            stmt.setString(2, shoesColorInp);
            stmt.setString(3, shoesSizeInp);
            result = stmt.executeQuery();

            while (result.next()) {
                shoesIdFound = result.getInt("all_the_shoes.IDshoes");
                resultCounter++;
            }

            if (resultCounter == 0)
                System.out.println("Product not found");

            if (resultCounter > 1) {
                System.out.println("Sorry, an error occurred. Contact the Administrator.");
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shoesIdFound;
    }


    public void addToCart(int customerId, int ordersId, int shoesId) {
        String query = "call addToCart(?,?,?);";
        ResultSet result = null;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, ordersId);
            stmt.setInt(3, shoesId);
            result = stmt.executeQuery(); //che fai con sta variabile

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "Error. Operation aborted.");

        }
        System.out.println( "The product was added to the cart");
    }



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
                if (checkOrderedItemId == shoesFound.getInt("IDorderedItem") */
/*|| checkShoesId == shoesFound.getInt("IDShoes")*//*
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
*/

}



