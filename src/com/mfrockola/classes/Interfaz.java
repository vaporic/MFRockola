package com.mfrockola.classes;

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

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

    private boolean creditosLibres = false;

    MusicasProhibidas prohibir = new MusicasProhibidas(); // Objeto de musicas que no se pueden repetir.
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

    Reproductor repro;

    SelectMusica objeto = new SelectMusica();

    private int monedasASubir;
    private int creditosASubir;
    private JScrollPane barras;
    private Timer timerChangerLblCredits;

    @SuppressWarnings("unchecked")
    public Interfaz()
    {
        try
        {
            registroDatos.abrirRegConfigLectura();
            configuraciones = registroDatos.leerRegConfigLectura();
            monedasASubir = configuraciones.getCantidadMonedasInsertadas();
            creditosASubir = configuraciones.getCantidadCreditosUsados();
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
                labelcreditos.setText(String.format("Creditos: %d", creditos));
                labelcreditos.setForeground(Color.WHITE);
            }
        };

        timerChangerLblCredits = new Timer(5000, changeLblCredits);
        timerChangerLblCredits.setRepeats(false);

        getContentPane().add(panelFondo);

        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(resolucion);
        setVisible(true);

        repro.embeddedMediaPlayer.addMediaPlayerEventListener(new manejadorDeReproductor());

        if(configuraciones.isVideoPromocional()) {
            repro.embeddedMediaPlayer.playMedia(configuraciones.getDireccionVideoPromocional());
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if(configuraciones.getClickCreditos()==0 && e.isMetaDown() == false )
                {
                    creditos = creditos + configuraciones.getCantidadCreditos();
                    labelcreditos.setText(String.format("Creditos: %d", creditos));
                    agregarMonedasYCreditos();
                    labelcreditos.setForeground(Color.WHITE);
                }
                if (e.isMetaDown() && configuraciones.getClickCreditos() == 1)
                {
                    creditos = creditos + configuraciones.getCantidadCreditos();
                    labelcreditos.setText(String.format("Creditos: %d", creditos));
                    agregarMonedasYCreditos();
                    labelcreditos.setForeground(Color.WHITE);
                }


            }
        });

        this.addKeyListener(new manejadorDeTeclas());

        panelInferior.addKeyListener(new manejadorDeTeclas());
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
        labelGeneroMusical.setBounds(30,15,ancho-590,35);

        labelcreditos = new JLabel("Creditos: 0");
        labelcreditos.setForeground(Color.WHITE);
        labelcreditos.setFont(new Font("Calibri", Font.BOLD, 23));
        labelcreditos.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));

        Icon log = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/nombre.png"));
        labelLogo = new JLabel(log);
        labelLogo.setHorizontalAlignment(SwingConstants.RIGHT);
        labelLogo.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        labelCancionEnRepro = new JLabel("MFRockola");
        labelCancionEnRepro.setForeground(Color.WHITE);
        labelCancionEnRepro.setFont(new Font("Calibri", Font.BOLD, 23));
        labelCancionEnRepro.setHorizontalAlignment(SwingConstants.CENTER);

        // Iniciar las listas

        listMusic = new ListMusic(configuraciones.getDireccionVideos());

        listaDeMusicas = new JList();
        listaDeMusicas.setCellRenderer(new ModificadorDeCeldas(new Font("Consolas", Font.BOLD,20),
                configuraciones.getColor1(), configuraciones.getColor2()));
        listaDeMusicas.setListData(listMusic.getGenderSongs(0));
        listaDeMusicas.addKeyListener(new manejadorDeTeclas());
        listaDeMusicas.setVisibleRowCount(20);
        listaDeMusicas.setFocusable(false);
        listaDeMusicas.setMaximumSize(getMaximumSize());

        barras = new JScrollPane(listaDeMusicas);
        barras.setBounds(30, 60, ancho-590, alto-65);
        barras.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        barras.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        listaDeReproduccion = new JList();
        listaDeReproduccion.setListData(listaReproduccion.obtenerCancionesEnLista());
        listaDeReproduccion.setCellRenderer(new ModificadorDeCeldas(new Font("Consolas", Font.BOLD,20),
                configuraciones.getColor1(), configuraciones.getColor2()));
        listaDeReproduccion.setBounds(ancho-530, alto-200,500,190);
        listaDeReproduccion.setFocusable(false);

        labelGeneroMusical.setText("Genero Musical: "+ listMusic.getNameOfGender());

        // Iniciar los panel

        contenedorPrincipal = new JPanel();
        contenedorPrincipal.setOpaque(false);
        contenedorPrincipal.setLayout(null);
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
        contenedorVideo.setBounds(ancho-530, alto-(alto*94/100),500, 283);
        contenedorVideo.add(repro.obtenerReproductor(),BorderLayout.CENTER);
        contenedorPrincipal.add(contenedorVideo);

        panel = new JPanel();
        panel.setOpaque(false);
        panel.setBounds(ancho-530, alto-(alto*55/100), 500, 380); // contenedorVideo.setBounds(ancho-530, alto-(alto*94/100),500, 283);
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
            contenedorVideo.setBounds(ancho-530, alto-(alto*94/100),500, 283);
            barras.setVisible(true);
            listaDeMusicas.setVisible(true);
            panel.setVisible(true);
            labelGeneroMusical.setVisible(true);
            isFullScreen = false;
        }
    }

    public static void main(String [] args)
    {
        Interfaz aplicacion = new Interfaz();
    }

    private class manejadorDeTeclas extends KeyAdapter
    {


        public void keyPressed(KeyEvent evento)
        {

            if (evento.getKeyCode()==10)
            {
                pantallaCompleta();
            }

            if(evento.getKeyCode()== 8 && objeto.contador > 0)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }

            if (evento.getKeyCode()==48 || evento.getKeyCode()==96)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if (evento.getKeyCode()==49 || evento.getKeyCode()==97)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if (evento.getKeyCode()==50 || evento.getKeyCode()==98)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if (evento.getKeyCode()==51 || evento.getKeyCode()==99)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if (evento.getKeyCode()==52 || evento.getKeyCode()==100)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if (evento.getKeyCode()==53 || evento.getKeyCode()==101)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if (evento.getKeyCode()==54 || evento.getKeyCode()==102)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if (evento.getKeyCode()==55 || evento.getKeyCode()==103)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if (evento.getKeyCode()==56 || evento.getKeyCode()==104)
            {
                objeto.selectorMusica.setText(objeto.manejadorDeEvento(evento));
            }
            else if (evento.getKeyCode()==57 || evento.getKeyCode()==105)
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

                if (numero >= listMusic.getSizeListOfSongs())
                {
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
                            repro.reproducirMusica(listaReproduccion.obtenerGenero(),listaReproduccion.obtenerCancionAReproducir());
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
                            labelMusica.setText(String.format("%04d - %s",listaReproduccion.obtenerNumero()
                                    ,listaReproduccion.obtenerCancionAReproducir()));
                            labelCancionEnRepro.setText(String.format("%04d - %s",
                                    listaReproduccion.obtenerNumero(), listaReproduccion.obtenerCancionAReproducir()));
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

                        }
                    }
                    else
                    {
                        labelcreditos.setForeground(Color.RED);
                        labelcreditos.setText("Sin creditos o no se puede reproducir en 30 mins");
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

            else if (evento.getKeyCode()==45 || evento.getKeyCode()==109)
            {
                if (listaDeMusicas.getSelectedIndex() - 10 < 0)
                {
                    listaDeMusicas.setSelectedIndex(0);
                    listaDeMusicas.ensureIndexIsVisible(0);
                }
                else
                {
                    listaDeMusicas.setSelectedIndex(listaDeMusicas.getSelectedIndex()-10);
                    listaDeMusicas.ensureIndexIsVisible(listaDeMusicas.getSelectedIndex());
                }

            }
            else if (evento.getKeyCode()==521 || evento.getKeyCode()==107)
            {
                if(listaDeMusicas.getSelectedIndex()+10 > listMusic.getGenderSongs(listMusic.getSelectedGender()).length)
                {
                    listaDeMusicas.setSelectedIndex(listMusic.getGenderSongs(listMusic.getSelectedGender()).length-1);
                    listaDeMusicas.ensureIndexIsVisible(listaDeMusicas.getSelectedIndex());
                }
                else
                {
                    listaDeMusicas.setSelectedIndex(listaDeMusicas.getSelectedIndex()+10);
                    listaDeMusicas.ensureIndexIsVisible(listaDeMusicas.getSelectedIndex());
                }
            }
            else if (evento.getKeyCode() == 71)
            {
                if (listMusic.upGender()) {
                    listaDeMusicas.setListData(listMusic.getGenderSongs(listMusic.getSelectedGender()));
                    listaDeMusicas.setSelectedIndex(0);
                    listaDeMusicas.ensureIndexIsVisible(0);
                    labelGeneroMusical.setText("Genero Musical: " + listMusic.getNameOfGender());
                }
            }
            else if (evento.getKeyCode() == 72)
            {
                if (listMusic.downGender()) {
                    listaDeMusicas.setListData(listMusic.getGenderSongs(listMusic.getSelectedGender()));
                    listaDeMusicas.setSelectedIndex(0);
                    listaDeMusicas.ensureIndexIsVisible(0);
                    labelGeneroMusical.setText("Genero Musical: " + listMusic.getNameOfGender());
                }
            }
        }
    }

    private class manejadorDeReproductor extends MediaPlayerEventAdapter
    {
        public void finished(MediaPlayer mediaPlayer)
        {
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
                repro.reproducirMusica(listaReproduccion.obtenerGenero(), listaReproduccion.obtenerCancionAReproducir());
                labelMusica.setText(listaReproduccion.obtenerCancionAReproducir());
                labelCancionEnRepro.setText(listaReproduccion.obtenerCancionAReproducir());
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
                    this.configuraciones.getTeclaCambiarLista(),
                    creditosASubir,
                    monedasASubir,
                    this.configuraciones.isDefaultBackground(),
                    this.configuraciones.getDireccionFondo(),
                    this.configuraciones.getColor1(),
                    this.configuraciones.getColor2()
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
        creditosASubir = creditosASubir + configuraciones.getCantidadCreditos();
        monedasASubir++;
        abrirRegConfigEscritura();
        agregarDatosRegConfig();
        cerrarRegConfig();
    }

//    public void activarListaReproduccion()
//    {
//        int delay = 6000;
//
//        Timer temporizadorListaReproduccion = new Timer(delay, new ActionListener()
//        {
//
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                listaDeMusicas.setListData(musicaDisponible.getListaMusicas());
//            }
//        });
//
//        temporizadorListaReproduccion.setRepeats(false);
//
//        temporizadorListaReproduccion.start();
//    }
}
