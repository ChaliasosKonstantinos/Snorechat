package gr.compassbook.snorechat.server.requests.user;

import android.app.ProgressDialog;
import android.content.Context;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

public class UserServerRequests {

    private static final String SERVER_ADDRESS = "http://www.compassbook.gr/";
    private static HttpURLConnection urlConnection;
    ProgressDialog progressDialog;
    BufferedReader reader = null;

    public UserServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }
}
