package com.example.commonproject1;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.commonproject1.Tab_1.Tab_1;
import com.example.commonproject1.Tab_2.Tab_2;
import com.example.commonproject1.Tab_3.Tab_3;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    public static Context mContext;
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

        mContext = this;
    }


    // 뒤로가기 버튼을 두번 연속으로 눌러야 종료되게끔 하는 메소드
    private long time= 0;
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
        }
    }
}
