package gr.compassbook.snorechat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class FriendList extends AppCompatActivity {

    private SharedPreferences userData;

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
        serverRequest.fetchFriendsInBackground(userData.getString("username", ""), new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

            }

            @Override
            public void done2(List<String> returnedList) {
                createFriendList(returnedList);
            }
        });
    }

    private void createFriendList(List<String> returnedList) {
        String[] friends = new String[returnedList.size()];

        for (int i=0; i<returnedList.size(); i++){
            friends[i] = returnedList.get(i);
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
}
