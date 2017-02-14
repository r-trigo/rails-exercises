package pt.edp.trainingday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import pt.edp.trainingday.Dialogs.CheckMateDialog;

public class CheckMateActivity extends AppCompatActivity {

    private TextView tv_escolhas;
    private FrameLayout fl_check_mate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_mate);

        tv_escolhas = (TextView) findViewById(R.id.textView_escolhas);

        fl_check_mate = (FrameLayout) findViewById(R.id.frameLayout_check_mate);
        fl_check_mate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckMateDialog cmd = new CheckMateDialog();
                cmd.show(getSupportFragmentManager(),"cmd");
            }
        });
    }
}
