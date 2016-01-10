package gr.compassbook.snorechat;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Themis on 20/12/2015.
 */
public class ChatTest extends AppCompatActivity {
    @Test
    public void SuccessfullChatMessage(){
        EditText Message;
        Message = (EditText) findViewById(R.id.MessageTextField);
        //String Message = "Correct Message";
        Assert.assertEquals(Message,"Correct Message!!");
    }
   // @Test
    //public void FailedChatMessage(){
      //  String Message = "";
        //String result = "Correct Message!!";
        //Assert.assertEquals(result,"Correct Message!!");
    //}
}
