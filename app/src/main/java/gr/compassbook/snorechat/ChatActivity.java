package gr.compassbook.snorechat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike&Katerina on 11/12/2015.
 */
public class ChatActivity extends AppCompatActivity {
    EditText message;
    Button Send;
    String SaveMessage;
    TextView ShowMessage;
    List<String> mList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        message = (EditText) findViewById(R.id.MessageTextField);
        Send = (Button) findViewById(R.id.bSendWS);
        ShowMessage = (TextView) findViewById(R.id.messagesList);

    }

    public void SendMessageToTxt(View view) {
        SaveMessage = message.getText().toString();
        ShowMessage.setText(ShowMessage.getText().toString()+ message.getText().toString());
        AsyncCallWebServices task = new AsyncCallWebServices();
        task.execute();


    }

    public void refreshMessages(View view) {

   AsyncReadFile read = new AsyncReadFile();
        read.execute();


    }

    public void ClearMessages(View view) {
        ShowMessage.setText(" ");
    }

    private class AsyncCallWebServices extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            ChatActivityWs.invokeChatWS(SaveMessage, "chatOperation");
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
           //super.onPostExecute(aVoid);
            String line ;
           for(int i=0 ; i<mList.size();i++){
               line= mList.get(i);
               ShowMessage.setText(ShowMessage.getText().toString()+ line);
           }

        }

    }
   

}