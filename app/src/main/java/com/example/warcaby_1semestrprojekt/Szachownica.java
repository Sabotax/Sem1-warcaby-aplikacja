package com.example.warcaby_1semestrprojekt;

import android.app.Activity;
import android.widget.ImageView;

import static com.example.warcaby_1semestrprojekt.Pole.TypyPol.*;

/*
TODODONE
tekstury dla podświetleńDONE i zedytować Pole.dotyk() żeby podświetlało odpowiednio + co za tym idzie jak będzie wykrywać co podświetlać żeby nie wychodziło za ramę
+ jak później ma wykywać sąsiadów i ich typy itp. to będzie trzeba jakoś: Pole getObiektZtegoPolakoordynaty()DONE -> koniecznosc wprowadzenia koordow x,y do obiektu?DONE
, ale mozna to przy tworzeniu jak tablica sie robiDONE
 */
public class Szachownica {
    public static  int szerokosc = 8;
    public static int wysokosc = 8;
    private static int[][] default_szachownica = {
            {-1, -2, -1, -2, -1, -2, -1, -2},
            {-2, -1, -2, -1, -2, -1, -2, -1},
            {-1, -2, -1, -2, -1, -2, -1, -2},
            {1, -1, 1, -1, 1, -1, 1, -1},
            {-1, 1, -1, 1, -1, 1, -1, 1},
            {2, -1, 2, -1, 2, -1, 2, -1},
            {-1, 2, -1, 2, -1, 2, -1, 2},
            {2, -1, 2, -1, 2, -1, 2, -1}
    };
    private static Pole[][] tablica_obiektow;
    private static int liczba_bialych_figur=-1;
    private static int liczba_bialych_punktow=-1;
    private static int liczba_czarnych_figur=-1;
    private static int liczba_czarnych_punktow=-1;
    private static int liczba_czarnych_ruchow=-1;
    private static int liczba_bialych_ruchow=-1;
    // brązowe_puste -> 1 , brązowe_pionek -> 2, brązowe_damka -> 3
    // białe_puste -> -1 , białe_pionek -> -2, białe_damka -> -3

    public static void makeTablicaObiektow(Activity activity) {
        Pole[][] tab = new Pole[szerokosc][wysokosc];

        // przez to że dalej szachownica jest interpretowana jak oś X i Y, a nie jak tablica rysowana od góry
        for(int i = 0; i < szerokosc; i++) {
            for(int j = 0 ; j < wysokosc; j++) {
                int resID = activity.getResources().getIdentifier("pole_R"+(szerokosc-1-i)+"_C"+j, "id", activity.getPackageName());
                //resID = pole_R_0_C_4
                tab[i][j] = new Pole( (ImageView) activity.findViewById(resID),resID, ZinterpretujLiczbe(default_szachownica[i][j]),j,wysokosc-1-i);
                liczba_bialych_figur = 12;
                liczba_bialych_punktow = 12;
                liczba_czarnych_figur = 12;
                liczba_czarnych_punktow = 12;
                liczba_czarnych_ruchow=0;
                liczba_bialych_ruchow=0;


            }
        }

        tablica_obiektow = tab;
        updateLiczby();
        Pole.zerujStale();
    }

    //TODODONE przechowywanie ilosci punktow tez musi byc
    public static void makeTablicaObiektow(Activity activity, int[][] tab_input) {
        Pole[][] tab = new Pole[szerokosc][wysokosc];
        liczba_bialych_figur = 0;
        liczba_czarnych_figur = 0;
        for(int i = 0; i < szerokosc; i++) {
            for(int j = 0 ; j < wysokosc; j++) {
                int resID = activity.getResources().getIdentifier("pole_R"+(szerokosc-1-i)+"_C"+j, "id", activity.getPackageName());
                tab[i][j] = new Pole( (ImageView) activity.findViewById(resID),resID, ZinterpretujLiczbe(tab_input[i][j]),j,wysokosc-1-i);
                if(tab_input[i][j] == -3 || tab_input[i][j] == -2) liczba_bialych_figur++;
                else if(tab_input[i][j] == 3 || tab_input[i][j] == 2) liczba_czarnych_figur++;
            }
        }

        tablica_obiektow = tab;
        updateLiczby();
        Pole.zerujStale();
    }


    private static Pole.TypyPol ZinterpretujLiczbe(int a) {
        switch (a) {
            case -3:
                return pole_brazowe_damka_bialy;
            case -2:
                return pole_brazowe_pionek_bialy;
            case -1:
                return  pole_biale_puste;
            case 1:
                return  pole_brazowe_puste;
            case 2:
                return pole_brazowe_pionek_czarny;
            case 3:
                return pole_brazowe_damka_czarny;
            default:
                return null;
        }
    }

    public static int[][] tablicaObiektowNaTabliceLiczb(Pole[][] tab) {
        int[][] re = new int[tab.length][tab[0].length];

        for(int i = 0 ; i < tab.length ; i++ ) {
            for(int j = 0 ; j < tab[i].length ; j++ ) {
                re[i][j] = typNaLiczbe(tab[i][j].getTyp());
            }
        }

        return re;
    }

    private static int typNaLiczbe(Pole.TypyPol typ) {
        switch (typ) {
            case pole_brazowe_damka_bialy:
                return -3;
            case pole_brazowe_pionek_bialy:
                return -2;
            case pole_biale_puste:
                return  -1;
            case pole_brazowe_puste:
                return  1;
            case pole_brazowe_pionek_czarny:
                return 2;
            case pole_brazowe_damka_czarny:
                return 3;
            default:
                return 0;
        }
    }

    public static Pole[][] getTablica_obiektow() {
        return tablica_obiektow;
    }

    public static void setTablica_obiektow(Pole[][] tablica_obiektow) {
        Szachownica.tablica_obiektow = tablica_obiektow;
    }

    public static int getLiczba_bialych_figur() {
        return liczba_bialych_figur;
    }

    public static int getLiczba_czarnych_figur() {
        return liczba_czarnych_figur;
    }

    public static void updateLiczby() {
        Pole.setLiczbaFigurBialych(liczba_bialych_figur);
        Pole.setLiczbaFigurCzarnych(liczba_czarnych_figur);
        Pole.setLiczbaPunktowBialych(liczba_bialych_punktow);
        Pole.setLiczbaPunktowCzarnych(liczba_czarnych_punktow);
        Pole.setLiczbaRuchowBialy(liczba_bialych_ruchow);
        Pole.setLiczbaRuchowCzarny(liczba_czarnych_ruchow);
    }
}
