package ninja.tretton37.testmeas.testapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ninja.tretton37.testmeas.testapplication.data.RecipeContentProvider;

import static ninja.tretton37.testmeas.testapplication.data.RecipeContract.RecipeEntry.COLUMN_NAME_TEXT;
import static ninja.tretton37.testmeas.testapplication.data.RecipeContract.RecipeEntry.COLUMN_NAME_TITLE;

/**
 * Created by ilkinartuc on 25/01/2017.
 */
public class NewRecipeActivity extends AppCompatActivity
{
    private EditText txtTitle;
    private EditText txtInfo;
    private Button save;
    private Button delete;

    private Uri recipeURI;
    private long recipeID = -1;

    private RecipeContentProvider recipeContentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newrecipe);

        initLayout();
        clickListeners();


        Bundle extras = getIntent().getExtras();
        recipeURI = (savedInstanceState == null) ? null : (Uri) savedInstanceState
                .getParcelable(RecipeContentProvider.CONTENT_ITEM_TYPE);

        if (extras != null)
        {
            recipeURI = extras
                    .getParcelable(RecipeContentProvider.CONTENT_ITEM_TYPE);
            recipeID = getIntent().getLongExtra("RecipeID", -1);
            fillLayoutData(recipeURI);
        }

        if (recipeURI == null)
        {
            delete.setEnabled(false);
        } else
        {
            delete.setEnabled(true);
        }


    }

    private void fillLayoutData(Uri recipeURI)
    {
        String[] projection = {COLUMN_NAME_TEXT,
                COLUMN_NAME_TITLE};
        Cursor cursor = getContentResolver().query(recipeURI, projection, null, null,
                null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            String title = cursor.getString(cursor
                    .getColumnIndexOrThrow(COLUMN_NAME_TITLE));
            String text = cursor.getString(cursor
                    .getColumnIndexOrThrow(COLUMN_NAME_TEXT));

            txtTitle.setText(title);
            txtInfo.setText(text);


            cursor.close();
        }
    }

    private void clickListeners()
    {
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
        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                deleteInfo();
            }
        });
    }

    private void deleteInfo()
    {
        Uri uri = Uri.parse(RecipeContentProvider.CONTENT_URI + "/"
                + recipeID);
        getContentResolver().delete(uri, null, null);
        setResult(RESULT_OK);
        finish();
    }

    private void saveInfo()
    {

        // only save if either summary or description
        // is available


        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE, txtTitle.getText().toString());
        values.put(COLUMN_NAME_TEXT, txtInfo.getText().toString());

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
        delete = (Button) findViewById(R.id.btn_activty_newrecipe_delete);
        txtInfo = (EditText) findViewById(R.id.et_activity_newrecipe_info);
        txtTitle = (EditText) findViewById(R.id.et_activity_newrecipe_title);
    }
}
