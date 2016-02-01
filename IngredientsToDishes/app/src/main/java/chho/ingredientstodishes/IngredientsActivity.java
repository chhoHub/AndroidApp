package chho.ingredientstodishes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Gary on 2/1/2016.
 */
public class IngredientsActivity extends Activity {
    private ImageButton fav;
    private ImageButton ingred;
    private ImageButton recipe;
    private ImageButton timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ingredientslist_activity);

//        fav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(IngredientsActivity.this, FavoritesActivity.class);
//                startActivity(intent);
//            }
//        });
//        recipe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(IngredientsActivity.this, SelectingRecipeActivity.class);
//                startActivity(intent);
//            }
//        });
//        timer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(IngredientsActivity.this, TimerActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
