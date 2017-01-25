package ninja.tretton37.testmeas.testapplication.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import static ninja.tretton37.testmeas.testapplication.data.RecipeContract.RecipeEntry.*;

public class RecipeContract
{


    // TODO: Fix this create string
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME_TEXT + " text not null, "
            + COLUMN_NAME_TITLE + " text not null,"
            + ");";


    private RecipeContract()
    {
    }

    static void onCreate(SQLiteDatabase database)
    {
        database.execSQL(DATABASE_CREATE);
    }

    static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL("DROP TABLE IF EXISTS " + RecipeEntry.TABLE_NAME);
        onCreate(database);
    }

    public static class RecipeEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TEXT = "text";

    }
}
