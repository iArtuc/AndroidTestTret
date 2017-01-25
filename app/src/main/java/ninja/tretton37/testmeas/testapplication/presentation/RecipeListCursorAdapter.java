package ninja.tretton37.testmeas.testapplication.presentation;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ninja.tretton37.testmeas.testapplication.R;


public class RecipeListCursorAdapter extends CursorAdapter
{
    public RecipeListCursorAdapter(Context context, Cursor c, boolean autoRequery)
    {
        super(context, c, autoRequery);
    }

    public RecipeListCursorAdapter(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        // TODO: Implement using RecipeViewHolder
        RecipeViewHolder viewHolder;
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list_custom_item, parent, false);
        viewHolder = new RecipeViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        // TODO: Implement using RepicViewHolder
        RecipeViewHolder viewHolder = (RecipeViewHolder) view.getTag();

        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String info = cursor.getString(cursor.getColumnIndexOrThrow("text"));

        viewHolder.getTitle().setText(title);
        viewHolder.getInfo().setText(info);
    }

    private class RecipeViewHolder
    {


        // TODO: Use and implement
        private TextView title;
        private TextView info;

        public RecipeViewHolder(View v)
        {
            title = (TextView) v.findViewById(R.id.txt_recipe_list_custom_item_title);
            info = (TextView) v.findViewById(R.id.txt_recipe_list_custom_item_info);
        }

        public TextView getTitle()
        {
            return title;
        }

        public TextView getInfo()
        {
            return info;
        }
    }
}
