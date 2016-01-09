package gr.compassbook.snorechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        List<String> adapterData = new ArrayList<>();
        adapterData = setReceiver(adapterData);
        final ListAdapter myAdapter = new CustomUserAdapter(this, adapterData);
        ListView userListView = (ListView) findViewById(R.id.userListView);
        userListView.setAdapter(myAdapter);

        userListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String singleUser = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(Jsondecode.this, singleUser, Toast.LENGTH_LONG).show();
                    }
                }
        );

}

    public List<String> setReceiver(List<String> adapterData) {


        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchAllUsersInBackground(new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

            }

            @Override
            public void done2(List<String> usernames) {

            }
        });

        adapterData.add("dbtest");
        adapterData.add("katko");
        adapterData.add("kostas");
        adapterData.add("mikeknight");
        adapterData.add("mix");
        adapterData.add("xristina");

        return  adapterData;
    }
}
