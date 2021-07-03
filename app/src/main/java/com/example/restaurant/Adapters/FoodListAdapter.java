package com.example.restaurant.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.restaurant.Objects.Food;
import com.example.restaurant.R;
import com.example.restaurant.databinding.CustomFoodlistThemeBinding;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends BaseAdapter {
    private CustomFoodlistThemeBinding binding;
    private List<Food> foodList = new ArrayList<>();
    private AdapterInterface1 interface1;

    public FoodListAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    public void setInterface1(AdapterInterface1 interface1) {
        this.interface1 = interface1;
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_foodlist_theme, parent, false);
        Food food = foodList.get(position);
        binding.nametxt.setText(food.getName());
        binding.costtxt.setText(String.valueOf(food.getCost()));
        binding.unittxt.setText(food.getUnit());

        binding.behindlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interface1.onclickEdit(food.getId());
            }
        });

        binding.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interface1.onclickRemove(food.getId());
            }
        });
        return binding.getRoot();
    }
}
