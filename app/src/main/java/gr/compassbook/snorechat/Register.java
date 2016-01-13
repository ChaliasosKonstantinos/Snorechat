package gr.compassbook.snorechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    //Creates a user class with registered details
    public void registerUser(View view) {
        String username = etUsername.getText().toString();
        String lastname = etLastName.getText().toString();
        String firstname = etFirstName.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();

        User userToRegister = new User(username, lastname, firstname, password, email);

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.storeUserDataInBackground(userToRegister, new GetServerCallback() {
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
                showAlertDialog("Server is busy. Try again in a moment!", "OK");
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