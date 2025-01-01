package javalibrary;
import java.io.*;
import java.util.*;
public class Supplier implements Serializable
{   
    private static final long serialVersionUID = 1L;
    private int suppid;
    private String namesupp;
    private int  phoneno;
    private int ordercount;
    private double revenue;
    static Scanner input = new Scanner(System.in);  
    static HashMap<Integer, Supplier> suppmap = new HashMap<>();
    public Supplier(int suppid, String namesupp, int phoneno, int ordercount, double revenue) 
    {
        this.suppid = suppid;
        this.namesupp = namesupp;
        this.phoneno = phoneno;
        this.ordercount = ordercount;
        this.revenue = revenue;
    }
    public int getSuppid() 
    {
        return suppid;
    }
    public void setSuppid(int suppid) 
    {
        this.suppid = suppid;
    }
    public String getNamesupp() 
    {
        return namesupp;
    }
    public void setNamesupp(String namesupp) 
    {
        this.namesupp = namesupp;
    }
    public int getPhoneno() 
    {
        return phoneno;
    }
    public void setPhoneno(int phoneno) 
    {
        this.phoneno = phoneno;
    }
    public int getOrdercount() 
    {
        return ordercount;
    }
    public void setOrdercount(int ordercount) 
    {
        this.ordercount = ordercount;
    }
    public double getRevenue() 
    {
        return revenue;
    }
    public void setRevenue(double revenue) 
    {
        this.revenue = revenue;
    }
    public static void savefile() 
    {
        try (ObjectOutputStream writing = new ObjectOutputStream(new FileOutputStream("supplier.bin"))) 
        {
            writing.writeObject(suppmap);
            writing.close();
            System.out.println("Suppliers data saved to file successfully.");
        } 
        catch (IOException e) 
        {
            System.out.println("Error while saving suppliers: " + e.getMessage());
        }
    }
    public static void readfile()
    {
        try (ObjectInputStream loading = new ObjectInputStream(new FileInputStream("supplier.bin"))) 
        {
            suppmap = (HashMap<Integer, Supplier>) loading.readObject();
            System.out.println("Suppliers loaded from file successfully.");
            loading.close();
        } 
        catch (IOException | ClassNotFoundException e)
        {
            System.err.println("Error while loading suppliers: " + e.getMessage());
        }
    }
    public static void displaySuppliers()
    {
        readfile(); 
        if (suppmap.isEmpty())
        {
            System.out.println("No suppliers available."); 
        } 
        else
        {
        System.out.println  ("Supplier List:");
            for (HashMap.Entry<Integer, Supplier> entry : suppmap.entrySet())
            {
                Supplier supp = entry.getValue();
                System.out.println("ID: " + supp.getSuppid() + " Name: " + supp.getNamesupp()
                        + " Phone: " + supp.getPhoneno() + " Orders: " + supp.getOrdercount()
                        + " Revenue: " + supp.getRevenue());
            }
        }
    }
    public static boolean add(int id, String name, int phone) {
    readfile();
    if (suppmap.containsKey(id)) {
        return false; // Supplier with this ID already exists
    }

    Supplier supplier = new Supplier(id, name, phone, 0, 0);
    suppmap.put(id, supplier);
    savefile();
    return true;
}
    public static void editSupplier(int supplierId, String newName, String newPhone, String newOrderCount, String newRevenue) {
    readfile();

    if (!suppmap.containsKey(supplierId)) {
        System.out.println("No data found for ID " + supplierId + ".");
        return;
    }

    Supplier supplier = suppmap.get(supplierId);
    System.out.println("Editing supplier: " + supplier.getNamesupp());

    // Update name if provided
    if (newName != null && !newName.isEmpty()) {
        supplier.setNamesupp(newName);
    }

    // Update phone number if provided
    if (newPhone != null && !newPhone.isEmpty()) {
        try {
            int phone = Integer.parseInt(newPhone);
            supplier.setPhoneno(phone);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid phone number format. Update skipped.");
        }
    }

    // Update order count if provided
    if (newOrderCount != null && !newOrderCount.isEmpty()) {
        try {
            int orderCount = Integer.parseInt(newOrderCount);
            supplier.setOrdercount(orderCount);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid order count format. Update skipped.");
        }
    }

    // Update revenue if provided
    if (newRevenue != null && !newRevenue.isEmpty()) {
        try {
            double revenue = Double.parseDouble(newRevenue);
            supplier.setRevenue(revenue);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid revenue format. Update skipped.");
        }
    }

    suppmap.put(supplierId, supplier); // Save updated supplier back to the map
    savefile();
    System.out.println("Supplier data updated successfully.");
}
    public static void deleteSupplier(int supplierId) {
    readfile();

    if (suppmap.containsKey(supplierId)) {
        suppmap.remove(supplierId);
        savefile();
        System.out.println("Supplier with ID " + supplierId + " has been deleted.");
    } else {
        System.out.println("No Supplier found with ID " + supplierId + ".");
    }
}
    public static void searchSupplier(String searchTerm) {
    boolean found = false;

    // Iterate through supplier map
    for (Supplier supplier : suppmap.values()) {
        if (String.valueOf(supplier.getSuppid()).equals(searchTerm) || 
            supplier.getNamesupp().equalsIgnoreCase(searchTerm) || 
            String.valueOf(supplier.getPhoneno()).equals(searchTerm)) {
            System.out.println("Supplier found:");
            System.out.println("ID: " + supplier.getSuppid());
            System.out.println("Name: " + supplier.getNamesupp());
            System.out.println("Phone: " + supplier.getPhoneno());
            System.out.println("Orders: " + supplier.getOrdercount());
            System.out.println("Revenue: " + supplier.getRevenue());
            found = true;
            break;
        }
    }

    if (!found) {
        System.out.println("No supplier found with the given search term.");
    }
}
    public static void MaxOrderCountSupplier() {
    readfile(); 

    if (suppmap.isEmpty()) {
        return; 
    }

    Supplier maxOrderSupplier = null;

    for (Supplier supplier : suppmap.values()) {
        if (maxOrderSupplier == null || supplier.getOrdercount() > maxOrderSupplier.getOrdercount()) {
            maxOrderSupplier = supplier;
        }
    }
    }
    public static void MaxRevenueSupplier() {
    readfile(); 

    if (suppmap.isEmpty()) {
        return; 
    }

    Supplier maxRevenueSupplier = null;

    for (Supplier supplier : suppmap.values()) {
        if (maxRevenueSupplier == null || supplier.getRevenue() > maxRevenueSupplier.getRevenue()) {
            maxRevenueSupplier = supplier;
        }
    }
} 
    
}