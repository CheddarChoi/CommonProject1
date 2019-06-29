package com.example.commonproject1.Tab_3;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commonproject1.R;

import java.io.InputStream;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.util.ArrayList;

public class Tab_3 extends Fragment {
    ImageView original_Image;
    RecyclerView filtered_Images;
    ArrayList<ImageItem> imagelist;
    LinearLayoutManager mLinearLayoutManager;
    FilteredImageAdapter filtered_Images_Adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagelist = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.tab_3_main, container, false);

        // codes for original images
        original_Image = view.findViewById(R.id.original_image);
        int image_number = 1;
        original_Image.setImageResource(getResources().getIdentifier("sample" + image_number,"drawable",getActivity().getPackageName()));

        original_Image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // HERE
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

                System.out.println("clicked ><");
                Toast.makeText(getActivity().getApplicationContext(), "new image", Toast.LENGTH_SHORT).show();
            }
        });


        // codes for filtered images
        for (int i=1; i<=11; i++){
            ImageItem item = new ImageItem(getResources().getIdentifier("filter_" + i,"drawable",getActivity().getPackageName()), "filter "+i);
            imagelist.add(item);
        }

        // show images in image list
        filtered_Images = view.findViewById(R.id.filtered_images);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        filtered_Images.setLayoutManager(mLinearLayoutManager);

        filtered_Images_Adapter = new FilteredImageAdapter(imagelist, view, getActivity());
        filtered_Images.setAdapter(filtered_Images_Adapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == getActivity().RESULT_OK) {
                try {
                    // make bitmap image
                    InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // show image
                    original_Image.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}