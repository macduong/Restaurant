package com.example.restaurant.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;

import com.example.restaurant.Activities.ManageATable;
import com.example.restaurant.Adapters.TableListAdapter;
import com.example.restaurant.Database.Data;
import com.example.restaurant.Objects.Table;
import com.example.restaurant.R;
import com.example.restaurant.databinding.FragmentTablesBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllTablesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllTablesFragment extends Fragment {
    private FragmentTablesBinding binding;
    private Data data;
    private TableListAdapter adapter;
    private List<Table> tableList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllTablesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllTablesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllTablesFragment newInstance(String param1, String param2) {
        AllTablesFragment fragment = new AllTablesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tables, container, false);
        data = new Data(getContext());

        tableList.addAll(data.searchAllTables());
        adapter = new TableListAdapter(tableList);
        binding.tablelst.setAdapter(adapter);

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

        binding.tablelst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ManageATable.class);
                intent.putExtra("tableId", tableList.get(position).getId());
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout();
    }

    private void refreshLayout() {
        tableList.clear();
        tableList.addAll(data.searchAllTables());
        adapter.notifyDataSetChanged();
    }

    private void search() {
        if (binding.searchtxt.getText().toString().isEmpty()) {
            warning(binding.searchtxt, "Please insert table's name or guest's name");
        } else {
            String search = binding.searchtxt.getText().toString();
            tableList.clear();
            tableList.addAll(data.searchAllTableByNameId(search));
            adapter.notifyDataSetChanged();
            binding.searchtxt.getText().clear();
            binding.searchtxt.clearFocus();
        }

    }

    public void warning(EditText focusText, String warning) {
        focusText.setError(warning);
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }
}