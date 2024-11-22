package com.cscorner.feelit;

import java.util.ArrayList;
import java.util.Collections;

public class load_all_songs_of_all_songs_interface_in_ascending {

    public static ArrayList<Recently_added_recyclerview_elements_item_class> load_songs_in_ascending(ArrayList<Recently_added_recyclerview_elements_item_class> recently_added_arraylist){
        ArrayList<Recently_added_recyclerview_elements_item_class> songs_in_ascending_order_arraylist=new ArrayList<>();
        for(int i=0;i<recently_added_arraylist.size();i++){
            Recently_added_recyclerview_elements_item_class current_item=recently_added_arraylist.get(i);
            songs_in_ascending_order_arraylist.add(new Recently_added_recyclerview_elements_item_class(
                    current_item.getMsong_name(),
                    current_item.getMpath(),
                    current_item.getMartist(),
                    current_item.getMalbum_name(),
                    current_item.getMduration(),
                    current_item.getMalbum_art(),
                    false
            ));

        }

        for(int i=0;i<songs_in_ascending_order_arraylist.size();i++){
            for(int j=0;j<recently_added_arraylist.size()-1;j++){
                Recently_added_recyclerview_elements_item_class current_item=songs_in_ascending_order_arraylist.get(j);
                Recently_added_recyclerview_elements_item_class current_item2=songs_in_ascending_order_arraylist.get(j+1);
                if(current_item.getMsong_name().compareTo(current_item2.getMsong_name())>0){
                    Collections.swap(songs_in_ascending_order_arraylist,j,j+1);
                }
            }


        }
//        ArrayList<String> songs_path_meant_to_be_ascending=new ArrayList<>();




//        Collections.sort(songs_path_meant_to_be_ascending);
//
//
//        for(int i=0;i<recently_added_arraylist.size();i++){
//            String Song_Path=songs_path_meant_to_be_ascending.get(i);
//            for(int j=0;j<recently_added_arraylist.size();j++){
//                Recently_added_recyclerview_elements_item_class current_item=recently_added_arraylist.get(j);
//                if(Song_Path.equals(current_item.getMpath())){
//                    songs_in_ascending_order_arraylist.add(new Recently_added_recyclerview_elements_item_class(
//                            current_item.getMsong_name(),
//                            current_item.getMpath(),
//                            current_item.getMartist(),
//                            current_item.getMalbum_name(),
//                            current_item.getMduration(),
//                            current_item.getMalbum_art(),false));
//                    break;
//                }
//            }
//        }




        return songs_in_ascending_order_arraylist;
    }
}
