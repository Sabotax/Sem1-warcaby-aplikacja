package com.example.warcaby_1semestrprojekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_DANE = "com.example.warcaby_1semestrprojekt.EXTRA_DANE"; //dobra praktyka

    //pamiec
    //private static final Pole[][] KEY_MEMORY_SZACH = null;

    /* List do Sprawdzającego
        Jednak dodałem wszystkie funkcje które Pan zaproponował(staty,save[które były prawie skończone]) i rzeczywiście bardzo dużo mnie to nauczyło ze środowiska aplikacji:
            tworzenie nowych aktywności
            przesylanie miedzy nimi danych
            jak dzialaja onCreate OnResume i przeskakiwanie miedzy aktywnosciami z roznymi flagami (bez zabijania main activity, ale z zabijaniem Statów)
            tworzenie własnego toolbara
            tworzenie menu w toolbarze i spinane go
            nawet trochę o Override przycisków fizycznych itp
        Stan aplikacji mi się nie podoba, wiem że jest tu okropny syf, głównie:
            nie zawsze dobrze przemyślane zmienne statyczne
            ogólna konstrukcja gdzieniegdzie zbyt skryptowa (metoda dotkniecie powinna dzielic sie na inne, lepiej zorganizowane, a nie wszystko w drzewie)
        Zaczynałęm pisać aplikacje nie znając zasad warcab i pod koniec zrozumiałem, że kompletnie źle zrobiłem ruchy, kolejkowanie, teraz bym zrobił osobne funkcje od
        zbieranie list z listami dostępnych ruchów i wymuszania najdłuższego oraz że tylko ten pionek bije, że można wybierać tylko między tymi co biją itd.
        Postanowiłem tego nie zmianiać, tylko skończyć "na skróty"(czyli dodać punkty), ponieważ to tylko by zajęło czas i skomplikowało w stopniu konstrukcyjnym program (jeszcze wiecej ifow i tworzenie list tras itp),
        czyli by to zajęło siły na niepoznawanie nowych technologii, a naprawianie już całkiem okej "gry".
        Więc zamiast tego skupiłem się na dodaniu takich rzeczy jak kolejna aktywność, menu w toolbarze, toolbar, okno dialogowe w nowa gra, albowiem i tak zamierzałem ten projekt potraktować
        jako bazę do przyszłych aplikacji, więc w samym androidzie chciałem się jak najwięcej nauczyć, więc same warcaby nie miały dużego znaczenia czy skaczą tak czy tak.
        ----
        Najwięcej skomplikowania sprawia oczywiście tablica Pole[][], a raczej OnClick na Pole, kiedy musi też sprawdzać czy coś jest zaznaczone, czy jeśli jest jakie są możliwości itp, jest to
        niesamowicie skomplikowane drzewo, a manewrowanie z metodami cofnijRuch() która bardzo dużo dodatkowej implementacji (static zmiennych na kolanie robionych),a wartościami
        które gdzieś są nullami, a gdzie indziej nie wymagała tylko jeszcze bardziej usyfienia kodu.
        Podoba mi się też że tak dobrze połączyłem MainActivity i Context z własnymi klasami i bardzo dobrze nimi manipulowałem oraz OnClick w konstruktorze tworzony.
        Nie podoba mi się ogólny syf projektu, ale przebudowanie tej aplikacji, żeby wyglądała pięknie i dobrze opisana i stworzona to byłby osobny projekt.
        Ponadto gdybym implementował damki, to chciałbym to zrobić tworząc abstrakcyjną klasę Pole i pochodne - Pionek i Damka i stworzyć dla nich środowisko
        w którym doskonale by ze sobą działały, starałbym się porzucić flagi z typami między damką a pionkiem itp, dzięki temu metoda dostępne ruchy mogłąby być ładnie nadpisywana
        Na projekt poświęciłem więcej czasu niż na początku chciałem, ale mimo, tego że na pewno bym się nie chciał nim chwalić pod względem praktyki programistycznej, opisywania kodu jeszcze czytelniej
        itp to działa całkiem nieźle i zostawia dla mnie miejsca na przyszłość, żeby sprawdzić jak coś tam zrobiłem, ponadto jeszcze więcej się nauczyłem o obiektowości i samym działaniu
        Javy gdzieś indziej niż w podręcznikowych czy maturalnych przykładach, więc pod względem ile się nauczyłem jestem zadowolony, teraz tylko starać się na bieżąco robić ładny i czytelny kod
        i ogólne udoskonalenie programowania, żeby robić to zmyślniej. I nadrobić w C++ to samo co umiem, tu, w Javie, to najbardziej.
     */

    public static Activity aktywnosc;

    //Pole ob1;
    //ImageView img,img2;

    public static ImageButton btn_cofnij_dol,btn_cofnij_gora;
    public static TextView tekst_gorny, tekst_dolny;
    //public Activity a = this;
    //Pole tab[][];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aktywnosc = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        if(savedInstanceState != null) {
            Szachownica.setTablica_obiektow(KEY_MEMORY_SZACH);
        }
        else */Szachownica.makeTablicaObiektow(this);



        btn_cofnij_dol = (ImageButton) findViewById(R.id.btn_cofnij_dol);
        btn_cofnij_gora = (ImageButton) findViewById(R.id.btn_cofnij_gora);
        tekst_gorny = (TextView) findViewById(R.id.textView_gorny);
        tekst_dolny = (TextView) findViewById(R.id.textView_dolny);

        Dialog.zmianaRuchu();
        btn_cofnij_dol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pole.cofnijRuch();
            }
        });

        btn_cofnij_gora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pole.cofnijRuch();
            }
        });

        //!!!! TODOIGNORED
        //Save.zapisDoPliku("abctest123",getApplicationContext());

        //tab = new Pole[Szachownica.szerokosc][Szachownica.wysokosc];
/*

        img = (ImageView) findViewById(R.id.pole1);
        tab[0][0] = new Pole(img,R.drawable.pole_brazowe_puste);
        img2 = (ImageView) findViewById(R.id.pole2);
        tab[1][0] = new Pole(img2,R.drawable.pole_biale_puste);
*/

//        img = (ImageView) findViewById(R.id.Pole_R0_C0);

       /* img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageResource(R.drawable.testowe_zdjecie);
            }
        });*/

    }
/*
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putAll(Szachownica.getTablica_obiektow());
        super.onSaveInstanceState(savedInstanceState);
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nowa_gra:
                //Szachownica.makeTablicaObiektow(this);
                Dialog.nowaGraDialog(MainActivity.aktywnosc);
                break;
            case R.id.wczytaj:
                Save.wczytaj(getApplicationContext(),this);
                break;
            case R.id.statystyki:
                openStatystykiActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openStatystykiActivity() {
        Intent intent = new Intent(this, StatystykiActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // nie kiluje "w teorii"
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        int[] t = {Pole.getLiczbaPunktowBialych(),Pole.getLiczbaPunktowCzarnych(),Pole.getLiczbaRuchowBialy(),Pole.getLiczbaRuchowCzarny()};
        intent.putExtra(EXTRA_DANE,t);
        startActivity(intent);
    }



    /*  public static Pole[][] MakeTablicaObiektow(Pole[][] tab) {
        tab = new Pole[Szachownica.szerokosc][Szachownica.wysokosc];

        tab[0][0] = new Pole((ImageView) findViewById )
    }*/

}
