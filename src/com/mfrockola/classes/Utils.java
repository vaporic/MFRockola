package com.mfrockola.classes;

import java.util.Random;

/**
 * Created by Angel C on 19/07/2016.
 */
public class Utils {

    public static int EXT_MP4 = 0;
    public static int EXT_MP3 = 1;

    public static int getExtension(String path) {

        int index = path.lastIndexOf(".");

        String extension = path.substring(index+1);

        if (extension.equals("mp4")) {
            return EXT_MP4;
        }

        if (extension.equals("mp3")) {
            return EXT_MP3;
        }
        return EXT_MP4;
    }
}
