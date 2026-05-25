package com.example.journeyplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "DailyRitualsPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.bottomNav);
        nav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                fragment = new HomeFragment();
            } else if (id == R.id.nav_rituals) {
                fragment = new RitualsFragment();
            } else if (id == R.id.nav_prefs) {
                fragment = new PreferencesFragment();
            } else if (id == R.id.nav_about) {
                fragment = new AboutFragment();
            }
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
                return true;
            }
            return false;
        });

        // Load home by default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new HomeFragment())
                .commit();
        }
    }
}
