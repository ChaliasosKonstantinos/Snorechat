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
        spEditor.putString("lastname", user.lastName);
        spEditor.putString("firstname", user.firstName);
        spEditor.commit();
    }

    //Gets logged in user's details
    public User getLoggedInUser() {
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String email = userLocalDatabase.getString("email", "");
        String lastName = userLocalDatabase.getString("lastname", "");
        String firstName = userLocalDatabase.getString("firstname", "");

        User storedUser = new User(username, lastName, firstName, password, email);
        return storedUser;
    }

    //Setting the user "Logged In"
    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    //Setting the user "Logged In"
    public void setReceiver(String receiver) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("receiver", receiver);
        spEditor.commit();
    }

    //Check if the user is logged in
    public boolean isLoggedIn() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == true) {
            return true;
        }else {
            return false;
        }
    }

    //Updates User's username
    public void updateUserUsername(String newUsername) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", newUsername);
        spEditor.commit();
    }

    //Updates User's password
    public void updateUserPassword(String newPassword) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("password", newPassword);
        spEditor.commit();
    }

    //Updates User's email
    public void updateUserEmail(String newEmail) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("email", newEmail);
        spEditor.commit();
    }

    //Updates User's country
    public void updateUserLastName(String newLastName) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("lastname", newLastName);
        spEditor.commit();
    }

    //Updates User's city
    public void updateUserFirstName(String firstName) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("firstname", firstName);
        spEditor.commit();
    }

    //Clears local user Database
    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

    public String getStoredUserEmail() {
        return userLocalDatabase.getString("Email", "");
    }
}
