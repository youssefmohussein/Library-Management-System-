    package javalibrary;
//  import java.util.Scanner;
    import java.util.Optional;
    import javafx.geometry.*;
    import javafx.application.Application;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.layout.*;
    import javafx.stage.Stage;
    import javafx.scene.image.*;
    import java.io.*;
    import javafx.animation.*;
    import javafx.util.*;
    import java.util.*;
    import javafx.event.*;
    import javafx.scene.media.*;
    public class Javalibrary extends Application 
    {
    public static void main(String[] args) 
    {   
            launch(args);
        }
    //Start
    @Override
    public void start(Stage primaryStage) 
    {
            // Create buttons with images for Admin, Borrower, and Librarian
            Button adminButton = createButton("C:\\Users\\LENOVO\\Downloads\\Leonardo_Phoenix_A_serene_and_organized_library_interior_with_3.jpg");
            Button borrowerButton = createButton("C:\\Users\\LENOVO\\Downloads\\Leonardo_Phoenix_A_serene_and_organized_library_interior_with_1.jpg");
            Button librarianButton = createButton("C:\\Users\\LENOVO\\Downloads\\Leonardo_Phoenix_A_warm_and_inviting_image_of_a_modern_library_1.jpg");


            // Set actions for each button to show the login page for the respective role
            adminButton.setOnAction(e -> showLoginPage(primaryStage, "Admin"));
            borrowerButton.setOnAction(e -> showLoginPage(primaryStage, "Borrower"));
            librarianButton.setOnAction(e -> showLoginPage(primaryStage, "Librarian"));

            // Arrange buttons in a horizontal layout
            HBox roleSelectionBox = new HBox(50, adminButton, borrowerButton, librarianButton);
            roleSelectionBox.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
            // Create and display the main scene
            Scene scene = new Scene(roleSelectionBox, 1080, 720);
            primaryStage.setTitle("Role Selection");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    //Image Button
    private Button createButton(String imagePath) 
    {
            try {
                // Load image and set it in a button
                Image image = new Image(new FileInputStream(imagePath));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(300);
                imageView.setFitHeight(300);
                Button button = new Button("", imageView);
                TranslateTransition slide = new TranslateTransition(Duration.seconds(3), button);
                slide.setFromX(-500); // Start off-screen
                slide.setToX(0); // Slide to the original position
                slide.play();
                return button;
            } catch (FileNotFoundException e) {
                System.out.println("Image not found: " + imagePath);
                return new Button("Error"); // Fallback button in case of an error
                
            }
    }
    //Login Page
    private void showLoginPage(Stage primaryStage, String role) {
        // Create fields for ID and Password
        Label idLabel = new Label("ID: ");
        idLabel.setStyle("-fx-text-fill:White;");
        TextField idField = new TextField();
        idField.setMinWidth(200); // Minimum width
        idField.setPrefWidth(200); // Preferred width   
        idField.setMaxWidth(200);
        Label passwordLabel = new Label("Password: ");
        passwordLabel.setStyle("-fx-text-fill:White;");
        PasswordField passwordField = new PasswordField();
        passwordField.setMinWidth(200); // Minimum width
        passwordField.setPrefWidth(200); // Preferred width
        passwordField.setMaxWidth(200); // Maximum width
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-text-fill:Black;");
        Button BackButton = new Button("Back");
        BackButton.setStyle("-fx-text-fill:Black;");
        double widrh =60;
        loginButton.setPrefWidth(widrh);
        BackButton.setPrefWidth(widrh);
        Label messageLabel = new Label(); // Label to show messages (e.g., "Login failed")
        loginButton.setOnAction(e -> 
        {
            try {
                int id = Integer.parseInt(idField.getText());
                String password = passwordField.getText();

                boolean success = false;

                while (!success) {
                    if ("Admin".equalsIgnoreCase(role)) {
                        Admin a = new Admin(0, "", "", "");
                        success = a.login(id, password);
                    } else if ("Borrower".equalsIgnoreCase(role)) {
                        Borrower b = new Borrower(0, "", "", "", 0, 0);
                        success = b.login(id, password);
                    } else if ("Librarian".equalsIgnoreCase(role)) {
                        Librarian l = new Librarian(0, "", "", "", 0, 0);
                        success = l.login(id, password);
                    }

                    if (success) {
                        messageLabel.setText("Login successful!");
                         messageLabel.setStyle("-fx-text-fill: green;"); 
                        if ("Admin".equalsIgnoreCase(role)) {
                            showAdmin(primaryStage);
                        } else if ("Borrower".equalsIgnoreCase(role)) {
                            showBorrowerMenu(primaryStage);
                            // showBorrowerMenu(primaryStage); // Replace with actual method
                        } else if ("Librarian".equalsIgnoreCase(role)) {
                            showLibrarianMenu(primaryStage);
                            // showLibrarianMenu(primaryStage); // Replace with actual method
                        }
                               }
                    else
                    {
                        messageLabel.setText("Login failed. Please try again.");
                        messageLabel.setStyle("-fx-text-fill:red;");
                        break; // Exit the loop to allow re-entry of credentials
                    }
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid ID. Please enter a numeric value.");
                messageLabel.setStyle("-fx-text-fill:red;");
            }
        });
        BackButton.setOnAction(e ->start(primaryStage));
        Image image = new Image("file:///C:/Users/LENOVO/Downloads/pngtree-huge-library-with-old-books-that-have-multiple-doors-image_2513239.jpg"); // Replace with the path to your image
        BackgroundImage backgroundImage = new BackgroundImage
        (image,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        // Layout for the login page
        VBox loginLayout = new VBox(15, idLabel, idField, passwordLabel, passwordField, loginButton,BackButton, messageLabel);
        loginLayout.setStyle("-fx-alignment: center; -fx-padding: 30;  -fx-spacing: 10px;");
        loginLayout.setBackground(new Background(backgroundImage));
        // Create and display the login scene
        Scene loginScene = new Scene(loginLayout,1080, 720);
        primaryStage.setTitle(role + " Login");
        primaryStage.setScene(loginScene);
    }
    //Message Back
    private void showMessage(Stage primaryStage, String message, EventHandler<ActionEvent> onBackAction) {
    Label messageLabel = new Label(message);
    Button backButton = new Button("Back");
    
    // Set the action for the back button based on the provided handler
    backButton.setOnAction(onBackAction);

    VBox messageLayout = new VBox(10, messageLabel, backButton);
    messageLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightgray;");

    Scene messageScene = new Scene(messageLayout,1080,720);
    primaryStage.setScene(messageScene);
    primaryStage.show();
    }
    //Jomana Dialog Hya fahma w htfhomyny 
    private String showInputDialog(String message) 
    {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Input Required");
            dialog.setHeaderText(message);
            Optional <String> result = dialog.showAndWait();
            return result.orElse(null);
        }
    //Admin Menu First 7 boxes
    private void showAdmin(Stage primaryStage) {


            // Create buttons for each admin action
            Button adminButton = new Button("Admin ");
            Button borrowerButton = new Button("Borrower ");
            Button librarianButton = new Button("Librarian ");
            Button borrowingButton = new Button("Borrowing");
            Button supplierButton = new Button("Supplier");
            Button orderButton = new Button("Order");
            Button bookButton = new Button("Book");
            Button logoutButton = new Button("Logout");
            double buttonWidth = 150;
            adminButton.setPrefWidth(buttonWidth);
            borrowerButton.setPrefWidth(buttonWidth);
            librarianButton.setPrefWidth(buttonWidth);
            borrowingButton.setPrefWidth(buttonWidth);
            supplierButton.setPrefWidth(buttonWidth);
            orderButton.setPrefWidth(buttonWidth);
            bookButton.setPrefWidth(buttonWidth);
            logoutButton.setPrefWidth(buttonWidth);
            adminButton.setOnAction(e -> showAdminMenu(primaryStage));
            borrowerButton.setOnAction(e -> showAdminBorrower(primaryStage));
            librarianButton.setOnAction(e -> showAdminLibrarian(primaryStage));
            borrowingButton.setOnAction(e -> showBorrowingMenu(primaryStage));
            supplierButton.setOnAction(e -> showAdminSupplier(primaryStage));
            orderButton.setOnAction(e -> showOrderMenu(primaryStage));
            bookButton.setOnAction(e -> showAdminBook(primaryStage));
            logoutButton.setOnAction(e -> start(primaryStage));

            // Create a layout and add buttons
            VBox adminLayout = new VBox(10,adminButton, borrowerButton, librarianButton, borrowingButton, supplierButton, orderButton, bookButton, logoutButton);
            adminLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: lightblue;");

            // Create and set the scene
            Scene adminScene = new Scene(adminLayout, 1080,720);
            primaryStage.setScene(adminScene);
            primaryStage.show();
        }    
    //Admin manges Admin functions
    private  void addAdmin(Stage primaryStage) 
    {
        // Create fields for admin details
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

         Label idLabel = new Label("ID:");
        TextField idField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button submitButton = new Button("Add Admin");
        Button cancelButton = new Button("Cancel");

        // Set action for submit button
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String idadmin = idField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty())
            {
                showMessage (primaryStage, "All fields are required!", evt -> showAdminMenu(primaryStage));  
            } 
            else 
            {
                int id =Integer.parseInt(idadmin);
                Admin.addAdmin(id, name, email, password); // Assuming ID is auto-generated
                showMessage(primaryStage, "Admin added successfully!" ,evt -> showAdminMenu(primaryStage));
                start(primaryStage); // Return to the main admin menu
            }
        });
         cancelButton.setOnAction(e ->  showAdmin(primaryStage));
                VBox formLayout = new VBox
            (10,
                    nameLabel, nameField,
                    idLabel,idField,
                    emailLabel, emailField,
                    passwordLabel, passwordField,
                    submitButton, cancelButton
            );
            formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");

            // Create a scene for the form
            Scene formScene = new Scene(formLayout, 1080,720);
            primaryStage.setTitle("Add Admin");
            primaryStage.setScene(formScene);
            primaryStage.show();

       }    
    public void editAdmin(Stage primaryStage) 
    {
        Label idLabel = new Label("Admin ID:");
        TextField idField = new TextField();
        Label nameLabel = new Label("New Name (optional):");
        TextField nameField = new TextField();
        Label emailLabel = new Label("New Email (optional):");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("New Password (optional):");
        PasswordField passwordField = new PasswordField();
        Button submitButton = new Button("Edit Admin");
        Button cancelButton = new Button("Cancel");
        submitButton.setOnAction(e -> {
            try {
                int adminId = Integer.parseInt(idField.getText());
                String newName = nameField.getText();
                String newEmail = emailField.getText();
                String newPassword = passwordField.getText();

                if (!Admin.adminmap.containsKey(adminId)) {
                    showMessage(primaryStage, "No data found for ID " + adminId + ".", evt -> showAdminMenu(primaryStage));
                    return;
                }

                Admin admin = Admin.adminmap.get(adminId);

                if (newName != null && !newName.isEmpty()) {
                    admin.setName(newName);
                }

                if (newEmail != null && !newEmail.isEmpty()) {
                    admin.setEmail(newEmail);
                }

                if (newPassword != null && !newPassword.isEmpty()) {
                    admin.setPassword(newPassword);
                }

                Admin.adminmap.put(adminId, admin);
                Admin.savefile();
                showMessage(primaryStage, "Admin data updated successfully.", evt -> showAdminMenu(primaryStage));
            } catch (NumberFormatException ex) {
                showMessage(primaryStage, "Invalid Admin ID. Please enter a valid number.", evt -> showAdminMenu(primaryStage));
            }
        });

        cancelButton.setOnAction(e ->  showAdmin(primaryStage));
        // Layout for the form
        VBox formLayout = new VBox(10, idLabel, idField, nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField, submitButton, cancelButton);
        formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
        Scene formScene = new Scene(formLayout, 1080,720);
        primaryStage.setTitle("Edit Admin");
        primaryStage.setScene(formScene);
        primaryStage.show();
    }
     private void deleteAdmin(Stage primaryStage)
    {
        // Labels and fields for Admin ID input
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        Button deleteButton = new Button("Delete");
        Button cancelButton = new Button("Cancel");

        // Set delete button action
        deleteButton.setOnAction(e -> {
            try {
                int adminId = Integer.parseInt(idField.getText());
                if (!Admin.adminmap.containsKey(adminId)) {
                    showMessage(primaryStage, "No data found for ID " + adminId + ".", evt -> showAdminMenu(primaryStage));
                    return;
                }

                // Delete the admin
                Admin.deleteadmin(adminId);
                showMessage(primaryStage, "Admin with ID " + adminId + " has been deleted.", evt -> showAdminMenu(primaryStage));
            } catch (NumberFormatException ex) {
                showMessage(primaryStage, "Invalid input. Please enter a valid numeric ID.", evt -> showAdminMenu(primaryStage));
            }
        });

        // Set cancel button action
         cancelButton.setOnAction(e ->  showAdmin(primaryStage));

        // Layout for the delete form
        VBox deleteLayout = new VBox(10, idLabel, idField, deleteButton, cancelButton);
        deleteLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
        Scene deleteScene = new Scene(deleteLayout, 1080,720);
        primaryStage.setTitle("Delete Admin");
        primaryStage.setScene(deleteScene);
        primaryStage.show();
    }
    public void searchAdmin(Stage primaryStage) 
    {
        Label searchLabel = new Label("Search Admin (by ID, Name, or Email):");
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        Button cancelButton = new Button("Cancel");

        searchButton.setOnAction(e -> {
            String searchTerm = searchField.getText();
        boolean found = false;
        for (User admin : Admin.adminmap.values()) 
        {
            if (String.valueOf(admin.getId()).equals(searchTerm) || admin.getName().equalsIgnoreCase(searchTerm) || 
                admin.getEmail().equalsIgnoreCase(searchTerm)) 
            {
                 showMessage(primaryStage,"Admin found:\nID: " + admin.getId() + "\nName: " + admin.getName() + 
                                        "\nEmail: " + admin.getEmail(), evt -> showAdminMenu(primaryStage));
                    found = true;
                    break; 
            }
        if (!found) 
        {
            showMessage(primaryStage,"No admin found with the given search term.", evt -> showAdminMenu(primaryStage));
        }

        }

        });

         cancelButton.setOnAction(e ->  showAdmin(primaryStage));
        VBox layout = new VBox(10);
        layout.getChildren().addAll(searchLabel, searchField, searchButton,cancelButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
        Scene scene = new Scene(layout, 1080,720);
        primaryStage.setTitle("Search Admin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }       
    private  void showAdminMenu (Stage primaryStage ) 
    {  
            primaryStage.setTitle("Admin Menu");

            // Create buttons for each admin action
            Button addAdminButton = new Button("Add Admin");
            Button editAdminButton = new Button("Edit Admin");
            Button deleteAdminButton = new Button("Delete Admin");
            Button searchAdminButton = new Button("Search Admin");
            Button BackButton = new Button("Back");
            double buttonWidth = 150;
            addAdminButton.setPrefWidth(buttonWidth);
            editAdminButton.setPrefWidth(buttonWidth);
            deleteAdminButton.setPrefWidth(buttonWidth);
            searchAdminButton.setPrefWidth(buttonWidth);
            BackButton.setPrefWidth(buttonWidth);
            addAdminButton.setOnAction(e -> addAdmin(primaryStage));
            editAdminButton.setOnAction(e -> editAdmin(primaryStage));
            deleteAdminButton.setOnAction(e -> deleteAdmin(primaryStage));
            searchAdminButton.setOnAction(e -> searchAdmin(primaryStage));
            BackButton.setOnAction(e->showAdmin(primaryStage));
            VBox layout = new VBox(10);
            layout.getChildren().addAll
            (
                  addAdminButton,
                  editAdminButton,
                  deleteAdminButton,
                  searchAdminButton,
                  BackButton
            );

            layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: lightblue ;"); 
            Scene x = new Scene(layout,1080,720);
            primaryStage.setScene(x);
            primaryStage.show();

        }     
    //Librarian Admin Menu 
     private  void showAdminLibrarian (Stage primaryStage )
    {
      primaryStage.setTitle("Admin manage Librarian");
      Button addLibrarianButton = new Button("Add Librarian");
      Button editLibrarianButton = new Button("Edit Librarian ");
      Button deleteLibrarianButton = new Button("Delete Librarian ");
      Button searchLibrarianButton = new Button("Search Librarian ");
      Button maxBorrowingLibrarianButton = new Button("Max Borrow Count Librarian ");
      Button maxRevenueLibrarianButton = new Button("Max Revenue Librarian "); 
      Button BackButton = new Button("Back");
      double buttonWidth = 200;
      addLibrarianButton.setPrefWidth(buttonWidth);
      editLibrarianButton.setPrefWidth(buttonWidth);
      deleteLibrarianButton.setPrefWidth(buttonWidth);
      searchLibrarianButton.setPrefWidth(buttonWidth);
      maxBorrowingLibrarianButton.setPrefWidth(buttonWidth);
      maxRevenueLibrarianButton.setPrefWidth(buttonWidth);
      BackButton.setPrefWidth(buttonWidth);
      addLibrarianButton.setOnAction(e ->addLibrarian(primaryStage) );
      editLibrarianButton.setOnAction(e ->editlibrarian(primaryStage) );
      deleteLibrarianButton.setOnAction(e ->deletelibrarian(primaryStage) );
      searchLibrarianButton.setOnAction(e ->searchlibrarian(primaryStage) );
      maxBorrowingLibrarianButton.setOnAction(e ->LibrarianMaxBorrowings(primaryStage));
      maxRevenueLibrarianButton.setOnAction(e ->LibrarianMaxRevenue(primaryStage));
     BackButton.setOnAction(e -> showAdminMenu(primaryStage));
     VBox layout = new VBox(10);
        layout.getChildren().addAll
        (
                addLibrarianButton,
                editLibrarianButton,
                deleteLibrarianButton,
                searchLibrarianButton,
                maxBorrowingLibrarianButton,
                maxRevenueLibrarianButton,
                BackButton
                
        );
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: lightblue ;"); 
        Scene x = new Scene(layout, 1080,720);
        primaryStage.setScene(x);
        primaryStage.show();
}
    //Admin manages Librarian
    private void addLibrarian(Stage primaryStage)
    {
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button submitButton = new Button("Add Librarian");
        Button cancelButton = new Button("Cancel");
        submitButton.setOnAction(e -> {
            try {
                Librarian.readfile();
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    showMessage(primaryStage, "All fields are required!", evt -> showAdminLibrarian(primaryStage));
                    return;
                }
                boolean isAdded = Librarian.addLibrarian(id, name, email, password, 0, 0);
                if (isAdded) 
                {
                    showMessage(primaryStage, "Librarian added successfully!", evt -> showAdminLibrarian(primaryStage));
                    start(primaryStage);
                } 
                else 
                {
                    showMessage(primaryStage, "Librarian with this ID already exists.", evt -> showAdminLibrarian(primaryStage));
                    return;
                }
            } 
            catch (NumberFormatException ex) 
            {
                showMessage(primaryStage, "Invalid ID. Please enter a numeric value.", evt -> showAdminLibrarian(primaryStage));
            }
        });
        cancelButton.setOnAction(e ->  showAdmin(primaryStage)); 
        VBox formLayout = new VBox(10, idLabel, idField, nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField, submitButton, cancelButton);
        formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
        Scene formScene = new Scene(formLayout, 1080,720);
        primaryStage.setTitle("Add Librarian");
        primaryStage.setScene(formScene);
        primaryStage.show();
    }
    public void editlibrarian (Stage primaryStage) 
    {
    Label idLabel = new Label("Librarian ID:");
    TextField idField = new TextField();
    Label nameLabel = new Label("New Name (optional):");
    TextField nameField = new TextField();
    Label emailLabel = new Label("New Email (optional):");
    TextField emailField = new TextField();
    Label passwordLabel = new Label("New Password (optional):");
    PasswordField passwordField = new PasswordField();
    Button submitButton = new Button("Edit Librarian");
    Button cancelButton = new Button("Cancel");
    submitButton.setOnAction(e -> {
        Librarian.readfile();
        try {
            int librarianId = Integer.parseInt(idField.getText());
            String newName = nameField.getText();
            String newEmail = emailField.getText();
            String newPassword = passwordField.getText();

            if (!Librarian.librarianmap.containsKey(librarianId))
            {
                showMessage(primaryStage, "No data found for ID " + librarianId + ".",evt->showAdminLibrarian(primaryStage));
                return;
            }

            Librarian librarian = Librarian.librarianmap.get(librarianId);

            if (newName != null && !newName.isEmpty()) {
                librarian.setName(newName);
            }

            if (newEmail != null && !newEmail.isEmpty()) {
                librarian.setEmail(newEmail);
            }

            if (newPassword != null && !newPassword.isEmpty()) {
                librarian.setPassword(newPassword);
            }

           // Librarian.librarianmap.put(librarianId , librarian);
            
            Librarian.editLibrarian(librarianId, newName, newEmail, newPassword, 0, 0);
            
          //  Librarian.savefile();
            showMessage(primaryStage, "Librarian data updated successfully.",evt->showAdminLibrarian(primaryStage));
        } catch (NumberFormatException ex) {
            showMessage(primaryStage, "Invalid Librarian ID. Please enter a valid number.",evt->showAdminLibrarian(primaryStage));
        }
    });

    cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    // Layout for the form
    VBox formLayout = new VBox(10, idLabel, idField, nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField, submitButton, cancelButton);
    formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene formScene = new Scene(formLayout, 1080,720);
    primaryStage.setTitle("Edit Librarian");
    primaryStage.setScene(formScene);
    primaryStage.show();
}
    public void deletelibrarian(Stage primaryStage)
    {
    // Labels and fields for Admin ID input
    Label idLabel = new Label("ID:");
    TextField idField = new TextField();
    Button deleteButton = new Button("Delete");
    Button cancelButton = new Button("Cancel");

    // Set delete button action
    deleteButton.setOnAction(e -> {
        Librarian.readfile();
        try {Librarian.readfile();
            int librarianId = Integer.parseInt(idField.getText());
            if (!Librarian.librarianmap.containsKey(librarianId)) {
                showMessage(primaryStage, "No data found for ID " + librarianId + ".",evt->showAdminLibrarian(primaryStage));
                return;
            }

            // Delete the Librarian
            Librarian.deleteLibrarian(librarianId);
            showMessage(primaryStage, " Librarian with ID " + librarianId + " has been deleted.",evt->showAdminLibrarian(primaryStage));
        } 
        catch (NumberFormatException ex) 
        {
            showMessage(primaryStage, "Invalid input. Please enter a valid numeric ID.",evt->showAdminLibrarian(primaryStage));
        }
    });

    // Set cancel button action
     cancelButton.setOnAction(e ->  showAdmin(primaryStage));

    // Layout for the delete form
    VBox deleteLayout = new VBox(10, idLabel, idField, deleteButton, cancelButton);
    deleteLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene deleteScene = new Scene(deleteLayout, 1080,720);
    primaryStage.setTitle("Delete Librarian");
    primaryStage.setScene(deleteScene);
    primaryStage.show();
}
    public void searchlibrarian (Stage primaryStage) 
    {
    Label searchLabel = new Label("Search Librarian (by ID, Name, or Email):");
    TextField searchField = new TextField();
    Button searchButton = new Button("Search");
    Button cancelButton = new Button("Cancel");
  
    searchButton.setOnAction(e -> {
        Librarian.readfile();
        String searchTerm = searchField.getText();
        boolean found = false;
       for (User librarian : Librarian.librarianmap.values()) 
    {
        if (String.valueOf(librarian.getId()).equals(searchTerm) || librarian.getName().equalsIgnoreCase(searchTerm) || 
            librarian.getEmail().equalsIgnoreCase(searchTerm)) 
        {
              Librarian.searchLibrarian(searchTerm); 
               
             showMessage(primaryStage,"Librarian found:\nID: " + librarian.getId() + "\nName: " + librarian.getName() + 
                                    "\nEmail: " + librarian.getEmail(),evt->showAdminLibrarian(primaryStage));
             
                found = true;
                break; 
        }
        
    if (!found) 
    {
        showMessage(primaryStage,"No Librarian found with the given search term.",evt->showAdminLibrarian(primaryStage));
    }
    }
     
    });
 
     cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    VBox layout = new VBox(10);
    layout.getChildren().addAll(searchLabel, searchField, searchButton,cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Search Librarian");
    primaryStage.setScene(scene);
    primaryStage.show();
} 
    public void LibrarianMaxBorrowings (Stage primaryStage) 
    {
      
    Button submitButton = new Button("Max Borrow count of Librarian ");
    Button cancelButton = new Button("Cancel");
    
    submitButton.setOnAction(e -> {
        Librarian.readfile();
    if( Librarian.librarianmap.isEmpty())
       {
       showMessage(primaryStage,"No Librarian available to evaluate.",evt->showAdminLibrarian(primaryStage));
        return;
       }      
    Librarian.maxborrowcount();
    Librarian maxBorrower = null;
    for (Librarian librarian : Librarian.librarianmap.values()) 
    {
        if (maxBorrower == null || librarian.getBorrowcount() > maxBorrower.getBorrowcount()) 
        {
            maxBorrower = librarian;
        }
    }
    if (maxBorrower != null)
    {
        showMessage(primaryStage,"Librarian with the highest borrowing count:"+ 
                "\nID: " + maxBorrower.getId()+
                "\nName: " + maxBorrower.getName()+
                "\nEmail: " + maxBorrower.getEmail()+
                "\nBorrowing Counter: " + maxBorrower.getBorrowcount()+
                "\nTotal Revenue: $" + maxBorrower.getRevenue(),evt->showAdminLibrarian(primaryStage));
    }
    });
      
    cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    VBox layout = new VBox(10);
    layout.getChildren().addAll(submitButton  ,cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Max Borrow Count of Librarian");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
    public void LibrarianMaxRevenue(Stage primaryStage)
    {
    Button submitButton = new Button("Max Revenue of Librarian ");
    Button cancelButton = new Button("Cancel");
    
    submitButton.setOnAction(e -> {
    Librarian.readfile();
    if (Librarian.librarianmap.isEmpty()) 
    {
        showMessage(primaryStage,"No Librarian available to evaluate.",evt->showAdminLibrarian(primaryStage));
        return;
    }
    
    Librarian.maxtotalrevenue();
    Librarian maxRevenueBorrower = null;
    for (Librarian librarian : Librarian.librarianmap.values())
    {
            if (maxRevenueBorrower == null || librarian.getRevenue() > maxRevenueBorrower.getRevenue())
        {
            maxRevenueBorrower = librarian;
        }
    }
    if (maxRevenueBorrower != null)
    {
        showMessage(primaryStage,"Librarian with the highest total revenue:"+
          "\nID: " + maxRevenueBorrower.getId()+
          "\nName: " + maxRevenueBorrower.getName()+
          "\nEmail: " + maxRevenueBorrower.getEmail()+
          "\nBorrowcount: " + maxRevenueBorrower.getBorrowcount()+
          "\nTotal Revenue: $" + maxRevenueBorrower.getRevenue(),evt->showAdminLibrarian(primaryStage));
    }
         
     }); 
     
     
    cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    VBox layout = new VBox(10);
    layout.getChildren().addAll(submitButton  ,cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Max Revenue of Librarian");
    primaryStage.setScene(scene);
    primaryStage.show();
     
   } 
    
    
    //Borrower Menu
    private  void showAdminBorrower (Stage primaryStage )
    {
     primaryStage.setTitle("Admin manage Borrower");
     Button addBorrowerButton = new Button("Add Borrower");
     
     
      Button editBorrowerButton = new Button("Edit Borrower ");
      Button deleteBorrowerButton = new Button("Delete Borrower ");
      Button searchBorrowerButton = new Button("Search Borrower ");
      
      Button maxBorrowingBorrowerButton = new Button("Max Borrow Count Borrower ");
      Button maxRevenueBorrowerButton = new Button("Max Revenue Borrower ");
       
   
     Button BackButton = new Button("Back");
     double buttonWidth = 200;
     addBorrowerButton.setPrefWidth(buttonWidth);
     editBorrowerButton.setPrefWidth(buttonWidth);
     deleteBorrowerButton.setPrefWidth(buttonWidth);
     searchBorrowerButton.setPrefWidth(buttonWidth);
     maxBorrowingBorrowerButton.setPrefWidth(buttonWidth);
     maxRevenueBorrowerButton.setPrefWidth(buttonWidth);
     BackButton.setPrefWidth(buttonWidth);
    addBorrowerButton.setOnAction(e ->addborrower(primaryStage) );
    editBorrowerButton.setOnAction(e ->editborrower(primaryStage) );
    deleteBorrowerButton.setOnAction(e ->deleteborrower(primaryStage) );
    searchBorrowerButton.setOnAction(e ->searchborrower(primaryStage) );
    
    maxBorrowingBorrowerButton.setOnAction(e ->BorrowerMaxBorrowings(primaryStage));
    maxRevenueBorrowerButton.setOnAction(e ->BorrowerMaxRevenue(primaryStage));
      
     BackButton.setOnAction(e ->showAdmin(primaryStage));
     VBox layout = new VBox(10);
        layout.getChildren().addAll
        (
                addBorrowerButton,
                editBorrowerButton,
                deleteBorrowerButton,
                searchBorrowerButton,
                maxBorrowingBorrowerButton,
                maxRevenueBorrowerButton,
                BackButton
        );
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: lightblue ;"); 
        Scene x = new Scene(layout, 1080,720);
        primaryStage.setScene(x);
        primaryStage.show();
}
    //Borrower Functions 
    private void addborrower(Stage primaryStage)
    {
    Label idLabel = new Label("ID:");
    TextField idField = new TextField();
    
    Label nameLabel = new Label("Name:");
    TextField nameField = new TextField();
    
    Label emailLabel = new Label("Email:");
    TextField emailField = new TextField();
    
    Label passwordLabel = new Label("Password:");
    PasswordField passwordField = new PasswordField();
    
    Button submitButton = new Button("Add  Borrower");
    Button cancelButton = new Button("Cancel");
    
    submitButton.setOnAction(e -> {
        Borrower.readfile();
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showMessage(primaryStage, "All fields are required!",evt->showAdminBorrower(primaryStage));
                return;
            }
          boolean isAdded = Borrower.addborrower(id, name, email, password,0,0);
            if (isAdded) 
            {
                showMessage(primaryStage, "Borrower added successfully!",evt->showAdminBorrower(primaryStage));
                start(primaryStage);
            } 
            else 
            {
                showMessage(primaryStage, "Borrower with this ID already exists.",evt->showAdminBorrower(primaryStage));
                return;
            }
        } 
        catch (NumberFormatException ex) 
        {
            showMessage(primaryStage, "Invalid ID. Please enter a numeric value.",evt->showAdminBorrower(primaryStage));
        }
    });
    cancelButton.setOnAction(e ->  showAdmin(primaryStage)); 
    VBox formLayout = new VBox(10, idLabel, idField, nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField, submitButton, cancelButton);
    formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene formScene = new Scene(formLayout,1080,720);
    primaryStage.setTitle("Add Borrower");
    primaryStage.setScene(formScene);
    primaryStage.show();
}
    public void editborrower (Stage primaryStage) 
    {
    Label idLabel = new Label("Borrower ID:");
    TextField idField = new TextField();
    Label nameLabel = new Label("New Name (optional):");
    TextField nameField = new TextField();
    Label emailLabel = new Label("New Email (optional):");
    TextField emailField = new TextField();
    Label passwordLabel = new Label("New Password (optional):");
    PasswordField passwordField = new PasswordField();
    Button submitButton = new Button("Edit Borrower");
    Button cancelButton = new Button("Cancel");
    submitButton.setOnAction(e -> {
        Borrower.readfile();
        try {
            int borrowerid = Integer.parseInt(idField.getText());
            String newName = nameField.getText();
            String newEmail = emailField.getText();
            String newPassword = passwordField.getText();

            if (!Borrower.boorrowermap.containsKey(borrowerid))
            {
                showMessage(primaryStage, "No data found for ID " + borrowerid + ".",evt->showAdminBorrower(primaryStage));
                return;
            }

            Borrower borrower = Borrower.boorrowermap.get(borrowerid);

            if (newName != null && !newName.isEmpty()) {
                borrower.setName(newName);
            }

            if (newEmail != null && !newEmail.isEmpty()) {
                borrower.setEmail(newEmail);
            }

            if (newPassword != null && !newPassword.isEmpty()) {
                borrower.setPassword(newPassword);
            }

           // Borrower.boorrowermap.put(borrowerid , borrower);
           Borrower.editborrower(borrowerid, newName, newEmail, newPassword, borrowerid, borrowerid);
           // Borrower.savefile();
            showMessage(primaryStage, "Borrower data updated successfully.",evt->showAdminBorrower(primaryStage));
        } catch (NumberFormatException ex) {
            showMessage(primaryStage, "Invalid Borrower ID. Please enter a valid number.",evt->showAdminBorrower(primaryStage));
        }
    });

    cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    // Layout for the form
    VBox formLayout = new VBox(10, idLabel, idField, nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField, submitButton, cancelButton);
    formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene formScene = new Scene(formLayout, 1080,720);
    primaryStage.setTitle("Edit Borrower");
    primaryStage.setScene(formScene);
    primaryStage.show();
}
    public void deleteborrower(Stage primaryStage)
    {
    // Labels and fields for Admin ID input
    Label idLabel = new Label("ID:");
    TextField idField = new TextField();
    Button deleteButton = new Button("Delete");
    Button cancelButton = new Button("Cancel");

    // Set delete button action
    deleteButton.setOnAction(e -> {
        Borrower.readfile();
        try {
            int borrowerid = Integer.parseInt(idField.getText());
            if (!Borrower.boorrowermap.containsKey(borrowerid)) {
                showMessage(primaryStage, "No data found for ID " + borrowerid + ".",evt->showAdminBorrower(primaryStage));
                return;
            }

            // Delete the borrower
            Borrower.deleteborrower(borrowerid);
            showMessage(primaryStage, "Borrower with ID " + borrowerid + " has been deleted.",evt->showAdminBorrower(primaryStage));
        } 
        catch (NumberFormatException ex) 
        {
            showMessage(primaryStage, "Invalid input. Please enter a valid numeric ID.",evt->showAdminBorrower(primaryStage));
        }
    });

    // Set cancel button action
     cancelButton.setOnAction(e ->  showAdmin(primaryStage));

    // Layout for the delete form
    VBox deleteLayout = new VBox(10, idLabel, idField, deleteButton, cancelButton);
    deleteLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene deleteScene = new Scene(deleteLayout, 1080,720);
    primaryStage.setTitle("Delete Borrower");
    primaryStage.setScene(deleteScene);
    primaryStage.show();
}
    public void searchborrower (Stage primaryStage) 
    {
    Label searchLabel = new Label("Search Borrower (by ID, Name, or Email):");
    TextField searchField = new TextField();
    Button searchButton = new Button("Search");
    Button cancelButton = new Button("Cancel");
  
    searchButton.setOnAction(e -> {
        Borrower.readfile();
        String searchTerm = searchField.getText();
        boolean found = false;
       for (User borrower : Borrower.boorrowermap.values()) 
    {
        if (String.valueOf(borrower.getId()).equals(searchTerm) || borrower.getName().equalsIgnoreCase(searchTerm) || 
            borrower.getEmail().equalsIgnoreCase(searchTerm)) 
        {
             Borrower.searchBorrower(searchTerm); 
             showMessage(primaryStage,"Borrower found:\nID: " + borrower.getId() + "\nName: " + borrower.getName() + 
                                    "\nEmail: " + borrower.getEmail(),evt->showAdminBorrower(primaryStage));
                found = true;
                break; 
        }
    if (!found) 
    {
        showMessage(primaryStage,"No Borrower found with the given search term.",evt->showAdminBorrower(primaryStage));
    }
    }
     
    });
    
     cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    VBox layout = new VBox(10);
    layout.getChildren().addAll(searchLabel, searchField, searchButton,cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Search Borrower");
    primaryStage.setScene(scene);
    primaryStage.show();
}
    public void BorrowerMaxBorrowings (Stage primaryStage) 
    {
      
    Button submitButton = new Button("Max Borrow count of Borrower ");
    Button cancelButton = new Button("Cancel");
    
    submitButton.setOnAction(e -> {
        Borrower.readfile();
       if( Borrower.boorrowermap.isEmpty())
       {
       showMessage(primaryStage,"No Borrower available to evaluate.",evt->showAdminBorrower(primaryStage));
        return;
       }
       
    Borrower.maxborrowcount();
    Borrower maxBorrower = null;
    for (Borrower borrower : Borrower.boorrowermap.values()) 
    {
        if (maxBorrower == null || borrower.getBorroweringcounter()> maxBorrower.getBorroweringcounter()) 
        {
            maxBorrower = borrower;
        }
    }
    if (maxBorrower != null)
    {
        showMessage(primaryStage,"Borrower with the highest borrowing count:"+ 
                "\nID: " + maxBorrower.getId()+
                "\nName: " + maxBorrower.getName()+
                "\nEmail: " + maxBorrower.getEmail()+
                "\nBorrowing Counter: " + maxBorrower.getBorroweringcounter()+
                "\nTotal Revenue: $" + maxBorrower.getTotalrevenue(),evt->showAdminBorrower(primaryStage));
    }
    });
      
    cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    VBox layout = new VBox(10);
    layout.getChildren().addAll(submitButton  ,cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout,1080,720);
    primaryStage.setTitle("Max Borrow Count of Librarian");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
    public void BorrowerMaxRevenue(Stage primaryStage)
    {
    Button submitButton = new Button("Max Revenue of Borrower ");
    Button cancelButton = new Button("Cancel");
    
    submitButton.setOnAction(e -> {
    Borrower.readfile();
    if (Borrower.boorrowermap.isEmpty()) 
    {
        showMessage(primaryStage,"No Borrower available to evaluate.",evt->showAdminBorrower(primaryStage));
        return;
    }
    
    Borrower.maxtotalrevenue();
    Borrower maxRevenueBorrower = null;
    for (Borrower borrower : Borrower.boorrowermap.values())
    {
            if (maxRevenueBorrower == null || borrower.getTotalrevenue()> maxRevenueBorrower.getTotalrevenue())
        {
            maxRevenueBorrower = borrower;
        }
    }
    if (maxRevenueBorrower != null)
    {
        showMessage(primaryStage,"borrower with the highest total revenue:"+
          "\nID: " + maxRevenueBorrower.getId()+
          "\nName: " + maxRevenueBorrower.getName()+
          "\nEmail: " + maxRevenueBorrower.getEmail()+
          "\nBorrowcount: " + maxRevenueBorrower.getBorroweringcounter()+
          "\nTotal Revenue: $" + maxRevenueBorrower.getTotalrevenue(),evt->showAdminBorrower(primaryStage));
    }
         
     }); 
     
     
    cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    VBox layout = new VBox(10);
    layout.getChildren().addAll(submitButton  ,cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Max Revenue of Librarian");
    primaryStage.setScene(scene);
    primaryStage.show();
     
   }
    
    //BookMenu
    private  void showAdminBook (Stage primaryStage )
    {
     primaryStage.setTitle("Admin manage Book");
     Button addBookButton = new Button("Add Book");
     Button editBookButton = new Button("Edit Book ");
     Button deleteBookButton = new Button("Delete Book ");
     Button searchBookButton = new Button("Search Book ");
     Button displayBookButton = new Button(" Display Books ");
     Button maxRevenueButton = new Button(" Max Revenue Book ");
     Button maxBorrowCountButton = new Button(" Max Borrow Count Book ");
     Button mostRevenueBookOnDateButton = new Button(" Most Revenue Book On Date");
     Button mostBorrowedBookOnDate = new Button(" Most Borrowed Book On Date");
     Button BackButton = new Button("Back");
     double buttonWidth = 200;
      addBookButton.setPrefWidth(buttonWidth);
      editBookButton.setPrefWidth(buttonWidth);
      deleteBookButton.setPrefWidth(buttonWidth);
      searchBookButton.setPrefWidth(buttonWidth);
      BackButton.setPrefWidth(buttonWidth);
      displayBookButton.setPrefWidth(buttonWidth);
      maxRevenueButton.setPrefWidth(buttonWidth);
      maxBorrowCountButton.setPrefWidth(buttonWidth);
      mostRevenueBookOnDateButton.setPrefWidth(buttonWidth);
      mostBorrowedBookOnDate.setPrefWidth(buttonWidth);
      addBookButton.setOnAction(e ->addBook(primaryStage) );
      editBookButton.setOnAction(e ->editBook(primaryStage) );
      deleteBookButton.setOnAction(e ->removeBook(primaryStage) );
      searchBookButton.setOnAction(e ->searchBooks(primaryStage) );
    displayBookButton.setOnAction(e ->  displayBook(primaryStage));
    maxRevenueButton.setOnAction(e ->maxRevenue(primaryStage));
    maxBorrowCountButton.setOnAction(e ->maxBorrowCount(primaryStage));
    mostRevenueBookOnDateButton.setOnAction(e->mostRevenueBookOnDate(primaryStage));
    mostBorrowedBookOnDate.setOnAction(e->mostBorrowedBookOnDate(primaryStage));
     BackButton.setOnAction(e -> showAdmin(primaryStage));
     VBox layout = new VBox(10);
        layout.getChildren().addAll
        (
                addBookButton,
                editBookButton,
                deleteBookButton,
                searchBookButton,
                displayBookButton,
                maxRevenueButton,
                maxBorrowCountButton,
                mostBorrowedBookOnDate,
                mostRevenueBookOnDateButton,
                BackButton
        );
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: lightblue ;"); 
        Scene x = new Scene(layout, 1080,720);
        primaryStage.setScene(x);
        primaryStage.show();
}
    //Book Functions
    private void addBook(Stage primaryStage)
    {
    Label idLabel = new Label("ID:");
    TextField idField = new TextField();
    Label titleLabel = new Label("title:");
    TextField titleField = new TextField();
    Label authorLabel = new Label("author:");
    TextField authorField = new TextField();
    Label categoryLabel = new Label("category:");
    TextField categoryField = new TextField();
    Label priceLabel = new Label("Price:");
    TextField priceField = new TextField();
    Label copiesLabel = new Label("Copies:");
    TextField copiesField = new TextField();
    
    Button submitButton = new Button("Add  Book");
    Button cancelButton = new Button("Cancel");
    submitButton.setOnAction(e -> {
        try {
            Book.readFile();
            int id = Integer.parseInt(idField.getText());
            String title = titleField.getText();
            String author = authorField.getText();
            String category = categoryField.getText();
            
            // not sure 
            double price = Double.parseDouble(priceField.getText());
            int copies = Integer.parseInt(copiesField.getText());
             
            if (title.isEmpty() || author.isEmpty() || category.isEmpty()) {
                showMessage(primaryStage, "All fields are required!",evt->showAdminBook(primaryStage));
                return;
            }
         
            boolean isAdded =  Book.addBook(id, title, author, category, price, copies,0,0,0.0,0);
            if (isAdded) 
            {
                showMessage(primaryStage, "Book added successfully!",evt->showAdminBook(primaryStage));
            } 
            else 
            {
                showMessage(primaryStage, "Book with this ID already exists.",evt->showAdminBook(primaryStage));
                return;
            }
        } 
        catch (NumberFormatException ex) 
        {
            showMessage(primaryStage, "Invalid ID. Please enter a numeric value.",evt->showAdminBook(primaryStage));
        }
    });
    cancelButton.setOnAction(e ->  showAdmin(primaryStage)); 
    VBox formLayout = new VBox(10, idLabel, idField, titleLabel, titleField, authorLabel, authorField, categoryLabel, categoryField,priceLabel,priceField,copiesLabel,copiesField, submitButton, cancelButton);
    formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene formScene = new Scene(formLayout, 1080,720);
    primaryStage.setTitle("Add Book");
    primaryStage.setScene(formScene);
    primaryStage.show();
}
    public void editBook (Stage primaryStage) 
    {
    Label idLabel = new Label("Book ID:");
    TextField idField = new TextField();
    Label titleLabel = new Label("New title (optional):");
    TextField titleField = new TextField();
    Label authorLabel = new Label("New author (optional):");
    TextField authorField = new TextField();
    Label categoryLabel = new Label("New category (optional):");
    TextField categoryField = new TextField();
    
    // copies &price
    
    Button submitButton = new Button("Edit Book");
    Button cancelButton = new Button("Cancel");
    submitButton.setOnAction(e -> {
        try {
            Book.readFile();
            int bookid = Integer.parseInt(idField.getText());
            String newtitle = titleField.getText();
            String newauthor = authorField.getText();
            String newcategory = categoryField.getText();

            if (!Book.map.containsKey(bookid))
            {
                showMessage(primaryStage, "No data found for ID " + bookid + ".",evt->showAdminBook(primaryStage));
                return;
            }

            Book book = Book.map.get(bookid);

            if (newtitle != null && !newtitle.isEmpty())
            {
                book.setTitle(newtitle);
            }

            if (newauthor != null && !newauthor.isEmpty()) {
                book.setAuthor(newauthor);
            }

            if (newcategory != null && !newcategory.isEmpty()) {
                book.setCategory(newcategory);
            }

           // Book.map.put(bookid , book);
           Book.editBook(bookid, newtitle, newauthor, newcategory, 0.0, 0, 0, 0, 0.0, 0);
           // Book.saveFile();
            showMessage(primaryStage, "Book data updated successfully.",evt->showAdminBook(primaryStage));
        } 
        catch (NumberFormatException ex)
        {
            showMessage(primaryStage, "Invalid Book ID. Please enter a valid number.",evt->showAdminBook(primaryStage));
        }
    });

    cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    // Layout for the form
    VBox formLayout = new VBox(10, idLabel, idField, titleLabel, titleField, authorLabel, authorField, categoryLabel, categoryField, submitButton, cancelButton);
    formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene formScene = new Scene(formLayout, 1080,720);
    primaryStage.setTitle("Edit Book");
    primaryStage.setScene(formScene);
    primaryStage.show();
}
    public void removeBook(Stage primaryStage)
    {
    // Labels and fields for Admin ID input
    Label idLabel = new Label("ID:");
    TextField idField = new TextField();
    Button deleteButton = new Button("Delete");
    Button cancelButton = new Button("Cancel");

    // Set delete button action
    deleteButton.setOnAction(e -> {
        try {
            Book.readFile();
            int bookid = Integer.parseInt(idField.getText());
            if (!Book.map.containsKey(bookid)) {
                showMessage(primaryStage, "No data found for ID " + bookid + ".",evt->showAdminBook(primaryStage));
                return;
            }

            // Delete the book
            Book.removeBook(bookid);
            showMessage(primaryStage, "Book with ID " + bookid + " has been deleted.",evt->showAdminBook(primaryStage));
        } 
        catch (NumberFormatException ex) 
        {
            showMessage(primaryStage, "Invalid input. Please enter a valid numeric ID.",evt->showAdminBook(primaryStage));
        }
    });

    // Set cancel button action
     cancelButton.setOnAction(e ->  showAdmin(primaryStage));

    // Layout for the delete form
    VBox deleteLayout = new VBox(10, idLabel, idField, deleteButton, cancelButton);
    deleteLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene deleteScene = new Scene(deleteLayout, 1080,720);
    primaryStage.setTitle("Delete Book");
    primaryStage.setScene(deleteScene);
    primaryStage.show();
}
    public void searchBooks (Stage primaryStage) 
    {
    Book.readFile();
    Label searchLabel = new Label("Search Book (by ID, Title , Author , or Category ):");
    TextField searchField = new TextField();
    Button searchButton = new Button("Search");
    Button cancelButton = new Button("Cancel");
  
    searchButton.setOnAction(e -> {
        String searchTerm = searchField.getText();
        boolean found = false;
       for (Book book : Book.map.values()) 
    {
        if (String.valueOf(book.getBookId()).equals(searchTerm) ||book.getTitle().equalsIgnoreCase(searchTerm)|| book.getAuthor().equalsIgnoreCase(searchTerm) || 
            book.getCategory().equalsIgnoreCase(searchTerm)) 
        {
            Book.searchBooks(searchTerm); 
             showMessage(primaryStage,"Book found:\nID: " + book.getBookId()+ "\nTitle: " + book.getTitle() + 
                                    "\nAuthor: " + book.getAuthor()+ "\nCategory: "+ book.getCategory()+
                                    "\nCopies Available:" + book.getCopiesAvailability(),evt->showAdminBook(primaryStage));
                found = true;
                break; 
        }
    if (!found) 
    {
        showMessage(primaryStage,"No Book found with the given search term.",evt->showAdminBook(primaryStage));
    }
    }
     
    });
    
     cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    VBox layout = new VBox(10);
    layout.getChildren().addAll(searchLabel, searchField, searchButton,cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Search Book");
    primaryStage.setScene(scene);
    primaryStage.show();
}
    public void displayBook(Stage primaryStage)
    {
   
     Button displayButton = new Button("Display");
     Button cancelButton = new Button("Cancel");
      
    displayButton.setOnAction(e -> {
        Book.readFile();
        if (Book.map.isEmpty()) 
        {
            showMessage(primaryStage,"No books available.",evt->showAdminBook(primaryStage));
        } 
        else 
        {  
            Book.displayBooks();    
            showMessage(primaryStage,"Book List: ",evt->showAdminBook(primaryStage));
            for (Book book : Book.map.values()) 
            {
           
           showMessage(primaryStage,"ID: " + book.getBookId() +
                        "\n Title: " + book.getTitle() +
                        "\n Author: " + book.getAuthor() +
                        " \nCategory: " + book.getCategory() +
                        " \nPrice: $" + book.getPrice() +
                        " \nAvailable Copies: " + book.getCopiesAvailability() +
                        " \nBorrowed Copies: " + book.getCopiesBorrowed() +
                        " \nBorrow Count: " + book.getBorrowCount() +
                        " \nRevenue: $" + book.getRevenue(),evt->showAdminBook(primaryStage)); 
            }
        }
     });
    
    cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    
    VBox layout = new VBox(10);
    
    layout.getChildren().addAll( displayButton ,cancelButton);
    
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Display Book");
    primaryStage.setScene(scene);
    primaryStage.show();
 }
    public void updateRevenue(Stage primaryStage)
    {
    Label idLabel = new Label("Book ID:");
    TextField idField = new TextField();
    Button submitButton = new Button(" Update Revenue of Book ");
    Button cancelButton = new Button("Cancel");
    
     submitButton.setOnAction(e -> {
        Book.readFile();
        int bookid = Integer.parseInt(idField.getText());
        Book book = Book.map.get(bookid);
        double updatedRevenue = book.getBorrowCount() * book.getPrice();
        
        book.setRevenue(updatedRevenue); 
        showMessage(primaryStage, "Revenue for Book ID " + bookid + " updated: $" + updatedRevenue,evt->showAdminBook(primaryStage));
     });
     
    cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    
    VBox layout = new VBox(10);
    
    layout.getChildren().addAll(submitButton  ,cancelButton);
    
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle(" Updated revenue of Book");
    primaryStage.setScene(scene);
    primaryStage.show();
     
 }
    public void maxRevenue(Stage primaryStage)
    {
    Button submitButton = new Button("Max Revenue of Book ");
    Button cancelButton = new Button("Cancel");
    
     submitButton.setOnAction(e -> {
       Book.readFile();
       if (Book.map.isEmpty()) 
       {
        showMessage(primaryStage,"No books available.",evt->showAdminBook(primaryStage));
        return;
       }
       
    Book maxRevenueBook = null;
    Book.maxRevenue();
    for (Book book :Book.map.values()) 
    {
        if (maxRevenueBook == null || book.getRevenue() > maxRevenueBook.getRevenue())
        {
            maxRevenueBook = book;
        }
    }
    
    showMessage(primaryStage,"Book with Maximum Revenue:",evt->showAdminBook(primaryStage));
    if (maxRevenueBook != null)
    {
          showMessage(primaryStage,"ID: " + maxRevenueBook.getBookId() +
                "\n Title: " + maxRevenueBook.getTitle() +
                "\n Category: " + maxRevenueBook.getCategory() +
                "\n Price: $" + maxRevenueBook.getPrice() +
                "\n Available Copies: " + maxRevenueBook.getCopiesAvailability() +
                "\n Borrowed Copies: " + maxRevenueBook.getCopiesBorrowed() +
                "\n Borrow Count: " + maxRevenueBook.getBorrowCount() +
                "\n Revenue: $" + maxRevenueBook.getRevenue(),evt->showAdminBook(primaryStage));
    }
     });
     
     
    cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    
    VBox layout = new VBox(10);
    
    layout.getChildren().addAll(submitButton  ,cancelButton);
    
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle(" Max revenue of Book");
    primaryStage.setScene(scene);
    primaryStage.show();
    
 }
    public void maxBorrowCount(Stage primaryStage)
    {
    Button submitButton = new Button("Max Borrow count of Book ");
    Button cancelButton = new Button("Cancel");
    
    submitButton.setOnAction(e -> {
      Book.readFile();
      if (Book.map.isEmpty()) 
      {
        showMessage(primaryStage,"No books available.",evt->showAdminBook(primaryStage));
        return;
      }
      
    Book.findMaxBorrowCount();
    Book maxBorrowCountBook = null;

    for (Book book : Book.map.values())
    {
        if (maxBorrowCountBook == null || book.getBorrowCount() > maxBorrowCountBook.getBorrowCount())
        {
            maxBorrowCountBook = book;
        }
     }

     showMessage(primaryStage,"Book with Maximum Borrow Count:",evt->showAdminBook(primaryStage));
    if (maxBorrowCountBook != null) 
    {
         showMessage(primaryStage,"ID: " + maxBorrowCountBook.getBookId() +
                "\n Title: " + maxBorrowCountBook.getTitle() +
                "\n Category: " + maxBorrowCountBook.getCategory() +
                "\n Price: $" + maxBorrowCountBook.getPrice() +
                "\n Available Copies: " + maxBorrowCountBook.getCopiesAvailability() +
                "\n Borrowed Copies: " + maxBorrowCountBook.getCopiesBorrowed() +
                "\n Borrow Count: " + maxBorrowCountBook.getBorrowCount() +
                "\n Revenue: $" + maxBorrowCountBook.getRevenue(),evt->showAdminBook(primaryStage));
        
    }
    });
    
    cancelButton.setOnAction(e ->  showAdmin(primaryStage));
    VBox layout = new VBox(10);
    layout.getChildren().addAll(submitButton  ,cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Max Borrow Count of Book");
    primaryStage.setScene(scene);
    primaryStage.show();
    
 }
    private void mostRevenueBookOnDate(Stage primaryStage) 
    {
    Label dateLabel = new Label("Enter Date (YYYY-MM-DD):");
    TextField dateField = new TextField();
    Button searchButton = new Button("Search");
    Button cancelButton = new Button("Cancel");

    searchButton.setOnAction(e -> {
        String specificDate = dateField.getText();
        Borrowing.mostRevenueBookOnDate(specificDate);
        showMessage(primaryStage, "Results printed to console.",evt->showBorrowingMenu(primaryStage)); 
    });

    cancelButton.setOnAction(e -> showAdminBook(primaryStage)); 

    VBox layout = new VBox(10);
    layout.getChildren().addAll(dateLabel, dateField, searchButton, cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");

    Scene scene = new Scene(layout, 400, 200);
    primaryStage.setTitle("Most Revenue Book");
    primaryStage.setScene(scene);
    primaryStage.show();
}
    private void mostBorrowedBookOnDate(Stage primaryStage) 
    {
    Label dateLabel = new Label("Enter Date (YYYY-MM-DD):");
    TextField dateField = new TextField();
    Button searchButton = new Button("Search");
    Button cancelButton = new Button("Cancel");

    searchButton.setOnAction(e -> {
        String specificDate = dateField.getText();
        Borrowing.mostBorrowedBookOnDate(specificDate); 
        showMessage(primaryStage, "Results printed to console.",evt->showAdminBook(primaryStage));
    });

    cancelButton.setOnAction(e -> showAdminBook(primaryStage)); 

    VBox layout = new VBox(10);
    layout.getChildren().addAll(dateLabel, dateField, searchButton, cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");

    Scene scene = new Scene(layout, 400, 200);
    primaryStage.setTitle("Most Borrowed Book");
    primaryStage.setScene(scene);
    primaryStage.show();
}
    //Borrowing Menu
    public void showBorrowingMenu(Stage primaryStage) 
    {
        Button borrowingDetailsButton = new Button("Borrowing Details");
        Button borrowingperborrowerButton = new Button("No. Borrowing Per Borrower");
        Button borrowingperlibrarianButton = new Button("No. Borrowing Per Librarian");
        Button averagetotalrevenuetimeButton = new Button("Average Total Revenue Time");
         Button searchBorrowingsByDateButton = new Button("Search Borrowings By Date");
        Button BackButton = new Button("Back");
        BackButton.setOnAction(e -> start(primaryStage));
        double buttonWidth = 200;
        borrowingDetailsButton.setPrefWidth(buttonWidth);
        borrowingperborrowerButton.setPrefWidth(buttonWidth);
        borrowingperlibrarianButton.setPrefWidth(buttonWidth);  
        averagetotalrevenuetimeButton.setPrefWidth(buttonWidth);
        searchBorrowingsByDateButton.setPrefWidth(buttonWidth);
        BackButton.setPrefWidth(buttonWidth);
        
        borrowingDetailsButton.setOnAction(e -> BorrowingDetails(primaryStage));
        borrowingperborrowerButton.setOnAction(e -> borrowingsperborrower(primaryStage));
        borrowingperlibrarianButton.setOnAction(e -> borrowingsperlibrarian(primaryStage));
        averagetotalrevenuetimeButton.setOnAction(e->showTotalAndAverageRevenue(primaryStage));
        searchBorrowingsByDateButton.setOnAction(e->searchBorrowingsByDate(primaryStage));
        BackButton.setOnAction(e ->  showAdmin(primaryStage));
            VBox layout = new VBox(10);
            layout.getChildren().addAll
            (
                  borrowingDetailsButton,
                  borrowingperborrowerButton,
                  borrowingperlibrarianButton,
                  searchBorrowingsByDateButton,
                  averagetotalrevenuetimeButton,
                  BackButton
            );

        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: Lightblue ;"); 
        Scene x = new Scene(layout, 1080, 720);
        primaryStage.setScene(x);
        primaryStage.show();
    }
    //Borrowing Functiions
    public void BorrowingDetails(Stage primaryStage)
    {
   
     Button displayButton = new Button("Display Borrowing details");
     Button cancelButton = new Button("Cancel");
      
    displayButton.setOnAction(e -> {
        Borrowing.readfile();
        if (Borrowing.borrowinggMap.isEmpty()) 
        {
            showMessage(primaryStage,"No borrowing available.",evt->showBorrowingMenu(primaryStage));
        } 
        else 
        {  
            Borrowing.displayBorrowing();    
            showMessage(primaryStage,"borrowing List: ",evt->showBorrowingMenu(primaryStage));
            for (Borrowing borrowing : Borrowing.borrowinggMap.values()) 
            {
           
           showMessage(primaryStage, "Librarian ID: "+ borrowing.getLibrarianId()+
                    "Borrowing ID: " + borrowing.getBorrowingId() +
                    ", Borrower ID: " + borrowing.getBorrowerId() +
                    ", Book ID: " + borrowing.getBookId() +
                    ", Borrowing Date: " + borrowing.getBorrowingDate() +
                    ", Return Date: " + borrowing.getReturnDate() +
                    ", Returned: " + borrowing.isReturned() +
                    ", Rate: " + borrowing.getRate(),evt->showBorrowingMenu(primaryStage));
            }
        }
     });
    
    cancelButton.setOnAction(e ->  showBorrowingMenu(primaryStage));
    
    VBox layout = new VBox(10);
    
    layout.getChildren().addAll( displayButton ,cancelButton);
    
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 400, 300);
    primaryStage.setTitle("Display Borrowing details");
    primaryStage.setScene(scene);
    primaryStage.show();
 }
    public void borrowingsperlibrarian (Stage primaryStage)
    { 
    Label idLabel = new Label("ID:");
    TextField idField = new TextField();
 
    Button submitButton = new Button("Borrowings Per Librarian");
    Button cancelButton = new Button("Cancel");
    
    submitButton.setOnAction(e -> {
        
     int librarianId = Integer.parseInt(idField.getText());
      boolean found = false;
      
     Borrowing.LibrarianBorrowings(librarianId);
    Librarian librarian = Librarian.librarianmap.get(librarianId);
    
    showMessage(primaryStage,"Librarian Borrowing History for Librarian ID: " + librarianId+
                              "\nTotal Borrow Count: " + librarian.getBorrowcount(),evt->showBorrowingMenu(primaryStage));
    
    for (Borrowing borrow :Borrowing.borrowinggMap.values()) 
    {
        if (borrow.getLibrarianId() == librarianId) 
        {
            found = true;
        showMessage(primaryStage, "Borrowing ID: " + borrow.getBorrowingId()+
           "\nBook ID: " + borrow.getBookId()+
           "\nBorrowing Date: " + borrow.getBorrowingDate()+
           "\nReturn Date: " + borrow.getReturnDate()+
           "\nReturned: " + (borrow.isReturned() ? "Yes" : "No")+
           "\nRate: " + borrow.getRate(),evt->showBorrowingMenu(primaryStage));
        }
    }
    if (!found) 
    {
         showMessage(primaryStage,"No borrowing history found for Librarian ID: " + librarianId,evt->showBorrowingMenu(primaryStage));
    }
   });
    
    cancelButton.setOnAction(e ->  showBorrowingMenu(primaryStage)); 
    VBox formLayout = new VBox(10, idLabel, idField , submitButton, cancelButton);
    formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightyellow;");
    Scene formScene = new Scene(formLayout, 400, 300);
    primaryStage.setTitle("Librarian Borrowings");
    primaryStage.setScene(formScene);
    primaryStage.show();
    
   }
    public void borrowingsperborrower (Stage primaryStage)
    { 
       Label idLabel = new Label("ID:");
       TextField idField = new TextField();
       
    Button submitButton = new Button("Borrowings Per borrower");
    Button cancelButton = new Button("Cancel");
    
    submitButton.setOnAction(e -> {
      Borrower.readfile();
     int borrowerId = Integer.parseInt(idField.getText());
      boolean found = false;
      
     Borrowing.borrowingHistory(borrowerId);
    Borrower borrower = Borrower.boorrowermap.get(borrowerId);
    
    showMessage(primaryStage,"Borrower Borrowing History for borrower ID: " + borrowerId+
                              "\nTotal Borrow Count: " + borrower.getBorroweringcounter(),evt->showBorrowingMenu(primaryStage));
    
    for (Borrowing borrow :Borrowing.borrowinggMap.values()) 
    {
        if (borrow.getBorrowerId() == borrowerId) 
        {
            found = true;
        showMessage(primaryStage, "Borrowing ID: " + borrow.getBorrowingId()+
           "\nBook ID: " + borrow.getBookId()+
           "\nBorrowing Date: " + borrow.getBorrowingDate()+
           "\nReturn Date: " + borrow.getReturnDate()+
           "\nReturned: " + (borrow.isReturned() ? "Yes" : "No")+
           "\nRate: " + borrow.getRate(),evt->showBorrowingMenu(primaryStage));
        }
    }
    if (!found) 
    {
         showMessage(primaryStage,"No borrowing history found for BORROWER ID: " + borrowerId,evt->showBorrowingMenu(primaryStage));
    }
   });
    
    cancelButton.setOnAction(e ->  showBorrowingMenu(primaryStage)); 
    VBox formLayout = new VBox(10, idLabel, idField , submitButton, cancelButton);
    formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightyellow;");
    Scene formScene = new Scene(formLayout, 400, 300);
    primaryStage.setTitle("Borrower Borrowings");
    primaryStage.setScene(formScene);
    primaryStage.show();
    
   }
    public void searchBorrowingsByDate(Stage primaryStage) 
    {
    Label dateLabel = new Label("Enter Date (YYYY-MM-DD):");
    TextField dateField = new TextField();
    Button searchButton = new Button("Search");
    Button cancelButton = new Button("Cancel");

    searchButton.setOnAction(e -> {
        Borrowing.readfile();
        String date = dateField.getText().trim();
        boolean found = false;

        for (Borrowing borrow : Borrowing.borrowinggMap.values()) {
            if (borrow.getBorrowingDate().equals(date)) {
                showMessage(primaryStage, "Borrowing ID: " + borrow.getBorrowingId() + "\n" +
                        "Borrower ID: " + borrow.getBorrowerId() + "\n" +
                        "Book ID: " + borrow.getBookId() + "\n" +
                        "Borrowing Date: " + borrow.getBorrowingDate() + "\n" +
                        "Return Date: " + borrow.getReturnDate() + "\n" +
                        "Returned: " + (borrow.isReturned() ? "Yes" : "No"),evt->showBorrowingMenu(primaryStage));
                found = true; 
            }
        }

        if (!found) {
            showMessage(primaryStage, "No borrowings found for the date.",evt->showBorrowingMenu(primaryStage));
        }
    });

    cancelButton.setOnAction(e -> showBorrowingMenu(primaryStage)); 

    VBox layout = new VBox(10);
    layout.getChildren().addAll(dateLabel, dateField, searchButton, cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");

    Scene scene = new Scene(layout,1080,720);
    primaryStage.setTitle("Search Borrowings by Date");
    primaryStage.setScene(scene);
    primaryStage.show();
}
    private void showTotalAndAverageRevenue(Stage primaryStage) 
    {
    // Label for Date Input
    Label dateLabel = new Label("Enter the date (DD-MM-YYYY):");

    // TextField for Date Input
    TextField dateField = new TextField();
    dateField.setPrefWidth(300);
    dateField.setPromptText("Enter date in format DD-MM-YYYY");

    // Button to Calculate Revenue
    Button calculateButton = new Button("Calculate Revenue");
    Button backButton = new Button("Back");
    
    // Styling Buttons
    calculateButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;");
    backButton.setStyle("-fx-font-size: 14px; -fx-background-color: blue; -fx-text-fill: white;");

    // Add hover effects
    calculateButton.setOnMouseEntered(e -> calculateButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkgreen; -fx-text-fill: white;"));
    calculateButton.setOnMouseExited(e -> calculateButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;"));

    backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkblue; -fx-text-fill: white;"));
    backButton.setOnMouseExited(e -> backButton.setStyle("-fx-font-size: 14px; -fx-background-color: blue; -fx-text-fill: white;"));

    // TextArea to display results
    TextArea resultArea = new TextArea();
    resultArea.setEditable(false);
    resultArea.setPrefSize(400, 200);
    resultArea.setPromptText("Results will be displayed here...");

    // Action handler for Calculate Button
    calculateButton.setOnAction(e -> {
        String specificDate = dateField.getText().trim();
        if (specificDate.isEmpty()) {
            resultArea.setText("Please enter a valid date.");
            return;
        }

        // Call the method from Borrowing class and capture the result
        String result = Borrowing.TotalAndAverageRevenueDate(specificDate);

        // Set the result in the TextArea
        resultArea.setText(result);
    });

    // Back Button Action Handler
    backButton.setOnAction(e -> showBorrowingMenu(primaryStage));

    // Layout
    VBox layout = new VBox(10, dateLabel, dateField, calculateButton, backButton, resultArea);
    layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: lightblue;");

    // Create and set the scene
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Total and Average Revenue");
    primaryStage.setScene(scene);
    primaryStage.show();
}
    //order menu
    private void showOrderMenu(Stage primaryStage){
         primaryStage.setTitle("Order Menu");
         Button creatOrderButton=new Button("Creat Order");
         Button updateOrderButton = new Button("Update Order");
         Button cancelOrderButton = new Button("Cancel Order");
         Button viewOrderButton = new Button("View Order Details");
         Button BackButton = new Button("Back");
         double width= 150;
         creatOrderButton.setPrefWidth(width);
         updateOrderButton.setPrefWidth(width);
         cancelOrderButton.setPrefWidth(width);
         viewOrderButton.setPrefWidth(width);
         BackButton.setPrefWidth(width);
         creatOrderButton.setOnAction(e -> creatorder(primaryStage));
         updateOrderButton.setOnAction(e -> updateorder(primaryStage));
         cancelOrderButton.setOnAction(e -> cancleorder(primaryStage));
         viewOrderButton.setOnAction (e -> viewOrderDetails(primaryStage));
         BackButton.setOnAction(e->showAdmin(primaryStage));
          VBox layout = new VBox(10);
         layout.getChildren().addAll
        (
         creatOrderButton,
         updateOrderButton,
         cancelOrderButton,
         viewOrderButton,
         BackButton
        
        );
         layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: lightblue ;"); 
        Scene x = new Scene(layout, 1080,720);
        primaryStage.setScene(x);
        primaryStage.show();
     }
    //order functions
    public void creatorder(Stage primaryStage){
   Label supplierIdLabel = new Label("Enter Supplier ID:");
    TextField supplierIdField = new TextField();
    Label supplierContactLabel = new Label("Enter Supplier Contact Number:");
    TextField supplierContactField = new TextField();
    Label orderIdLabel = new Label("Enter Order ID:");
    TextField orderIdField = new TextField();
    Label orderDateLabel = new Label("Enter Order Date:");
    TextField orderDateField = new TextField();
    Label totalAmountLabel = new Label("Enter Total Amount:");
    TextField totalAmountField = new TextField();
    Button createOrderButton = new Button("Create Order");
    Button cancelButton = new Button("Cancel");
    
       createOrderButton.setOnAction (e -> {
       try{
            int supplierId = Integer.parseInt(supplierIdField.getText());
            int supplierContact = Integer.parseInt(supplierContactField.getText());
            int orderId = Integer.parseInt(orderIdField.getText());
            String orderDate = orderDateField.getText();
            double totalAmount = Double.parseDouble(totalAmountField.getText());
            
           if (!Supplier.suppmap.containsKey(supplierId)) 
              {
                showMessage(primaryStage,"Supplier ID " + supplierId + " not Found",evt->showOrderMenu(primaryStage));
              }
           
           if (Order.orderMap.containsKey(orderId)) {
             showMessage (primaryStage,"Order ID " + orderId + " already exists.",evt->showOrderMenu(primaryStage));
            }
           
              Order newOrder = new Order(orderId, supplierId, supplierContact, orderDate, false, totalAmount);
              Order.orderMap.put(orderId, newOrder);
            
              Supplier supplier = Supplier.suppmap.get(supplierId);
              supplier.setOrdercount(supplier.getOrdercount() + 1);
              supplier.setRevenue(supplier.getRevenue() + totalAmount); 
              Supplier.savefile();
              
              showMessage(primaryStage,"Order created successfully.",evt->showOrderMenu(primaryStage));
                  
        } catch (NumberFormatException ex) {
           showMessage(primaryStage,"Invalid input. Please enter correct values.",evt->showOrderMenu(primaryStage));
        }
    });
    cancelButton.setOnAction(e -> showOrderMenu(primaryStage));
       
    VBox layout = new VBox(10);
    layout.getChildren().addAll(supplierIdLabel, supplierIdField, supplierContactLabel, supplierContactField,
                                orderIdLabel, orderIdField,orderDateLabel, orderDateField,
                                totalAmountLabel, totalAmountField,  createOrderButton, cancelButton);

    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");

    Scene formScene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Create Order");
    primaryStage.setScene(formScene);
    primaryStage.show();
}
    public void updateorder(Stage primaryStage) {
  
        Label orderIdLabel = new Label("Enter Order ID:");
        TextField orderIdField = new TextField();
        
        Label supplierIdLabel = new Label("Enter Supplier ID:");
        TextField supplierIdField = new TextField();

        Label supplierContactLabel = new Label("Enter Supplier Contact Number:");
        TextField supplierContactField = new TextField();
        
        Label orderDateLabel = new Label("Enter Order Date:");
        TextField orderDateField = new TextField();
        
        Label totalAmountLabel = new Label("Enter Total Amount:");
        TextField totalAmountField = new TextField();
        
        Button updateOrderButton = new Button("Update Order");
        Button cancelButton = new Button("Cancel");
        
         updateOrderButton.setOnAction(e -> {
            try {
                int orderId = Integer.parseInt(orderIdField.getText());
                int supplierId = Integer.parseInt(supplierIdField.getText());
                int supplierContact = Integer.parseInt(supplierContactField.getText());
                String orderDate = orderDateField.getText();
                double totalAmount = Double.parseDouble(totalAmountField.getText());
                
                if (!Order.orderMap.containsKey(orderId)) {
                    showMessage(primaryStage, "Order ID " + orderId + " not found.",evt->showOrderMenu(primaryStage));
                    return;
                }
                if (!Supplier.suppmap.containsKey(supplierId)) {
                    showMessage(primaryStage, "Supplier ID " + supplierId + " not found.",evt->showOrderMenu(primaryStage));
                    return;
                }
                
                Order order = Order.orderMap.get(orderId);
                order.setSupplierContact(supplierContact);
                order.setOrderDate(orderDate);
                order.setTotalAmount(totalAmount);

                Supplier supplier = Supplier.suppmap.get(supplierId);
                supplier.setRevenue(supplier.getRevenue() + totalAmount);
                Supplier.savefile();
                showMessage(primaryStage, "Order updated successfully.",evt->showOrderMenu(primaryStage));

            } catch (NumberFormatException ex) {
                showMessage(primaryStage, "Invalid input. Please enter correct values.",evt->showOrderMenu(primaryStage));
            }
        });
         
          cancelButton.setOnAction(e -> showOrderMenu(primaryStage));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(orderIdLabel, orderIdField, supplierIdLabel, supplierIdField,
                                    supplierContactLabel, supplierContactField, orderDateLabel, orderDateField,
                                    totalAmountLabel, totalAmountField, updateOrderButton, cancelButton);

        layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
        
        Scene formScene = new Scene(layout, 1080,720);
        primaryStage.setTitle("Update Order");
        primaryStage.setScene(formScene);
        primaryStage.show();
}
    public void cancleorder(Stage primaryStage) {
       
        Label orderIdLabel = new Label("Enter Order ID to Cancel:");
        TextField orderIdField = new TextField();
        
        Button cancelOrderButton = new Button("Cancel Order");
        Button cancelButton = new Button("Cancel");

        cancelOrderButton.setOnAction(e -> {
            try {
                int orderId = Integer.parseInt(orderIdField.getText());

                if (!Order.orderMap.containsKey(orderId)) {
                    showMessage(primaryStage, "Order ID " + orderId + " not found.",evt->showOrderMenu(primaryStage));
                    return;
                }

                Order order = Order.orderMap.get(orderId);
                order.setStatus(true); 

                showMessage(primaryStage, "Order ID " + orderId + " has been canceled successfully.",evt->showOrderMenu(primaryStage));

            } catch (NumberFormatException ex) {
                showMessage(primaryStage, "Invalid input. Please enter a valid order ID.",evt->showOrderMenu(primaryStage));
            }
        });

        cancelButton.setOnAction(e -> showOrderMenu(primaryStage));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(orderIdLabel, orderIdField, cancelOrderButton, cancelButton);

        layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");

        Scene formScene = new Scene(layout,1080,720);
        primaryStage.setTitle("Cancel Order");
        primaryStage.setScene(formScene);
        primaryStage.show();
    }
    public void viewOrderDetails(Stage primaryStage) {
      
        Label orderIdLabel = new Label("Enter Order ID:");
        TextField orderIdField = new TextField();
        
        Button viewOrderButton = new Button("View Order Details");
        Button cancelButton = new Button("Cancel");
        
        Label supplierIdLabel = new Label("Supplier ID: ");
        Label orderIdDisplayLabel = new Label("Order ID: ");
        Label supplierContactLabel = new Label("Supplier Contact: ");
        Label orderDateLabel = new Label("Order Date: ");
        Label totalAmountLabel = new Label("Total Amount: ");
        
        viewOrderButton.setOnAction(e -> {
            try {
                int orderId = Integer.parseInt(orderIdField.getText());

                if (!Order.orderMap.containsKey(orderId)) {
                    showMessage(primaryStage, "Order ID " + orderId + " not found.",evt->showOrderMenu(primaryStage));
                    return;
                }

                Order order = Order.orderMap.get(orderId);

                supplierIdLabel.setText("Supplier ID: " + order.getSupplierId());
                orderIdDisplayLabel.setText("Order ID: " + order.getOrderId());
                supplierContactLabel.setText("Supplier Contact: " + order.getSupplierContact());
                orderDateLabel.setText("Order Date: " + order.getOrderDate());
                totalAmountLabel.setText("Total Amount: " + order.getTotalAmount());

            } catch (NumberFormatException ex) {
                showMessage(primaryStage, "Invalid input. Please enter a valid order ID.",evt->showOrderMenu(primaryStage));
            }
        });

        cancelButton.setOnAction(e -> showOrderMenu(primaryStage));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(orderIdLabel, orderIdField, viewOrderButton, cancelButton, 
                                    supplierIdLabel, orderIdDisplayLabel, supplierContactLabel, 
                                    orderDateLabel, totalAmountLabel);

        layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");

        Scene formScene = new Scene(layout, 1080,720);
        primaryStage.setTitle("View Order Details");
        primaryStage.setScene(formScene);
        primaryStage.show();
    }
      
    //Supplier Menu
    private  void showAdminSupplier (Stage primaryStage ){
     primaryStage.setTitle("Admin manage Supplier");
     Button addSupplierButton = new Button("Add Supplier");
      Button editSupplierButton = new Button("Edit Supplier ");
      Button deleteSupplierButton = new Button("Delete Supplier ");
      Button searchSupplierButton = new Button("Search Supplier");
      Button maxRevenueSupplierButton = new Button("Max Revenue Supplier");
      Button maxOrderCounterSupplier = new Button("Max order Supplier");
     Button BackButton = new Button("Back");
     double buttonWidth = 150;
     addSupplierButton.setPrefWidth(buttonWidth);
        editSupplierButton.setPrefWidth(buttonWidth);
        deleteSupplierButton.setPrefWidth(buttonWidth);
        searchSupplierButton.setPrefWidth(buttonWidth);
        maxRevenueSupplierButton.setPrefWidth(buttonWidth);
        maxOrderCounterSupplier.setPrefWidth(buttonWidth);
        BackButton.setPrefWidth(buttonWidth);
     
      addSupplierButton.setOnAction(e ->addSupplier(primaryStage) );
      editSupplierButton.setOnAction(e ->editSupplier(primaryStage) );
     deleteSupplierButton.setOnAction(e ->deleteSupplier(primaryStage) );
     searchSupplierButton.setOnAction(e ->searchSupplier(primaryStage) );
     maxRevenueSupplierButton.setOnAction(e ->maxRevenueSupplier(primaryStage) );
     maxOrderCounterSupplier.setOnAction(e ->maxOrderCountSupplier(primaryStage) );
     BackButton.setOnAction(e ->showAdmin (primaryStage));
     VBox layout = new VBox(10);
        layout.getChildren().addAll
        (
                addSupplierButton,
                editSupplierButton,
                deleteSupplierButton,
                searchSupplierButton,
                maxRevenueSupplierButton,
                maxOrderCounterSupplier,
                BackButton
        );
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: lightblue ;"); 
        Scene x = new Scene(layout, 1080,720);
        primaryStage.setScene(x);
        primaryStage.show();
}
    //Supplier Functions
    private void addSupplier(Stage primaryStage) {
    
    Label idLabel = new Label("ID:");
    TextField idField = new TextField();

    Label nameLabel = new Label("Name:");
    TextField nameField = new TextField();

    Label phoneLabel = new Label("Phone Number:");
    TextField phoneField = new TextField();

    Button submitButton = new Button("Add Supplier");
    Button cancelButton = new Button("Cancel");

    
    submitButton.setOnAction(e -> {
        Supplier.readfile();
     try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int phone = Integer.parseInt(idField.getText());
            if ( name.isEmpty()) {
                showMessage(primaryStage, "All fields are required!",evt->showAdminSupplier(primaryStage));
                return;
            }

            boolean isAdded = Supplier.add(id, name, phone);
            if (isAdded) {
                showMessage(primaryStage, "Supplier added successfully!",evt->showAdminSupplier(primaryStage));
                start(primaryStage); 
            } else {
                showMessage(primaryStage, "Supplier with this ID already exists!",evt->showAdminSupplier(primaryStage));
            }
        } catch (NumberFormatException ex) {
            showMessage(primaryStage, "Please enter valid numeric values for ID and Phone!",evt->showAdminSupplier(primaryStage));
        }
    });

    cancelButton.setOnAction(e -> showAdmin(primaryStage));

    
    VBox formLayout = new VBox(10,
            idLabel, idField,
            nameLabel, nameField,
            phoneLabel, phoneField,
            submitButton, cancelButton
    );
    formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");

    
    Scene formScene = new Scene(formLayout, 1080,720);
    primaryStage.setTitle("Add Supplier");
    primaryStage.setScene(formScene);
    primaryStage.show();
}
    public void editSupplier(Stage primaryStage) {
    
    Label idLabel = new Label("Supplier ID:");
    TextField idField = new TextField();

    Label nameLabel = new Label("New Name (optional):");
    TextField nameField = new TextField();

    Label phoneLabel = new Label("New Phone Number (optional):");
    TextField phoneField = new TextField();

    Label orderCountLabel = new Label("New Order Count (optional):");
    TextField orderCountField = new TextField();

    Label revenueLabel = new Label("New Revenue (optional):");
    TextField revenueField = new TextField();

    Button submitButton = new Button("Edit Supplier");
    Button cancelButton = new Button("Cancel");

    
    submitButton.setOnAction(e -> {
        Supplier.readfile();
        try {
            int supplierId = Integer.parseInt(idField.getText());
            String newName = nameField.getText();
            String newPhone = phoneField.getText();
            String newOrderCount = orderCountField.getText();
            String newRevenue = revenueField.getText();

            if (!Supplier.suppmap.containsKey(supplierId)) {
                showMessage(primaryStage, "No data found for ID " + supplierId + ".",evt->showAdminSupplier(primaryStage));
                return;
            }

            Supplier.editSupplier(supplierId, newName, newPhone, newOrderCount, newRevenue);
            showMessage(primaryStage, "Supplier data updated successfully.",evt->showAdminSupplier(primaryStage));
            start(primaryStage); 
        } catch (NumberFormatException ex) {
            showMessage(primaryStage, "Invalid Supplier ID. Please enter a valid number.",evt->showAdminSupplier(primaryStage));
        }
    });

    cancelButton.setOnAction(e ->showAdmin (primaryStage));

    
    VBox formLayout = new VBox(10,
            idLabel, idField,
            nameLabel, nameField,
            phoneLabel, phoneField,
            orderCountLabel, orderCountField,
            revenueLabel, revenueField,
            submitButton, cancelButton
    );
    formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");

    
    Scene formScene = new Scene(formLayout,1080,720);
    primaryStage.setTitle("Edit Supplier");
    primaryStage.setScene(formScene);
    primaryStage.show();
}
    private void deleteSupplier(Stage primaryStage) {
    
    Label idLabel = new Label("Supplier ID:");
    TextField idField = new TextField();
    Button deleteButton = new Button("Delete");
    Button cancelButton = new Button("Cancel");

   
    deleteButton.setOnAction(e -> {
        Supplier.readfile();
        try {
            int supplierId = Integer.parseInt(idField.getText());
            if (!Supplier.suppmap.containsKey(supplierId)) {
                showMessage(primaryStage, "No data found for Supplier ID " + supplierId + ".",evt->showAdminSupplier(primaryStage));
                return;
            }

            
            Supplier.deleteSupplier(supplierId);
            showMessage(primaryStage, "Supplier with ID " + supplierId + " has been deleted.",evt->showAdminSupplier(primaryStage));
        } catch (NumberFormatException ex) {
            showMessage(primaryStage, "Invalid input. Please enter a valid numeric Supplier ID.",evt->showAdminSupplier(primaryStage));
        }
    });

    
    cancelButton.setOnAction(e -> showAdmin(primaryStage)); 

    
    VBox deleteLayout = new VBox(10, idLabel, idField, deleteButton, cancelButton);
    deleteLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene deleteScene = new Scene(deleteLayout,1080,720);
    primaryStage.setTitle("Delete Supplier");
    primaryStage.setScene(deleteScene);
    primaryStage.show();
}
    public void searchSupplier(Stage primaryStage) {
    
    Label searchLabel = new Label("Search Supplier (by ID, Name, or Phone):");
    TextField searchField = new TextField();
    Button searchButton = new Button("Search");
    Button cancelButton = new Button("Cancel");

    searchButton.setOnAction(e -> {
        Supplier.readfile();
        String searchTerm = searchField.getText();
        boolean found = false;

        
        for (Supplier supplier : Supplier.suppmap.values()) {
            if (String.valueOf(supplier.getSuppid()).equals(searchTerm) || 
                supplier.getNamesupp().equalsIgnoreCase(searchTerm) || 
                String.valueOf(supplier.getPhoneno()).equals(searchTerm)) {
                showMessage(primaryStage, "Supplier found:\nID: " + supplier.getSuppid() + 
                            "\nName: " + supplier.getNamesupp() + 
                            "\nPhone: " + supplier.getPhoneno() + 
                            "\nOrders: " + supplier.getOrdercount() + 
                            "\nRevenue: " + supplier.getRevenue(),evt->showAdminSupplier(primaryStage));
                found = true;
                break;
            }
        }

        if (!found) {
            showMessage(primaryStage, "No supplier found with the given search term.",evt->showAdminSupplier(primaryStage));
        }
    });

    cancelButton.setOnAction(e -> showAdmin(primaryStage)); // Navigate to supplier menu

    
    VBox layout = new VBox(10, searchLabel, searchField, searchButton, cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Search Supplier");
    primaryStage.setScene(scene);
    primaryStage.show();
}
    public void maxRevenueSupplier(Stage primaryStage) {
    Button submitButton = new Button("Max Revenue Supplier");
    Button cancelButton = new Button("Cancel");

    submitButton.setOnAction(e -> {
        Supplier.MaxRevenueSupplier(); 

        if (Supplier.suppmap.isEmpty()) {
            showMessage(primaryStage, "No suppliers available.",evt->showAdminSupplier(primaryStage));
            return;
        }

        Supplier maxRevenueSupplier = null;
        for (Supplier supplier : Supplier.suppmap.values()) {
            if (maxRevenueSupplier == null || supplier.getRevenue() > maxRevenueSupplier.getRevenue()) {
                maxRevenueSupplier = supplier;
            }
        }

        if (maxRevenueSupplier != null) {
            showMessage(primaryStage, "Supplier with Maximum Revenue:\n" +
                    "ID: " + maxRevenueSupplier.getSuppid() + "\n" +
                    "Name: " + maxRevenueSupplier.getNamesupp() + "\n" +
                    "Phone: " + maxRevenueSupplier.getPhoneno() + "\n" +
                    "Order Count: " + maxRevenueSupplier.getOrdercount() + "\n" +
                    "Revenue: $" + maxRevenueSupplier.getRevenue(),evt->showAdminSupplier(primaryStage));
        } else {
            showMessage(primaryStage, "No suppliers available to evaluate.",evt->showAdminSupplier(primaryStage));
        }
    });

    cancelButton.setOnAction(e -> showAdmin(primaryStage));

    VBox layout = new VBox(10);
    layout.getChildren().addAll(submitButton, cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);

    primaryStage.setTitle("Max Revenue Supplier");
    primaryStage.setScene(scene);
    primaryStage.show();
}
    public void maxOrderCountSupplier(Stage primaryStage) {
    Button submitButton = new Button("Max Order Count of Supplier");
    Button cancelButton = new Button("Cancel");

    submitButton.setOnAction(e -> {
        if (Supplier.suppmap.isEmpty()) {
            showMessage(primaryStage, "No suppliers available.",evt->showAdminSupplier(primaryStage));
            return;
        }
        
        Supplier.MaxOrderCountSupplier();
        Supplier maxOrderSupplier = null;
        

        for (Supplier supplier : Supplier.suppmap.values()) {
            if (maxOrderSupplier == null || supplier.getOrdercount() > maxOrderSupplier.getOrdercount()) {
                maxOrderSupplier = supplier;
            }
        }

        showMessage(primaryStage, "Supplier with Maximum Order Count:",evt->showAdminSupplier(primaryStage));
        if (maxOrderSupplier != null) {
            showMessage(primaryStage, "ID: " + maxOrderSupplier.getSuppid() +
                    "\nName: " + maxOrderSupplier.getNamesupp() +
                    "\nPhone: " + maxOrderSupplier.getPhoneno() +
                    "\nOrder Count: " + maxOrderSupplier.getOrdercount() +
                    "\nRevenue: $" + maxOrderSupplier.getRevenue(),evt->showAdminSupplier(primaryStage));
        }
    });

    cancelButton.setOnAction(e -> showAdmin(primaryStage));

    VBox layout = new VBox(10);
    layout.getChildren().addAll(submitButton, cancelButton);
    layout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: lightblue;");
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Max Order Count of Supplier");
    primaryStage.setScene(scene);
    primaryStage.show();
}   
   
    //Borrower Menu
    private void showBorrowerMenu(Stage primaryStage) {
    // Create a label to display the borrower's name
    Borrower borrower = new Borrower(0,"","","",0,0);
    Label welcomeLabel = new Label("Welcome, " + borrower.getName() + "!");
    
    // Create a button to view borrowing history
    Button viewHistoryButton = new Button("View Borrowing History");
    viewHistoryButton.setOnAction(e -> showBorrowingHistory(primaryStage));
    // Create a button to rate a book
    Button rateBookButton = new Button("Rate a Book");
    rateBookButton.setOnAction(e -> rateBook(primaryStage));
    // Create a logout button
    Button logoutButton = new Button("Logout");
    logoutButton.setOnAction(e -> start(primaryStage));
    // Layout for the borrower's menu
    VBox borrowerLayout = new VBox(15, welcomeLabel, viewHistoryButton, rateBookButton, logoutButton);
    borrowerLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: lightblue;");

    // Create and set the scene
    Scene borrowerScene = new Scene(borrowerLayout, 1080,720);
    primaryStage.setTitle("Borrower Menu");
    primaryStage.setScene(borrowerScene);
    primaryStage.show();
}

    //Borrower Function
    private void showBorrowingHistory(Stage primaryStage) {
    // Label for Borrower ID
    Label idLabel = new Label("Enter Borrower ID to view borrowing history:");    
    // TextField for Borrower ID
    TextField idField = new TextField();
    idField.setPrefWidth(250);
    idField.setPromptText("Enter Borrower ID...");
    
    // TextArea for displaying results
    TextArea resultArea = new TextArea();
    resultArea.setEditable(false);
    resultArea.setWrapText(true);
    resultArea.setPrefHeight(150);

    // Buttons
    Button submitButton = new Button("Submit");
    Button backButton = new Button("Back");
    Button cancelButton = new Button("Cancel");
    
    // Styling Buttons
    submitButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;");
    cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;");
    backButton.setStyle("-fx-font-size: 14px; -fx-background-color: blue; -fx-text-fill: white;");

    // Add hover effects
    submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkgreen; -fx-text-fill: white;"));
    submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;"));

    cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkred; -fx-text-fill: white;"));
    cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;"));

    backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkblue; -fx-text-fill: white;"));
    backButton.setOnMouseExited(e -> backButton.setStyle("-fx-font-size: 14px; -fx-background-color: blue; -fx-text-fill: white;"));

    // Action handlers
    submitButton.setOnAction(e -> {
        String borrowerIdInput = idField.getText();
        if (!borrowerIdInput.isEmpty()) {
            int borrow = Integer.parseInt(borrowerIdInput);
            String result = Borrower.ViewBorrowingHistory(borrow);
            resultArea.setText(result != null && !result.isEmpty() ? result : "No borrowing history found.");
        } else {
            resultArea.setText("Please enter a valid Borrower ID.");
        }
    });

    cancelButton.setOnAction(e -> idField.clear());
    backButton.setOnAction(e -> showBorrowerMenu(primaryStage));

    // Layout
    VBox layout = new VBox(10, idLabel, idField, resultArea, submitButton, cancelButton, backButton);
    layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: lightblue;");
    
    Scene scene = new Scene(layout, 1080,720);
    primaryStage.setTitle("Borrowing History");
    primaryStage.setScene(scene);
    primaryStage.show();
}
    private void rateBook(Stage primaryStage) {
    // Label for Book ID
    Label bookIdLabel = new Label("Enter Book ID to rate:");

    // TextField for Book ID
    TextField bookIdField = new TextField();
    bookIdField.setPrefWidth(250);
    bookIdField.setPromptText("Enter Book ID...");

    // Label for Rating
    Label ratingLabel = new Label("Enter your rating (1-5):");

    // TextField for Rating
    TextField ratingField = new TextField();
    ratingField.setPrefWidth(250);
    ratingField.setPromptText("Enter rating (1-5)");

    // Buttons
    Button submitButton = new Button("Submit");
    Button backButton = new Button("Back");
    Button cancelButton = new Button("Cancel");

    // Styling Buttons
    submitButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;");
    cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;");
    backButton.setStyle("-fx-font-size: 14px; -fx-background-color: blue; -fx-text-fill: white;");

    // Add hover effects
    submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkgreen; -fx-text-fill: white;"));
    submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;"));

    cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkred; -fx-text-fill: white;"));
    cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;"));

    backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkblue; -fx-text-fill: white;"));
    backButton.setOnMouseExited(e -> backButton.setStyle("-fx-font-size: 14px; -fx-background-color: blue; -fx-text-fill: white;"));

    // Action handlers
    submitButton.setOnAction(e -> {
    try {
        int bookId = Integer.parseInt(bookIdField.getText().trim());
        int rating = Integer.parseInt(ratingField.getText().trim());

        Book.readFile();
        if (Book.map == null || !Book.map.containsKey(bookId)) {
            showMessage(primaryStage, "Book not found. Please check the Book ID.", evt -> showBorrowerMenu(primaryStage));
            return;
        }

        if (rating >= 1 && rating <= 5) {
            boolean success = Borrowing.rateBook(bookId);
            if (success) {
                showMessage(primaryStage, "Thank you for rating the book!", evt -> showBorrowerMenu(primaryStage));
            } else {
                showMessage(primaryStage, "Failed to rate the book. Please check the borrowing details.", evt -> showBorrowerMenu(primaryStage));
            }
        } else {
            showMessage(primaryStage, "Invalid rating. Please enter a number between 1 and 5.", evt -> showBorrowerMenu(primaryStage));
        }
    } catch (NumberFormatException ex) {
        showMessage(primaryStage, "Invalid input. Please enter numeric values only.", evt -> showBorrowerMenu(primaryStage));
    } catch (Exception ex) {
        ex.printStackTrace(); // Log the error
        showMessage(primaryStage, "An unexpected error occurred: " + ex.getMessage(), evt -> showBorrowerMenu(primaryStage));
    }
});


    cancelButton.setOnAction(e -> {
        bookIdField.clear();
        ratingField.clear();
    });
    backButton.setOnAction(e -> showBorrowerMenu(primaryStage));

    // Layout
    VBox layout = new VBox(10, bookIdLabel, bookIdField, ratingLabel, ratingField, submitButton, cancelButton, backButton);
    layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: lightblue;");

    Scene scene = new Scene(layout, 1080, 720);
    primaryStage.setTitle("Rate Book");
    primaryStage.setScene(scene);
    primaryStage.show();
}

    //Librarian menu
   public void showLibrarianMenu(Stage primaryStage) 
   {
        Button createBorrowingButton = new Button("Create Borrowing");
        Button cancelBorrowingButton = new Button("Cancel Borrowing");
        Button calculatePaymentButton = new Button("Calculate payment");
        Button searchBookButton = new Button("Search Book");
        Button logoutButton = new Button("logout");
        logoutButton.setOnAction(e -> start(primaryStage));
        double buttonWidth = 150;
        createBorrowingButton.setPrefWidth(buttonWidth);
        searchBookButton.setPrefWidth(buttonWidth);
        cancelBorrowingButton.setPrefWidth(buttonWidth);  
        calculatePaymentButton.setPrefWidth(buttonWidth);
        logoutButton.setPrefWidth(buttonWidth);
        createBorrowingButton.setOnAction(e -> createBorrowing(primaryStage));
        searchBookButton.setOnAction(e -> Booksearch(primaryStage));
        cancelBorrowingButton.setOnAction(e -> cancelBorrowing(primaryStage));
        calculatePaymentButton.setOnAction(e->calculatePayment(primaryStage));
        logoutButton.setOnAction(e ->  start(primaryStage));
            VBox layout = new VBox(10);
            layout.getChildren().addAll
            (
                  createBorrowingButton,
                  cancelBorrowingButton,
                  searchBookButton,
                  calculatePaymentButton,
                  logoutButton
            );

        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: Lightblue ;"); 
        Scene x = new Scene(layout, 1080, 720);
        primaryStage.setScene(x);
        primaryStage.show();
    }
   //librarian functions   
   private void createBorrowing(Stage primaryStage) {
    // Create fields for borrowing details
    Label librarianLabel = new Label("Librarian ID:");
    TextField librarianField = new TextField();
    librarianField.setMinWidth(400); // Minimum width
    librarianField.setPrefWidth(400); // Preferred width   
    librarianField.setMaxWidth(400);
    Label borrowingLabel = new Label("Borrowing Number:");
    TextField borrowingField = new TextField();
    borrowingField.setMinWidth(400); // Minimum width
    borrowingField.setPrefWidth(400); // Preferred width   
    borrowingField.setMaxWidth(400);
    Label borrowerIdLabel = new Label("Borrower ID:");
    TextField borrowerIdField = new TextField();
    borrowerIdField.setMinWidth(400); // Minimum width
    borrowerIdField.setPrefWidth(400); // Preferred width   
    borrowerIdField.setMaxWidth(400);
    Label bookIdLabel = new Label("Book ID:");
    TextField bookIdField = new TextField();
    bookIdField.setMinWidth(400); // Minimum width
    bookIdField.setPrefWidth(400); // Preferred width   
    bookIdField.setMaxWidth(400);
    Label borrowDateLabel = new Label("Borrow Date (YYYY-MM-DD):");
    TextField borrowDateField = new TextField();
    borrowDateField.setMinWidth(400); // Minimum width
    borrowDateField.setPrefWidth(400); // Preferred width   
    borrowDateField.setMaxWidth(400);
    Label returnDateLabel = new Label("Return Date (YYYY-MM-DD):");
    TextField returnDateField = new TextField();
    returnDateField.setMinWidth(400); // Minimum width
    returnDateField.setPrefWidth(400); // Preferred width   
    returnDateField.setMaxWidth(400);
    Button submitButton = new Button("Submit Borrowing");
    Button cancelButton = new Button("Cancel");
    // Submit button action
       submitButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;");
       cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;");
       // Set hover effects for buttons
       submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkgreen; -fx-text-fill: white;"));
       submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;"));

       cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkred; -fx-text-fill: white;"));
       cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;"));
      
    submitButton.setOnAction(e -> {
        try {
            int librarianId = Integer.parseInt(librarianField.getText());
            int borrowingId = Integer.parseInt(borrowingField.getText());
            int borrowerId = Integer.parseInt(borrowerIdField.getText());
            int bookId = Integer.parseInt(bookIdField.getText());
            String borrowDate = borrowDateField.getText();
            String returnDate = returnDateField.getText();

            // Validation: Check if required fields are empty
            if (borrowDate.isEmpty() || returnDate.isEmpty()) 
            {
                showMessage(primaryStage, "Please fill in all fields.",evt -> showLibrarianMenu(primaryStage));
                return;
            }

            // Use the Librarian's createBorrowing method to handle borrowing creation
            boolean success = Librarian.CreateBorrowing(librarianId, borrowingId, borrowerId, bookId, borrowDate, returnDate);
            // Check the result and show appropriate messages
            if (success) 
            {
                showMessage(primaryStage, "Borrowing created successfully!", evt -> showLibrarianMenu(primaryStage));
                
            }
            else 
            {
                showMessage(primaryStage, "Error: Could not create borrowing.", evt -> showLibrarianMenu(primaryStage));
            }

        }
        catch (NumberFormatException ex) 
        {
            showMessage(primaryStage, "Invalid ID format. Please enter numeric values for IDs.", evt -> showLibrarianMenu(primaryStage));
        }
        catch (Exception ex) 
        {
            showMessage(primaryStage, "Error adding borrowing: " + ex.getMessage(), evt -> showLibrarianMenu(primaryStage));
        }
    });

    // Cancel button action
    cancelButton.setOnAction(e -> showLibrarianMenu(primaryStage));  // Go back to the librarian menu

    // Layout the form
    VBox formLayout = new VBox(10, librarianLabel, librarianField, borrowingLabel, borrowingField, borrowerIdLabel, borrowerIdField,
                               bookIdLabel, bookIdField, borrowDateLabel, borrowDateField, returnDateLabel, returnDateField, submitButton, cancelButton);
    formLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: Lightblue;");

    // Create a scene for the form
    Scene formScene = new Scene(formLayout, 1080, 720);
    primaryStage.setTitle("Create Borrowing");
    primaryStage.setScene(formScene);
    primaryStage.show();
}
   private void cancelBorrowing(Stage primaryStage) {
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        idField.setMinWidth(400); // Minimum width
        idField.setPrefWidth(400); // Preferred width   
        idField.setMaxWidth(400);
        Button deleteButton = new Button("CancelBorrowing");
        Button cancelButton = new Button("Cancel");
        deleteButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;");
        // Set hover effects for buttons
        deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkgreen; -fx-text-fill: white;"));
        deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;"));

        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkred; -fx-text-fill: white;"));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;"));
      
        deleteButton.setOnAction(e -> 
        {
            try 
            {
                int BorrowingId = Integer.parseInt(idField.getText());
                if (!Borrowing.borrowinggMap.containsKey(BorrowingId)) 
                {
                    showMessage(primaryStage, "No data found for ID " + BorrowingId + ".",evt->showLibrarianMenu(primaryStage));
                    return;
                }
                // Delete the admin
                Borrowing.cancelBorrowing(BorrowingId);
                showMessage(primaryStage, "Borrowing with ID " + BorrowingId + " has been updated.",evt->showLibrarianMenu(primaryStage));
            }
            catch (NumberFormatException ex) 
            {
                showMessage(primaryStage, "Invalid input. Please enter a valid numeric ID.",evt->showLibrarianMenu(primaryStage));
            }
        });
        // Set cancel button action
        cancelButton.setOnAction(e ->  showLibrarianMenu(primaryStage));
        // Layout for the delete form
        VBox deleteLayout = new VBox(10, idLabel, idField, deleteButton, cancelButton);
        deleteLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: Lightblue;");
        Scene deleteScene = new Scene(deleteLayout,1080, 720);
        primaryStage.setTitle("Cancel Borrowing");
        primaryStage.setScene(deleteScene);
        primaryStage.show();
   }
   private void Booksearch(Stage primaryStage) {
        primaryStage.setTitle("Book Search");

        // Label and TextField for input
        Label searchLabel = new Label("Enter Book ID, Author, or Category:");
        searchLabel.setStyle("-fx-font-size: 14px;");
        TextField searchField = new TextField();
        searchField.setPromptText("Enter search term here...");
        // Buttons for search and cancel
        Button searchButton = new Button("Search");
        Button backButton = new Button("Back");
        Button cancelButton = new Button("Cancel");

        // TextArea to display search results
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false); // Make the result area read-only
        resultArea.setWrapText(true);  // Wrap text for better readability
        resultArea.setPrefHeight(150);

        // Button Styling
        // Button Styling
       searchButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;");
       cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;");
       backButton.setStyle("-fx-font-size: 14px; -fx-background-color: blue; -fx-text-fill: white;");

       // Set hover effects for buttons
       searchButton.setOnMouseEntered(e -> searchButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkgreen; -fx-text-fill: white;"));
       searchButton.setOnMouseExited(e -> searchButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;"));

       cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkred; -fx-text-fill: white;"));
       cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;"));

       backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkblue; -fx-text-fill: white;"));
       backButton.setOnMouseExited(e -> backButton.setStyle("-fx-font-size: 14px; -fx-background-color: blue; -fx-text-fill: white;"));
       
// Set up search button action
        searchButton.setOnAction(e -> {
            String searchTerm = searchField.getText();
            if (!searchTerm.isEmpty()) {
                String result = Admin.searchBook(searchTerm); // Assuming Admin.searchBooks() is your method
                resultArea.setText(result);  // Display the result in the TextArea
            } else {
                resultArea.setText("Please enter a search term.");
            }
        });
        // Set up cancel button action
        cancelButton.setOnAction(e -> searchField.clear());
        backButton.setOnAction(e -> showLibrarianMenu(primaryStage));
        // Layout for the scene
        VBox mainLayout = new VBox(15); // Vertical spacing between elements
        mainLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: Lightblue;");
        mainLayout.setPadding(new javafx.geometry.Insets(20)); // Padding around the entire layout
        mainLayout.setAlignment(Pos.CENTER); // Center align the components
        mainLayout.getChildren().addAll(searchLabel, searchField, searchButton, cancelButton, resultArea,backButton);
        // Create a scene with a slightly larger size
        Scene scene = new Scene(mainLayout, 1080, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
      
}
   private void calculatePayment(Stage primaryStage) {
    primaryStage.setTitle("Calculate Payment");

    // Label and TextField for input
    Label paymentLabel = new Label("Enter Book IDs to Calculate Total Payment:\n \t \t \tFormat:ID,ID,ID");
    paymentLabel.setStyle("-fx-font-size: 14px;");
    
    TextField bookIdsField = new TextField();
    bookIdsField.setPrefWidth(250);
    bookIdsField.setPromptText("Enter book IDs separated by commas Ex.( 101,102,103 )");
    // Buttons for calculate and cancel
    Button calculateButton = new Button("Calculate Payment");
    Button cancelButton = new Button("Cancel");
    Button backButton = new Button("Back");
    // TextArea to display the payment result
    TextArea resultArea = new TextArea();
    resultArea.setEditable(false); // Make the result area read-only
    resultArea.setWrapText(true);  // Wrap text for better readability
    // Button Styling
    calculateButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;");
    cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;");
    backButton.setStyle("-fx-font-size: 14px; -fx-background-color: blue; -fx-text-fill: white;");
    // Set hover effects for buttons
    calculateButton.setOnMouseEntered(e -> calculateButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkgreen; -fx-text-fill: white;"));
    calculateButton.setOnMouseExited(e -> calculateButton.setStyle("-fx-font-size: 14px; -fx-background-color: green; -fx-text-fill: white;"));
    cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkred; -fx-text-fill: white;"));
    cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;"));
    backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-font-size: 14px; -fx-background-color: darkblue; -fx-text-fill: white;"));
    backButton.setOnMouseExited(e -> backButton.setStyle("-fx-font-size: 14px; -fx-background-color: blue; -fx-text-fill: white;"));
    // Set up calculate button action
    calculateButton.setOnAction(e -> {
        String bookIdsInput = bookIdsField.getText();
        if (!bookIdsInput.isEmpty()) {
            String[] bookIdsArray = bookIdsInput.split(",");
            List<Integer> bookIds = new ArrayList<>();
            for (String id : bookIdsArray) {
                try {
                    bookIds.add(Integer.parseInt(id.trim())); // Parse book ID and add to the list
                } catch (NumberFormatException ex) {
                    resultArea.setText("Invalid book ID: " + id);
                    return;
                }
            }           
            // Call the method to calculate payment
            String result = Admin.calculatePayment(bookIds); // Assuming calculatePayment() is a method in Admin class
            resultArea.setText(result);  // Display the result in the TextArea
        } else {
            resultArea.setText("Please enter valid book IDs.");
        }
    });

    // Set up cancel button action
    cancelButton.setOnAction(e -> bookIdsField.clear());  // Clear the input field

    // Set up back button action
    backButton.setOnAction(e -> showLibrarianMenu(primaryStage));  // Assuming this returns to the librarian menu

    // Layout for the scene
    VBox mainLayout = new VBox(15); // Vertical spacing between elements
    mainLayout.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: Lightblue;");
    mainLayout.setPadding(new javafx.geometry.Insets(20)); // Padding around the entire layout
    mainLayout.setAlignment(Pos.CENTER); // Center align the components
    mainLayout.getChildren().addAll(paymentLabel, bookIdsField, calculateButton, cancelButton, resultArea, backButton);

    // Create a scene with a slightly larger size
    Scene scene = new Scene(mainLayout, 1080, 720);
    primaryStage.setScene(scene);
    primaryStage.show();
}

    
}   