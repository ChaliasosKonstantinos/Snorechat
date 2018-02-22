package gr.compassbook.snorechat.friendsList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import gr.compassbook.snorechat.R;
import gr.compassbook.snorechat.chat.privateChat.PrivateChat;
import gr.compassbook.snorechat.localStorage.UserLocalStorage;
import gr.compassbook.snorechat.user.User;


public class CustomFriendAdapter extends ArrayAdapter<User> {

    private Context context2;

    public CustomFriendAdapter(Context context, List<User> friends) {
        super(context, R.layout.custom_row_friend, friends);
        this.context2 = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View customView = convertView;
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        customView = myInflater.inflate(R.layout.custom_row_friend, parent, false);
        Button bFriend;
        User singleFriend = getItem(position);
        bFriend = (Button) customView.findViewById(R.id.bFriend);
        bFriend.setText(singleFriend.getUsername());

        if (singleFriend.getIsOnline() == 1) {
            bFriend.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_online_button, 0);
        } else if (singleFriend.getIsOnline() == 0) {
            bFriend.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_offline_button, 0);
        }

        final View finalCustomView = customView;
        bFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button bFriend;
                bFriend = (Button) finalCustomView.findViewById(R.id.bFriend);
                UserLocalStorage userDatabase;
                userDatabase = new UserLocalStorage(getContext());
                userDatabase.setReceiver(bFriend.getText().toString());
                Intent intent = new Intent(context2, PrivateChat.class);
                context2.startActivity(intent);
            }
        });

        return  customView;
    }
}
