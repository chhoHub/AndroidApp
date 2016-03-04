package chho.ingredientstodishes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gary on 2/13/2016.
 */
public class FavAdapter extends ArrayAdapter<String> implements Serializable {

    private Context context;
    private List<String> recipenames;
    private List<String> recipeids;
    private List<String> recipeingreds;
    private List<String> recipeimgs;
    private List<String> recipeurls;
    private SavedRecipeDbHelper savedRecipeDbHelper;

    public FavAdapter(Context context, int resource, List<String> recipenames, List<String> recipeids, List<String> recipeingreds, List<String> recipeimgs, List<String> recipeurls){
        super(context, resource, recipenames);
        this.context = context;
        this.recipeids = recipeids;
        this.recipeimgs = recipeimgs;
        this.recipeingreds = recipeingreds;
        this.recipenames = recipenames;
        this.recipeurls = recipeurls;
        savedRecipeDbHelper = new SavedRecipeDbHelper(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.favlistdisplay_activity, parent, false);

        final String recipename = recipenames.get(position);
        final String recipeid = recipeids.get(position);
        final String recipeimg = recipeimgs.get(position);
        final String recipeingred = recipeingreds.get(position);
        final String recipeurl = recipeurls.get(position);

        final TextView nameTextView = (TextView) view.findViewById(R.id.recipename);
        final Button delete = (Button)view.findViewById(R.id.foodDel);
        nameTextView.setText(recipename);

        nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Activity host = (Activity) nameTextView.getContext();
                Intent i = new Intent(host, FoodActivity.class);
                i.putExtra("recipename", recipename);
                i.putExtra("recipeimg", (Serializable) recipeimg);
                i.putExtra("recipeid", (Serializable) recipeid);
                i.putExtra("recipeingred", (Serializable) recipeingred);
                i.putExtra("recipesource", (Serializable) recipeurl);
                host.startActivity(i);

                notifyDataSetChanged();
                //host.finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipenames.remove(position);
                recipeids.remove(position);
                recipeimgs.remove(position);
                recipeingreds.remove(position);
                recipeurls.remove(position);

                SQLiteDatabase db = savedRecipeDbHelper.getWritableDatabase();

                String Query = "Select * from " + SavedRecipeEntry.TABLE_NAME + " where " + SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID + " = "  + "'"+ recipeid + "'";
                Cursor cursor = db.rawQuery(Query, null);
                if(cursor.getCount() > 0){
                    db.delete(SavedRecipeEntry.TABLE_NAME, SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID + " = " + "'" + recipeid + "'", null);
                    Log.i("TABLE", "data deleted!");
                }

                notifyDataSetChanged();
                Toast.makeText(delete.getContext(), "Recipe deleted!",
                        Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}
