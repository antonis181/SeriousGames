package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.List;

public class GameList extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, NavigationView.OnNavigationItemSelectedListener {

    private  GameViewModel gameViewModel;
    private int currentGameId;
    private static final String GAME_ID = "GAME_ID";
    private static final String USER_ID = "USER_ID";
    private static final String DIFFICULTY = "DIFFICULTY";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    List<Game> allgamesList = new ArrayList<Game>();
    List<Integer> allgamesIDList = new ArrayList<Integer>();

    int useridfromIntennt = -1;

    private boolean mToolBarNavigationListenerIsRegistered = false;
    private String preferenceDifficulty;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        setupPreferences();

//        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
//
//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbarLayout);
//        collapsingToolbarLayout.setTitle("username");
//
//
//
        Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("username");
        setSupportActionBar(toolbar);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
        }
        else
        {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

        }

//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingtoolbarLayout);
//        collapsingToolbar.setTitle("username");




        final GameAdapter gameAdapter = new GameAdapter();
        recyclerView.setAdapter(gameAdapter);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        useridfromIntennt = getIntent().getExtras().getInt("USERID");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.gameList);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




//        gameViewModel.getGetallgames().observe(this, new Observer<List<Game>>() {
//            @Override
//            public void onChanged(@Nullable List<Game> games) {
//                gameAdapter.setGames(games);
//            }
//        });

        allgamesList = gameViewModel.loadAllGames();
//        Iterator<Game>  gameslistIterator =     allgamesList.iterator();
//        while (gameslistIterator.hasNext())
//        {
//            allgamesIDList.add(gameslistIterator.next().getId());
//        }

        gameAdapter.setGames(allgamesList);



        gameAdapter.setOnClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game) {
                Toast.makeText(GameList.this, "to patisa "+game.getName(), Toast.LENGTH_SHORT).show();
                //edw ton stelnw sto paixnidi
                //edw na tsekarw to name toy paixnidioy gia na tons telnvw sto swsto paixnidi
                if (game.getName().equals("Rock"))
                {
                    currentGameId = game.getId();
                    Intent intent = new Intent(GameList.this,RockPaperScissors.class);
                    Bundle extras = new Bundle();
                    extras.putInt(USER_ID,useridfromIntennt);
                    extras.putInt(GAME_ID,currentGameId);
                    extras.putString(DIFFICULTY,preferenceDifficulty);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
                else if (game.getName().equals("Calcution"))
                {
                    currentGameId = game.getId();
                    Intent intent = new Intent(GameList.this,ScalingGame.class);
                    Bundle extras = new Bundle();
                    extras.putInt(USER_ID,useridfromIntennt);
                    extras.putInt(GAME_ID,currentGameId);
                    extras.putString(DIFFICULTY,preferenceDifficulty);
                    intent.putExtras(extras);
                    startActivity(intent);

                }
                else if (game.getName().equals("MemoryMatrix"))
                {
                    currentGameId = game.getId();
                    Intent intent = new Intent(GameList.this,MemoryMatrix.class);
                    Bundle extras = new Bundle();
                    extras.putInt(USER_ID,useridfromIntennt);
                    extras.putInt(GAME_ID,currentGameId);
                    extras.putString(DIFFICULTY,preferenceDifficulty);
                    intent.putExtras(extras);
                    startActivity(intent);

                }
                else if (game.getName().equals("ObjectSelector"))
                {
                    currentGameId = game.getId();
                    Intent intent = new Intent(GameList.this,ObjectSelector.class);
                    Bundle extras = new Bundle();
                    extras.putInt(USER_ID,useridfromIntennt);
                    extras.putInt(GAME_ID,currentGameId);
                    extras.putString(DIFFICULTY,preferenceDifficulty);
                    intent.putExtras(extras);
                    startActivity(intent);

                }
//                else if (game.getName().equals("OrderGame"))
//                {
//                    currentGameId = game.getId();
//                    Intent intent = new Intent(GameList.this,OrderGame.class);
//                    Bundle extras = new Bundle();
//                    extras.putInt(USER_ID,useridfromIntennt);
//                    extras.putInt(GAME_ID,currentGameId);
//                    intent.putExtras(extras);
//                    startActivity(intent);
//
//                }

            }
        });



    }


    private void enableViews(boolean enable) {

        // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
        // when you enable on one, you disable on the other.
        // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
        if(enable) {
            //You may not want to open the drawer on swipe from the left in this case
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            // Remove hamburger
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            // Show back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.
            if(!mToolBarNavigationListenerIsRegistered) {
                actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Doesn't have to be onBackPressed
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            //You must regain the power of swipe for the drawer.
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // Remove back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            actionBarDrawerToggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }

        // So, one may think "Hmm why not simplify to:
        // .....
        // getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        // mDrawer.setDrawerIndicatorEnabled(!enable);
        // ......
        // To re-iterate, the order in which you enable and disable views IS important #dontSimplify.
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }


    private void setupPreferences()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceDifficulty = sharedPreferences.getString(getString(R.string.difficultyKey),getString(R.string.mediumValue));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        if (key.equals(getString(R.string.difficultyKey)))
        {
            preferenceDifficulty = sharedPreferences.getString(getString(R.string.difficultyKey),getString(R.string.mediumValue));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {
            case R.id.nav_settings:
                Intent intentSettings = new Intent(this,Settings.class);
                startActivity(intentSettings);
                Animatoo.animateFade(GameList.this);
                break;
            case R.id.nav_statistics:
                Intent intentStats = new Intent(this,StatisticsTable.class);
                Bundle extras = new Bundle();
                extras.putInt(USER_ID,useridfromIntennt);
                // extras.putIntegerArrayList("allgamesIDList",(ArrayList<Integer>) allgamesIDList);
                intentStats.putExtras(extras);
                //intentStats.putIntegerArrayListExtra("allgamesIDList",(ArrayList<Integer>) allgamesIDList);
                startActivity(intentStats);
                Animatoo.animateDiagonal(GameList.this);
                break;

            case R.id.nav_logout:
                finish();
                break;
        }



        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}