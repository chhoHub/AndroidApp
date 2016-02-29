package chho.ingredientstodishes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gary on 2/28/2016.
 */
public class IngredientDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_INGREDIENT_TABLE =
            "CREATE TABLE " + IngredientEntry.TABLE_NAME + " ( "
                    + IngredientEntry.INGREDIENT_COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
                    + IngredientEntry.INGREDIENT_COLUMN_NAME_INGREDIENT + " TEXT )";

    public IngredientDBHelper(Context context) {
        super(context, "ingredient-db", null, 1);
    }

    public IngredientDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INGREDIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + IngredientEntry.TABLE_NAME);
        onCreate(db);
    }
}
