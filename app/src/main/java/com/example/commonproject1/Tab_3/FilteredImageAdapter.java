package com.example.commonproject1.Tab_3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.commonproject1.R;

import java.util.ArrayList;

public class FilteredImageAdapter extends RecyclerView.Adapter<FilteredImageAdapter.CustomViewHolder> {
    private ArrayList<ImageItem> imagelist;
    private View my_view;
    Context my_context;
    private Bitmap original;
    private Bitmap filtered;

    public FilteredImageAdapter(ArrayList<ImageItem> list, View v, Context c) {
        imagelist = list;
        my_view = v;
        my_context = c;
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
    public void onBindViewHolder(CustomViewHolder viewHolder, final int position) {
        viewHolder.filtered_image.setImageResource(imagelist.get(position).getImage());
        viewHolder.filter_name.setText(imagelist.get(position).getFilterName());
        viewHolder.filtered_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView original_view = my_view.findViewById(R.id.original_image);
                Filter filter = new Filter();

                if (original == null)
                {
                    Drawable d = original_view.getDrawable();
                    original = ((BitmapDrawable)d).getBitmap();
                }

                switch(position){
                    case 0:
                        filtered = filter.doGreyscale(original);
                        original_view.setImageBitmap(filtered);
                        Toast.makeText(my_context.getApplicationContext(), "gray scale filter", Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        filtered = filter.applyGaussianBlur(original);
                        original_view.setImageBitmap(filtered);
                        Toast.makeText(my_context.getApplicationContext(), "gaussian filter", Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        filtered = filter.sharpen(original, 11);
                        original_view.setImageBitmap(filtered);
                        Toast.makeText(my_context.getApplicationContext(), "sharpening filter", Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        filtered = filter.doBrightness(original, 80);
                        original_view.setImageBitmap(filtered);
                        Toast.makeText(my_context.getApplicationContext(), "bright filter", Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        filtered = filter.doBrightness(original, -60);
                        original_view.setImageBitmap(filtered);
                        Toast.makeText(my_context.getApplicationContext(), "dark filter", Toast.LENGTH_SHORT).show();
                        break;

                    case 5:
                        filtered = filter.createSepiaToningEffect(original, 256, 0.4, 0.3, 0.3);
                        original_view.setImageBitmap(filtered);
                        Toast.makeText(my_context.getApplicationContext(), "red-like filter", Toast.LENGTH_SHORT).show();
                        break;

                    case 6:
                        filtered = filter.createSepiaToningEffect(original, 256, 0.3, 0.4, 0.3);
                        original_view.setImageBitmap(filtered);
                        Toast.makeText(my_context.getApplicationContext(), "green-like filter", Toast.LENGTH_SHORT).show();
                        break;

                    case 7:
                        filtered = filter.createSepiaToningEffect(original, 256, 0.3, 0.3, 0.4);
                        original_view.setImageBitmap(filtered);
                        Toast.makeText(my_context.getApplicationContext(), "blue-like filter", Toast.LENGTH_SHORT).show();
                        break;

                    case 8:
                        filtered = filter.histogram(original);
                        original_view.setImageBitmap(filtered);
                        Toast.makeText(my_context.getApplicationContext(), "High Dynamic Range filter", Toast.LENGTH_SHORT).show();
                        break;

                    case 9:
                        filtered = filter.nonphoto(original, 64);
                        original_view.setImageBitmap(filtered);
                        Toast.makeText(my_context.getApplicationContext(), "Non-Photo realistic filter High", Toast.LENGTH_SHORT).show();
                        break;

                    case 10:
                        filtered = filter.nonphoto(original, 32);
                        original_view.setImageBitmap(filtered);
                        Toast.makeText(my_context.getApplicationContext(), "Non-Photo realistic filter Low", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }
}