package pt.edp.trainingday;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

public class ImageryActivity extends AppCompatActivity {

    private ImageButton ib_foto1;
    private ImageButton ib_foto2;
    private TextView tv_lat_foto1;
    private TextView tv_lng_foto1;
    private final static int REQUEST_CODE = 200;
    private boolean foto_1;

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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                foto_1 = true;
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

        ib_foto2 = (ImageButton) findViewById(R.id.imageButton_foto2);
        ib_foto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                foto_1 = false;
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Bundle extras = data.getExtras();
                Bitmap bmp = (Bitmap) extras.get("data");

                if (foto_1) {
                    ib_foto1.setImageBitmap(bmp);
                } else {
                    ib_foto2.setImageBitmap(bmp);
                }
                WriteTag(bmp);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelada", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void WriteTag(Bitmap bmp) {
        /*OutputStream os = new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, os);
        ExifInterface ei = new ExifInterface(bmp);*/
    }
}