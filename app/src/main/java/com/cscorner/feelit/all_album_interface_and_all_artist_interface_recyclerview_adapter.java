package com.cscorner.feelit;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class all_album_interface_and_all_artist_interface_recyclerview_adapter extends RecyclerView.Adapter<all_album_interface_and_all_artist_interface_recyclerview_adapter.all_album_view_holder> {

    private ArrayList<all_album_interface_all_all_artist_interface_recyclerview_item_class> marrayList;
    private ON_CLICKED_LISTENER mlistener;
    public interface ON_CLICKED_LISTENER{
        void ON_ITEM_CLICKED(long ALBUM_ART,String ALBUM_NAME,int TOTAL_ALBUM_SONGS,int ALBUM_POSITION) throws IOException;
    }
    public void SET_ON_CLICKED_LISTENER(ON_CLICKED_LISTENER listener){
        mlistener=listener;
    }
    public all_album_interface_and_all_artist_interface_recyclerview_adapter(ArrayList<all_album_interface_all_all_artist_interface_recyclerview_item_class> arrayList){
        marrayList=arrayList;
    }

    @NonNull
    @Override
    public all_album_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_album_interface_recyclerview_layout,parent,false);
        all_album_view_holder evh =new all_album_view_holder(view,mlistener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull all_album_view_holder holder, @SuppressLint("RecyclerView") int position) {
        all_album_interface_all_all_artist_interface_recyclerview_item_class current_item= marrayList.get(position);
        long Album_ID = current_item.getMalbum_art();
        Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + Album_ID);

        Picasso.get().load(albumArtUri).into(holder.album_art_image_view);




        holder.album_name_text_view.setText(current_item.getMalbum_name());
        if(current_item.getMtotal_songs()==1){
            holder.total_album_songs_text_view.setText(String.format("SONG : %d",current_item.getMtotal_songs()));

        }else {
            holder.total_album_songs_text_view.setText(String.format("SONGS : %d",current_item.getMtotal_songs()));

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mlistener.ON_ITEM_CLICKED(current_item.getMalbum_art(),current_item.getMalbum_name(),current_item.getMtotal_songs(),position);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }

    public static class all_album_view_holder extends RecyclerView.ViewHolder {
        private ImageView album_art_image_view;
        private TextView album_name_text_view;
        private TextView total_album_songs_text_view;
        public all_album_view_holder(@NonNull View itemView,ON_CLICKED_LISTENER listener) {
            super(itemView);
            album_art_image_view=itemView.findViewById(R.id.album_art_imageview_all_album_interface);
            album_name_text_view=itemView.findViewById(R.id.album_name_text_view_all_album_interface);
            total_album_songs_text_view=itemView.findViewById(R.id.total_songs_text_view_all_album_interface);



        }
    }
}
