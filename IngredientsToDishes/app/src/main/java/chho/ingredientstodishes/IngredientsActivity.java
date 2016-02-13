package chho.ingredientstodishes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gary on 2/1/2016.
 */
public class IngredientsActivity extends Activity {
    private ImageButton fav;
    private ImageButton ingred;
    private ImageButton recipe;
    private ImageButton timer;
    private ListView ToplistView;
    private ListView BottomlistView;
    private FoodListAdapter foodListAdapter;
    private YourListAdapter yourListAdapter;
    private Button clear;

    private List<String> ingredlist = new ArrayList<String>() {
        {
            add ("Apple");
            add ("Orange");
            add ("Banana");
            add ("Grape");
            add ("Pear");
        }
    };

    private List<String> foodlist = new ArrayList<>(ingredlist);
    private List<String> yourlist = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ingredientslist_activity);

        fav = (ImageButton)findViewById(R.id.FavoriteIcon);
        ingred = (ImageButton)findViewById(R.id.IngredientsIcon);
        recipe = (ImageButton)findViewById(R.id.RecipeIcon);
        timer = (ImageButton)findViewById(R.id.TimerIcon);
        clear = (Button)findViewById(R.id.clear);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this, FavoritesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this, SelectingRecipeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this, TimerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yourlist = new ArrayList<String>();
                foodlist = new ArrayList<>(ingredlist);
                finish();
                startActivity(getIntent());
            }
        });

        ToplistView = (ListView) findViewById(R.id.searchlist);
        foodListAdapter = new FoodListAdapter(this, R.layout.searchlistdisplay_activity, foodlist, yourlist);
        ToplistView.setAdapter(foodListAdapter);

        BottomlistView = (ListView) findViewById(R.id.ingredlist);
        yourListAdapter = new YourListAdapter(this, R.layout.yourlistdisplay_activity, yourlist, foodlist);
        BottomlistView.setAdapter(yourListAdapter);



    }
}
