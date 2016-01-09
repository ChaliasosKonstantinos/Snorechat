package gr.compassbook.snorechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class Jsondecode extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsondecode);
        List<String> adapterData;
        adapterData = setReceiver();
        ListAdapter myAdapter = new CustomUserAdapter(this, adapterData);
        ListView userListView = (ListView) findViewById(R.id.userListView);
        userListView.setAdapter(myAdapter);

        userListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String user = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(Jsondecode.this, user, Toast.LENGTH_LONG).show();
                    }
                }
        );

}

    public void showJson(View view) {

        /* ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchAllUsersInBackground(new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

            }

            @Override
            public String[] done2(String[] usernames) {

                String myString = "";
                for (int i = 0; i<usernames.length(); i++) {
                    myString = myString + "\n" + usernames.get(i).toString();

                }
                return myString;
            }
        }); */

    }

    private void setReceiver() {

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchAllUsersInBackground(new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

            }

            @Override
            public List<String> done2(List<String> usernames) {
                List<String> adapterData;
                adapterData = usernames;

                return adapterData;
            }
        });
    }
}
