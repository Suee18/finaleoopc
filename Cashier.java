package pack1;

import java.util.ArrayList;
import java.io.*;

public class Cashier extends User implements Serializable {

    public int numofcart;
    String cashierUserName;
    String cashierPassword;
    private static final long serialVersionUID = 1L; // Ensure consistent serialization
    String cashierFileName = "cashiersData.dat";
    String CartFileName = "CartData.dat";

    public ArrayList<Products> cartProducts = new ArrayList<>(); // retrieve from Class product

    public static ArrayList<Cashier> cashierDataList = new ArrayList<>();
    String cashierConfirmPass;

    // Constructors
    public Cashier() {
    };

    public Cashier(String u, String p, int num) {
        cashierUserName = u;
        cashierPassword = p;
        numofcart = num;
    };

    // Getters and setters
    public String getCashierUserName() {
        return cashierUserName;
    }

    public String getCashierPassword() {
        return cashierPassword;
    }

    public void setCashierUserName(String u) {
        this.cashierUserName = u;
    }

    public void setCashierPassword(String p) {
        this.cashierPassword = p;
    }

    @Override
    public void createFile() {
        try {
            File userDataFile = new File(cashierFileName);
            if (userDataFile.createNewFile()) {
                System.out.println("File created successfully!");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("File is not created" + e.getMessage());
        }
    }

    @Override
    public void readFromFile() {
        File file = new File(cashierFileName);
        if (!file.exists()) {
            System.out.println("File doesn't exist. Creating a new one.");
            createFile();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object readObject = ois.readObject();

            if (readObject instanceof ArrayList<?>) {
                cashierDataList = (ArrayList<Cashier>) readObject;
                System.out.println("Cashier data read from file into arraylist successfully.");
            } else {
                System.out.println("Invalid data format in the file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e);
        }
    }

    @Override
    public void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cashierFileName))) {
            oos.writeObject(cashierDataList);
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // LOGGING IN AND SIGNING UP
    @Override
    public void signUp(String un, String p) {
        Cashier currentCashier = new Cashier(un, p, 0); // Set the userName and cashierPassword
        cashierDataList.add(currentCashier);
    }

    @Override
    public boolean checkUserData(String un, String p) {
        for (Cashier c : cashierDataList) {
            if (c.getCashierUserName().equals(un)
                    && c.getCashierPassword().equals(p)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkUserName(String un) {
        for (Cashier c : cashierDataList) {
            if (c.getCashierUserName().equals(un))
                return true;
        }
        return false;
    }

    static public Cashier maxnumofcarts() {

        if (Cashier.cashierDataList.isEmpty()) {
            System.out.println("no cashiers available");
            return null;

        }
        int maximum = 0;
        int index = 0;
        for (int i = 0; i < cashierDataList.size(); i++) {
            if (cashierDataList.get(i).numofcart > maximum) {
                maximum = cashierDataList.get(i).numofcart;
                index = i;
            }
        }
        return cashierDataList.get(index);
    }

    @Override
    public String toString() {
        return "Name: " + this.cashierUserName + "\n" + " number of carts: " + this.numofcart;
    }

    public void createCart() {
        try {
            File CartDataFile = new File(CartFileName);
            if (CartDataFile.createNewFile()) {
                System.out.println("File created succefully!");

            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("File is not created" + e.getMessage());
        }

    }

    public void addProductToCart(String p) {

        for (Products product : Products.productsDataList) {
            if (product.productName.equals(p)) {
                cartProducts.add(product);
                System.out.println("product added successfully");
                return;
            }
        }
        System.out.println("Product not Found");
        return;
    }

    public void writeArrayListtoFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CartFileName))) {
            oos.writeObject(cashierUserName);
            oos.writeObject(cartProducts);
            System.out.println("Cart Products Written to file successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readfromList() {
        File f = new File(CartFileName);
        if (!f.exists()) {
            System.out.println("File doesn't exist. Creating a new one");
            createFile();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object readObject = ois.readObject();

            if (readObject instanceof ArrayList<?>) {
                ArrayList<Products> CartFileProduct = (ArrayList<Products>) readObject;
                System.out.println("Cart data read from file into arraylist successfully");
            } else {
                System.out.println("Invalid data format in the file");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e);
        }
    }

    public void removeProductFromCart(String pname) {
        for (Products product : Products.productsDataList) {
            if (product.productName.equals(pname)) {
                cartProducts.remove(product);
                System.out.println("product deleted successfully");
                return;
            }
        }
        System.out.println("Product not Found");
    }

    public void deleteCart() {
        if (cartProducts == null) {
            System.out.println("Cart already empty");
            cartProducts = new ArrayList<>();
        } else {
            cartProducts.clear();
            System.out.println("Cart Cleared Successfully");
        }
    }

    public void displayCarts() {

        if (cartProducts == null) {
            cartProducts = new ArrayList<>();
        }
        if (cartProducts.isEmpty()) {
            System.out.println("The cart is empty.");
        } else {

            System.out.println("Products in the cart:");
            for (Products p : cartProducts) {
                System.out.println("- " + p.productName + "-" + p.price);
            }
        }
    }

    public double calculatePayment() {
        double totalPayment = 0.0;
        for (Products product : cartProducts) {
            totalPayment += product.price;
        }
        // System.out.println("Total Payment=" + totalPayment);
        return totalPayment;
    }

}
