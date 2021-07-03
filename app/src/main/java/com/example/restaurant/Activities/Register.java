package com.example.restaurant.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurant.Database.Data;
import com.example.restaurant.Objects.Manager;
import com.example.restaurant.R;
import com.example.restaurant.databinding.ActivityRegisterBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        data = new Data(getBaseContext());

        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Login.class));
                finish();
            }
        });

        binding.regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.fullnametxt.getText().toString().isEmpty() || !checkFullName(binding.fullnametxt.getText().toString())) {
                    warning(binding.fullnametxt, "Wrong type of fullname");

                } else if (binding.usernametxt.getText().toString().equalsIgnoreCase("")) {
                    warning(binding.usernametxt, "Username mustn't be blank");

                } else if (!checkUsernameAndPassword(binding.usernametxt.getText().toString())) {
                    warning(binding.usernametxt, "Username must contain only uppercase, lowercase and number");

                } else if (data.checkUsedUsername(binding.usernametxt.getText().toString())) {
                    warning(binding.usernametxt, "Username is used, please choose another one");

                } else if (binding.passtxt.getText().toString().length() <= 8) {
                    warning(binding.passtxt, "Password must be longer than 8 characters");

                } else if (!checkUsernameAndPassword(binding.passtxt.getText().toString())) {
                    warning(binding.passtxt, "Password must contain only uppercase, lowercase and number");

                } else {
                    String fullname = binding.fullnametxt.getText().toString();
                    String username = binding.usernametxt.getText().toString();
                    String password = binding.passtxt.getText().toString();
                    Manager manager = new Manager(fullname, username, password);
                    data.insertManager(manager);

                    startActivity(new Intent(getBaseContext(), Login.class));
                    Toast.makeText(getBaseContext(), "Signup successfully, please login!", Toast.LENGTH_LONG).show();
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

    private boolean checkUsernameAndPassword(String UAP) {
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(UAP);
        return matcher.matches();
    }

    private boolean checkFullName(String name) {
        String regex = "^[a-zA-Z ]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}