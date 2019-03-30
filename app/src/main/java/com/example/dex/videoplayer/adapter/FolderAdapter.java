package com.example.dex.videoplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dex.videoplayer.R;
import com.example.dex.videoplayer.activities.VideoPlayerActivity;
import com.example.dex.videoplayer.model.ListItemDetail;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> {
    Context context;
    ArrayList<ListItemDetail> mlistItems;


    public FolderAdapter(Context context, ArrayList<ListItemDetail> listItems) {
        this.context = context;
        this.mlistItems = listItems;
    }

    @Override
    public FolderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.detail_item_list, parent, false);
        return new FolderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ListItemDetail mlistDetail = mlistItems.get(position);

        String thumb_url = mlistDetail.getThumbnail();
        String file_title = mlistDetail.getTitle();

        Glide.with(context)
                .load(thumb_url)
                .skipMemoryCache(false)
                .into(holder.thumb);

        holder.title.setText(file_title);

    }

    @Override
    public int getItemCount() {
        return mlistItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumb;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);

            thumb = itemView.findViewById(R.id.file_thumb);
            title = itemView.findViewById(R.id.file_title);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {


                ListItemDetail listDetail = mlistItems.get(position);
                String abs_file_path = listDetail.getAbs_file_path();

                Intent videoIntent = new Intent(context, VideoPlayerActivity.class);
                videoIntent.putExtra("VideoUrl", abs_file_path);
                context.startActivity(videoIntent);

            }
        }
    }
}
