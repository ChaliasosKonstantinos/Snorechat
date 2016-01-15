package gr.compassbook.snorechat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PrivateChat extends AppCompatActivity {

    private SharedPreferences userData;
    private EditText etMessage;
    private ListView lvPrivateChat;
    private String sender, receiver, message;

    private int mInterval = 2000;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);

        userData = getSharedPreferences("userDetails", 0);
        sender = userData.getString("username", "");
        receiver = userData.getString("receiver", "");
        setTitle(receiver);
        etMessage = (EditText) findViewById(R.id.etMessage);
        lvPrivateChat = (ListView) findViewById(R.id.lvPrivateChat);

        mHandler = new Handler();
        //startRepeatingTask();

        PrivateMessage messageToSend = new PrivateMessage(sender, receiver);

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchPrivateConvInBackground(messageToSend, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

            }

            @Override
            public void done2(List<String> returnedList) {
                updateChatView(returnedList);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    //------------------------------------OnClick-------------------------------------------------//


    //Sends the private Message
    public void sendPrivateMessage(View view) {

        message = etMessage.getText().toString();

        if (!message.isEmpty()) {
            PrivateMessage messageToSend = new PrivateMessage(sender, receiver, message);

            ServerRequests serverRequest = new ServerRequests(this);
            serverRequest.sendPrivateMessageInBackground(messageToSend, new GetUserCallback() {
                @Override
                public void done(User returnedUser) {
                    etMessage.setText("");
                    fetchPrivateConv();
                }

                @Override
                public void done2(List<String> returnedList) {
                    updateChatView(returnedList);
                }
            });
        } else {
            showAlertDialog("Write a message", "OK");
        }
    }


    //-------------------------------------Helpers------------------------------------------------//


    //Fetches the whole of a Private Conv
    private void fetchPrivateConv() {
        PrivateMessage messageToSend = new PrivateMessage(sender, receiver);

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchPrivateConvInBackground(messageToSend, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

            }

            @Override
            public void done2(List<String> returnedList) {
                updateChatView(returnedList);
            }
        });
    }

    //Updates the Conv view
    private void updateChatView(List<String> returnedList) {
        List<PrivateMessage> messages = new ArrayList<>();


        for (int i=0; i<returnedList.size(); i+=2) {
            PrivateMessage message = new PrivateMessage();
            message.setSender(returnedList.get(i));
            message.setMessage(returnedList.get(i+1));
            messages.add(message);
        }

        ListAdapter myAdapter = new CustomChatAdapter(this, messages);
        ListView privateChatListView = (ListView) findViewById(R.id.lvPrivateChat);
        privateChatListView.setAdapter(myAdapter);
        privateChatListView.setItemsCanFocus(true);
    }


    //----------------------------------AlertDialog-----------------------------------------------//


    //Shows an alert Dialog
    private void showAlertDialog(String message, String positiveButton) {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(PrivateChat.this);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(positiveButton, null);
        dialogBuilder.show();
    }


    //--------------------------------------Timer-------------------------------------------------//

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            fetchPrivateConv();
            mHandler.postDelayed(mStatusChecker, mInterval);
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }
}

