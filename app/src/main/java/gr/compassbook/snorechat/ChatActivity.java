package gr.compassbook.snorechat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Mike&Katerina on 11/12/2015.
 */
public class ChatActivity extends AppCompatActivity {
    EditText message;
    Button Send;
    String SaveMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        message = (EditText) findViewById(R.id.MessageTextField);
        Send = (Button) findViewById(R.id.bSendWS);

    }

    public void SendMessageToTxt(View view) {
        SaveMessage = message.getText().toString();
        AsyncCallWebServices task = new AsyncCallWebServices();
        task.execute();


    }

    private class AsyncCallWebServices extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            ChatActivityWs.invokeChatWS(SaveMessage, "chatOperation");
            return null;
        }
    }
}