package chho.ingredientstodishes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yusun on 2/15/16.
 */
public class SavedRecipeDbHelper extends SQLiteOpenHelper {

    private static final String TYPE_TEXT = " TEXT ";

    private static final String CREATE_SAVED_RECIPE_TABLE =
            "CREATE TABLE " + SavedRecipeEntry.TABLE_NAME + " ( "
                    + SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_ID + TYPE_TEXT + " , "
                    + SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_NAME + TYPE_TEXT + " , "
                    + SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_IMAGEURL + TYPE_TEXT + " , "
                    + SavedRecipeEntry.SAVEDRECIPE_COLUMN_NAME_INGREDIENTS + TYPE_TEXT + ")";

    public SavedRecipeDbHelper(Context context) {
        super(context, "SavedRecipe-db", null, 1);
    }

    public SavedRecipeDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SAVED_RECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // delete db first
        db.execSQL("DROP TABLE " + SavedRecipeEntry.TABLE_NAME);
        // re-create the db
        onCreate(db);
    }
}
