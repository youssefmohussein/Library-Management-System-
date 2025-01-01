package javalibrary;
import java.io.*;
import java.util.*;
public class Borrowing implements Serializable
{
    private static final long serialVersionUID = 1L;    
    private int borrowingId;
    private int librarianId;
    private int borrowerId;
    private int bookId;
    private String borrowingDate;
    private String returnDate;
    private boolean isReturned;
    private int rate;
    // Static map to store borrowing data
    static HashMap<Integer, Borrowing> borrowinggMap = new HashMap<>();
    private static Scanner input = new Scanner(System.in);
    // Constructor for Borrowing class
    public Borrowing(int librarianId, int borrowingId, int borrowerId, int bookId, String borrowingDate, String returnDate, boolean isReturned, int rate) {
        this.librarianId = librarianId;
        this.borrowingId = borrowingId;
        this.borrowerId = borrowerId;
        this.bookId = bookId;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
        this.rate = rate;
    }

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }
    
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    
    // Getters and Setters
    public int getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(int borrowingId) {
        this.borrowingId = borrowingId;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public String getBorrowingDate() {
        return borrowingDate;
    }
    public void setBorrowingDate(String borrowingDate) {
        this.borrowingDate = borrowingDate;
    }
    public String getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    public boolean isReturned() {
        return isReturned;
    }
    public void setReturned(boolean isReturned) {
        this.isReturned = isReturned;
    }
    // Method to save borrowing data to a file
    public static void savefile() {
        try (ObjectOutputStream writing = new ObjectOutputStream(new FileOutputStream("borrowingdata.bin"))) {
            writing.writeObject(borrowinggMap);
            System.out.println("Borrowing data saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error while saving Borrowing: " + e.getMessage());
        }
    }
    // Method to load borrowing data from a file
    public static void readfile() {
        try (ObjectInputStream loading = new ObjectInputStream(new FileInputStream("borrowingdata.bin"))) {
            borrowinggMap = (HashMap<Integer, Borrowing>) loading.readObject();
            System.out.println("Borrowing loaded from file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while loading Borrowing: " + e.getMessage());
        }
    }

    // Method to create a new borrowing record
   public static boolean createBorrowing(int librarianId, int borrowingId, int borrowerId, int bookId, String borrowingDate, String returnDate, boolean isReturned) {
        Book.readFile();  // Load books data
        Librarian.readfile();
        Borrower.readfile();  // Load borrowers data
        Borrowing.readfile();  // Load borrowing data

        // Check if librarian exists
        if (!Librarian.librarianmap.containsKey(librarianId)) {
            System.out.println("Librarian ID " + librarianId + " not Found");
            return false;
        }

        // Check if the borrowing ID already exists
        if (borrowinggMap.containsKey(borrowingId)) {
            System.out.println("Borrowing ID " + borrowingId + " already exists.");
            return false;
        }

        // Check if the borrower exists
        if (!Borrower.boorrowermap.containsKey(borrowerId)) {
            System.out.println("Borrower ID " + borrowerId + " not found.");
            return false;
        }

        // Check if the book exists
        if (!Book.map.containsKey(bookId)) {
            System.out.println("Book ID " + bookId + " not found.");
            return false;
        }

        Book selectedBook = Book.map.get(bookId);

        // Check if the book has available copies
        if (selectedBook.getCopiesAvailability() <= 0) {
            System.out.println("No copies available for Book ID: " + bookId);
            return false;
        }

        // Borrowing Date and Return Date
        double bookPrice = selectedBook.getPrice();
        Borrower borrower = Borrower.boorrowermap.get(borrowerId);
        borrower.setBorroweringcounter(borrower.getBorroweringcounter() + 1);
        borrower.setTotalrevenue(borrower.getTotalrevenue() + bookPrice);
        Borrower.savefile();

        // Create new borrowing object and save
        int rate = 0; // Default rate is 0
        Borrowing newBorrowing = new Borrowing(librarianId, borrowingId, borrowerId, bookId, borrowingDate, returnDate, isReturned, rate);
        borrowinggMap.put(borrowingId, newBorrowing);
        savefile();

        // Update book details
        selectedBook.setCopiesBorrowed(selectedBook.getCopiesBorrowed() + 1);
        selectedBook.setCopiesAvailability(selectedBook.getCopiesAvailability() - 1);
        selectedBook.setBorrowCount(selectedBook.getBorrowCount() + 1);
        Book.updateRevenue(bookId);
        Book.saveFile();

        // Update librarian details
        Librarian librarian = Librarian.librarianmap.get(librarianId);
        librarian.setBorrowcount(librarian.getBorrowcount() + 1);
        librarian.setRevenue(librarian.getRevenue() + bookPrice);
        Librarian.savefile();

        System.out.println("Item borrowed successfully.");
        return true; // Success
    }
    // Method to cancel a borrowing record
  // Method to cancel a borrowing record (corrected)
    public static boolean cancelBorrowing(int borrowingId) {
    // Check if borrowing ID exists
    if (!borrowinggMap.containsKey(borrowingId)) {
        System.out.println("Borrowing ID " + borrowingId + " not found.");
        return false; // Return false if borrowing ID is not found
    }

    Borrowing borrowing = borrowinggMap.get(borrowingId);
    
    // If the book has already been returned, it can't be cancelled
    if (borrowing.isReturned) {
        System.out.println("The book has already been returned and cannot be cancelled.");
        return false; // Return false if the book is already returned
    }

    // Get the book associated with this borrowing
    Book book = Book.map.get(borrowing.getBookId());
    
    // Update the book's availability
    book.setCopiesBorrowed(book.getCopiesBorrowed() - 1);
    book.setCopiesAvailability(book.getCopiesAvailability() + 1);
    Book.updateRevenue(book.getBookId());
    Book.saveFile();

    // Update borrower information
    Borrower borrower = Borrower.boorrowermap.get(borrowing.getBorrowerId());
    borrower.setBorroweringcounter(borrower.getBorroweringcounter() - 1);
    borrower.setTotalrevenue(borrower.getTotalrevenue() - book.getPrice());
    Borrower.savefile();

    // Update librarian information
    Librarian librarian = Librarian.librarianmap.get(borrowing.getLibrarianId());
    librarian.setBorrowcount(librarian.getBorrowcount() - 1);
    librarian.setRevenue(librarian.getRevenue() - book.getPrice());
    Librarian.savefile();

    // Remove the borrowing record from the map
    borrowinggMap.remove(borrowingId);
    savefile(); // Save the updated borrowing records

    System.out.println("Borrowing cancelled successfully.");
    return true; // Return true if the borrowing was successfully cancelled
}
    // Method to display all borrowing records
   public static void displayBorrowing() {
    // Ensure borrowings are loaded from the file
    readfile();

    // Check if the borrowing map is empty
    if (borrowinggMap.isEmpty()) {
        System.out.println("No borrowings available.");
        return;
    }

    // Display each borrowing record
    System.out.println("Borrowing List:");
    for (Map.Entry<Integer, Borrowing> entry : borrowinggMap.entrySet()) {
        Borrowing borrow = entry.getValue();

        // Check if the necessary fields are available and valid
        if (borrow != null) {
            System.out.println(
                    "Librarian ID: "+ borrow.getLibrarianId()+
                    "Borrowing ID: " + borrow.getBorrowingId() +
                    ", Borrower ID: " + borrow.getBorrowerId() +
                    ", Book ID: " + borrow.getBookId() +
                    ", Borrowing Date: " + borrow.getBorrowingDate() +
                    ", Return Date: " + borrow.getReturnDate() +
                    ", Returned: " + borrow.isReturned() +
                    ", Rate: " + borrow.getRate()); // Use getRate() instead of hashCode()
        } else {
            System.out.println("Error: Borrowing data is null.");
        }
    }
}
   
public static String borrowingHistory(int borrowerId) {
    readfile();  // Ensure this loads the borrowing data correctly
    boolean found = false;

    StringBuilder result = new StringBuilder(); // To store the result string
    result.append("Borrowing History for Borrower ID: ").append(borrowerId).append("\n");

    for (Borrowing borrow : borrowinggMap.values()) {
        if (borrow.getBorrowerId() == borrowerId) {
            found = true;
            result.append("Borrowing ID: ").append(borrow.getBorrowingId()).append("\n");
            result.append("Book ID: ").append(borrow.getBookId()).append("\n");
            result.append("Borrowing Date: ").append(borrow.getBorrowingDate()).append("\n");
            result.append("Return Date: ").append(borrow.getReturnDate()).append("\n");
            result.append("Returned: ").append(borrow.isReturned() ? "Yes" : "No").append("\n");
            result.append("Rate: ").append(borrow.getRate()).append("\n"); 
        }
    }

    if (!found) {
        result.append("No borrowing history found for Borrower ID: ").append(borrowerId).append("\n");
    }

    return result.toString(); // Return the result as a string
}

  public static boolean rateBook(int borrowingId) {
    Borrowing.readfile();
    Book.readFile();

    // Find the borrowing
    Borrowing borrow = Borrowing.borrowinggMap.get(borrowingId);

    // Ensure borrowing exists
    if (borrow == null) {
        System.out.println("Invalid borrowing ID.");
        return false;
    }

    // Prompt for rating
    System.out.println("Please enter a rating between 1 and 5:");
    int rate;
    while (true) {
        try {
            rate = input.nextInt(); // Get the rating input
            if (rate >= 1 && rate <= 5) {
                break;
            } else {
                System.out.println("Invalid rating. Enter a number between 1 and 5.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
            input.next(); // Clear invalid input
        }
    }

    // Set the rating for this borrowing
    borrow.setRate(rate);

    // Save the updated data
    Borrowing.savefile();
    System.out.println("Your rating of " + rate + " has been recorded.");
    return true;
}

   public static void LibrarianBorrowings(int librarianId) {
    readfile();  
    Librarian.readfile();
    boolean found = false;
    Librarian librarian = Librarian.librarianmap.get(librarianId);
    System.out.println("Librarian Borrowing History for Librarian ID: " + librarianId);
    System.out.println("Total Borrow Count: " + librarian.getBorrowcount());
    for (Borrowing borrow : borrowinggMap.values()) 
    {
        if (borrow.getLibrarianId() == librarianId) 
        {
            found = true;
            System.out.println("Borrowing ID: " + borrow.getBorrowingId());
            System.out.println("Book ID: " + borrow.getBookId());
            System.out.println("Borrowing Date: " + borrow.getBorrowingDate());
            System.out.println("Return Date: " + borrow.getReturnDate());
            System.out.println("Returned: " + (borrow.isReturned() ? "Yes" : "No"));
            System.out.println("Rate: " + borrow.getRate());
        }
    }
    if (!found) {
        System.out.println("No borrowing history found for Librarian ID: " + librarianId);
    }
}
   public static void searchBorrowingsdate(String date) 
   {
        readfile();
        boolean found = false;
        for (Borrowing borrow : borrowinggMap.values()) {
            if (String.valueOf(borrow.getBorrowingDate()).equals(date))
            {
            System.out.println("Borrowing ID: " + borrow.getBorrowingId());
            System.out.println("Borrower ID: " + borrow.getBorrowerId());
            System.out.println("Book ID: " + borrow.getBookId());
            System.out.println("Borrowing Date: " + borrow.getBorrowingDate());
            System.out.println("Return Date: " + borrow.getReturnDate());
            System.out.println("Returned: " + (borrow.isReturned() ? "Yes" : "No"));
            found = true;
            }
        }
        if (!found) {
            System.out.println("No borrowings found for the date");
        }
}
   public static void mostBorrowedBookOnDate(String specificDate) {
    readfile();
    int mostBorrowedBookId = -1;
    int maxCount = 0;
    for (Borrowing borrow : borrowinggMap.values()) {
        String borrowingDate = borrow.getBorrowingDate();
        if (borrowingDate.equals(specificDate)) 
        {
            int bookId = borrow.getBookId();
            int borrowCount = 0;
            for (Borrowing b : borrowinggMap.values()) 
            {
                if (b.getBookId() == bookId && b.getBorrowingDate().equals(specificDate))
                {
                    borrowCount++;
                }
            }
            if (borrowCount > maxCount) {
                mostBorrowedBookId = bookId;
                maxCount = borrowCount;
            }
        }
    }
    if (mostBorrowedBookId != -1) {
        System.out.println("Most borrowed book ID: " + mostBorrowedBookId);
        if (Book.map.containsKey(mostBorrowedBookId))
        {
            Book book = Book.map.get(mostBorrowedBookId);
            System.out.println("Book Title: " + book.getTitle());
            System.out.println("Book Author: " + book.getAuthor());
            System.out.println("Times Borrowed: " + maxCount);
        }
    } else {
        System.out.println("No books borrowed on the specified date.");
    }
}
   public static void mostRevenueBookOnDate(String specificDate) {
    readfile();
    Book.readFile();
    int maxRevenueBookId = -1;
    double maxRevenue = 0.0;
    for (Borrowing borrow : borrowinggMap.values()) {
        if (borrow.getBorrowingDate().equals(specificDate)) {
            int bookId = borrow.getBookId();
            Book book = Book.map.get(bookId);
            if (book != null) {
                double revenue = book.getPrice();
                int borrowCount = 0;
                for (Borrowing b : borrowinggMap.values()) {
                    if (b.getBookId() == bookId && b.getBorrowingDate().equals(specificDate)) {
                        borrowCount++;
                    }
                }
                revenue *= borrowCount;
                if (revenue > maxRevenue)
                {
                    maxRevenueBookId = bookId;
                    maxRevenue = revenue;
                }
            }
        }
    }
    if (maxRevenueBookId != -1) {
        System.out.println("Book that generated the most revenue on " + specificDate + ": " + maxRevenueBookId);
        Book book = Book.map.get(maxRevenueBookId);
        if (book != null) 
        {
            System.out.println("Book Title: " + book.getTitle());
            System.out.println("Book Author: " + book.getAuthor());
            System.out.println("Total Revenue: " + maxRevenue);
        }
    } else {
        System.out.println("No books borrowed on the specified date.");
    }
}
   public static String TotalAndAverageRevenueDate(String specificDate) {
    readfile();
    Book.readFile();
    double totalRevenue = 0.0;
    int totalBooksBorrowed = 0;
    StringBuilder result = new StringBuilder();  // To hold the result

    for (Borrowing borrow : borrowinggMap.values()) {
        if (borrow.getBorrowingDate().equals(specificDate)) {
            int bookId = borrow.getBookId();
            Book book = Book.map.get(bookId);
            if (book != null) {
                double revenue = book.getPrice();
                int borrowCount = 0;
                for (Borrowing b : borrowinggMap.values()) {
                    if (b.getBookId() == bookId && b.getBorrowingDate().equals(specificDate)) {
                        borrowCount++;
                    }
                }
                totalRevenue += revenue * borrowCount;
                totalBooksBorrowed++;
            }
        }
    }

    if (totalBooksBorrowed > 0) {
        double averageRevenue = totalRevenue / totalBooksBorrowed;
        result.append("Total Revenue on ").append(specificDate).append(": ").append(totalRevenue).append("\n");
        result.append("Average Revenue on ").append(specificDate).append(": ").append(averageRevenue).append("\n");
    } else {
        result.append("No books borrowed on the specified date.");
    }

    return result.toString();  // Return the result as a string
}

   
}
