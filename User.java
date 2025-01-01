package javalibrary;
public interface User 
{

    // Abstract getter methods
    int getId();

    String getName();

    String getEmail();

    String getPassword();

    // Abstract setter methods
    void setId(int id);

    void setName(String name);

    void setEmail(String email);

    void setPassword(String password);

    // Abstract login method
    boolean login(int id, String password);
}
