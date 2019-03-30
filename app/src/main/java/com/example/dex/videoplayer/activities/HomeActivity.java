package com.example.dex.videoplayer.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.dex.videoplayer.R;
import com.example.dex.videoplayer.adapter.FolderAdapter;
import com.example.dex.videoplayer.model.ListItemDetail;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS = 100;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    Context context;
    FolderAdapter folderlistAdapter;
    ArrayList listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    private void init() {

        context = getApplicationContext();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        checkPermission();


    }

    private void checkPermission() {

        /*RUN TIME PERMISSIONS*/
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    && (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            Log.e("Else", "Else");
            FolderList();
        }

    }

    private void FolderList() {

        Uri uri;
        Cursor cursor;

        String absPathOfImage = null;
        String absPathOfFile = null;
        String folderName = null;

        int column_index_data,
                column_index_folder_name,
                column_index_file_name,
                column_id,
                thumbnail,
                count;


        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Thumbnails.DATA};


        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;

        cursor = context.getContentResolver()
                .query(uri,
                        projection,
                        null,
                        null,
                        orderBy + " DESC");

//        count = cursor.getColumnIndex(MediaStore.Video.Media._COUNT);
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_file_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
//        count = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._COUNT);

        column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        thumbnail = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);

        while (cursor.moveToNext()) {
            absPathOfImage = cursor.getString(column_index_file_name);
            absPathOfFile = cursor.getString(column_index_data);
            folderName = cursor.getString(column_index_folder_name);


            Log.d("Column", absPathOfImage);
            Log.d("Folder", cursor.getString(column_index_folder_name));
            Log.d("column_id", cursor.getString(column_id));
            Log.d("thum", cursor.getString(thumbnail));
//            Log.d("count",cursor.getString(count));

            // Instance of model
            ListItemDetail listDetail = new ListItemDetail();
            listDetail.setFile_selected(false);
            listDetail.setTitle(folderName);
            listDetail.setAbs_file_path(absPathOfFile);
            listDetail.setThumbnail(cursor.getString(thumbnail));

            listItems.add(listDetail);


        }
        folderlistAdapter = new FolderAdapter(context, listItems);
        recyclerView.setAdapter(folderlistAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        FolderList();
                    } else {
                        Toast.makeText(HomeActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
