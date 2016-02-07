package kite.ak.com.teamace.kite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kite.ak.com.teamace.kite.activities.MainActivity;

public class CamActivity extends AppCompatActivity {
    static  final  int REQUEST_IMAGE_CAPTURE=1;
    String mCurrentPhotoPath;
    public static Uri IMGURI;
    ImageView thumbnailview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
        findViewById(R.id.determinate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startCamIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (startCamIntent.resolveActivity(getPackageManager()) != null) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "Kite_selfie" + new Date().getTime() + ".png");
                    Uri imgUri = Uri.fromFile(file);
                    IMGURI=imgUri;
                    if(file!=null) {
                        startCamIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                        startActivityForResult(startCamIntent, REQUEST_IMAGE_CAPTURE);
                    }


                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cam, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Kite_" + timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//
//            thumbnailview.setImageBitmap(imageBitmap);
            Intent test= new Intent(CamActivity.this,MainActivity.class);
            test.putExtra("img",IMGURI.toString());
            startActivity(test);
        }
    }
}
