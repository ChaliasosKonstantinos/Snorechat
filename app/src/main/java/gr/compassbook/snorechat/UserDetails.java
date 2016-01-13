package gr.compassbook.snorechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class UserDetails extends AppCompatActivity {

    TextView tvLastName, tvFirstName, tvEmail;
    SharedPreferences userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        tvLastName = (TextView) findViewById(R.id.tvLastName);
        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        userData = getSharedPreferences("userDetails", 0);

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchSingleUserDataInBackground(userData.getString("receiver", ""), new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                showSingleUserDetails(returnedUser);
            }

            @Override
            public void done2(List<String> returnedList) {

            }
        });
    }

    private void showSingleUserDetails(User returnedUser) {

        tvLastName.setText(returnedUser.lastName);
        tvFirstName.setText(returnedUser.firstName);
        tvEmail.setText(returnedUser.email);
    }

    public void showPrivateChat(View view) {
        startActivity(new Intent(this,PrivateChat.class));
    }
}
