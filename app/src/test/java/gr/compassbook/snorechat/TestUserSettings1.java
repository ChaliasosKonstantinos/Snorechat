package gr.compassbook.snorechat;

import android.test.InstrumentationTestCase;

/**
 * Created by Themis on 10/1/2016.
 */
public class TestUserSettings1 extends InstrumentationTestCase{

    private UserSettings usersettings;

    @Override
    protected void setUp() throws Exception{

    }

    public void testUserSettings(){
        givenNewInformations();
        whenNewInformationsSaved();
        thenVerifyNoException();
    }

    private void whenNewInformationsSaved() {
        
    }

    private void givenNewInformations() {
        usersettings = new UserSettings;
        usersettings.changeCity("Test City");
        usersettings.changeCountry("Test Country");
        usersettings.changeEmail("Test Email");
        usersettings.changePassword("Test Password");
        usersettings.changeUsername("Test Username");
        

    }


    @Override
    protected void tearDown() throws Exception{

    }
}
