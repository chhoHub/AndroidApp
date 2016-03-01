package chho.ingredientstodishes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import chho.ingredientstodishes.data.RecipeData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.JacksonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Gary on 2/1/2016.
 */
public class SelectingRecipeActivity extends Activity {
    private ImageButton fav;
    private ImageButton ingred;
    private ImageButton recipe;
    private ImageButton timer;

    private Food2ForkAPIServiceGetRecipe service;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.selectingrecipe_activity);

        fav = (ImageButton)findViewById(R.id.FavoriteIcon);
        ingred = (ImageButton)findViewById(R.id.IngredientsIcon);
        recipe = (ImageButton)findViewById(R.id.RecipeIcon);
        timer = (ImageButton)findViewById(R.id.TimerIcon);

        listView = (ListView) findViewById(R.id.recipes);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectingRecipeActivity.this, FavoritesActivity.class);
                startActivity(intent);
               // finish();
            }
        });
        ingred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectingRecipeActivity.this, IngredientsActivity.class);
                startActivity(intent);
              //  finish();
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectingRecipeActivity.this, TimerActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://food2fork.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(Food2ForkAPIServiceGetRecipe.class);
        Call<RecipeData> RecipeDataCall = service.getSearchedRecpies(
                "  "//,
                );

        RecipeDataCall.enqueue(new Callback<RecipeData>() {
            @Override
            public void onResponse(Response<RecipeData> response) {
                RecipeAdapter recipeAdapter = new RecipeAdapter(SelectingRecipeActivity.this,R.layout.selectingrecipe_activity, response.body().getRecipes().getRecipe());
                listView.setAdapter(recipeAdapter);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("TEST", "Failed to get the response. ", t);
            }
        });
    }
}
