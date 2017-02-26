package com.mfrockola.classes;

import java.net.URL;
import java.util.Random;

/**
 * Created by Angel C on 19/07/2016.
 */
public class Utils {

    public static final int SELECT_VIDEO = 0;
    public static final int SELECT_IMAGE = 1;

    public static int EXT_UNKNOW = -1;
    public static int EXT_MP4 = 0;
    public static int EXT_MP3 = 1;
    public static int EXT_AVI = 2;
    public static int EXT_MPG = 3;
    public static int EXT_WMA = 4;
    public static int EXT_WAV = 5;
    public static int EXT_AAC = 6;
    public static int EXT_FLV = 7;
    public static int EXT_MKV = 8;

    public static int getExtension(String path) {

        int index = path.lastIndexOf(".");

        String extension = path.substring(index+1);

        if (extension.equals("jpg") || extension.equals("JPG")) {
            return EXT_UNKNOW;
        }

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

        if (extension.equals("flv") || extension.equals("FLV")) {
            return EXT_FLV;
        }

        if (extension.equals("mkv") || extension.equals("MKV")) {
            return EXT_MKV;
        }

        return EXT_MP4;
    }

    public static String printKeyCharCode(int extendedKeyCode) {
        switch (extendedKeyCode) {
            case 16777425:
                return "Ñ";
            case 8:
                return "BORRAR";
            case 10:
                return "INTRO";
            case 17:
                return "CTRL";
            case 18:
                return "ALT";
            case 19:
                return "PAUSA";
            case 26:
                return "SPACE";
            case 27:
                return "ESC";
            case 33:
                return "REPAG";
            case 34:
                return "AVPAG";
            case 35:
                return "FIN";
            case 36:
                return "INIC";
            case 44:
                return ",";
            case 45:
                return "-";
            case 46:
                return ".";
            case 65:
                return "A";
            case 66:
                return "B";
            case 67:
                return "C";
            case 68:
                return "D";
            case 69:
                return "E";
            case 70:
                return "F";
            case 71:
                return "G";
            case 72:
                return "H";
            case 73:
                return "I";
            case 74:
                return "J";
            case 75:
                return "K";
            case 76:
                return "L";
            case 77:
                return "M";
            case 78:
                return "N";
            case 79:
                return "O";
            case 80:
                return "P";
            case 81:
                return "Q";
            case 82:
                return "R";
            case 83:
                return "S";
            case 84:
                return "T";
            case 85:
                return "U";
            case 86:
                return "V";
            case 87:
                return "W";
            case 88:
                return "X";
            case 89:
                return "Y";
            case 90:
                return "Z";
            case 106:
                return "*";
            case 107:
                return "+";
            case 109:
                return "-";
            case 111:
                return "/";
            case 112:
                return "F1";
            case 113:
                return "F2";
            case 114:
                return "F3";
            case 115:
                return "F4";
            case 116:
                return "F5";
            case 117:
                return "F6";
            case 118:
                return "F7";
            case 119:
                return "F8";
            case 120:
                return "F9";
            case 121:
                return "F10";
            case 122:
                return "F11";
            case 123:
                return "F12";
            case 127:
                return "SUPR";
            case 129:
                return "´";
            case 145:
                return "SCRLK";
            case 155:
                return "INSRT";
            case 161:
                return "{";
            case 162:
                return "}";
            case 521:
                return "+";
            case 524:
                return "WIN";
            default:
                return String.format("%s", extendedKeyCode);
        }
    }
}
