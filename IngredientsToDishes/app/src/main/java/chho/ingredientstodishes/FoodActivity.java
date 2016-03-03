package chho.ingredientstodishes;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

import chho.ingredientstodishes.data.RecipeIngredients;
import retrofit2.Call;
import retrofit2.JacksonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Gary on 2/1/2016.
 */
public class FoodActivity extends Activity implements Serializable {
    private ImageButton fav;
    private ImageButton ingred;
    private ImageButton recipe;
    private ImageButton timer;
    private ImageButton back;
    private ImageButton setfav;
    private Recipe food;
    private TextView recipeTitle;
    private ImageView foodimg;
    private ListView listView;
    private Context context = FoodActivity.this;
    private String imageurl;
    private String recipeid;
    private String recipename;
    private String[] ingredients;
    private String ingredientsToString;
    private Food2ForkAPIServiceRecipeIngreds service;
    private SavedRecipeDbHelper savedRecipeDbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.chosenrecipe_activity);

        fav = (ImageButton)findViewById(R.id.FavoriteIcon);
        ingred = (ImageButton)findViewById(R.id.IngredientsIcon);
        recipe = (ImageButton)findViewById(R.id.RecipeIcon);
        timer = (ImageButton)findViewById(R.id.TimerIcon);
        back = (ImageButton) findViewById(R.id.back);
        setfav = (ImageButton) findViewById(R.id.fav);
        foodimg = (ImageView) findViewById(R.id.foodpic);
        recipeTitle = (TextView)findViewById(R.id.recipeTitle);
        listView = (ListView) findViewById(R.id.recipeingreds);

        savedRecipeDbHelper = new SavedRecipeDbHelper(this);

        recipename = (String) getIntent().getExtras().getSerializable("recipename");
        recipeTitle.setText(recipename);
        imageurl = (String) getIntent().getExtras().getSerializable("recipeimg");
        recipeid = (String) getIntent().getExtras().getSerializable("recipeid");

        String longingred = (String) getIntent().getExtras().getSerializable("recipeingred");

        if(longingred != null) {
            ingredients = convertStringToArray(longingred);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, ingredients);
            listView.setAdapter(adapter);
            ingredientsToString = convertArrayToString(ingredients);
        }
        else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://food2fork.com/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            service = retrofit.create(Food2ForkAPIServiceRecipeIngreds.class);
            Call<RecipeIngredients> RecipeIngredDataCall = service.getRecipeIngreds(
                    "8de033bf118b3d4c13a70c2401faa23b",
                    recipeid
            );
            RecipeIngredDataCall.enqueue(new retrofit2.Callback<RecipeIngredients>() {
                @Override
                public void onResponse(Response<RecipeIngredients> response) {
                    RecipeIngredients recipeIngredients = response.body();

                    ingredients = recipeIngredients.getRecipe().getIngredients();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, ingredients);
                    listView.setAdapter(adapter);
                    ingredientsToString = convertArrayToString(ingredients);

                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("TEST", "Failed to get the response. ", t);
                }
            });
        }

        Picasso.with(context).load(imageurl).into(foodimg);


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, FavoritesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        ingred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, IngredientsActivity.class);
                startActivity(intent);
            }
        });
        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, SelectingRecipeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //finish();
            }
        });

        setfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues contentValues = new ContentValues();
                contentValues.put(SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID, recipeid);
                contentValues.put(SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_IMAGEURL, imageurl);
                contentValues.put(SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_NAME, recipename);
                contentValues.put(SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_INGREDIENTS,ingredientsToString);
                SQLiteDatabase db = savedRecipeDbHelper.getWritableDatabase();

                String Query = "Select * from " + SavedRecipeEntry.TABLE_NAME + " where " + SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID + " = " + "'" +recipeid + "'";
                Cursor cursor = db.rawQuery(Query, null);
                if (cursor.getCount() <= 0){
                    db.insert(SavedRecipeEntry.TABLE_NAME, "null", contentValues);
                    Log.i("TABLE", "data created!");
                    Toast.makeText(context, "Recipe added!",
                            Toast.LENGTH_SHORT).show();

                } else {
                    db.delete(SavedRecipeEntry.TABLE_NAME, SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID + " = " + "'" +recipeid + "'", null);
                    Log.i("TABLE", "data deleted!");
                    Toast.makeText(context, "Recipe removed!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+"__,__";
            }
        }
        return str;
    }

    public String[] convertStringToArray(String str){
        String[] arr = str.split("__,__");
        return arr;
    }
}
