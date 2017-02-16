package pt.edp.trainingday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button bu_imagery;
    private Button bu_check_mate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bu_imagery = (Button) findViewById(R.id.button_imagery);
        bu_imagery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageryActivity.class);
                startActivity(intent);
            }
        });

        bu_check_mate = (Button) findViewById(R.id.button_check_mate);
        bu_check_mate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper myHelper = new Helper();
                myHelper.ResetCheckBoxes(getApplicationContext());
                Intent intent = new Intent(MainActivity.this, CheckMateActivity.class);
                startActivity(intent);
            }
        });

    }

}
