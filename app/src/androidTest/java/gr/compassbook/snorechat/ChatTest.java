package gr.compassbook.snorechat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Themis on 14/1/2016.
 */
public class ChatTest extends ActivityInstrumentationTestCase2<ChatActivity> {
    ChatActivity TChatActivity;
    EditText TMessage;
    Button TSendButton;
    TextView TViewMessage;

    public ChatTest(Class<ChatActivity> activityClass) {
        super(activityClass);
    }

    public ChatTest(String name) {
        super(ChatActivity.class);
        setName(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        TChatActivity = getActivity();
        TSendButton = (Button) TChatActivity.findViewById(R.id.bSendWS);

        TMessage = (EditText) TChatActivity.findViewById(R.id.MessageTextField);

        TViewMessage = (TextView) TChatActivity.findViewById(R.id.messagesList);

        setActivityInitialTouchMode(true);
    }

    //Check if Activity was Created
    public final void testPreconditions() {
        assertNotNull("Test If the Activity was Created", TChatActivity);
    }

    //Check if Register Button was Created
    public final void testButtons() {
        assertNotNull(TSendButton);
    }

    //Check if TextViews were Created
    public final void testTextView() {
        assertNotNull(TViewMessage);
    }

    //Check if EditTexts were Created
    public final void testEditText() {
        assertNotNull(TMessage);
    }
}
