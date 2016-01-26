package gr.compassbook.snorechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {

    private EditText message;
    private ListView lvGroupChat;
    private SharedPreferences userData;
    private UserLocalStore userDatabase;

    private int mInterval = 2000;
    private Handler mHandler;
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            fetchGroupConv();
            mHandler.postDelayed(mStatusChecker, mInterval);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        message = (EditText) findViewById(R.id.MessageTextField);
        lvGroupChat = (ListView) findViewById(R.id.lvGroupChat);
        userData = getSharedPreferences("userDetails", 0);

        mHandler = new Handler();
        startRepeatingTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRepeatingTask();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRepeatingTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.generic_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //------------------------------------OnClick-------------------------------------------------//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                showUserSettings();
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

    public void sendGroupMessage(View view) {
        Message messageToSend = new Message();
        messageToSend.setSender(userData.getString("username", ""));
        messageToSend.setMessage(message.getText().toString());

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.sendGroupeMessageInBackground(messageToSend, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                message.setText("");
                fetchGroupConv();
            }

            @Override
            public void done2(List<String> returnedList) {

            }
        });


    }

    //Show User Settings
    private void showUserSettings() {
        startActivity(new Intent(this, UserSettings.class));
    }

    //Show About Us
    private void showAboutUs() {
        startActivity(new Intent(this, About.class));
    }

    //-------------------------------------Helpers------------------------------------------------//

    //Log User out
    private void logUserOut() {
        userDatabase = new UserLocalStore(this);
        userDatabase.setUserLoggedIn(false);
        userDatabase.clearUserData();
        Toast.makeText(ChatActivity.this, "Logout Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, Main.class));

    }

    private void fetchGroupConv() {

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchGroupConvInBackground(new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

            }

            @Override
            public void done2(List<String> returnedList) {
                updateGroupChatView(returnedList);
            }
        });
    }

    private void updateGroupChatView(List<String> returnedList) {
        List<Message> messages = new ArrayList<>();
        int counter = 0;

        for (int i = 0; i < returnedList.size(); i += 2) {
            Message message = new Message();
            message.setSender(returnedList.get(i));
            message.setMessage(returnedList.get(i + 1));
            message.setCounter(counter);
            messages.add(message);

            counter++;
        }

        ListAdapter myAdapter = new CustomGroupChatAdapter(this, messages);
        ListView GroupChatListView = (ListView) findViewById(R.id.lvGroupChat);
        GroupChatListView.setAdapter(myAdapter);
        GroupChatListView.setItemsCanFocus(true);
    }

    //--------------------------------------Timer-------------------------------------------------//

    public void ClearMessages(View view) {
        stopRepeatingTask();
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

}