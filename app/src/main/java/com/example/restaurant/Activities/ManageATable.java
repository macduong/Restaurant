package com.example.restaurant.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.restaurant.Adapters.AdapterInterface1;
import com.example.restaurant.Adapters.FoodListAdapter;
import com.example.restaurant.Adapters.FoodListAdapter2;
import com.example.restaurant.Adapters.FoodListAdapter3;
import com.example.restaurant.Database.Data;
import com.example.restaurant.Objects.Food;
import com.example.restaurant.Objects.Table;
import com.example.restaurant.Objects.TableFood;
import com.example.restaurant.R;
import com.example.restaurant.databinding.ActivityManageAtableBinding;

import java.util.ArrayList;
import java.util.List;

public class ManageATable extends AppCompatActivity {
    private ActivityManageAtableBinding binding;
    private Data data;
    private Table table = new Table();
    private Food food = new Food();
    private TableFood tableFood = new TableFood();
    private int tableId;
    private FoodListAdapter2 tableFoodListAdapter;
    private FoodListAdapter3 foodListAdapter;
    private List<TableFood> tableFoodList;
    private List<Food> foodList;
    private long result = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_atable);
        data = new Data(getBaseContext());
        Intent intent = getIntent();
        tableId = intent.getIntExtra("tableId", 0);
        table = data.searchTableById(tableId);

        foodList = new ArrayList<>();
        foodList.addAll(data.searchAllFood());
        foodListAdapter = new FoodListAdapter3(foodList);
        binding.foodrecycle.setAdapter(foodListAdapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 1,
                RecyclerView.VERTICAL, false);
        binding.foodrecycle.setLayoutManager(layoutManager);

        tableFoodList = new ArrayList<>();
        tableFoodList.addAll(data.searchAllTableFoodByTableId(tableId));
        tableFoodListAdapter = new FoodListAdapter2(tableFoodList);
        binding.userecycle.setAdapter(tableFoodListAdapter);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getBaseContext(), 1,
                RecyclerView.VERTICAL, false);
        binding.userecycle.setLayoutManager(layoutManager1);


        if (table.getIsAvailable() == 1) {
            firstAppearance();
        } else {
            secondAppearance();
        }

        binding.tablenametxt.setText("Table" + tableId);

        binding.confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.phonetxt.getText().toString().isEmpty()) {
                    binding.phonetxt.setText("No mention");
                }
                if (binding.nametxt.getText().toString().isEmpty()) {
                    warning(binding.nametxt, "Guest's name can't be blank");
                } else {
                    table.setPhoneNum(binding.phonetxt.getText().toString());
                    table.setGuestName(binding.nametxt.getText().toString());
                    table.setIsAvailable(0);
                    data.updateTable(table);
                    secondAppearance();
                }
            }
        });

        binding.searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout();
                binding.refresh.setRefreshing(false);
            }
        });

        foodListAdapter.setInterface1(new AdapterInterface1() {
            @Override
            public void onclickRemove(int id) {
                data.deleteFoodById(id);
                refreshLayout();
            }

            @Override
            public void onclickEdit(int id) {
                food = data.searchFoodById(id);
                binding.foodname.setText(food.getName());
                warning(binding.foodamount, "Insert amount and click add");
                binding.useaddbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (binding.foodamount.getText().toString().isEmpty() || binding.foodamount.getText().toString().equalsIgnoreCase("0")) {
                            warning(binding.foodamount, "Amount can't be blank or zero");
                        } else {
                            data.insertTableFood(table, food, Integer.parseInt(binding.foodamount.getText().toString()));
                            binding.foodname.setText("Food's name");
                            binding.foodamount.getText().clear();
                            binding.foodamount.clearFocus();
                            refreshLayout();
                        }
                    }
                });
            }
        });

        tableFoodListAdapter.setInterface1(new AdapterInterface1() {
            @Override
            public void onclickRemove(int id) {
                data.deleteTableFoodById(id);
                refreshLayout();
            }

            @Override
            public void onclickEdit(int id) {
                tableFood = data.searchTableFoodById(id);
                binding.foodname.setText(tableFood.getName());
                warning(binding.foodamount, "Insert amount and click add");
                binding.useaddbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (binding.foodamount.getText().toString().isEmpty() || binding.foodamount.getText().toString().equalsIgnoreCase("0")) {
                            warning(binding.foodamount, "Amount can't be blank or zero");
                        } else {
                            tableFood.setAmount(Integer.parseInt(binding.foodamount.getText().toString()));
                            data.updateTableFood(tableFood);
                            binding.foodname.setText("Food's name");
                            binding.foodamount.getText().clear();
                            binding.foodamount.clearFocus();
                            refreshLayout();
                        }
                    }
                });
            }
        });

        binding.checktablebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.foodlayout.setVisibility(View.GONE);
                checkBill();
            }
        });

    }

    private void secondAppearance() {
        binding.nametxt.setText(table.getGuestName());
        binding.phonetxt.setText(table.getPhoneNum());
        binding.nametxt.setEnabled(false);
        binding.phonetxt.setEnabled(false);
        binding.foodlayout.setVisibility(View.VISIBLE);
        binding.checktablebtn.setVisibility(View.VISIBLE);
        binding.confirmbtn.setVisibility(View.GONE);

        refreshLayout();
    }

    private void firstAppearance() {
        binding.foodlayout.setVisibility(View.GONE);
        binding.checktablebtn.setVisibility(View.GONE);
    }

    public void warning(EditText focusText, String warning) {
        focusText.setError(warning);
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void search() {
        if (binding.searchtxt.getText().toString().isEmpty()) {
            warning(binding.searchtxt, "Please insert food's name");
        } else {
            String search = binding.searchtxt.getText().toString();
            foodList.clear();
            foodList.addAll(data.searchAllFoodByName(search));
            foodListAdapter.notifyDataSetChanged();
            binding.searchtxt.getText().clear();
            binding.searchtxt.clearFocus();
        }
    }

    private void refreshLayout() {
        foodList.clear();
        foodList.addAll(data.searchAllFood());
        foodListAdapter.notifyDataSetChanged();

        tableFoodList.clear();
        tableFoodList.addAll(data.searchAllTableFoodByTableId(tableId));
        tableFoodListAdapter.notifyDataSetChanged();
    }

    private void checkBill() {
        result = 0;
        tableFoodList.forEach(tableFood1 -> {
            result = result + tableFood1.getCost() * tableFood1.getAmount();
            tableFood1.setIsAvailable(1);
            data.updateTableFood(tableFood1);
        });
        table.setIsAvailable(1);
        table.setGuestName("");
        table.setPhoneNum("");
        data.updateTable(table);
        binding.checktablebtn.setVisibility(View.GONE);
        binding.totalcosttxt.setVisibility(View.VISIBLE);
        binding.totalcosttxt.setText(String.valueOf(result));
    }

}