package gr.compassbook.snorechat;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Themis on 14/1/2016.
 */
public class AboutUsTest extends ActivityInstrumentationTestCase2<About> {
    TextView Taboutus;
    ImageView TaboutusImage;
    About Tabout;

    public AboutUsTest(Class<About> activityClass) {
        super(activityClass);
    }

    public AboutUsTest(String name) {
        super(About.class);
        setName(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Tabout = getActivity();

        Taboutus = (TextView) Tabout.findViewById(R.id.tAbout2);
        TaboutusImage = (ImageView) Tabout.findViewById(R.id.imgAbout);

        setActivityInitialTouchMode(true);
    }

    //Check if Activity was Created
    public final void testPreconditions() {
        assertNotNull("Test If the Activity was Created", Tabout);
    }

    //Check if TextViews were Created
    public final void testTextView() {
        assertNotNull(Taboutus);
    }

    //Check if ImageView were Created
    public final void testImageView() {
        assertNotNull(TaboutusImage);
    }
}
