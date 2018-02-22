package gr.compassbook.snorechat.user.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import gr.compassbook.snorechat.R;
import gr.compassbook.snorechat.localStorage.UserLocalStorage;
import gr.compassbook.snorechat.server.callbacks.GetUserDataCallback;
import gr.compassbook.snorechat.server.callbacks.UpdateUserOnlineStatusCallback;
import gr.compassbook.snorechat.server.requests.ServerRequests;
import gr.compassbook.snorechat.user.User;
import gr.compassbook.snorechat.user.menu.UserMenu;

public class Login extends AppCompatActivity {

    private  EditText etUsername, etPassword;
    private  CheckBox cbRememberMe;
    private UserLocalStorage userDatabase;
    private  SharedPreferences userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        cbRememberMe = (CheckBox) findViewById(R.id.cbRememberMe);
        userData = getSharedPreferences("userDetails", 0);
        etUsername.setText(userData.getString("usernameRemember", ""));
        etPassword.setText(userData.getString("passwordRemember", ""));

    }

    public void logIn(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        User userData = new User(username, password);
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchUserDataInBackground(userData, new GetUserDataCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser != null) {
                    logUserIn(returnedUser);
                } else {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
                    dialogBuilder.setMessage("Wrong username and password combination");
                    dialogBuilder.setPositiveButton("OK", null);
                    dialogBuilder.show();
                }
            }
        });
    }

    private void logUserIn(User returnedUser) {
        userDatabase = new UserLocalStorage(this);
        userDatabase.storeUserData(returnedUser);
        userDatabase.setUserLoggedIn(true);
        if (cbRememberMe.isChecked()) {
            User rememberUser = new User(etUsername.getText().toString(), etPassword.getText().toString());
            userDatabase.setRememberMe(rememberUser);
        }
        setUserOnline(returnedUser);


    }

    private void setUserOnline(User returnedUser) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.updateUserOnlineStatusInBackground(returnedUser.getUsername(), "isonline", "1", new UpdateUserOnlineStatusCallback() {
            @Override
            public void done() {
                showUserMenu();
            }
        });
    }

    private void showUserMenu() {
        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, UserMenu.class));
    }
}
