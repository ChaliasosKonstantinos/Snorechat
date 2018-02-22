package gr.compassbook.snorechat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import gr.compassbook.snorechat.user.settings.UserSettings;

/**
 * Created by Themis on 14/1/2016.
 */
public class UserSettingsTest extends ActivityInstrumentationTestCase2<UserSettings> {

    EditText TetNewPassword, TetNewPassword2, TetNewEmail, TetNewEmail2;
    TextView TtvCurrentPassword, TtvCurrentEmail;
    UserSettings TUserSettings;

    public UserSettingsTest(Class<UserSettings> activityClass) {
        super(activityClass);
    }

    public UserSettingsTest(String name) {
        super(UserSettings.class);
        setName(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        TUserSettings = getActivity();

        TetNewPassword = (EditText) TUserSettings.findViewById(R.id.etNewPassword);
        TetNewPassword2 = (EditText) TUserSettings.findViewById(R.id.etNewPassword2);
        TetNewEmail = (EditText) TUserSettings.findViewById(R.id.etNewEmail);
        TetNewEmail2 = (EditText) TUserSettings.findViewById(R.id.etNewEmail2);

        TtvCurrentEmail = (TextView) TUserSettings.findViewById(R.id.textView6);
        TtvCurrentPassword = (TextView) TUserSettings.findViewById(R.id.textView4);

        setActivityInitialTouchMode(true);
    }

    //Check if Activity was Created
    public final void testPreconditions() {
        assertNotNull("Test If the Activity was Created", TUserSettings);

    }

    //Check if TextViews were Created
    public final void testTextView() {
        assertNotNull(TtvCurrentEmail);
        assertNotNull(TtvCurrentPassword);
    }

    //Check if EditTexts were Created
    public final void testEditText() {
        assertNotNull(TetNewPassword);
        assertNotNull(TetNewPassword2);
        assertNotNull(TetNewEmail);
        assertNotNull(TetNewEmail2);
    }

}
