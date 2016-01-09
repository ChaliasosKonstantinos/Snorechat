package gr.compassbook.snorechat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Login extends AppCompatActivity {

    EditText etUsername, etPassword;
    UserLocalStore userDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    //Login User
    public void logIn(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        User userData = new User(username, password);

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchUserDataInBackground(userData, new GetUserCallback() {
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

            @Override
            public void done2(List<String> usernames) {

            }


        });
    }

    private void logUserIn(User returnedUser) {
        userDatabase = new UserLocalStore(this);
        userDatabase.storeUserData(returnedUser);
        userDatabase.setUserLoggedIn(true);
        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, UserMenu.class));

    }
}
