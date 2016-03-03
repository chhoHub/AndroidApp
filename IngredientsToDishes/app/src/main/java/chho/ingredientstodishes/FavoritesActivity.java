package chho.ingredientstodishes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gary on 2/1/2016.
 */
public class FavoritesActivity extends Activity {
    private ImageButton ingred;
    private ImageButton recipe;
    private ImageButton timer;
    private ListView listView;
    private FavAdapter favAdapter;

    private SavedRecipeDbHelper savedRecipeDbHelper;
    private SQLiteDatabase db;

    private List<String> recipenames = new ArrayList<String>();
    private List<String> recipeids = new ArrayList<String>();
    private List<String> recipeingreds = new ArrayList<String>();
    private List<String> recipeimg = new ArrayList<String>();
    private List<String> recipeurl = new ArrayList<String>();

    private String[] columns = {
            SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID,
            SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_NAME,
            SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_INGREDIENTS,
            SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_IMAGEURL,
            SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_SOURCEURL
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.favoriterecipe_activity);

        ingred = (ImageButton)findViewById(R.id.IngredientsIcon);
        recipe = (ImageButton)findViewById(R.id.RecipeIcon);
        timer = (ImageButton)findViewById(R.id.TimerIcon);


        savedRecipeDbHelper = new SavedRecipeDbHelper(this);
        SQLiteDatabase db = savedRecipeDbHelper.getReadableDatabase();

        String count = "SELECT COUNT(*) FROM " + SavedRecipeEntry.TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount <= 0){
            Log.i("TABLE", "database is empty!");
        }
        else{
            Log.i("TABLE", "Not empty!");

            recipeids = readFromDb(columns[0]);
            recipenames = readFromDb(columns[1]);
            recipeingreds = readFromDb(columns[2]);
            recipeimg = readFromDb(columns[3]);
            recipeurl = readFromDb(columns[4]);
        }


        ingred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, IngredientsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, SelectingRecipeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, TimerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.favorites);

        favAdapter = new FavAdapter(this, R.layout.favlistdisplay_activity, recipenames, recipeids, recipeingreds, recipeimg, recipeurl);

        listView.setAdapter(favAdapter);

    }

    @Override
    public void onResume(){

        recipeids = readFromDb(columns[0]);
        recipenames = readFromDb(columns[1]);
        recipeingreds = readFromDb(columns[2]);
        recipeimg = readFromDb(columns[3]);
        recipeurl = readFromDb(columns[4]);

        favAdapter = new FavAdapter(this, R.layout.favlistdisplay_activity, recipenames, recipeids, recipeingreds, recipeimg, recipeurl);
        listView.setAdapter(favAdapter);
        super.onResume();
    }

    private List<String> readFromDb(String columnname){
        List<String> stuff = new ArrayList<String>();
        SQLiteDatabase db = savedRecipeDbHelper.getReadableDatabase();
        Cursor cursor = db.query(SavedRecipeEntry.TABLE_NAME,columns,null,null,null,null,columnname);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            String name = cursor.getString(cursor.getColumnIndex(columnname));
            stuff.add(name);
            cursor.moveToNext();
        }
        return stuff;
    }
}
