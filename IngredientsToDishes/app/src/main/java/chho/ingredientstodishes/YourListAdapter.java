package chho.ingredientstodishes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Gary on 2/8/2016.
 */
public class YourListAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> yourlist;
    private List<String> foodlist;
    public YourListAdapter(Context context, int resource, List<String> ingreds, List<String> fulllist){
        super(context,resource, ingreds);
        this.context = context;
        this.yourlist = ingreds;
        this.foodlist = fulllist;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.yourlistdisplay_activity, parent, false);

        if(yourlist != null) {

            final String food = yourlist.get(position);

            TextView nameTextView = (TextView) view.findViewById(R.id.foodname);
            Button delete = (Button)view.findViewById(R.id.foodDel);
            nameTextView.setText(food);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    foodlist.add(food);
                    yourlist.remove(position);
                    Collections.sort(foodlist);
                    notifyDataSetChanged();

                }
            });
        }
        Collections.sort(foodlist);

        return view;
    }
}
