package gr.compassbook.snorechat;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Themis on 10/1/2016.
 */
public class LoginTestV4 extends ActivityInstrumentationTestCase2<Login> {

    Login LoginTest1;
    View view;
    EditText etUsername, etPassword;

    public LoginTestV4(Class<Login> activityClass){
        super(activityClass);
    }

    public LoginTestV4(String name){
        super(Login.class);
        setName(name);
    }
    protected void setUp() throws Exception{
        super.setUp();
         LoginTest1= getActivity();
         etUsername = (EditText) LoginTest1.findViewById(R.id.etUsername);
         etPassword = (EditText) LoginTest1.findViewById(R.id.etPassword);
         setActivityInitialTouchMode(true);
    }


   /* //Chekc if activity was created
    public final void testPreconditions(){
        assertNotNull("Test if activity was created", LoginTest1);
    }
    */

    //Check if etUsername were created
    public final void testetUsername(){
        assertNotNull(etUsername);
        User user = new User(etUsername.getText().toString(),etPassword.getText().toString());
    }

    //Check if etPassword were created
    public final void testetPassword(){
        assertNotNull(etPassword);
    }

}