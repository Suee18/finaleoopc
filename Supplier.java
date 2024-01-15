package pack1;

import java.util.ArrayList;
import java.io.*;

public class Supplier {
    // Attributes
    public String supplierName;
    public String supplierUserType;

    public static ArrayList<Supplier> SupplierDataList = new ArrayList<>();
    public ArrayList<Products> supplierProducts = new ArrayList<>();

    public Supplier() {
    }

    public Supplier(String supplierName) {
        this.supplierName = supplierName;
        this.supplierUserType = "Supplier";
    }

    public void addProductToSupplierList(String pName, int price) {
        Products p = new Products(pName, price);
        supplierProducts.add(p);
    }

    public void removeProductFromSupplierList(String pName) {
        for (Products a : supplierProducts) {
            if (a.productName.equalsIgnoreCase(pName)) {
                supplierProducts.remove(a);
                return;
            }
        }
    }

}
