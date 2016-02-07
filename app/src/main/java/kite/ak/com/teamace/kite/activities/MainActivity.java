package kite.ak.com.teamace.kite.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kite.ak.com.teamace.kite.R;
import kite.ak.com.teamace.kite.helpers.DocumentHelper;
import kite.ak.com.teamace.kite.helpers.IntentHelper;
import kite.ak.com.teamace.kite.imgurmodel.ImageResponse;
import kite.ak.com.teamace.kite.imgurmodel.Upload;
import kite.ak.com.teamace.kite.services.UploadService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = MainActivity.class.getSimpleName();

    /*
      These annotations are for ButterKnife by Jake Wharton
      https://github.com/JakeWharton/butterknife
     */
    @Bind(R.id.imageview)
    ImageView uploadImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    Uri imgUri;
    private Upload upload; // Upload object containging image and meta data
    private File chosenFile; //chosen file from intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imgUri=Uri.parse(getIntent().getStringExtra("img"));
        setSupportActionBar(toolbar);
        uploadImage.setImageURI(imgUri);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri returnUri;

        if (requestCode != IntentHelper.FILE_PICK) {
            return;
        }

        if (resultCode != RESULT_OK) {
            return;
        }

        returnUri = imgUri;
        String filePath = DocumentHelper.getPath(this, imgUri);
        //Safety check to prevent null pointer exception
        if (filePath == null || filePath.isEmpty()) return;
        chosenFile = new File(filePath);

                /*
                    Picasso is a wonderful image loading tool from square inc.
                    https://github.com/square/picasso
                 */
        Picasso.with(getBaseContext())
                .load(chosenFile)
                .placeholder(R.drawable.ic_photo_library_black)
                .fit()
                .into(uploadImage);

    }


//
//    private void clearInput() {
//        uploadTitle.setText("");
//        uploadDesc.clearFocus();
//        uploadDesc.setText("");
//        uploadTitle.clearFocus();
//        uploadImage.setImageResource(R.drawable.ic_photo_library_black);
//    }

    @OnClick(R.id.fab)
    public void uploadImage() {
    /*
      Create the @Upload object
     */
        String filePath = DocumentHelper.getPath(this, imgUri);
        //Safety check to prevent null pointer exception
        if (filePath == null || filePath.isEmpty()) return;
        chosenFile = new File(filePath);
        createUpload(chosenFile);

    /*
      Start upload
     */
        new UploadService(this).Execute(upload, new UiCallback());
    }

    private void createUpload(File image) {
        upload = new Upload();

        upload.image = image;
        upload.title = "kite_selfie";
        upload.description ="fun is on the way";
    }

    public class UiCallback implements Callback<ImageResponse> {

        @Override
        public void success(ImageResponse imageResponse, Response response) {
            /*getCode*/
        }

        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
            if (error == null) {
                Snackbar.make(findViewById(R.id.rootView), "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
