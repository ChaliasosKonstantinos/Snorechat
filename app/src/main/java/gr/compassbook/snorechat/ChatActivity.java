package gr.compassbook.snorechat;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {
    private EditText message;
    private Button Send;
    private String SaveMessage;
    private TextView ShowMessage;
    private List<String> mList = new ArrayList<>();
    private  SharedPreferences userData;

    private int mInterval = 2000;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        message = (EditText) findViewById(R.id.MessageTextField);
        Send = (Button) findViewById(R.id.bSendWS);
        ShowMessage = (TextView) findViewById(R.id.messagesList);
        ShowMessage.setMovementMethod(new ScrollingMovementMethod());

        mHandler = new Handler();
        startRepeatingTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    //------------------------------------OnClick-------------------------------------------------//


    public void SendMessageToTxt(View view) {
        SaveMessage = message.getText().toString();
        userData = getSharedPreferences("userDetails",0);
        String username = userData.getString("username", "");
        ShowMessage.setText(ShowMessage.getText().toString()+ "\n" +username + ":  " +
                message.getText().toString());
        message.setText("");
        AsyncCallWebServices task = new AsyncCallWebServices();
        task.execute();


    }


    //-------------------------------------Helpers------------------------------------------------//


    public void refreshMessages() {


        AsyncReadFile read = new AsyncReadFile();
        read.execute();


    }

    public void ClearMessages(View view) {
        ShowMessage.setText("");
        stopRepeatingTask();
    }

    private class AsyncCallWebServices extends AsyncTask<String, Void, Void> {
        String username;

        @Override
        protected Void doInBackground(String... params) {
            userData = getSharedPreferences("userDetails",0);
            username = userData.getString("username", "");
            System.out.println(username);
            ChatActivityWs.invokeChatWS(username, SaveMessage, "chatOperation");
            return null;
        }
    }

    private class AsyncReadFile extends AsyncTask<List<String >,Void,Void>{


        @Override
        protected Void doInBackground(List<String>... params) {
            mList=ChatActivityWs.MessArchive("readFileOperation");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String newMessage ;

            ShowMessage.setText("");
            for(int i=0 ; i<mList.size();i++){
                newMessage = mList.get(i);
                ShowMessage.setText(ShowMessage.getText().toString() + "\n" + newMessage);

            }
        }

    }

    //--------------------------------------Timer-------------------------------------------------//


    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
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