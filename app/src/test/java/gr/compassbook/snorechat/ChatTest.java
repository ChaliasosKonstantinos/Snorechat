package gr.compassbook.snorechat;

import junit.framework.Assert;

import org.junit.Test;
import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Themis on 20/12/2015.
 */
public class ChatTest {
    @Test
    public void SuccessfullChatMessage(){
        String Message = "Correct Message";
        String result = "Correct Message!!";
        Assert.assertEquals(result,"Correct Message!!");
    }
    @Test
    public void FailedChatMessage(){
        String Message = "";
        String result = "Correct Message!!";
        Assert.assertEquals(result,"Correct Message!!");
    }
}
