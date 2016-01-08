package gr.compassbook.snorechat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Konstantinos
 */
public class ServerRequests {

    ProgressDialog progressDialog;
    public static HttpURLConnection urlConnection;
    public static final String SERVER_ADDRESS = "http://www.compassbook.gr/";
    BufferedReader reader = null;

    //ServerRequests constructor
    public ServerRequests (Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }

    //Fetches User's data from the server
    public User fetchUserDataInBackground(User userData, GetUserCallback userCallback) {
        progressDialog.show();
        new FetchUserDataAsyncTask(userData, userCallback).execute();
        return null;
    }

    //AsyncTask that get User's data from the server
    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {

        User user;
        GetUserCallback userCallback;

        //FetchUserDataAsyncTask constructor
        public FetchUserDataAsyncTask(User userData, GetUserCallback userCallback) {
            this.user = userData;
            this.userCallback = userCallback;
        }



        @Override
        protected User doInBackground(Void... params) {
            User returnedUser = null;
            HashMap<String,String> dataToSend = new HashMap<>();
            dataToSend.put("username", user.username);
            dataToSend.put("password", user.password);

            try {

                URL url = new URL(SERVER_ADDRESS + "fetchUserData.php" + getQueryData(dataToSend));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                //urlConnection.setDoOutput(true);

                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String result = buffer.toString();
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() != 0) {
                    String username = jObject.getString("username");
                    String password = jObject.getString("password");
                    String email = jObject.getString("email");
                    String country = jObject.getString("country");
                    String city = jObject.getString("city");

                    returnedUser = new User(username, password, email, country, city);
                }


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }

    //Stores User's data on the server
    public User storeUserDataInBackground(User userData, GetUserCallback userCallback) {
        progressDialog.show();
        new StoreUserDataAsyncTask(userData, userCallback).execute();
        return null;
    }

    public class StoreUserDataAsyncTask extends AsyncTask<User,Void,Void> {

        User user;
        GetUserCallback userCallback;

        //StoreUserDataAsyncTask constructor
        public StoreUserDataAsyncTask(User userData, GetUserCallback userCallback) {
            this.user = userData;
            this.userCallback = userCallback;
        }


        @Override
        protected Void doInBackground(User... params) {

            HashMap<String,String> dataToSend = new HashMap<>();
            dataToSend.put("username", user.username);
            dataToSend.put("password", user.password);
            dataToSend.put("email", user.email);
            dataToSend.put("country", user.country);
            dataToSend.put("city", user.city);

            try{
                URL url = new URL(SERVER_ADDRESS + "register.php" + getQueryData(dataToSend));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                InputStream errors = (urlConnection.getErrorStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    //Updates User's data on the server
    public User updateUserDataInBackground(String username, String fieldToUpdate, String dataToUpdate, GetUserCallback userCallback) {
        progressDialog.show();
        new UpdateUserDataAsyncTask(username, fieldToUpdate, dataToUpdate, userCallback).execute();
        return null;
    }

    public class UpdateUserDataAsyncTask extends AsyncTask<String, Void, Void> {

        String username, fieldToUpdate, dataToUpdate;
        GetUserCallback userCallback;

        public UpdateUserDataAsyncTask(String username, String fieldToUpdate, String dataToUpdate, GetUserCallback userCallback) {
            this.username = username;
            this.fieldToUpdate = fieldToUpdate;
            this.dataToUpdate = dataToUpdate;
            this.userCallback = userCallback;
        }

        @Override
        protected Void doInBackground(String... params) {


            HashMap<String,String> dataToSend = new HashMap<>();
            dataToSend.put("username", username);
            dataToSend.put("fieldToUpdate", fieldToUpdate);
            dataToSend.put("dataToUpdate", dataToUpdate);


            try{
                URL url = new URL(SERVER_ADDRESS + "updateUserData.php" + getQueryData(dataToSend));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                InputStream errors = (urlConnection.getErrorStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }



    //Resolve dataToSend
    public  String getQueryData(HashMap<String,String> data) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;


        for(Map.Entry<String,String> entry:data.entrySet()) {
            if (first) {
                result.append("?");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                first = false;
            } else {
                result.append("&");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        }

        return result.toString();
    }

    // ---------------------------------------------------------------------------------------------------------------------

    public List fetchAllUsersInBackground(GetUserCallback userCallback) {
        progressDialog.show();
        new FetchAllUsersAsyncTask(userCallback).execute();
        return null;
    }

    public class FetchAllUsersAsyncTask extends AsyncTask<Void, Void, List> {


        GetUserCallback userCallback;

        //FetchUserDataAsyncTask constructor
        public FetchAllUsersAsyncTask(GetUserCallback userCallback) {
            this.userCallback = userCallback;
        }


        @Override
        protected List doInBackground(Void... params) {
            User returnedUser = null;
            List<String> usernames = new ArrayList<>();

            try {

                URL url = new URL(SERVER_ADDRESS + "fetchAllUsers.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                //urlConnection.setDoOutput(true);

                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String result = buffer.toString();
                JSONObject jObject = new JSONObject(result);
                JSONArray jArray = jObject.getJSONArray("username");

                for (int i=0; i<jArray.length(); i++) {
                    usernames.add(jArray.getString(i));
                }
                /*if (jObject.length() != 0) {
                    for (int i = 0; i < jObject.length(); i++) {
                        usernames.add();
                    }
                } */


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return usernames;
        }

        @Override
        protected void onPostExecute(List usernames) {
            progressDialog.dismiss();
            userCallback.done2(usernames);
            super.onPostExecute(usernames);
        }
    }
}
