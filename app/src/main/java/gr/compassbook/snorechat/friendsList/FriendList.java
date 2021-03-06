package gr.compassbook.snorechat.friendsList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gr.compassbook.snorechat.Main;
import gr.compassbook.snorechat.R;
import gr.compassbook.snorechat.aboutUs.About;
import gr.compassbook.snorechat.localStorage.UserLocalStorage;
import gr.compassbook.snorechat.server.callbacks.GetFriendsCallback;
import gr.compassbook.snorechat.server.callbacks.UpdateUserOnlineStatusCallback;
import gr.compassbook.snorechat.server.requests.ServerRequests;
import gr.compassbook.snorechat.user.User;
import gr.compassbook.snorechat.user.settings.UserSettings;

public class FriendList extends AppCompatActivity {

    private SharedPreferences userData;
    private UserLocalStorage userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        userData = getSharedPreferences("userDetails", 0);
        showFriendList();
    }

    //Show Friends List using Adapter
    private void showFriendList() {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchFriendsInBackground(userData.getString("username", ""), new GetFriendsCallback() {
            @Override
            public void done(List<String> friendsList) {
                createFriendList(friendsList);
            }
        });
    }

    //Creates the Friends List
    private void createFriendList(List<String> returnedList) {
        List<User> friends = new ArrayList<>();

        //Check's if user have registered friends and populates the empty returnedList
        if (returnedList.isEmpty()) {
            User user = new User();
            user.setUsername("You don't have any friends yet!");
            user.setIsOnline(404);
            friends.add(user);
        } else {
            for (int i =0; i<returnedList.size(); i+=2) {
                User user = new User();
                user.setUsername(returnedList.get(i));
                user.setIsOnline(Integer.parseInt(returnedList.get(i+1)));
                friends.add(user);
            }
        }

        ListAdapter myAdapter = new CustomFriendAdapter(this, friends);
        ListView friendListView = (ListView) findViewById(R.id.lvFriends);
        friendListView.setAdapter(myAdapter);
        friendListView.setItemsCanFocus(true);

        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    //Log User out
    private void logUserOut() {
        userDatabase = new UserLocalStorage(this);
        userDatabase.setUserLoggedIn(false);
        userDatabase.clearUserData();
        Toast.makeText(FriendList.this, "Logout Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, Main.class));

    }

/* ========================================= MENU =============================================== */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.generic_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, UserSettings.class));
                return true;
            case R.id.action_about:
                startActivity(new Intent(this, About.class));
                return true;
            case R.id.action_sign_out:
                ServerRequests serverRequest = new ServerRequests(this);
                serverRequest.updateUserOnlineStatusInBackground(userData.getString("username", ""),
                        "isonline", "0", new UpdateUserOnlineStatusCallback() {
                    @Override
                    public void done() {
                        logUserOut();
                    }
                });
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
