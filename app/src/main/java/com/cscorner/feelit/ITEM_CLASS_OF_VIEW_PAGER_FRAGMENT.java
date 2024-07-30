package com.cscorner.feelit;

import androidx.fragment.app.Fragment;

public class ITEM_CLASS_OF_VIEW_PAGER_FRAGMENT {
    public Fragment mFRAGMENT;
    public String mTITLE;
    public ITEM_CLASS_OF_VIEW_PAGER_FRAGMENT(Fragment FRAGMENT,String TITLE){
        mFRAGMENT=FRAGMENT;
        mTITLE=TITLE;
    }

    public Fragment getmFRAGMENT() {
        return mFRAGMENT;
    }

    public String getmTITLE() {
        return mTITLE;
    }
}
