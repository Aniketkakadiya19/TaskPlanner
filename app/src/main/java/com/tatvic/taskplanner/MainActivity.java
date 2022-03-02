package com.tatvic.taskplanner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tatvic.taskplanner.Adapters.ToDoAdapter;
import com.tatvic.taskplanner.Utils.DatabaseHandler;

import com.tatvic.taskplanner.Adapters.ToDoAdapter;
import com.tatvic.taskplanner.Model.ToDoModel;
import com.tatvic.taskplanner.Utils.DatabaseHandler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Fragment hFragment, dFragment, pFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        hFragment = new HomeFragment();
        dFragment = new DoneFragment();
        pFragment = new ProfileFragment();

        getSupportActionBar().setTitle("Home");

        BottomNavigationView bottomnav = findViewById(R.id.bottom_nav_id);
        bottomnav.setOnNavigationItemSelectedListener(navListner);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                    new HomeFragment()).commit();
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                            hFragment).commit();

                    break;
                case R.id.nav_done:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                            dFragment).commit();
                    break;
                case R.id.nav_profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                            pFragment).commit();
                    break;
            }

            return true;
        }
    };

}