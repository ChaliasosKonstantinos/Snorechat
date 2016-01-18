package gr.compassbook.snorechat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

/**
 * Created by Themis on 14/1/2016.
 */
public class MainTest extends ActivityInstrumentationTestCase2<Main> {
    ImageView TMainView;
    Main TMain;

    public MainTest(Class<Main> activityClass) {
        super(activityClass);
    }

    public MainTest(String name) {
        super(Main.class);
        setName(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        TMain = getActivity();

        TMainView = (ImageView) TMain.findViewById(R.id.imgSplash);

        setActivityInitialTouchMode(true);
    }

    //Check if Activity was Created
    public final void testPreconditions() {
        assertNotNull("Test If the Activity was Created", TMain);
    }


    //Check if ImageView were Created
    public final void testImageView() {
        assertNotNull(TMainView);
    }
}
