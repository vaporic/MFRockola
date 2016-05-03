package com.mfrockola.classes;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Angel C on 03/05/2016.
 */
public class ListMusic {

    private ArrayList <Cancion> listOfSongs;
    private ArrayList<Gender> gender;
    private String path;
    private int selectedGender;
    private int songNumber;

    public ListMusic (String path) {
        setPath(path);
        setGender(new ArrayList<>());
        setListOfSongs(new ArrayList<>());

        selectedGender = 0;
        countGender();
    }

    public int getSelectedGender (){
        return selectedGender;
    }

    public void setListOfSongs(ArrayList<Cancion> listOfSongs) {
        this.listOfSongs = listOfSongs;
    }

    public int getSizeListOfSongs () {
        return listOfSongs.size();
    }

    public Cancion getSong(int number) {
        return listOfSongs.get(number);
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
    }

    public boolean upGender() {
        if (selectedGender + 1 < gender.size()) {
            selectedGender++;
            return true;
        } else
            return false;
    }

    public boolean downGender() {
        if (selectedGender != 0) {
            selectedGender--;
            return true;
        } else
            return false;
    }

    public String getNameOfGender() {
        return gender.get(selectedGender).getName();
    }

    public Cancion[] countSongs(File file) {

        String [] files = file.list();

        Cancion[] gender = new Cancion[files.length];

        for (int i = 0; i < files.length; i++) {
            gender[i] = new Cancion(songNumber,file.getName(),files[i]);
            listOfSongs.add(new Cancion(songNumber,file.getName(),files[i]));
            songNumber++;
        }

        return gender;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setGender(ArrayList<Gender> gender) {
        this.gender = gender;
    }

    public Cancion[] getGenderSongs(int i) {
        return gender.get(i).getSongs();
    }

}