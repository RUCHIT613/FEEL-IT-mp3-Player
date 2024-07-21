package com.cscorner.feelit;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ADAPTER_FOR_EDIT_SONG_POSITION extends RecyclerView.Adapter<ADAPTER_FOR_EDIT_SONG_POSITION.VIEWHOLDER_FOR_EDIT_SONG_POSITION> {
    private ArrayList<Recently_added_recyclerview_elements_item_class> marrayList;

    public ADAPTER_FOR_EDIT_SONG_POSITION (ArrayList<Recently_added_recyclerview_elements_item_class> arrayList){
        marrayList=arrayList;
    }
    public static class VIEWHOLDER_FOR_EDIT_SONG_POSITION extends RecyclerView.ViewHolder{
        ImageView imageView_for_song_album_art;
        TextView song_textView;
        TextView artist_textView;
        TextView duration_textView;
        public VIEWHOLDER_FOR_EDIT_SONG_POSITION(@NonNull View itemView) {
            super(itemView);
            imageView_for_song_album_art=itemView.findViewById(R.id.album_art_image_view_edit_song_position);
            song_textView=itemView.findViewById(R.id.song_name_text_view_edit_song_position);
            artist_textView=itemView.findViewById(R.id.artist_name_text_view_edit_song_position);
            duration_textView=itemView.findViewById(R.id.song_duration_text_view_edit_song_position);
        }
    }
    @NonNull
    @Override
    public VIEWHOLDER_FOR_EDIT_SONG_POSITION onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_song_position_recyclerview_layout,parent,false);
        VIEWHOLDER_FOR_EDIT_SONG_POSITION evh =new VIEWHOLDER_FOR_EDIT_SONG_POSITION(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull VIEWHOLDER_FOR_EDIT_SONG_POSITION holder, int position) {
        Recently_added_recyclerview_elements_item_class current_item=marrayList.get(position);
        Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + current_item.getMalbum_art());

        Picasso.get().load(albumArtUri).into(holder.imageView_for_song_album_art);

        holder.song_textView.setText(current_item.getMsong_name());
        holder.artist_textView.setText(current_item.getMartist());
        if(current_item.getMartist().length()>30){
            holder.artist_textView.setText(current_item.getMartist().substring(0,30)+"..");
        }
        else {
            holder.artist_textView.setText(current_item.getMartist());
        }

        int duration=current_item.getMduration();
        int min=(duration/1000)/60;
        int sec=(duration/1000)%60;
        holder.duration_textView.setText(String.format("| %02d:%02d",min,sec));


    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }


}
