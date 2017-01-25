package ninja.tretton37.testmeas.testapplication;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ninja.tretton37.testmeas.testapplication.data.RecipeContentProvider;
import ninja.tretton37.testmeas.testapplication.data.RecipeContract;

/**
 * Created by ilkinartuc on 25/01/2017.
 */
public class NewRecipeActivity extends AppCompatActivity
{
    private EditText txtTitle;
    private EditText txtInfo;
    private Button save;

    private Uri recipeURI;

    private RecipeContentProvider recipeContentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newrecipe);

        Bundle extras = getIntent().getExtras();
        recipeURI = (savedInstanceState == null) ? null : (Uri) savedInstanceState
                .getParcelable(RecipeContentProvider.CONTENT_ITEM_TYPE);

        initLayout();

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (txtInfo.getText().toString().length() == 0 || txtTitle.getText().toString()
                        .length() == 0)
                {
                    return;
                }

                saveInfo();

            }


        });

    }

    private void saveInfo()
    {

        // only save if either summary or description
        // is available


        ContentValues values = new ContentValues();
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_TITLE, txtTitle.getText().toString());
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_TEXT, txtInfo.getText().toString());

        if (recipeURI == null)
        {
            // New recipe
            recipeURI = getContentResolver().insert(
                    RecipeContentProvider.CONTENT_URI, values);
        } else
        {
            // Update recipe
            getContentResolver().update(recipeURI, values, null, null);
        }


        setResult(RESULT_OK);
        finish();
    }

    private void initLayout()
    {
        save = (Button) findViewById(R.id.btn_activty_newrecipe_save);
        txtInfo = (EditText) findViewById(R.id.et_activity_newrecipe_info);
        txtTitle = (EditText) findViewById(R.id.et_activity_newrecipe_title);
    }
}
