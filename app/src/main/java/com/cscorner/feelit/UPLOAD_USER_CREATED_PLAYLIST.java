package com.cscorner.feelit;

import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.arrayList_for_all_playlists;
import static com.cscorner.feelit.UPLOAD_SONGS_ACTIVITY.Upload_arrayList;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;





import java.io.*;


import okhttp3.*;
import retrofit2.*;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public class UPLOAD_USER_CREATED_PLAYLIST extends AppCompatActivity {
    RecyclerView recyclerView;
    recently_added_adapter_class Adapter_For_Upload_Arraylist;
    private static final String SERVER_URL = "https://mega-cloud-storage-bridge.onrender.com/";
    LinearLayoutManager layoutManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_user_created_playlist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ArrayList<Recently_added_recyclerview_elements_item_class> arrayList=new ArrayList<>();
        for(Recently_added_recyclerview_elements_item_class i : Upload_arrayList){
            arrayList.add(i);
        }
        recyclerView=findViewById(R.id.recyclerview_of_upload_user_created);
        recyclerView.setHasFixedSize(true);
        Adapter_For_Upload_Arraylist=new recently_added_adapter_class(arrayList);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(Adapter_For_Upload_Arraylist);
        recyclerView.setLayoutManager(layoutManager);
        Adapter_For_Upload_Arraylist.set_ON_CLICKED_LISTENER(new recently_added_adapter_class.OnCLICK_LISTENER() {
            @Override
            public void on_ITEM_Clicked(int position) throws Exception {

            }

            @Override
            public void more_button_ITEM_Clicked(View view, int position) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END);
                popupMenu.inflate(R.menu.upload_song_popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.upload) {
                            String filePath =arrayList.get(position).getMpath() ;
                            Log.d("dta", filePath);

                            Uri uri = Uri.fromFile(new File(filePath));
                            uploadFile(uri);

                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

            @Override
            public void on_ITEM_LONG_CLICKED(int Long_pressed_song) {

            }
        });


    }
    public void uploadFile(Uri uri) {
        try {

//            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
//                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
//                return;
//            }

            String uid = "uid1";
            String folder = "folder2";

            InputStream inputStream = getContentResolver().openInputStream(uri);

            // 🔥 Get real file name
            String fileName = new File(uri.getPath()).getName();

            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (cursor.moveToFirst() && nameIndex != -1) {
                    fileName = cursor.getString(nameIndex);
                }
                cursor.close();
            }

            // Ensure extension
            if (!fileName.contains(".")) {
                String mime = getContentResolver().getType(uri);
                if (mime != null) {
                    String ext = android.webkit.MimeTypeMap.getSingleton()
                            .getExtensionFromMimeType(mime);
                    if (ext != null) fileName += "." + ext;
                }
            }

            Log.d("FILE_NAME", fileName);

            File file = new File(getCacheDir(), fileName);
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int len;

            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            outputStream.close();
            inputStream.close();

            String type = getContentResolver().getType(uri);

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse(type != null ? type : "*/*"), file);

            MultipartBody.Part filePart =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            // 🔥 UID + FOLDER
            RequestBody uidPart = RequestBody.create(
                    MediaType.parse("text/plain"), uid
            );

            RequestBody folderPart = RequestBody.create(
                    MediaType.parse("text/plain"), folder
            );

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .client(client)
                    .build();

            ApiService api = retrofit.create(ApiService.class);

            api.uploadFile(uidPart, folderPart, filePart).enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json = response.body().string();
                        Log.d("SERVER_RESPONSE", json);

                        JSONObject obj = new JSONObject(json);

                        String fileName = obj.getString("fileName");
                        String nodeId = obj.getString("nodeId"); // 🔥 IMPORTANT

                        Log.d("NODE_ID", nodeId);

                        // 🔥 Encode filename
                        String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

                        String downloadUrl = SERVER_URL + "download/"
                                + nodeId + "/"
                                + encodedFileName;
                        Log.d("DOWNLOAD_URL",downloadUrl+" "+fileName);
//                        URI=downloadUrl;
//                        String message=String.format("File Name:%s\n\nNode ID:%s\n\nDownload Url:%s",fileName,nodeId,downloadUrl);
//                        NODEID=nodeId;
//                        FILENAME=fileName;
////                        URItextView.setText(URI);
//                        id_textView.setText(message);
//                        downloadFile(downloadUrl, fileName);

                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("UPLOAD_ERROR", t.toString());
                }
            });

        } catch (Exception e) {
            Log.e("FILE_ERROR", e.getMessage());
        }
    }
    public interface ApiService {
        @Multipart
        @POST("upload")
        Call<ResponseBody> uploadFile(
                @Part("uid") RequestBody uid,
                @Part("folder") RequestBody folder,
                @Part MultipartBody.Part file
        );
    }
}