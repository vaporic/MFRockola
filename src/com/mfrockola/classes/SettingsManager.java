package com.mfrockola.classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Angel C on 14/05/2017.
 */
public class SettingsManager {

    // Panel TIME
    static final String KEY_RANDOM_SONG = "randomSong";
    static final String KEY_RESET_SONGS = "resetSongs";
    static final String KEY_PROMOTIONAL_VIDEO = "promotionalVideo";
    static final String KEY_DEFAULT_PROMOTIONAL_VIDEO = "defaultPromotionalVideo";
    static final String KEY_PATH_PROMOTIONAL_VIDEO = "pathPromotionalVideo";

    // Panel CREDITS
    static final String KEY_AMOUNT_OF_CREDITS = "amountOfCredits";
    static final String KEY_FREE = "free";
    static final String KEY_LOCK_SCREEN = "lockScreen";
    static final String KEY_SAVE_SONGS = "saveSongs";
    static final String KEY_FONT_SELECTOR_SIZE = "fontSelectorSize";

    // Panel PATHS
    static final String KEY_PATH_SONGS = "pathSongs";
    static final String KEY_PATH_VIDEOS_MP3 = "pathVideosMP3";
    static final String KEY_PATH_VLC = "pathVLC";

    // Panel KEYBOARD AND MOUSE
    static final String KEY_UP_LIST = "keyUpList";
    static final String KEY_DOWN_LIST = "keyDownList";
    static final String KEY_UP_GENRE = "keyUpGenre";
    static final String KEY_DOWN_GENRE = "keyDownGenre";
    static final String KEY_FULL_SCREEN = "keyFullScreen";
    static final String KEY_DELETE_NUMBER = "keyDeleteNumber";
    static final String KEY_NEXT_SONG = "keyNextSong";
    static final String KEY_ADD_CREDIT = "keyAddCredit";
    static final String KEY_DELETE_CREDIT = "keyDeleteCredit";
    static final String KEY_CLICK_OF_CREDITS = "clickOfCredits";
    static final String KEY_RIGHT_CLICK_CANCEL_MUSIC = "rightClickCancelMusic";
    static final String KEY_PASSWORD = "password";

    // Panel APPEARANCE
    static final String KEY_DEFAULT_BACKGROUND = "defaultBackground";
    static final String KEY_PATH_BACKGRONUD = "pathBackground";
    static final String KEY_COLOR_1 = "color1";
    static final String KEY_COLOR_2 = "color2";
    static final String KEY_FONT_CELLS = "fontCells";
    static final String KEY_FONT_CELLS_SIZE = "fontCellsSize";
    static final String KEY_FONTS_CELLS_COLOR = "fontCellsColor";
    static final String KEY_FONT_CELL_BOLD = "fontCellsBold";

    // Panel STATISTICS
    static final String KEY_USED_CREDITS = "usedCredits";
    static final String KEY_INSERTED_CREDITS = "insertedCredits";

    // Panel PROMOTIONS

    static final String KEY_ADD_ADITIONAL_CREDIT = "addAditionalCredit";
    static final String KEY_NUMBER_ADITIONAL_CREDITS = "numberAditionalCredits";
    static final String KEY_EVERY_AMOUNT_OF_CREDITS = "everyAmountOfCredit";
    static final String KEY_CONTINUOUS_CREDITS = "continuousCredits";
    static final String KEY_AWARD_PRIZE = "awardPrize";
    static final String KEY_PRIZE_AMOUNT = "prizeAmount";
    static final String KEY_CREDITS_FOR_PRICE = "creditsForPrize";
    static final String KEY_TYPE_OF_PRIZE = "typeOfPrize";

    // EXTRAS

    static final String KEY_SAVED_CREDITS = "savedCredits";
    static final String KEY_SAVED_SONGS = "savedSongs";
    static final String KEY_LANGUAGE = "language";
    static final String KEY_VERSION = "version";

    // SONGS

    static final String KEY_SONG_NUMBER = "songNumber";
    static final String KEY_SONG_GENRE = "songGenre";
    static final String KEY_SONG_SINGER = "songSinger";
    static final String KEY_SONG_NAME = "songName";


    private JSONObject settings;

    SettingsManager(){
        readFile();
    }

    private void readFile(){
        BufferedReader reader = null;

        String settingsJsonStr;

        StringBuffer buffer = new StringBuffer();

        try {

            File file = new File(new File("").getAbsolutePath()+"\\config.json");

            if (!file.exists()) {
                writeDefaultSettings();
                reader = new BufferedReader(new FileReader(file));
            } else {
                reader = new BufferedReader(new FileReader(file));
            }

            String line;

            while ((line = reader.readLine())!=null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length()==0) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error en el archivo de configuracion",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            settingsJsonStr = buffer.toString();

            settings = new JSONObject(settingsJsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    System.out.println("Error closing stream");
                }
            }
        }
    }

    Object getSetting(String key){
        try {
            return settings.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    void writeSetting(boolean update, KeyPairValue value) {
        try {
            settings.put(value.key,value.value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (update) {
            updateSettingsFile();
        }
    }

    private void updateSettingsFile(){
        try {
            FileWriter file = new FileWriter(new File("").getAbsolutePath()+"\\config.json");
            settings.write(file);
            file.flush();
            file.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }


    void writeDefaultSettings(){
        ArrayList newSettings = new ArrayList();

        // Panel TIME
        newSettings.add(new KeyPairValue(KEY_RANDOM_SONG, 1));
        newSettings.add(new KeyPairValue(KEY_RESET_SONGS,15));
        newSettings.add(new KeyPairValue(KEY_PROMOTIONAL_VIDEO,true));
        newSettings.add(new KeyPairValue(KEY_DEFAULT_PROMOTIONAL_VIDEO,true));
        newSettings.add(new KeyPairValue(KEY_PATH_PROMOTIONAL_VIDEO,"C:\\MFRockola\\Videos para MP3\\promotional.mpg"));

        // Panel CREDITS
        newSettings.add(new KeyPairValue(KEY_AMOUNT_OF_CREDITS,1));
        newSettings.add(new KeyPairValue(KEY_FREE,false));
        newSettings.add(new KeyPairValue(KEY_LOCK_SCREEN,true));
        newSettings.add(new KeyPairValue(KEY_SAVE_SONGS,true));
        newSettings.add(new KeyPairValue(KEY_FONT_SELECTOR_SIZE,50));

        // Panel PATHS
        newSettings.add(new KeyPairValue(KEY_PATH_SONGS,"c:\\videos"));
        newSettings.add(new KeyPairValue(KEY_PATH_VIDEOS_MP3,"C:\\MFRockola\\Videos para MP3"));
        newSettings.add(new KeyPairValue(KEY_PATH_VLC,"C:\\Program Files\\VideoLAN\\VLC"));

        // Panel KEYBOARD AND MOUSE
        newSettings.add(new KeyPairValue(KEY_UP_LIST,106));
        newSettings.add(new KeyPairValue(KEY_DOWN_LIST,111));
        newSettings.add(new KeyPairValue(KEY_UP_GENRE,107));
        newSettings.add(new KeyPairValue(KEY_DOWN_GENRE,109));
        newSettings.add(new KeyPairValue(KEY_FULL_SCREEN,70));
        newSettings.add(new KeyPairValue(KEY_DELETE_NUMBER,67));
        newSettings.add(new KeyPairValue(KEY_NEXT_SONG,78));
        newSettings.add(new KeyPairValue(KEY_ADD_CREDIT,65));
        newSettings.add(new KeyPairValue(KEY_DELETE_CREDIT,66));
        newSettings.add(new KeyPairValue(KEY_CLICK_OF_CREDITS,0));
        newSettings.add(new KeyPairValue(KEY_RIGHT_CLICK_CANCEL_MUSIC,false));
        newSettings.add(new KeyPairValue(KEY_PASSWORD,""));

        // Panel APPEARANCE
        newSettings.add(new KeyPairValue(KEY_DEFAULT_BACKGROUND,true));
        newSettings.add(new KeyPairValue(KEY_PATH_BACKGRONUD,getDeaultURLBackground().getFile()));
        newSettings.add(new KeyPairValue(KEY_COLOR_1,"102,204,255"));
        newSettings.add(new KeyPairValue(KEY_COLOR_2,"255,255,255"));
        newSettings.add(new KeyPairValue(KEY_FONT_CELLS,"Consolas"));
        newSettings.add(new KeyPairValue(KEY_FONT_CELLS_SIZE,20));
        newSettings.add(new KeyPairValue(KEY_FONTS_CELLS_COLOR,"000,000,000"));
        newSettings.add(new KeyPairValue(KEY_FONT_CELL_BOLD,1));

        // Panel STATISTICS
        newSettings.add(new KeyPairValue(KEY_USED_CREDITS,0));
        newSettings.add(new KeyPairValue(KEY_INSERTED_CREDITS,0));

        // Panel PROMOTIONS
        newSettings.add(new KeyPairValue(KEY_ADD_ADITIONAL_CREDIT,false));
        newSettings.add(new KeyPairValue(KEY_NUMBER_ADITIONAL_CREDITS,0));
        newSettings.add(new KeyPairValue(KEY_EVERY_AMOUNT_OF_CREDITS,0));
        newSettings.add(new KeyPairValue(KEY_CONTINUOUS_CREDITS,false));
        newSettings.add(new KeyPairValue(KEY_AWARD_PRIZE,false));
        newSettings.add(new KeyPairValue(KEY_PRIZE_AMOUNT,0));
        newSettings.add(new KeyPairValue(KEY_CREDITS_FOR_PRICE,0));
        newSettings.add(new KeyPairValue(KEY_TYPE_OF_PRIZE,""));

        // EXTRAS
        newSettings.add(new KeyPairValue(KEY_SAVED_CREDITS,0));
        newSettings.add(new KeyPairValue(KEY_SAVED_SONGS,new JSONArray()));
        newSettings.add(new KeyPairValue(KEY_LANGUAGE,"ES"));
        newSettings.add(new KeyPairValue(KEY_VERSION,"17.05.29.1455"));

        String settingsNew = "{}";
        try {
            settings = new JSONObject(settingsNew);
            KeyPairValue keyPairValue;
            for (int i = 0; i < newSettings.size(); i++){
                keyPairValue = (KeyPairValue) newSettings.get(i);
                settings.put(keyPairValue.key,keyPairValue.value);
            }

            updateSettingsFile();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private URL getDeaultURLBackground(){
        URL urlBackground = this.getClass().getResource("/com/mfrockola/imagenes/fondo.jpg");
        return urlBackground;
    }
}
