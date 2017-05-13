package com.mfrockola.classes;

import com.mfrockola.android.InternetConnection;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.mfrockola.classes.Utils.*;

/**
 * This class is the center of MFRockola. From this class we instantiate all the others
 * and it is what we see from the screen
 */
class Interface extends JFrame {
    private ObjectOutputStream outputStream;
    private UserSettings mUserSettings;

    // This variable stores the amount of credits entered
    private int credits;

    // This pair of variables store the size of the screen to draw the interface
    private int widthScreen;
    private int heightScreen;

    // List of songs that are blocked
    private BlockedSongs mBlockedSongs;

    // Instance that stores the playlist
    private PlayList mPlayList = new PlayList(); // Objeto de las musicas en reproduccion.

    // Boolean that determines whether the screen is complete
    private boolean isFullScreen = false;

    // Variable that stores the interface background
    private BackgroundImagePanel mBackgroundImagePanel;

    // Panel containing the video surface
    private JPanel videoPanel;
    private JPanel panel;

    // Panel containing the bottom interface information
    private JPanel bottomPanel;

    // Dimension object that contains the width and height of the screen
    private Dimension resolution;

    // ListMusic object that keeps the list of songs
    private ListMusic listMusicData;

    // JList object containing in the interface all available videos in the configured directory
    private JList mSongListInterface;
    // JList object containing all the songs in playback in the interface
    private JList playListInterface;

    // JLabels of the interface
    private JLabel labelMusicalGenre;
    private JLabel labelSongPlayingBottom;
    private JLabel labelSongPlayingRight;
    private JLabel labelCredits;
    private JLabel labelPromotions;

    // MediaPlayer object that has the video player
    private MediaPlayer mMediaPlayer;

    // SongSelector object used to select songs
    private SongSelector mSongSelector;


    private int monedasASubir;
    private int creditosASubir;

    // cancel song flag
    private boolean cancelSong;

    // counter cancel click flag
    private int counterClick;

    // free credits flag
    private boolean freeCredits;

    // Add additional promotional credit flag set in the user settings
    private boolean addAditionalCredit;

    // Credits inserted for give prizes and aditional credits
    private int insertedCredits;

    // Flag give prize set in the user settings
    private boolean givePrizeSetting;

    // when this integer reached the user settings, MFRockola give a prize
    private int insertedCreditsForPrize;

    // Bars for the list of songs
    private JScrollPane mScrollPane;

    // This timer controls the text that shows the credit tag
    private Timer timerChangerLabelCredits;

    // this timer controls the automatic fullscreen
    private Timer timerFullScreen;

    // This timer controls the error that sometimes occurs when the video screen is complete
    private Timer timer;

    // This timer controls the playback of a random song when there are no credits
    private Timer timerRandomSong;

    private KeyboardManager mKeyboardManager;

    // keep the promotional sound
    private Clip promotionalSound;

    Interface() {
        try {
            UserSettingsManagement mUserSettingsManagement = new UserSettingsManagement();
            mUserSettingsManagement.openUserSettings();
            mUserSettings = mUserSettingsManagement.readUserSettings();
            mBlockedSongs = new BlockedSongs(mUserSettings.getReinicioMusicas());
            mSongSelector = new SongSelector(mUserSettings.getTeclaBorrar(), mUserSettings.getTeclaSubirLista(),
                    mUserSettings.getTeclaBajarLista(), mUserSettings.getTeclaSubirGenero(),
                    mUserSettings.getTeclaBajarGenero(),mUserSettings.getFontSelectorSize());
            monedasASubir = mUserSettings.getCantidadMonedasInsertadas();
            creditosASubir = mUserSettings.getCantidadCreditosUsados();
            cancelSong = mUserSettings.isCancelMusic();
            freeCredits = mUserSettings.isLibre();
            addAditionalCredit = mUserSettings.isAgregarAdicional();
            givePrizeSetting = mUserSettings.isEntregarPremio();

            File file = new File(mUserSettings.getPathVlc());
            if (!file.exists()) {
                JOptionPane.showMessageDialog(null,"VLC no se encuentra instalado o el directorio no se encuentra.","Error de VLC",JOptionPane.CLOSED_OPTION);
                System.exit(-1);
            }

            file = new File(mUserSettings.getPathSongs());

            if (!file.exists()) {
                JOptionPane.showMessageDialog(null,"El directorio de musicas no se encuentra, verifiquelo e intente nuevamente.","Error de Directorios",JOptionPane.CLOSED_OPTION);
                System.exit(-1);
            }

            file = new File(mUserSettings.getPathVideosMp3());

            if (!file.exists()) {
                JOptionPane.showMessageDialog(null,"El directorio de los videos predeterminados no se encuentra, verifiquelo e intente nuevamente.","Error de Directorios",JOptionPane.CLOSED_OPTION);
                System.exit(-1);
            }

            file = new File(mUserSettings.getDireccionVideoPromocional());

            if (!file.exists()) {
                JOptionPane.showMessageDialog(null,"El video promocional no se encuentra, verifique la ruta.");
            }
        }
        catch (NullPointerException excepcion) {
            new SettingsWindow();
        }

        initComponents();

        ActionListener changeLblCredits = e -> {
            if (freeCredits) {
                labelCredits.setText("Creditos Libres");
            } else {
                labelCredits.setText(String.format("Creditos: %d", credits));
            }

            labelCredits.setForeground(Color.WHITE);
        };

        timerChangerLabelCredits = new Timer(5000, changeLblCredits);
        timerChangerLabelCredits.setRepeats(false);

        ActionListener changeFullScreen = e -> {
            if (!isFullScreen) {
                setFullScreen();

                timer.restart();
            }
        };

        ActionListener playRandomSong = e -> {
            Random random = new Random();
            File path = new File(mUserSettings.getPathSongs());
            if (path.exists()) {
                File [] genres = path.listFiles();
                File selectedGenre = genres[random.nextInt(genres.length)];
                if (selectedGenre.exists()) {
                    File [] singers = selectedGenre.listFiles();
                    File selectedSinger = singers[random.nextInt(singers.length)];
                    if (selectedSinger.exists()) {
                        File [] songs = selectedSinger.listFiles();

                        String randomSong = songs[random.nextInt(songs.length)].getAbsolutePath();

                        int extension = Utils.getExtension(randomSong);

                        if (extension == EXT_MP4 || extension == EXT_AVI || extension == EXT_MPG || extension == EXT_FLV || extension == EXT_MKV) {
                            mMediaPlayer.playVideo(randomSong);
                        } else if (extension == EXT_MP3 || extension == EXT_WMA || extension == EXT_WAV || extension == EXT_AAC) {
                            mMediaPlayer.playAudio(
                                    randomSong,
                                    mUserSettings.getPathVideosMp3() + "\\" + listMusicData.getPromVideo());
                        }
                    }
                }
            }
        };

        ActionListener pressKey = e -> {
            try {
                Robot robot = new Robot();
                robot.keyPress(120);
                robot.keyRelease(120);
            } catch (AWTException exception) {
                exception.printStackTrace();
            }
        };

        timer = new Timer(500, pressKey);
        timer.setRepeats(false);

        timerFullScreen = new Timer(10000, changeFullScreen);
        timerFullScreen.setRepeats(false);

        timerRandomSong = new Timer(mUserSettings.getMusicAleatoria()*1000*60,playRandomSong);
        timerRandomSong.setRepeats(false);

        if (mPlayList.songToPlay()==null) {
            timerRandomSong.start();
        }

        getContentPane().add(mBackgroundImagePanel);

        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(resolution);
        setVisible(true);

        MediaPlayerManager sMediaPlayerManager = new MediaPlayerManager();

        mMediaPlayer.embeddedMediaPlayer.addMediaPlayerEventListener(sMediaPlayerManager);
        mMediaPlayer.embeddedMediaPlayerMp3.addMediaPlayerEventListener(sMediaPlayerManager);

        if(mUserSettings.isVideoPromocional()) {
            mMediaPlayer.embeddedMediaPlayer.playMedia(mUserSettings.getDireccionVideoPromocional());
            setFullScreen();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e)
            {

                //  isMetaDown = rigth CLick

                // Cuando se presiona click izquierdo y las canciones no se pueden cancelar

                if(mUserSettings.getClickCreditos()==0 && !e.isMetaDown() && !freeCredits)
                {
                    credits = credits + mUserSettings.getCantidadCreditos();
                    labelCredits.setText(String.format("Creditos: %d", credits));
                    agregarMonedasYCreditos();
                    labelCredits.setForeground(Color.WHITE);
                    if (isFullScreen) {
                        setFullScreen();
                    }

                    entregarPremiosYCreditosAdicionales();

                    // Click derecho y las canciones se pueden eliminar

                } else if (cancelSong && e.isMetaDown() && mPlayList.songToPlay()!=null) {

                    if (isFullScreen) {
                        setFullScreen();
                    }
                    counterClick++;
                    if (counterClick == 3) {

                        PasswordPanel passwordPanel = new PasswordPanel();
                        JOptionPane optionPane = new JOptionPane(passwordPanel, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        JDialog dlg = optionPane.createDialog("Eliminar Canción");

                        dlg.addWindowFocusListener(new WindowAdapter() {
                            @Override
                            public void windowGainedFocus(WindowEvent e) {
                                passwordPanel.gainedFocus();
                            }
                        });

                        dlg.setVisible(true);

                        if (optionPane.getValue()!=null && optionPane.getValue().equals(JOptionPane.OK_OPTION)) {
                            if (new String(passwordPanel.getPassword()).equals(mUserSettings.getPassword())) {
                                if (mMediaPlayer.embeddedMediaPlayerMp3.isPlaying()) {
                                    mMediaPlayer.embeddedMediaPlayerMp3.stop();
                                } else {
                                    mMediaPlayer.embeddedMediaPlayer.stop();
                                }
                                dlg.dispatchEvent(new WindowEvent(dlg, WindowEvent.WINDOW_CLOSING));
                                dlg.dispose(); // else java VM will wait for dialog to be disposed of (forever)
                            }
                        } else {
                            dlg.dispatchEvent(new WindowEvent(dlg, WindowEvent.WINDOW_CLOSING));
                            dlg.dispose(); // else java VM will wait for dialog to be disposed of (forever)
                        }

                        counterClick = 0;
                    }

                    // click derecho y las canciones no se pueden eliminar

                } else if (e.isMetaDown() && mUserSettings.getClickCreditos() == 1 && !freeCredits && !cancelSong) {

                    credits = credits + mUserSettings.getCantidadCreditos();
                    labelCredits.setText(String.format("Creditos: %d", credits));
                    agregarMonedasYCreditos();
                    labelCredits.setForeground(Color.WHITE);
                    if (isFullScreen) {
                        setFullScreen();
                    }
                    entregarPremiosYCreditosAdicionales();
                }
            }
        });

        mKeyboardManager = new KeyboardManager();

        this.addKeyListener(mKeyboardManager);

        bottomPanel.addKeyListener(mKeyboardManager);
    }

    private void initComponents() {

        credits = mUserSettings.getCreditosGuardados();

        resolution = Toolkit.getDefaultToolkit().getScreenSize();

        widthScreen = (int) resolution.getWidth();
        heightScreen = (int) (resolution.getHeight() - 54);

        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/mfrockola/imagenes/icono.png")));

        // Iniciar los labels

        labelMusicalGenre = new JLabel("Genero");
        labelMusicalGenre.setForeground(Color.WHITE);
        labelMusicalGenre.setFont(new Font("Calibri", Font.BOLD, 23));
        labelMusicalGenre.setBounds((int)(widthScreen/45.533), (int)(heightScreen/51.2), (int)(widthScreen/1.7603), 35);

        if (freeCredits) {
            labelCredits= new JLabel("Creditos Libres");
        } else {
            labelCredits = new JLabel(String.format("Creditos: %d", mUserSettings.getCreditosGuardados()));
        }

        labelCredits.setForeground(Color.WHITE);
        labelCredits.setFont(new Font("Calibri", Font.BOLD, 23));
        labelCredits.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        Icon log = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/nombre.png"));
        JLabel labelLogo = new JLabel(log);
        labelLogo.setHorizontalAlignment(SwingConstants.RIGHT);
        labelLogo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        labelSongPlayingRight = new JLabel("Su selección musical");
        labelSongPlayingRight.setForeground(Color.WHITE);
        labelSongPlayingRight.setFont(new Font("Calibri", Font.BOLD, 23));
        labelSongPlayingRight.setHorizontalAlignment(SwingConstants.CENTER);

        labelSongPlayingBottom = new JLabel();
        labelSongPlayingBottom.setText("MFRockola");
        labelSongPlayingBottom.setHorizontalAlignment(SwingConstants.CENTER);
        labelSongPlayingBottom.setForeground(Color.WHITE);
        labelSongPlayingBottom.setFont(new Font("Calibri", Font.BOLD, 23));

        Icon icon = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/promocionLabel.png"));
        labelPromotions = new JLabel("Aqui van las promociones",icon,JLabel.CENTER);
        labelPromotions.setVerticalTextPosition(JLabel.BOTTOM);
        labelPromotions.setHorizontalTextPosition(JLabel.CENTER);
        labelPromotions.setForeground(Color.BLACK);
        labelPromotions.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        labelPromotions.setFont(new Font("Calibri", Font.BOLD, 23));
        labelPromotions.setHorizontalAlignment(SwingConstants.CENTER);
        labelPromotions.setVisible(false);
        labelPromotions.setOpaque(true);
        labelPromotions.setBackground(Color.WHITE);
        labelPromotions.setBounds((widthScreen/2)-250,(heightScreen/2)-80,500,160);

        // Iniciar las listas

        listMusicData = new ListMusic(mUserSettings.getPathSongs(),mUserSettings.getPathVideosMp3()); //Aqui falta la direccion de los videos promocionales

        mSongListInterface = new JList();
        mSongListInterface.setCellRenderer(new RowRenderer(new Font(mUserSettings.getFontCeldasName(),
                mUserSettings.getFontCeldasNegrita(), mUserSettings.getFontCeldasSize()),mUserSettings.getFontCeldasColor(),
                mUserSettings.getColor1(), mUserSettings.getColor2()));
        mSongListInterface.setListData(listMusicData.getGenderSongs(0));
        mSongListInterface.addKeyListener(mKeyboardManager);
        mSongListInterface.setVisibleRowCount(20);
        mSongListInterface.setFocusable(false);
        mSongListInterface.setMaximumSize(getMaximumSize());

        mScrollPane = new JScrollPane(mSongListInterface);
        mScrollPane.setBounds((int)(widthScreen/45.533), (int)(heightScreen/12.8),(int)(widthScreen/1.7603), (int)(heightScreen/1.0924));
        mScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        playListInterface = new JList();
        playListInterface.setListData(mPlayList.getPlayList());
        playListInterface.setCellRenderer(new RowRenderer(new Font(mUserSettings.getFontCeldasName(),
                mUserSettings.getFontCeldasNegrita(), mUserSettings.getFontCeldasSize()),mUserSettings.getFontCeldasColor(),
                mUserSettings.getColor1(), mUserSettings.getColor2()));
        playListInterface.setBounds((int)(widthScreen/1.633), (int)(heightScreen/1.52), (int)(widthScreen/2.732), (int)(heightScreen/3.051));
//        listaDeReproduccion.setBounds(ancho - 530, alto - 260, 500, alto-461);
        playListInterface.setFocusable(false);

        labelMusicalGenre.setText("Genero Musical: "+ listMusicData.getNameOfGender());

        // Iniciar los panel

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(null);
        mainPanel.add(labelPromotions);
        mainPanel.add(mScrollPane);

        mainPanel.add(labelMusicalGenre);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(labelLogo,BorderLayout.EAST);

        mBackgroundImagePanel = new BackgroundImagePanel(mUserSettings.getDireccionFondo());
        mBackgroundImagePanel.setLayout(new BorderLayout());
        mBackgroundImagePanel.add(bottomPanel,BorderLayout.SOUTH);

        mBackgroundImagePanel.add(mainPanel,BorderLayout.CENTER);

        mMediaPlayer = new MediaPlayer();
        videoPanel = new JPanel();
        videoPanel.setLayout(new BorderLayout());
        videoPanel.setBounds((int)(widthScreen/1.633), (int)(heightScreen/16.340),(int)(widthScreen/2.732), (int)(heightScreen/2.7137));
        videoPanel.add(mMediaPlayer.getMediaPlayerContainer(),BorderLayout.CENTER);
        mainPanel.add(videoPanel);

        panel = new JPanel();
        panel.setOpaque(false);
        panel.setBounds((int)(widthScreen/1.633), (int)(heightScreen/2.2222), (int)(widthScreen/2.732), (int)(heightScreen/2.021));
        mainPanel.add(panel);
        panel.setLayout(new GridLayout(2, 1, 20, 0));

        bottomPanel.add(labelCredits,BorderLayout.WEST);
        bottomPanel.add(labelSongPlayingBottom, BorderLayout.CENTER);

        JPanel panelRight = new JPanel();
        panelRight.setOpaque(false);
        panel.add(panelRight);
        panelRight.setLayout(new GridLayout(3, 1, 0, 0));
        panelRight.add(labelSongPlayingRight);

        panelRight.add(mSongSelector.labelSelector);

        mainPanel.add(playListInterface);
    }

    private void setFullScreen()
    {
        if (!isFullScreen)
        {
            mScrollPane.setVisible(false);
            mSongListInterface.setVisible(false);
            panel.setVisible(false);
            labelMusicalGenre.setVisible(false);
            videoPanel.setBounds(0, 0, widthScreen, heightScreen);
            isFullScreen = true;
        }
        else
        {
            videoPanel.setBounds((int)(widthScreen/1.633), (int)(heightScreen/16.340),(int)(widthScreen/2.732), (int)(heightScreen/2.7137));
            mScrollPane.setVisible(true);
            mSongListInterface.setVisible(true);
            panel.setVisible(true);
            labelMusicalGenre.setVisible(true);
            isFullScreen = false;
        }
    }

    private void entregarPremiosYCreditosAdicionales() {
        // creditosInsertados es la variable de control de los clicks

        if (addAditionalCredit && !freeCredits) {
            insertedCredits++;
            if (insertedCredits >= mUserSettings.getCadaCantidadDeCreditos()) {
                // Aqui va la cuestion para controlar el cartel de los creditos adicionales
                insertedCredits = 0;
                credits = credits + mUserSettings.getNumeroDeCreditosAdicionales();
                labelCredits.setText(String.format("Creditos: %d", credits));
                labelPromotions.setText(String.format("Ganaste %s creditos adicionales",
                        mUserSettings.getNumeroDeCreditosAdicionales()));
                labelPromotions.setVisible(true);
            }
        }

        if (givePrizeSetting && !freeCredits) {
            insertedCreditsForPrize++;
            if (insertedCreditsForPrize % mUserSettings.getCantidadDeCreditosPorPremio() == 0) {
                // Aqui va la cuestion para controlar el cartel de premio
                labelPromotions.setText(String.format("Ganaste %s %s!", mUserSettings.getCantidadDePremios(),
                        mUserSettings.getTipoDePremio()));
                labelPromotions.setVisible(true);
                playSound();
            }
        }
    }

    private class KeyboardManager extends KeyAdapter
    {
        SQLiteConsultor consultor = new SQLiteConsultor();

        public void keyPressed(KeyEvent evento)
        {
            // tecla bloque numerico = 144
            if (evento.getKeyCode()==KeyEvent.VK_NUM_LOCK) {
                Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_NUM_LOCK,true);
            }
            if (evento.getKeyCode()==mUserSettings.getTeclaPantallaCompleta() && (credits > 0 || !mUserSettings.isLockScreen()))
            {
                if (labelPromotions.isVisible()) {
                    labelPromotions.setVisible(false);
                    if (promotionalSound!=null) {
                        promotionalSound.stop();
                        promotionalSound.close();
                    }
                } else {
                    setFullScreen();
                }
            }

            if(evento.getKeyCode()== mUserSettings.getTeclaBorrar() && mSongSelector.counterValue > 0)
            {
                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));
            }

            if ((evento.getKeyCode()==48 || evento.getKeyCode()==96) && (credits > 0 || !mUserSettings.isLockScreen()))
            {
                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==49 || evento.getKeyCode()==97) && (credits > 0 || !mUserSettings.isLockScreen()))
            {
                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==50 || evento.getKeyCode()==98) && (credits > 0 || !mUserSettings.isLockScreen()))
            {
                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==51 || evento.getKeyCode()==99) && (credits > 0 || !mUserSettings.isLockScreen()))
            {
                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==52 || evento.getKeyCode()==100) && (credits > 0 || !mUserSettings.isLockScreen()))
            {
                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==53 || evento.getKeyCode()==101) && (credits > 0 || !mUserSettings.isLockScreen()))
            {
                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==54 || evento.getKeyCode()==102) && (credits > 0 || !mUserSettings.isLockScreen()))
            {
                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==55 || evento.getKeyCode()==103) && (credits > 0 || !mUserSettings.isLockScreen()))
            {
                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==56 || evento.getKeyCode()==104) && (credits > 0 || !mUserSettings.isLockScreen()))
            {
                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==57 || evento.getKeyCode()==105) && (credits > 0 || !mUserSettings.isLockScreen()))
            {
                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));
            }
            else if (evento.getKeyCode()==77) {
                Thread ic = new Thread(new InternetConnection());
//                addSongToPlayList(ic.start());
            }

            if (mSongSelector.play)
            {
                int numero;
                boolean condicion = false;

                numero = Integer.parseInt(String.format("%s%s%s%s%s", mSongSelector.values[0],mSongSelector.values[1],
                        mSongSelector.values[2],mSongSelector.values[3],mSongSelector.values[4]));

                if (mBlockedSongs.checkBlockedSongs(numero))
                    condicion = true;
                else
                    condicion = false;

                if (numero >= listMusicData.getSizeListOfSongs()) {
                    labelCredits.setText("Musica no encontrada");
                    mSongSelector.play = false;
                    mSongSelector.resetValues();
                    mSongSelector.labelSelector.setText("- - - - -");
                    timerChangerLabelCredits.start();
                }
                else
                {
                    if ((credits > 0 && condicion)||(freeCredits && condicion))
                    {
                        if (mPlayList.songToPlay()==null)
                        {
                            if (timerRandomSong.isRunning()) {
                                timerRandomSong.stop();
                            }

                            Song cancionAReproducir =  listMusicData.getSong(numero);

                            mPlayList.addSong(cancionAReproducir);

                            int extension = Utils.getExtension(String.format("%s\\%s\\%s\\%s", mUserSettings.getPathSongs(),mPlayList.getSongGender(),mPlayList.getSinger(), mPlayList.songToPlay()));

                            if (extension == EXT_MP4 || extension == EXT_AVI || extension == EXT_MPG || extension == EXT_FLV || extension == EXT_MKV) {
                                mMediaPlayer.playVideo(mPlayList.getSongGender(),mPlayList.getSinger(),mPlayList.songToPlay());
                            } else if (extension == EXT_MP3 || extension == EXT_WMA || extension == EXT_WAV || extension == EXT_AAC) {
                                mMediaPlayer.playAudio(
                                        mPlayList.getSongGender(),
                                        mPlayList.getSinger(),
                                        mPlayList.songToPlay(),
                                        mUserSettings.getPathVideosMp3() + "\\" + listMusicData.getPromVideo());
                            }

                            playListInterface.setListData(mPlayList.getPlayList());
                            if (!freeCredits)
                            {
                                --credits;
                                abrirRegConfigEscritura();
                                agregarDatosRegConfig();
                                cerrarRegConfig();
                                labelCredits.setText(String.format("%s: %d","Creditos",credits));
                            }
                            mSongSelector.play = false;
                            mSongSelector.resetValues();
                            mSongSelector.labelSelector.setText("- - - - -");
                            mBlockedSongs.blockSong(numero);
                            labelSongPlayingBottom.setText(String.format("%05d - %s - %s - %s",
                                    mPlayList.getSongNumber(),
                                    mPlayList.getSongGender(),
                                    mPlayList.getSinger(),
                                    mPlayList.songToPlay()));
                            labelSongPlayingRight.setText(String.format("%05d - %s - %s - %s",
                                    mPlayList.getSongNumber(),mPlayList.getSongGender(),
                                    mPlayList.getSinger(), mPlayList.songToPlay()));
                            if (credits == 0 && !freeCredits) {
                                timerFullScreen.restart();
                            }

                            if (mUserSettings.isCreditosContinuos()) {
                                insertedCredits = 0;
                            }

                            updateDataBase(cancionAReproducir);
                        }
                        else
                        {
                            Song cancionAReproducir = listMusicData.getSong(numero);
                            //Song cancion = new Song(numero, cancionAReproducir);
                            mPlayList.addSong(cancionAReproducir);
                            playListInterface.setListData(mPlayList.getPlayList());
                            if (!freeCredits)
                            {
                                --credits;
                                abrirRegConfigEscritura();
                                agregarDatosRegConfig();
                                cerrarRegConfig();
                                labelCredits.setForeground(Color.WHITE);
                                labelCredits.setText(String.format("%s: %d","Creditos",credits));
                            }
                            mSongSelector.play = false;
                            mSongSelector.resetValues();
                            mSongSelector.labelSelector.setText("- - - - -");
                            mBlockedSongs.blockSong(numero);
                            if (credits == 0 && !freeCredits) {
                                timerFullScreen.restart();
                            }

                            if (mUserSettings.isCreditosContinuos()) {
                                insertedCredits = 0;
                            }

                            updateDataBase(cancionAReproducir);
                        }
                    }
                    else
                    {
                        labelCredits.setForeground(Color.RED);
                        labelCredits.setText("La canción que ha seleccionado no se puede reproducir antes de " + mUserSettings.getReinicioMusicas() +" mins");
                        timerChangerLabelCredits.start();
                        mSongSelector.play = false;
                        mSongSelector.resetValues();
                        mSongSelector.labelSelector.setText("- - - - -");
                    }


                }
            }
            else if (evento.getKeyCode()==122) {
                String contrasenia = JOptionPane.showInputDialog("Introduzca la clave");
                if (contrasenia.equals("12345")) {
                    if (!freeCredits) {
                        freeCredits = true;
                        labelCredits.setText("Creditos: Libres");
                    } else {
                        freeCredits = false;
                        labelCredits.setText(String.format("Creditos: %d", credits));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña Incorrecta");
                }
            } else if (evento.getKeyCode()==123) {
                @SuppressWarnings("unused")
                SettingsWindow config = new SettingsWindow();
            } else if (evento.getKeyCode()==mUserSettings.getTeclaBajarLista() && (credits > 0 || !mUserSettings.isLockScreen())) {
                if (isFullScreen) {
                    setFullScreen();
                }

                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));

                labelPromotions.setVisible(false);

                if (mSongListInterface.getSelectedIndex() - 20 < 0) {
                    if (mSongListInterface.getSelectedIndex() == 0) {
                        if (listMusicData.downGender()) {
                            mSongListInterface.setListData(listMusicData.getGenderSongs(listMusicData.getSelectedGender()));
                            mSongListInterface.setSelectedIndex(listMusicData.getGenderSongs(listMusicData.getSelectedGender()).length-1);
                            mSongListInterface.ensureIndexIsVisible(listMusicData.getGenderSongs(listMusicData.getSelectedGender()).length-1);
                            labelMusicalGenre.setText("Genero Musical: " + listMusicData.getNameOfGender());
                        }
                    } else {
                        mSongListInterface.setSelectedIndex(0);
                        mSongListInterface.ensureIndexIsVisible(0);
                    }
                } else {
                    mSongListInterface.setSelectedIndex(mSongListInterface.getSelectedIndex()-20);
                    mSongListInterface.ensureIndexIsVisible(mSongListInterface.getSelectedIndex());
                }
            } else if (evento.getKeyCode()==mUserSettings.getTeclaSubirLista() && (credits > 0 || !mUserSettings.isLockScreen())) {
                if (isFullScreen) {
                    setFullScreen();
                }

                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));

                labelPromotions.setVisible(false);

                if(mSongListInterface.getSelectedIndex()+20 > listMusicData.getGenderSongs(listMusicData.getSelectedGender()).length) {
                    if (mSongListInterface.getSelectedIndex()==listMusicData.getGenderSongs(listMusicData.getSelectedGender()).length-1) {
                        if (listMusicData.upGender()) {
                            mSongListInterface.setListData(listMusicData.getGenderSongs(listMusicData.getSelectedGender()));
                            mSongListInterface.setSelectedIndex(0);
                            mSongListInterface.ensureIndexIsVisible(0);
                            labelMusicalGenre.setText("Genero Musical: " + listMusicData.getNameOfGender());
                        }
                    } else {
                        mSongListInterface.setSelectedIndex(listMusicData.getGenderSongs(listMusicData.getSelectedGender()).length-1);
                        mSongListInterface.ensureIndexIsVisible(mSongListInterface.getSelectedIndex());
                    }
                } else {
                    mSongListInterface.setSelectedIndex(mSongListInterface.getSelectedIndex()+20);
                    mSongListInterface.ensureIndexIsVisible(mSongListInterface.getSelectedIndex());
                }
            } else if (evento.getKeyCode() == mUserSettings.getTeclaSubirGenero() && (credits > 0 || !mUserSettings.isLockScreen())) {
                if (isFullScreen) {
                    setFullScreen();
                }

                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));
                if (listMusicData.upGender()) {
                    labelPromotions.setVisible(false);
                    mSongListInterface.setListData(listMusicData.getGenderSongs(listMusicData.getSelectedGender()));
                    mSongListInterface.setSelectedIndex(0);
                    mSongListInterface.ensureIndexIsVisible(0);
                    labelMusicalGenre.setText("Genero Musical: " + listMusicData.getNameOfGender());
                }
            } else if (evento.getKeyCode() == mUserSettings.getTeclaBajarGenero() && (credits > 0 || !mUserSettings.isLockScreen())) {
                if (isFullScreen) {
                    setFullScreen();
                }

                mSongSelector.labelSelector.setText(mSongSelector.keyEventHandler(evento));

                if (listMusicData.downGender()) {
                    labelPromotions.setVisible(false);
                    mSongListInterface.setListData(listMusicData.getGenderSongs(listMusicData.getSelectedGender()));
                    mSongListInterface.setSelectedIndex(0);
                    mSongListInterface.ensureIndexIsVisible(0);
                    labelMusicalGenre.setText("Genero Musical: " + listMusicData.getNameOfGender());
                }
            } else if (evento.getKeyCode()==mUserSettings.getTeclaSaltarCancion() && mPlayList.songToPlay()!=null) {
                if (mMediaPlayer.embeddedMediaPlayerMp3.isPlaying()) {
                    mMediaPlayer.embeddedMediaPlayerMp3.stop();
                } else {
                    mMediaPlayer.embeddedMediaPlayer.stop();
                }
            }
            else if (evento.getKeyCode()==mUserSettings.getTeclaAgregarCredito()) {
                credits = credits + mUserSettings.getCantidadCreditos();
                labelCredits.setText(String.format("Creditos: %d", credits));
                agregarMonedasYCreditos();
                labelCredits.setForeground(Color.WHITE);
                if (isFullScreen) {
                    setFullScreen();
                }

                entregarPremiosYCreditosAdicionales();
            } else if (evento.getKeyCode()==mUserSettings.getTeclaBorrarCredito() && credits > 0) {
                --credits;
                abrirRegConfigEscritura();
                agregarDatosRegConfig();
                cerrarRegConfig();
                labelCredits.setText(String.format("Creditos: %d", credits));
                if (credits == 0 && !freeCredits) {
                    timerFullScreen.restart();
                }
            }
        }

        private void updateDataBase(Song cancionAReproducir) {
            try {
                String consulta = "SELECT * FROM most_popular WHERE number = " + cancionAReproducir.getSongNumber();

                ResultSet resultSet = consultor.query(consulta);

                if (resultSet.isClosed()) {
                    resultSet.close();

                    String insertar = "INSERT INTO most_popular(number, name, artist, genre, times, last_date)" +
                            " VALUES ("+cancionAReproducir.getSongNumber()+",'" +
                            cancionAReproducir.getSongName()+ "','" +
                            cancionAReproducir.getSinger() + "','" +
                            cancionAReproducir.getSongGenre() + "'," +
                            1 + ", "+ new Date().getTime() +");";

                    consultor.insert(insertar);
                } else {

                    int times = 0;
                    while (resultSet.next()) {
                        times = resultSet.getInt("times") + 1;
                    }

                    resultSet.close();

                    consultor.update("UPDATE most_popular SET times = " + times + ", last_date = " + new Date().getTime() + " WHERE number = " + cancionAReproducir.getSongNumber());

                    consultor.closeConnection();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class MediaPlayerManager extends MediaPlayerEventAdapter
    {
        @Override
        public void stopped(uk.co.caprica.vlcj.player.MediaPlayer mediaPlayer) {
            nextSong();
        }

        public void finished(uk.co.caprica.vlcj.player.MediaPlayer mediaPlayer) {
            if (mMediaPlayer.embeddedMediaPlayerMp3.isPlaying()) {
                mMediaPlayer.embeddedMediaPlayer.playMedia(mUserSettings.getPathVideosMp3() + "\\" + listMusicData.getPromVideo());
            } else {
                nextSong();
            }
        }

        public void nextSong() {
            mPlayList.removeSong();
            playListInterface.setListData(mPlayList.getPlayList());

            if (mPlayList.songToPlay() == null) {
                timerRandomSong.start();
                if (mUserSettings.isVideoPromocional()) {
                    mMediaPlayer.embeddedMediaPlayer.playMedia(mUserSettings.getDireccionVideoPromocional());
                    labelSongPlayingBottom.setText("MFRockola");
                    labelSongPlayingRight.setText("Su selección musical");
                } else {
                    labelSongPlayingBottom.setText("MFRockola");
                    labelSongPlayingRight.setText("Su selección musical");
                }
            } else {
                int extension = Utils.getExtension(String.format("%s\\%s\\%s\\%s", mUserSettings.getPathSongs(),mPlayList.getSongGender(),mPlayList.getSinger(), mPlayList.songToPlay()));

                if (extension == EXT_MP4 || extension == EXT_AVI || extension == EXT_MPG) {
                    mMediaPlayer.playVideo(
                            mPlayList.getSongGender(),
                            mPlayList.getSinger(),
                            mPlayList.songToPlay());

                } else if (extension == EXT_MP3 || extension == EXT_WMA || extension == EXT_WAV || extension == EXT_AAC) {
                    mMediaPlayer.playAudio(
                            mPlayList.getSongGender(),
                            mPlayList.getSinger(),
                            mPlayList.songToPlay(),
                            mUserSettings.getPathVideosMp3() + "\\" + listMusicData.getPromVideo());
                }

                labelSongPlayingBottom.setText(String.format("%05d - %s - %s - %s",
                        mPlayList.getSongNumber(),
                        mPlayList.getSongGender(),
                        mPlayList.getSinger(),
                        mPlayList.songToPlay()));

                labelSongPlayingRight.setText(String.format("%05d - %s - %s - %s",
                        mPlayList.getSongNumber(),
                        mPlayList.getSongGender(),
                        mPlayList.getSinger(),
                        mPlayList.songToPlay()));
            }
        }
    }

    public void abrirRegConfigEscritura() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream("config.mfr"));
        } catch(IOException ioExcepcion) {
            System.err.println("Error al abrir el archivo.");
        }
    }

    public void agregarDatosRegConfig() {
        UserSettings configuraciones;

        try {
            configuraciones = new UserSettings(
                    this.mUserSettings.getPathSongs(),
                    this.mUserSettings.getPathVideosMp3(),
                    this.mUserSettings.getPathVlc(),
                    this.mUserSettings.getDireccionVideoPromocional(),
                    this.mUserSettings.getMusicAleatoria(),
                    this.mUserSettings.getReinicioMusicas(),
                    this.mUserSettings.getCantidadCreditos(),
                    this.mUserSettings.isLibre(),
                    this.mUserSettings.isLockScreen(),
                    this.mUserSettings.isVideoPromocional(),
                    this.mUserSettings.isDefaultPromotionalVideo(),
                    this.mUserSettings.getClickCreditos(),
                    this.mUserSettings.getTeclaSubirLista(),
                    this.mUserSettings.getTeclaBajarLista(),
                    this.mUserSettings.getTeclaSubirGenero(),
                    this.mUserSettings.getTeclaBajarGenero(),
                    this.mUserSettings.getTeclaPantallaCompleta(),
                    this.mUserSettings.getTeclaBorrar(),
                    this.mUserSettings.getTeclaSaltarCancion(),
                    this.mUserSettings.getTeclaAgregarCredito(),
                    this.mUserSettings.getTeclaBorrarCredito(),
                    this.mUserSettings.isCancelMusic(),
                    this.mUserSettings.getPassword(),
                    creditosASubir,
                    monedasASubir,
                    this.mUserSettings.isDefaultBackground(),
                    this.mUserSettings.getDireccionFondo(),
                    this.mUserSettings.getColor1(),
                    this.mUserSettings.getColor2(),
                    this.mUserSettings.getFontCeldasName(),
                    this.mUserSettings.getFontCeldasSize(),
                    this.mUserSettings.getFontSelectorSize(),
                    this.mUserSettings.getFontCeldasColor(),
                    this.mUserSettings.getFontCeldasNegrita(),
                    this.mUserSettings.isAgregarAdicional(),
                    this.mUserSettings.getNumeroDeCreditosAdicionales(),
                    this.mUserSettings.getCadaCantidadDeCreditos(),
                    this.mUserSettings.isCreditosContinuos(),
                    this.mUserSettings.isEntregarPremio(),
                    this.mUserSettings.getCantidadDePremios(),
                    this.mUserSettings.getCantidadDeCreditosPorPremio(),
                    this.mUserSettings.getTipoDePremio(),
                    credits
            );

            outputStream.writeObject(configuraciones);
        } catch (IOException excepcion) {
            System.err.println("Error al escribir el archivo");
        }
    }

    public void cerrarRegConfig() {
        try {
            if (outputStream != null)
                outputStream.close();

            outputStream = null;
        }
        catch (IOException ioExcepcion) {
            System.err.println("Error al cerrar el archivo");
            System.exit(1);
        }
    }

    public void agregarMonedasYCreditos() {
        timerFullScreen.stop();
        creditosASubir = creditosASubir + mUserSettings.getCantidadCreditos();
        monedasASubir++;
        abrirRegConfigEscritura();
        agregarDatosRegConfig();
        cerrarRegConfig();
    }

    public void playSound() {
        try {
            BufferedInputStream bis = new BufferedInputStream(getClass().getResourceAsStream("/com/mfrockola/sounds/felicitaciones.wav"));
            AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
            promotionalSound = AudioSystem.getClip();
            promotionalSound.open(ais);
            promotionalSound.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSongToPlayList(ArrayList numbers) {
        if (numbers.size()>0) {
            for (int i = 0; i < numbers.size(); i++) {
                Song cancionAReproducir = listMusicData.getSong((int) numbers.get(i));
                //Song cancion = new Song(numero, cancionAReproducir);
                mPlayList.addSong(cancionAReproducir);

            }
        }
        playListInterface.setListData(mPlayList.getPlayList());
    }
}
