package gr.compassbook.snorechat;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityTestCase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Katerina on 1/14/16.
 */
public class RegisterTest extends ActivityInstrumentationTestCase2<Register> {

    Register userRegister;
    Button register;
    TextView uRegistrationTxt , usernameTxt , lNameTxt , fNameTxt , passwordTxt , emailTxt;
    EditText edTxtUsername , edTxtLastName , edTxtFirstName , edTxtPassword , edTxtEmail;
    LinearLayout linearLayout;


    public RegisterTest(Class<Register> activityClass){
        super(activityClass);
    }
    public RegisterTest(String name){
        super(Register.class);
        setName(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        userRegister = getActivity();
        register = (Button) userRegister.findViewById(R.id.bRegister);

        uRegistrationTxt = (TextView) userRegister.findViewById(R.id.txtUserRegistration);
        usernameTxt = (TextView) userRegister.findViewById(R.id.txtUsername);
        lNameTxt = (TextView) userRegister.findViewById(R.id.txtLastName);
        fNameTxt = (TextView) userRegister.findViewById(R.id.txtFirstName);
        passwordTxt = (TextView) userRegister.findViewById(R.id.txtPassword);
        emailTxt = (TextView) userRegister.findViewById(R.id.txtEmail);

        edTxtUsername = (EditText) userRegister.findViewById(R.id.etRegUsername);
        edTxtLastName = (EditText) userRegister.findViewById(R.id.etLastName);
        edTxtFirstName = (EditText) userRegister.findViewById(R.id.etFirstName);
        edTxtPassword = (EditText) userRegister.findViewById(R.id.etRegPassword);
        edTxtEmail = (EditText) userRegister.findViewById(R.id.etRegEmail);

        linearLayout = (LinearLayout) userRegister.findViewById(R.id.userRegisterLinear);

        setActivityInitialTouchMode(true);
    }


    //Check if Activity was Created
    public final void testPreconditions(){
        assertNotNull("Test If the Activity was Created",userRegister);

    }

    //Check if Register Button was Created
    public final void testButtons(){
        assertNotNull(register);

    }

    //Check if TextViews were Created
    public final void testTextView(){
        assertNotNull(uRegistrationTxt);
        assertNotNull(usernameTxt);
        assertNotNull(lNameTxt);
        assertNotNull(fNameTxt);
        assertNotNull(passwordTxt);
        assertNotNull(emailTxt);
    }

    //Check if EditTexts were Created
    public final void testEditText(){
        assertNotNull(edTxtUsername);
        assertNotNull(edTxtLastName);
        assertNotNull(edTxtFirstName);
        assertNotNull(edTxtPassword);
        assertNotNull(edTxtEmail);
    }

    //Check if Layout was Created
    public final void testLinearLayout(){
        assertNotNull(linearLayout);

    }

}