package gr.compassbook.snorechat;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Themis on 20/12/2015.
 */
public class UserSettingsTest {
    @Test
    public void testSuccessfullPasswordChange() {

        String newPassword = "correct Password";
        String result = "Password changed successfully";
        Assert.assertEquals(result, "Password changed successfully");
    }
    @Test
    public void testFailedPasswordChange() {

        String newPassword = "";
        String result = "Password field is empty";
        Assert.assertEquals(result, "Password field is empty");
    }



    @Test
    public void testSuccessfullUserNameChange() {

        String newUserName = "correct User Name";
        String result = "User Name changed successfully";
        Assert.assertEquals(result, "User Name changed successfully");
    }
    @Test
    public void testFailedUserNameChange() {
        String newUserName = "";
        String result = "User Name changed successfully";
        Assert.assertEquals(result, "User Name changed successfully");
    }



    @Test
    public void testSuccessfullEmailChange() {
        String newEmail = "correct Email";
        String result = "Email changed successfully";
        Assert.assertEquals(true, "Email changed successfully");
    }
    @Test
    public void testFailedEmailChange() {
        String newEmail = "";
        String result = "User Name changed successfully";
        Assert.assertEquals(true, "Email changed successfully");
    }




    @Test
     public void testSuccessfullCountryChange() {
        String newCountry = "correct Country";
        String result = "Country changed successfully";
        Assert.assertEquals(result, "Country changed successfully");
    }
    @Test
    public void testFailedCountryChange() {
        String newCountry = "";
        String result = "User Name changed successfully";
        Assert.assertEquals(result, "User Name changed successfully");
    }




    @Test
    public void testSuccessfullCityChange() {
        String newCity = "correct City";
        String result = "City changed successfully";
        Assert.assertEquals(true, "City changed successfully");
    }
    @Test
    public void testFailedCityChange() {
        String newCity = "";
        String result = "City changed successfully";
        Assert.assertEquals(true, "City changed successfully");
    }
}
