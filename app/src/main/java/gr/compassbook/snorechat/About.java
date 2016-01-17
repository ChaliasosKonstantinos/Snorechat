package gr.compassbook.snorechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class About extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* create a full screen window *//*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



    *//* adapt the image to the size of the display *//*
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.farmchat_background),size.x,size.y,true);


    *//* fill the background ImageView with the resized image *//*
        ImageView iv_background = (ImageView) findViewById(R.id.ivAboutBackground);
        iv_background.setImageBitmap(bmp);*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


    }
}
