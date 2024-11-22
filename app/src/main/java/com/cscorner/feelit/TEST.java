package com.cscorner.feelit;

import android.content.Context;
import android.widget.Toast;

public class TEST {
    public static Context mcontext;
    public TEST(Context context){
        mcontext=context;
    }
    public static Interface listener;
    public interface Interface {

        void NEXT();
    }
    public static void next(){
        listener.NEXT();

    }
}
