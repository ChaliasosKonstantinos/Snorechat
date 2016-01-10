package gr.compassbook.snorechat;

/**
 * Created by Themis on 10/1/2016.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import junit.framework.Assert;

import org.junit.Test;

public class LoginTestV2 extends AppCompatActivity{

    EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }


    @Test
    public void addition_isCorrect() throws Exception {
        String username = etUsername.getText().toString();
        Assert.assertNull(username);
    }
}