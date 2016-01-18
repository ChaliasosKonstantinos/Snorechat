package gr.compassbook.snorechat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

/**
 * Created by Themis on 14/1/2016.
 */
public class UserListTest extends ActivityInstrumentationTestCase2<UserList> {
    ListView TUserListView;
    UserList TUserList;

    public UserListTest(Class<UserList> activityClass) {
        super(activityClass);
    }

    public UserListTest(String name) {
        super(UserList.class);
        setName(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        TUserList = getActivity();

        TUserListView = (ListView) TUserList.findViewById(R.id.userListView);

        setActivityInitialTouchMode(true);
    }

    //Check if Activity was Created
    public final void testPreconditions() {
        assertNotNull("Test If the Activity was Created", TUserList);
    }

    //Check if ListViews were Created
    public final void testListView() {
        assertNotNull(TUserListView);
    }

}
