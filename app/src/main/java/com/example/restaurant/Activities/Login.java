package com.example.restaurant.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurant.Database.Data;
import com.example.restaurant.R;
import com.example.restaurant.databinding.ActivityLoginBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private Data data;
    private boolean backPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        data = new Data(getBaseContext());

        binding.regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),Register.class));
            }
        });

        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = binding.usernametxt.getText().toString();
                String pass = binding.passtxt.getText().toString();
                int managerId = data.login(user, pass);

                if (user.isEmpty() || !checkUsernameAndPassword(user)) {
                    warning(binding.usernametxt, "Wrong username!");

                } else if (pass.isEmpty() || !checkUsernameAndPassword(pass)) {
                    warning(binding.passtxt, "Wrong password!");

                } else if (managerId == 0) {
                    warning(binding.usernametxt, "Wrong username and password!");

                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("MANAGER", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("managerId", managerId);
                    editor.commit();

                    Intent intent = new Intent(getBaseContext(), ContinueManagerment.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.usernametxt.getText().clear();
        binding.passtxt.getText().clear();
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
}