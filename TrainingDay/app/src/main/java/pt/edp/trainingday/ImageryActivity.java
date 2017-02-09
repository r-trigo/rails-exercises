package pt.edp.trainingday;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ImageryActivity extends AppCompatActivity {

    private ImageButton ib_foto1;
    private ImageButton ib_foto2;
    private Button bu_enviar;
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

        bu_enviar = (Button) findViewById(R.id.button_enviar_fotos);
        bu_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelada", Toast.LENGTH_LONG).show();
            }
        }
    }
}