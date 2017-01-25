package ninja.tretton37.testmeas.testapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ilkinartuc on 25/01/2017.
 */
public class NewRecipeActivity extends AppCompatActivity
{
    private EditText txtTitle;
    private EditText txtInfo;
    private Button save;

    private Uri todoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newrecipe);

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


            }
        });

    }

    private void initLayout()
    {
        save = (Button) findViewById(R.id.btn_activty_newrecipe_save);
        txtInfo = (EditText) findViewById(R.id.et_activity_newrecipe_info);
        txtTitle = (EditText) findViewById(R.id.et_activity_newrecipe_title);
    }
}
