package gr.compassbook.snorechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class CustomGroupChatAdapter extends ArrayAdapter<Message> {


    public CustomGroupChatAdapter(Context context, List<Message> message) {
        super(context, R.layout.custom_group_chat_row, message);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View customView = convertView;
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        Button bMessage;
        TextView tvSender;
        Message message = getItem(position);

        if (message.getCounter() % 2 == 0) {
            customView = myInflater.inflate(R.layout.custom_group_chat_row, parent, false);
            tvSender = (TextView) customView.findViewById(R.id.tvGroupChatSender);
            bMessage = (Button) customView.findViewById(R.id.bGroupChatBubble);
            tvSender.setText(message.getSender());
            bMessage.setText(message.getMessage());
        } else {
            customView = myInflater.inflate(R.layout.custom_group_chat_row2, parent, false);
            tvSender = (TextView) customView.findViewById(R.id.tvGroupChatSender);
            bMessage = (Button) customView.findViewById(R.id.bGroupChatBubble);
            tvSender.setText(message.getSender());
            bMessage.setText(message.getMessage());
        }

        return customView;
    }
}
