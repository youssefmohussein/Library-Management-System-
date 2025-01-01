package javalibrary;
import java.io.*;
import java.util.*;
public class Borrower implements User, Serializable 
{
      private static final long serialVersionUID = 1L;
        private int id;
        private String name;
        private String email;
        private String password;
        private int borroweringcounter;
        private double totalrevenue;
        static Scanner input = new Scanner(System.in);
        static HashMap<Integer, Borrower> boorrowermap = new HashMap<>();
        public Borrower(int id, String name, String email, String password, int borroweringcounter, float totalrevenue)
        {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
            this.borroweringcounter = borroweringcounter;
            this.totalrevenue = totalrevenue;
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
        public double getTotalrevenue() 
        {
            return totalrevenue;
        } 
        public void setTotalrevenue(double totalrevenue)
        {
            this.totalrevenue = totalrevenue;
        }
        public int getBorroweringcounter() {
        return borroweringcounter;
    }
        public void setBorroweringcounter(int borroweringcounter) {
        this.borroweringcounter = borroweringcounter;
    }
        public static void savefile() 
        {
            try (ObjectOutputStream writing = new ObjectOutputStream(new FileOutputStream("borrower.bin"))) 
            {
                writing.writeObject(boorrowermap);
                writing.close();
                System.out.println("Borrower data saved to file successfully.");
            } 
            catch (IOException e) 
            {
                System.out.println("Error while saving Borrower: " + e.getMessage());
            }
        }
        public static void readfile()
        {
            try (ObjectInputStream loading = new ObjectInputStream(new FileInputStream("borrower.bin"))) 
            {
                boorrowermap = (HashMap<Integer, Borrower>) loading.readObject();
                System.out.println("Borrower loaded from file successfully.");
                loading.close();
            } 
            catch (IOException | ClassNotFoundException e)
            {
                System.err.println("Error while loading Borrower: " + e.getMessage());
            }
        }
        public static boolean addborrower(int id, String name, String email, String password, int borroweringcounter, float totalrevenue) 
        {
            readfile();
             if (boorrowermap.containsKey(id))
             {
                return false ;
            }
            Borrower borowwer = new Borrower(id, name, email, password, 0, 0);
            boorrowermap.put(id, borowwer);
            savefile();
         return true ;
        }
        public static void editborrower(int borrowerid, String name, String email, String password, int borroweringcounter, float totalrevenue) 
        {
            if (!boorrowermap.containsKey(borrowerid)) 
            {
               // System.out.println("No data found for ID " + borrowerid + ".");
                return;
            }
            Borrower borrower = boorrowermap.get(borrowerid);
           // System.out.println("Editing Borower: " + borrower.getName());
        
            if (!name.isEmpty()) {
                borrower.setName(name);
            }
       
            if (!email.isEmpty()) {
                borrower.setEmail(email);
            }

      
            if (!password.isEmpty()) {
                borrower.setPassword(password);
            }

            boorrowermap.put(borrowerid, borrower);
            savefile();
           // System.out.println("Borower data updated successfully.");
        }
        public static void deleteborrower(int borrowerid)
        {
            if (boorrowermap.containsKey(borrowerid)) 
            {
                boorrowermap.remove(borrowerid);
                savefile();
                //System.out.println("Borrower with ID " + borrowerid + " has been deleted.");
            } else {
               // System.out.println("No Borrower found with ID " + borrowerid + ".");
            }
        }
        public static void searchBorrower (String searchTerm) 
        {
            readfile();
        boolean found = false;
        for (User borrower : boorrowermap.values()) 
        {
            if (String.valueOf(borrower.getId()).equals(searchTerm) || 
                borrower.getName().equalsIgnoreCase(searchTerm) || 
                borrower.getEmail().equalsIgnoreCase(searchTerm)) 
            {
//                System.out.println("Borrower  found: ");
//                System.out.println("ID: " + borrower.getId());
//                System.out.println("Name: " + borrower.getName());
//                System.out.println("Email: " + borrower.getEmail());
                found = true;
                continue; 
            }
        }
        if (!found) {
          //  System.out.println("No Borrower found with the given search term.");
        }

   }
        @Override
       public boolean login(int id, String password) {
           readfile();
        for (Borrower borrower : boorrowermap.values()) {
            if (borrower.getId() == id && borrower.getPassword().equals(password)) {
                System.out.println("Login successful for " + borrower.getName());
                return true;
            }
        }
        System.out.println("Login failed: Invalid ID or password.");
        return false;
    }
       public static void displayBorrowers() 
       {
           readfile();
    if (boorrowermap.isEmpty()) {
        System.out.println("No borrowers available to display.");
        return;
    }
    System.out.println("Borrower List:");
    for (Borrower borrower : boorrowermap.values()) {
        System.out.println("ID: " + borrower.getId());
        System.out.println("Name: " + borrower.getName());
        System.out.println("Email: " + borrower.getEmail());
        System.out.println("Borrowing Counter: " +borrower.getBorroweringcounter());
        System.out.println("Total Revenue: $" + borrower.getTotalrevenue());
    }
    }
       
       
       public static void maxborrowcount() {
    readfile(); 
    if (boorrowermap.isEmpty()) {
        System.out.println("No borrowers available to evaluate.");
        return;
    }
    Borrower maxBorrower = null;
    for (Borrower borrower : boorrowermap.values()) {
        if (maxBorrower == null || borrower.getBorroweringcounter()> maxBorrower.getBorroweringcounter()) {
            maxBorrower = borrower;
        }
    }
    if (maxBorrower != null) {
        System.out.println("Borrower with the highest borrowing count:");
        System.out.println("ID: " + maxBorrower.getId());
        System.out.println("Name: " + maxBorrower.getName());
        System.out.println("Email: " + maxBorrower.getEmail());
        System.out.println("Borrowing Counter: " + maxBorrower.getBorroweringcounter());
        System.out.println("Total Revenue: $" + maxBorrower.getTotalrevenue());
    }
}
       public static void maxtotalrevenue()
       {
    readfile(); 
    if (boorrowermap.isEmpty()) 
    {
        System.out.println("No borrowers available to evaluate.");
        return;
    }
    Borrower maxRevenueBorrower = null;
    for (Borrower borrower : boorrowermap.values())
    {
        if (maxRevenueBorrower == null || borrower.getTotalrevenue() > maxRevenueBorrower.getTotalrevenue())
        {
            maxRevenueBorrower = borrower;
        }
    }
    if (maxRevenueBorrower != null)
    {
        System.out.println("Borrower with the highest total revenue:");
        System.out.println("ID: " + maxRevenueBorrower.getId());
        System.out.println("Name: " + maxRevenueBorrower.getName());
        System.out.println("Email: " + maxRevenueBorrower.getEmail());
        System.out.println("Borrowcount: " + maxRevenueBorrower.getBorroweringcounter());
        System.out.println("Total Revenue: $" + maxRevenueBorrower.getTotalrevenue());
    }
    }
       public static boolean ratebook(int BookId)
       {
           return Borrowing.rateBook(BookId);
       }
       public static String ViewBorrowingHistory(int BorrowerId)
       {
          return  Borrowing.borrowingHistory(BorrowerId);
          
       }
}
