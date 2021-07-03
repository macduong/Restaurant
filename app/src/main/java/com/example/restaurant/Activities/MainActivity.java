package com.example.restaurant.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.restaurant.Adapters.ViewPagerAdapter;
import com.example.restaurant.Database.Data;
import com.example.restaurant.R;
import com.example.restaurant.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Data data;
    private int managerId;
    private boolean backPressedOnce = false;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        data = new Data(getBaseContext());
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewpager.setAdapter(adapter);
        binding.tablayout.setupWithViewPager(binding.viewpager);

        binding.tablayout.getTabAt(0).setIcon(R.drawable.ic_baseline_list_alt_24);
        binding.tablayout.getTabAt(0).setText("All Tables");
        binding.tablayout.getTabAt(1).setIcon(R.drawable.ic_baseline_event_available_24);
        binding.tablayout.getTabAt(1).setText("Available");
        binding.tablayout.getTabAt(2).setIcon(R.drawable.ic_baseline_event_busy_24);
        binding.tablayout.getTabAt(2).setText("Unavailable");

        SharedPreferences sharedPreferences = getSharedPreferences("MANAGER", MODE_PRIVATE);
        managerId = sharedPreferences.getInt("managerId", 0);

        binding.menu.setItemIconTintList(null);

        binding.menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.exit:
                        finishAffinity();
                        break;
                    case R.id.logout:
                        startActivity(new Intent(getBaseContext(), Login.class));
                        finish();
                        break;
                    case R.id.managefood:
                        startActivity(new Intent(getBaseContext(), ManageFood.class));
                        break;
                    case R.id.accInfo:
                        startActivity(new Intent(getBaseContext(), ManagerInfo.class));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (backPressedOnce) {
            finishAffinity();
            return;
        }
        this.backPressedOnce = true;
        Toast.makeText(this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backPressedOnce = false;
            }
        }, 2000);
    }
}