package ninja.tretton37.testmeas.testapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ninja.tretton37.testmeas.testapplication.data.RecipeContentProvider;
import ninja.tretton37.testmeas.testapplication.data.RecipeDbHelper;
import ninja.tretton37.testmeas.testapplication.presentation.MainActivityPresenter;
import ninja.tretton37.testmeas.testapplication.presentation.MainActivityPresenterImpl;
import ninja.tretton37.testmeas.testapplication.presentation.RecipeListCursorAdapter;

import static ninja.tretton37.testmeas.testapplication.data.RecipeContract.RecipeEntry.COLUMN_NAME_TITLE;
import static ninja.tretton37.testmeas.testapplication.data.RecipeContract.RecipeEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityView
{
    private ListView recipeList;
    private RecipeListCursorAdapter recipeListCursorAdapter;
    private MainActivityPresenter presenter;
    private Cursor oldOne;
    private Cursor newOne;
    private SQLiteDatabase db;
    private RecipeDbHelper handler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // TODO: Add a new recipe
                addItemToList();
//                throw new UnsupportedOperationException("Not yet implemented");
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        presenter = new MainActivityPresenterImpl();
        presenter.start(this);
        initLayout();
        initAdapters();
    }

    private void addItemToList()
    {
        startActivityForResult(new Intent(this, NewRecipeActivity.class), 1);
    }

    private void initAdapters()
    {

        handler = new RecipeDbHelper(this);
        //For testing
//        handler.add();
        db = handler.getWritableDatabase();
        oldOne = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
        recipeListCursorAdapter = new RecipeListCursorAdapter(this, oldOne, false);
        recipeList.setAdapter(recipeListCursorAdapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        newOne = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
        recipeListCursorAdapter.swapCursor(newOne);
        recipeListCursorAdapter.notifyDataSetChanged();

    }

    private void initLayout()
    {
        recipeList = (ListView) findViewById(R.id.lv_main_activity_recipe_list);
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id)
            {
                Intent intent = new Intent(getApplicationContext(), NewRecipeActivity.class);
                Uri todoUri = Uri.parse(RecipeContentProvider.CONTENT_URI + "/" + id);
                intent.putExtra(RecipeContentProvider.CONTENT_ITEM_TYPE, todoUri);
                intent.putExtra("RecipeID", id);
                startActivityForResult(intent, 2);
            }
        });


    }


    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        // TODO: Add a clear/empty database option instead
        if (id == R.id.action_delete_db)
        {
            db.delete(TABLE_NAME, null, null);
            newOne = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
            recipeListCursorAdapter.swapCursor(newOne);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // TODO: Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_view_all)
        {
            // TODO: show a list of all, sorted by title
            newOne = db.rawQuery("SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                            COLUMN_NAME_TITLE + " ASC",
                    null);
            recipeListCursorAdapter.swapCursor(newOne);

        } else if (id == R.id.nav_search)
        {
            // TODO: Let the user search by full text
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
