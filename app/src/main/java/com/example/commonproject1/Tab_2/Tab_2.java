package com.example.commonproject1.Tab_2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.style.TypefaceSpan;
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

import com.example.commonproject1.MainActivity;
import com.example.commonproject1.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Tab_2 extends Fragment {
    private View view;

    ArrayList<String> image_names;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image_names = new ArrayList<String>(
                Arrays.asList("Lab top on the desk",
                        "Forest with sky",
                        "Ecstatic beach",
                        "Snow mountain",
                        "Daddy and his son",
                        "Couple :(",
                        "Handsome guy",
                        "Building and sky",
                        "Man and dog",
                        "Rock and river",
                        "Cute dog",
                        "Blind girl",
                        "On the hill",
                        "Eagle",
                        "Desert Strike",
                        "Waterfall",
                        "Castle on the gill",
                        "Treacherous sea",
                        "MAN VS WILD",
                        "Coffee Time :P",
                        "Sunset",
                        "Couple 2 :'(",
                        "Guitar",
                        "idk but, probs Venice",
                        "Raindrop flower",
                        "Trail of nature",
                        "Pretending to study ><",
                        "Dandelion",
                        "Old Camera",
                        "Lonely Man")
        );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_2_main, container, false);

        final GridView gridview = view.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity()));
        final ImageView imageView = view.findViewById(R.id.image_view);
        imageView.setVisibility((View.GONE));

        final TextView imageName = view.findViewById(R.id.image_name);
        imageName.setVisibility(View.GONE);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DancingScript-Bold.ttf");
        imageName.setTypeface(type);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "IMAGE " + (position+1), Toast.LENGTH_SHORT).show();

                gridview.setVisibility(View.GONE);
                imageView.setImageResource(getResources().getIdentifier("sample" + (position+1),"drawable",getActivity().getPackageName()));
                imageView.setVisibility((View.VISIBLE));

                imageName.setText(image_names.get(position));
                imageName.setVisibility(View.VISIBLE);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imageView.setVisibility((View.INVISIBLE));
                gridview.setVisibility((View.VISIBLE));

                imageName.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }
}