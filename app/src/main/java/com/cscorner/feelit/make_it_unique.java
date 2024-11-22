package com.cscorner.feelit;

import java.util.ArrayList;

public class make_it_unique {
    public static ArrayList<String> MAKE_THE_ARRAYLIST_UNIQUE(ArrayList<String> arrayList){
        ArrayList<String> marrayList=new ArrayList<>();

        for(int i=0;i<arrayList.size();i++){

            for(int j=0;j<marrayList.size();j++){
                if(!arrayList.get(i).equals(marrayList.get(j))){
                    marrayList.add(arrayList.get(i));
                }
            }
        }


       return marrayList;
    }
}
