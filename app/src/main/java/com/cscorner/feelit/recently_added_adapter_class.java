package com.cscorner.feelit;

import static java.io.File.createTempFile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class recently_added_adapter_class extends RecyclerView.Adapter<recently_added_adapter_class.recently_added_view_holder> {
    private OnCLICK_LISTENER mlistner;
    private ArrayList<Recently_added_recyclerview_elements_item_class> MarrayList;

    public recently_added_adapter_class(ArrayList<Recently_added_recyclerview_elements_item_class> arrayList){
        MarrayList=arrayList;
    }
    public void set_ON_CLICKED_LISTENER(OnCLICK_LISTENER listner){
        mlistner=listner;
    }
    public interface OnCLICK_LISTENER{
        void on_ITEM_Clicked(int position) throws Exception;
        void more_button_ITEM_Clicked(View view,int position);
        void on_ITEM_LONG_CLICKED(int Long_pressed_song);
    }
    public void updateData(ArrayList<Recently_added_recyclerview_elements_item_class> newDataList) {
        MarrayList.clear();
        MarrayList.addAll(newDataList);
    }
    public static class recently_added_view_holder extends RecyclerView.ViewHolder{
        private ImageView album_art_image_view;
        private TextView song_name_text_view;
        private TextView artist_name_text_view;
        private TextView song_duration_text_view;
        private ImageView more_button;

        public recently_added_view_holder(@NonNull View itemView,OnCLICK_LISTENER listener) {
            super(itemView);
            song_name_text_view=itemView.findViewById(R.id.song_name_text_view_recently_added);
            artist_name_text_view=itemView.findViewById(R.id.artist_name_text_view_recently_added);
            song_duration_text_view=itemView.findViewById(R.id.song_duration_text_view_recently_added);
            album_art_image_view=itemView.findViewById(R.id.album_art_image_view_recently_added);
            more_button =itemView.findViewById(R.id.more_button_image_button_recently_added);
            more_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.more_button_ITEM_Clicked(itemView,getLayoutPosition());
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getLayoutPosition();

                    try {
                        listener.on_ITEM_Clicked(position);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.on_ITEM_LONG_CLICKED(getLayoutPosition());
                    return false;
                }
            });

        }

    }

    @NonNull
    @Override
    public recently_added_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recently_added_recyclerview_element_item_layout,parent,false);
        recently_added_view_holder evh=new recently_added_view_holder(v,mlistner);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull recently_added_view_holder holder, int position) {
        Recently_added_recyclerview_elements_item_class current_item=MarrayList.get(position);
        if(current_item.getMsong_name().length()>36){
            holder.song_name_text_view.setText(current_item.getMsong_name().substring(0,36)+"...");

        }
        else {
            holder.song_name_text_view.setText(current_item.getMsong_name());

        }



        int Artist_name_length=current_item.getMartist().length();

        if(Artist_name_length>20){
            holder.artist_name_text_view.setText(current_item.getMartist().substring(0,20)+"..");
        }
        else {
            holder.artist_name_text_view.setText(current_item.getMartist());
        }
        int duration=current_item.getMduration();
        int min=(duration/1000)/60;
        int sec=(duration/1000)%60;
        holder.song_duration_text_view.setText(String.format("ㅣ%02d:%02d",min,sec));

        long Album_ID = current_item.getMalbum_art();

        if(MarrayList.size()>7){
            Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + Album_ID);
            Picasso.get().load(albumArtUri).into(holder.album_art_image_view);

        }else{
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(current_item.getMpath());

            byte[] albumArtBytes = retriever.getEmbeddedPicture();
            if (albumArtBytes != null) {
                Bitmap albumArtBitmap = BitmapFactory.decodeByteArray(albumArtBytes, 0, albumArtBytes.length);


                File tempFile = null;
                try {
                    tempFile = createTempFile("album_art", ".jpg");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    FileOutputStream fos = new FileOutputStream(tempFile);
                    albumArtBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

// Get the Uri of the temporary file
                Uri uri = Uri.fromFile(tempFile);

                Picasso.get().load(uri).into(holder.album_art_image_view);


//            Picasso.get().load(uri).into(music_player_album_art_image_view);
//            Picasso.get().load(uri).into(miniplayer_album_art_imageview);
//            music_player_album_art_image_view.setImageBitmap(albumArtBitmap);
//            miniplayer_album_art_imageview.setImageBitmap(albumArtBitmap);

                // Now you have the album art bitmap, you can display it or process it further
            } else {
                // No album art available
                Picasso.get().load(R.drawable.logo).into(holder.album_art_image_view);
            }
            try {
                retriever.release();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        holder.more_button.setImageResource(R.drawable.more_button);

    }


    @Override
    public int getItemCount() {
        return MarrayList.size();
    }


}
