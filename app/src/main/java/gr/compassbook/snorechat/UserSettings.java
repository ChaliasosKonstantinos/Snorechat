package gr.compassbook.snorechat;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class UserSettings extends AppCompatActivity {

    EditText etNewUsername, etNewPassword, etNewEmail, etNewCountry, etNewCity;
    SharedPreferences userData;
    UserLocalStore userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        etNewUsername = (EditText) findViewById(R.id.NewUserName);
        etNewPassword = (EditText) findViewById(R.id.NewPassword);
        etNewEmail = (EditText) findViewById(R.id.NewEmail);
        etNewCountry = (EditText) findViewById(R.id.NewCountry);
        etNewCity = (EditText) findViewById(R.id.NewCity);
        userData = getSharedPreferences("userDetails", 0);
        userDatabase = new UserLocalStore(this);

    }

    public void changeUsername (View view) {
        final String newUsername = etNewUsername.getText().toString();

        if (!newUsername.isEmpty()) {
            showAlertDialog("Username change is not supported!", "OK");
            /*ServerRequests serverRequest = new ServerRequests(this);
            serverRequest.updateUserDataInBackground(userData.getString("username", ""), "username",
                    newUsername, new GetUserCallback() {
                        @Override
                        public void done(User returnedUser) {
                            userDatabase.updateUserUsername(newUsername);
                            etNewUsername.setText("");
                            Toast.makeText(UserSettings.this, "Username changed successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void done2(List<String> usernames) {

                        }

                    });*/
        } else {
            showAlertDialog("Username field is empty", "OK");
        }

    }

    public void changePassword (View view) {
        final String newPassword = etNewPassword.getText().toString();

        if (!newPassword.isEmpty()) {
            ServerRequests serverRequest = new ServerRequests(this);
            serverRequest.updateUserDataInBackground(userData.getString("username", ""), "password",
                    newPassword, new GetUserCallback() {
                        @Override
                        public void done(User returnedUser) {
                            userDatabase.updateUserPassword(newPassword);
                            etNewPassword.setText("");
                            Toast.makeText(UserSettings.this, "Password changed successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void done2(List<String> usernames) {

                        }
                    });
        } else {
            showAlertDialog("Password field is empty", "OK");
        }
    }

    public void changeEmail (View view) {
        final String newEmail = etNewEmail.getText().toString();

        if (!newEmail.isEmpty()) {
            ServerRequests serverRequest = new ServerRequests(this);
            serverRequest.updateUserDataInBackground(userData.getString("username", ""), "email",
                    newEmail, new GetUserCallback() {
                        @Override
                        public void done(User returnedUser) {
                            userDatabase.updateUserEmail(newEmail);
                            etNewEmail.setText("");
                            Toast.makeText(UserSettings.this, "Email changed successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void done2(List<String> usernames) {

                        }
                    });
        } else {
            showAlertDialog("Email field is empty", "OK");
        }
    }

    public void changeCountry (View view) {
        final String newCountry = etNewCountry.getText().toString();

        if (!newCountry.isEmpty()) {
            System.out.println(newCountry);
            ServerRequests serverRequest = new ServerRequests(this);
            serverRequest.updateUserDataInBackground(userData.getString("username", ""), "country",
                    newCountry, new GetUserCallback() {
                        @Override
                        public void done(User returnedUser) {
                            userDatabase.updateUserCountry(newCountry);
                            etNewCountry.setText("");
                            Toast.makeText(UserSettings.this, "Country changed successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void done2(List<String> usernames) {

                        }
                    });
        } else {
            showAlertDialog("Country field is empty", "OK");
        }
    }

    public void changeCity (View view) {
        final String newCity = etNewCity.getText().toString();

        if (!newCity.isEmpty()) {
            ServerRequests serverRequest = new ServerRequests(this);
            serverRequest.updateUserDataInBackground(userData.getString("username", ""), "City",
                    newCity, new GetUserCallback() {
                        @Override
                        public void done(User returnedUser) {
                            userDatabase.updateUserCity(newCity);
                            etNewCity.setText("");
                            Toast.makeText(UserSettings.this, "City changed successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void done2(List<String> usernames) {

                        }
                    });
        } else {
            showAlertDialog("City field is empty", "OK");
        }
    }

    private void showAlertDialog(String message, String positiveButton) {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(UserSettings.this);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(positiveButton, null);
        dialogBuilder.show();
    }

}
