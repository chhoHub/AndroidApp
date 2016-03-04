package chho.ingredientstodishes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gary on 2/1/2016.
 */
public class SelectingRecipeActivity extends Activity implements Serializable {
    private ImageButton fav;
    private ImageButton ingred;
    private ImageButton timer;

    private ListView listView;
    private List<String> recipe_name  = new ArrayList<String>();
    private List<String> recipe_sourceurl = new ArrayList<String>();
    private List<String> recipe_id = new ArrayList<String>();
    private List<String> recipe_imageurl = new ArrayList<String>();

    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.selectingrecipe_activity);

        fav = (ImageButton)findViewById(R.id.FavoriteIcon);
        ingred = (ImageButton)findViewById(R.id.IngredientsIcon);
        timer = (ImageButton)findViewById(R.id.TimerIcon);

        listView = (ListView) findViewById(R.id.recipes);

        try {
            recipe_name = (List<String>) getIntent().getExtras().getSerializable("recipe_name");
            recipe_id = (List<String>) getIntent().getExtras().getSerializable("recipe_id");
            recipe_sourceurl = (List<String>) getIntent().getExtras().getSerializable("recipe_sourceurl");
            recipe_imageurl = (List<String>) getIntent().getExtras().getSerializable("recipe_imageurl");
            String msg = (String) getIntent().getExtras().getSerializable("toast");
            Toast.makeText(SelectingRecipeActivity.this, msg,
                    Toast.LENGTH_SHORT).show();
            recipeAdapter = new RecipeAdapter(SelectingRecipeActivity.this,R.layout.selectingrecipe_activity,recipe_id,recipe_name,recipe_sourceurl,recipe_imageurl);
            listView.setAdapter(recipeAdapter);
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(SelectingRecipeActivity.this, "No recipes generated! Please press update in Ingredients List!",
                    Toast.LENGTH_SHORT).show();
        }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectingRecipeActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });
        ingred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectingRecipeActivity.this, IngredientsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectingRecipeActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });
    }
}
