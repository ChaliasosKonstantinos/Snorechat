package gr.compassbook.snorechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Themis on 10/1/2016.
 */
public class LoginTestV3 extends AppCompatActivity {

    EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    @Test
    public void EmptyUsername(){
        String username = etUsername.getText().toString();
        Assert.assertNull(username);
    }

}