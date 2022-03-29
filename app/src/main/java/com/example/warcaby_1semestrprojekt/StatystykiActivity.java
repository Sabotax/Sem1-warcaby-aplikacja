package com.example.warcaby_1semestrprojekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StatystykiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statystyki);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        //WAŻNE !! Override strzałki wracania, dzięki temu można ustawić flagi dzięki czemu nie wywołuje oncreata, a onresume w main activity! dzięki czemu nie tworzy od nowa itp
        //
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                Intent openMainActivity = new Intent(StatystykiActivity.this, MainActivity.class);
                openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openMainActivity, 0);
                finish();//specjalnie killuje żeby wywoływało oncreate za każdym razem => updatowało wszystko
            }
        });

        Intent intent = getIntent();
        int[] dane = intent.getIntArrayExtra(MainActivity.EXTRA_DANE);

        TextView ilosc_ruchow_bialego = (TextView) findViewById(R.id.staty_ilosc_ruchow_bialy_show);
        TextView ilosc_ruchow_czarnego = (TextView) findViewById(R.id.staty_ilosc_ruchow_czarny_show);
        TextView ilosc_punktow_bialego = (TextView) findViewById(R.id.staty_ilosc_punktow_bialy_show);
        TextView ilosc_punktow_czarnego = (TextView) findViewById(R.id.staty_ilosc_punktow_czarny_show);

        ilosc_ruchow_bialego.setText(String.valueOf(dane[2]));
        ilosc_punktow_bialego.setText(String.valueOf(dane[0]));
        ilosc_ruchow_czarnego.setText(String.valueOf(dane[3]));
        ilosc_punktow_czarnego.setText(String.valueOf(dane[1]));
    }

}
