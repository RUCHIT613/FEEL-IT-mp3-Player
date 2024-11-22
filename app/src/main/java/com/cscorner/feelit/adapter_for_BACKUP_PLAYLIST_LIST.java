package com.cscorner.feelit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter_for_BACKUP_PLAYLIST_LIST extends RecyclerView.Adapter<adapter_for_BACKUP_PLAYLIST_LIST.viewholder_for_backup_playlist_list> {
    private ArrayList<String> marrayList;


    public adapter_for_BACKUP_PLAYLIST_LIST(ArrayList<String> arrayList){
        marrayList=arrayList;
    }
    public static class viewholder_for_backup_playlist_list extends RecyclerView.ViewHolder{
        private TextView playlist_name_textview;
        public viewholder_for_backup_playlist_list(@NonNull View itemView) {
            super(itemView);
            playlist_name_textview=itemView.findViewById(R.id.Playlist_name);
        }
    }

    @NonNull
    @Override
    public viewholder_for_backup_playlist_list onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.backup_playlist_list_recyclerview_layout,parent,false);
        viewholder_for_backup_playlist_list evh=new viewholder_for_backup_playlist_list(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder_for_backup_playlist_list holder, int position) {
        holder.playlist_name_textview.setText(marrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }


}
