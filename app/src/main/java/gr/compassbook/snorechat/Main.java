package gr.compassbook.snorechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    SharedPreferences userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Check if User is already Logged In
    @Override
    protected void onStart() {
        super.onStart();
        userData = getSharedPreferences("userDetails", 0);

        if (userData.getBoolean("loggedIn", false) == true) {
            startActivity(new Intent(this,UserMenu.class));
        }

    }

    //Show Login Activity
    public void showLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    //Show About Activity
    public void showAbout(View view) {
        Intent aboutIntent  = new Intent(this,About.class);
        startActivity(aboutIntent);
    }
    //Show Register Activity
    public void showRegister(View view) {
        Intent intentRegister = new Intent(this, Register.class);
        startActivity(intentRegister);
    }
}
