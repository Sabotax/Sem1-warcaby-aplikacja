package com.example.warcaby_1semestrprojekt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;

public class Dialog {
    private static TextView gorny = (TextView) MainActivity.tekst_gorny;
    private static TextView dolny = (TextView) MainActivity.tekst_dolny;
    private static boolean b = true;

    public static void zmianaRuchu() {
        if (b) {
            dolny.setText(R.string.ruch_bialych);
            gorny.setText(R.string.ruch_bialych);
        }
        else {
            dolny.setText(R.string.ruch_czarnych);
            gorny.setText(R.string.ruch_czarnych);
        }
        b = !b;
    }
    public static void oglosZwyciestwo(int c) {
        //b = true, czarne wygrywają
        if(c == 1) {
            dolny.setText(R.string.wygrana_czarnych);
            gorny.setText(R.string.wygrana_czarnych);
        }
        else if(c == 2) {
            dolny.setText(R.string.wygrana_bialych);
            gorny.setText(R.string.wygrana_bialych);
        }
        else {
            dolny.setText(R.string.wygrana_remis);
            gorny.setText(R.string.wygrana_remis);
        }
    }
    public static void nowaGraDialog(final Activity activity) {
        AlertDialog.Builder okno  = new AlertDialog.Builder(activity);

        okno.setMessage(R.string.nowa_gra_dialog_pytanie).setCancelable(true)
        .setPositiveButton(R.string.nowa_gra_dialog_tak, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Szachownica.makeTablicaObiektow(activity);
            }
        })
        .setNegativeButton(R.string.nowa_gra_dialog_nie, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); //bez tego tez działa?
            }
        });

        AlertDialog alert = okno.create();
        alert.setTitle(R.string.nowa_gra_dialog_naglowek);
        alert.show();
    }
}
