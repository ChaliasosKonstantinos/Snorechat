package gr.compassbook.snorechat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

/**
 * Created by Themis on 14/1/2016.
 */
public class FriendListTest extends ActivityInstrumentationTestCase2<FriendList> {
    ListView TFriendListView;
    FriendList TFriendList;

    public FriendListTest(Class<FriendList> activityClass) {
        super(activityClass);
    }

    public FriendListTest(String name) {
        super(FriendList.class);
        setName(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        TFriendList = getActivity();

        TFriendListView = (ListView) TFriendList.findViewById(R.id.lvFriends);

        setActivityInitialTouchMode(true);
    }

    //Check if Activity was Created
    public final void testPreconditions() {
        assertNotNull("Test If the Activity was Created", TFriendList);
    }

    //Check if TextViews were Created
    public final void testTextView() {
        assertNotNull(TFriendListView);
    }
}
