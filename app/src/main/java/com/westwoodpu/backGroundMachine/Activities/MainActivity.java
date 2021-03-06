package com.westwoodpu.backGroundMachine.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.westwoodpu.backGroundMachine.Fragments.CollectionsFragment;
import com.westwoodpu.backGroundMachine.Fragments.FavoriteFragment;
import com.westwoodpu.backGroundMachine.Fragments.PhotosFragment;
import com.westwoodpu.backGroundMachine.R;
import com.westwoodpu.backGroundMachine.Utils.Functions;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        PhotosFragment homeFragment = new PhotosFragment();
        Functions.changeMainFragement(MainActivity.this, homeFragment);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_photos) {
            PhotosFragment photosFragment = new PhotosFragment();
            Functions.changeMainFragement(MainActivity.this, photosFragment);
        } else if (id == R.id.nav_collections) {
            CollectionsFragment collectionsFragment = new CollectionsFragment();
            Functions.changeMainFragement(MainActivity.this, collectionsFragment);
        } else if (id == R.id.nav_favorite) {
            FavoriteFragment favoriteFragment = new FavoriteFragment();
            Functions.changeMainFragement(MainActivity.this, favoriteFragment);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
