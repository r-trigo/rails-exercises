package pt.edp.trainingday;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

/**
 * Created by e348900 on 09-02-2017.
 */

public class Helper {

    public void ResetCheckBoxes(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("checkMateCheckedItems", 0);
        boolean[] checkedItems = new boolean[context.getResources().getStringArray(R.array.pecas).length];
        SharedPreferences.Editor editor = sharedpreferences.edit();

        for (int i = 0; i < checkedItems.length; i++) {
            editor.putBoolean("i=" + i, false);
        }

        editor.commit();
    }
}
