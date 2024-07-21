package com.cscorner.feelit;

import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_for_add_multiple_songs_to_multiple_playlist extends RecyclerView.Adapter<adapter_for_add_multiple_songs_to_multiple_playlist.view_holder_for_add_multiple_songs_to_multiple_playlist> {
    private  ArrayList<Recently_added_recyclerview_elements_item_class> marrayList;
    private ArrayList<Integer> selected_songs_in_sequence;
    private SET_ON_ITEM_CLICKED_LISTENER mlistener;

    public interface SET_ON_ITEM_CLICKED_LISTENER{
        void MORE_BUTTON_WHICH_IS_CHECKBOX_ON_CLICKED_LISTENER(int POSITION);
    }

    public void set_on_clicked_listener(adapter_for_add_multiple_songs_to_multiple_playlist.SET_ON_ITEM_CLICKED_LISTENER listener){
        mlistener=listener;
    }
    public adapter_for_add_multiple_songs_to_multiple_playlist(ArrayList<Recently_added_recyclerview_elements_item_class> arrayList){
        marrayList=arrayList;
        selected_songs_in_sequence=new ArrayList<>();
    }

    public static class view_holder_for_add_multiple_songs_to_multiple_playlist extends RecyclerView.ViewHolder{
        private ImageView album_art_image_view_of_add_multiple_songs_to_multiple_songs;
        private TextView song_name_text_view_of_add_multiple_songs_to_multiple_songs;
        private TextView artist_name_text_view_of_add_multiple_songs_to_multiple_songs;
        private TextView song_duration_text_view_of_add_multiple_songs_to_multiple_songs;
        private ImageView more_button_of_add_multiple_songs_to_multiple_songs;
        public view_holder_for_add_multiple_songs_to_multiple_playlist(@NonNull View itemView,adapter_for_add_multiple_songs_to_multiple_playlist.SET_ON_ITEM_CLICKED_LISTENER listener) {
            super(itemView);
            album_art_image_view_of_add_multiple_songs_to_multiple_songs=itemView.findViewById(R.id.add_multiple_songs_to_multiple_playlist_album_art);
            song_name_text_view_of_add_multiple_songs_to_multiple_songs=itemView.findViewById(R.id.song_name_text_view_add_multiple_songs_to_multiple_playlist);
            artist_name_text_view_of_add_multiple_songs_to_multiple_songs=itemView.findViewById(R.id.artist_name_text_view_add_multiple_songs_to_multiple_playlist);
            song_duration_text_view_of_add_multiple_songs_to_multiple_songs=itemView.findViewById(R.id.song_duration_text_view_add_multiple_songs_to_multiple_playlist);
            more_button_of_add_multiple_songs_to_multiple_songs=itemView.findViewById(R.id.add_multiple_songs_to_multiple_playlist_checkbox);
            more_button_of_add_multiple_songs_to_multiple_songs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.MORE_BUTTON_WHICH_IS_CHECKBOX_ON_CLICKED_LISTENER(getLayoutPosition());
                }
            });
        }
    }


    @NonNull
    @Override
    public view_holder_for_add_multiple_songs_to_multiple_playlist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_multiple_songs_to_multiple_playlist_recyclerview_item_layout,parent,false);
        view_holder_for_add_multiple_songs_to_multiple_playlist evh=new view_holder_for_add_multiple_songs_to_multiple_playlist(view,mlistener);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull view_holder_for_add_multiple_songs_to_multiple_playlist holder, int position) {
        Recently_added_recyclerview_elements_item_class current_item=marrayList.get(position);

    if(current_item.getMsong_name().length()>36){
            holder.song_name_text_view_of_add_multiple_songs_to_multiple_songs.setText(current_item.getMsong_name().substring(0,36)+"...");

        }
        else {
            holder.song_name_text_view_of_add_multiple_songs_to_multiple_songs.setText(current_item.getMsong_name());

        }


        int Artist_name_length=current_item.getMartist().length();

        if(Artist_name_length>25){
            holder.artist_name_text_view_of_add_multiple_songs_to_multiple_songs.setText(current_item.getMartist().substring(0,26)+"..");
        }
        else {
            holder.artist_name_text_view_of_add_multiple_songs_to_multiple_songs.setText(current_item.getMartist());
        }
        int duration=current_item.getMduration();
        int min=(duration/1000)/60;
        int sec=(duration/1000)%60;
        holder.song_duration_text_view_of_add_multiple_songs_to_multiple_songs.setText(String.format("|%02d:%02d",min,sec));

        long Album_ID = current_item.getMalbum_art();
        Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + Album_ID);

        Picasso.get().load(albumArtUri).into(holder.album_art_image_view_of_add_multiple_songs_to_multiple_songs);

//        holder.more_button_of_add_multiple_songs_to_multiple_songs.setSelected(current_item.isMis_selected());


        if(current_item.isMis_selected()){
            holder.more_button_of_add_multiple_songs_to_multiple_songs.setImageResource(R.drawable.radio_button_on);
        }else {
            holder.more_button_of_add_multiple_songs_to_multiple_songs.setImageResource(R.drawable.radio_button_off);
        }

        holder.more_button_of_add_multiple_songs_to_multiple_songs.setOnClickListener( v -> {
            mlistener.MORE_BUTTON_WHICH_IS_CHECKBOX_ON_CLICKED_LISTENER(position);
            current_item.setMis_selected(!current_item.isMis_selected());
//            holder.more_button_of_add_multiple_songs_to_multiple_songs.setSelected(current_item.isMis_selected());
            if(current_item.isMis_selected()){
                selected_songs_in_sequence.add(position);
                holder.more_button_of_add_multiple_songs_to_multiple_songs.setImageResource(R.drawable.radio_button_on);

            }
            else {
                for(int i=0 ; i<selected_songs_in_sequence.size();i++){
                    if(selected_songs_in_sequence.get(i)==position){
                        selected_songs_in_sequence.remove(i);
                    }
                }
                holder.more_button_of_add_multiple_songs_to_multiple_songs.setImageResource(R.drawable.radio_button_off);

            }
        });
        holder.itemView.setOnClickListener(v ->{
            mlistener.MORE_BUTTON_WHICH_IS_CHECKBOX_ON_CLICKED_LISTENER(position);
            current_item.setMis_selected(!current_item.isMis_selected());
            holder.more_button_of_add_multiple_songs_to_multiple_songs.setImageResource(R.drawable.radio_button_on);
            holder.more_button_of_add_multiple_songs_to_multiple_songs.setSelected(current_item.isMis_selected());
            if(current_item.isMis_selected()){
                holder.more_button_of_add_multiple_songs_to_multiple_songs.setImageResource(R.drawable.radio_button_on);
                selected_songs_in_sequence.add(position);
            }
            else {
                for(int i=0 ; i<selected_songs_in_sequence.size();i++){
                    if(selected_songs_in_sequence.get(i)==position){
                        selected_songs_in_sequence.remove(i);
                    }
                }
                holder.more_button_of_add_multiple_songs_to_multiple_songs.setImageResource(R.drawable.radio_button_off);

            }
        });









    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }

    public ArrayList<Integer> getSelected_songs_in_sequence() {
        return selected_songs_in_sequence;
    }
}
