package javalibrary;
import java.io.*;
import java.util.*;
public class Order implements Serializable {
    private int orderId;
    private int supplierId;
    private int supplierContact;
    private String orderDate;
    private boolean status;  
    private double totalAmount;
    static HashMap<Integer, Order> orderMap = new HashMap<>();
    private static Scanner input = new Scanner(System.in);
    public Order(int orderId, int supplierId, int supplierContact, String orderDate, boolean status, double totalAmount) {
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.supplierContact = supplierContact;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
    public int getSupplierContact() {
        return supplierContact;
    }
    public void setSupplierContact(int supplierContact) {
        this.supplierContact = supplierContact;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public static void savefile() {
        try (ObjectOutputStream writing = new ObjectOutputStream(new FileOutputStream("order.bin"))) {
            writing.writeObject(orderMap);
            System.out.println("Order data saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error while saving Order: " + e.getMessage());
        }
    }
    public static void readfile() {
        try (ObjectInputStream loading = new ObjectInputStream(new FileInputStream("order.bin"))) {
            orderMap = (HashMap<Integer, Order>) loading.readObject();
            System.out.println("Orders loaded from file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while loading Order: " + e.getMessage());
        }
    }
    public static void createOrder() 
    {
        
        readfile();
        Supplier.readfile();
        System.out.println("Enter Supplier ID:");
        int supplierId = input.nextInt();
        if (!Supplier.suppmap.containsKey(supplierId)) 
        {
        System.out.println("Supplier ID " + supplierId + " not Found");
        return;
        }
        System.out.println("Enter Supplier Contact Number:");
        int supplierContact = input.nextInt();

        System.out.println("Enter Order ID:");
        int orderId = input.nextInt();

        // Check if the order ID already exists
        if (orderMap.containsKey(orderId)) {
            System.out.println("Order ID " + orderId + " already exists.");
            return;
        }
        System.out.println("Enter Order Date: ");
        String orderDateString = input.nextLine();
        System.out.println("Enter Total Amount:");
        double totalAmount = input.nextFloat();
        Order newOrder = new Order(orderId, supplierId, supplierContact, orderDateString, false, totalAmount);
        orderMap.put(orderId, newOrder);
        Order order = Order.orderMap.get(orderId);
        double amountorder = order.getTotalAmount();
        Supplier supplier = Supplier.suppmap.get(supplierId);
        supplier.setOrdercount(supplier.getOrdercount()+1);
        supplier.setRevenue(supplier.getRevenue()+amountorder);
        Supplier.savefile();
        savefile();
        System.out.println("Order created successfully.");
    }
    public static void updateOrder(int orderId) {
        readfile();
        Supplier.readfile();
        // Check if the order exists
        if (!orderMap.containsKey(orderId)) {
            System.out.println("Order ID " + orderId + " not found.");
            return;
        }
        System.out.println("Enter Supplier ID to update: ");
        int supplierId = input.nextInt();
        
        if (!Supplier.suppmap.containsKey(supplierId)) 
        {
        System.out.println("Supplier ID " + supplierId + " not Found");
        return;
        }
        Order order = orderMap.get(orderId);

        System.out.println("Enter new Supplier Contact Number:");
        int supplierContact = input.nextInt();
        order.setSupplierContact(supplierContact);

        System.out.println("Enter new Order Date: ");
        String orderDateString = input.nextLine(); 
        order.setOrderDate(orderDateString);

        System.out.println("Enter new Total Amount:");
        double totalAmount = input.nextFloat();
        order.setTotalAmount(totalAmount);
        Supplier supplier = Supplier.suppmap.get(supplierId);
        supplier.setRevenue(supplier.getRevenue() + totalAmount);
        Supplier.savefile();
        savefile();
        System.out.println("Order updated successfully.");
    }
    public static void cancelOrder(int orderId) {
        readfile();

        if (!orderMap.containsKey(orderId)) {
            System.out.println("Order ID " + orderId + " not found.");
            return;
        }

        Order order = orderMap.get(orderId);

        order.setStatus(true);

        savefile();
        System.out.println("Order ID " + orderId + " has been canceled successfully.");
    }
    public static void viewOrderDetails(int orderId) {
        readfile();
        
        if (!orderMap.containsKey(orderId)) {
            System.out.println("Order ID " + orderId + " not found.");
            return;
        }

        Order order = orderMap.get(orderId);

        System.out.println("Order Details:");
        System.out.println("Supplier ID: " + order.getSupplierId());
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Supplier ID: " + order.getSupplierId());
        System.out.println("Supplier Contact: " + order.getSupplierContact());
        System.out.println("Order Date: " + order.getOrderDate());
        System.out.println("Total Amount: " + order.getTotalAmount());
    }
    public static void Supplierhistory(int SupplierId) 
    {
    readfile();  
    boolean found = false;
    System.out.println("Supplier History for Supplier ID: " + SupplierId);
    for (Order order : orderMap.values()) {
        if (order.getSupplierId() == SupplierId) {
            found = true;
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Order Date: " + order.getOrderDate());
            System.out.println("Supplier Contact: " + order.getSupplierContact());
            System.out.println("Total Amount: " + order.getTotalAmount());
            System.out.println("Order Status: " + (order.getStatus() ? "Canceled" : "Active"));
        }
    }
    if (!found) {
        System.out.println("No Supplier history found for Supplier ID: " + SupplierId);
    }
}
    
}
