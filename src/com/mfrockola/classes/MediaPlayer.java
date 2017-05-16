package com.mfrockola.classes;

import com.sun.jna.Native;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.awt.*;

import com.sun.jna.NativeLibrary;

/*
    This class is responsible for calling the VLC instance to create the media player
 */

class MediaPlayer {
    // We created public instances of Embedded MediaPlayer since we use it in other classes
    EmbeddedMediaPlayer embeddedMediaPlayer;
    EmbeddedMediaPlayer embeddedMediaPlayerMp3;

    String pathSongs;

    // We created a JPanel container for our player
    private JPanel mediaPlayerContainer;

    // In the constructor we start our variables
    MediaPlayer(String pathVLC, String pathSongs) {

        this.pathSongs = pathSongs;

        // We call the native libraries of VLC, passing them as parameters the path where VLC is installed
        try {
            NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),pathVLC);
            Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(),LibVlc.class);

            // A Canvas is created
            Canvas canvas = new Canvas();
            canvas.setBackground(Color.WHITE);

            // Add to the player container our canvas
            mediaPlayerContainer = new JPanel();
            mediaPlayerContainer.setLayout(new BorderLayout());
            mediaPlayerContainer.add(canvas,BorderLayout.CENTER);

            // Instantiating a MediaPlayerFactory. Create an embedded player and add our canvas
            MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory("--no-video-title-show");
            embeddedMediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
            embeddedMediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(canvas));
            embeddedMediaPlayerMp3 = mediaPlayerFactory.newEmbeddedMediaPlayer();
        } catch(UnsatisfiedLinkError error) {
            // If there is an exception is that the VLC directory is not found
            JOptionPane.showMessageDialog(null,"No se encuentran las librerias de VLC, intente reinstalar VLC y configure el directorio correctamente. Si el problema persiste y su version de Java es de 64Bits pruebe instalando VLC de 64 Bits.");
            System.exit(1);
        }
    }

    // Method to get our container from the player
    JPanel getMediaPlayerContainer() {
        return mediaPlayerContainer;
    }

    // Method to play audio
    void playAudio(String path, String pathPromotionalVideo) {
        embeddedMediaPlayerMp3.playMedia(path);
        embeddedMediaPlayer.playMedia(pathPromotionalVideo);
    }

    // Method to play audio
    void playAudio(String gender, String singer, String songName, String pathPromotionalVideo) {
        embeddedMediaPlayerMp3.playMedia(String.format("%s\\%s\\%s\\%s", pathSongs,gender,singer,songName));
        embeddedMediaPlayer.playMedia(pathPromotionalVideo);
    }

    // Method to play video
    void playVideo(String path) {
        embeddedMediaPlayer.playMedia(path);
    }

    // Method to play video
    void playVideo(String gender, String singer, String songName) {
        embeddedMediaPlayer.playMedia(String.format("%s\\%s\\%s\\%s", pathSongs,gender,singer,songName));
    }
}