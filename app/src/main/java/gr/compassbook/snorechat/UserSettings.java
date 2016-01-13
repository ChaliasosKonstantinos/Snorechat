package gr.compassbook.snorechat;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class UserSettings extends AppCompatActivity {

    EditText etNewPassword, etNewPassword2, etNewEmail, etNewEmail2;
    TextView tvCurrentPassword, tvCurrentEmail;
    SharedPreferences userData;
    UserLocalStore userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etNewPassword2 = (EditText) findViewById(R.id.etNewPassword2);
        etNewEmail = (EditText) findViewById(R.id.etNewEmail);
        etNewEmail2 = (EditText) findViewById(R.id.etNewEmail2);
        tvCurrentPassword = (TextView) findViewById(R.id.textView4);
        tvCurrentEmail = (TextView) findViewById(R.id.textView6);
        userData = getSharedPreferences("userDetails", 0);
        userDatabase = new UserLocalStore(this);

        tvCurrentPassword.setText(userData.getString("password", ""));
        tvCurrentEmail.setText(userData.getString("email", ""));


    }


    //------------------------------------OnClick-------------------------------------------------//


    public void changePassword (View view) {
        final String newPassword = etNewPassword.getText().toString();
        String newPassword2 = etNewPassword2.getText().toString();

        if (!newPassword.isEmpty() && !newPassword2.isEmpty()) {

            if (newPassword.equals(newPassword2)) {
                ServerRequests serverRequest = new ServerRequests(this);
                serverRequest.updateUserDataInBackground(userData.getString("username", ""), "password",
                        newPassword, new GetUserCallback() {
                            @Override
                            public void done(User returnedUser) {
                                userDatabase.updateUserPassword(newPassword);
                                etNewPassword.setText("");
                                etNewPassword2.setText("");
                                Toast.makeText(UserSettings.this, "Password changed successfully", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void done2(List<String> usernames) {

                            }
                        });
            } else {
                showAlertDialog("Passwords doesn't match", "OK");
                etNewPassword.setText("");
                etNewPassword2.setText("");
            }

        } else {
            showAlertDialog("Password field is empty", "OK");
        }
    }

    public void changeEmail (View view) {
        final String newEmail = etNewEmail.getText().toString();
        final String newEmail2 = etNewEmail2.getText().toString();

        if (!newEmail.isEmpty() && !newEmail2.isEmpty()) {

            if (newEmail.equals(newEmail2)) {
                ServerRequests serverRequest = new ServerRequests(this);
                serverRequest.updateUserDataInBackground(userData.getString("username", ""), "email",
                        newEmail, new GetUserCallback() {
                            @Override
                            public void done(User returnedUser) {
                                userDatabase.updateUserEmail(newEmail);
                                etNewEmail.setText("");
                                etNewEmail2.setText("");
                                Toast.makeText(UserSettings.this, "Email changed successfully", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void done2(List<String> usernames) {

                            }
                        });
            } else {
                showAlertDialog("Emails doesn't match", "OK");
                etNewEmail.setText("");
                etNewEmail2.setText("");
            }

        } else {
            showAlertDialog("Email field is empty", "OK");
        }
    }


    //----------------------------------AlertDialog-----------------------------------------------//


    private void showAlertDialog(String message, String positiveButton) {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(UserSettings.this);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(positiveButton, null);
        dialogBuilder.show();
    }

}
