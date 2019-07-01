package com.example.commonproject1.Tab_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import com.example.commonproject1.R;

public class PhonebookDetail extends AppCompatActivity implements View.OnClickListener {
    TextView textView_name;
    TextView textView_number;
    ImageView ImageView_photo;
    String name, number;
    Bitmap photo;
    ImageButton callbutton, messagebutton;
    byte[] bytes;
    boolean isDelete = false;
    boolean isEdit = false;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook_detail);
        position = getIntent().getIntExtra("position",-1);
        name = getIntent().getStringExtra("name");
        number = getIntent().getStringExtra("number");
        bytes = getIntent().getByteArrayExtra("photo");
        photo = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        // Get the ActionBar here to configure the way it behaves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Contact Detail");

        textView_name = findViewById(R.id.edit_name);
        textView_number = findViewById(R.id.edit_number);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_with_edit_delete, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.CallButton:
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + number)));
                break;
            case R.id.messageButton:
                Intent intentsms = new Intent( Intent.ACTION_VIEW, Uri.parse("sms:" + number));
                intentsms.putExtra("sms_body", "");
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
            case R.id.edit:
                Intent edit_intent = new Intent(this, PhonebookEdit.class);
                edit_intent.putExtra("position",position);
                edit_intent.putExtra("name",name);
                edit_intent.putExtra("number",number);
                edit_intent.putExtra("photo",bytes);
                startActivityForResult(edit_intent, 2);
                return true;
            case R.id.delete:
                isDelete = true;
                onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("isDelete", isDelete);
        resultIntent.putExtra("isEdit", isEdit);
        resultIntent.putExtra("position", position);
        resultIntent.putExtra("name", name);
        resultIntent.putExtra("number", number);
        setResult(Activity.RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 2 : {
                if (resultCode == Activity.RESULT_OK) {
                    isEdit = data.getBooleanExtra("isEdit", false);
                    if (isEdit){
                        name = data.getStringExtra("name");
                        number = data.getStringExtra("number");
                        textView_name.setText(name);
                        textView_number.setText(number);
                    }
                }
                break;
            }
        }
    }
}
