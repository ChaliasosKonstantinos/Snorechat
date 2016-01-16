package gr.compassbook.snorechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class UserMenu extends AppCompatActivity {

    private UserLocalStore userDatabase;
    private SharedPreferences userData;
    private ImageButton bLeftArrow, bRightArrow, bFriends, bGroupChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        bLeftArrow = (ImageButton) findViewById(R.id.bLeftArrow);
        bRightArrow = (ImageButton) findViewById(R.id.bRightArrow);
        bFriends = (ImageButton) findViewById(R.id.bShowFriendList);
        bGroupChat = (ImageButton) findViewById(R.id.bGroupChat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                showUserList();
                return true;
            case R.id.action_settings:
                showUserSettings();
                return true;
            case R.id.action_about:
                showAboutUs();
                return true;
            case R.id.action_sign_out:
                userData = getSharedPreferences("userDetails", 0);
                ServerRequests serverRequest = new ServerRequests(this);
                serverRequest.updateUserDataInBackground(userData.getString("username", ""), "isonline", "0", new GetUserCallback() {
                    @Override
                    public void done(User returnedUser) {
                        logUserOut();
                    }

                    @Override
                    public void done2(List<String> returnedList) {

                    }
                });
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
    }

    //Show Menu Buttons
    public void showMenuButtons(View view) {
        bRightArrow.setVisibility(View.INVISIBLE);
        bLeftArrow.setVisibility(View.VISIBLE);
        bFriends.setVisibility(View.VISIBLE);
        bGroupChat.setVisibility(View.VISIBLE);
    }

    //Hide Menu Buttons
    public void hideMenuButtons(View view) {
        bRightArrow.setVisibility(View.VISIBLE);
        bLeftArrow.setVisibility(View.INVISIBLE);
        bFriends.setVisibility(View.INVISIBLE);
        bGroupChat.setVisibility(View.INVISIBLE);
    }


    //Show User Settings
    private void showUserSettings() {
        startActivity(new Intent(this, UserSettings.class));
    }

    //Show About Us
    private void showAboutUs() {
        startActivity(new Intent(this, About.class));
    }

    //Show Chat Activity
    public void showChatActivity(View view) {
        startActivity(new Intent(this, ChatActivity.class));
    }


    //Show User List
    private void showUserList() {
        startActivity(new Intent(this, UserList.class));
    }

    //Show User List
    public void showFriendsList(View view) {
        startActivity(new Intent(this, FriendList.class));
    }

    //Log User out
    private void logUserOut() {
        userDatabase = new UserLocalStore(this);
        userDatabase.setUserLoggedIn(false);
        userDatabase.clearUserData();
        Toast.makeText(UserMenu.this, "Logout Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, Main.class));

    }


}
