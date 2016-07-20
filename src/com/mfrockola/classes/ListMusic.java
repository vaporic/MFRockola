package com.mfrockola.classes;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Angel C on 03/05/2016.
 */
public class ListMusic {

    private ArrayList <Cancion> listOfSongs;
    private ArrayList<Gender> gender;
    private String path;
    private String pathPromVideos;
    private int selectedGender;
    private int songNumber;

    private String [] promVideos;

    private Random random;

    public static void main (String [] args) {
        new ListMusic("C:\\videos","");
    }

    public ListMusic (String path, String pathPromVideos) {
        setPath(path);
        setPathPromVideos(pathPromVideos);
        setGender(new ArrayList<>());
        setListOfSongs(new ArrayList<>());

        selectedGender = 0;
        countGender();

        random = new Random();
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

        File directorioVideosPromocionales = new File(getPathPromVideos());

        if (directorioVideosPromocionales.isDirectory()) {
            promVideos = directorioVideosPromocionales.list();
        }
    }

    public boolean upGender() {
        if (selectedGender + 1 < gender.size()) {
            selectedGender++;
            return true;
        } else {
            selectedGender = 0;
            return true;
        }
    }

    public boolean downGender() {
        if (selectedGender != 0) {
            selectedGender--;
            return true;
        } else {
            selectedGender = gender.size()-1;
            return true;
        }
    }

    public String getNameOfGender() {
        return gender.get(selectedGender).getName();
    }

    public Cancion[] countSongs(File file) {

        String [] artistas = file.list();

        ArrayList<Cancion> provisionalGender = new ArrayList<>();

        Cancion[] gender = new Cancion[0];

        for (int i = 0; i < artistas.length; i++) {

            File artista = new File(String.format("%s\\%s\\%s", path,file.getName(), artistas[i]));

            if (artista.isDirectory()) {
                String [] canciones = artista.list();

                for (int j = 0; j < canciones.length; j++) {
                    provisionalGender.add(new Cancion(songNumber,file.getName(),artista.getName(),canciones[j]));
                    listOfSongs.add(new Cancion(songNumber,file.getName(),artista.getName(),canciones[j]));
                    songNumber++;
                }
            }

            gender = new Cancion[provisionalGender.size()];

            for (int k = 0; k < provisionalGender.size(); k++) {
                gender[k] = provisionalGender.get(k);
            }
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

    public String getPromVideo() {
        String promVideo = promVideos[random.nextInt(promVideos.length)];
        return promVideo;
    }

    public String getPathPromVideos() {
        return pathPromVideos;
    }

    public void setPathPromVideos(String pathPromVideos) {
        this.pathPromVideos = pathPromVideos;
    }
}