package chho.ingredientstodishes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gary on 2/8/2016.
 */
public class YourListAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> yourlist;
    public YourListAdapter(Context context, int resource, List<String> ingreds){
        super(context,resource, ingreds);
        this.context = context;
        this.yourlist = ingreds;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.yourlistdisplay_activity, parent, false);

        if(yourlist != null) {

            final String food = yourlist.get(position);

            TextView nameTextView = (TextView) view.findViewById(R.id.foodname);
            nameTextView.setText(food);

            nameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    yourlist.remove(position);
                    notifyDataSetChanged();

                }
            });
        }

        return view;
    }
}
