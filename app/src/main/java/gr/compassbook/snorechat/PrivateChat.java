package gr.compassbook.snorechat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class PrivateChat extends AppCompatActivity {

    UserLocalStore userDatabase;
    SharedPreferences userData;
    EditText etMessage;
    TextView tChat;
    String sender, receiver, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);

        userData = getSharedPreferences("userDetails", 0);
        sender = userData.getString("username", "");
        receiver = userData.getString("receiver", "");
        userDatabase = new UserLocalStore(this);
        userDatabase.setReceiver("mix");
        etMessage = (EditText) findViewById(R.id.etMessage);
        tChat = (TextView) findViewById(R.id.tChat);

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

    //Sends the private Message
    public void sendPrivateMessage(View view) {

        message = etMessage.getText().toString();

        PrivateMessage messageToSend = new PrivateMessage(sender, receiver, message);

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.sendPrivateMessageInBackground(messageToSend, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                fetchPrivateConv();
            }

            @Override
            public void done2(List<String> returnedList) {
                updateChatView(returnedList);
            }
        });

    }

    public void fetchPrivateConv() {
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

    private void updateChatView(List<String> returnedList) {

        for (int i=0; i<returnedList.size(); i+=2) {
            tChat.setText(tChat.getText() + "\n" + returnedList.get(i) + ": " + returnedList.get(i + 1));
        }
    }


}

