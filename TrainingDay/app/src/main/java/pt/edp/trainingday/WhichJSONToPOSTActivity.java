package pt.edp.trainingday;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class WhichJSONToPOSTActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_which_jsonto_post);

        String imjson = getIntent().getStringExtra("imjson");
        String base64 = getIntent().getStringExtra("base64");
        TextView tv_json_to_post = (TextView) findViewById(R.id.textView_json_to_post);
        tv_json_to_post.setText(imjson);

        byte[] byteArray = Base64.decode(base64, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ImageView iv_foto_64 = (ImageView) findViewById(R.id.imageView_foto_64);
        iv_foto_64.setImageBitmap(bmp);
    }
}
