package gr.compassbook.snorechat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;



/**
 * Created by Konstantinos on 8/1/2016.
 */
class CustomUserAdapter extends ArrayAdapter<String>{

    private Context context2;

    public CustomUserAdapter(Context context, String[] usernames) {
        super(context, R.layout.custom_row_user, usernames);
        this.context2 = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View customView = convertView;
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        customView = myInflater.inflate(R.layout.custom_row_user, parent, false);


        Button bUser;
        String singleUser = getItem(position);
        bUser = (Button) customView.findViewById(R.id.bUserList);
        bUser.setText(singleUser);

        final View finalCustomView = customView;
        bUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button bUser;
                bUser = (Button) finalCustomView.findViewById(R.id.bUserList);
                UserLocalStore userDatabase;
                userDatabase = new UserLocalStore(getContext());
                userDatabase.setReceiver(bUser.getText().toString());
                Intent intent = new Intent(context2, UserDetails.class);
                context2.startActivity(intent);


            }
        });

        return customView;
    }
}
