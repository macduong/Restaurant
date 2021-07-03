package com.example.restaurant.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.restaurant.Fragments.AvailableTableFragment;
import com.example.restaurant.Fragments.AllTablesFragment;
import com.example.restaurant.Fragments.UnavailableTableFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllTablesFragment();
            case 1:
                return new AvailableTableFragment();
            case 2:
                return new UnavailableTableFragment();
            default:
                return new AllTablesFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
