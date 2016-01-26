package gr.compassbook.snorechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class UserMenu extends AppCompatActivity {

    private UserLocalStore userDatabase;
    private SharedPreferences userData;
    private ImageButton bLeftArrow, bRightArrow, bFriends, bGroupChat;
    private ListView inboxListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        bLeftArrow = (ImageButton) findViewById(R.id.bLeftArrow);
        bRightArrow = (ImageButton) findViewById(R.id.bRightArrow);
        bFriends = (ImageButton) findViewById(R.id.bShowFriendList);
        bGroupChat = (ImageButton) findViewById(R.id.bGroupChat);
        userData = getSharedPreferences("userDetails", 0);
        fetchInbox();
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
            case R.id.action_how_to:
                showHowTo();
                return true;
            case R.id.action_about:
                showAboutUs();
                return true;
            case R.id.action_sign_out:
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

    @Override
    protected void onResume() {
        super.onResume();
        new OnlineStatus(this, true);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        new OnlineStatus(this, false);
    }

    //------------------------------------OnClick-------------------------------------------------//


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

    //Show Group Chat Activity
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

    private void showInbox(List<String> inbox) {

        for(int i=0; i < inbox.size(); i++) {
            inbox.set(i, inbox.get(i).toUpperCase());
        }
        Collections.sort(inbox);
        if (inbox.isEmpty()) {
            inbox.add(0, "You don't have any messages");
        }
        ListAdapter myAdapter = new CustomInboxAdapter(this, inbox);
        inboxListView = (ListView) findViewById(R.id.lvInbox);
        inboxListView.setAdapter(myAdapter);
        inboxListView.setItemsCanFocus(true);

        inboxListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void showHowTo() {
        startActivity(new Intent(this, HowTo.class));
    }

    //Log User out
    private void logUserOut() {
        userDatabase = new UserLocalStore(this);
        userDatabase.setUserLoggedIn(false);
        userDatabase.clearUserData();
        Toast.makeText(UserMenu.this, "Logout Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, Main.class));

    }



    //-------------------------------------Helpers------------------------------------------------//


    private void fetchInbox() {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchInboxInBackground(userData.getString("username", ""), new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

            }

            @Override
            public void done2(List<String> returnedList) {
                showInbox(returnedList);
            }
        });
    }


}
