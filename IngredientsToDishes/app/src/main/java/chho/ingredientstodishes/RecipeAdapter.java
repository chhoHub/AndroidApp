package chho.ingredientstodishes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Gary on 2/13/2016.
 */
public class RecipeAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> recipe_id;
    private List<String> recipe_name;
    private List<String> recipe_sourceurl;
    private List<String> recipe_imageurl;

    public RecipeAdapter(Context context, int resource, List<String> recipe_id, List<String> recipe_name, List<String> recipe_sourceurl, List<String> recipe_imageurl){
        super(context, resource, recipe_name);
        this.context = context;
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.recipe_sourceurl = recipe_sourceurl;
        this.recipe_imageurl = recipe_imageurl;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recipelistdisplay_activity, parent, false);

        final String recipename = recipe_name.get(position);
        final String recipeimage = recipe_imageurl.get(position);
        final String recipeid = recipe_id.get(position);
        final String recipesource = recipe_sourceurl.get(position);

        final TextView nameTextView = (TextView) view.findViewById(R.id.recipename);
        nameTextView.setText(recipename);

        nameTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Activity host = (Activity) nameTextView.getContext();
                Intent i = new Intent(host, FoodActivity.class);
                i.putExtra("recipename", recipename);
                i.putExtra("recipeimg", (Serializable) recipeimage);
                i.putExtra("recipeid", (Serializable) recipeid);
                i.putExtra("recipesource", (Serializable) recipesource);
                host.startActivity(i);
            }
        });

        return view;
    }
}
