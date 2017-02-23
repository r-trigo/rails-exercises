package pt.edp.trainingday;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import pt.edp.trainingday.Dialogs.CamaraOuGaleriaDialog;
import pt.edp.trainingday.Dialogs.GeoTagDisDialog;

public class ImageryActivity extends AppCompatActivity {

    private ImageButton ib_foto1, ib_foto2;
    private TextView tv_lat_foto1, tv_lng_foto1;
    private boolean foto_1;
    private double latitude, longitude;
    private Button bu_enviar_fotos;
    private Bitmap foto1, foto2;
    private String nome, data_tirada, f1, mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int IMAGE_GALLERY_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagery);

        //tem câmara?
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            ib_foto1.setEnabled(false);
            ib_foto2.setEnabled(false);
            Toast.makeText(this, "O dispositivo não possui câmara", Toast.LENGTH_LONG).show();
        }

        tv_lat_foto1 = (TextView) findViewById(R.id.textView_latitude_foto1);
        tv_lng_foto1 = (TextView) findViewById(R.id.textView_longitude_foto1);

        ib_foto1 = (ImageButton) findViewById(R.id.imageButton_foto1);
        ib_foto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto_1 = true;
                CamaraOuGaleriaDialog cogd = new CamaraOuGaleriaDialog();
                cogd.show(getSupportFragmentManager(), "cogd");
            }
        });


        if (VarSessao.getX()== 1) {
            dispatchTakePictureIntent();
        } else if (VarSessao.getX() == 2){
            selectImage();
        }


        ib_foto2 = (ImageButton) findViewById(R.id.imageButton_foto2);
        ib_foto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto_1 = false;
                dispatchTakePictureIntent();

            }
        });

        bu_enviar_fotos = (Button) findViewById(R.id.button_enviar_fotos);
        bu_enviar_fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPhotoPath != null) {
                    Intent intent = new Intent(ImageryActivity.this, WhichJSONToPOSTActivity.class);
                    Bundle args = new Bundle();
                    args.putString("nome", nome);
                    args.putDouble("latitude", latitude);
                    args.putDouble("longitude", longitude);
                    args.putString("data_tirada", data_tirada);
                    args.putString("img_link", mCurrentPhotoPath);
                    intent.putExtra("args", args);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //tirar foto
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (foto_1) {
                    setPic(ib_foto1);
                } else {
                    setPic(ib_foto2);
                }
                GetPhotoAttributes();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelada", Toast.LENGTH_LONG).show();
            }
        //galeria
        } else if (requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            InputStream inputStream;

            try {
                inputStream = getContentResolver().openInputStream(imageUri);
                //Bitmap image = BitmapFactory.decodeStream(inputStream);
                mCurrentPhotoPath = String.valueOf(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Não foi possível abrir a imagem.", Toast.LENGTH_LONG);
            }

        }
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
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
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //// TODO: 17-02-2017 guardar na galeria?

    private void setPic(ImageButton ib) {
        // Get the dimensions of the View
        int targetW = ib.getWidth();
        int targetH = ib.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ib.setImageBitmap(bitmap);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        intent.setDataAndType(data, "image/*");
        startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
    }
}