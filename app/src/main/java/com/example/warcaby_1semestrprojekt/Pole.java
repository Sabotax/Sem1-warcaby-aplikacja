package com.example.warcaby_1semestrprojekt;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class Pole {
    private ImageView img;
    private int drawableID; //może być:
    public enum TypyPol {
        pole_brazowe_puste,
        pole_biale_puste,
        pole_brazowe_pionek_czarny,
        //pole_biale_pionek_czarny,
        pole_brazowe_damka_czarny,
        //pole_biale_damka_czarny;
        pole_brazowe_pionek_bialy,
        pole_brazowe_damka_bialy
    }
    private boolean podswietlenie;
    private TypyPol typ;
    private int koordX;
    private int koordY;
    private boolean zaznaczenie;
    private static boolean czyCosJestZaznaczone;
    private static Pole[] tab_dostepne_ruchy;
    private static Pole[] tab_zbijane;
    private static Pole poprzedni;
    private static Pole poprzedni2;
    private static Pole poprzedni_zbite;
    private static Pole poprzedni_zbite_wyniesienie;
    private static Pole zaznaczonePole = null;
    private static boolean kolejkaCzarnych = false;
    //TODODONE żeby odczytywało ile jest figur tych i tych z czasem jak zrobię sejwy żeby odczytywało(liczyło) z tablicy
    private static int liczbaFigurCzarnych;
    private static int liczbaFigurBialych;
    private static int liczbaPunktowCzarnych;
    private static int liczbaPunktowBialych;
    private static boolean czyMoznaCofnac = false;
    private static int liczbaRuchowBialy;
    private static int liczbaRuchowCzarny;

    public static int getLiczbaRuchowBialy() {
        return liczbaRuchowBialy;
    }

    public static void setLiczbaRuchowBialy(int liczbaRuchowBialy) {
        Pole.liczbaRuchowBialy = liczbaRuchowBialy;
    }

    public static void setLiczbaRuchowCzarny(int liczbaRuchowCzarny) {
        Pole.liczbaRuchowCzarny = liczbaRuchowCzarny;
    }

    public static int getLiczbaRuchowCzarny() {
        return liczbaRuchowCzarny;
    }

    public static void setKolejkaCzarnych(boolean kolejkaCzarnych) {
        Pole.kolejkaCzarnych = kolejkaCzarnych;
    }

    public static boolean getCzyCosJestZaznaczone() {
        return czyCosJestZaznaczone;
    }

    public static boolean getKolejkaCzarnych() {
        return kolejkaCzarnych;
    }

    public static int getLiczbaPunktowCzarnych() {
        return liczbaPunktowCzarnych;
    }

    public static int getLiczbaPunktowBialych() {
        return liczbaPunktowBialych;
    }

    public static void zerujStale() {
        czyCosJestZaznaczone = false;
        poprzedni_zbite = null;
        poprzedni_zbite_wyniesienie = null;
        poprzedni = null;
        poprzedni2 = null;
        kolejkaCzarnych = false;
        czyMoznaCofnac =false;
        tab_dostepne_ruchy = null;
        tab_zbijane = null;
    }

/*

    public void changeImage(int ID) {
        this.drawableID = ID;
        this.img.setImageResource(ID);
    }
*/

    public void changeImage() {
        drawableID = Typ_z_DrawableID();
        img.setImageResource(drawableID);
    }

    Pole(ImageView img, int drawableID, TypyPol typ,int koordX, int koordY) {
        this.img = img;
        this.drawableID = drawableID;
        this.typ = typ;
        this.koordX = koordX;
        this.koordY = koordY;
        podswietlenie = false;
        zaznaczenie = false;
        changeImage();
        makeListener();
    }
    //copy constructor
    Pole(Pole x) {
        this.img = x.img;
        this.drawableID = x.drawableID;
        this.typ = x.typ;
        this.koordX = x.koordX;
        this.koordY = x.koordY;
        podswietlenie = false;
        zaznaczenie = false;
    }
    //TODODONE fix bug -> zaznacz bialy i zaznacz od razu inny biały, to poprzedni przejmuje tablicę ruchów nowego (odpowiednio naprawić ify)
    //wciąż + cofniecię ruchu musi wyłączyć zaznaczenia + cofanie psuje gre, pionki nie mogą się ruszać na pola-> naprawić

    @Override
    public String toString() {
        return "Obiekt: X: " + getKoordX() + " Y: " + getKoordY() + " Typ: " + typ;
    }
    //w zaleznosci od typu ma decydowac co robi
    private void Dotkniecie() {
        Log.d("Przerwa","--------------------------------");
        Log.d("OnClick co to",this.toString());
        //if(zaznaczonePole != null) Log.d("ZaznaczonePole: ", zaznaczonePole.toString());
        //else Log.d("ZaznaczonePole: ", "brak");
        //Log.d("Koordynaty","X: " + koordX + "\tY: " + koordY);
        //Log.d("Liczba figur:","\nBiałe " + liczbaFigurBialych);
        //Log.d("Liczba figur:","Czarne " + liczbaFigurCzarnych);
        //Log.d("Liczba punktow:","\nBiałe " + liczbaPunktowBialych);
        //Log.d("Liczba punktow:","Czarne " + liczbaPunktowCzarnych);
        boolean temp = false;
        if(kolejkaCzarnych) {
            if(czyToJestFigura()) {

                if (typ == TypyPol.pole_brazowe_damka_czarny || typ == TypyPol.pole_brazowe_pionek_czarny) {
                    if(!czyCosJestZaznaczone) {

                        tab_dostepne_ruchy = DostepneRuchy()[0];
                        tab_zbijane = DostepneRuchy()[1];
                        for (Pole x : tab_dostepne_ruchy) {
                            x.zmienPodswietlenie();
                        }
                        zmienZaznaczenie();
                    }
                    else if(czyCosJestZaznaczone && this != zaznaczonePole) {
                        //nic;
                    }
                    else {
                        tab_dostepne_ruchy = DostepneRuchy()[0];
                        tab_zbijane = DostepneRuchy()[1];

                        for (Pole x : tab_dostepne_ruchy) {
                            x.zmienPodswietlenie();
                        }
                        zmienZaznaczenie();
                    }

                }

            }
            else if(czyCosJestZaznaczone){
                for(int i = 0; i < tab_dostepne_ruchy.length ; i++) { // jeżeli nalezy do dostepnych ruchów
                    //Log.d("Spr obiektow","Obiekt this: " + this + "\tObiekt x: " + x);
                    //Log.d("Wielkosc tablic 1" , tab_dostepne_ruchy.length + " " + tab_zbijane.length);

                    if(this == tab_dostepne_ruchy[i]) {
                        //Log.d("Test ifa","Wchodze");
                        //ruch();
                        //zmienRuch(false);
                        poprzedni_zbite = null;
                        if( tab_zbijane[i] != null ) {
                            tab_zbijane[i].zbicie();
                        }
                        else {
                            zmienRuch(false);
                        }
                        //TODOIGNORED damka, a teraz po prostu usuwa figurę
                        if(getKoordY() == 7) temp = true;
                        //koniec damkowania
                        ruch();
                        break;
                    }
                }
                zmienZaznaczenie();
                if(temp) zbicieAlt();
                //tab_zbijane = null;
                //tab_dostepne_ruchy = null;
            }
            //zmienZaznaczenie();
        }
        else{
            if(czyToJestFigura()) {

                if (typ == TypyPol.pole_brazowe_damka_bialy || typ == TypyPol.pole_brazowe_pionek_bialy) {
                    if(!czyCosJestZaznaczone) {

                        tab_dostepne_ruchy = DostepneRuchy()[0];
                        tab_zbijane = DostepneRuchy()[1];
                        for (Pole x : tab_dostepne_ruchy) {
                            x.zmienPodswietlenie();
                        }
                        zmienZaznaczenie();
                    }
                    else if(czyCosJestZaznaczone && this != zaznaczonePole) {
                        //nic
                    }
                    else {
                        tab_dostepne_ruchy = DostepneRuchy()[0];
                        tab_zbijane = DostepneRuchy()[1];
                        for (Pole x : tab_dostepne_ruchy) {
                            x.zmienPodswietlenie();
                        }
                        zmienZaznaczenie();
                    }

                }

            }
            else if(czyCosJestZaznaczone){
                for(int i = 0; i < tab_dostepne_ruchy.length ; i++) { // jeżeli nalezy do dostepnych ruchów
                    //Log.d("Spr obiektow","Obiekt this: " + this + "\tObiekt x: " + x);
                    //Log.d("Wielkosc tablic 1" , tab_dostepne_ruchy.length + " " + tab_zbijane.length);
                    if(this == tab_dostepne_ruchy[i]) {
                        //Log.d("Test ifa","Wchodze");
                        //ruch();
                        //zmienRuch(true);
                        //debug
                        /*for(int j = 0; j < tab_zbijane.length ; j++) {
                            if(tab_zbijane[j] == null) Log.d("Testy nullowosci", j + " to null");
                            else Log.d("Testy nullowosci", j + " to obiekt");
                        }*/
                        poprzedni_zbite = null;
                        if( tab_zbijane[i] != null ) {
                            tab_zbijane[i].zbicie();
                        }
                        else {
                            zmienRuch(true);
                        }
                        ruch();
                        if(getKoordY() == 0) temp = true;
                        break;
                    }
                }
                zmienZaznaczenie();
                //temporary w celu sprawdzenia czy oglos zwyciestwo dziala
                if(temp) zbicieAlt();
                //tab_zbijane = null;
                //tab_dostepne_ruchy = null;
            }
            //zmienZaznaczenie();
        }



    }
    //TODODONE kolejność tur ogarnąć i zobaczyć co zrobiłem że zaczął działać ruch

    private static void zmienRuch(boolean b) {
        kolejkaCzarnych = b;
        Dialog.zmianaRuchu();
    }

    //TODODONE? licznik figur też musi się cofać i punktów
    public static void cofnijRuch() {
        if(czyMoznaCofnac) {
            if(czyCosJestZaznaczone) {
                zaznaczonePole.zmienZaznaczenie();
                for(Pole x: tab_dostepne_ruchy) x.zmienPodswietlenie();
            }

            //Log.d("cofanie ruchu 1", "Typ: " + poprzedni.typ.name() + " X: " + poprzedni.getKoordX() + " Y: " + poprzedni.getKoordY());
            //Pole x = getObject(poprzedni.getKoordX(),poprzedni.getKoordY());
            //Log.d("cofanie ruchu 2", "Typ: " + x.typ.name() + " X: " + x.getKoordX() + " Y: " + x.getKoordY());
            TypyPol typ_a = poprzedni.typ;
            TypyPol typ_c = poprzedni2.typ;
            boolean b = false;
            if(poprzedni_zbite != null) {
                TypyPol typ_b = poprzedni_zbite.typ;
                getObject(poprzedni_zbite.getKoordX(),poprzedni_zbite.getKoordY()).setTyp(typ_b);
                if(poprzedni_zbite.czyCzarny()) {
                    liczbaPunktowBialych--;
                    liczbaFigurCzarnych++;
                }
                if(!poprzedni_zbite.czyCzarny()) {
                    liczbaPunktowCzarnych--;
                    liczbaFigurBialych++;
                }
                b = true;
            }
            //;if(poprzedni_zbite == null) Log.d("Test poprzedni zbite","Jestem null");
            if(poprzedni_zbite_wyniesienie != null) {
                TypyPol typ_d = poprzedni_zbite_wyniesienie.typ;
                getObject(poprzedni_zbite_wyniesienie.getKoordX(),poprzedni_zbite_wyniesienie.getKoordY()).setTyp(typ_d);
                if(poprzedni_zbite_wyniesienie.czyCzarny()) {
                    liczbaPunktowCzarnych--;
                    liczbaFigurCzarnych++;
                }
                if(!poprzedni_zbite_wyniesienie.czyCzarny()) {
                    liczbaPunktowBialych--;
                    liczbaFigurBialych++;
                }

                b = true;
            }
            //TypyPol pom = typ_a;
            getObject(poprzedni.getKoordX(),poprzedni.getKoordY()).setTyp(typ_a);
            getObject(poprzedni2.getKoordX(),poprzedni2.getKoordY()).setTyp(typ_c);
            // TODODONE jest bug że czasami chyba zostaje obiekt w poprzedni2 i cofanie niechcąco wskrzesza pionek niezwiązany z cofnięciem

            czyMoznaCofnac = false;
            if(!b) zmienRuch(!kolejkaCzarnych);

            if(poprzedni.czyCzarny()) liczbaRuchowCzarny--;
            else liczbaRuchowBialy--;
        }
    }

    private void ruch() {

        poprzedni = new Pole(this);
        poprzedni2 = new Pole(zaznaczonePole);

        for(Pole x: tab_dostepne_ruchy) x.zmienPodswietlenie();
        zaznaczonePole.zmienPodswietlenie();
        TypyPol pom = typ;
        setTyp(zaznaczonePole.typ);
        zaznaczonePole.setTyp(pom);

        zaznaczonePole.zmienZaznaczenie();
        czyMoznaCofnac = true;

        if(czyCzarny()) liczbaRuchowCzarny++;
        else liczbaRuchowBialy++;

        Save.zapisz(MainActivity.aktywnosc);

    }

    public void zmienZaznaczenie() {
        if(czyToJestFigura()) {
            if(czyCosJestZaznaczone && this == zaznaczonePole) { // trafiles na zaznaczone pole
                //Log.d("Zaznaczenie", "Wyłączam zaznaczenie");
                zmienPodswietlenie();
                zaznaczenie = false;
                zaznaczonePole = null;
                czyCosJestZaznaczone = false;
            } else if(czyCosJestZaznaczone && this != zaznaczonePole) {//cos innego jest zaznaczone
                //Log.d("Zaznaczenie", "czyCosJestZaznaczone: " + czyCosJestZaznaczone );
                for(Pole x : tab_dostepne_ruchy) if( this == x ) {
                    zaznaczonePole = null;
                    czyCosJestZaznaczone = false;
                }
            } else if(!czyCosJestZaznaczone && this != zaznaczonePole) {//nic nie ma zaznaczonego i chcesz to zaznaczyc
                //Log.d("Zaznaczenie", "Zaznaczam to");
                zmienPodswietlenie();
                zaznaczenie = true;
                czyCosJestZaznaczone = true;
                zaznaczonePole = this;
            }
        }
    }


    private boolean czyCzarny() {
        switch (typ) {
            case pole_brazowe_pionek_czarny:
                return true;
            case pole_brazowe_damka_czarny:
                return true;
            default:
                return false;
        }
    }

    //TODODONE sprawdza lewą górę czy wychodzi poza ramy, jak nie to sprawdza czy to puste pole, czy pionek, jak puste, to moze tam pojsc(to pozniej) jak pionek
    // to sprawdza czy cos jest za nim( czy moze go zbic) i jesli moze to udostepnia mu ten ruch (?moze zmienic typ zwracany na tablice kordów jakie/gdzie może
    // wykonać ruchy

    private Pole[][] DostepneRuchy() {
        // moze isc po przekatnej do przodu (osobne muszą być dla białego i brązowego, bo to różne "przody"), moze bić w każdą przekątną, musi bić jeśli może
        ArrayList<Pole> dostepne_pola = new ArrayList<Pole>();
        ArrayList<Pole> zbijane = new ArrayList<Pole>();
        boolean czyJestCosDoZbicia = false;
        if (czyCzarny() && czyToJestFigura()) { //przod jest inny dla kazdego
            //lewa góra puste pole
            if (koordX != 0 && koordY != 7) { //ramka
                Pole a = getObject(koordX - 1, koordY + 1);
                Log.d("test brązowe lewa góra","X: " + (koordX - 1) + "Y: " +(koordX + 1));
                if (!a.czyToJestFigura()) {
                    dostepne_pola.add(a);
                    zbijane.add(null);
                }
            }
            //lewa górna pionek
            if( koordX != 1  && koordX != 0 && koordY != 7 && koordY != 6) { //ramka
                Pole a = getObject(koordX - 1, koordY + 1);
                Pole b = getObject(koordX - 2, koordY + 2);
                if (a.czyToJestFigura() && !a.czyCzarny() && !b.czyToJestFigura()) {
                    dostepne_pola.add(b);
                    zbijane.add(a);
                    czyJestCosDoZbicia = true;
                }
            }
            //else nic
            //prawa góra puste pole
            if (koordX != 7 && koordY != 7) { //ramka
                Pole a = getObject(koordX + 1, koordY + 1);
                if (!a.czyToJestFigura()) {
                    dostepne_pola.add(a);
                    zbijane.add(null);
                }
            }
            //prawa górna pionek
            if( koordX != 6 && koordX != 7 && koordY != 7 && koordY != 6) { //ramka
                Pole a = getObject(koordX + 1, koordY + 1);
                Pole b = getObject(koordX + 2, koordY + 2);
                if (a.czyToJestFigura() && !a.czyCzarny() && !b.czyToJestFigura()) {
                    dostepne_pola.add(b);
                    zbijane.add(a);
                    czyJestCosDoZbicia = true;
                }
            }
            // do tyłu lewo dół musi być pionek
            if(koordX != 0 && koordX != 1 && koordY != 0 && koordY != 1) {
                Pole a = getObject(koordX - 1, koordY - 1);
                Pole b = getObject(koordX - 2, koordY - 2);
                if(a.czyToJestFigura() && !a.czyCzarny() && !b.czyToJestFigura() )  {
                    dostepne_pola.add(b);
                    zbijane.add(a);
                    czyJestCosDoZbicia = true;
                }
            }
            // do tyłu prawo dół musi być pionek
            if(koordX != 7 && koordX != 6 && koordY != 0 && koordY != 1) {
                Pole a = getObject(koordX + 1, koordY - 1);
                Pole b = getObject(koordX + 2, koordY - 2);
                if(a.czyToJestFigura() && !a.czyCzarny() && !b.czyToJestFigura() )  {
                    dostepne_pola.add(b);
                    zbijane.add(a);
                    czyJestCosDoZbicia = true;
                }
            }
        }
        else if(!czyCzarny() && czyToJestFigura()) {
            //lewa dół puste pole
            if (koordX != 0 && koordY != 0) { //ramka
                Pole a = getObject(koordX - 1, koordY - 1);
                //Log.d("test brązowe lewa góra","X: " + (koordX - 1) + "Y: " +(koordX + 1));
                if (!a.czyToJestFigura()) {
                    dostepne_pola.add(a);
                    zbijane.add(null);
                }
            }
            //lewa dół pionek
            if( koordX != 1  && koordX != 0 && koordY != 0 && koordY != 1) { //ramka
                Pole a = getObject(koordX - 1, koordY - 1);
                Pole b = getObject(koordX - 2, koordY - 2);
                if (a.czyToJestFigura() && a.czyCzarny() && !b.czyToJestFigura()) {
                    dostepne_pola.add(b);
                    zbijane.add(a);
                    czyJestCosDoZbicia = true;
                }
            }
            //else nic
            //prawa dół puste pole
            if (koordX != 7 && koordY != 0) { //ramka
                Pole a = getObject(koordX + 1, koordY - 1);
                if (!a.czyToJestFigura()) {
                    dostepne_pola.add(a);
                    zbijane.add(null);
                }
            }
            //prawa dół pionek
            if( koordX != 6 && koordX != 7 && koordY != 0 && koordY != 1) { //ramka
                Pole a = getObject(koordX + 1, koordY - 1);
                Pole b = getObject(koordX + 2, koordY - 2);
                if (a.czyToJestFigura() && a.czyCzarny() && !b.czyToJestFigura()) {
                    dostepne_pola.add(b);
                    zbijane.add(a);
                    czyJestCosDoZbicia = true;
                }
                //Log.d("Bicie białe prawy dół", "Wykrywam figurę na X: " + b.getKoordX() + "Y: " + b.getKoordY() );
            }
            //do tyłu lewa góra musi być pionek
            if(koordX != 0 && koordX != 1 && koordY != 7 && koordY != 6) {
                Pole a = getObject(koordX - 1, koordY + 1);
                Pole b = getObject(koordX - 2, koordY + 2);
                if(a.czyToJestFigura() && a.czyCzarny() && !b.czyToJestFigura() )  {
                    dostepne_pola.add(b);
                    zbijane.add(a);
                    czyJestCosDoZbicia = true;
                }
            }
            // do tyłu prawo góra musi być pionek
            if(koordX != 7 && koordX != 6 && koordY != 7 && koordY != 6) {
                Pole a = getObject(koordX + 1, koordY + 1);
                Pole b = getObject(koordX + 2, koordY + 2);
                if(a.czyToJestFigura() && a.czyCzarny() && !b.czyToJestFigura() )  {
                    dostepne_pola.add(b);
                    zbijane.add(a);
                    czyJestCosDoZbicia = true;
                }
            }

        }
        /*for(int i = 0 ; i < zbijane.size() ; i++ ) {
            if(zbijane.get(i) == null ) Log.d("Test ruchu" , i + " to null");
            else Log.d("Test ruchu" , i + " to obiekt");
        }
        Log.d("Test ruchu" , "-----");
        Pole test[] = zbijane.toArray(new Pole[0]);
        for(int i = 0 ; i < test.length ; i++ ) {
            if(test[i] == null ) Log.d("Test ruchu" , i + " to null");
            else Log.d("Test ruchu" , i + " to obiekt");
        }*/

        if(czyJestCosDoZbicia) {
            for(int i = 0; i < dostepne_pola.size() ; i++) {
                if(zbijane.get(i) == null) {
                    zbijane.remove(i);
                    dostepne_pola.remove(i);
                }
            }
        }

        Pole r_tab[][] = { dostepne_pola.toArray(new Pole[0]), zbijane.toArray(new Pole[0])};
        //Pole r_tab[] = dostepne_pola.toArray(new Pole[0]);
        //for(int i = 0; i < r_tab.length ; i++) Log.d("Dostepny ruch " + i , r_tab[i].getKoordX() + " " + r_tab[i].getKoordY());
        return r_tab;
    }
    // TODODONE zrobić że bicie jest wymuszone ? być może jeśli jest w dostepny ruch opcja z pionkiem to zwraca specjalne tablice tylko z tymi dwoma wartościami
    // dzieki temu pomija problem że może to być ostatnia rzecz z sprawdzanych

    //TODODONE zrobić zamianę w damki i całe opcje wokół nich, albo lepiej -> nie robić damek w ogóle, porzucić feature

    //TODODONE uruchomić w końcu na tel

    //TODODONE zrobić kiedy ktoś wygrywa

    //TODODONE zrobić cofanie ruchu

    //TODODONE zrobić dialog z userami: pole z tekstem zmienia się na Twój ruch itp, button z clipartem cofania cofa; 3 przycisk usunąć? (miał być do zatwierdzania ruchu, ale chyba jak
    //jest lepiej

    //TODOIGNORED zrobić sejwowanie + odczyt z sejwa i żeby co każdy ruch zapisywało (i cofnięcie ruchu)

    private boolean czyDamka() {
        switch (typ) {
            case pole_brazowe_damka_bialy:
                return true;
            case pole_brazowe_damka_czarny:
                return true;
            default:
                return false;
        }
    }

    private void zbicie() {
        if(typ == TypyPol.pole_brazowe_pionek_bialy || typ == TypyPol.pole_brazowe_damka_bialy ) {
            liczbaFigurBialych--;
            liczbaPunktowCzarnych++;
        }
        if(typ == TypyPol.pole_brazowe_pionek_czarny || typ == TypyPol.pole_brazowe_damka_czarny ) {
            liczbaFigurCzarnych--;
            liczbaPunktowBialych++;
        }

        if(liczbaFigurBialych == 0 || liczbaFigurCzarnych == 0  ) zwyciestwo();
        poprzedni_zbite = new Pole(this);
        //w tym miejscu "this" musi się odnosić do poprzedni_zbite, dlatego wszystko nie działa
        setTyp(TypyPol.pole_brazowe_puste);
    }

    private static void zwyciestwo() {
        if(liczbaPunktowCzarnych > liczbaPunktowBialych) Dialog.oglosZwyciestwo(1);
        else if(liczbaPunktowCzarnych < liczbaPunktowBialych) Dialog.oglosZwyciestwo(2);
        else Dialog.oglosZwyciestwo(3);
    }

    private void zbicieAlt() {
        if(typ == TypyPol.pole_brazowe_pionek_bialy || typ == TypyPol.pole_brazowe_damka_bialy ) {
            liczbaFigurBialych--;
            liczbaPunktowBialych++;
        }
        if(typ == TypyPol.pole_brazowe_pionek_czarny || typ == TypyPol.pole_brazowe_damka_czarny ) {
            liczbaFigurCzarnych--;
            liczbaPunktowCzarnych++;
        }

        //TODODONE sprawdza ilosc punktow
        if(liczbaFigurBialych == 0 || liczbaFigurCzarnych == 0  ) zwyciestwo();
        poprzedni_zbite_wyniesienie = new Pole(this);
        //w tym miejscu "this" musi się odnosić do poprzedni_zbite, dlatego wszystko nie działa
        setTyp(TypyPol.pole_brazowe_puste);
    }

    private boolean czyToJestFigura() {
        switch (typ) {
            case pole_brazowe_damka_bialy:
                return true;
            case pole_brazowe_pionek_bialy:
                return true;
            case pole_brazowe_pionek_czarny:
                return true;
            case pole_brazowe_damka_czarny:
                return true;
            default:
                return false;
        }
    }

    private int Typ_z_DrawableID() {
        if(podswietlenie) {
            switch (typ) {
                case pole_brazowe_damka_bialy:
                    return R.drawable.pole_brazowe_damka_bialy_podswietlone;
                case pole_brazowe_pionek_bialy:
                    return R.drawable.pole_brazowe_pionek_bialy_podswietlone;
                case pole_biale_puste:
                    return  R.drawable.pole_biale_puste_podswietlone;
                case pole_brazowe_puste:
                    return  R.drawable.pole_brazowe_puste_podswietlone;
                case pole_brazowe_pionek_czarny:
                    return R.drawable.pole_brazowe_pionek_czarny_podswietlone;
                case pole_brazowe_damka_czarny:
                    return R.drawable.pole_brazowe_damka_czarny_podswietlone;
                default:
                    return 0;
            }
        } else {
            switch (typ) {
                case pole_brazowe_damka_bialy:
                    return R.drawable.pole_brazowe_damka_bialy;
                case pole_brazowe_pionek_bialy:
                    return R.drawable.pole_brazowe_pionek_bialy;
                case pole_biale_puste:
                    return  R.drawable.pole_biale_puste;
                case pole_brazowe_puste:
                    return  R.drawable.pole_brazowe_puste;
                case pole_brazowe_pionek_czarny:
                    return R.drawable.pole_brazowe_pionek_czarny;
                case pole_brazowe_damka_czarny:
                    return R.drawable.pole_brazowe_damka_czarny;
                default:
                    return 0;
            }
        }

    }



    private void zmienPodswietlenie() {
        podswietlenie = !podswietlenie;
        changeImage();
    }

    public void setTyp(TypyPol typ) {
        this.typ = typ;
        changeImage();
    }

     private void makeListener() {
        this.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeImage(R.drawable.testowe_zdjecie);
                Dotkniecie();
            }
        });
    }

    //TODODONE zrobić że jak zbije to ma kolejny ruch!!
    public TypyPol getTyp() {
        return typ;
    }


    public int getKoordX() {
        return koordX;
    }

    public int getKoordY() {
        return koordY;
    }

    public static Pole getObject(int x, int y) {
        return Szachownica.getTablica_obiektow()[Szachownica.wysokosc-1-y][x];
    }

    public static void setLiczbaFigurCzarnych(int x) {
        Pole.liczbaFigurCzarnych = x;
    }

    public static void setLiczbaFigurBialych(int x) {
        Pole.liczbaFigurBialych = x;
    }

    public static void setLiczbaPunktowBialych(int x) {
        Pole.liczbaPunktowBialych = x;
    }

    public static void setLiczbaPunktowCzarnych(int x) {
        Pole.liczbaPunktowCzarnych = x;
    }
}
