package gr.compassbook.snorechat;

/**
 * Created by Konstantinos
 */
public class User {

    private String username, password, email, lastName, firstName;

    //Full details User constructor
    public User(String username, String lastName, String firstName, String password, String email) {

        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;

        this.email = email;
    }

    //Limited details User constructor
    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public User(String lastName, String firstName, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



}
