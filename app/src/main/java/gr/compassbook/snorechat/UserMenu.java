package gr.compassbook.snorechat;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserMenu extends AppCompatActivity {

    Button bCamera;
    //SharedPreferences userData = getSharedPreferences("userDetails", 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

    }

    public void showDetails(View view) {
        bCamera = (Button) findViewById(R.id.bCamera);
        //bCamera.setText(userData.getString("email", ""));
        bCamera.setText("ok");
    }
}
