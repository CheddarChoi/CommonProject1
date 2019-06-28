package com.example.commonproject1.Tab_3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.commonproject1.R;

import java.util.ArrayList;

public class FilteredImageAdapter extends RecyclerView.Adapter<FilteredImageAdapter.CustomViewHolder> {
    private ArrayList<ImageItem> imagelist;

    public FilteredImageAdapter(ArrayList<ImageItem> list) {
        imagelist = list;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView filtered_image;
        public TextView filter_name;
        public CustomViewHolder(View view) {
            super(view);
            filtered_image = view.findViewById(R.id.filtered_image);
            filter_name = view.findViewById(R.id.filter_name);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filtered_image_list, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder viewHolder, int position) {
        viewHolder.filtered_image.setImageResource(imagelist.get(position).getImage());
        viewHolder.filter_name.setText(imagelist.get(position).getFilterName());
        viewHolder.filtered_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // click 시 필요한 동작 정의
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }
}