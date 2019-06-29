package com.example.commonproject1.Tab_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commonproject1.R;


import java.util.ArrayList;

public class PhonebookAdapter extends RecyclerView.Adapter<PhonebookAdapter.ViewHolder> {
    private ArrayList<Item> mList;
    private Context context;
    private FragmentManager fm;

    public PhonebookAdapter(ArrayList<Item> list, Context context) {
        mList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView number;
        public ImageView image;
        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.textView_phonebook_name);
            number = view.findViewById(R.id.textView_phonebook_number);
            image = view.findViewById(R.id.imageView_phonebook);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.phonebook_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.name.setText(mList.get(position).getName());
        viewHolder.number.setText(mList.get(position).getNumber());
        if (mList.get(position).getPhoto() == null) {
            viewHolder.image.setImageResource(viewHolder.itemView.getContext().getResources().getIdentifier("robot" + (int) (Math.random() * 3 + 1), "drawable", viewHolder.itemView.getContext().getPackageName()));
        }
        else{
            Bitmap photo = mList.get(position).getPhoto();
            viewHolder.image.setImageBitmap(Bitmap.createScaledBitmap(photo, 50, 50, true));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}