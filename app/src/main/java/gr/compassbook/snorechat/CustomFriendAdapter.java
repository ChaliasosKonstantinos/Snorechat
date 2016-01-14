package gr.compassbook.snorechat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

/**
 * Created by Konstantinos on 13/1/2016.
 */
public class CustomFriendAdapter extends ArrayAdapter<String> {

    private Context context2;

    public CustomFriendAdapter(Context context, String[] friends) {
        super(context, R.layout.custom_row_friend, friends);
        this.context2 = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View customView = convertView;
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        customView = myInflater.inflate(R.layout.custom_row_friend, parent, false);

        Button bFriend;
        String singleFriend = getItem(position);
        bFriend = (Button) customView.findViewById(R.id.bFriend);
        bFriend.setText(singleFriend);

        final View finalCustomView = customView;
        bFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button bFriend;
                bFriend = (Button) finalCustomView.findViewById(R.id.bFriend);
                UserLocalStore userDatabase;
                userDatabase = new UserLocalStore(getContext());
                userDatabase.setReceiver(bFriend.getText().toString());
                Intent intent = new Intent(context2, PrivateChat.class);
                context2.startActivity(intent);
            }
        });

        return  customView;
    }
}
