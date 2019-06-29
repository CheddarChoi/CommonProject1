package com.example.commonproject1.Tab_1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commonproject1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Tab_1 extends Fragment {
    private PhonebookAdapter phonebookadapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    ArrayList<Item> phonebooklist;

    //variables for fab animation
    private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, fab_rotate, fab_rotate_backward;
    private Boolean isFabOpen = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_1_main, container, false);

        phonebooklist = new ArrayList<>();

        //variables for fab animations
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        fab_rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_rotate);
        fab_rotate_backward = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_rotate_backward);

        fab = view.findViewById(R.id.fab);
        fab1 = view.findViewById(R.id.fab1);
        fab2 = view.findViewById(R.id.fab2);
        //

        final JSONArray jsonArray = getJSONFromContactList();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject person = jsonArray.getJSONObject(i);
                Item item = new Item(person.get("name").toString(), person.get("number").toString(),(Bitmap) person.get("photo"));
                phonebooklist.add(item);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mRecyclerView = view.findViewById(R.id.phonebook);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        phonebookadapter = new PhonebookAdapter(phonebooklist, getActivity());
        mRecyclerView.setAdapter(phonebookadapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), PhonebookDetail.class);

                intent.putExtra("name",phonebooklist.get(position).getName());
                intent.putExtra("number",phonebooklist.get(position).getNumber());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bmp = phonebooklist.get(position).getPhoto();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                intent.putExtra("photo",bytes);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                AlertDialog.Builder oDialog = new AlertDialog.Builder(getContext(),android.R.style.Theme_DeviceDefault_Light_Dialog);
                oDialog.setMessage("연락처를 삭제합니다.")
                        .setTitle("Delete Contact")
                        .setPositiveButton("Commit", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(getContext(), "commit", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(getContext(), "cancel", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
            }
        }));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }});

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                JSONArray dummyjsonArray = null;
                try {
                    JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
                    dummyjsonArray = jsonObject.getJSONArray("person");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0 ; i < dummyjsonArray.length() ; i++) {
                    try {
                        JSONObject person = dummyjsonArray.getJSONObject(i);
                        Item item = new Item(person.get("name").toString(), person.get("number").toString(), generateRandomPhoto());
                        phonebooklist.add(item);
                    } catch (JSONException e) {
                        System.out.println("check");
                        e.printStackTrace();
                    }
                }
                phonebookadapter.notifyDataSetChanged();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                FragmentManager fm = getFragmentManager();
                final CustomDialogFragment dialogFragment = new CustomDialogFragment();
                dialogFragment.show(fm, "input_dialog");
                fm.executePendingTransactions();
                dialogFragment.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (dialogFragment.isValid()) {
                            Item item = new Item(dialogFragment.getInputName(), dialogFragment.getInputNumber(),generateRandomPhoto());
                            phonebooklist.add(item);
                            phonebookadapter.notifyDataSetChanged();
                        }
                    }
                });
            }});
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public String loadJSONFromAsset() {
        try {
            InputStream is = getActivity().getAssets().open("contacts");
            int size = is.available();  //assigning size
            byte[] buffer = new byte[size]; //make a buffer
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8"); //assigning read values into string
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getJSONFromContactList() {
        JSONArray jsonArray = new JSONArray();
        final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;

        // permission check
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME);
        if (cursor != null){
            if (cursor.getCount() > 0){
                while (cursor.moveToNext()){
                    JSONObject person = new JSONObject();
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    int photo_id = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID));
                    Bitmap bitmap = queryContactImage(photo_id);
                    if (bitmap == null){
                        bitmap = generateRandomPhoto();
                    }

                    try {
                        person.put("name", name);
                        person.put("number", number);
                        person.put("photo",bitmap);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(person);
                }
            }
        }
        return jsonArray;
    }

    private Bitmap queryContactImage(int imageDataRow) {
        Cursor c = getActivity().getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[] {ContactsContract.CommonDataKinds.Photo.PHOTO}, ContactsContract.Data._ID + "=?", new String[] {Integer.toString(imageDataRow)}, null);
        byte[] imageBytes = null;
        if (c != null) {
            if (c.moveToFirst()) {
                imageBytes = c.getBlob(0);
            }
            c.close();
        }

        if (imageBytes != null) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            return null;
        }
    }

    public void anim() {
        if (isFabOpen) {
            fab2.startAnimation(fab_close);
            fab1.startAnimation(fab_close);
            fab2.setClickable(false);
            fab1.setClickable(false);
            fab.startAnimation(fab_rotate_backward);
            isFabOpen = false;
        } else {
            fab2.startAnimation(fab_open);
            fab1.startAnimation(fab_open);
            fab2.setClickable(true);
            fab1.setClickable(true);
            fab.startAnimation(fab_rotate);
            isFabOpen = true;
        }
    }
    private Bitmap generateRandomPhoto() {
        int random_number = (int) (Math.random()*3);
        return BitmapFactory.decodeResource(getContext().getResources(),
                getResources().getIdentifier("robot" + (random_number+1),"drawable",getActivity().getPackageName()));
    }
}