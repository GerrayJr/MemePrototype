package com.gerray.memeprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView mNav;

    private CameraFragment cameraFragment;
    private VideoFragment videoFragment;
    private ArticleFragment articleFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout mFrame = findViewById(R.id.mFrame);
        mNav = findViewById(R.id.bottom_nav);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        cameraFragment = new CameraFragment();
        videoFragment = new VideoFragment();
        articleFragment = new ArticleFragment();

        setFragment(cameraFragment);

        mNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_photos:
                        setFragment(cameraFragment);
                        return true;

                    case R.id.nav_video:
                        setFragment(videoFragment);
                        return true;

                    case R.id.nav_articles:
                        setFragment(articleFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }


    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mFrame, fragment);
        fragmentTransaction.commit();
    }
}
