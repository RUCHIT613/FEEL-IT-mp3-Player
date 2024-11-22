package com.cscorner.feelit;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.icu.text.Transliterator;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Playlist_recycler_item_Adapter_class extends RecyclerView.Adapter<Playlist_recycler_item_Adapter_class.Playlist_recycler_item_view_holder_class> {
    public ArrayList<Playlists_recycler_item_class> MarrayList;
    private onCLICK_Listener mlistener;

    public interface onCLICK_Listener{
        void on_ITEM_click(int Position) throws IOException;
        void more_on_ITEM_click(View view,int Position) throws IOException;
    }
    public void set_ON_CLICK_Listener(onCLICK_Listener listener){
        mlistener=listener;
    }

    public Playlist_recycler_item_Adapter_class( ArrayList<Playlists_recycler_item_class> arrayList){
        MarrayList=arrayList;
    }


    public static class Playlist_recycler_item_view_holder_class extends RecyclerView.ViewHolder{
        private TextView playlist_name_textview;
        private ImageButton playlist_more_button;
        private ImageView playlist_image;

        public Playlist_recycler_item_view_holder_class(@NonNull View itemView,onCLICK_Listener listener) {
            super(itemView);
            playlist_name_textview=itemView.findViewById(R.id.Playlist_name);
            playlist_more_button=itemView.findViewById(R.id.more_button_all_playlist_interface);
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
    public Playlist_recycler_item_view_holder_class onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_interface_recyclerview_item_layout,parent,false);
        Playlist_recycler_item_view_holder_class evh=new Playlist_recycler_item_view_holder_class(v,mlistener);


        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull Playlist_recycler_item_view_holder_class holder, int position) {
        if(MarrayList.get(position).getMPlaylist_image_album_art()==100){
            holder.playlist_image.setBackgroundResource(R.drawable.logo);
        } else if (MarrayList.get(position).getMPlaylist_image_album_art()==913) {
            holder.playlist_image.setImageResource(R.drawable.favorite_on);

        } else {
            long Album_ID=MarrayList.get(position).getMPlaylist_image_album_art();
            Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + Album_ID);

            Picasso.get().load(albumArtUri).into(holder.playlist_image);


        }
//
        holder.playlist_name_textview.setText(MarrayList.get(position).getMPlaylist_name());

    }

    @Override
    public int getItemCount() {
        return MarrayList.size();
    }



}
