package gr.compassbook.snorechat.server.requests;

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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gr.compassbook.snorechat.chat.groupChat.Message;
import gr.compassbook.snorechat.chat.privateChat.PrivateMessage;
import gr.compassbook.snorechat.server.callbacks.AddFriendCallback;
import gr.compassbook.snorechat.server.callbacks.GetAllUsersCallback;
import gr.compassbook.snorechat.server.callbacks.GetFriendsCallback;
import gr.compassbook.snorechat.server.callbacks.GetGroupConvCallback;
import gr.compassbook.snorechat.server.callbacks.GetInboxCallback;
import gr.compassbook.snorechat.server.callbacks.GetPrivateConvCallback;
import gr.compassbook.snorechat.server.callbacks.GetUserCallback;
import gr.compassbook.snorechat.server.callbacks.GetUserDataCallback;
import gr.compassbook.snorechat.server.callbacks.RegisterUserCallback;
import gr.compassbook.snorechat.server.callbacks.SendGroupMessageCallback;
import gr.compassbook.snorechat.server.callbacks.SendPrivateMessageCallback;
import gr.compassbook.snorechat.server.callbacks.UpdateUserOnlineStatusCallback;
import gr.compassbook.snorechat.server.connection.ServerConnection;
import gr.compassbook.snorechat.user.User;

public class ServerRequests {

    private static final String SERVER_ADDRESS = "http://www.compassbook.gr/";
//    private static HttpURLConnection urlConnection;
    ProgressDialog progressDialog;
    BufferedReader reader = null;

    //ServerRequests constructor
    public ServerRequests (Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }

//------------------------------------------------------------------------------------------------//
//                                    USER
//------------------------------------------------------------------------------------------------//

    //Fetches User's data from the server
    public User fetchUserDataInBackground(User userData, GetUserDataCallback userDataCallback) {
        progressDialog.show();
        new FetchUserDataAsyncTask(userData, userDataCallback).execute();
        return null;
    }

    private class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserDataCallback userDataCallback;

        private FetchUserDataAsyncTask(User userData, GetUserDataCallback userDataCallback) {
            this.user = userData;
            this.userDataCallback = userDataCallback;
        }

        @Override
        protected User doInBackground(Void... params) {
            User returnedUser = null;
            HashMap<String,String> dataToSend = new HashMap<>();
            dataToSend.put("username", user.getUsername());
            dataToSend.put("password", user.getPassword());

            try {
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("fetchUserData.php" + getQueryData(dataToSend));
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
            }
            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userDataCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }

    //Updates User's data on the server
    public User updateUserDataInBackground(String username, String fieldToUpdate, String dataToUpdate,
                                           GetUserCallback userCallback) {
        progressDialog.show();
        new UpdateUserDataAsyncTask(username, fieldToUpdate, dataToUpdate, userCallback).execute();
        return null;
    }
    public class UpdateUserDataAsyncTask extends AsyncTask<String, Void, Void> {

        String username, fieldToUpdate, dataToUpdate;
        GetUserCallback userCallback;

        private UpdateUserDataAsyncTask(String username, String fieldToUpdate, String dataToUpdate, GetUserCallback userCallback) {
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
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("updateUserData.php" + getQueryData(dataToSend));
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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

    //Updates User's online status on the server
    public User updateUserOnlineStatusInBackground(String username, String fieldToUpdate, String dataToUpdate,
                                                   UpdateUserOnlineStatusCallback updateUserOnlineStatusCallback) {
        progressDialog.show();
        new UpdateUserOnlineStatusAsyncTask(username, fieldToUpdate, dataToUpdate, updateUserOnlineStatusCallback).execute();
        return null;
    }

    private class UpdateUserOnlineStatusAsyncTask extends AsyncTask<String, Void, Void> {

        String username, fieldToUpdate, dataToUpdate;
        UpdateUserOnlineStatusCallback updateUserOnlineStatusCallback;

        private UpdateUserOnlineStatusAsyncTask(String username, String fieldToUpdate, String dataToUpdate,
                                                UpdateUserOnlineStatusCallback updateUserOnlineStatusCallback) {
            this.username = username;
            this.fieldToUpdate = fieldToUpdate;
            this.dataToUpdate = dataToUpdate;
            this.updateUserOnlineStatusCallback = updateUserOnlineStatusCallback;
        }

        @Override
        protected Void doInBackground(String... params) {

            HashMap<String,String> dataToSend = new HashMap<>();
            dataToSend.put("username", username);
            dataToSend.put("fieldToUpdate", fieldToUpdate);
            dataToSend.put("dataToUpdate", dataToUpdate);

            try{
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("updateUserData.php" + getQueryData(dataToSend));
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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
            updateUserOnlineStatusCallback.done();
            super.onPostExecute(aVoid);
        }
    }

    //Register user on the server
    public User registerUserInBackground(User userData, RegisterUserCallback registerUserCallback) {
        progressDialog.show();
        new registerUserAsyncTask(userData, registerUserCallback).execute();
        return null;
    }

    private class registerUserAsyncTask extends AsyncTask<User,Void, String> {

        User user;
        RegisterUserCallback registerUserCallback;

        private registerUserAsyncTask(User userData, RegisterUserCallback registerUserCallback) {
            this.user = userData;
            this.registerUserCallback = registerUserCallback;
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
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("register.php" + getQueryData(dataToSend));
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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
            registerUserCallback.done(registerResult);
            super.onPostExecute(registerResult);
        }
    }

    //Get all users from the server
    public List fetchAllUsersInBackground(GetAllUsersCallback allUsersCallback) {
        progressDialog.show();
        new FetchAllUsersAsyncTask(allUsersCallback).execute();
        return null;
    }

    private class FetchAllUsersAsyncTask extends AsyncTask<Void, Void, List<String>> {
        GetAllUsersCallback allUsersCallback;

        //FetchAllUsersAsyncTask constructor
        private FetchAllUsersAsyncTask(GetAllUsersCallback allUsersCallback) {
            this.allUsersCallback = allUsersCallback;
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> usernames = new ArrayList<>();

            try {
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("fetchAllUsers.php");
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
            }
            return usernames;
        }

        @Override
        protected void onPostExecute(List<String> usernames) {
            progressDialog.dismiss();
            allUsersCallback.done(usernames);
            super.onPostExecute(usernames);
        }
    }

//------------------------------------------------------------------------------------------------//
//                                    PRIVATE CHAT
//------------------------------------------------------------------------------------------------//

    //Sends a private message on the server
    public List sendPrivateMessageInBackground(PrivateMessage messageToSend, SendPrivateMessageCallback privateMessageCallback) {
        new SendPrivateMessageAsyncTask(messageToSend, privateMessageCallback).execute();
        return null;
    }

    private class SendPrivateMessageAsyncTask extends AsyncTask<PrivateMessage,Void,Void> {

        PrivateMessage messageToSend;
        SendPrivateMessageCallback privateMessageCallback;

        private SendPrivateMessageAsyncTask(PrivateMessage messageToSend, SendPrivateMessageCallback privateMessageCallback) {
            this.messageToSend = messageToSend;
            this.privateMessageCallback = privateMessageCallback;
        }

        @Override
        protected Void doInBackground(PrivateMessage... params) {
            HashMap<String,String> dataToSend = new HashMap<>();
            dataToSend.put("username", messageToSend.getSender());
            dataToSend.put("receiver", messageToSend.getReceiver());
            dataToSend.put("message", messageToSend.getMessage());

            try{
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("sendMessage.php" + getQueryData(dataToSend));
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.connect();

                InputStream errors = (urlConnection.getErrorStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            privateMessageCallback.done();
            super.onPostExecute(aVoid);
        }
    }

    //Get private conversation from the server
    public List fetchPrivateConvInBackground(PrivateMessage convDetails, GetPrivateConvCallback privateConvCallback) {
        new FetchPrivateConvAsyncTask(convDetails, privateConvCallback).execute();
        return null;
    }

    private class FetchPrivateConvAsyncTask extends AsyncTask<PrivateMessage, Void, List<String>> {

        PrivateMessage convDetails;
        GetPrivateConvCallback privateConvCallback;

        private FetchPrivateConvAsyncTask(PrivateMessage convDetails, GetPrivateConvCallback privateConvCallback) {
            this.convDetails = convDetails;
            this.privateConvCallback = privateConvCallback;
        }

        @Override
        protected List<String> doInBackground(PrivateMessage... params) {
            HashMap<String,String> dataToSend = new HashMap<>();
            dataToSend.put("username", convDetails.getSender());
            dataToSend.put("receiver", convDetails.getReceiver());

            List<String> conv = new ArrayList<>();
            try {
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("fetchConv.php" + getQueryData(dataToSend));
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
            }
            return conv;
        }

        @Override
        protected void onPostExecute(List<String> conv) {
            privateConvCallback.done(conv);
            super.onPostExecute(conv);
        }
    }


//------------------------------------------------------------------------------------------------//
//                                    GROUP CHAT
//------------------------------------------------------------------------------------------------//

    //Sends a group message on the server
    public List sendGroupeMessageInBackground(Message messageToSend, SendGroupMessageCallback groupMessageCallback) {
        new SendGroupMessageAsycTask(messageToSend, groupMessageCallback).execute();
        return null;
    }

    private class SendGroupMessageAsycTask extends AsyncTask<Message, Void, Void> {

        Message messageToSend;
        SendGroupMessageCallback groupMessageCallback;

        private SendGroupMessageAsycTask(Message messageToSend, SendGroupMessageCallback groupMessageCallback) {
            this.messageToSend = messageToSend;
            this.groupMessageCallback = groupMessageCallback;
        }


        @Override
        protected Void doInBackground(Message... params) {
            HashMap<String, String> dataToSend = new HashMap<>();
            dataToSend.put("username", messageToSend.getSender());
            dataToSend.put("message", messageToSend.getMessage());

            try {
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("sendGroupMessage.php" + getQueryData(dataToSend));
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.connect();

                InputStream errors = (urlConnection.getErrorStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            groupMessageCallback.done();
            super.onPostExecute(aVoid);
        }
    }

    //Get group conversation from the server
    public List fetchGroupConvInBackground(GetGroupConvCallback groupConvCallback) {
        new FetchGroupConvAsyncTask(groupConvCallback).execute();
        return null;
    }

    private class FetchGroupConvAsyncTask extends AsyncTask<Void, Void, List<String>> {

        GetGroupConvCallback groupConvCallback;

        private FetchGroupConvAsyncTask(GetGroupConvCallback groupConvCallback) {
            this.groupConvCallback = groupConvCallback;
        }

        @Override
        protected List<String> doInBackground(Void... params) {

            List<String> conv = new ArrayList<>();

            try {
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("fetchGroupConv.php");
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

                for (int i = 0; i < jArray_sender.length(); i++) {
                    conv.add(jArray_sender.getString(i));
                    conv.add(jArray_message.getString(i));
                }


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return conv;
        }


        @Override
        protected void onPostExecute(List<String> conv) {
            groupConvCallback.done(conv);
            super.onPostExecute(conv);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------//

    //Fetches User's data from the server
    public User fetchSingleUserDataInBackground(String username, GetUserCallback userCallback) {
        progressDialog.show();
        new fetchSingleUserDataAsyncTask(username, userCallback).execute();
        return null;
    }

    public class fetchSingleUserDataAsyncTask extends AsyncTask<String, Void, User> {

        String username;
        GetUserCallback userCallback;

        public fetchSingleUserDataAsyncTask(String username, GetUserCallback userCallback) {
            this.username = username;
            this.userCallback = userCallback;
        }

        @Override
        protected User doInBackground(String... params) {
            User returnedUser = null;

            try {
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("fetchSingleUserData.php" + "?username=" + username);
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

//------------------------------------------------------------------------------------------------//
//                                    FRIENDS
//------------------------------------------------------------------------------------------------//

    // Get user's friends from the server
    public List fetchFriendsInBackground(String username, GetFriendsCallback friendsCallback) {
        progressDialog.show();
        new FetchFriendsAsyncTask(username, friendsCallback).execute();
        return null;
    }

    private class FetchFriendsAsyncTask extends AsyncTask<String, Void, List<String>> {

        String username;
        GetFriendsCallback friendsCallback;

        private FetchFriendsAsyncTask(String username, GetFriendsCallback friendsCallback) {
            this.username = username;
            this.friendsCallback = friendsCallback;
        }

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> friends = new ArrayList<>();

            try {
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("fetchFriends.php" + "?username=" + username);
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
                JSONArray jArray_friends = jObject.getJSONArray("friendusername");
                JSONArray jArray_isOnline = jObject.getJSONArray("isonline");

                for (int i=0; i<jArray_friends.length(); i++) {
                    friends.add(jArray_friends.getString(i));
                    friends.add(jArray_isOnline.getString(i));
                }


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return friends;
        }

        @Override
        protected void onPostExecute(List<String> friends) {
            progressDialog.dismiss();
            friendsCallback.done(friends);
            super.onPostExecute(friends);
        }
    }

    // Add friend
    public List addFriendInBackground(String username, String friendName, AddFriendCallback addFriendCallback) {
        progressDialog.show();
        new AddFriendAsyncTask(username, friendName, addFriendCallback).execute();
        return null;
    }

    private class AddFriendAsyncTask extends AsyncTask<String, Void, Void> {

        String username, friendName;
        AddFriendCallback addFriendCallback;

        private AddFriendAsyncTask(String username, String friendName, AddFriendCallback addFriendCallback) {
            this.username = username;
            this.friendName = friendName;
            this.addFriendCallback = addFriendCallback;
        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("?username=" + username + "&friendName=" + friendName);
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
            addFriendCallback.done();
            super.onPostExecute(aVoid);
        }
    }

//------------------------------------------------------------------------------------------------//
//                                    INBOX
//------------------------------------------------------------------------------------------------//

    // Get user's inbox from the server
    public List fetchInboxInBackground(String username, GetInboxCallback inboxCallback) {
        progressDialog.show();
        new FetchInboxAsyncTask(username, inboxCallback).execute();
        return null;
    }

    private class FetchInboxAsyncTask extends AsyncTask<String, Void, List<String>> {

        String username;
        GetInboxCallback inboxCallback;

        private FetchInboxAsyncTask(String username, GetInboxCallback inboxCallback) {
            this.username = username;
            this.inboxCallback = inboxCallback;
        }

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> inbox = new ArrayList<>();

            try {
                ServerConnection connection = new ServerConnection();
                HttpURLConnection urlConnection;
                urlConnection = connection.openGetConnection("fetchInbox.php" + "?username=" + username);
                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));

                StringBuffer buffer = new StringBuffer();

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String result = buffer.toString();
                JSONArray jArray = new JSONArray(result);
                JSONObject jObject;

                for (int i=0; i<jArray.length(); i++) {
                    jObject = jArray.getJSONObject(i);
                    inbox.add(jObject.getString("inbox"));

                }

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return inbox;
        }

        @Override
        protected void onPostExecute(List<String> inbox) {
            progressDialog.dismiss();
            inboxCallback.done(inbox);
            super.onPostExecute(inbox);
        }
    }

//------------------------------------------------------------------------------------------------//
//                                    QUERY DATA
//------------------------------------------------------------------------------------------------//

    //Resolve dataToSend
    private String getQueryData(HashMap<String, String> data) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;


        for (Map.Entry<String, String> entry : data.entrySet()) {
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
}
