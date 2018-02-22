package gr.compassbook.snorechat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import gr.compassbook.snorechat.chat.groupChat.ChatActivity;

/**
 * Created by Themis on 14/1/2016.
 */
public class ChatTest extends ActivityInstrumentationTestCase2<ChatActivity> {
    ChatActivity TChatActivity;
    EditText TMessage;
    ImageButton TSendImageButton;
    ImageButton TClearImageButton;
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
        TSendImageButton = (ImageButton) TChatActivity.findViewById(R.id.bSendWS);
        TClearImageButton = (ImageButton) TChatActivity.findViewById(R.id.bClearMessages);

        TMessage = (EditText) TChatActivity.findViewById(R.id.MessageTextField);

        TViewMessage = (TextView) TChatActivity.findViewById(R.id.messagesList);

        setActivityInitialTouchMode(true);
    }

    //Check if Activity was Created
    public final void testPreconditions() {
        assertNotNull("Test If the Activity was Created", TChatActivity);
    }

    //Check if ImageButtons was Created
    public final void testImageButtons() {
        assertNotNull(TSendImageButton);
        assertNotNull(TClearImageButton);
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
