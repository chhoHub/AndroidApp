package chho.ingredientstodishes;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.widget.*;

/**
 * Created by Gary on 2/1/2016.
 */
public class IngredientsActivity extends Activity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{
    private ImageButton fav;
    private ImageButton ingred;
    private ImageButton recipe;
    private ImageButton timer;
    private ListView ToplistView;
    private ListView BottomlistView;
    private FoodListAdapter foodListAdapter;
    private YourListAdapter yourListAdapter;
    private Button clear;
    private Button addfood;
    private List<String> foodlist;
    private List<String> yourlist = new ArrayList<String>();
    private SearchView searchView;

    private IngredientDBHelper ingredientDBHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ingredientslist_activity);

        fav = (ImageButton)findViewById(R.id.FavoriteIcon);
        ingred = (ImageButton)findViewById(R.id.IngredientsIcon);
        recipe = (ImageButton)findViewById(R.id.RecipeIcon);
        timer = (ImageButton)findViewById(R.id.TimerIcon);
        clear = (Button)findViewById(R.id.clear);
        addfood = (Button) findViewById(R.id.foodAdd);
        ToplistView = (ListView) findViewById(R.id.searchlist);
        BottomlistView = (ListView) findViewById(R.id.ingredlist);
        searchView = (SearchView) findViewById(R.id.searchview);

        ingredientDBHelper = new IngredientDBHelper(this);

        ContentValues contentValues = new ContentValues();

        db = ingredientDBHelper.getWritableDatabase();
        String count = "SELECT COUNT(*) FROM " + IngredientEntry.TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount <= 0){
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(getAssets().open("ingredientsListv2.2.txt")));
                String mLine;
                while ((mLine = br.readLine()) != null) {
                    contentValues.put(IngredientEntry.INGREDIENT_COLUMN_NAME_INGREDIENT, mLine);
                    db = ingredientDBHelper.getWritableDatabase();
                    db.insert(IngredientEntry.TABLE_NAME, "null", contentValues);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        Log.e("FILE", "File closed Error");
                    }
                }
            }
        }
        loadListView();

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });
        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this, SelectingRecipeActivity.class);
                startActivity(intent);
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        yourListAdapter = new YourListAdapter(this, R.layout.yourlistdisplay_activity, yourlist, foodlist);
        BottomlistView.setAdapter(yourListAdapter);
    }

    private void loadListView() {
        foodlist = readIngredientListFromDb(yourlist);
        foodListAdapter = new FoodListAdapter(this, R.layout.searchlistdisplay_activity, foodlist, yourlist);
        ToplistView.setAdapter(foodListAdapter);
    }

    private List<String> readIngredientListFromDb(List<String> mylist){

        List<String> ingredients = new ArrayList<String>();
        String[] columns = {
                IngredientEntry.INGREDIENT_COLUMN_NAME_ID,
                IngredientEntry.INGREDIENT_COLUMN_NAME_INGREDIENT};
        SQLiteDatabase db = ingredientDBHelper.getReadableDatabase();
        Cursor cursor = db.query(IngredientEntry.TABLE_NAME,columns,null,null,null,null,IngredientEntry.INGREDIENT_COLUMN_NAME_INGREDIENT);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(IngredientEntry.INGREDIENT_COLUMN_NAME_INGREDIENT));
            ingredients.add(name);
            cursor.moveToNext();
        }

        if(mylist.size() > 0) {
            ingredients.removeAll(mylist);
        }

        return ingredients;
    }

    public List<String> findIngred(String productname, List<String> mylist) {

        List<String> ingredlist = new ArrayList<String>();

        String query = "Select * FROM " + IngredientEntry.TABLE_NAME + " WHERE " + IngredientEntry.INGREDIENT_COLUMN_NAME_INGREDIENT + " LIKE " + "'%" + productname + "%'";

        SQLiteDatabase db = ingredientDBHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                ingredlist.add(cursor.getString(cursor.getColumnIndex(IngredientEntry.INGREDIENT_COLUMN_NAME_INGREDIENT)));
                cursor.moveToNext();
            }
            cursor.close();

            if(mylist.size() > 0) {
                ingredlist.removeAll(mylist);
            }
            return ingredlist;
        }
        db.close();

        return ingredlist;
    }

    @Override
    public boolean onClose() {
        ToplistView.setAdapter(foodListAdapter);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        foodlist = findIngred(query, yourlist);
        foodListAdapter = new FoodListAdapter(this, R.layout.searchlistdisplay_activity, foodlist, yourlist);
        ToplistView.setAdapter(foodListAdapter);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.isEmpty()){
            foodlist = findIngred(newText, yourlist);
            foodListAdapter = new FoodListAdapter(this, R.layout.searchlistdisplay_activity, foodlist, yourlist);
            ToplistView.setAdapter(foodListAdapter);
        } else {
            foodlist = readIngredientListFromDb(yourlist);
            foodListAdapter = new FoodListAdapter(this, R.layout.searchlistdisplay_activity, foodlist, yourlist);
            ToplistView.setAdapter(foodListAdapter);
        }
        return false;
    }
}
