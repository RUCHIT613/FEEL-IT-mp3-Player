package com.cscorner.feelit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VIEW_PAGER_ADAPTER extends FragmentPagerAdapter {
    ArrayList<Fragment> ARRAYLIST_FOR_FRAGMENT=new ArrayList<>();
    ArrayList<String> ARRAYLIST_FOR_TITLE=new ArrayList<>();
//    public VIEW_PAGER_ADAPTER(Fragment fragment)
    public VIEW_PAGER_ADAPTER(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ARRAYLIST_FOR_FRAGMENT.get(position);
    }

    public void ADD_FRAGMENT(Fragment fragment,String Title){
        ARRAYLIST_FOR_FRAGMENT.add(fragment);
        ARRAYLIST_FOR_TITLE.add(Title);
    }

    @Override
    public int getCount() {
        return ARRAYLIST_FOR_FRAGMENT.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return ARRAYLIST_FOR_TITLE.get(position);
    }
}
