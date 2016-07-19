package com.mfrockola.classes;

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Angel C on 17/04/2016.
 */
public class Interfaz extends JFrame
{
    private ObjectOutputStream salida;
    OperacionesRegConfig registroDatos = new OperacionesRegConfig();
    RegConfig configuraciones;

    private int creditos;
    private int ancho;
    private int alto;

    MusicasProhibidas prohibir; // Objeto de musicas que no se pueden repetir.
    ListaDeReproduccion listaReproduccion = new ListaDeReproduccion(); // Objeto de las musicas en reproduccion.

    boolean isFullScreen = false; // Objeto que determina si se entra o no en pantalla completa.

    JEImagePanel panelFondo;
    JPanel contenedorPrincipal;
    JPanel contenedorVideo;
    JPanel panel;
    JPanel panelInferior;

    Dimension resolucion; // Objeto para determinar la resolucion de la pantalla

    @SuppressWarnings("rawtypes")
    private JList listaDeMusicas; // JList para colocar el listado de los videos disponibles en el directorio.
    private ListMusic listMusic;
    private JList listaDeReproduccion; // JList para colocar el listado de los videos en reproduccion.

    // JLabels

    JLabel labelGeneroMusical;
    JLabel labelMusica = new JLabel();
    JLabel labelCancionEnRepro;
    JLabel labelcreditos;
    JLabel labelLogo;
    JLabel labelPromociones;

    Reproductor repro;

    SelectMusica objeto;

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

    ManejadorDeTeclas manejadorDeTeclas;

    private Clip sound;

    @SuppressWarnings("unchecked")
    public Interfaz()
    {
        try
        {
            registroDatos.abrirRegConfigLectura();
            configuraciones = registroDatos.leerRegConfigLectura();
            prohibir = new MusicasProhibidas(configuraciones.getReinicioMusicas());
            objeto = new SelectMusica(configuraciones.getTeclaBorrar());
            monedasASubir = configuraciones.getCantidadMonedasInsertadas();
            creditosASubir = configuraciones.getCantidadCreditosUsados();
            cancelMusic = configuraciones.isCancelMusic();
            creditosLibres = configuraciones.isLibre();
            agregarCreditoAdicional = configuraciones.isAgregarAdicional();
            entregarPremio = configuraciones.isEntregarPremio();
        }
        catch (NullPointerException excepcion)
        {
            @SuppressWarnings("unused")
            Configuracion configurar = new Configuracion();
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

        ActionListener pressKey = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Robot robot = new Robot();
                    robot.keyPress(120);
                    robot.keyRelease(120);
                } catch (AWTException exception) {
                    exception.printStackTrace();
                }
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

        repro.embeddedMediaPlayer.addMediaPlayerEventListener(new manejadorDeReproductor());

        if(configuraciones.isVideoPromocional()) {
            repro.embeddedMediaPlayer.playMedia(configuraciones.getDireccionVideoPromocional());
            pantallaCompleta();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e)
            {

                //  isMetaDown = rigth CLick

                // Cuando se presiona click izquierdo y las canciones no se pueden cancelar

                if(configuraciones.getClickCreditos()==0 && e.isMetaDown() == false && !creditosLibres)
                {
                    creditos = creditos + configuraciones.getCantidadCreditos();
                    labelcreditos.setText(String.format("Creditos: %d", creditos));
                    agregarMonedasYCreditos();
                    labelcreditos.setForeground(Color.WHITE);
                    if (isFullScreen) {
                        pantallaCompleta();
                    }

                    entregarPremiosYCreditosAdicionales();

                    // Click derecho y las canciones se pueden eliminar

                } else if (cancelMusic && e.isMetaDown() == true && listaReproduccion.obtenerCancionAReproducir()!=null) {

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
                            if (new String(passwordPanel.getPassword()).equals(configuraciones.getPassword())) {
                                repro.embeddedMediaPlayer.stop();
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

                } else if (e.isMetaDown() && configuraciones.getClickCreditos() == 1 && !creditosLibres && !cancelMusic) {

                    creditos = creditos + configuraciones.getCantidadCreditos();
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

        creditos = 0;

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
            labelcreditos = new JLabel("Creditos: 0");
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

        listMusic = new ListMusic(configuraciones.getDireccionVideos());

        listaDeMusicas = new JList();
        listaDeMusicas.setCellRenderer(new ModificadorDeCeldas(new Font(configuraciones.getFontCeldasName(),
                configuraciones.getFontCeldasNegrita(), configuraciones.getFontCeldasSize()),configuraciones.getFontCeldasColor(),
                configuraciones.getColor1(), configuraciones.getColor2()));
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
        listaDeReproduccion.setCellRenderer(new ModificadorDeCeldas(new Font(configuraciones.getFontCeldasName(),
                configuraciones.getFontCeldasNegrita(), configuraciones.getFontCeldasSize()),configuraciones.getFontCeldasColor(),
                configuraciones.getColor1(), configuraciones.getColor2()));
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

        panelFondo = new JEImagePanel(configuraciones.getDireccionFondo());
        panelFondo.setLayout(new BorderLayout());
        panelFondo.add(panelInferior,BorderLayout.SOUTH);

        panelInferior.add(labelCancionEnRepro, BorderLayout.CENTER);

        panelFondo.add(contenedorPrincipal,BorderLayout.CENTER);

        repro = new Reproductor();
        contenedorVideo = new JPanel();
        contenedorVideo.setLayout(new BorderLayout());
        contenedorVideo.setBounds((int)(ancho/1.633), (int)(alto/16.340),(int)(ancho/2.732), (int)(alto/2.7137));
        contenedorVideo.add(repro.obtenerReproductor(),BorderLayout.CENTER);
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

        panel_1.add(objeto.selectorMusica);

        contenedorPrincipal.add(listaDeReproduccion);
    }

    public void pantallaCompleta()
    {
        if (isFullScreen == false)
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

    public void entregarPremiosYCreditosAdicionales() {
        // creditosInsertados es la variable de control de los clicks

        if (agregarCreditoAdicional && !creditosLibres) {
            creditosInsertados++;
            if (creditosInsertados >= configuraciones.getCadaCantidadDeCreditos()) {
                // Aqui va la cuestion para controlar el cartel de los creditos adicionales
                creditosInsertados = 0;
                creditos = creditos + configuraciones.getNumeroDeCreditosAdicionales();
                labelcreditos.setText(String.format("Creditos: %d", creditos));
                labelPromociones.setText(String.format("Ganaste %s creditos adicionales",
                        configuraciones.getNumeroDeCreditosAdicionales()));
                labelPromociones.setVisible(true);
            }
        }

        if (entregarPremio && !creditosLibres) {
            creditosInsertadosParaPremio++;
            if (creditosInsertadosParaPremio % configuraciones.getCantidadDeCreditosPorPremio() == 0) {
                // Aqui va la cuestion para controlar el cartel de premio
                labelPromociones.setText(String.format("Ganaste %s %s!", configuraciones.getCantidadDePremios(),
                        configuraciones.getTipoDePremio()));
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
            if (evento.getKeyCode()==configuraciones.getTeclaPantallaCompleta() && creditos > 0)
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

            if(evento.getKeyCode()== configuraciones.getTeclaBorrar() && objeto.contador > 0)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }

            if ((evento.getKeyCode()==48 || evento.getKeyCode()==96) && creditos > 0)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if ((evento.getKeyCode()==49 || evento.getKeyCode()==97) && creditos > 0)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if ((evento.getKeyCode()==50 || evento.getKeyCode()==98) && creditos > 0)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if ((evento.getKeyCode()==51 || evento.getKeyCode()==99) && creditos > 0)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if ((evento.getKeyCode()==52 || evento.getKeyCode()==100) && creditos > 0)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if ((evento.getKeyCode()==53 || evento.getKeyCode()==101) && creditos > 0)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if ((evento.getKeyCode()==54 || evento.getKeyCode()==102) && creditos > 0)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if ((evento.getKeyCode()==55 || evento.getKeyCode()==103) && creditos > 0)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if ((evento.getKeyCode()==56 || evento.getKeyCode()==104) && creditos > 0)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if ((evento.getKeyCode()==57 || evento.getKeyCode()==105) && creditos > 0)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }

            if (objeto.reproducir == true)
            {
                int numero;
                boolean condicion = false;

                numero = Integer.parseInt(String.format("%s%s%s%s", objeto.valores[0],objeto.valores[1],
                        objeto.valores[2],objeto.valores[3]));

                if (prohibir.revisarProhibido(numero)== true)
                    condicion = true;
                else
                    condicion = false;

                if (numero >= listMusic.getSizeListOfSongs()) {
                    labelcreditos.setText("Musica no encontrada");
                    objeto.reproducir = false;
                    objeto.reiniciarValores();
                    objeto.selectorMusica.setText("- - - -");
                    timerChangerLblCredits.start();
                }
                else
                {
                    if ((creditos > 0 && condicion == true)||(creditosLibres == true && condicion == true))
                    {
                        if (listaReproduccion.obtenerCancionAReproducir()==null)
                        {
                            Cancion cancionAReproducir =  listMusic.getSong(numero);

                            listaReproduccion.agregarCanciones(cancionAReproducir);
                            repro.reproducirMusica(listaReproduccion.obtenerGenero(),listaReproduccion.obtenerArtista(),listaReproduccion.obtenerCancionAReproducir());
                            listaDeReproduccion.setListData(listaReproduccion.obtenerCancionesEnLista());
                            if (creditosLibres== false)
                            {
                                --creditos;
                                labelcreditos.setText(String.format("%s: %d","Creditos",creditos));
                            }
                            objeto.reproducir = false;
                            objeto.reiniciarValores();
                            objeto.selectorMusica.setText("- - - -");
                            prohibir.agregarProhibido(numero);
                            labelMusica.setText(String.format("%04d - %s - %s",listaReproduccion.obtenerNumero(),
                                    listaReproduccion.obtenerArtista(),
                                    listaReproduccion.obtenerCancionAReproducir()));
                            labelCancionEnRepro.setText(String.format("%04d - %s - %s",
                                    listaReproduccion.obtenerNumero(), listaReproduccion.obtenerArtista(),
                                    listaReproduccion.obtenerCancionAReproducir()));
                            if (creditos == 0 && !creditosLibres) {
                                timerFullScreen.restart();
                            }

                            if (configuraciones.isCreditosContinuos()) {
                                creditosInsertados = 0;
                            }

                            updateDB(cancionAReproducir);
                        }
                        else
                        {
                            Cancion cancionAReproducir = listMusic.getSong(numero);
                            //Cancion cancion = new Cancion(numero, cancionAReproducir);
                            listaReproduccion.agregarCanciones(cancionAReproducir);
                            listaDeReproduccion.setListData(listaReproduccion.obtenerCancionesEnLista());
                            if (creditosLibres == false)
                            {
                                --creditos;
                                labelcreditos.setForeground(Color.WHITE);
                                labelcreditos.setText(String.format("%s: %d","Creditos",creditos));
                            }
                            objeto.reproducir = false;
                            objeto.reiniciarValores();
                            objeto.selectorMusica.setText("- - - -");
                            prohibir.agregarProhibido(numero);
                            if (creditos == 0 && !creditosLibres) {
                                timerFullScreen.restart();
                            }

                            if (configuraciones.isCreditosContinuos()) {
                                creditosInsertados = 0;
                            }

                            updateDB(cancionAReproducir);
                        }
                    }
                    else
                    {
                        labelcreditos.setForeground(Color.RED);
                        labelcreditos.setText("Sin creditos o no se puede reproducir en " + configuraciones.getReinicioMusicas() +" mins");
                        timerChangerLblCredits.start();
                        objeto.reproducir = false;
                        objeto.reiniciarValores();
                        objeto.selectorMusica.setText("- - - -");
                    }


                }
            }
            else if (evento.getKeyCode()==122)
            {
                String contrasenia = JOptionPane.showInputDialog("Introduzca la clave");
                if (contrasenia.equals("12345"))
                {
                    if (creditosLibres == false)
                    {
                        creditosLibres = true;
                        labelcreditos.setText("Creditos: Libres");
                    }
                    else
                    {
                        creditosLibres = false;
                        labelcreditos.setText(String.format("Creditos: %d", creditos));
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "Contraseña Incorrecta");
            }
            else if (evento.getKeyCode()==123)
            {
                @SuppressWarnings("unused")
                Configuracion config = new Configuracion();
            }

            else if (evento.getKeyCode()==configuraciones.getTeclaBajarLista() && creditos > 0)
            {
                if (isFullScreen) {
                    pantallaCompleta();
                }

                labelPromociones.setVisible(false);

                if (listaDeMusicas.getSelectedIndex() - 20 < 0)
                {
                    listaDeMusicas.setSelectedIndex(0);
                    listaDeMusicas.ensureIndexIsVisible(0);
                }
                else
                {
                    listaDeMusicas.setSelectedIndex(listaDeMusicas.getSelectedIndex()-20);
                    listaDeMusicas.ensureIndexIsVisible(listaDeMusicas.getSelectedIndex());
                }

            }
            else if (evento.getKeyCode()==configuraciones.getTeclaSubirLista() && creditos > 0)
            {
                if (isFullScreen) {
                    pantallaCompleta();
                }

                labelPromociones.setVisible(false);

                if(listaDeMusicas.getSelectedIndex()+20 > listMusic.getGenderSongs(listMusic.getSelectedGender()).length)
                {
                    listaDeMusicas.setSelectedIndex(listMusic.getGenderSongs(listMusic.getSelectedGender()).length-1);
                    listaDeMusicas.ensureIndexIsVisible(listaDeMusicas.getSelectedIndex());
                }
                else
                {
                    listaDeMusicas.setSelectedIndex(listaDeMusicas.getSelectedIndex()+20);
                    listaDeMusicas.ensureIndexIsVisible(listaDeMusicas.getSelectedIndex());
                }
            }
            else if (evento.getKeyCode() == configuraciones.getTeclaSubirGenero() && creditos > 0)
            {
                if (isFullScreen) {
                    pantallaCompleta();
                }
                if (listMusic.upGender()) {
                    labelPromociones.setVisible(false);
                    listaDeMusicas.setListData(listMusic.getGenderSongs(listMusic.getSelectedGender()));
                    listaDeMusicas.setSelectedIndex(0);
                    listaDeMusicas.ensureIndexIsVisible(0);
                    labelGeneroMusical.setText("Genero Musical: " + listMusic.getNameOfGender());
                }
            }
            else if (evento.getKeyCode() == configuraciones.getTeclaBajarGenero() && creditos > 0)
            {
                if (isFullScreen) {
                    pantallaCompleta();
                }
                if (listMusic.downGender()) {
                    labelPromociones.setVisible(false);
                    listaDeMusicas.setListData(listMusic.getGenderSongs(listMusic.getSelectedGender()));
                    listaDeMusicas.setSelectedIndex(0);
                    listaDeMusicas.ensureIndexIsVisible(0);
                    labelGeneroMusical.setText("Genero Musical: " + listMusic.getNameOfGender());
                }
            }
            else if (evento.getKeyCode()==configuraciones.getTeclaSaltarCancion() && listaReproduccion.obtenerCancionAReproducir()!=null) {
                repro.embeddedMediaPlayer.stop();
            }
            else if (evento.getKeyCode()==configuraciones.getTeclaAgregarCredito()) {
                creditos = creditos + configuraciones.getCantidadCreditos();
                labelcreditos.setText(String.format("Creditos: %d", creditos));
                agregarMonedasYCreditos();
                labelcreditos.setForeground(Color.WHITE);
                if (isFullScreen) {
                    pantallaCompleta();
                }

                entregarPremiosYCreditosAdicionales();

            }
            else if (evento.getKeyCode()==configuraciones.getTeclaBorrarCredito() && creditos > 0) {
                creditos--;
                labelcreditos.setText(String.format("Creditos: %d", creditos));
                if (creditos == 0 && !creditosLibres) {
                    timerFullScreen.restart();
                }
            }
        }

        private void updateDB(Cancion cancionAReproducir) {
            try {
                String consulta = "SELECT * FROM most_popular WHERE number = " + cancionAReproducir.obtenerNumero();

                System.out.println(consulta);

                ResultSet resultSet = consultor.query(consulta);

                if (resultSet.isClosed()) {
                    resultSet.close();

                    String insertar = "INSERT INTO most_popular(number, name, artist, genre, times, last_date)" +
                            " VALUES ("+cancionAReproducir.obtenerNumero()+",'" +
                            cancionAReproducir.obtenerNombreCancion()+ "','" +
                            cancionAReproducir.getArtista() + "','" +
                            cancionAReproducir.getGenero() + "'," +
                            1 + ", "+ new Date().getTime() +");";

                    System.out.println(insertar);

                    consultor.insert(insertar);
                } else {

                    int times = 0;
                    while (resultSet.next()) {

                        times = resultSet.getInt("times") + 1;

                        System.out.println(String.format("%s - %s - %s - %s - %s",
                                resultSet.getInt("_ID"),
                                resultSet.getInt("number"),
                                resultSet.getString("name"),
                                resultSet.getInt("times"),
                                resultSet.getLong("last_date")));
                    }

                    resultSet.close();

                    consultor.update("UPDATE most_popular SET times = " + times + ", last_date = " + new Date().getTime() + " WHERE number = " + cancionAReproducir.obtenerNumero());

                    consultor.closeConnection();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class manejadorDeReproductor extends MediaPlayerEventAdapter
    {
        @Override
        public void stopped(MediaPlayer mediaPlayer) {
            nextMusic();
        }

        public void finished(MediaPlayer mediaPlayer)
        {
            nextMusic();
        }

        public void nextMusic() {
            listaReproduccion.quitarMusica();
            listaDeReproduccion.setListData(listaReproduccion.obtenerCancionesEnLista());

            if (listaReproduccion.obtenerCancionAReproducir() == null)
            {
                if (configuraciones.isVideoPromocional()) {
                    repro.embeddedMediaPlayer.playMedia(configuraciones.getDireccionVideoPromocional());
                    labelMusica.setText("MFRockola");
                    labelCancionEnRepro.setText("MFRockola");
                } else {
                    labelMusica.setText("MFRockola");
                    labelCancionEnRepro.setText("MFRockola");
                }
            }
            else
            {
                repro.reproducirMusica(listaReproduccion.obtenerGenero(),listaReproduccion.obtenerArtista(), listaReproduccion.obtenerCancionAReproducir());
                labelMusica.setText(String.format("%04d - %s - %s",
                        listaReproduccion.obtenerNumero(), listaReproduccion.obtenerArtista(),
                        listaReproduccion.obtenerCancionAReproducir()));
                labelCancionEnRepro.setText(String.format("%04d - %s - %s",
                        listaReproduccion.obtenerNumero(),listaReproduccion.obtenerArtista(),
                        listaReproduccion.obtenerCancionAReproducir()));
            }
        }
    }

    public void abrirRegConfigEscritura()
    {
        try
        {
            salida = new ObjectOutputStream(new FileOutputStream("config.mfr"));
        }
        catch(IOException ioExcepcion)
        {
            System.err.println("Error al abrir el archivo.");
        }
    }

    public void agregarDatosRegConfig()
    {
        RegConfig configuraciones;

        try
        {
            configuraciones = new RegConfig(
                    this.configuraciones.getDireccionVideos(),
                    this.configuraciones.getDireccionVlc(),
                    this.configuraciones.getDireccionVideoPromocional(),
                    this.configuraciones.getMusicAleatoria(),
                    this.configuraciones.getReinicioMusicas(),
                    this.configuraciones.getCantidadCreditos(),
                    this.configuraciones.isLibre(),
                    this.configuraciones.isVideoPromocional(),
                    this.configuraciones.getClickCreditos(),
                    this.configuraciones.getTeclaSubirLista(),
                    this.configuraciones.getTeclaBajarLista(),
                    this.configuraciones.getTeclaSubirGenero(),
                    this.configuraciones.getTeclaBajarGenero(),
                    this.configuraciones.getTeclaPantallaCompleta(),
                    this.configuraciones.getTeclaBorrar(),
                    this.configuraciones.getTeclaSaltarCancion(),
                    this.configuraciones.getTeclaAgregarCredito(),
                    this.configuraciones.getTeclaBorrarCredito(),
                    this.configuraciones.isCancelMusic(),
                    this.configuraciones.getPassword(),
                    creditosASubir,
                    monedasASubir,
                    this.configuraciones.isDefaultBackground(),
                    this.configuraciones.getDireccionFondo(),
                    this.configuraciones.getColor1(),
                    this.configuraciones.getColor2(),
                    this.configuraciones.getFontCeldasName(),
                    this.configuraciones.getFontCeldasSize(),
                    this.configuraciones.getFontCeldasColor(),
                    this.configuraciones.getFontCeldasNegrita(),
                    this.configuraciones.isAgregarAdicional(),
                    this.configuraciones.getNumeroDeCreditosAdicionales(),
                    this.configuraciones.getCadaCantidadDeCreditos(),
                    this.configuraciones.isCreditosContinuos(),
                    this.configuraciones.isEntregarPremio(),
                    this.configuraciones.getCantidadDePremios(),
                    this.configuraciones.getCantidadDeCreditosPorPremio(),
                    this.configuraciones.getTipoDePremio()
            );

            salida.writeObject(configuraciones);
        } catch (IOException excepcion)
        {
            System.err.println("Error al escribir el archivo");
        }
    }

    public void cerrarRegConfig()
    {
        try
        {
            if (salida != null)
                salida.close();

            salida = null;
        }
        catch (IOException ioExcepcion)
        {
            System.err.println("Error al cerrar el archivo");
            System.exit(1);
        }
    }

    public void agregarMonedasYCreditos()
    {
        timerFullScreen.stop();
        creditosASubir = creditosASubir + configuraciones.getCantidadCreditos();
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
}
