package com.example.warcaby_1semestrprojekt;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class Save {
    private static String save = "save.txt";
    private static String pomoc = "pomoc.txt";

    public static void zapis(String file, String tekst, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(file, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void zapisDoPliku(String data,Context context,String nazwaPliku) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(nazwaPliku, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromFile(Context context,String nazwaPliku) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(nazwaPliku);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public static String tabNaString(int[][] tab) {
        String re = "";
        for(int i = 0 ; i < tab.length ; i++) {
            for(int j = 0 ; j < tab[i].length ; j++) {
                re += tab[i][j];
                if(j != tab[i].length-1 ) re += ",";
            }
            if(i != tab.length-1 ) re += ";";
        }
        return re;
    }
    public static int[][] StringNaTab(String dane) {
        int[][] re = new int[8][8];

        String wiersze_str[] = dane.split(";");
        String a[][] = new String[8][8];
        for(int i = 0 ; i < wiersze_str.length ; i++) {
            a[i] = wiersze_str[i].split(",");
        }

        for(int i = 0 ; i < re.length ; i++ ) {
            for(int j = 0; j < re[i].length ; j++ ) {
                re[i][j] = Integer.parseInt(a[i][j]);
            }
        }

        return re;
    }

    private static void zapisDodatkoweInfo(Context context) {
        boolean b = Pole.getKolejkaCzarnych();
        int czarne_p = Pole.getLiczbaPunktowCzarnych();
        int biale_p = Pole.getLiczbaPunktowBialych();
        int czarne_r = Pole.getLiczbaRuchowCzarny();
        int biale_r = Pole.getLiczbaRuchowBialy();

        zapisDoPliku(b+";"+czarne_p+";"+biale_p+";"+czarne_r+";"+biale_r,context,pomoc);
    }

    private static void wczytajDodatkoweInfo(Context context) {
        String s = readFromFile(context,pomoc);
        String[] tab = s.split(";");
        Pole.setKolejkaCzarnych(Boolean.parseBoolean(tab[0]));
        Pole.setLiczbaPunktowCzarnych(Integer.parseInt(tab[1]));
        Pole.setLiczbaPunktowBialych(Integer.parseInt(tab[2]));
        Pole.setLiczbaRuchowCzarny(Integer.parseInt(tab[3]));
        Pole.setLiczbaRuchowBialy(Integer.parseInt(tab[4]));

    }
    //TODODONE bugfix po wczytaniu nie przechowuje statystyk?/nie wczytuje ich poprawnie
    // albo raczej ostatniego ruchu

    //TODODONE dodac informacje kto ma jaki ruch
    public static void zapisz(Context context) {
        Pole[][] aktualnaTablicaObiektow = Szachownica.getTablica_obiektow();
        int[][] aktualna_liczby = Szachownica.tablicaObiektowNaTabliceLiczb(aktualnaTablicaObiektow);
        String dane = tabNaString(aktualna_liczby);
        zapisDoPliku(dane,context,save);
        zapisDodatkoweInfo(context);
    }

    public static void wczytaj(Context context, Activity activity) {
        if(!Pole.getCzyCosJestZaznaczone()) {
            String s = readFromFile(context,save);
            int[][] si = StringNaTab(s);
            Szachownica.makeTablicaObiektow(activity,si);
            //TODODONE tu się przyjrzeć czy nie zeruje to poprzedni_zbite przez co wczytanie narusza kolejke? czy cos
            wczytajDodatkoweInfo(context);
        }

    }
}

