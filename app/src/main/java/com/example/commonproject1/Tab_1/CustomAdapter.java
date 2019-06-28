package com.example.commonproject1.Tab_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.commonproject1.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<Item> mList;

    public CustomAdapter(ArrayList<Item> list) {
        mList = list;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView number;
        public CustomViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.textView_phonebook_name);
            number = view.findViewById(R.id.textView_phonebook_number);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.phonebook_list, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder viewHolder, int position) {
        viewHolder.name.setText(mList.get(position).getName());
        viewHolder.number.setText(mList.get(position).getNumber());
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // click 시 필요한 동작 정의
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}