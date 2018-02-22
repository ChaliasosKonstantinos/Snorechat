package gr.compassbook.snorechat.user.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import gr.compassbook.snorechat.R;
import gr.compassbook.snorechat.chat.privateChat.PrivateChat;
import gr.compassbook.snorechat.server.callbacks.AddFriendCallback;
import gr.compassbook.snorechat.server.callbacks.GetUserCallback;
import gr.compassbook.snorechat.server.requests.ServerRequests;
import gr.compassbook.snorechat.user.User;

public class UserDetails extends AppCompatActivity {

    private TextView tvLastName, tvFirstName, tvEmail;
    private SharedPreferences userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        tvLastName = (TextView) findViewById(R.id.tvLastName);
        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        userData = getSharedPreferences("userDetails", 0);
        getUser();
    }

    private void getUser() {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchSingleUserDataInBackground(userData.getString("receiver", ""), new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                showSingleUserDetails(returnedUser);
            }
        });
    }

    private void showSingleUserDetails(User returnedUser) {
        tvLastName.setText(returnedUser.getLastName());
        tvFirstName.setText(returnedUser.getFirstName());
        tvEmail.setText(returnedUser.getEmail());
    }

    public void showPrivateChat(View view) {
        startActivity(new Intent(this, PrivateChat.class));
    }

    public void addFriend(View view) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.addFriendInBackground(userData.getString("username", ""),
                userData.getString("receiver", ""), new AddFriendCallback() {
                    @Override
                    public void done() {
                        Toast.makeText(UserDetails.this, "Friend added!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
