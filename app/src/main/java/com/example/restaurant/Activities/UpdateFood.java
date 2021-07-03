package com.example.restaurant.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.restaurant.Database.Data;
import com.example.restaurant.Objects.Food;
import com.example.restaurant.R;
import com.example.restaurant.databinding.ActivityUpdateFoodBinding;

public class UpdateFood extends AppCompatActivity {
    private ActivityUpdateFoodBinding binding;
    private Data data;
    private Intent intent;
    private Food food = new Food();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_food);
        data = new Data(getBaseContext());

        intent = getIntent();
        int foodId = intent.getIntExtra("foodId", 0);
        food = data.searchFoodById(foodId);

        binding.titletxt.setText(food.getName() + " Info");
        binding.nametxt.setText(food.getName());
        binding.pricetxt.setText(String.valueOf(food.getCost()));
        binding.unittxt.setText(food.getUnit());

        binding.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.nametxt.getText().toString().isEmpty()) {
                    warning(binding.nametxt, "Please insert service's name");
                } else if (binding.pricetxt.getText().toString().isEmpty()) {
                    warning(binding.pricetxt, "Please insert service's price");
                } else if (binding.unittxt.getText().toString().isEmpty()) {
                    warning(binding.unittxt, "Please insert service's unit");
                } else {
                    food.setName(binding.nametxt.getText().toString()); ;
                    food.setCost(Integer.parseInt(binding.pricetxt.getText().toString()));
                    food.setUnit(binding.unittxt.getText().toString());

                    data.updateFood(food);
                    finish();
                }
            }
        });
    }

    public void warning(EditText focusText, String warning) {
        focusText.setError(warning);
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}