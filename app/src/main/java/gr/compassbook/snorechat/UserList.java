package gr.compassbook.snorechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class UserList extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        showUserList();

}

    private void showUserList() {

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchAllUsersInBackground(new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
            }

            @Override
            public void done2(List<String> returnedList) {
                createUserList(returnedList);
            }
        });
    }

    private void createUserList(List<String> returnedList) {
        String[] users = new String[returnedList.size()];

        for (int i=0; i<returnedList.size(); i++){
            users[i] = returnedList.get(i);
        }
        ListAdapter myAdapter = new CustomUserAdapter(this, users);
        ListView userListView = (ListView) findViewById(R.id.userListView);
        userListView.setAdapter(myAdapter);
        userListView.setItemsCanFocus(true);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

}


