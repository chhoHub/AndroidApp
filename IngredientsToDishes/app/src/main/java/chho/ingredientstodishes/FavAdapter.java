package chho.ingredientstodishes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gary on 2/13/2016.
 */
public class FavAdapter extends ArrayAdapter<Recipe> implements Serializable {

    private Context context;
    private List<Recipe> myrecipes;

    public FavAdapter(Context context, int resource, List<Recipe> recipes){
        super(context, resource, recipes);
        this.context = context;
        this.myrecipes = recipes;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.favlistdisplay_activity, parent, false);

        final String recipename = myrecipes.get(position).getName();

        final TextView nameTextView = (TextView) view.findViewById(R.id.recipename);
        Button delete = (Button)view.findViewById(R.id.foodDel);
        nameTextView.setText(recipename);

        nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Activity host = (Activity) nameTextView.getContext();
                Intent intent = new Intent(host, FoodActivity.class);

                Recipe obj = myrecipes.get(position);
                intent.putExtra("Recipe", (Serializable) obj);

                host.startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myrecipes.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
