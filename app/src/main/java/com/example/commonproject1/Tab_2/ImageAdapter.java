package com.example.commonproject1.Tab_2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.commonproject1.R;

public class ImageAdapter extends BaseAdapter {

    private int pad;

    private Context mContext;

    private Integer[] mThumbIds = {
            R.drawable.sample1,
            R.drawable.sample2,
            R.drawable.sample3,
            R.drawable.sample4,
            R.drawable.sample5,
            R.drawable.sample6,
            R.drawable.sample7,
            R.drawable.sample8,
            R.drawable.sample9,
            R.drawable.sample10,
            R.drawable.sample11,
            R.drawable.sample12,
            R.drawable.sample13,
            R.drawable.sample14,
            R.drawable.sample15,
            R.drawable.sample16,
            R.drawable.sample17,
            R.drawable.sample18,
            R.drawable.sample19,
            R.drawable.sample20,
            R.drawable.sample21,
            R.drawable.sample22,
            R.drawable.sample23,
            R.drawable.sample24,
            R.drawable.sample25,
            R.drawable.sample26,
            R.drawable.sample27,
            R.drawable.sample28,
            R.drawable.sample29,
            R.drawable.sample30
    };

    public ImageAdapter(Context c){
        mContext = c;
        pad = 8;
    }


    @Override
    public int getCount() {
        // number of items in image set
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return image based on clicked position
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(240, 160));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(pad, pad, pad, pad);
        }else{
            imageView = (ImageView) convertView;
        }

        // set image based on position
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }


}
