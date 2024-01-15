package pack1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User implements Serializable {
    public String adminUserName;
    public String adminUserType;
    private String adminPassword;
    private String adminConfirmPass;
    public ArrayList<Admin> adminDataList = new ArrayList<>();
    public String adminFileName = "adminData.dat";
    public String productsFileName = "productsData.dat";

    Admin() {
    }

    public Admin(String adminUserName, String adminUserType, String adminPassword) {
        this.adminUserName = adminUserName;
        this.adminUserType = adminUserType;
        this.adminPassword = adminPassword;
        this.setAdminConfirmPass(adminPassword);
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminConfirmPass() {
        return adminConfirmPass;
    }

    public void setAdminConfirmPass(String confirmPass) {
        this.adminConfirmPass = confirmPass;
    }

    public void ViewMaxCartCashier() {
        System.out.println("cashier with maximum number of carts");
        System.out.println(Cashier.maxnumofcarts());
    }

    public int tester = 0;

    // MOE
    // Methods for managing products
    public void addProducts(String pname) {
        Products a = new Products(pname, 500);
        Products.productsDataList.add(a);// adding the new product obj to the list
        System.out.println("product added succesfully");
        System.out.println(Products.productsDataList.size());
        tester = Products.counter;
        System.out.println(Products.productsDataList.get(tester - 1));
    }

    public void removeProducts(String pname, int id) {
        for (Products a : Products.productsDataList) {
            if (a.productName.equals(pname) || a.PID.equals(id)) {
                System.out.println("product name:" + a.productName);
                System.out.println("product id:" + a.PID);
                Products.productsDataList.remove(a);
                System.out.println("product removed succesfully");
                tester -= 1;
                return;
            }
        }
    }

    // return product details
    public void searchProducts() {
        Scanner input12 = new Scanner(System.in);
        System.out.println("1)search by name    2)search by id");
        short num = input12.nextShort();
        input12.close();
        if (num == 1) {
            Scanner pnamein = new Scanner(System.in);
            System.out.println("enter the product name");
            String pname = pnamein.next();
            pnamein.close();
            for (Products a : Products.productsDataList) {
                if (a.productName.equals(pname)) {
                    System.out.println("product name:" + a.productName);
                    System.out.println("product id:" + a.PID);
                    return;
                }
            }
        } else if (num == 2) {
            Scanner pidin = new Scanner(System.in);
            System.out.println("enter the product id");
            String pid = pidin.next();
            pidin.close();
            for (Products a : Products.productsDataList) {
                if (a.PID.equals(pid)) {
                    System.out.println("product name:" + a.productName);
                    System.out.println("product id:" + a.PID);
                    return;
                }
            }

        }

        System.out.println("cant find the product");
    }

    void addUser(String Usernamein, String Usertypein, String Passwordin, String ConfirmPassword) {
        if (Usertypein.equalsIgnoreCase("cashier")) {
            Cashier a = new Cashier(Usernamein, Passwordin, 0);
            Cashier.cashierDataList.add(a);
            System.out.println(Cashier.cashierDataList.size());

        } else if (Usertypein.equalsIgnoreCase("customer")) {
            Customer a = new Customer(Usernamein, Passwordin);
            Customer.customerDataList.add(a);
            System.out.println(Customer.customerDataList.size());
        }
    }

    public boolean editUser(String currentusername, String newusername, String usertype, String newpassword) {

        if (usertype.equalsIgnoreCase("cashier")) {
            for (Cashier a : Cashier.cashierDataList) {
                if (currentusername.equals(a.cashierUserName)) {
                    a.cashierUserName = newusername;
                    a.setCashierPassword(newpassword);
                    a.setCashierPassword(newpassword);
                    return true;
                }
            }
        } else if (usertype.equalsIgnoreCase("customer")) {
            for (Customer a : Customer.customerDataList) {
                if (currentusername.equals(a.customerUserName)) {
                    a.customerUserName = newusername;
                    a.setCustomerPassword(newpassword);
                    a.setCustomerConfirmPass(newpassword);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeUser(String removeUsername, String removeUserType) {

        if (removeUserType.equalsIgnoreCase("cashier")) {
            for (Cashier a : Cashier.cashierDataList) {
                if (a.cashierUserName.equals(removeUsername)) {
                    Cashier.cashierDataList.remove(a);
                    return true;
                }
            }
        } else if (removeUserType.equalsIgnoreCase("customer")) {
            for (Customer a : Customer.customerDataList) {
                if (a.customerUserName.equals(removeUsername)) {
                    Customer.customerDataList.remove(a);
                    return true;
                }
            }
        }

        return false; // Return false if user not found
    }

    public boolean searchUser(String searchUsername, String searchUserType) {

        if (searchUserType.equalsIgnoreCase("cashier")) {
            for (Cashier a : Cashier.cashierDataList) {
                if (a.cashierUserName.equals(searchUsername))
                    System.out.println("username: " + a.cashierUserName + "\n" + "user type: " + "cashier");
                return true;
            }
        } else if (searchUserType.equalsIgnoreCase("customer")) {
            for (Customer a : Customer.customerDataList) {
                if (a.customerUserName.equals(searchUsername))
                    System.out.println("username: " + a.customerUserName + "\n" + "user type: " + "Customer");
                return true;
            }
        }
        System.out.println("user not found");
        return false;
    }

    // Checks in the array llst of admin object sfor the credientials in the
    // parameters
    @Override
    public boolean checkUserData(String userName, String password) {
        for (Admin admin : adminDataList) {
            if (admin.adminUserName.equals(userName) && admin.adminUserType.equals("Admin")
                    && admin.getAdminPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void createFile() {
        try {
            File userDataFile = new File(adminFileName);
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
        File file = new File(adminFileName);
        if (!file.exists()) {
            System.out.println("File doesn't exist. Creating a new one.");
            createFile();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object readObject = ois.readObject();

            if (readObject instanceof ArrayList<?>) {
                adminDataList = (ArrayList<Admin>) readObject;
                System.out.println("Admin data read from file into arraylist successfully.");
            } else {
                System.out.println("Invalid data format in the file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e);
        }
    }

    @Override
    public void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(adminFileName))) {
            oos.writeObject(adminDataList);
            System.out.println("Admin data written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    // user name repetiotion in the same file for SIGN UP
    public boolean checkUserName(String AuserName) {
        for (Admin admin1 : adminDataList) {
            if (admin1.adminUserName.equals(AuserName))
                return true;
        }
        return false;
    }

    @Override
    public void signUp(String un, String p) {
        Admin currentAdmin = new Admin(un, "Admin", p);
        adminDataList.add(currentAdmin);
    }

    // GUI integration
    public void launchAdminGUI() {
        AdminGUI.launch(AdminGUI.class);
    }

}