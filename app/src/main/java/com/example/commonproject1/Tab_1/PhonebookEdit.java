package com.example.commonproject1.Tab_1;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.commonproject1.R;

public class PhonebookEdit extends AppCompatActivity {
    EditText edit_name;
    EditText edit_number;
    ImageView ImageView_photo;
    String name, number;
    Bitmap photo;    boolean isEdit = false;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook_edit);
        position = getIntent().getIntExtra("position",-1);
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

        edit_name = findViewById(R.id.edit_name);
        edit_number = findViewById(R.id.edit_number);
        ImageView_photo = findViewById(R.id.imageView_phonebook);

        edit_name.setText(name);
        edit_number.setText(number);
        ImageView_photo.setImageBitmap(photo);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("isEdit", isEdit);
        resultIntent.putExtra("name", edit_name.getText().toString());
        resultIntent.putExtra("number", edit_number.getText().toString());
        setResult(Activity.RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_with_commit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.commit:
                if (edit_name.getText().toString().length() == 0 | edit_number.getText().toString().length() == 0){
                    Toast.makeText(this,"이름과 전화번호를 입력하세요", Toast.LENGTH_LONG).show();
                }
                else{
                    isEdit = true;
                    onBackPressed();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}