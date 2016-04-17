package com.mfrockola.classes;

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

/**
 * Created by Angel C on 17/04/2016.
 */
public class Interfaz extends JFrame
{
    private ObjectOutputStream salida;
    OperacionesRegConfig registroDatos = new OperacionesRegConfig();
    RegConfig configuraciones;

    JLabel lblcreditos = new JLabel("Creditos: 0");

    int creditos = 0;
    int ancho;
    int alto;

    boolean creditosLibres = false;

    ListaDeMusica listado;
    MusicasProhibidas prohibir = new MusicasProhibidas();
    ListaDeReproduccion listaReproduccion = new ListaDeReproduccion();

    boolean isFullScreen = false;

    JPanel contenedorVideo;
    JPanel panel;

    Dimension resolucion;

    @SuppressWarnings("rawtypes")
    JList lista = new JList();
    JLabel lblMusica = new JLabel();
    JLabel labelCancionEnRepro;
    JLabel labelPublicidad;
    String [] directorioPublicidad;

    Reproductor repro;

    SelectMusica objeto = new SelectMusica();

    Random numeroAleatorio = new Random();
    private int monedasASubir;
    private int creditosASubir;
    private JScrollPane barras;
    private Timer temporizadorLblPublicidad;

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

        File archivo = new File(configuraciones.getDireccionImagenes());

        if (archivo.isDirectory())
        {
            directorioPublicidad = archivo.list();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "error");
        }

        ActionListener cambiarPublicidad = new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                labelPublicidad.setIcon(new ImageIcon(String.format("%s\\%s",
                        configuraciones.getDireccionImagenes(),cambiarImagen())));
            }
        };

        ActionListener cambiarLblPublicidad = new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                lblcreditos.setText(String.format("Creditos: %d", creditos));
                lblcreditos.setForeground(Color.WHITE);
            }
        };

        int demoraCambioPublicidad = 20000;

        int demoraLblPublicidad = 5000;

        Timer temporizadorPublicidad = new Timer(demoraCambioPublicidad, cambiarPublicidad);

        temporizadorLblPublicidad = new Timer(demoraLblPublicidad, cambiarLblPublicidad);
        temporizadorLblPublicidad.setRepeats(false);

        temporizadorPublicidad.start();


        listado = new ListaDeMusica(configuraciones.getDireccionVideos());

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.setOpaque(false);

        resolucion = Toolkit.getDefaultToolkit().getScreenSize();
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/mfrockola/imagenes/icono.png")));

        Icon log = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/nombre.png"));
        JLabel logo = new JLabel(log);
        logo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblcreditos.setForeground(Color.WHITE);
        lblcreditos.setFont(new Font("Calibri", Font.BOLD, 23));

        panelInferior.add(lblcreditos,BorderLayout.WEST);
        panelInferior.add(logo,BorderLayout.EAST);
        JEImagePanel panelFondo = new JEImagePanel(configuraciones.getDireccionFondo());
        panelFondo.setLayout(new BorderLayout());
        panelFondo.add(panelInferior,BorderLayout.SOUTH);

        labelCancionEnRepro = new JLabel("MFRockola");
        labelCancionEnRepro.setForeground(Color.WHITE);
        labelCancionEnRepro.setFont(new Font("Calibri", Font.BOLD, 23));
        labelCancionEnRepro.setHorizontalAlignment(SwingConstants.CENTER);
        panelInferior.add(labelCancionEnRepro, BorderLayout.CENTER);

        JPanel contenedorPrincipal = new JPanel();
        panelFondo.add(contenedorPrincipal,BorderLayout.CENTER);

        ancho = (int) resolucion.getWidth();
        alto = (int) (resolucion.getHeight() - 54);


        lista.setCellRenderer(new ModificadorDeCeldas(new Font("Consolas", Font.BOLD,20),
                configuraciones.getColor1(), configuraciones.getColor2()));
        lista.setSelectedIndex(1);
        lista.setListData(listado.getListaMusicas());
        lista.addKeyListener(new manejadorDeTeclas());
        lista.setVisibleRowCount(20);
        lista.setFocusable(false);
        lista.setMaximumSize(getMaximumSize());

        barras = new JScrollPane(lista);
        barras.setBounds(30, 30, ancho-590, alto-35);
        barras.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        barras.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        contenedorPrincipal.setOpaque(false);
        contenedorPrincipal.setLayout(null);
        contenedorPrincipal.add(barras);

        repro = new Reproductor();
        contenedorVideo = new JPanel();
        contenedorVideo.setOpaque(false);
        contenedorVideo.setBackground(Color.BLACK);
        contenedorVideo.setLayout(new BorderLayout());
        contenedorVideo.setBounds(ancho-530, alto-(alto*94/100),500, 283);
        contenedorVideo.add(repro.obtenerReproductor(),BorderLayout.CENTER);
        contenedorPrincipal.add(contenedorVideo);

        panel = new JPanel();
        panel.setOpaque(false);
        panel.setBounds(ancho-530, alto-(alto*55/100), 500, 380); // contenedorVideo.setBounds(ancho-530, alto-(alto*94/100),500, 283);
        contenedorPrincipal.add(panel);
        panel.setLayout(new GridLayout(2, 1, 20, 0));

        JPanel panel_1 = new JPanel();
        panel_1.setOpaque(false);
        panel.add(panel_1);
        panel_1.setLayout(new GridLayout(2, 0, 0, 0));

        lblMusica.setText("MFRockola");
        lblMusica.setHorizontalAlignment(SwingConstants.CENTER);
        lblMusica.setForeground(Color.WHITE);
        lblMusica.setFont(new Font("Calibri", Font.BOLD, 23));
        panel_1.add(lblMusica);


        panel_1.add(objeto.selectorMusica);

        labelPublicidad = new JLabel(new ImageIcon(String.format("%s\\%s", configuraciones.getDireccionImagenes(),directorioPublicidad[0])));
        labelPublicidad.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelPublicidad);

        getContentPane().add(panelFondo);

        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(resolucion);
        setVisible(true);

        //lista.setVisible(false);

        repro.embeddedMediaPlayer.addMediaPlayerEventListener(new manejadorDeReproductor());

        //contenedorVideo.setBounds(0, 0, ancho, alto);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if(configuraciones.getClickCreditos()==0 && e.isMetaDown() == false )
                {
                    creditos = creditos + configuraciones.getCantidadCreditos();
                    lblcreditos.setText(String.format("Creditos: %d", creditos));
                    agregarMonedasYCreditos();
                    lblcreditos.setForeground(Color.WHITE);
                }
                if (e.isMetaDown() && configuraciones.getClickCreditos() == 1)
                {
                    creditos = creditos + configuraciones.getCantidadCreditos();
                    lblcreditos.setText(String.format("Creditos: %d", creditos));
                    agregarMonedasYCreditos();
                    lblcreditos.setForeground(Color.WHITE);
                }


            }
        });
        this.addKeyListener(new manejadorDeTeclas());
        panelInferior.addKeyListener(new manejadorDeTeclas());
    }

    public String cambiarImagen()
    {
        String imagen = directorioPublicidad[numeroAleatorio.nextInt(directorioPublicidad.length)];

        return imagen;
    }

    public void pantallaCompleta()
    {
        if (isFullScreen == false)
        {
            barras.setVisible(false);
            lista.setVisible(false);
            panel.setVisible(false);
            contenedorVideo.setBounds(0, 0, ancho, alto);
            isFullScreen = true;
        }
        else
        {
            contenedorVideo.setBounds(ancho-530, alto-(alto*94/100),500, 283);
            barras.setVisible(true);
            lista.setVisible(true);
            panel.setVisible(true);
            isFullScreen = false;
        }
    }

    public static void main(String [] args)
    {
        Interfaz aplicacion = new Interfaz();
    }

    private class manejadorDeTeclas extends KeyAdapter
    {
        public void keyReleased(KeyEvent evento)
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

                if (numero >= listado.getListaMusicas().length)
                {
                    lblcreditos.setText("Musica no encontrada");
                    objeto.reproducir = false;
                    objeto.reiniciarValores();
                    objeto.selectorMusica.setText("- - - -");
                    temporizadorLblPublicidad.start();

                }
                else
                {
                    if ((creditos > 0 && condicion == true)||(creditosLibres == true && condicion == true))
                    {
                        if (listaReproduccion.obtenerCancionAReproducir()==null)
                        {
                            lista.setSelectedIndex(numero);
                            Cancion cancionAReproducir =  (Cancion) lista.getSelectedValue();

                            listaReproduccion.agregarCanciones(cancionAReproducir);
                            repro.reproducirMusica(listaReproduccion.obtenerGenero(),listaReproduccion.obtenerCancionAReproducir());
                            if (creditosLibres== false)
                            {
                                --creditos;
                                lblcreditos.setText(String.format("%s: %d","Creditos",creditos));
                            }
                            objeto.reproducir = false;
                            objeto.reiniciarValores();
                            objeto.selectorMusica.setText("- - - -");
                            prohibir.agregarProhibido(numero);
                            lblMusica.setText(String.format("%04d - %s",listaReproduccion.obtenerNumero()
                                    ,listaReproduccion.obtenerCancionAReproducir()));
                            labelCancionEnRepro.setText(String.format("%04d - %s",
                                    listaReproduccion.obtenerNumero(), listaReproduccion.obtenerCancionAReproducir()));
                        }
                        else
                        {
                            lista.setSelectedIndex(numero);
                            Cancion cancionAReproducir = (Cancion) lista.getSelectedValue();
                            //Cancion cancion = new Cancion(numero, cancionAReproducir);
                            listaReproduccion.agregarCanciones(cancionAReproducir);
                            if (creditosLibres == false)
                            {
                                --creditos;
                                lblcreditos.setForeground(Color.WHITE);
                                lblcreditos.setText(String.format("%s: %d","Creditos",creditos));
                            }
                            objeto.reproducir = false;
                            objeto.reiniciarValores();
                            objeto.selectorMusica.setText("- - - -");
                            prohibir.agregarProhibido(numero);

                        }
                    }
                    else
                    {
                        lblcreditos.setForeground(Color.RED);
                        lblcreditos.setText("Sin creditos o no se puede reproducir en 30 mins");
                        temporizadorLblPublicidad.start();
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
                        lblcreditos.setText("Creditos: Libres");
                    }
                    else
                    {
                        creditosLibres = false;
                        lblcreditos.setText(String.format("Creditos: %d", creditos));
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "Contrase�a Incorrecta");
            }
            else if (evento.getKeyCode()==123)
            {
                @SuppressWarnings("unused")
                Configuracion config = new Configuracion();
            }

            else if (evento.getKeyCode()==45 || evento.getKeyCode()==109)
            {
                if (lista.getSelectedIndex() - 10 < 0)
                {
                    lista.setSelectedIndex(0);
                    lista.ensureIndexIsVisible(lista.getSelectedIndex());
                }
                else
                {
                    lista.setSelectedIndex(lista.getSelectedIndex()-10);
                    lista.ensureIndexIsVisible(lista.getSelectedIndex());
                }

            }
            else if (evento.getKeyCode()==521 || evento.getKeyCode()==107)
            {
                if(lista.getSelectedIndex()+10 > listado.getListaMusicas().length)
                {
                    lista.setSelectedIndex(listado.getListaMusicas().length-1);
                    lista.ensureIndexIsVisible(lista.getSelectedIndex());
                }
                else
                {
                    lista.setSelectedIndex(lista.getSelectedIndex()+10);
                    lista.ensureIndexIsVisible(lista.getSelectedIndex());
                }
            }
            else if (evento.getKeyCode() == 71)
            {
                lista.setSelectedIndex(listado.subirGenero());
                lista.ensureIndexIsVisible(lista.getSelectedIndex());
            }
            else if (evento.getKeyCode() == 72)
            {
                lista.setSelectedIndex(listado.bajarGenero());
                lista.ensureIndexIsVisible(lista.getSelectedIndex());
            }
        }
    }

    private class manejadorDeReproductor extends MediaPlayerEventAdapter
    {
        public void finished(MediaPlayer mediaPlayer)
        {
            listaReproduccion.quitarMusica();

            if (listaReproduccion.obtenerCancionAReproducir() == null)
            {
                repro.embeddedMediaPlayer.playMedia("C:\\creditos.jpg");
                lblMusica.setText("MFRockola");
                labelCancionEnRepro.setText("MFRockola");
            }
            else
            {
                repro.reproducirMusica(listaReproduccion.obtenerGenero(), listaReproduccion.obtenerCancionAReproducir());
                lblMusica.setText(listaReproduccion.obtenerCancionAReproducir());
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
                    this.configuraciones.getDireccionMusicas(),
                    this.configuraciones.getDireccionVideos(),
                    this.configuraciones.getDireccionImagenes(),
                    this.configuraciones.getDireccionVlc(),
                    this.configuraciones.getDireccionVideoPromocional(),
                    this.configuraciones.getMusicAleatoria(),
                    this.configuraciones.getReinicioMusicas(),
                    this.configuraciones.getCantidadCreditos(),
                    this.configuraciones.isLibre(),
                    this.configuraciones.isVideoPromocional(),
                    this.configuraciones.getClickCreditos(),
                    this.configuraciones.isVideoPromocional(),
                    this.configuraciones.getTeclaSubirLista(),
                    this.configuraciones.getTeclaBajarLista(),
                    this.configuraciones.getTeclaSubirGenero(),
                    this.configuraciones.getTeclaBajarGenero(),
                    this.configuraciones.getTeclaPantallaCompleta(),
                    this.configuraciones.getTeclaBorrar(),
                    this.configuraciones.getTeclaCambiarLista(),
                    creditosASubir,
                    monedasASubir,
                    this.configuraciones.getDireccionFondo(),
                    this.configuraciones.isMostrarPublicidad(),
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

    public void activarListaReproduccion()
    {
        int delay = 6000;

        Timer temporizadorListaReproduccion = new Timer(delay, new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                lista.setListData(listado.getListaMusicas());
            }
        });

        temporizadorListaReproduccion.setRepeats(false);

        temporizadorListaReproduccion.start();
    }
}
