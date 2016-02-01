package chho.ingredientstodishes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Gary on 2/1/2016.
 */
public class TimerActivity extends Activity {
    private ImageButton fav;
    private ImageButton ingred;
    private ImageButton recipe;
    private ImageButton timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.timer_activity);

        fav = (ImageButton)findViewById(R.id.FavoriteIcon);
        ingred = (ImageButton)findViewById(R.id.IngredientsIcon);
        recipe = (ImageButton)findViewById(R.id.RecipeIcon);
        timer = (ImageButton)findViewById(R.id.TimerIcon);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivity.this, FavoritesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ingred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivity.this, IngredientsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivity.this, SelectingRecipeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
