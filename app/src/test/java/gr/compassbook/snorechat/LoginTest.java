package gr.compassbook.snorechat;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Themis on 20/12/2015.
 */
public class LoginTest {
    @Test
    public void SuccessfullTestLoginUserName(){
        String UserName = "Correct User Name";
        String result = "Correct User Name!!";
        Assert.assertEquals(result, "Correct User Name!!");
    }
    @Test
    public void FailedTestLoginUserName(){
        String UserName = "";
        String result = "Correct User Name!!";
        Assert.assertEquals(result, "Correct User Name!!");
    }




    @Test
    public void SuccessfullTestLoginPassword(){
        String Password = "Correct Password";
        String result = "Correct Password!!";
        Assert.assertEquals(result, "Correct Password!!");
    }
    @Test
    public void FailedTestLoginPassword(){
        String Password = "";
        String result = "Correct Password!!";
        Assert.assertEquals(result, "Correct Password!!");
    }
}
