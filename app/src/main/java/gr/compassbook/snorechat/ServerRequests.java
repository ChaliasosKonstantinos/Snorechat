package gr.compassbook.snorechat;

import android.app.ProgressDialog;
import android.content.Context;
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
            dataToSend.put("username", user.getUsername());
            dataToSend.put("password", user.getPassword());

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

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String result = buffer.toString();
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() != 0) {
                    String username = jObject.getString("username");
                    String password = jObject.getString("password");
                    String email = jObject.getString("email");
                    String lastName = jObject.getString("lastname");
                    String firstName = jObject.getString("firstname");

                    returnedUser = new User(username, lastName, firstName, password, email);
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
    public User storeUserDataInBackground(User userData, GetServerCallback serverCallback) {
        progressDialog.show();
        new StoreUserDataAsyncTask(userData, serverCallback).execute();
        return null;
    }

    public class StoreUserDataAsyncTask extends AsyncTask<User,Void, String> {

        User user;
        GetServerCallback serverCallback;

        //StoreUserDataAsyncTask constructor
        public StoreUserDataAsyncTask(User userData, GetServerCallback serverCallback) {
            this.user = userData;
            this.serverCallback = serverCallback;
        }


        @Override
        protected String doInBackground(User... params) {
            String registerResult = null;

            HashMap<String,String> dataToSend = new HashMap<>();
            dataToSend.put("username", user.getUsername());
            dataToSend.put("password", user.getPassword());
            dataToSend.put("email", user.getEmail());
            dataToSend.put("lastName", user.getLastName());
            dataToSend.put("firstName", user.getFirstName());

            try{
                URL url = new URL(SERVER_ADDRESS + "register.php" + getQueryData(dataToSend));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuffer buffer = new StringBuffer();

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String result = buffer.toString();
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() != 0) {
                    registerResult = jObject.getString("result");
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return registerResult;
        }



        @Override
        protected void onPostExecute(String registerResult) {
            progressDialog.dismiss();
            serverCallback.done(registerResult);
            super.onPostExecute(registerResult);
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

    public class FetchAllUsersAsyncTask extends AsyncTask<Void, Void, List<String>> {


        GetUserCallback userCallback;

        //FetchAllUsersAsyncTask constructor
        public FetchAllUsersAsyncTask(GetUserCallback userCallback) {
            this.userCallback = userCallback;
        }


        @Override
        protected List<String> doInBackground(Void... params) {
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

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String result = buffer.toString();
                JSONObject jObject = new JSONObject(result);
                JSONArray jArray = jObject.getJSONArray("username");

                for (int i=0; i<jArray.length(); i++) {
                    usernames.add(jArray.getString(i));

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
            return usernames;
        }

        @Override
        protected void onPostExecute(List<String> usernames) {
            progressDialog.dismiss();
            userCallback.done2(usernames);
            super.onPostExecute(usernames);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------

    //Sends a private message on the server
    public List sendPrivateMessageInBackground(PrivateMessage messageToSend, GetUserCallback userCallback) {
        new SendPrivateMessageAsyncTask(messageToSend, userCallback).execute();
        return null;
    }

    public class SendPrivateMessageAsyncTask extends AsyncTask<PrivateMessage,Void,Void> {

        PrivateMessage messageToSend;
        GetUserCallback userCallback;

        //SendPrivateMessageAsyncTask constructor
        public SendPrivateMessageAsyncTask(PrivateMessage messageToSend, GetUserCallback userCallback) {
            this.messageToSend = messageToSend;
            this.userCallback = userCallback;
        }


        @Override
        protected Void doInBackground(PrivateMessage... params) {
            HashMap<String,String> dataToSend = new HashMap<>();
            dataToSend.put("username", messageToSend.getSender());
            dataToSend.put("receiver", messageToSend.getReceiver());
            dataToSend.put("message", messageToSend.getMessage());


            try{
                URL url = new URL(SERVER_ADDRESS + "sendMessage.php" + getQueryData(dataToSend));
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
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------

    public List fetchPrivateConvInBackground(PrivateMessage convDetails, GetUserCallback userCallback) {
        new FetchPrivateConvAsyncTask(convDetails, userCallback).execute();
        return null;
    }

    public class FetchPrivateConvAsyncTask extends AsyncTask<PrivateMessage, Void, List<String>> {

        PrivateMessage convDetails;
        GetUserCallback userCallback;

        //FetchAllUsersAsyncTask constructor
        public FetchPrivateConvAsyncTask(PrivateMessage convDetails, GetUserCallback userCallback) {
            this.convDetails = convDetails;
            this.userCallback = userCallback;
        }


        @Override
        protected List<String> doInBackground(PrivateMessage... params) {
            HashMap<String,String> dataToSend = new HashMap<>();
            dataToSend.put("username", convDetails.getSender());
            dataToSend.put("receiver", convDetails.getReceiver());

            List<String> conv = new ArrayList<>();


            try {

                URL url = new URL(SERVER_ADDRESS + "fetchConv.php"  + getQueryData(dataToSend));
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

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String result = buffer.toString();
                JSONObject jObject = new JSONObject(result);
                JSONArray jArray_sender = jObject.getJSONArray("sender");
                JSONArray jArray_message = jObject.getJSONArray("messages");

                for (int i=0; i<jArray_sender.length(); i++) {
                    conv.add(jArray_sender.getString(i));
                    conv.add(jArray_message.getString(i));
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
            return conv;
        }



        @Override
        protected void onPostExecute(List<String> conv) {
            userCallback.done2(conv);
            super.onPostExecute(conv);
        }
    }

    //----------------------------------------------------------------------------------------------------------------------

    //Fetches User's data from the server
    public User fetchSingleUserDataInBackground(String username, GetUserCallback userCallback) {
        progressDialog.show();
        new fetchSingleUserDataAsyncTask(username, userCallback).execute();
        return null;
    }

    //AsyncTask that get User's data from the server
    public class fetchSingleUserDataAsyncTask extends AsyncTask<String, Void, User> {

        String username;
        GetUserCallback userCallback;

        //FetchUserDataAsyncTask constructor
        public fetchSingleUserDataAsyncTask(String username, GetUserCallback userCallback) {
            this.username = username;
            this.userCallback = userCallback;
        }



        @Override
        protected User doInBackground(String... params) {
            User returnedUser = null;

            try {

                URL url = new URL(SERVER_ADDRESS + "fetchSingleUserData.php" + "?username=" + username);
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

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String result = buffer.toString();
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() != 0) {
                    String email = jObject.getString("email");
                    String lastName = jObject.getString("lastname");
                    String firstName = jObject.getString("firstname");

                    returnedUser = new User(lastName, firstName, email);
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

    //----------------------------------------------------------------------------------------------------------------------

    public List fetchFriendsInBackground(String username, GetUserCallback userCallback) {
        progressDialog.show();
        new FetchFriendsAsyncTask(username, userCallback).execute();
        return null;
    }

    public class FetchFriendsAsyncTask extends AsyncTask<String, Void, List<String>> {

        String username;
        GetUserCallback userCallback;

        //FetchAllUsersAsyncTask constructor
        public FetchFriendsAsyncTask(String username, GetUserCallback userCallback) {
            this.username = username;
            this.userCallback = userCallback;
        }


        @Override
        protected List<String> doInBackground(String... params) {
            List<String> friends = new ArrayList<>();

            try {

                URL url = new URL(SERVER_ADDRESS + "fetchFriends.php" + "?username=" + username);
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

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String result = buffer.toString();
                JSONObject jObject = new JSONObject(result);
                JSONArray jArray = jObject.getJSONArray("friendusername");

                for (int i=0; i<jArray.length(); i++) {
                    friends.add(jArray.getString(i));
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
            return friends;
        }

        @Override
        protected void onPostExecute(List<String> friends) {
            progressDialog.dismiss();
            userCallback.done2(friends);
            super.onPostExecute(friends);
        }
    }
}
