package gr.compassbook.snorechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText etUsername, etPassword, etEmail, etCountry, etCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.etRegUsername);
        etPassword = (EditText) findViewById(R.id.etRegPassword);
        etEmail = (EditText) findViewById(R.id.etRegEmail);
        etCountry = (EditText) findViewById(R.id.etRegCountry);
        etCity = (EditText) findViewById(R.id.etRegCity);
    }

    //Creates a user class with registered details
    public void registerUser(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        String country = etCountry.getText().toString();
        String city = etCity.getText().toString();

        User userToRegister = new User(username, password, email, country, city);

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.storeUserDataInBackground(userToRegister, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                showLogin();
            }
        });

    }

    private void showLogin() {
        Toast.makeText(Register.this, "Register Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,Login.class));
    }


    }
