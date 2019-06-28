package com.example.commonproject1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.commonproject1.Tab_1.Item;
import com.example.commonproject1.Tab_1.Tab_1;
import com.example.commonproject1.Tab_2.Tab_2;
import com.example.commonproject1.Tab_3.Tab_3;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Item> phonebooklist = new ArrayList<>();
    private ViewPager pager;
    FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = findViewById(R.id.pager);

        adapter.addItem(new Tab_1(),"PhoneBook");
        adapter.addItem(new Tab_2(),"Gallery");
        adapter.addItem(new Tab_3(),"What's New?");
        pager.setAdapter(adapter);

        TabLayout tab_layout = findViewById(R.id.tab_layout);
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        tab_layout.setupWithViewPager(pager);

        pager.setCurrentItem(0);
    }

    public ArrayList<Item> getPhonebooklist() {
        return phonebooklist;
    }

    public void setPhonebooklist(ArrayList<Item> phonebooklist) {
        this.phonebooklist = phonebooklist;
    }

    public void addPhonebooklist(Item phonebookitem) {
        this.phonebooklist.add(phonebookitem);
    }
}
