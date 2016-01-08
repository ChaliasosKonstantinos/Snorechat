package gr.compassbook.snorechat;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class Jsondecode extends AppCompatActivity {

    TextView JsonViewer;
    UserLocalStore userDatabase;
    SharedPreferences userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsondecode);

        JsonViewer = (TextView) findViewById(R.id.JsonViewer);
    }

    public void showJson(View view) {

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchAllUsersInBackground(new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

            }

            @Override
            public void done2(List usernames) {

                String myString;
                for (int i = 0; i<usernames.size(); i++) {
                    myString = usernames.get(i).toString();
                    JsonViewer.setText(myString);
                }

            }
        });

    }

    private String setReceiver(User returnedUser) {
        userDatabase = new UserLocalStore(this);
        userDatabase.storeUserData(returnedUser);
        userDatabase.setReceiver(returnedUser.username);

        userData = getSharedPreferences("userDetails", 0);

        return userData.getString("receiver", "");
    }
}
