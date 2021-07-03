package com.example.restaurant.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;

import com.example.restaurant.Adapters.AdapterInterface1;
import com.example.restaurant.Adapters.FoodListAdapter;
import com.example.restaurant.Database.Data;
import com.example.restaurant.Objects.Food;
import com.example.restaurant.R;
import com.example.restaurant.databinding.ActivityManageFoodBinding;

import java.util.ArrayList;
import java.util.List;

public class ManageFood extends AppCompatActivity {
    private ActivityManageFoodBinding binding;
    private Data data;
    private FoodListAdapter adapter;
    private List<Food> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_food);
        data = new Data(getBaseContext());

        adapter = new FoodListAdapter(foodList);
        binding.foodlist.setAdapter(adapter);
        refreshLayout();

        binding.foodlist.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (binding.foodlist.getChildAt(0) != null) {
                    binding.refresh.setEnabled(binding.foodlist.getFirstVisiblePosition() == 0 && binding.foodlist.getChildAt(0).getTop() == 0);
                }
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 13) {
                    binding.addbtn.setVisibility(View.GONE);
                } else {
                    binding.addbtn.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout();
                binding.refresh.setRefreshing(false);
            }
        });

        adapter.setInterface1(new AdapterInterface1() {
            @Override
            public void onclickRemove(int id) {
                data.deleteFoodById(id);
                refreshLayout();
            }

            @Override
            public void onclickEdit(int id) {
                Intent intent = new Intent(getBaseContext(),UpdateFood.class);
                intent.putExtra("foodId",id);
                startActivity(intent);
            }
        });

        binding.searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AddFood.class));
            }
        });

        binding.addbtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                binding.addbtn.setVisibility(View.GONE);
                return true;
            }
        });

    }

    private void search() {
        if (binding.searchtxt.getText().toString().isEmpty()) {
            warning(binding.searchtxt, "Please insert food's name");
        } else {
            String search = binding.searchtxt.getText().toString();
            foodList.clear();
            foodList.addAll(data.searchAllFoodByName(search));
            adapter.notifyDataSetChanged();
            binding.searchtxt.getText().clear();
            binding.searchtxt.clearFocus();
        }

    }

    private void refreshLayout() {
        foodList.clear();
        foodList.addAll(data.searchAllFood());
        adapter.notifyDataSetChanged();
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

    @Override
    protected void onResume() {
        super.onResume();
        refreshLayout();
    }
}