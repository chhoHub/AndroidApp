package chho.ingredientstodishes;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.os.Handler;
import android.widget.*;

/**
 * Created by Gary on 2/1/2016.
 */
public class IngredientsActivity extends Activity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {
    private ImageButton fav;
    private ImageButton ingred;
    private ImageButton recipe;
    private ImageButton timer;
    private ListView ToplistView;
    private ListView BottomlistView;
    private FoodListAdapter foodListAdapter;
    private YourListAdapter yourListAdapter;
    private Button clear;
    private SearchView searchView;
    private SearchHelper mDbHelper;
    private Button addfood;

    private List<String> ingredlist = new ArrayList<String>();

    private List<String> foodlist;
    private List<String> yourlist = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ingredientslist_activity);

        AssetManager am = getAssets();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open("ingredientsListv2.2.txt")));
            String mLine;
            while ((mLine = br.readLine()) != null) {
                ingredlist.add(mLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        foodlist = new ArrayList<>(ingredlist);

        fav = (ImageButton)findViewById(R.id.FavoriteIcon);
        ingred = (ImageButton)findViewById(R.id.IngredientsIcon);
        recipe = (ImageButton)findViewById(R.id.RecipeIcon);
        timer = (ImageButton)findViewById(R.id.TimerIcon);
        clear = (Button)findViewById(R.id.clear);

        addfood = (Button) findViewById(R.id.resultfoodAdd);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this, FavoritesActivity.class);
                startActivity(intent);
            //    finish();
            }
        });
        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this, SelectingRecipeActivity.class);
                startActivity(intent);
              //  finish();
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this, TimerActivity.class);
                startActivity(intent);
               // finish();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        searchView = (SearchView) findViewById(R.id.searchview);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        mDbHelper = new SearchHelper(this);
        mDbHelper.open();
        mDbHelper.deleteAllNames();

        for (String name : foodlist) {
            mDbHelper.createList(name);
        }



    }

    @Override
    public boolean onClose() {
        ToplistView.setAdapter(foodListAdapter);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        displayResults(query + "*");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.isEmpty()){
            displayResults(newText + "*");
        } else {
            ToplistView.setAdapter(foodListAdapter);
        }
        return false;
    }

    private void displayResults(String query) {

        Cursor cursor = mDbHelper.searchByInputText((query != null ? query : "@@@@"));

        if (cursor != null) {

            String[] from = new String[] {SearchHelper.COLUMN_NAME};

            // Specify the view where we want the results to go
            int[] to = new int[] {R.id.searchresulttextview};

            List<String> stringList = new ArrayList<String>(Arrays.asList(from));
            foodListAdapter = new FoodListAdapter(this, R.layout.searchlistdisplay_activity, stringList, yourlist);
            ToplistView.setAdapter(foodListAdapter);


            // Create a simple cursor adapter to keep the search data
           // SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.searchresult_activity, cursor, from, to);
           // ToplistView.setAdapter(cursorAdapter);



            // Click listener for the searched item that was selected
            ToplistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // Get the cursor, positioned to the corresponding row in the result set
                    Cursor cursor = (Cursor) ToplistView.getItemAtPosition(position);

                    // Get the state's capital from this row in the database.
                    String selectedName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    Toast.makeText(IngredientsActivity.this, selectedName, Toast.LENGTH_SHORT).show();

                    // Set the default adapter
                    //ToplistView.setAdapter(foodListAdapter);

                    // Find the position for the original list by the selected name from search
                    for (int pos = 0; pos < foodlist.size(); pos++) {
                        if (foodlist.get(pos).equals(selectedName)) {
                            position = pos;
                            break;
                        }
                    }

                    final int finalPosition1 = position;
                    addfood.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final String food = foodlist.get(finalPosition1);

                            yourlist.add(food);
                            foodlist.remove(finalPosition1);
                            //this.notifyDataSetChanged();

                        }
                    });

                    // Create a handler. This is necessary because the adapter has just been set on the list again and
                    // the list might not be finished setting the adapter by the time we perform setSelection.
                    Handler handler = new Handler();
                    final int finalPosition = position;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            ToplistView.setSelection(finalPosition);
                        }
                    });

                    searchView.setQuery("", true);
                }

            });

        }
    }
}
