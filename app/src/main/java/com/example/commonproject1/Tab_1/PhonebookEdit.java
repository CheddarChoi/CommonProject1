package com.example.commonproject1.Tab_1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.commonproject1.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PhonebookEdit extends AppCompatActivity {
    EditText edit_name, edit_number, edit_email;
    ImageView ImageView_photo;
    String name, number, email;
    Bitmap photo;
    byte[] bytes;
    boolean isEdit = false;
    int position;

    final int MY_PERMISSIONS_REQUEST_ALBUM = 100;
    final int PIXEL_THRESHOLD = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook_edit);
        position = getIntent().getIntExtra("position", -1);
        name = getIntent().getStringExtra("name");
        number = getIntent().getStringExtra("number");
        email = getIntent().getStringExtra("email");
        bytes = getIntent().getByteArrayExtra("photo");
        photo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        // Get the ActionBar here to configure the way it behaves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(0);
        actionBar.setTitle("Edit Contact");

        edit_name = findViewById(R.id.edit_name);
        edit_number = findViewById(R.id.edit_number);
        edit_email = findViewById(R.id.edit_email);
        ImageView_photo = findViewById(R.id.imageView_phonebook);

        edit_name.setText(name);
        edit_number.setText(number);
        edit_email.setText(email);
        ImageView_photo.setImageBitmap(photo);

        ImageView_photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // permission check
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_ALBUM);
                    onClick(v);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                    Toast.makeText(getApplicationContext().getApplicationContext(), "new image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("isEdit", isEdit);
        resultIntent.putExtra("name", edit_name.getText().toString());
        resultIntent.putExtra("number", edit_number.getText().toString());
        resultIntent.putExtra("email", edit_email.getText().toString());
        resultIntent.putExtra("photo",bytes);
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
                if (edit_name.getText().toString().length() == 0 | edit_number.getText().toString().length() == 0) {
                    Toast.makeText(this, "Fill name and number", Toast.LENGTH_LONG).show();
                } else {
                    isEdit = true;
                    onBackPressed();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
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
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // make bitmap image
                    Uri fileUri = data.getData();
                    InputStream in = getApplicationContext().getContentResolver().openInputStream(fileUri);
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    // resizing image if too big
                    if (img.getHeight() > PIXEL_THRESHOLD | img.getWidth() > PIXEL_THRESHOLD) {
                        int dstWidth = img.getWidth();
                        int dstHeight = img.getHeight();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        if (dstHeight > dstWidth) {
                            dstWidth = dstWidth * PIXEL_THRESHOLD / dstHeight;
                            dstHeight = PIXEL_THRESHOLD;
                        } else {
                            dstHeight = dstHeight * PIXEL_THRESHOLD / dstWidth;
                            dstWidth = PIXEL_THRESHOLD;
                        }
                        img = Bitmap.createScaledBitmap(img, dstWidth, dstHeight, true);
                    }

                    // show image
                    ImageView_photo.setImageBitmap(img);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    bytes = stream.toByteArray();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}