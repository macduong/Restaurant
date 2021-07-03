package com.example.restaurant.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.Objects.TableFood;
import com.example.restaurant.R;
import com.example.restaurant.databinding.CustomFoodlistThemeBinding;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter2 extends RecyclerView.Adapter<FoodListAdapter2.ViewHolder> {
    private List<TableFood> tableFoodList = new ArrayList<>();
    private AdapterInterface1 interface1;

    public FoodListAdapter2(List<TableFood> tableFoodList) {
        this.tableFoodList = tableFoodList;
    }

    public void setInterface1(AdapterInterface1 interface1) {
        this.interface1 = interface1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomFoodlistThemeBinding binding = DataBindingUtil.inflate(inflater, R.layout.custom_foodlist_theme, parent, false);
        FoodListAdapter2.ViewHolder viewHolder = new FoodListAdapter2.ViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter2.ViewHolder holder, int position) {
        int amount = tableFoodList.get(position).getAmount();
        int price = tableFoodList.get(position).getCost();
        holder.binding.nametxt.setText(tableFoodList.get(position).getName().toString() + "\n" + price + " VND / " + tableFoodList.get(position).getUnit());
        holder.binding.costtxt.setText(String.valueOf(amount));
        holder.binding.unittxt.setText(String.valueOf(amount * price));

        holder.binding.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interface1.onclickRemove(tableFoodList.get(position).getId());
            }
        });

        holder.binding.behindlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interface1.onclickEdit(tableFoodList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tableFoodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomFoodlistThemeBinding binding;

        public ViewHolder(@NonNull CustomFoodlistThemeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
