package ninja.tretton37.testmeas.testapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static ninja.tretton37.testmeas.testapplication.data.RecipeContract.RecipeEntry.COLUMN_NAME_TEXT;
import static ninja.tretton37.testmeas.testapplication.data.RecipeContract.RecipeEntry.COLUMN_NAME_TITLE;
import static ninja.tretton37.testmeas.testapplication.data.RecipeContract.RecipeEntry.TABLE_NAME;


public class RecipeDbHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "recipes.db";

    public RecipeDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        RecipeContract.onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        RecipeContract.onUpgrade(db, oldVersion, newVersion);
    }

    public boolean add()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TEXT, "test purpose info");
        values.put(COLUMN_NAME_TITLE, "test purpose title");
        sqLiteDatabase.insert(TABLE_NAME, null, values);
        return true;

    }
}
