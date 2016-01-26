package gr.compassbook.snorechat;

import android.content.Context;
import android.content.SharedPreferences;

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
        spEditor.putString("username", user.getUsername());
        spEditor.putString("password", user.getPassword());
        spEditor.putString("email", user.getEmail());
        spEditor.putString("lastname", user.getLastName());
        spEditor.putString("firstname", user.getFirstName());
        spEditor.apply();
    }

    //Gets logged in user's details
    public User getLoggedInUser() {
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String email = userLocalDatabase.getString("email", "");
        String lastName = userLocalDatabase.getString("lastname", "");
        String firstName = userLocalDatabase.getString("firstname", "");

        return new User(username, lastName, firstName, password, email);
    }

    //Setting the user "Logged In"
    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.apply();
    }

    //Setting the user "Logged In"
    public void setReceiver(String receiver) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("receiver", receiver);
        spEditor.apply();
    }

    //Check if the user is logged in
    public boolean isLoggedIn() {
        return userLocalDatabase.getBoolean("loggedIn", false);
    }

    //Updates User's username
    public void updateUserUsername(String newUsername) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", newUsername);
        spEditor.apply();
    }

    //Updates User's password
    public void updateUserPassword(String newPassword) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("password", newPassword);
        spEditor.apply();
    }

    //Updates User's email
    public void updateUserEmail(String newEmail) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("email", newEmail);
        spEditor.apply();
    }

    //Updates User's country
    public void updateUserLastName(String newLastName) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("lastname", newLastName);
        spEditor.apply();
    }

    //Updates User's city
    public void updateUserFirstName(String firstName) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("firstname", firstName);
        spEditor.apply();
    }

    //Clears local user Database
    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.remove("username");
        spEditor.remove("password");
        spEditor.remove("email");
        spEditor.remove("lastname");
        spEditor.remove("firstname");
        spEditor.apply();
    }

    public String getStoredUserEmail() {
        return userLocalDatabase.getString("Email", "");
    }

    public void setRememberMe(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("usernameRemember", user.getUsername());
        spEditor.putString("passwordRemember", user.getPassword());
        spEditor.apply();
    }
}
