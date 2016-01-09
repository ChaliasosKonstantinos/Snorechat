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
class CustomUserAdapter extends ArrayAdapter<String>  {
    private  Context Context2;
    public CustomUserAdapter(Context context, String[] usernames) {
        super(context, R.layout.custom_row_user, usernames);
        this.Context2=context;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row_user, parent, false);

        String singleUser = getItem(position);
        Button bUser = (Button) customView.findViewById(R.id.bUserList);
        bUser.setText(singleUser);

        final View finalCustomView = customView;
        bUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button bUser;
                System.out.println("Eimai sto Adapter onClick");
                bUser = (Button) finalCustomView.findViewById(R.id.bUserList);
                Jsondecode jsondecode = new Jsondecode();
                UserLocalStore userDatabase;
                userDatabase = new UserLocalStore(getContext());
                userDatabase.setReceiver(bUser.getText().toString());
                String receiver = bUser.getText().toString();

                Intent intent = new Intent(Context2,ChatActivity.class);
                Context2.startActivity(intent);
            }
        });

        return customView;
    }
}


