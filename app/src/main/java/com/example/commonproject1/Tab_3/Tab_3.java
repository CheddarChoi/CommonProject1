package com.example.commonproject1.Tab_3;

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
        int image_number = 1;   // 여기서 original image 번호를 지정하시면 됩니다.
        original_Image.setImageResource(getResources().getIdentifier("sample" + image_number,"drawable",getActivity().getPackageName()));


        // codes for filtered images
        for (int i=1; i<=30; i++){
            ImageItem item = new ImageItem(getResources().getIdentifier("sample" + i,"drawable",getActivity().getPackageName()), "filter "+i);
            imagelist.add(item);
        }
            // 위의 코드에서 일단 모든 sample 사진들을 다 imagelist에 넣는 걸로 해 뒀는데 나중에 필터링 된 사진 이미지를 넣는 것으로 교체하시면 됩니다.

            // 여기부터는 imagelist에 있는 사진들을 띄우는 코드입니다.
        filtered_Images = view.findViewById(R.id.filtered_images);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        filtered_Images.setLayoutManager(mLinearLayoutManager);

        filtered_Images_Adapter = new FilteredImageAdapter(imagelist);
        filtered_Images.setAdapter(filtered_Images_Adapter);

        return view;
    }
}