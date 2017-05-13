package com.mfrockola.classes;

import javax.swing.*;
import java.io.File;

/**
 * Created by Angel C on 12/05/2017.
 */
public class RenameSongs {

    private String path;

    public RenameSongs(String path){
        this.path = path;
    }

    public boolean renameFiles(){
        // obtenemos un File con la ruta de los videos
        File file = new File(path);

        // si la ruta existe listamos los generos
        if (file.exists()){
            File [] genres = file.listFiles();

            // para cada genero
            for (int i = 0; i < genres.length; i++) {
                // verificamos si existe el genero
                if (genres[i].exists()){
                    // Listamos los cantantes
                    File [] singers = genres[i].listFiles();
                    // para cada cantante
                    for (int j = 0; j < singers.length; j++){
                        // si el cantante existe
                        if (singers[j].exists()) {
                            //listamos las canciones y las renombramos
                            rename(singers[j].listFiles());
                        }
                    }
                    rename(singers);
                }
            }
            rename(genres);
        } else {
            JOptionPane.showMessageDialog(null,"El directorio no existe.");
            return false;
        }
        return true;
    }

    private void rename(File [] files){
        for(int i = 0; i < files.length;i++){
            if (files[i].exists()){
                checkName(files[i]);
            }
        }
    }

    private void checkName(File file){
        String name = file.getName();

        name = name.replace('ñ','n');
        name = name.replace('á','a');
        name = name.replace('é','e');
        name = name.replace('í','i');
        name = name.replace('ó','o');
        name = name.replace('ú','u');
        name = name.replace('Ñ','N');
        name = name.replace('Á','A');
        name = name.replace('É','E');
        name = name.replace('I','I');
        name = name.replace('Ó','O');
        name = name.replace('Ú','U');
        name = name.replace('à','a');
        name = name.replace('è','e');
        name = name.replace('ì','i');
        name = name.replace('ò','o');
        name = name.replace('ù','u');
        name = name.replace('À','A');
        name = name.replace('È','E');
        name = name.replace('Ì','I');
        name = name.replace('Ò','O');
        name = name.replace('Ù','U');
        name = name.replace('\'',' ');

        String newPath = file.getPath();
        newPath = newPath.substring(0,newPath.length()-name.length())+name;
        File newPathFile = new File(newPath);
        file.renameTo(newPathFile);
    }
}
