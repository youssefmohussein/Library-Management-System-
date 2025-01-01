    package javalibrary;
    import java.io.*;
    import java.util.*;
    public class Librarian implements User, Serializable {
        private static final long serialVersionUID = 1L;
        private int id;
        private String name;
        private String email;
        private String password;
        private int borrowcount;
        private double revenue;
        // Static data structure to hold all librarian instances
        static Scanner input = new Scanner(System.in);
        static HashMap<Integer, Librarian> librarianmap = new HashMap<>();
        // Constructor for Librarian
        public Librarian(int id, String name, String email, String password, int borrowcount, float revenue) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
            this.borrowcount = borrowcount;
            this.revenue = revenue;
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
        @Override
        public boolean login(int id, String password) {
            readfile();
            for (Librarian librarian : librarianmap.values()) {
                if (librarian.getId() == id && librarian.getPassword().equals(password)) {
                    System.out.println("Login successful for " + librarian.getName());
                    return true;
                }
            }
            System.out.println("Login failed: Invalid ID or password.");
            return false;
        }
        // Getters and setters for borrowcount and revenue
        public int getBorrowcount() {
            return borrowcount;
        }
        public double getRevenue() {
            return revenue;
        }
        public void setBorrowcount(int borrowcount) {
            this.borrowcount = borrowcount;
        }
        public void setRevenue(double revenue) {
            this.revenue = revenue;
        }
        // Save librarian data to file
        public static void savefile() {
            try (ObjectOutputStream writing = new ObjectOutputStream(new FileOutputStream("Librarian.bin"))) {
                writing.writeObject(librarianmap);
                System.out.println("Librarian data saved to file successfully.");
            } catch (IOException e) {
                System.out.println("Error while saving Librarian: " + e.getMessage());
            }
        }
        // Read librarian data from file
        public static void readfile() {
            try (ObjectInputStream loading = new ObjectInputStream(new FileInputStream("Librarian.bin"))) {
                librarianmap = (HashMap<Integer, Librarian>) loading.readObject();
                System.out.println("Librarians loaded from file successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error while loading Librarian: " + e.getMessage());
            }
        }
        // Add a new librarian
        public static boolean addLibrarian(int id, String name, String email, String password, int borrowcount, float revenue) {
            readfile();
            if (librarianmap.containsKey(id)) {
                return false;
            }
            Librarian librarian = new Librarian(id, name, email, password, borrowcount, revenue);
            librarianmap.put(id, librarian);
            savefile();
            return true;
        }
        // Edit librarian details
        public static void editLibrarian(int librarianId, String name, String email, String password, int borrowcount, float revenue){
        readfile();
        if (!librarianmap.containsKey(librarianId)) {
            System.out.println("No data found for ID " + librarianId + ".");
            return;
        }
        Librarian librarian = librarianmap.get(librarianId);
        System.out.println("Editing librarian: " + librarian.getName());
   
        if (!name.isEmpty()) 
        {
            librarian.setName(name);
        }

        if (!email.isEmpty())
        {
            librarian.setEmail(email);
        }

        if (!password.isEmpty())
        {
            librarian.setPassword(password);
        }

        librarianmap.put(librarianId, librarian);
        savefile();
        System.out.println("Librarian data updated successfully.");
    }
        public static void deleteLibrarian(int librarianId) {
        readfile();
        if (librarianmap.containsKey(librarianId))
        {
            librarianmap.remove(librarianId);
            savefile();
            System.out.println("Librarian with ID " + librarianId + " has been deleted.");
        } else {
            System.out.println("No librarian found with ID " + librarianId + ".");
        }
    }
        public static void searchLibrarian(String searchterm) {
        readfile();
        boolean found = false;
       for (Librarian lib : librarianmap.values()) 
        {
            if (String.valueOf(lib.getId()).equals(searchterm) || 
                lib.getName().equalsIgnoreCase(searchterm) || 
                String.valueOf(lib.getEmail()).equalsIgnoreCase(searchterm)
             ) 
            {
                 found = true;
                continue;
            }
        }
        if (!found) {
            System.out.println("No librarian found with the given search term.");
        }
    }
        // Display all librarians
        public static void displayLibrarians() {
            readfile();
            if (librarianmap.isEmpty()) {
                System.out.println("No librarians available to display.");
                return;
            }
            System.out.println("Librarian List:");
            for (Librarian librarian : librarianmap.values()) {
                System.out.println("ID: " + librarian.getId());
                System.out.println("Name: " + librarian.getName());
                System.out.println("Email: " + librarian.getEmail());
                System.out.println("Borrow Count: " + librarian.getBorrowcount());
                System.out.println("Revenue: $" + librarian.getRevenue());
            }
        }
        // Find librarian with the highest borrow count
        public static void maxborrowcount() {
       readfile();
    if (librarianmap.isEmpty())
    {
        //System.out.println("No Librarian available to evaluate.");
        return;
    }
    Librarian maxBorrower = null;
    for (Librarian librarian : librarianmap.values()) {
        if (maxBorrower == null || librarian.getBorrowcount() > maxBorrower.getBorrowcount()) {
            maxBorrower = librarian;
        }
    }
    if (maxBorrower != null) 
    {
    }
}
    
    public static void maxtotalrevenue()
    {
    readfile(); 
    if (librarianmap.isEmpty()) 
    {
       // System.out.println("No Librarian available to evaluate.");
        return;
    }
    Librarian maxRevenueBorrower = null;
    for (Librarian librarian : librarianmap.values())
    {
        if (maxRevenueBorrower == null || librarian.getRevenue() > maxRevenueBorrower.getRevenue())
        {
            maxRevenueBorrower = librarian;
        }
    }
    if (maxRevenueBorrower != null)
    {
    }
    }
        // Method to create a borrowing record
        public static boolean CreateBorrowing(int librarianId, int borrowingId, int borrowerId, int bookId, String borrowingDate, String returnDate) 
        {
            return Borrowing.createBorrowing(librarianId, borrowingId, borrowerId, bookId, borrowingDate, returnDate, true)  ;  
         }
        // Method to cancel a borrowing
        public static boolean CancelBorrowing(int BorrowingId) {
            return Borrowing.cancelBorrowing(BorrowingId);
        }
 }

