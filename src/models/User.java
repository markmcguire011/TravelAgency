package src.models;

public class User {
    private String userName;
    private String firstName;
    private String lastName;
    private String userPassword;
    private boolean isAdmin;

    // Default constructor
    public User() {
        this.userName = "user1";
        this.firstName = "John";
        this.lastName = "Doe";
        this.userPassword = "password";
        this.isAdmin = false;
    }

    public User(String userName, String firstName, String lastName, String userPassword, boolean isAdmin) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userPassword = userPassword;
        this.isAdmin = isAdmin;
    }

    // Getters
    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
