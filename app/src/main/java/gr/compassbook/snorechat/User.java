package gr.compassbook.snorechat;

/**
 * Created by Konstantinos
 */
public class User {

    String username, password, email, lastName, firstName;

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

}
