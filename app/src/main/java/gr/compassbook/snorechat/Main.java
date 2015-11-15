package gr.compassbook.snorechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Show Login Activity
    public void showLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

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
