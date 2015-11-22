package gr.compassbook.snorechat;

/**
 * Created by Konstantinos
 */
public class User {

    String username, password, email, country, city;

    //Full details User constructor
    public User(String username, String password, String email, String country, String city) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.country = country;
        this.city = city;
    }

    //Limited details User constructor
    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

}
