package gr.compassbook.snorechat;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;


public class OnlineStatus {

    private Context context;
    private SharedPreferences userData;

    public OnlineStatus(Context context, boolean isOnline) {
        this.context = context;
        ServerRequests serverRequest = new ServerRequests(context);
        userData = context.getSharedPreferences("userDetails",0);

        if (!isOnline) {
            serverRequest.updateUserDataInBackground(userData.getString("username", ""), "isonline", "0", new GetUserCallback() {
                @Override
                public void done(User returnedUser) {

                }

                @Override
                public void done2(List<String> returnedList) {

                }
            });
        } else {
            serverRequest.updateUserDataInBackground(userData.getString("username", ""), "isonline", "1", new GetUserCallback() {
                @Override
                public void done(User returnedUser) {

                }

                @Override
                public void done2(List<String> returnedList) {

                }
            });
        }
    }
}
