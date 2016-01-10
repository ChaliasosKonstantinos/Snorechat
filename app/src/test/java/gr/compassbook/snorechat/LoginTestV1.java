package gr.compassbook.snorechat;

import android.os.Bundle;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Themis on 20/12/2015.
 */


public class LoginTestV1 extends AppCompatActivity{

   EditText etUsername, etPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }


    @Test
    public void SuccessfullTestLoginUserName(){
        EditText username = (EditText) findViewById(R.id.etUsername);
        String usernameEmpt = "";
        Assert.assertEquals(username, usernameEmpt);

        //String UserName = "Correct User Name";
        //String result = "Correct User Name!!";
        //Assert.assertEquals(result, "Correct User Name!!");
    }
    @Test
    public void FailedTestLoginUserName(){
        String password = etPassword.getText().toString();
        String passwordEmpt = "";
        Assert.assertEquals(password, passwordEmpt);



        //String UserName = "";
        //String result = "Correct User Name!!";
        //Assert.assertEquals(result, "Correct User Name!!");
    }




    @Test
    public void SuccessfullTestLoginPassword(){
        String Password = "Correct Password";
        String result = "Correct Password!!";
        Assert.assertEquals(result, "Correct Password!!");
    }
    @Test
    public void FailedTestLoginPassword(){
        String Password = "";
        String result = "Correct Password!!";
        Assert.assertEquals(result, "Correct Password!!");
    }
}
