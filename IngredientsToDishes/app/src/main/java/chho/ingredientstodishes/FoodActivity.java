package chho.ingredientstodishes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
    private String[] ingredients;
    private Food2ForkAPIServiceRecipeIngreds service;
    private SavedRecipeDbHelper savedRecipeDbHelper;

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

        //food = (Recipe)getIntent().getExtras().getSerializable("Recipe");


        recipeTitle.setText((String) getIntent().getExtras().getSerializable("recipename"));
        imageurl = (String) getIntent().getExtras().getSerializable("recipeimg");
        recipeid = (String) getIntent().getExtras().getSerializable("recipeid");

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,android.R.id.text1,ingredients);
                listView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("TEST", "Failed to get the response. ", t);
            }
        });

        Picasso.with(context).load(imageurl).into(foodimg);


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, FavoritesActivity.class);
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
                finish();
            }
        });

        setfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
