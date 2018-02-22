package gr.compassbook.snorechat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import gr.compassbook.snorechat.user.login.Login;

/**
 * Created by Mike on 14/1/2016.
 */
public class LogInTest extends ActivityInstrumentationTestCase2<Login> {
    Login cLogin;
    EditText edTestUsername , edTestPassword;
    CheckBox cbTestRememberMe;
    Button bTestLoginButton;
    TextView tvTestUsername, tvTestPassword;
    LinearLayout linearLayout;

    public LogInTest(Class <Login> activityClass){
        super(activityClass);
    }

    public LogInTest(String name){
        super(Login.class);
        setName(name);
    }

    protected void  setUp() throws Exception{
        super.setUp();
        cLogin=getActivity();
        edTestUsername = (EditText) cLogin.findViewById(R.id.etUsername);
        edTestPassword = (EditText) cLogin.findViewById(R.id.etPassword);
        cbTestRememberMe = (CheckBox) cLogin.findViewById(R.id.cbRememberMe);
        bTestLoginButton = (Button) cLogin.findViewById(R.id.bLogin);
        tvTestUsername = (TextView) cLogin.findViewById(R.id.tvUserName);
        tvTestPassword  =(TextView) cLogin.findViewById(R.id.tvPassword);
        linearLayout = (LinearLayout) cLogin.findViewById(R.id.loginLinear);

        setActivityInitialTouchMode(true);

    }

    //Check if activity was created
    public final void testPreconditions(){
        assertNotNull(cLogin);
    }

    //Check if edit text was created
    public final void testEditText(){
        assertNotNull(edTestUsername);
        assertNotNull(edTestPassword);
    }

    //Check if checkbox was created
    public final void testCheckBox(){
        assertNotNull(cbTestRememberMe);
    }

    //Check if button was created
    public final void testButton(){
        assertNotNull(bTestLoginButton);
    }

    //Check if textview was created
    public final void testTextView(){
        assertNotNull(tvTestUsername);
        assertNotNull(tvTestPassword);
    }
    //Check if LinearLayout was created
    public final void testLinearLayout(){
        assertNotNull(linearLayout);
    }

}
