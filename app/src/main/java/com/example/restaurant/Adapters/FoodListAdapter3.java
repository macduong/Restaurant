package com.example.restaurant.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.Objects.Food;
import com.example.restaurant.Objects.TableFood;
import com.example.restaurant.R;
import com.example.restaurant.databinding.CustomFoodlistThemeBinding;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter3 extends RecyclerView.Adapter<FoodListAdapter3.ViewHolder> {
    private List<Food> foodList;
    private AdapterInterface1 interface1;

    public FoodListAdapter3(List<Food> foodList) {
        this.foodList = foodList;
    }

    public void setInterface1(AdapterInterface1 interface1) {
        this.interface1 = interface1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomFoodlistThemeBinding binding = DataBindingUtil.inflate(inflater, R.layout.custom_foodlist_theme, parent, false);
        FoodListAdapter3.ViewHolder viewHolder = new FoodListAdapter3.ViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter3.ViewHolder holder, int position) {
        holder.binding.nametxt.setText(foodList.get(position).getName());
        holder.binding.costtxt.setText(String.valueOf(foodList.get(position).getCost()));
        holder.binding.unittxt.setText(foodList.get(position).getUnit());

        holder.binding.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interface1.onclickRemove(foodList.get(position).getId());
            }
        });

        holder.binding.behindlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interface1.onclickEdit(foodList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomFoodlistThemeBinding binding;

        public ViewHolder(@NonNull CustomFoodlistThemeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
