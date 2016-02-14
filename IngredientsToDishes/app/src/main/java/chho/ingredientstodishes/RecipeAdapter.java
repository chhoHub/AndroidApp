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
public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private List<Recipe> myrecipes;

    public RecipeAdapter(Context context, int resource, List<Recipe> recipes){
        super(context, resource, recipes);
        this.context = context;
        this.myrecipes = recipes;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recipelistdisplay_activity, parent, false);

        final String recipename = myrecipes.get(position).getName();

        final TextView nameTextView = (TextView) view.findViewById(R.id.recipename);
        Button select = (Button)view.findViewById(R.id.foodSel);
        nameTextView.setText(recipename);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity host = (Activity) nameTextView.getContext();
                Intent intent = new Intent(host, FoodActivity.class);

                Recipe obj = myrecipes.get(position);
                intent.putExtra("Recipe", (Serializable) obj);

                host.startActivity(intent);
            }
        });

        return view;
    }
}
