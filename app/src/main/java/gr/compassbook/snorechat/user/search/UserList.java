package gr.compassbook.snorechat.user.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gr.compassbook.snorechat.Main;
import gr.compassbook.snorechat.R;
import gr.compassbook.snorechat.aboutUs.About;
import gr.compassbook.snorechat.localStorage.UserLocalStorage;
import gr.compassbook.snorechat.server.callbacks.GetAllUsersCallback;
import gr.compassbook.snorechat.server.callbacks.UpdateUserOnlineStatusCallback;
import gr.compassbook.snorechat.server.requests.ServerRequests;
import gr.compassbook.snorechat.user.settings.UserSettings;

public class UserList extends AppCompatActivity {

    private EditText etSearch;
    private ListView userListView;
    private SharedPreferences userData;
    private UserLocalStorage userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        etSearch = (EditText) findViewById(R.id.etSearch);
        getAllUsers();
        userData = getSharedPreferences("userDetails", 0);

}
    //Fetches Data and Shows the list
    private void getAllUsers() {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchAllUsersInBackground(new GetAllUsersCallback() {
            @Override
            public void done(List<String> returnedUsers) {
                populateUserList(returnedUsers);
            }
        });
    }

    //Creates the UserList
    private void populateUserList(final List<String> returnedList) {

        ListAdapter myAdapter = new CustomUserAdapter(this, returnedList);
        userListView = (ListView) findViewById(R.id.userListView);
        userListView.setAdapter(myAdapter);
        userListView.setItemsCanFocus(true);

        //Search user's from the populated List
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> temp = new ArrayList<>();
                int textLength = etSearch.getText().length();
                temp.clear();

                for (int i = 0; i < returnedList.size(); i++) {
                    if (textLength <= returnedList.get(i).length()) {
                        if (etSearch.getText().toString().equalsIgnoreCase((String) returnedList.get(i).subSequence(0, textLength))) {
                            temp.add(returnedList.get(i));
                        }
                    }
                }
                userListView.setAdapter(new CustomUserAdapter(UserList.this, temp));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        Toast.makeText(UserList.this, "Logout Successful", Toast.LENGTH_LONG).show();
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


