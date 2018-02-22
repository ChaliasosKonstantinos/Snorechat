package gr.compassbook.snorechat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;
import android.widget.ListView;

import gr.compassbook.snorechat.user.menu.UserMenu;

/**
 * Created by Themis on 14/1/2016.
 */
public class UserMenuTest extends ActivityInstrumentationTestCase2<UserMenu> {
    ListView TUserMenuList;
    ImageButton TbRightArrow;
    ImageButton TbLefttArrow;
    ImageButton TbShowFriendsList;
    ImageButton TbGroupChat;
    UserMenu TUserMenu;

    public UserMenuTest(Class<UserMenu> activityClass) {
        super(activityClass);
    }

    public UserMenuTest(String name) {
        super(UserMenu.class);
        setName(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        TUserMenu = getActivity();

        TUserMenuList = (ListView) TUserMenu.findViewById(R.id.lvInbox);
        TbLefttArrow = (ImageButton) TUserMenu.findViewById(R.id.bLeftArrow);
        TbRightArrow = (ImageButton) TUserMenu.findViewById(R.id.bRightArrow);
        TbShowFriendsList = (ImageButton) TUserMenu.findViewById(R.id.bShowFriendList);
        TbGroupChat = (ImageButton) TUserMenu.findViewById(R.id.bGroupChat);

        setActivityInitialTouchMode(true);
    }

    //Check if Activity was Created
    public final void testPreconditions() {
        assertNotNull("Test If the Activity was Created", TUserMenu);
    }

    //Check if ListViews were Created
    public final void testListView() {
        assertNotNull(TUserMenuList);
    }

    //Check if ImageButton were Created
    public final void testImageButton() {
        assertNotNull(TbGroupChat);
        assertNotNull(TbLefttArrow);
        assertNotNull(TbRightArrow);
        assertNotNull(TbShowFriendsList);
    }
}
