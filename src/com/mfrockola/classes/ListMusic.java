package com.mfrockola.classes;

import java.io.File;
import java.lang.reflect.Array;
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

    public static void main (String [] args) {
        new ListMusic("C:\\videos");
    }

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

        // creamos el ArrayList para ir almacenando todas las canciones en el genero

        // primero debemos validar la direccion del genero

        // luego determinar los artistas

        // luego contar artista por artista

        // agregar la cancion.

        // pasar las canciones del ArrayList al arreglo

        // agregar el genero

        String [] artistas = file.list();

        ArrayList<Cancion> provisionalGender = new ArrayList<>();

        Cancion[] gender = new Cancion[0];

        for (int i = 0; i < artistas.length; i++) {

//            gender[i] = new Cancion(songNumber,file.getName(),files[i]);
//            listOfSongs.add(new Cancion(songNumber,file.getName(),files[i]));
//            songNumber++;

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

                System.out.println(provisionalGender.get(k));
            }
        }

        System.out.println("Genero terminado");

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