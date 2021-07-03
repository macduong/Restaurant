package com.example.restaurant.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.restaurant.Database.Data;
import com.example.restaurant.R;
import com.example.restaurant.databinding.ActivityContinueManagermentBinding;

public class ContinueManagerment extends AppCompatActivity {
    private ActivityContinueManagermentBinding binding;
    private Data data;
    private boolean backPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_continue_managerment);
        data = new Data(getBaseContext());

        if (data.tableDataExists()){
            binding.choosecontinue.setVisibility(View.VISIBLE);
        }

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Login.class));
                finish();
            }
        });

        binding.choosenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.keepfood.isChecked()){
                    data.deleteAllFood();
                }
                data.deleteAllTableFood();
                startActivity(new Intent(getBaseContext(),CreateTables.class));
            }
        });

        binding.choosecontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),MainActivity.class));
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