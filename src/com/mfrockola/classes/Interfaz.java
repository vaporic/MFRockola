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
 * Created by Angel C on 17/04/2016.
 */
public class Interfaz extends JFrame
{
    private ObjectOutputStream salida;
    private UserSettingsManagement mUserSettingsManagement = new UserSettingsManagement();
    private UserSettings mUserSettings;

    private int creditos;
    private int ancho;
    private int alto;

    private MusicasProhibidas prohibir; // Objeto de musicas que no se pueden repetir.
    private ListaDeReproduccion listaReproduccion = new ListaDeReproduccion(); // Objeto de las musicas en reproduccion.

    private boolean isFullScreen = false; // Objeto que determina si se entra o no en pantalla completa.

    private JEImagePanel panelFondo;
    private JPanel contenedorPrincipal;
    private JPanel contenedorVideo;
    private JPanel panel;
    private JPanel panelInferior;

    private Dimension resolucion; // Objeto para determinar la resolucion de la pantalla

    @SuppressWarnings("rawtypes")
    private JList listaDeMusicas; // JList para colocar el listado de los videos disponibles en el directorio.
    private ListMusic listMusic;
    private JList listaDeReproduccion; // JList para colocar el listado de los videos en reproduccion.

    // JLabels

    private JLabel labelGeneroMusical;
    private JLabel labelMusica = new JLabel();
    private JLabel labelCancionEnRepro;
    private JLabel labelcreditos;
    private JLabel labelLogo;
    private JLabel labelPromociones;

    private MediaPlayer repro;

    private SongSelector objeto;

    private int monedasASubir;
    private int creditosASubir;

    private boolean cancelMusic;
    private boolean creditosLibres;
    private int countClickCancelMusic;
    private boolean agregarCreditoAdicional;
    private int creditosInsertados;
    private boolean entregarPremio;
    private int creditosInsertadosParaPremio;

    private JScrollPane barras;
    private Timer timerChangerLblCredits;
    private Timer timerFullScreen;
    private Timer timer;

    private ManejadorDeTeclas manejadorDeTeclas;

    private Clip sound;

    @SuppressWarnings("unchecked")
    public Interfaz()
    {
        try
        {
            mUserSettingsManagement.openUserSettings();
            mUserSettings = mUserSettingsManagement.readUserSettings();
            prohibir = new MusicasProhibidas(mUserSettings.getReinicioMusicas());
            objeto = new SongSelector(mUserSettings.getTeclaBorrar(), mUserSettings.getTeclaSubirLista(),
                    mUserSettings.getTeclaBajarLista(), mUserSettings.getTeclaSubirGenero(),
                    mUserSettings.getTeclaBajarGenero(),mUserSettings.getFontSelectorSize());
            monedasASubir = mUserSettings.getCantidadMonedasInsertadas();
            creditosASubir = mUserSettings.getCantidadCreditosUsados();
            cancelMusic = mUserSettings.isCancelMusic();
            creditosLibres = mUserSettings.isLibre();
            agregarCreditoAdicional = mUserSettings.isAgregarAdicional();
            entregarPremio = mUserSettings.isEntregarPremio();
        }
        catch (NullPointerException excepcion)
        {
            @SuppressWarnings("unused")
            SettingsWindow configurar = new SettingsWindow();
        }

        initComponents();

        ActionListener changeLblCredits = new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                if (creditosLibres) {
                    labelcreditos.setText("Creditos Libres");
                } else {
                    labelcreditos.setText(String.format("Creditos: %d", creditos));
                }

                labelcreditos.setForeground(Color.WHITE);
            }
        };

        timerChangerLblCredits = new Timer(5000, changeLblCredits);
        timerChangerLblCredits.setRepeats(false);

        ActionListener changeFullScreen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isFullScreen) {
                    pantallaCompleta();

                    timer.restart();
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

        getContentPane().add(panelFondo);

        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(resolucion);
        setVisible(true);

        ManejadorDeReproductor manejadorDeReproductor = new ManejadorDeReproductor();

        repro.embeddedMediaPlayer.addMediaPlayerEventListener(manejadorDeReproductor);
        repro.embeddedMediaPlayerMp3.addMediaPlayerEventListener(manejadorDeReproductor);

        if(mUserSettings.isVideoPromocional()) {
            repro.embeddedMediaPlayer.playMedia(mUserSettings.getDireccionVideoPromocional());
            pantallaCompleta();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e)
            {

                //  isMetaDown = rigth CLick

                // Cuando se presiona click izquierdo y las canciones no se pueden cancelar

                if(mUserSettings.getClickCreditos()==0 && !e.isMetaDown() && !creditosLibres)
                {
                    creditos = creditos + mUserSettings.getCantidadCreditos();
                    labelcreditos.setText(String.format("Creditos: %d", creditos));
                    agregarMonedasYCreditos();
                    labelcreditos.setForeground(Color.WHITE);
                    if (isFullScreen) {
                        pantallaCompleta();
                    }

                    entregarPremiosYCreditosAdicionales();

                    // Click derecho y las canciones se pueden eliminar

                } else if (cancelMusic && e.isMetaDown() && listaReproduccion.obtenerCancionAReproducir()!=null) {

                    if (isFullScreen) {
                        pantallaCompleta();
                    }
                    countClickCancelMusic++;
                    if (countClickCancelMusic == 3) {

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
                                if (repro.embeddedMediaPlayerMp3.isPlaying()) {
                                    repro.embeddedMediaPlayerMp3.stop();
                                } else {
                                    repro.embeddedMediaPlayer.stop();
                                }
                                dlg.dispatchEvent(new WindowEvent(dlg, WindowEvent.WINDOW_CLOSING));
                                dlg.dispose(); // else java VM will wait for dialog to be disposed of (forever)
                            }
                        } else {
                            dlg.dispatchEvent(new WindowEvent(dlg, WindowEvent.WINDOW_CLOSING));
                            dlg.dispose(); // else java VM will wait for dialog to be disposed of (forever)
                        }

                        countClickCancelMusic = 0;
                    }

                    // click derecho y las canciones no se pueden eliminar

                } else if (e.isMetaDown() && mUserSettings.getClickCreditos() == 1 && !creditosLibres && !cancelMusic) {

                    creditos = creditos + mUserSettings.getCantidadCreditos();
                    labelcreditos.setText(String.format("Creditos: %d", creditos));
                    agregarMonedasYCreditos();
                    labelcreditos.setForeground(Color.WHITE);
                    if (isFullScreen) {
                        pantallaCompleta();
                    }
                    entregarPremiosYCreditosAdicionales();
                }
            }
        });

        manejadorDeTeclas = new ManejadorDeTeclas();

        this.addKeyListener(manejadorDeTeclas);

        panelInferior.addKeyListener(manejadorDeTeclas);
    }

    public void initComponents() {

        creditos = mUserSettings.getCreditosGuardados();

        resolucion = Toolkit.getDefaultToolkit().getScreenSize();

        ancho = (int) resolucion.getWidth();
        alto = (int) (resolucion.getHeight() - 54);

        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/mfrockola/imagenes/icono.png")));

        // Iniciar los labels

        labelGeneroMusical = new JLabel("Genero");
        labelGeneroMusical.setForeground(Color.WHITE);
        labelGeneroMusical.setFont(new Font("Calibri", Font.BOLD, 23));
        labelGeneroMusical.setBounds((int)(ancho/45.533), (int)(alto/51.2), (int)(ancho/1.7603), 35);

        if (creditosLibres) {
            labelcreditos= new JLabel("Creditos Libres");
        } else {
            labelcreditos = new JLabel(String.format("Creditos: %d", mUserSettings.getCreditosGuardados()));
        }

        labelcreditos.setForeground(Color.WHITE);
        labelcreditos.setFont(new Font("Calibri", Font.BOLD, 23));
        labelcreditos.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        Icon log = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/nombre.png"));
        labelLogo = new JLabel(log);
        labelLogo.setHorizontalAlignment(SwingConstants.RIGHT);
        labelLogo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        labelCancionEnRepro = new JLabel("MFRockola");
        labelCancionEnRepro.setForeground(Color.WHITE);
        labelCancionEnRepro.setFont(new Font("Calibri", Font.BOLD, 23));
        labelCancionEnRepro.setHorizontalAlignment(SwingConstants.CENTER);

        Icon icon = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/promocionLabel.png"));
        labelPromociones = new JLabel("Aqui van las promociones",icon,JLabel.CENTER);
        labelPromociones.setVerticalTextPosition(JLabel.BOTTOM);
        labelPromociones.setHorizontalTextPosition(JLabel.CENTER);
        labelPromociones.setForeground(Color.BLACK);
        labelPromociones.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        labelPromociones.setFont(new Font("Calibri", Font.BOLD, 23));
        labelPromociones.setHorizontalAlignment(SwingConstants.CENTER);
        labelPromociones.setVisible(false);
        labelPromociones.setOpaque(true);
        labelPromociones.setBackground(Color.WHITE);
        labelPromociones.setBounds((ancho/2)-250,(alto/2)-80,500,160);

        // Iniciar las listas

        listMusic = new ListMusic(mUserSettings.getDireccionVideos(),mUserSettings.getDireccionVideosMp3()); //Aqui falta la direccion de los videos promocionales

        listaDeMusicas = new JList();
        listaDeMusicas.setCellRenderer(new ModificadorDeCeldas(new Font(mUserSettings.getFontCeldasName(),
                mUserSettings.getFontCeldasNegrita(), mUserSettings.getFontCeldasSize()),mUserSettings.getFontCeldasColor(),
                mUserSettings.getColor1(), mUserSettings.getColor2()));
        listaDeMusicas.setListData(listMusic.getGenderSongs(0));
        listaDeMusicas.addKeyListener(manejadorDeTeclas);
        listaDeMusicas.setVisibleRowCount(20);
        listaDeMusicas.setFocusable(false);
        listaDeMusicas.setMaximumSize(getMaximumSize());

        barras = new JScrollPane(listaDeMusicas);
        barras.setBounds((int)(ancho/45.533), (int)(alto/12.8),(int)(ancho/1.7603), (int)(alto/1.0924));
        barras.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        barras.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        listaDeReproduccion = new JList();
        listaDeReproduccion.setListData(listaReproduccion.obtenerCancionesEnLista());
        listaDeReproduccion.setCellRenderer(new ModificadorDeCeldas(new Font(mUserSettings.getFontCeldasName(),
                mUserSettings.getFontCeldasNegrita(), mUserSettings.getFontCeldasSize()),mUserSettings.getFontCeldasColor(),
                mUserSettings.getColor1(), mUserSettings.getColor2()));
        listaDeReproduccion.setBounds((int)(ancho/1.633), (int)(alto/1.52), (int)(ancho/2.732), (int)(alto/3.051));
//        listaDeReproduccion.setBounds(ancho - 530, alto - 260, 500, alto-461);
        listaDeReproduccion.setFocusable(false);

        labelGeneroMusical.setText("Genero Musical: "+ listMusic.getNameOfGender());

        // Iniciar los panel

        contenedorPrincipal = new JPanel();
        contenedorPrincipal.setOpaque(false);
        contenedorPrincipal.setLayout(null);
        contenedorPrincipal.add(labelPromociones);
        contenedorPrincipal.add(barras);

        contenedorPrincipal.add(labelGeneroMusical);

        panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.setOpaque(false);
        panelInferior.add(labelLogo,BorderLayout.EAST);

        panelFondo = new JEImagePanel(mUserSettings.getDireccionFondo());
        panelFondo.setLayout(new BorderLayout());
        panelFondo.add(panelInferior,BorderLayout.SOUTH);

        panelInferior.add(labelCancionEnRepro, BorderLayout.CENTER);

        panelFondo.add(contenedorPrincipal,BorderLayout.CENTER);

        repro = new MediaPlayer();
        contenedorVideo = new JPanel();
        contenedorVideo.setLayout(new BorderLayout());
        contenedorVideo.setBounds((int)(ancho/1.633), (int)(alto/16.340),(int)(ancho/2.732), (int)(alto/2.7137));
        contenedorVideo.add(repro.getMediaPlayerContainer(),BorderLayout.CENTER);
        contenedorPrincipal.add(contenedorVideo);

        panel = new JPanel();
        panel.setOpaque(false);
        panel.setBounds((int)(ancho/1.633), (int)(alto/2.2222), (int)(ancho/2.732), (int)(alto/2.021));
        contenedorPrincipal.add(panel);
        panel.setLayout(new GridLayout(2, 1, 20, 0));

        panelInferior.add(labelcreditos,BorderLayout.WEST);

        JPanel panel_1 = new JPanel();
        panel_1.setOpaque(false);
        panel.add(panel_1);
        panel_1.setLayout(new GridLayout(3, 1, 0, 0));

        labelMusica.setText("MFRockola");
        labelMusica.setHorizontalAlignment(SwingConstants.CENTER);
        labelMusica.setForeground(Color.WHITE);
        labelMusica.setFont(new Font("Calibri", Font.BOLD, 23));
        panel_1.add(labelMusica);

        panel_1.add(objeto.labelSelector);

        contenedorPrincipal.add(listaDeReproduccion);
    }

    private void pantallaCompleta()
    {
        if (!isFullScreen)
        {
            barras.setVisible(false);
            listaDeMusicas.setVisible(false);
            panel.setVisible(false);
            labelGeneroMusical.setVisible(false);
            contenedorVideo.setBounds(0, 0, ancho, alto);
            isFullScreen = true;
        }
        else
        {
            contenedorVideo.setBounds((int)(ancho/1.633), (int)(alto/16.340),(int)(ancho/2.732), (int)(alto/2.7137));
            barras.setVisible(true);
            listaDeMusicas.setVisible(true);
            panel.setVisible(true);
            labelGeneroMusical.setVisible(true);
            isFullScreen = false;
        }
    }

    private void entregarPremiosYCreditosAdicionales() {
        // creditosInsertados es la variable de control de los clicks

        if (agregarCreditoAdicional && !creditosLibres) {
            creditosInsertados++;
            if (creditosInsertados >= mUserSettings.getCadaCantidadDeCreditos()) {
                // Aqui va la cuestion para controlar el cartel de los creditos adicionales
                creditosInsertados = 0;
                creditos = creditos + mUserSettings.getNumeroDeCreditosAdicionales();
                labelcreditos.setText(String.format("Creditos: %d", creditos));
                labelPromociones.setText(String.format("Ganaste %s creditos adicionales",
                        mUserSettings.getNumeroDeCreditosAdicionales()));
                labelPromociones.setVisible(true);
            }
        }

        if (entregarPremio && !creditosLibres) {
            creditosInsertadosParaPremio++;
            if (creditosInsertadosParaPremio % mUserSettings.getCantidadDeCreditosPorPremio() == 0) {
                // Aqui va la cuestion para controlar el cartel de premio
                labelPromociones.setText(String.format("Ganaste %s %s!", mUserSettings.getCantidadDePremios(),
                        mUserSettings.getTipoDePremio()));
                labelPromociones.setVisible(true);
                playSound();
            }
        }
    }

    private class ManejadorDeTeclas extends KeyAdapter
    {
        SQLiteConsultor consultor = new SQLiteConsultor();

        public void keyPressed(KeyEvent evento)
        {
            // tecla bloque numerico = 144
            if (evento.getKeyCode()==KeyEvent.VK_NUM_LOCK) {
                Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_NUM_LOCK,true);
            }
            if (evento.getKeyCode()==mUserSettings.getTeclaPantallaCompleta() && creditos > 0)
            {
                if (labelPromociones.isVisible()) {
                    labelPromociones.setVisible(false);
                    if (sound!=null) {
                        sound.stop();
                        sound.close();
                    }
                } else {
                    pantallaCompleta();
                }
            }

            if(evento.getKeyCode()== mUserSettings.getTeclaBorrar() && objeto.counterValue > 0)
            {
                objeto.labelSelector.setText(objeto.keyEventHandler(evento));
            }

            if ((evento.getKeyCode()==48 || evento.getKeyCode()==96) && creditos > 0)
            {
                objeto.labelSelector.setText(objeto.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==49 || evento.getKeyCode()==97) && creditos > 0)
            {
                objeto.labelSelector.setText(objeto.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==50 || evento.getKeyCode()==98) && creditos > 0)
            {
                objeto.labelSelector.setText(objeto.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==51 || evento.getKeyCode()==99) && creditos > 0)
            {
                objeto.labelSelector.setText(objeto.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==52 || evento.getKeyCode()==100) && creditos > 0)
            {
                objeto.labelSelector.setText(objeto.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==53 || evento.getKeyCode()==101) && creditos > 0)
            {
                objeto.labelSelector.setText(objeto.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==54 || evento.getKeyCode()==102) && creditos > 0)
            {
                objeto.labelSelector.setText(objeto.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==55 || evento.getKeyCode()==103) && creditos > 0)
            {
                objeto.labelSelector.setText(objeto.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==56 || evento.getKeyCode()==104) && creditos > 0)
            {
                objeto.labelSelector.setText(objeto.keyEventHandler(evento));
            }
            else if ((evento.getKeyCode()==57 || evento.getKeyCode()==105) && creditos > 0)
            {
                objeto.labelSelector.setText(objeto.keyEventHandler(evento));
            }
            else if (evento.getKeyCode()==77) {
                Thread ic = new Thread(new InternetConnection());
//                addSongToPlayList(ic.start());
            }

            if (objeto.play)
            {
                int numero;
                boolean condicion = false;

                numero = Integer.parseInt(String.format("%s%s%s%s", objeto.values[0],objeto.values[1],
                        objeto.values[2],objeto.values[3]));

                if (prohibir.revisarProhibido(numero))
                    condicion = true;
                else
                    condicion = false;

                if (numero >= listMusic.getSizeListOfSongs()) {
                    labelcreditos.setText("Musica no encontrada");
                    objeto.play = false;
                    objeto.resetValues();
                    objeto.labelSelector.setText("- - - -");
                    timerChangerLblCredits.start();
                }
                else
                {
                    if ((creditos > 0 && condicion)||(creditosLibres && condicion))
                    {
                        if (listaReproduccion.obtenerCancionAReproducir()==null)
                        {
                            Song cancionAReproducir =  listMusic.getSong(numero);

                            listaReproduccion.agregarCanciones(cancionAReproducir);

                            int extension = Utils.getExtension(String.format("%s\\%s\\%s\\%s", mUserSettings.getDireccionVideos(),listaReproduccion.obtenerGenero(),listaReproduccion.obtenerArtista(), listaReproduccion.obtenerCancionAReproducir()));

                            if (extension == EXT_MP4 || extension == EXT_AVI || extension == EXT_MPG || extension == EXT_FLV) {
                                repro.playVideo(listaReproduccion.obtenerGenero(),listaReproduccion.obtenerArtista(),listaReproduccion.obtenerCancionAReproducir());
                            } else if (extension == EXT_MP3 || extension == EXT_WMA || extension == EXT_WAV || extension == EXT_AAC) {
                                repro.playAudio(
                                        listaReproduccion.obtenerGenero(),
                                        listaReproduccion.obtenerArtista(),
                                        listaReproduccion.obtenerCancionAReproducir(),
                                        mUserSettings.getDireccionVideosMp3() + "\\" + listMusic.getPromVideo());
                            }

                            listaDeReproduccion.setListData(listaReproduccion.obtenerCancionesEnLista());
                            if (!creditosLibres)
                            {
                                --creditos;
                                abrirRegConfigEscritura();
                                agregarDatosRegConfig();
                                cerrarRegConfig();
                                labelcreditos.setText(String.format("%s: %d","Creditos",creditos));
                            }
                            objeto.play = false;
                            objeto.resetValues();
                            objeto.labelSelector.setText("- - - -");
                            prohibir.agregarProhibido(numero);
                            labelMusica.setText(String.format("%04d - %s - %s - %s",listaReproduccion.obtenerNumero(),
                                    listaReproduccion.obtenerGenero(),
                                    listaReproduccion.obtenerArtista(),
                                    listaReproduccion.obtenerCancionAReproducir()));
                            labelCancionEnRepro.setText(String.format("%04d - %s - %s - %s",
                                    listaReproduccion.obtenerNumero(),listaReproduccion.obtenerGenero(),
                                    listaReproduccion.obtenerArtista(), listaReproduccion.obtenerCancionAReproducir()));
                            if (creditos == 0 && !creditosLibres) {
                                timerFullScreen.restart();
                            }

                            if (mUserSettings.isCreditosContinuos()) {
                                creditosInsertados = 0;
                            }

                            updateDB(cancionAReproducir);
                        }
                        else
                        {
                            Song cancionAReproducir = listMusic.getSong(numero);
                            //Song cancion = new Song(numero, cancionAReproducir);
                            listaReproduccion.agregarCanciones(cancionAReproducir);
                            listaDeReproduccion.setListData(listaReproduccion.obtenerCancionesEnLista());
                            if (!creditosLibres)
                            {
                                --creditos;
                                abrirRegConfigEscritura();
                                agregarDatosRegConfig();
                                cerrarRegConfig();
                                labelcreditos.setForeground(Color.WHITE);
                                labelcreditos.setText(String.format("%s: %d","Creditos",creditos));
                            }
                            objeto.play = false;
                            objeto.resetValues();
                            objeto.labelSelector.setText("- - - -");
                            prohibir.agregarProhibido(numero);
                            if (creditos == 0 && !creditosLibres) {
                                timerFullScreen.restart();
                            }

                            if (mUserSettings.isCreditosContinuos()) {
                                creditosInsertados = 0;
                            }

                            updateDB(cancionAReproducir);
                        }
                    }
                    else
                    {
                        labelcreditos.setForeground(Color.RED);
                        labelcreditos.setText("La canción que ha seleccionado no se puede reproducir antes de " + mUserSettings.getReinicioMusicas() +" mins");
                        timerChangerLblCredits.start();
                        objeto.play = false;
                        objeto.resetValues();
                        objeto.labelSelector.setText("- - - -");
                    }


                }
            }
            else if (evento.getKeyCode()==122) {
                String contrasenia = JOptionPane.showInputDialog("Introduzca la clave");
                if (contrasenia.equals("12345")) {
                    if (!creditosLibres) {
                        creditosLibres = true;
                        labelcreditos.setText("Creditos: Libres");
                    } else {
                        creditosLibres = false;
                        labelcreditos.setText(String.format("Creditos: %d", creditos));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña Incorrecta");
                }
            } else if (evento.getKeyCode()==123) {
                @SuppressWarnings("unused")
                SettingsWindow config = new SettingsWindow();
            } else if (evento.getKeyCode()==mUserSettings.getTeclaBajarLista() && creditos > 0) {
                if (isFullScreen) {
                    pantallaCompleta();
                }

                objeto.labelSelector.setText(objeto.keyEventHandler(evento));

                labelPromociones.setVisible(false);

                if (listaDeMusicas.getSelectedIndex() - 20 < 0) {
                    if (listaDeMusicas.getSelectedIndex() == 0) {
                        if (listMusic.downGender()) {
                            listaDeMusicas.setListData(listMusic.getGenderSongs(listMusic.getSelectedGender()));
                            listaDeMusicas.setSelectedIndex(listMusic.getGenderSongs(listMusic.getSelectedGender()).length-1);
                            listaDeMusicas.ensureIndexIsVisible(listMusic.getGenderSongs(listMusic.getSelectedGender()).length-1);
                            labelGeneroMusical.setText("Genero Musical: " + listMusic.getNameOfGender());
                        }
                    } else {
                        listaDeMusicas.setSelectedIndex(0);
                        listaDeMusicas.ensureIndexIsVisible(0);
                    }
                } else {
                    listaDeMusicas.setSelectedIndex(listaDeMusicas.getSelectedIndex()-20);
                    listaDeMusicas.ensureIndexIsVisible(listaDeMusicas.getSelectedIndex());
                }
            } else if (evento.getKeyCode()==mUserSettings.getTeclaSubirLista() && creditos > 0) {
                if (isFullScreen) {
                    pantallaCompleta();
                }

                objeto.labelSelector.setText(objeto.keyEventHandler(evento));

                labelPromociones.setVisible(false);

                if(listaDeMusicas.getSelectedIndex()+20 > listMusic.getGenderSongs(listMusic.getSelectedGender()).length) {
                    if (listaDeMusicas.getSelectedIndex()==listMusic.getGenderSongs(listMusic.getSelectedGender()).length-1) {
                        if (listMusic.upGender()) {
                            listaDeMusicas.setListData(listMusic.getGenderSongs(listMusic.getSelectedGender()));
                            listaDeMusicas.setSelectedIndex(0);
                            listaDeMusicas.ensureIndexIsVisible(0);
                            labelGeneroMusical.setText("Genero Musical: " + listMusic.getNameOfGender());
                        }
                    } else {
                        listaDeMusicas.setSelectedIndex(listMusic.getGenderSongs(listMusic.getSelectedGender()).length-1);
                        listaDeMusicas.ensureIndexIsVisible(listaDeMusicas.getSelectedIndex());
                    }
                } else {
                    listaDeMusicas.setSelectedIndex(listaDeMusicas.getSelectedIndex()+20);
                    listaDeMusicas.ensureIndexIsVisible(listaDeMusicas.getSelectedIndex());
                }
            } else if (evento.getKeyCode() == mUserSettings.getTeclaSubirGenero() && creditos > 0) {
                if (isFullScreen) {
                    pantallaCompleta();
                }

                objeto.labelSelector.setText(objeto.keyEventHandler(evento));
                if (listMusic.upGender()) {
                    labelPromociones.setVisible(false);
                    listaDeMusicas.setListData(listMusic.getGenderSongs(listMusic.getSelectedGender()));
                    listaDeMusicas.setSelectedIndex(0);
                    listaDeMusicas.ensureIndexIsVisible(0);
                    labelGeneroMusical.setText("Genero Musical: " + listMusic.getNameOfGender());
                }
            } else if (evento.getKeyCode() == mUserSettings.getTeclaBajarGenero() && creditos > 0) {
                if (isFullScreen) {
                    pantallaCompleta();
                }

                objeto.labelSelector.setText(objeto.keyEventHandler(evento));

                if (listMusic.downGender()) {
                    labelPromociones.setVisible(false);
                    listaDeMusicas.setListData(listMusic.getGenderSongs(listMusic.getSelectedGender()));
                    listaDeMusicas.setSelectedIndex(0);
                    listaDeMusicas.ensureIndexIsVisible(0);
                    labelGeneroMusical.setText("Genero Musical: " + listMusic.getNameOfGender());
                }
            } else if (evento.getKeyCode()==mUserSettings.getTeclaSaltarCancion() && listaReproduccion.obtenerCancionAReproducir()!=null) {
                if (repro.embeddedMediaPlayerMp3.isPlaying()) {
                    repro.embeddedMediaPlayerMp3.stop();
                } else {
                    repro.embeddedMediaPlayer.stop();
                }
            }
            else if (evento.getKeyCode()==mUserSettings.getTeclaAgregarCredito()) {
                creditos = creditos + mUserSettings.getCantidadCreditos();
                labelcreditos.setText(String.format("Creditos: %d", creditos));
                agregarMonedasYCreditos();
                labelcreditos.setForeground(Color.WHITE);
                if (isFullScreen) {
                    pantallaCompleta();
                }

                entregarPremiosYCreditosAdicionales();
            } else if (evento.getKeyCode()==mUserSettings.getTeclaBorrarCredito() && creditos > 0) {
                --creditos;
                abrirRegConfigEscritura();
                agregarDatosRegConfig();
                cerrarRegConfig();
                labelcreditos.setText(String.format("Creditos: %d", creditos));
                if (creditos == 0 && !creditosLibres) {
                    timerFullScreen.restart();
                }
            }
        }

        private void updateDB(Song cancionAReproducir) {
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

    private class ManejadorDeReproductor extends MediaPlayerEventAdapter
    {
        @Override
        public void stopped(uk.co.caprica.vlcj.player.MediaPlayer mediaPlayer) {
            nextSong();
        }

        public void finished(uk.co.caprica.vlcj.player.MediaPlayer mediaPlayer) {
            if (repro.embeddedMediaPlayerMp3.isPlaying()) {
                repro.embeddedMediaPlayer.playMedia(mUserSettings.getDireccionVideosMp3() + "\\" + listMusic.getPromVideo());
            } else {
                nextSong();
            }
        }

        public void nextSong() {
            listaReproduccion.quitarMusica();
            listaDeReproduccion.setListData(listaReproduccion.obtenerCancionesEnLista());

            if (listaReproduccion.obtenerCancionAReproducir() == null) {
                if (mUserSettings.isVideoPromocional()) {
                    repro.embeddedMediaPlayer.playMedia(mUserSettings.getDireccionVideoPromocional());
                    labelMusica.setText("MFRockola");
                    labelCancionEnRepro.setText("MFRockola");
                } else {
                    labelMusica.setText("MFRockola");
                    labelCancionEnRepro.setText("MFRockola");
                }
            } else {
                int extension = Utils.getExtension(String.format("%s\\%s\\%s\\%s", mUserSettings.getDireccionVideos(),listaReproduccion.obtenerGenero(),listaReproduccion.obtenerArtista(), listaReproduccion.obtenerCancionAReproducir()));

                if (extension == EXT_MP4 || extension == EXT_AVI || extension == EXT_MPG) {
                    repro.playVideo(
                            listaReproduccion.obtenerGenero(),
                            listaReproduccion.obtenerArtista(),
                            listaReproduccion.obtenerCancionAReproducir());

                } else if (extension == EXT_MP3 || extension == EXT_WMA || extension == EXT_WAV || extension == EXT_AAC) {
                    repro.playAudio(
                            listaReproduccion.obtenerGenero(),
                            listaReproduccion.obtenerArtista(),
                            listaReproduccion.obtenerCancionAReproducir(),
                            mUserSettings.getDireccionVideosMp3() + "\\" + listMusic.getPromVideo());
                }

                labelMusica.setText(String.format("%04d - %s - %s - %s",
                        listaReproduccion.obtenerNumero(),
                        listaReproduccion.obtenerGenero(),
                        listaReproduccion.obtenerArtista(),
                        listaReproduccion.obtenerCancionAReproducir()));

                labelCancionEnRepro.setText(String.format("%04d - %s - %s - %s",
                        listaReproduccion.obtenerNumero(),
                        listaReproduccion.obtenerGenero(),
                        listaReproduccion.obtenerArtista(),
                        listaReproduccion.obtenerCancionAReproducir()));
            }
        }
    }

    public void abrirRegConfigEscritura() {
        try {
            salida = new ObjectOutputStream(new FileOutputStream("config.mfr"));
        } catch(IOException ioExcepcion) {
            System.err.println("Error al abrir el archivo.");
        }
    }

    public void agregarDatosRegConfig() {
        UserSettings configuraciones;

        try {
            configuraciones = new UserSettings(
                    this.mUserSettings.getDireccionVideos(),
                    this.mUserSettings.getDireccionVideosMp3(),
                    this.mUserSettings.getDireccionVlc(),
                    this.mUserSettings.getDireccionVideoPromocional(),
                    this.mUserSettings.getMusicAleatoria(),
                    this.mUserSettings.getReinicioMusicas(),
                    this.mUserSettings.getCantidadCreditos(),
                    this.mUserSettings.isLibre(),
                    this.mUserSettings.isVideoPromocional(),
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
                    creditos
            );

            salida.writeObject(configuraciones);
        } catch (IOException excepcion) {
            System.err.println("Error al escribir el archivo");
        }
    }

    public void cerrarRegConfig() {
        try {
            if (salida != null)
                salida.close();

            salida = null;
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
            sound = AudioSystem.getClip();
            sound.open(ais);
            sound.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSongToPlayList(ArrayList numbers) {
        if (numbers.size()>0) {
            for (int i = 0; i < numbers.size(); i++) {
                Song cancionAReproducir = listMusic.getSong((int) numbers.get(i));
                //Song cancion = new Song(numero, cancionAReproducir);
                listaReproduccion.agregarCanciones(cancionAReproducir);

            }
        }
        listaDeReproduccion.setListData(listaReproduccion.obtenerCancionesEnLista());
    }
}
