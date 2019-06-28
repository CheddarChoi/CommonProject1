package com.example.commonproject1.Tab_1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Tab_1 extends Fragment {
    ArrayList<Item> phonebooklist;
    private CustomAdapter phonebookadapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    //variables for fab animation
    private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, fab_rotate, fab_rotate_backward;
    private Boolean isFabOpen = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phonebooklist = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_1_main, container, false);

        //variables for fab animations
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        fab_rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_rotate);
        fab_rotate_backward = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_rotate_backward);

        fab = view.findViewById(R.id.fab);
        fab1 = view.findViewById(R.id.fab1);
        fab2 = view.findViewById(R.id.fab2);
        //

        mRecyclerView = view.findViewById(R.id.phonebook);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        phonebookadapter = new CustomAdapter(phonebooklist);
        mRecyclerView.setAdapter(phonebookadapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }});

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();

                JSONArray jsonArray = getJSONFromContactList();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject person = jsonArray.getJSONObject(i);
                        Item item = new Item(person.get("name").toString(), person.get("number").toString());
                        phonebooklist.add(item);
                    }catch (JSONException e) {
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
                            Item item = new Item(dialogFragment.getInputName(), dialogFragment.getInputNumber());
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
        String json;
        try {
            InputStream is = getActivity().getAssets().open("contacts");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
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
                    try {
                        person.put("name", name);
                        person.put("number", number);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(person);
                }
            }
        }
        return jsonArray;
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

}