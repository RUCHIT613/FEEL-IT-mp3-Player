package com.cscorner.feelit;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter_for_BACKUP_PLAYLIST_LIST extends RecyclerView.Adapter<adapter_for_BACKUP_PLAYLIST_LIST.viewholder_for_backup_playlist_list> {
    private ArrayList<String> marrayList;

    private adapter_for_BACKUP_PLAYLIST_LIST.set_on_click_listener mlistener;
    public interface set_on_click_listener{
        void RETRIEVED_PLAYLIST_NAME(String RETRIEVED_PLAYLIST);
//        void remove_playlist_from_backup_playlist(String BACKUP_PLAYLIST_NAME, int POSITION);
    }
    public void SET_ON_CLICK(adapter_for_BACKUP_PLAYLIST_LIST.set_on_click_listener listener){
        mlistener=listener;
    }
    public adapter_for_BACKUP_PLAYLIST_LIST(ArrayList<String> arrayList){
        marrayList=arrayList;
    }
    public static class viewholder_for_backup_playlist_list extends RecyclerView.ViewHolder{
        private TextView playlist_name_textview;
        private ImageView add_playlist;
        public viewholder_for_backup_playlist_list(@NonNull View itemView,adapter_for_BACKUP_PLAYLIST_LIST.set_on_click_listener listener) {
            super(itemView);
            playlist_name_textview=itemView.findViewById(R.id.Playlist_name);
            add_playlist=itemView.findViewById(R.id.add_retrieve_playlist);
//            add_playlist.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.RETRIEVED_PLAYLIST_NAME(marraylist);
//                }
//            });
        }
    }

    @NonNull
    @Override
    public viewholder_for_backup_playlist_list onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.backup_playlist_list_recyclerview_layout,parent,false);
        viewholder_for_backup_playlist_list evh=new viewholder_for_backup_playlist_list(view,mlistener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder_for_backup_playlist_list holder, @SuppressLint("RecyclerView") int position) {
        holder.playlist_name_textview.setText(marrayList.get(position));
        holder.add_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.RETRIEVED_PLAYLIST_NAME(marrayList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }


}
