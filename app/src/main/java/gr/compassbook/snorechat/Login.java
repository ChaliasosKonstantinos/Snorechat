package gr.compassbook.snorechat;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText etUsername, etPassword;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    public void logIn(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        User userData = new User(username, password);

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchUserDataInBackgroud(userData, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser != null) {
                    logUserIn(returnedUser);
                } else {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
                    dialogBuilder.setMessage("Something went wrong");
                    dialogBuilder.setPositiveButton("OK", null);
                    dialogBuilder.show();
                }
            }
        });
    }

    public void logUserIn(User returnedUser) {
        UserLocalStore userDatabase = new UserLocalStore(this);
        userDatabase.storeUserData(returnedUser);
        userDatabase.setUserLoggedIn(true);
        startActivity(new Intent(this, UserMenu.class));

    }
}
