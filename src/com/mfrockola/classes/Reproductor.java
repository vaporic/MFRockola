package com.mfrockola.classes;

import com.sun.jna.Native;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import com.sun.jna.NativeLibrary;

/**
 * Created by Angel C on 14/04/2016.
 */
public class Reproductor {
    private MediaPlayerFactory mediaPlayerFactory;
    EmbeddedMediaPlayer embeddedMediaPlayer;
    private Canvas canvas;
    private JPanel mediaPlayerContainer;

    OperacionesRegConfig registroDatos = new OperacionesRegConfig();
    RegConfig configuraciones;

    public Reproductor() {

        try
        {
            registroDatos.abrirRegConfigLectura();
            configuraciones = registroDatos.leerRegConfigLectura();
        }
        catch (NullPointerException excepcion)
        {
            Configuracion configurar = new Configuracion();
        }

        try {
            NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),configuraciones.getDireccionVlc());
            Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(),LibVlc.class);

            canvas = new Canvas();
            canvas.setBackground(Color.WHITE);

            mediaPlayerContainer = new JPanel();
            mediaPlayerContainer.setLayout(new BorderLayout());
            mediaPlayerContainer.add(canvas,BorderLayout.CENTER);

            mediaPlayerFactory = new MediaPlayerFactory("--no-video-title-show");
            embeddedMediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();

            embeddedMediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(canvas));
        }
        catch(UnsatisfiedLinkError error)
        {
            System.out.println("No se encuentra el directorio del VLC. Presione Q para ir a la configuracion");
            System.exit(1);
        }
    }

    public JPanel obtenerReproductor()
    {
        return mediaPlayerContainer;
    }

    public void reproducirMusica(String genero, String artista, String nombreCancion)
    {
        embeddedMediaPlayer.playMedia(String.format("%s\\%s\\%s\\%s", configuraciones.getDireccionVideos(),genero,artista,nombreCancion));
    }

    public static void main (String [] args) {
        new Reproductor();
    }
}