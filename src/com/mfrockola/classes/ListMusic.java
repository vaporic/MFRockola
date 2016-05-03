package com.mfrockola.classes;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Angel C on 03/05/2016.
 */
public class ListMusic {

    private ArrayList<Gender> gender;
    private String path;
    private int selectedGenre;
    private int songNumber;

    public ListMusic (String path) {
        selectedGenre = 0;
        setPath(path);
        setGender(new ArrayList<>());

        countGender();
    }

    public void countGender() {
        String [] listaArchivos = null;

        File directorio = new File(path);

        if (directorio.isDirectory())
            listaArchivos = directorio.list();

        for (int i = 0; i< listaArchivos.length; i++) {
            File archivoActual = new File(String.format("%s\\%s", path, listaArchivos[i]));

            if (archivoActual.isDirectory()) {
                gender.add(new Gender(listaArchivos[i],countSongs(archivoActual)));
            }
        }

        printListMusic();
    }

    public Cancion[] countSongs(File file) {

        String [] files = file.list();

        Cancion[] gender = new Cancion[files.length];

        for (int i = 0; i < files.length; i++) {
            gender[i] = new Cancion(songNumber,file.getName(),files[i]);
            songNumber++;
        }

        return gender;
    }

    public void printListMusic() {
        for (int i = 0; i < gender.size(); i++) {
            printGender(gender.get(i));
        }
    }

    public void printGender(Gender gender) {
        for (int i = 0; i < gender.getSongs().length; i++) {
            System.out.println(gender.getSongs()[i].toString());
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setGender(ArrayList<Gender> gender) {
        this.gender = gender;
    }

    public static void main(String [] args) {
        new ListMusic("C:/videos");
    }
}
