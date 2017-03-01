package pt.edp.trainingday;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pt.edp.trainingday.Dialogs.GeoTagDisDialog;

public class ImageryActivity extends AppCompatActivity {

    private ImageButton ib_foto1, ib_foto2;
    private TextView tv_lat_foto1, tv_lng_foto1;
    private Button bu_enviar_fotos;
    private boolean foto_1;
    private String mCurrentPhotoPath, data_tirada;
    private static final int ACTION_TAKE_PHOTO = 1;
    private static final int ACTION_PICK_PHOTO = 2;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagery2);

        tv_lat_foto1 = (TextView) findViewById(R.id.textView_latitude_foto1);
        tv_lng_foto1 = (TextView) findViewById(R.id.textView_longitude_foto1);

        ib_foto1 = (ImageButton) findViewById(R.id.imageButton_foto1);
        ib_foto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto_1 = true;
                chooseSource();
            }
        });

        ib_foto2 = (ImageButton) findViewById(R.id.imageButton_foto1);
        ib_foto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto_1 = false;
                chooseSource();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == ACTION_PICK_PHOTO) {
                Uri myUri = data.getData();
                mCurrentPhotoPath = getRealPathFromURI(myUri);
            }

            if (mCurrentPhotoPath != null) {

                if (foto_1) {
                    setPic(ib_foto1);
                } else {
                    setPic(ib_foto2);
                }

                GetPhotoAttributes();

                //add to gallery
                Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                File f = new File(mCurrentPhotoPath);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                mCurrentPhotoPath = null;

                //BundleUpAndSend
            }
        }
    }

    private void chooseSource() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] items = new CharSequence[]{"Câmara", "Galeria"};
        builder.setTitle("Escolha ou tire uma foto").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    dispatchTakePictureIntent();
                } else {
                    pickPhotoIntent();
                }
            }
        });

        builder.create().show();
    }

    private void pickPhotoIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, ACTION_PICK_PHOTO);
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                //...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "pt.edp.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, ACTION_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic(ImageButton imageButton) {

		/* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = imageButton.getWidth();
        int targetH = imageButton.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
        imageButton.setImageBitmap(bitmap);
        imageButton.setVisibility(View.VISIBLE);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void GetPhotoAttributes() {
        String[] columns = {MediaStore.Images.ImageColumns.LATITUDE,
                MediaStore.Images.ImageColumns.LONGITUDE,
                MediaStore.Images.ImageColumns.TITLE,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_TAKEN
        };

        final String orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        cursor.moveToPosition(0);

        Helper myHelper = new Helper();
        data_tirada = myHelper.MillisToDate(Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN))));
        latitude = cursor.getDouble(cursor.getColumnIndex(MediaStore.Images.ImageColumns.LATITUDE));
        longitude = cursor.getDouble(cursor.getColumnIndex(MediaStore.Images.ImageColumns.LONGITUDE));

        if (latitude != 0.0 && longitude != 0.0) {
            tv_lat_foto1.setText(String.valueOf(latitude));
            tv_lng_foto1.setText(String.valueOf(longitude));
        } else {
            GeoTagDisDialog gtdd = new GeoTagDisDialog();
            gtdd.show(getSupportFragmentManager(), "gtdd");
        }
    }

}