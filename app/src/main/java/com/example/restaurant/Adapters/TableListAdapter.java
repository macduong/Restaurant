package com.example.restaurant.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.restaurant.Objects.Table;
import com.example.restaurant.R;
import com.example.restaurant.databinding.TableCustomThemeBinding;

import java.util.ArrayList;
import java.util.List;

public class TableListAdapter extends BaseAdapter {
    private List<Table> tableList = new ArrayList<>();
    private TableCustomThemeBinding binding;

    public TableListAdapter(List<Table> tableList) {
        this.tableList = tableList;
    }
    //    private int  layout;
//    private Context context;

//    public TableListAdapter(int layout, List<Table> tableList, Context context) {
//        this.layout = layout;
//        this.tableList = tableList;
//        this.context = context;
//    }

    @Override
    public int getCount() {
        return tableList.size();
    }

    @Override
    public Object getItem(int position) {
        return tableList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tableList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.table_custom_theme, parent, false);

        binding.tablename.setText("Table "+String.valueOf(tableList.get(position).getId()));
        if (tableList.get(position).getIsAvailable()==1){
            binding.tableimage.setImageResource(R.drawable.available_table);
        }
        else{
            binding.tableimage.setImageResource(R.drawable.unavailable_table);
            binding.guestname.setText(tableList.get(position).getGuestName());
            binding.tablename.setTextColor(Color.parseColor("#F61313"));
        }

        return binding.getRoot();
    }
}
