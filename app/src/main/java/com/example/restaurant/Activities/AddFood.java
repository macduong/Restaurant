package com.example.restaurant.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.restaurant.Database.Data;
import com.example.restaurant.Objects.Food;
import com.example.restaurant.R;
import com.example.restaurant.databinding.ActivityAddFoodBinding;

public class AddFood extends AppCompatActivity {
    private ActivityAddFoodBinding binding;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_food);
        data = new Data(getBaseContext());

        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.nametxt.getText().toString().isEmpty()) {
                    warning(binding.nametxt, "Please insert service's name");
                } else if (binding.pricetxt.getText().toString().isEmpty()) {
                    warning(binding.pricetxt, "Please insert service's price");
                } else if (binding.unittxt.getText().toString().isEmpty()) {
                    warning(binding.unittxt, "Please insert service's unit");
                } else {
                    String name = binding.nametxt.getText().toString();
                    int price = Integer.parseInt(binding.pricetxt.getText().toString());
                    String unit = binding.unittxt.getText().toString();
                    data.insertFood(new Food(name, price, unit));
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