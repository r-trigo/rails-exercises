package pt.edp.trainingday.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import pt.edp.trainingday.VarSessao;

/**
 * Created by e348900 on 14-02-2017.
 */

public class CamaraOuGaleriaDialog extends DialogFragment {

    private CharSequence[] items = new CharSequence[] {"Câmara", "Galeria"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //abrir câmara
                    VarSessao.setX(1);
                } else {
                    //abrir galeria
                    VarSessao.setX(2);
                }
            }
        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                }
//            });
//        }


        return builder.create();
    }

}