package gr.compassbook.snorechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Themis on 20/12/2015.
 */
public class ChatTestV1 extends AppCompatActivity {

    EditText message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        message = (EditText) findViewById(R.id.MessageTextField);
    }



    @Test
    public void SuccessfullChatMessage(){
        String Message = message.getText().toString();
        Assert.assertEquals(Message, "");
        //String Message = "Correct Message";
        //Assert.assertEquals(Message,"Correct Message!!");
    }
    // @Test
    //public void FailedChatMessage(){
    //  String Message = "";
    //String result = "Correct Message!!";
    //Assert.assertEquals(result,"Correct Message!!");
    //}
}
