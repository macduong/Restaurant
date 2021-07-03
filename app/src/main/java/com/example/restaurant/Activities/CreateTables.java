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
import com.example.restaurant.Objects.Table;
import com.example.restaurant.R;
import com.example.restaurant.databinding.ActivityCreateTablesBinding;

public class CreateTables extends AppCompatActivity {
    private ActivityCreateTablesBinding binding;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_tables);
        data = new Data(getBaseContext());

        binding.logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Login.class));
                finish();
            }
        });

        binding.createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tablenumbertxt.getText().toString().isEmpty() || Integer.parseInt(binding.tablenumbertxt.getText().toString()) == 0) {
                    warning(binding.tablenumbertxt, "Table number mustn't be blank or zero");
                } else {
                    data.deleteAllTables();
                    int number = Integer.parseInt(binding.tablenumbertxt.getText().toString());
                    for (int i =1;i<=number;i++){
                        data.insertTable(new Table(1));
                    }
                    startActivity(new Intent(getBaseContext(),MainActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void warning(EditText focusText, String warning) {
        focusText.setError(warning);
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }
}