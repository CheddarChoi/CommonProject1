package com.example.commonproject1.Tab_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.commonproject1.R;

public class Tab_2 extends Fragment {
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_2_main, container, false);

        final GridView gridview = view.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity()));
        final ImageView imageView = view.findViewById(R.id.image_view);
        imageView.setVisibility((View.GONE));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "IMAGE " + (position+1), Toast.LENGTH_SHORT).show();

                gridview.setVisibility(View.GONE);
                imageView.setImageResource(getResources().getIdentifier("sample" + (position+1),"drawable",getActivity().getPackageName()));
                imageView.setVisibility((View.VISIBLE));
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imageView.setVisibility((View.INVISIBLE));
                gridview.setVisibility((View.VISIBLE));
            }
        });
        return view;
    }
}