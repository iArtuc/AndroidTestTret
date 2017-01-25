package ninja.tretton37.testmeas.testapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListView;

import ninja.tretton37.testmeas.testapplication.data.RecipeDbHelper;
import ninja.tretton37.testmeas.testapplication.presentation.MainActivityPresenter;
import ninja.tretton37.testmeas.testapplication.presentation.MainActivityPresenterImpl;
import ninja.tretton37.testmeas.testapplication.presentation.RecipeListCursorAdapter;

import static ninja.tretton37.testmeas.testapplication.data.RecipeContract.RecipeEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityView
{
    private ListView recipeList;
    private RecipeListCursorAdapter recipeListCursorAdapter;
    private MainActivityPresenter presenter;

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
        //For test purpose;
        startActivity(new Intent(this, NewRecipeActivity.class));

    }

    private void updateItemInList()
    {

        //start activity with result with uri;
    }

    private void initAdapters()
    {

        RecipeDbHelper handler = new RecipeDbHelper(this);
        //For testing
//        handler.add();
        SQLiteDatabase db = handler.getWritableDatabase();
        Cursor todoCursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
        RecipeListCursorAdapter todoAdapter = new RecipeListCursorAdapter(this, todoCursor, false);
        recipeList.setAdapter(todoAdapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void initLayout()
    {
        recipeList = (ListView) findViewById(R.id.lv_main_activity_recipe_list);


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
        if (id == R.id.action_settings)
        {
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
        } else if (id == R.id.nav_search)
        {
            // TODO: Let the user search by full text
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
