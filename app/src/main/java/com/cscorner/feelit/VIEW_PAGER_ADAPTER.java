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
    ArrayList<ITEM_CLASS_OF_VIEW_PAGER_FRAGMENT> mArrayList;
    public VIEW_PAGER_ADAPTER(@NonNull FragmentManager fm, int behavior,ArrayList<ITEM_CLASS_OF_VIEW_PAGER_FRAGMENT> arrayList) {
        super(fm, behavior);
        mArrayList=arrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mArrayList.get(position).mFRAGMENT;
    }

    public void ADD_FRAGMENT(Fragment fragment,String Title){
        ARRAYLIST_FOR_FRAGMENT.add(fragment);
        ARRAYLIST_FOR_TITLE.add(Title);
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mArrayList.get(position).mTITLE;
    }
}
