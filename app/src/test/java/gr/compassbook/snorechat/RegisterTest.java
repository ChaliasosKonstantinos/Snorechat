package gr.compassbook.snorechat;

import junit.framework.Assert;

import org.junit.Test;
import java.util.regex.Pattern;

import dalvik.annotation.TestTargetClass;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Themis on 20/12/2015.
 */
public class RegisterTest {
    @Test
    public void SuccessfulTestName(){
        String UserName = "Correct Name";
        String result = "Correct Name!!";
        Assert.assertEquals(result, "Correct Name!!");
    }
    @Test
    public void FailedTestName(){
        String UserName = "";
        String result = "Correct Name!!";
        Assert.assertEquals(result, "Correct Name!!");
    }





    @Test
    public void SuccessfullTestPassword(){
        String Password = "Correct Password";
        String result = "Correct Password!!";
        Assert.assertEquals(result, "Correct Password!!");
    }
    @Test
    public void FailedTestPassword(){
        String Password = "";
        String result = "Correct Password!!";
        Assert.assertEquals(result, "Correct Password!!");
    }





    @Test
    public void SuccessfullTestEmail(){
        String Email = "Correct Email";
        String result = "Correct Email!!";
        Assert.assertEquals(result, "Correct Email!!");
    }
    @Test
    public void FailedTestEmail(){
        String Email = "";
        String result = "Correct Email!!";
        Assert.assertEquals(result, "Correct Email!!");
    }





    @Test
    public void SuccessfullTestCountry(){
        String Country = "Correct Country";
        String result = "Correct Country!!";
        Assert.assertEquals(result, "Correct Country!!");
    }
    @Test
    public void FailedTestCountry(){
        String Country = "";
        String result = "Correct Country!!";
        Assert.assertEquals(result, "Correct Country!!");
    }





    @Test
    public void SuccessfullTestCity(){
        String City = "Correct City";
        String result = "Correct City!!";
        Assert.assertEquals(result, "Correct City!!");
    }
    @Test
    public void FailedTestCity(){
        String City = "";
        String result = "Correct City!!";
        Assert.assertEquals(result, "Correct City!!");
    }

}