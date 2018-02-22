package gr.compassbook.snorechat.user.search;

import android.content.Context;
import android.content.SharedPreferences;

import gr.compassbook.snorechat.server.callbacks.UpdateUserOnlineStatusCallback;
import gr.compassbook.snorechat.server.requests.ServerRequests;


public class OnlineStatus {

    private Context context;
    private SharedPreferences userData;

    public OnlineStatus(Context context, boolean isOnline) {
        this.context = context;
        ServerRequests serverRequest = new ServerRequests(context);
        userData = context.getSharedPreferences("userDetails",0);

        if (!isOnline) {
            serverRequest.updateUserOnlineStatusInBackground(userData.getString("username", ""),
                    "isonline", "0", new UpdateUserOnlineStatusCallback() {
                @Override
                public void done() {

                }
            });
        } else {
            serverRequest.updateUserOnlineStatusInBackground(userData.getString("username", ""),
                    "isonline", "1", new UpdateUserOnlineStatusCallback() {
                        @Override
                        public void done() {

                        }
                    });
        }
    }
}
