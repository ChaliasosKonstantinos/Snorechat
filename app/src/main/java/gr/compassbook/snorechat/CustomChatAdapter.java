package gr.compassbook.snorechat;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;
import java.util.Objects;

/**
 * Created by Konstantinos on 15/1/2016.
 */
public class CustomChatAdapter extends ArrayAdapter<PrivateMessage> {

    private SharedPreferences userData;

    public CustomChatAdapter(Context context, List<PrivateMessage> message) {
        super(context, R.layout.custom_chat_bubble, message);
        userData = context.getSharedPreferences("userDetails", 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View customView = convertView;
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        Button bMessage;
        PrivateMessage message = getItem(position);

        if (Objects.equals(message.getSender(), userData.getString("username", ""))) {
            customView = myInflater.inflate(R.layout.custom_chat_bubble, parent, false);
            bMessage = (Button) customView.findViewById(R.id.bChatBubble);
            bMessage.setText(message.getMessage());
        } else {
            customView = myInflater.inflate(R.layout.custom_chat_bubble2, parent, false);
            bMessage = (Button) customView.findViewById(R.id.bChatBubble2);
            bMessage.setText(message.getMessage());
        }


        return  customView;
    }
}
