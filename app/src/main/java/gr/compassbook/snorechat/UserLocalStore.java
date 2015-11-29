package gr.compassbook.snorechat;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Konstantinos
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    //Define of user local Database
    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    //User local Database constructor
    public void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.putString("email", user.email);
        spEditor.putString("country", user.country);
        spEditor.putString("city", user.city);
        spEditor.apply();
    }

    //Gets logged in user's details
    public User getLoggedInUser() {
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String email = userLocalDatabase.getString("email", "");
        String country = userLocalDatabase.getString("country", "");
        String city = userLocalDatabase.getString("city", "");

        User storedUser = new User(username, password, email, country, city);
        return storedUser;
    }

    //Setting the user "Logged In"
    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.apply();
    }

    //Check if the user is logged in
    public boolean isLoggedIn() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == true) {
            return true;
        }else {
            return false;
        }
    }

    //Clears local user Database
    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }

}
