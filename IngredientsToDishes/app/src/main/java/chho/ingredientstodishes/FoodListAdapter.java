package chho.ingredientstodishes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gary on 2/8/2016.
 */
public class FoodListAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> foodlist;
    public FoodListAdapter(Context context, int resource,List<String> ingreds){
        super(context,resource, ingreds);
        this.context = context;
        this.foodlist = ingreds;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.searchlistdisplay_activity, parent, false);

        if(foodlist != null) {

            final String food = foodlist.get(position);

            TextView nameTextView = (TextView) view.findViewById(R.id.foodname);
            nameTextView.setText(food);

            nameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    foodlist.remove(position);
                    notifyDataSetChanged();

                }
            });
        }

        return view;
    }
}
