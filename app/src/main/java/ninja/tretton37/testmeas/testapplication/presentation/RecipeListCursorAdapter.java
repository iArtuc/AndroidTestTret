package ninja.tretton37.testmeas.testapplication.presentation;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;


public class RecipeListCursorAdapter extends CursorAdapter {
    public RecipeListCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public RecipeListCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // TODO: Implement using RecipeViewHolder
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // TODO: Implement using RepicViewHolder
    }

    private class RecipeViewHolder {
        // TODO: Use and implement
    }
}
