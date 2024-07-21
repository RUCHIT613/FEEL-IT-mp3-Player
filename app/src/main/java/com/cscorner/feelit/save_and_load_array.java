package com.cscorner.feelit;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class save_and_load_array {



        private static final String PREFS_NAME="name";
        private static final String PREFS_NAME2="name2";
        private static final String ARRAY_NAME="Array_name";
        private static final String ARRAY_NAME2="Array_name2";
        public static  void save_array_for_all_playlist(Context context, ArrayList<Playlists_recycler_item_class> arrayList){
            SharedPreferences preferences =context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            Gson gson=new Gson();
            String json= gson.toJson(arrayList);
            editor.putString(ARRAY_NAME,json);
            editor.apply();

        }
        public static ArrayList<Playlists_recycler_item_class> load_array_for_all_playlist(Context context){
            SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
            String json=preferences.getString(ARRAY_NAME,"");
            Gson gson =new Gson();
            Type type=new TypeToken<ArrayList<Playlists_recycler_item_class>>(){}.getType();
            return gson.fromJson(json,type);
        }

        public static  void save_array_for_user_created_playlist(Context context, ArrayList<Recently_added_recyclerview_elements_item_class> arrayList,String ARRAY_ID){
            SharedPreferences preferences =context.getSharedPreferences(PREFS_NAME2,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            Gson gson=new Gson();
            String json= gson.toJson(arrayList);
            editor.putString(ARRAY_ID,json);
            editor.apply();

        }
        public static ArrayList<Recently_added_recyclerview_elements_item_class> load_array_for_user_created_playlist(Context context,String ARRAY_ID){
            SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME2,Context.MODE_PRIVATE);
            String json=preferences.getString(ARRAY_ID,"");
            Gson gson =new Gson();
            Type type=new TypeToken<ArrayList<Recently_added_recyclerview_elements_item_class>>(){}.getType();
            return gson.fromJson(json,type);
        }

    public static  void save_array_for_backup_playlist(Context context, ArrayList<Playlists_recycler_item_class> arrayList){
        SharedPreferences preferences =context.getSharedPreferences(PREFS_NAME2,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        Gson gson=new Gson();
        String json= gson.toJson(arrayList);
        editor.putString("BACKUP_PLAYLIST",json);
        editor.apply();

    }
    public static ArrayList<Playlists_recycler_item_class> load_array_for_backup_playlist(Context context){
        SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME2,Context.MODE_PRIVATE);
        String json=preferences.getString("BACKUP_PLAYLIST","");
        Gson gson =new Gson();
        Type type=new TypeToken<ArrayList<Recently_added_recyclerview_elements_item_class>>(){}.getType();
        return gson.fromJson(json,type);
    }

    public static void save_array_for_available_backup_playlist_list(Context context,ArrayList<String> arrayList){
        SharedPreferences preferences =context.getSharedPreferences(PREFS_NAME2,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        Gson gson=new Gson();
        String json= gson.toJson(arrayList);
        editor.putString("BACKUP_PLAYLIST_LISTS",json);
        editor.apply();
    }
    public static ArrayList<String> load_array_for_available_backup_playlists(Context context){
            SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME2,Context.MODE_PRIVATE);
            String json=preferences.getString("BACKUP_PLAYLIST_LISTS","");
            Gson gson=new Gson();
            Type type=new TypeToken<ArrayList<String>>(){}.getType();
            return gson.fromJson(json,type);
    }





}
