package ninja.tretton37.testmeas.testapplication.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

public class RecipeContentProvider extends ContentProvider
{
    public RecipeContentProvider()
    {
    }

    private RecipeDbHelper mOpenHelper;
    private SQLiteDatabase mDb;

    // used for the UriMacher
    private static final int RECIPES = 1;
    private static final int RECIPE_ID = 2;

    private static final String AUTHORITY = "ninja.tretton37.testmeas.testapplication.data";
    private static final String BASE_PATH = "recipe";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/recipes";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/recipe";


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static
    {
        // TODO: Create your own uri:s instead of these
        sUriMatcher.addURI(AUTHORITY, BASE_PATH, RECIPES);
        sUriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", RECIPE_ID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        // TODO: Implement this to handle requests to delete one or more recipes.
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = mOpenHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case RECIPES:
                rowsDeleted = sqlDB.delete(RecipeContract.RecipeEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case RECIPE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(
                            RecipeContract.RecipeEntry.TABLE_NAME,
                            RecipeContract.RecipeEntry.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(
                            RecipeContract.RecipeEntry.TABLE_NAME,
                            RecipeContract.RecipeEntry.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri)
    {
        // TODO: Ignore this if you want to
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        // TODO: Implement this to handle requests to insert a new recipe.

        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = mOpenHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case RECIPES:
                id = sqlDB.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }



        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);


    }

    @Override
    public boolean onCreate()
    {
        mOpenHelper = new RecipeDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder)
    {
        // TODO: Implement this to handle query requests.
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // check if the caller has requested a column which does not exists
        checkColumns(projection);
        // Set the table
        queryBuilder.setTables(RecipeContract.RecipeEntry.TABLE_NAME);


        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case RECIPES:
                break;
            case RECIPE_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(RecipeContract.RecipeEntry.COLUMN_ID + "="
                        + uri.getLastPathSegment());

                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }


        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs)
    {
        // TODO: Implement this to handle requests to update one or more recipes.
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = mOpenHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType)
        {
            case RECIPES:
                rowsUpdated = sqlDB.update(RecipeContract.RecipeEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case RECIPE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection))
                {
                    rowsUpdated = sqlDB.update(RecipeContract.RecipeEntry.TABLE_NAME,
                            values,
                            RecipeContract.RecipeEntry.COLUMN_ID + "=" + id,
                            null);
                } else
                {
                    rowsUpdated = sqlDB.update(RecipeContract.RecipeEntry.TABLE_NAME,
                            values,
                            RecipeContract.RecipeEntry.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection)
    {
        String[] available = {RecipeContract.RecipeEntry.COLUMN_ID,
                RecipeContract.RecipeEntry.COLUMN_NAME_TITLE, RecipeContract.RecipeEntry.COLUMN_NAME_TEXT,};
        if (projection != null)
        {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns))
            {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }
}
