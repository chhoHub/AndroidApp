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
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gary on 2/1/2016.
 */
public class FavoritesActivity extends Activity {
    private ImageButton fav;
    private ImageButton ingred;
    private ImageButton recipe;
    private ImageButton timer;
    private ListView listView;
    private FavAdapter favAdapter;

    private SavedRecipeDbHelper savedRecipeDbHelper;
    private SQLiteDatabase db;

    private String[] ingreds = {"Tuna", "Cheese", "Bread"};
    private String[] steps = {"Put tuna on bread", "Put cheese on tuna", "Bake in oven"};
    private List<Recipe> myfavs = new ArrayList<Recipe>(){{
        add(new Recipe("Tuna Melt", ingreds, steps, 5 ));
    }};

    private List<String> recipenames = new ArrayList<String>();
    private List<String> recipeids = new ArrayList<String>();;
    private List<String> recipeingreds = new ArrayList<String>();;
    private List<String> recipeimg = new ArrayList<String>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.favoriterecipe_activity);

        fav = (ImageButton)findViewById(R.id.FavoriteIcon);
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

            recipeingreds = readIngredientListFromDb(); // list of list(String) of ingredients
            recipeids = readRecipeIDFromDb();
            recipeimg = readRecipeimgFromDb();
            recipenames = readRecipeNameFromDb();
        }


        ingred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, IngredientsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, SelectingRecipeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, TimerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.favorites);
        //favAdapter = new FavAdapter(this, R.layout.favlistdisplay_activity, myfavs);

        favAdapter = new FavAdapter(this, R.layout.favlistdisplay_activity, recipenames, recipeids, recipeingreds, recipeimg);

        listView.setAdapter(favAdapter);

    }

    @Override
    public void onResume(){

        recipeingreds = readIngredientListFromDb(); // list of list(String) of ingredients
        recipeids = readRecipeIDFromDb();
        recipeimg = readRecipeimgFromDb();
        recipenames = readRecipeNameFromDb();

        Log.i("Hi", "Hello data resumed");
        favAdapter = new FavAdapter(this, R.layout.favlistdisplay_activity, recipenames, recipeids, recipeingreds, recipeimg);
        listView.setAdapter(favAdapter);
        super.onResume();
    }


    private List<String> readIngredientListFromDb(){

        List<String> ingredients = new ArrayList<String>();
        String[] columns = {
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID,
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_NAME,
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_INGREDIENTS,
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_IMAGEURL
        };
        SQLiteDatabase db = savedRecipeDbHelper.getReadableDatabase();
        Cursor cursor = db.query(SavedRecipeEntry.TABLE_NAME,columns,null,null,null,null,SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_INGREDIENTS);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_INGREDIENTS));
            ingredients.add(name);
            cursor.moveToNext();
        }

        return ingredients;
    }

    private List<String> readRecipeNameFromDb(){

        List<String> recipename = new ArrayList<String>();
        String[] columns = {
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID,
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_NAME,
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_INGREDIENTS,
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_IMAGEURL
        };
        SQLiteDatabase db = savedRecipeDbHelper.getReadableDatabase();
        Cursor cursor = db.query(SavedRecipeEntry.TABLE_NAME,columns,null,null,null,null,SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_NAME);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_NAME));
            recipename.add(name);
            cursor.moveToNext();
        }

        return recipename;
    }

    private List<String> readRecipeIDFromDb(){

        List<String> recipeid = new ArrayList<String>();
        String[] columns = {
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID,
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_NAME,
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_INGREDIENTS,
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_IMAGEURL
        };
        SQLiteDatabase db = savedRecipeDbHelper.getReadableDatabase();
        Cursor cursor = db.query(SavedRecipeEntry.TABLE_NAME,columns,null,null,null,null,SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID));
            recipeid.add(name);
            cursor.moveToNext();
        }

        return recipeid;
    }

    private List<String> readRecipeimgFromDb(){

        List<String> recipeimg = new ArrayList<String>();
        String[] columns = {
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID,
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_NAME,
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_INGREDIENTS,
                SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_IMAGEURL
        };
        SQLiteDatabase db = savedRecipeDbHelper.getReadableDatabase();
        Cursor cursor = db.query(SavedRecipeEntry.TABLE_NAME,columns,null,null,null,null,SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_IMAGEURL);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_IMAGEURL));
            recipeimg.add(name);
            cursor.moveToNext();
        }

        return recipeimg;
    }
}
