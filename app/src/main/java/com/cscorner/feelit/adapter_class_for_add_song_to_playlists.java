package com.cscorner.feelit;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class adapter_class_for_add_song_to_playlists extends RecyclerView.Adapter<adapter_class_for_add_song_to_playlists.add_song_to_Playlist_recycler_item_view_holder_class> {

    public ArrayList<Playlists_recycler_item_class> MarrayList;
    private Playlist_recycler_item_Adapter_class.onCLICK_Listener mlistener;

    public interface onCLICK_Listener{
        void on_ITEM_click(int Position);
        void more_on_ITEM_click(View view,int Position);
    }
    public void set_ON_CLICK_Listener(Playlist_recycler_item_Adapter_class.onCLICK_Listener listener){
        mlistener=listener;
    }

    public adapter_class_for_add_song_to_playlists(ArrayList<Playlists_recycler_item_class> arrayList){
        MarrayList=arrayList;
    }


    public static class add_song_to_Playlist_recycler_item_view_holder_class extends RecyclerView.ViewHolder{
        private TextView playlist_name_textview;
        private ImageView playlist_more_button;
        private ImageView playlist_image;

        public add_song_to_Playlist_recycler_item_view_holder_class(@NonNull View itemView, Playlist_recycler_item_Adapter_class.onCLICK_Listener listener) {
            super(itemView);
            playlist_name_textview=itemView.findViewById(R.id.Playlist_name);
            playlist_more_button=itemView.findViewById(R.id.is_playlist_selected);
            playlist_image=itemView.findViewById(R.id.all_playlist_Image);
            playlist_more_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        listener.more_on_ITEM_click(itemView,getLayoutPosition());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getLayoutPosition();
                    try {
                        listener.on_ITEM_click(position);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });


        }
    }
    @NonNull
    @Override
    public adapter_class_for_add_song_to_playlists.add_song_to_Playlist_recycler_item_view_holder_class onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_song_to_playlist_layout,parent,false);
        add_song_to_Playlist_recycler_item_view_holder_class evh=new add_song_to_Playlist_recycler_item_view_holder_class(v,mlistener);


        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull add_song_to_Playlist_recycler_item_view_holder_class holder, int position) {
//        holder.playlist_image.setImageResource(MarrayList.get(position).getMPlaylist_image());
            holder.playlist_name_textview.setText(MarrayList.get(position).getMPlaylist_name());
            holder.playlist_more_button.setSelected(MarrayList.get(position).isMis_selected());

            if(MarrayList.get(position).isMis_selected()){
                holder.playlist_more_button.setImageResource(R.drawable.radio_button_on);
            }else {
                holder.playlist_more_button.setImageResource(R.drawable.radio_button_off);
            }
            holder.playlist_more_button.setOnClickListener(v -> {
                MarrayList.get(position).setMis_selected(!MarrayList.get(position).isMis_selected());
                holder.playlist_more_button.setSelected(MarrayList.get(position).isMis_selected());
                if(MarrayList.get(position).isMis_selected()){
                    holder.playlist_more_button.setImageResource(R.drawable.radio_button_on);
                }
                else {
                    holder.playlist_more_button.setImageResource(R.drawable.radio_button_off);
                }
            });

            holder.itemView.setOnClickListener(v->{
                MarrayList.get(position).setMis_selected(!MarrayList.get(position).isMis_selected());
                holder.playlist_more_button.setSelected(MarrayList.get(position).isMis_selected());
                if(MarrayList.get(position).isMis_selected()){
                    holder.playlist_more_button.setImageResource(R.drawable.radio_button_on);
                }
                else {
                    holder.playlist_more_button.setImageResource(R.drawable.radio_button_off);
                }
            });


        if (MarrayList.get(position).getMPlaylist_image_album_art()!=100){
            long Album_ID=MarrayList.get(position).getMPlaylist_image_album_art();
            Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + Album_ID);

            Picasso.get().load(albumArtUri).into(holder.playlist_image);
        }
        else{
            holder.playlist_image.setBackgroundResource(R.drawable.logo);
        }
    }


    @Override
    public int getItemCount() {
        return MarrayList.size();
    }

}
