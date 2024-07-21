package com.cscorner.feelit;

import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_for_backup_playlist extends RecyclerView.Adapter<adapter_for_backup_playlist.view_holder_for_backup_playlist> {

    private ArrayList<Playlists_recycler_item_class> marrayList;
    private SharedPreferences preferences;
    private set_on_click_listener mlistener;
    public interface set_on_click_listener{
        void add_playlist_to_backup_playlist(String BACKUP_PLAYLIST_NAME);
        void remove_playlist_from_backup_playlist(String BACKUP_PLAYLIST_NAME, int POSITION);
    }
    public void SET_ON_CLICK(set_on_click_listener listener){
        mlistener=listener;
    }
    public adapter_for_backup_playlist(ArrayList<Playlists_recycler_item_class> arrayList, Context context){
        marrayList=arrayList;
        preferences=context.getSharedPreferences("preff",Context.MODE_PRIVATE);
    }
    public static class view_holder_for_backup_playlist extends RecyclerView.ViewHolder{
        private ImageView backup_playlist_image;
        private TextView backup_playlist_name;
        private Switch backup_playlist_switch;
        public view_holder_for_backup_playlist(@NonNull View itemView) {
            super(itemView);
            backup_playlist_image=itemView.findViewById(R.id.all_playlist_Image);
            backup_playlist_name=itemView.findViewById(R.id.Playlist_name);
            backup_playlist_switch=itemView.findViewById(R.id.switch_of_backup_playlist);
        }
    }

    @NonNull
    @Override
    public view_holder_for_backup_playlist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_backup_layout,parent,false);
        view_holder_for_backup_playlist evh=new view_holder_for_backup_playlist(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull view_holder_for_backup_playlist holder, int position) {
        Playlists_recycler_item_class current_item=marrayList.get(position);
        SharedPreferences.Editor editor=preferences.edit();
        if (current_item.getMPlaylist_image_album_art()!=100){
            long Album_ID=current_item.getMPlaylist_image_album_art();
            Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + Album_ID);

            Picasso.get().load(albumArtUri).into(holder.backup_playlist_image);
        }
        else{
            holder.backup_playlist_image.setBackgroundResource(R.drawable.logo);
        }


        holder.backup_playlist_name.setText(current_item.getMPlaylist_name());
        holder.backup_playlist_switch.setChecked(current_item.isMis_selected());

        if(current_item.isMis_selected()){
            holder.backup_playlist_switch.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#79C9C8")));
        }
        else {
            holder.backup_playlist_switch.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#a0a0a0")));
        }

        holder.backup_playlist_switch.setOnClickListener(v -> {
            current_item.setMis_selected(!current_item.isMis_selected());
            holder.backup_playlist_switch.setSelected(current_item.isMis_selected());
            editor.putBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+current_item.getMPlaylist_name(),current_item.isMis_selected());
            editor.apply();

            if(current_item.isMis_selected()){
                holder.backup_playlist_switch.setChecked(true);
                mlistener.add_playlist_to_backup_playlist(current_item.getMPlaylist_name());
                holder.backup_playlist_switch.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#79C9C8")));
            }
            else {
                holder.backup_playlist_switch.setChecked(false);
                mlistener.remove_playlist_from_backup_playlist(current_item.getMPlaylist_name(),position);
                holder.backup_playlist_switch.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#a0a0a0")));
            }

        });

    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }


}
