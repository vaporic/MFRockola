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

public class MediaPlayer {
    // We created public instances of Embedded MediaPlayer since we use it in other classes
    EmbeddedMediaPlayer embeddedMediaPlayer;
    EmbeddedMediaPlayer embeddedMediaPlayerMp3;

    // We created a JPanel container for our player
    private JPanel mediaPlayerContainer;

    // We create a UserSettings object to get the routes of the videos and VLC
    private UserSettings mUserSettings;

    // In the constructor we start our variables
    public MediaPlayer() {
        try {
            // Read the user settings. If they do not exist we open a configuration window
            UserSettingsManagement mUserSettingsManagement = new UserSettingsManagement();
            mUserSettingsManagement.openUserSettings();
            mUserSettings = mUserSettingsManagement.readUserSettings();
        } catch (NullPointerException excepcion) {
            SettingsWindow settingsWindow = new SettingsWindow();
        }

        // We call the native libraries of VLC, passing them as parameters the path where VLC is installed
        try {
            NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),mUserSettings.getDireccionVlc());
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
            System.out.println("No se encuentra el directorio del VLC. Presione Q para ir a la configuracion");
            System.exit(1);
        }
    }

    // Method to get our container from the player
    public JPanel getMediaPlayerContainer() {
        return mediaPlayerContainer;
    }

    // Method to play audio
    public void playAudio(String genero, String artista, String nombreCancion, String pathPromVideo) {
        embeddedMediaPlayerMp3.playMedia(String.format("%s\\%s\\%s\\%s", mUserSettings.getDireccionVideos(),genero,artista,nombreCancion));
        embeddedMediaPlayer.playMedia(pathPromVideo);
    }

    // Method to play video
    public void playVideo(String genero, String artista, String nombreCancion) {
        embeddedMediaPlayer.playMedia(String.format("%s\\%s\\%s\\%s", mUserSettings.getDireccionVideos(),genero,artista,nombreCancion));
    }
}