package gr.compassbook.snorechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {

    private EditText etSearch;
    private ListView userListView;
    private ListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        etSearch = (EditText) findViewById(R.id.etSearch);
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

    private void createUserList(final List<String> returnedList) {

        myAdapter = new CustomUserAdapter(this, returnedList);
        userListView = (ListView) findViewById(R.id.userListView);
        userListView.setAdapter(myAdapter);
        userListView.setItemsCanFocus(true);

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
}


