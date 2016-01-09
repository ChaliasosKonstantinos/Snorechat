package gr.compassbook.snorechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

/**
 * Created by Konstantinos on 8/1/2016.
 */
class CustomUserAdapter extends ArrayAdapter<String>{

    public CustomUserAdapter(Context context, List<String> usernames) {
        super(context, R.layout.custom_row_user ,usernames);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row_user, parent, false);

        String singleUser = getItem(position);
        Button bUser = (Button) customView.findViewById(R.id.bUserList);
        bUser.setText(singleUser);

        return customView;
    }
}
