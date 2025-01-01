package javalibrary;
import java.io.*; 
import java.util.*;

public class Book implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int bookId;
    private String title;  // Removed static, as it should be unique to each book
    private String author;
    private String category;
    private double price;  // Removed static, as it should be unique to each book
    private int copies;  // Removed static, as it should be unique to each book
    private int copiesBorrowed;
    private int borrowCount;  // Removed static, as it should be unique to each book
    private double revenue;  // Removed static, as it should be unique to each book
    private int copiesAvailability;
    public static HashMap<Integer, Book> map = new HashMap<>();
    static Scanner input = new Scanner(System.in);
    // Constructor
    public Book(int bookId, String title, String author, String category, double price, int copies,
                int copiesBorrowed, int borrowCount, double revenue, int copiesAvailability) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.copies = copies;
        this.copiesBorrowed = copiesBorrowed;
        this.borrowCount = borrowCount;
        this.revenue = revenue;
        this.copiesAvailability = copiesAvailability;
        
    }
    // Getters and setters for all attributes (no static methods for instance variables)
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getCopies() { return copies; }
    public void setCopies(int copies) { this.copies = copies; }
    public int getCopiesBorrowed() { return copiesBorrowed; }
    public void setCopiesBorrowed(int copiesBorrowed) { this.copiesBorrowed = copiesBorrowed; }
    public int getBorrowCount() { return borrowCount; }
    public void setBorrowCount(int borrowCount) { this.borrowCount = borrowCount; }
    public double getRevenue() { return revenue; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
    public int getCopiesAvailability() { return copiesAvailability; }
    public void setCopiesAvailability(int copiesAvailability) { this.copiesAvailability = copiesAvailability; }
    public static void saveFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("book.bin"))) {
            out.writeObject(map);
            System.out.println("Books saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }
    public static void readFile() {
        try (ObjectInputStream loading = new ObjectInputStream(new FileInputStream("book.bin"))) {
            map = (HashMap<Integer, Book>) loading.readObject();
            System.out.println("Books loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
    }

    public static boolean addBook(int bookId, String title, String author, String category, double price, int copies,
                int copiesBorrowed, int borrowCount, double revenue, int copiesAvailability) 
    {
        readFile();
        if (map.containsKey(bookId))
        {
            return false ;
          
        }
             Book newBook = new Book(bookId, title, author, category, price, copies, 0, 0, 0.0, copies);
            map.put(bookId, newBook);
            saveFile();
            return true ;
        
    }

    public static void editBook(int bookId, String title, String author, String category, double price, int copies,
                int copiesBorrowed, int borrowCount, double revenue, int copiesAvailability) 
    {
        readFile();
        if (!map.containsKey(bookId)) 
    {
          
            System.out.println("Book not found for id "+bookId+".");
              return;
    }

    Book book = map.get(bookId);
    System.out.println("Editing Book: " + book.getTitle());

    // Update title if provided
    if (title != null && !title.isEmpty()) {
        book.setTitle(title);
    }

    // Update author if provided
    if (author != null && !author.isEmpty()) {
        book.setAuthor(author);
    }

    // Update category if provided
    if (category != null && !category.isEmpty()) {
        book.setCategory(category);
      }
//                                          //  not working error
// // Update price if provided
//    if (price != null && !price.isEmpty()) {
//        book.setPrice(price);
//    }

    map.put(bookId, book); // Save updated admin back to the map
    saveFile();
    System.out.println("Book data updated successfully.");
  }

    public static void removeBook(int bookId) 
    {
        readFile();
        if (map.containsKey(bookId)) {
            map.remove(bookId);
            saveFile();
            System.out.println("Book with ID " + bookId + " has been deleted.");
        } else {
            System.out.println("No Book found with ID " + bookId + ".");
        }
    }
    
    public static void displayBooks()
    {
        readFile();
        if (map.isEmpty())
        {
           // System.out.println("No books available.");
        } 
        else {
            //System.out.println("Book List:");
            for (Book book : map.values()) {
                System.out.println("ID: " + book.getBookId() +
                        ", Title: " + book.getTitle() +
                        ", Author: " + book.getAuthor() +
                        ", Category: " + book.getCategory() +
                        ", Price: $" + book.getPrice() +
                        ", Available Copies: " + book.getCopiesAvailability() +
                        ", Borrowed Copies: " + book.getCopiesBorrowed() +
                        ", Borrow Count: " + book.getBorrowCount() +
                        ", Revenue: $" + book.getRevenue());
            }
        }
    }
    public  void updateAvailability() 
    {
        copiesAvailability = copies - copiesBorrowed;
        System.out.println("Availability updated: " + copiesAvailability + " copies available.");
    }
    public static void updateRevenue(int bookId) 
    {
        Book book = map.get(bookId);
        double updatedRevenue = book.getBorrowCount() * book.getPrice();
        book.setRevenue(updatedRevenue); 
        System.out.println("Revenue for Book ID " + bookId + " updated: $" + updatedRevenue);
        saveFile();
    }
    
    public static void maxRevenue() 
    {
    readFile(); 
    if (map.isEmpty()) 
    {
    //    System.out.println("No books available.");
        return;
    }
    Book maxRevenueBook = null;
    for (Book book : map.values()) 
    {
        if (maxRevenueBook == null || book.getRevenue() > maxRevenueBook.getRevenue()) 
        {
            maxRevenueBook = book;
        }
    }
    
   // System.out.println("Book with Maximum Revenue:");
   
    if (maxRevenueBook != null) 
    {
//        System.out.println("ID: " + maxRevenueBook.getBookId() +
//                ", Title: " + maxRevenueBook.getTitle() +
//                ", Category: " + maxRevenueBook.getCategory() +
//                ", Price: $" + maxRevenueBook.getPrice() +
//                ", Available Copies: " + maxRevenueBook.getCopiesAvailability() +
//                ", Borrowed Copies: " + maxRevenueBook.getCopiesBorrowed() +
//                ", Borrow Count: " + maxRevenueBook.getBorrowCount() +
//                ", Revenue: $" + maxRevenueBook.getRevenue());
    }
    }
    public static void findMaxBorrowCount() 
    {
    readFile(); 
    if (map.isEmpty())
    {
      //  System.out.println("No books available.");
        return;
    }

    Book maxBorrowCountBook = null;

    for (Book book : map.values()) {
        if (maxBorrowCountBook == null || book.getBorrowCount() > maxBorrowCountBook.getBorrowCount()) {
            maxBorrowCountBook = book;
        }
    }

   // System.out.println("Book with Maximum Borrow Count:");
    if (maxBorrowCountBook != null)
    {
//        System.out.println("ID: " + maxBorrowCountBook.getBookId() +
//                ", Title: " + maxBorrowCountBook.getTitle() +
//                ", Category: " + maxBorrowCountBook.getCategory() +
//                ", Price: $" + maxBorrowCountBook.getPrice() +
//                ", Available Copies: " + maxBorrowCountBook.getCopiesAvailability() +
//                ", Borrowed Copies: " + maxBorrowCountBook.getCopiesBorrowed() +
//                ", Borrow Count: " + maxBorrowCountBook.getBorrowCount() +
//                ", Revenue: $" + maxBorrowCountBook.getRevenue());
//        
    }
    }
  public static String searchBooks(String searchTerm) {
    readFile();
    boolean found = false;
    StringBuilder result = new StringBuilder();
    for (Book book : map.values()) {
        if (String.valueOf(book.getBookId()).equals(searchTerm) ||
                book.getAuthor().equalsIgnoreCase(searchTerm) ||
                book.getCategory().equalsIgnoreCase(searchTerm)) {
            result.append("Book found: ID=").append(book.getBookId())
                .append(", Title=").append(book.getTitle())
                .append(", Author=").append(book.getAuthor())
                .append(", Category=").append(book.getCategory())
                .append(", Copies Available=").append(book.getCopiesAvailability()).append("\n");
            found = true;
        }
    }
    if (!found) {
        result.append("No book found matching the search term.");
    }
    return result.toString();
}

  public static String calculatePayment(List<Integer> selectedBookIds) {
    double totalPayment = 0.0;
    StringBuilder result = new StringBuilder();

    // Iterate through the list of book IDs
    for (int bookId : selectedBookIds) {
        if (map.containsKey(bookId)) {
            Book book = map.get(bookId);
            // Calculate the payment for the book (borrowed copies * price per book)
            totalPayment += book.getPrice() * book.getCopiesBorrowed();
        } else {
            result.append("Book with ID " + bookId + " not found.\n");
        }
    }

    // Append the total payment to the result
    result.append("Total Payment for the selected books: $").append(totalPayment);

    return result.toString();  // Return the result as a string
}
    
}