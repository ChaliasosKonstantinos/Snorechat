package gr.compassbook.snorechat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Themis on 14/1/2016.
 */
public class PrivateChatTest extends ActivityInstrumentationTestCase2<PrivateChat> {
    EditText TetMessage;
    TextView TtChat;
    PrivateChat TPrivateChatTest;

    public PrivateChatTest(Class<PrivateChat> activityClass) {
        super(activityClass);
    }

    public PrivateChatTest(String name) {
        super(PrivateChat.class);
        setName(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        TPrivateChatTest = getActivity();

        TetMessage = (EditText) TPrivateChatTest.findViewById(R.id.etMessage);

        TtChat = (TextView) TPrivateChatTest.findViewById(R.id.tChat);

        setActivityInitialTouchMode(true);
    }

    //Check if Activity was Created
    public final void testPreconditions() {
        assertNotNull("Test If the Activity was Created", TPrivateChatTest);
    }

    //Check if TextViews were Created
    public final void testTextView() {
        assertNotNull(TtChat);
    }

    //Check if EditText were Created
    public final void testEditText() {
        assertNotNull(TetMessage);
    }
}
