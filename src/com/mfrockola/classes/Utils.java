package com.mfrockola.classes;

import java.util.Random;

/**
 * Created by Angel C on 19/07/2016.
 */
public class Utils {

    public static int EXT_MP4 = 0;
    public static int EXT_MP3 = 1;
    public static int EXT_AVI = 2;
    public static int EXT_MPG = 3;
    public static int EXT_WMA = 4;
    public static int EXT_WAV = 5;
    public static int EXT_AAC = 6;

    public static int getExtension(String path) {

        int index = path.lastIndexOf(".");

        String extension = path.substring(index+1);

        if (extension.equals("mp4") || extension.equals("MP4")) {
            return EXT_MP4;
        }

        if (extension.equals("mp3") || extension.equals("MP3")) {
            return EXT_MP3;
        }

        if (extension.equals("avi") || extension.equals("AVI")) {
            return EXT_AVI;
        }

        if (extension.equals("mpg") || extension.equals("MPG")) {
            return EXT_MPG;
        }

        if (extension.equals("wma") || extension.equals("WMA")) {
            return EXT_WMA;
        }

        if (extension.equals("wav") || extension.equals("WAV")) {
            return EXT_WAV;
        }

        if (extension.equals("aac") || extension.equals("AAC")) {
            return EXT_AAC;
        }

        return EXT_MP4;
    }
}
