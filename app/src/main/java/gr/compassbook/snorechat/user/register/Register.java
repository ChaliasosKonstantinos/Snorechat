package gr.compassbook.snorechat.user.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import gr.compassbook.snorechat.R;
import gr.compassbook.snorechat.server.callbacks.RegisterUserCallback;
import gr.compassbook.snorechat.server.requests.ServerRequests;
import gr.compassbook.snorechat.user.User;
import gr.compassbook.snorechat.user.login.Login;

public class Register extends AppCompatActivity {

    private EditText etUsername, etPassword, etLastName, etFirstName, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.etRegUsername);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etPassword = (EditText) findViewById(R.id.etRegPassword);
        etEmail = (EditText) findViewById(R.id.etRegEmail);
    }

    public void registerUser(View view) {
        String username = etUsername.getText().toString();
        String lastname = etLastName.getText().toString();
        String firstname = etFirstName.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        User userToRegister = new User(username, lastname, firstname, password, email);

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.registerUserInBackground(userToRegister, new RegisterUserCallback() {
            @Override
            public void done(String result) {
                tryLogin(result);
            }
        });

    }

    private void tryLogin(String result) {
        switch (result) {

            case "success":
                Toast.makeText(Register.this, "Register Successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, Login.class));
                break;
            case "username exist":
                showAlertDialog("Username already exist!", "OK");
                break;
            case "email exist":
                showAlertDialog("Email already exist!", "OK");
                break;
            default:
                showAlertDialog("No internet connection!", "OK");
                break;
        }
    }


    //----------------------------------AlertDialog-----------------------------------------------//


    private void showAlertDialog(String message, String positiveButton) {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(Register.this);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(positiveButton, null);
        dialogBuilder.show();
    }
}