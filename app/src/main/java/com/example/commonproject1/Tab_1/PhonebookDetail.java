package com.example.commonproject1.Tab_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commonproject1.R;

public class PhonebookDetail extends AppCompatActivity implements View.OnClickListener {
    TextView textView_name;
    TextView textView_number;
    ImageView ImageView_photo;
    String name, number;
    Bitmap photo;
    ImageButton callbutton, messagebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook_detail);
        name = getIntent().getStringExtra("name");
        number = getIntent().getStringExtra("number");
        byte[] bytes = getIntent().getByteArrayExtra("photo");
        photo = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        // Get the ActionBar here to configure the way it behaves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Contact Detail");

        textView_name = findViewById(R.id.textView_phonebook_name);
        textView_number = findViewById(R.id.textView_phonebook_number);
        ImageView_photo = findViewById(R.id.imageView_phonebook);
        callbutton = findViewById(R.id.CallButton);
        messagebutton = findViewById(R.id.messageButton);

        callbutton.setOnClickListener(this);
        messagebutton.setOnClickListener(this);

        textView_name.setText(name);
        textView_number.setText(number);
        ImageView_photo.setImageBitmap(photo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.CallButton:
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + number)));
                break;
            case R.id.messageButton:
                Intent intentsms = new Intent( Intent.ACTION_VIEW, Uri.parse("sms:" + number));
                intentsms.putExtra("sms_body", "Test text...");
                startActivity(intentsms);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
