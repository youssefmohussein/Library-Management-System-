package javalibrary;
import java.io.*;
import java.util.*;
public class Admin implements User ,Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String email;
    private String password;
    static Scanner input = new Scanner(System.in);
    static HashMap<Integer, Admin> adminmap = new HashMap<>();
    public Admin(int id, String name, String email, String password)
{
      this.id = id;
      this.name = name;
      this.email = email;
      this.password = password;
}
@Override
public int getId() {
        return id;
    }
@Override
public String getName() {
        return name;
    }
@Override
public String getEmail() {
        return email;
    }
@Override
public String getPassword() {
        return password;
    }
@Override
public void setId(int id) {
        this.id = id;
    }
@Override
public void setName(String name) {
        this.name = name;
    }
@Override
public void setEmail(String email) {
        this.email = email;
    }
@Override
public void setPassword(String password) {
        this.password = password;
    }


public static boolean addlibrarian(int id, String name, String email, String password, int borrowcount, float revenue)
 {
  return Librarian.addLibrarian( id,  name,  email,  password,  borrowcount,  revenue);
 }
public void editlibrarian(int id, String name, String email, String password, int borrowcount, float revenue)
 {
     Librarian.editLibrarian(id,  name,  email,  password,  borrowcount,  revenue);
 }
public void searchlibrarian(String search)
 {
     Librarian.searchLibrarian(search);
 }
public void deletelibrarian(int id)      
 {
     Librarian.deleteLibrarian(id);
 }


public  static boolean addborrower(int id, String name, String email, String password, int borroweringcounter, float totalrevenue)
{
  return  Borrower.addborrower( id,  name,  email,  password,  borroweringcounter,  totalrevenue);
}
public void editborrower(int id, String name, String email, String password, int borroweringcounter, float totalrevenue)
{
    Borrower.editborrower(id,  name,  email,  password,  borroweringcounter,  totalrevenue);
}
public void searchborrower( String search)
{
    Borrower.searchBorrower(search);
}
public void deleteborrower(int id)
{
    Borrower.deleteborrower(id);
}


public static void savefile() 
{
        try (ObjectOutputStream writing = new ObjectOutputStream(new FileOutputStream("admin.bin"))) 
        {
            writing.writeObject(adminmap);
            writing.close();
            System.out.println("Admin data saved to file successfully.");
        } 
        catch (IOException e) 
        {
            System.out.println("Error while saving Admin: " + e.getMessage());
        }
    } 
public static void readfile()
{
        try (ObjectInputStream loading = new ObjectInputStream(new FileInputStream("admin.bin"))) 
        {
            adminmap = (HashMap<Integer, Admin>) loading.readObject();
            System.out.println("Admin loaded from file successfully.");
            loading.close();
        } 
        catch (IOException | ClassNotFoundException e)
        {
            System.err.println("Error while loading Admin: " + e.getMessage());
        }
    }
public static boolean addAdmin(int id, String name, String email, String password) 
{
        readfile();
        if (adminmap.containsKey(id)) 
        {
            return false; // ID already exists
        }
        Admin admin = new Admin(id, name, email, password);
        adminmap.put(id, admin);
        savefile();
        return true;
    
  }
public static void editAdmin(int adminId, String newName, String newEmail, String newPassword) 
{
    if (!adminmap.containsKey(adminId)) {
        System.out.println("No data found for ID " + adminId + ".");
        return;
    }

    Admin admin = adminmap.get(adminId);
    System.out.println("Editing admin: " + admin.getName());

    // Update name if provided
    if (newName != null && !newName.isEmpty()) {
        admin.setName(newName);
    }

    // Update email if provided
    if (newEmail != null && !newEmail.isEmpty()) {
        admin.setEmail(newEmail);
    }

    // Update password if provided
    if (newPassword != null && !newPassword.isEmpty()) {
        admin.setPassword(newPassword);
    }

    adminmap.put(adminId, admin); // Save updated admin back to the map
    savefile();
    System.out.println("Admin data updated successfully.");
}
public static void deleteadmin(int adminid)    
{
        if (adminmap.containsKey(adminid)) 
        {
            adminmap.remove(adminid);
            savefile();
            System.out.println("Admin with ID " + adminid + " has been deleted.");
        } else {
            System.out.println("No Admin found with ID " + adminid + ".");
        }
    } 
public static void searchadmin (String searchTerm) 
{
    boolean found = false;
    for (User admin : adminmap.values()) 
    {
        if (String.valueOf(admin.getId()).equals(searchTerm) || admin.getName().equalsIgnoreCase(searchTerm) || 
            admin.getEmail().equalsIgnoreCase(searchTerm)) 
        {
//            System.out.println("admin  found: ");
//            System.out.println("ID: " + admin.getId());
//            System.out.println("Name: " + admin.getName());
//            System.out.println("Email: " + admin.getEmail());
            found = true;
            continue; 
        }
    }
    if (!found) 
    {
        System.out.println("No admin found with the given search term.");
    }
    
    }
@Override
public boolean login(int id, String password) {
    readfile();
        Admin admin = adminmap.get(id);
        if (admin != null && admin.getPassword().equals(password)) {
            System.out.println("Admin login successful for " + admin.getName());
            return true;
        }
        System.out.println("Invalid Admin ID or password.");
        return false;
    }
public static void searchcategory(String category)
{
  Book.searchBooks(category);
}
public static void borrowspecifictime(String date)
{
     Borrowing.searchBorrowingsdate(date);
}
public static void mostborrowedtime(String date)
{
    Borrowing.mostBorrowedBookOnDate(date);
}
public static void mostborrowedrevenue(String date)
{
    Borrowing.mostRevenueBookOnDate(date);
}
public static void borrowingsperlibrarian(int LibrarianId)
{
    Borrowing.LibrarianBorrowings(LibrarianId);
}
public static void LibrarianMaxBorrowings()
{
    Librarian.maxborrowcount();
}
public static void LibrarianMaxRevenue()
{
    Librarian.maxtotalrevenue();
}
public static boolean add(int id, String name, int phone){
      return Supplier.add(id, name, phone);
  }
  public static void editSupplier(int supplierId, String newName, String newPhone, String newOrderCount, String newRevenue)
  {
      Supplier.editSupplier(supplierId, newName, newPhone, newOrderCount, newRevenue);
  }
 
  public static void deleteSupplier(int supplierId)
  {
      Supplier.deleteSupplier(supplierId);
  }
  public static void searchSupplier(String searchTerm)
  {
      Supplier.searchSupplier(searchTerm);
  }
public static void DisplaySupplier()
{
    Supplier.displaySuppliers();
}
public static void supplierorder(int SupplierId)
{
    Order.Supplierhistory(SupplierId);
}
public static void MaxRevenueSupplier(){
      Supplier.MaxRevenueSupplier();
  }
  public static void MaxOrderCounterSupplier()
{
    Supplier.MaxOrderCountSupplier();
}
public static void BorrowingsPerBorrower(int BorrowerId)
{
    Borrowing.borrowingHistory(BorrowerId);
}
public static void BorrowerMaxCount()
{
    Borrower.maxborrowcount();
}
public static void BorrowerMaxRevenue()
{
    Borrower.maxtotalrevenue();
}
public static void BorrowingsDetails()
{
    Borrowing.displayBorrowing();
}
public static void AverageTotalRevenueTime(String Date)
{
    Borrowing.TotalAndAverageRevenueDate(Date);
}
public static void CreateOrder()
{
    Order.createOrder();
}
public static void CancelOrder(int OrderId)
{
    Order.cancelOrder(OrderId);
}
public static void OrdersDetails(int OrderId)
{
    Order.viewOrderDetails(OrderId);
}
public static void UpdateOrder(int OrderId)
{
    Order.updateOrder(OrderId);
}
public static String searchBook(String searchTerm)
{
    return Book.searchBooks(searchTerm);
}
public static String calculatePayment(List<Integer> selectedBookIds)
{
    return Book.calculatePayment(selectedBookIds);
}
public static boolean addBook(int bookId, String title, String author, String category, double price, int copies,  int copiesBorrowed, int borrowCount, double revenue, int copiesAvailability) 
 {
     return Book.addBook(bookId,  title,  author,  category,  price,  copies,
                 copiesBorrowed,  borrowCount,  revenue,  copiesAvailability);
 }
public  void editBook(int bookId, String title, String author, String category, double price, int copies,  int copiesBorrowed, int borrowCount, double revenue, int copiesAvailability) 
 {
     Book.editBook(bookId,  title,  author,  category,  price,  copies,
                 copiesBorrowed,  borrowCount,  revenue,  copiesAvailability);
 }
public  void removeBook(int bookId) 
{
       Book.removeBook(bookId);
   }
public  void searchBooks(String searchTerm) 
{
     Book.searchBooks(searchTerm);
  }
public static void displayBooks()
{
          Book.displayBooks();
   }
public static void updateRevenue(int bookId) 
{
        Book.updateRevenue(bookId); 
    }
public static void maxRevenue() 
{
      Book.maxRevenue();
    }
public static void findMaxBorrowCount() 
{
      Book.findMaxBorrowCount();
  }
}