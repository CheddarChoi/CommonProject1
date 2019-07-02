package com.example.commonproject1.Tab_3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

    final int MY_PERMISSIONS_REQUEST_ALBUM = 101;
    final int PIXEL_THRESHOLD = 400;

    String[] filter_names = { "Original Image", "Gray scale filter", "Gaussian Blur filter", "Sharpening filter",
            "Bright filter", "Dark filter", "Red mood filter", "Green mood filter", "Blue mood filter",
            "HDR filter", "Non photorealistic filter 1", "Non photorealistic filter 2"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagelist = new ArrayList<>();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        System.out.println("permission request");

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ALBUM: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

                    System.out.println("clicked ><");
                    Toast.makeText(getActivity().getApplicationContext(), "new image", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(),"Permission denied :(", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_3_main, container, false);

        original_Image = view.findViewById(R.id.original_image);

        original_Image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // HERE

                // permission check
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_ALBUM);
                }
                else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

                    System.out.println("clicked ><");
                    Toast.makeText(getActivity().getApplicationContext(), "new image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imagelist = new ArrayList<>();
        // codes for filtered images
        for (int i=0; i<=11; i++){
            ImageItem item = new ImageItem(getResources().getIdentifier("filter_" + i,"drawable",getActivity().getPackageName()), filter_names[i]);
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
                    Uri fileUri = data.getData();
                    InputStream in = getActivity().getContentResolver().openInputStream(fileUri);
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    // resizing image if too big
                    if (img.getHeight() > PIXEL_THRESHOLD | img.getWidth() > PIXEL_THRESHOLD){
                        int dstWidth = img.getWidth();
                        int dstHeight = img.getHeight();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        if (dstHeight > dstWidth){
                            dstWidth = dstWidth*PIXEL_THRESHOLD/dstHeight;
                            dstHeight = PIXEL_THRESHOLD;
                        }
                        else {
                            dstHeight = dstHeight*PIXEL_THRESHOLD/dstWidth;
                            dstWidth = PIXEL_THRESHOLD;
                        }
                        img = Bitmap.createScaledBitmap(img, dstWidth, dstHeight, true);
                    }

                    // show image
                    original_Image.setImageBitmap(img);
                    filtered_Images_Adapter.setOriginal(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}