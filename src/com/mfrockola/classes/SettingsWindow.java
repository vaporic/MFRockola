package com.mfrockola.classes;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.net.URL;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import static com.mfrockola.classes.Utils.SELECT_VIDEO;

@SuppressWarnings("serial")
public class SettingsWindow extends JFrame {

	private ObjectOutputStream salida;
	private UserSettingsManagement mUserSettingsManagement = new UserSettingsManagement();
	private UserSettings mUserSettings;

	private JTextField textFieldVideos;
	private JTextField textFieldVideosParaMp3;
	private JTextField textFieldVlc;
	private JTextField textFieldCantCreditos;
	private JTextField textFieldMusicaAleatoria;
	private JTextField textFieldReinicioMusicas;
	private JTextField textFieldVideoPromocional;
	private TextFieldKey textFieldSubirL;
	private TextFieldKey textFieldBajarL;
	private TextFieldKey textFieldSubirGenero;
	private TextFieldKey textFieldBajarGenero;
	private TextFieldKey textFieldPantallaCompleta;
	private TextFieldKey textFieldBorrar;
	private TextFieldKey textFieldSaltarCancion;
	private TextFieldKey textFieldAgregarCreditos;
	private TextFieldKey textFieldBorrarCredito;
	private JPasswordField passwordField;
	private JTextField textFieldDirFondos;
	private JLabel labelCreditosUsados;
	private JLabel labelMonedasInsertadas;
	private JLabel labelTextoDelPremio;
	private JCheckBox checkBoxCreditosAdicionales;
	private JLabel labelNumeroDeCreditosAdicionales;
	private CustomTextField textFieldNumeroDeCreditosAdicionales;
	private JLabel labelCadaCantidadDeCreditos;
	private CustomTextField textFieldCadaCantidadDeCreditos;
	private JCheckBox checkBoxCreditosContinuos;
	private JCheckBox checkBoxPremio;
	private JLabel labelNumeroDePremios;
	private CustomTextField textFieldNumeroDePremios;
	private JLabel labelPremioCadaCreditos;
	private CustomTextField textFieldPremioCadaCreditos;
	private JLabel labelTipoDePremio;
	private JTextField textFieldTipoDePremio;
	private JCheckBox chckbxNewCheckBox;
    private JCheckBox checkBoxDefualtPromotionalVideo;
	private JCheckBox checkBoxFoundDefaultBackground;
	private JRadioButton rdbtnSi;
	private JRadioButton rdbtnNo;
	private boolean libre;
	private boolean videoPromocional;
    private boolean defaultVideoPromotional;
	private boolean defaultBackground;
	private boolean cancelMusic;
	private boolean selectVideoProm;
	private int clickCreditos;
	private int creditosGuardados;

	JFileChooser selectorArchivos = new JFileChooser();

	// variables de la pestaña apariencia

	private Color color1; // color de celda 1
	private Color color2; // color de celda 2
	private Color colorDeFuente; // color de fuentes
	private int typeFont;
	private JLabel labelColor1; // label de color de celda 1
	private JLabel labelColor2; // label de color de celda 2
	private JLabel labelFuente; // label de resultado del tipo de fuente seleccionada
	private JLabel labelSelector; // label del resultado del tamaño del seletor
	private JButton botonColor1; // boton selector de color de celda 1
	private JButton botonColor2; // boton selector de color de celda 2
	private JButton botonColorDeFuente; // boton selector del color de la fuente
	private JComboBox<String> comboBoxSelectorDeFuente; // comboBox de fuentes disponibles
	private JComboBox<String> comboBoxTamanioDeFuente; // comboBox de tamaño de fuente de celdas
	private JComboBox<String> comboBoxTamanioDeFuenteSelector; // comboBox de tamaño de fuente de selector de musicas
	private JCheckBox checkBoxFuenteCeldasNegrita; // checkbox para la fuente de las celdas negritsa

    URL urlBackground = this.getClass().getResource("/com/mfrockola/imagenes/fondo.jpg");

	public SettingsWindow()
	{
		setTitle("Configuración");

		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/mfrockola/imagenes/icono.png")));
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout(0, 0));

		try {
			File file = new File("config.mfr");

			if (!file.exists())
			{
				salida = new ObjectOutputStream(new FileOutputStream("config.mfr"));
				abrirRegConfigEscritura();
				agregarDatosRegConfig(true);
				cerrarRegConfig();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try
		{
			mUserSettingsManagement.openUserSettings();
			mUserSettings = mUserSettingsManagement.readUserSettings();
		}
		catch (NullPointerException excepcion)
		{
			abrirRegConfigEscritura();
		}

		JLabel bienvenido = new JLabel("Bienvenido a la configuración de MFRockola");
		panelPrincipal.add(bienvenido,BorderLayout.NORTH);

		JTabbedPane fichas = new JTabbedPane();
		fichas.setFocusable(false);

		Icon rockola = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/nombre.png"));
		JPanel panelBienvenido = new JPanel();
		panelBienvenido.setBackground(Color.WHITE);
		fichas.addTab("Bienvenido",null,panelBienvenido, "Bienvenido a MFRockola");
		panelBienvenido.setLayout(null);

		JTextPane txtpnBienvenidoAlPanel = new JTextPane();
		txtpnBienvenidoAlPanel.setText("Bienvenido al panel de configuración de MFRockola.\n\nAquí podrás modificar los aspectos"+" " +
				"básicos del funcionamiento de MFRockola como el precio de los créditos, la cantidad de tiempo necesario para la reproducción"+
				" aleatoria de una canción y también las teclas de acción en el teclado.\n\nPuedes oprimir la tecla ESC en cualquier momento para "+
				"salir de la configuración sin modificarla.");
		txtpnBienvenidoAlPanel.setEditable(false);
		txtpnBienvenidoAlPanel.setFocusable(false);
		txtpnBienvenidoAlPanel.setBounds(220, 50, 367, 132);
		panelBienvenido.add(txtpnBienvenidoAlPanel);

		JLabel lblGraciasPorUsar = new JLabel("Gracias por Usar y Adquirir MFRockola");
		lblGraciasPorUsar.setFont(new Font("Calibri", Font.BOLD, 21));
		lblGraciasPorUsar.setBounds(220, 11, 331, 27);
		panelBienvenido.add(lblGraciasPorUsar);
		JLabel label = new JLabel();
		label.setIcon(rockola);
		label.setBounds(10, 11, 200, 170);
		label.setHorizontalAlignment(JLabel.CENTER);
		panelBienvenido.add(label);

		JPanel panelInferior = new JPanel();
		panelPrincipal.add(panelInferior,BorderLayout.SOUTH);
		JButton buttonReestablecer = new JButton("Reestablecer");
		buttonReestablecer.setFocusable(false);
		panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JButton buttonGuardar = new JButton("Guardar");
		buttonGuardar.setFocusable(false);
		buttonGuardar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				abrirRegConfigEscritura();
				agregarDatosRegConfig(false);
				cerrarRegConfig();
				JOptionPane.showMessageDialog(null,"Configuración guardada correctamente");
				new Thread(new Splash()).start();
				Splash.moveMouse = true;
				dispose();
			}
		});
		panelInferior.add(buttonGuardar);

		panelInferior.add(buttonReestablecer);

		Icon tiempo = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/tiempos.png"));
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.WHITE);
		fichas.addTab("Tiempo",null,panel2, "Configuración de los tiempos de reproducción y pantalla completa");
		panel2.setLayout(null);
		JLabel labelTiempos = new JLabel();
		labelTiempos.setIcon(tiempo);
		labelTiempos.setBounds(10, 11, 200, 200);
		panel2.add(labelTiempos);

		JLabel lblConfiguracinDeLos = new JLabel("Configuración del Tiempo Interno");
		lblConfiguracinDeLos.setFont(new Font("Calibri", Font.BOLD, 21));
		lblConfiguracinDeLos.setBounds(220, 11, 295, 27);
		panel2.add(lblConfiguracinDeLos);

		JTextPane textPane = new JTextPane();
            textPane.setText("Este es el panel de configuración de los tiempos internos de MFRockola. Por ejemplo, el tiempo que debe pasar para reproducir una canción aleatoria y el tiempo necesario para poner la pantalla completa.");
		textPane.setEditable(false);
		textPane.setFocusable(false);
		textPane.setBounds(220, 50, 367, 48);
		panel2.add(textPane);

		JTextPane txtpnTiempoNecesarioPara = new JTextPane();
		txtpnTiempoNecesarioPara.setText("Tiempo necesario para reproducir una canción Aleatoria (Minutos)");
		txtpnTiempoNecesarioPara.setBounds(220, 107, 225, 48);
		txtpnTiempoNecesarioPara.setFocusable(false);
		txtpnTiempoNecesarioPara.setEditable(false);
		panel2.add(txtpnTiempoNecesarioPara);

		textFieldMusicaAleatoria = new JTextField();
		textFieldMusicaAleatoria.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldMusicaAleatoria.setFont(new Font("Calibri", Font.PLAIN, 13));
		textFieldMusicaAleatoria.setColumns(10);
		textFieldMusicaAleatoria.setBounds(455, 111, 86, 31);
		panel2.add(textFieldMusicaAleatoria);

		JTextPane txtpnTiempoNecesarioPara_1 = new JTextPane();
		txtpnTiempoNecesarioPara_1.setText("Límite de tiempo para poder repetir una misma canción");
		txtpnTiempoNecesarioPara_1.setFocusable(false);
		txtpnTiempoNecesarioPara_1.setEditable(false);
		txtpnTiempoNecesarioPara_1.setBounds(220, 163, 225, 63);
		panel2.add(txtpnTiempoNecesarioPara_1);

		textFieldReinicioMusicas = new JTextField();
		textFieldReinicioMusicas.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldReinicioMusicas.setFont(new Font("Calibri", Font.PLAIN, 13));
		textFieldReinicioMusicas.setColumns(10);
		textFieldReinicioMusicas.setBounds(455, 171, 86, 31);
		panel2.add(textFieldReinicioMusicas);

		textFieldVideoPromocional = new JTextField();
		textFieldVideoPromocional.setEnabled(false);
		textFieldVideoPromocional.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldVideoPromocional.setFont(new Font("Calibri", Font.PLAIN, 13));
		textFieldVideoPromocional.setColumns(10);
		textFieldVideoPromocional.setBounds(364, 245, 177, 23);
		panel2.add(textFieldVideoPromocional);

		final JButton buttonPathPromotionalVideo = new JButton("...");
		buttonPathPromotionalVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldVideoPromocional.setText(seleccionarArchivo(SELECT_VIDEO));
			}
		});
		buttonPathPromotionalVideo.setBounds(551, 245, 45, 23);
		buttonPathPromotionalVideo.setEnabled(false);
		panel2.add(buttonPathPromotionalVideo);

		chckbxNewCheckBox = new JCheckBox("Video Promocional");
		chckbxNewCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				if(chckbxNewCheckBox.isSelected())
				{
					videoPromocional = true;
                    checkBoxDefualtPromotionalVideo.setEnabled(true);
				}
				else
				{
					videoPromocional = false;
                    checkBoxDefualtPromotionalVideo.setEnabled(false);
                    textFieldVideoPromocional.setEditable(false);
                    textFieldVideoPromocional.setEnabled(false);
                    buttonPathPromotionalVideo.setEnabled(false);
				}
			}
		});
		chckbxNewCheckBox.setBackground(Color.WHITE);
		chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		chckbxNewCheckBox.setBounds(100, 245, 154, 23);
		panel2.add(chckbxNewCheckBox);

        checkBoxDefualtPromotionalVideo = new JCheckBox("Predeterminado");
        checkBoxDefualtPromotionalVideo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(checkBoxDefualtPromotionalVideo.isSelected()) {
                    defaultVideoPromotional = true;
                    textFieldVideoPromocional.setEditable(false);
                    textFieldVideoPromocional.setEnabled(false);
                    textFieldVideoPromocional.setText("C:\\MFRockola\\Videos para MP3\\promotional.mpg");
                    buttonPathPromotionalVideo.setEnabled(false);

                } else {
                    defaultVideoPromotional = false;
                    textFieldVideoPromocional.setEditable(true);
                    textFieldVideoPromocional.setEnabled(true);
                    textFieldVideoPromocional.setText("");
                    buttonPathPromotionalVideo.setEnabled(true);
                }
            }
        });
        checkBoxDefualtPromotionalVideo.setBackground(Color.WHITE);
        checkBoxDefualtPromotionalVideo.setHorizontalAlignment(SwingConstants.RIGHT);
        checkBoxDefualtPromotionalVideo.setBounds(251, 245, 110, 23);
        panel2.add(checkBoxDefualtPromotionalVideo);

		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.WHITE);
		fichas.addTab("Créditos",null,panel3,"Configuración de los créditos y sus precios");
		panel3.setLayout(null);

		JLabel lblConfiguracionDeCreditos = new JLabel("Configuración de Créditos");
		lblConfiguracionDeCreditos.setFont(new Font("Calibri", Font.BOLD, 21));
		lblConfiguracionDeCreditos.setBounds(220, 11, 297, 27);
		panel3.add(lblConfiguracionDeCreditos);

		Icon moneda = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/moneda.png"));
		JLabel labelMoneda = new JLabel();
		labelMoneda.setIcon(moneda);

		labelMoneda.setBounds(10, 17, 200, 200);
		panel3.add(labelMoneda);

		textFieldCantCreditos = new JTextField();
		textFieldCantCreditos.setFont(new Font("Calibri", Font.PLAIN, 13));
		textFieldCantCreditos.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCantCreditos.setBounds(455, 102, 86, 31);
		panel3.add(textFieldCantCreditos);
		textFieldCantCreditos.setColumns(10);

		ButtonGroup grupo = new ButtonGroup();
		rdbtnSi = new JRadioButton("Si");
		rdbtnSi.setBackground(Color.WHITE);
		rdbtnSi.setFocusable(false);
		rdbtnSi.addItemListener(new manejadorRadioButtons());
		rdbtnSi.setBounds(453, 142, 42, 23);
		panel3.add(rdbtnSi);

		rdbtnNo = new JRadioButton("No");
		rdbtnNo.setBackground(Color.WHITE);
		rdbtnNo.setFocusable(false);
		rdbtnNo.addItemListener(new manejadorRadioButtons());
		rdbtnNo.setBounds(497, 142, 44, 23);
		panel3.add(rdbtnNo);

		grupo.add(rdbtnSi);
		grupo.add(rdbtnNo);

		JTextPane txtpnCantidadDeCreditos = new JTextPane();
		txtpnCantidadDeCreditos.setText("Cantidad de creditos que recibe el cliente al introducir una moneda");
		txtpnCantidadDeCreditos.setEditable(false);
		txtpnCantidadDeCreditos.setFocusable(false);
		txtpnCantidadDeCreditos.setBounds(245, 99, 210, 39);
		panel3.add(txtpnCantidadDeCreditos);

		JLabel txtpnModoACreditos = new JLabel();
		txtpnModoACreditos.setText("Modo a creditos libre");
		txtpnModoACreditos.setHorizontalAlignment(JLabel.RIGHT);
		txtpnModoACreditos.setBounds(300, 140, 144, 25);
		panel3.add(txtpnModoACreditos);

		JTextPane txtpnEnEstePanel = new JTextPane();
		txtpnEnEstePanel.setText("En este panel podrás configurar lo referente a los créditos, como su precio, si se permiten libres o no.");
		txtpnEnEstePanel.setEditable(false);
		txtpnEnEstePanel.setFocusable(false);
		txtpnEnEstePanel.setBounds(220, 50, 367, 48);
		panel3.add(txtpnEnEstePanel);

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		JLabel labelTamanioDeLetraSelector = new JLabel("Tamaño de letra de la selección musical");
		labelTamanioDeLetraSelector.setBounds(291,171,200,14);
		labelTamanioDeLetraSelector.setHorizontalAlignment(SwingConstants.RIGHT);
		panel3.add(labelTamanioDeLetraSelector);

		String [] tamanios = {"8","9","10","11","12","13","14","16","18","20","24","28","36","48","50","55","60","65","70","72"};

		comboBoxTamanioDeFuenteSelector = new JComboBox(tamanios);
		comboBoxTamanioDeFuenteSelector.setBounds(498,166,43,23);
		panel3.add(comboBoxTamanioDeFuenteSelector);

		comboBoxTamanioDeFuenteSelector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarResultadoDeFuenteSelector();
			}
		});

		labelSelector = new JLabel("- - 2 8");
		labelSelector.setHorizontalAlignment(JLabel.CENTER);
		labelSelector.setBorder(border);
		labelSelector.setBounds(220,195,321,106);
		labelSelector.setOpaque(true);
		panel3.add(labelSelector);

		JPanel panel4 = new JPanel();
		panel4.setBackground(Color.WHITE);
		fichas.addTab("Carpetas",null,panel4, "Configuración de las carpetas y directorios de músicas y videos");
		panel4.setLayout(null);

		JButton buttonPathVideos = new JButton("...");
		buttonPathVideos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				textFieldVideos.setText(seleccionarDirectorio());
			}
		});

		buttonPathVideos.setBounds(563, 127, 32, 23);
		panel4.add(buttonPathVideos);


		JButton buttonPathVideosParaMp3 = new JButton("...");
		buttonPathVideosParaMp3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textFieldVideosParaMp3.setText(seleccionarDirectorio());
			}
		});
		buttonPathVideosParaMp3.setBounds(563, 161, 32, 23);
		panel4.add(buttonPathVideosParaMp3);

		JButton buttonPathVLC = new JButton("...");
		buttonPathVLC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				textFieldVlc.setText(seleccionarDirectorio());
			}
		});
		buttonPathVLC.setBounds(563, 195, 32, 23);
		panel4.add(buttonPathVLC);

		textFieldVideos = new JTextField();
		textFieldVideos.setText("");
		textFieldVideos.setColumns(10);
		textFieldVideos.setBounds(328, 128, 225, 20);
		panel4.add(textFieldVideos);

		textFieldVideosParaMp3 = new JTextField();
		textFieldVideosParaMp3.setText("");
		textFieldVideosParaMp3.setColumns(10);
		textFieldVideosParaMp3.setBounds(328, 162, 225, 20);
		panel4.add(textFieldVideosParaMp3);

		textFieldVlc = new JTextField();
		textFieldVlc.setColumns(10);
		textFieldVlc.setBounds(328, 196, 225, 20);
		panel4.add(textFieldVlc);

		JLabel lblVideos = new JLabel("Directorio de Música y Videos");
		lblVideos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVideos.setBounds(167, 131, 151, 14);
		panel4.add(lblVideos);

		JLabel lblVideosParaMp3 = new JLabel("Directorio de Videos para MP3");
		lblVideosParaMp3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVideosParaMp3.setBounds(157, 165, 161, 14);
		panel4.add(lblVideosParaMp3);

		JLabel lblVlc = new JLabel("Directorio del VLC");
		lblVlc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVlc.setBounds(217, 199, 101, 14);
		panel4.add(lblVlc);

		Icon carpeta = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/carpeta.png"));
		JLabel label_1 = new JLabel(carpeta);
		label_1.setBounds(10, 11, 180, 183);
		panel4.add(label_1);

		JLabel lblConfiguracinDeLos_1 = new JLabel("Configuración de los Directorios");
		lblConfiguracinDeLos_1.setFont(new Font("Calibri", Font.BOLD, 21));
		lblConfiguracinDeLos_1.setBounds(220, 11, 274, 27);
		panel4.add(lblConfiguracinDeLos_1);

		JTextPane txtpnEsteEsEl = new JTextPane();
		txtpnEsteEsEl.setText("Este es el panel de configuración de los directorios para un buen funcionamiento de MFRockola. Es importante tener en cuenta que debe manejar esta configuración con cuidado porque puede provocar que el programa se vuelva inestable.");
		txtpnEsteEsEl.setEditable(false);
		txtpnEsteEsEl.setFocusable(false);
		txtpnEsteEsEl.setBounds(220, 50, 367, 62);
		panel4.add(txtpnEsteEsEl);

		JTextPane txtpnAdvertenciaTengaCuidado = new JTextPane();
		txtpnAdvertenciaTengaCuidado.setForeground(Color.RED);
		txtpnAdvertenciaTengaCuidado.setText("Advertencia: Tenga cuidado al modificar el directorio del VLC. Consulte la documentación antes de realizar modificaciones");
		txtpnAdvertenciaTengaCuidado.setEditable(false);
		txtpnAdvertenciaTengaCuidado.setFocusable(false);
		txtpnAdvertenciaTengaCuidado.setBounds(10, 340, 585, 23);
		panel4.add(txtpnAdvertenciaTengaCuidado);

		Icon teclas = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/teclas.png"));
		JPanel panel5 = new JPanel();
		panel5.setBackground(Color.WHITE);
		fichas.addTab("Teclado y Mouse",null,panel5, "Configuración de las teclas de acción y mouse");
		panel5.setLayout(null);
		JLabel labelTeclas = new JLabel();
		labelTeclas.setIcon(teclas);
		labelTeclas.setBounds(10, 11, 200, 200);
		panel5.add(labelTeclas);

		JLabel lblConfiguracinDelTeclado = new JLabel("Configuración del Teclado y Mouse");
		lblConfiguracinDelTeclado.setFont(new Font("Calibri", Font.BOLD, 21));
		lblConfiguracinDelTeclado.setBounds(220, 11, 302, 27);
		panel5.add(lblConfiguracinDelTeclado);

		JTextPane txtpnEsteEsEl_1 = new JTextPane();
		txtpnEsteEsEl_1.setText("Este es el panel para configurar el teclado y el mouse. Si la configuración por defecto no le conviene puede cambiarla por cualquier tecla. Por comodidad del usuario, el bloque numérico siempre estará activado cuando inicie MFRockola.");
		txtpnEsteEsEl_1.setEditable(false);
		txtpnEsteEsEl_1.setFocusable(false);
		txtpnEsteEsEl_1.setBounds(220, 50, 367, 62);
		panel5.add(txtpnEsteEsEl_1);

		JLabel lblSubirbajrLista = new JLabel("Subir/Bajr Lista");
		lblSubirbajrLista.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSubirbajrLista.setBounds(262, 119, 103, 14);
		panel5.add(lblSubirbajrLista);

		textFieldSubirL = new TextFieldKey(this,mUserSettings.getTeclaSubirLista());
		textFieldSubirL.setBounds(375, 119, 50, 20);
		panel5.add(textFieldSubirL);

		JLabel label_2 = new JLabel("/");
		label_2.setBounds(435, 119, 4, 14);
		panel5.add(label_2);

		textFieldBajarL = new TextFieldKey(this,mUserSettings.getTeclaBajarLista());
		textFieldBajarL.setBounds(451, 119, 50, 20);
		panel5.add(textFieldBajarL);

		JLabel lblSubirBajarGenero = new JLabel("Subir/Bajr Genero");
		lblSubirBajarGenero.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSubirBajarGenero.setBounds(262, 144, 103, 14);
		panel5.add(lblSubirBajarGenero);

		textFieldSubirGenero = new TextFieldKey(this,mUserSettings.getTeclaSubirGenero());
		textFieldSubirGenero.setBounds(375, 144, 50, 20);
		panel5.add(textFieldSubirGenero);

		JLabel label_4 = new JLabel("/");
		label_4.setBounds(435, 144, 4, 14);
		panel5.add(label_4);

		textFieldBajarGenero = new TextFieldKey(this,mUserSettings.getTeclaBajarGenero());
		textFieldBajarGenero.setBounds(451, 144, 50, 20);
		panel5.add(textFieldBajarGenero);

		JLabel lblPantallaCompleta = new JLabel("Pantalla Completa");
		lblPantallaCompleta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPantallaCompleta.setBounds(262, 169, 103, 14);
		panel5.add(lblPantallaCompleta);

		textFieldPantallaCompleta = new TextFieldKey(this,mUserSettings.getTeclaPantallaCompleta());
		textFieldPantallaCompleta.setBounds(375, 169, 50, 20);
		panel5.add(textFieldPantallaCompleta);

		JLabel lblBorrar = new JLabel("Borrar");
		lblBorrar.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBorrar.setBounds(262, 194, 103, 14);
		panel5.add(lblBorrar);

		textFieldBorrar = new TextFieldKey(this,mUserSettings.getTeclaBorrar());
		textFieldBorrar.setColumns(10);
		textFieldBorrar.setBounds(375, 194, 50, 20);
		panel5.add(textFieldBorrar);

		JLabel labelSaltarCancion = new JLabel("Tecla para saltar canción");
		labelSaltarCancion.setBounds(167,219,200,23);
		labelSaltarCancion.setHorizontalAlignment(SwingConstants.RIGHT);
		panel5.add(labelSaltarCancion);

		textFieldSaltarCancion = new TextFieldKey(this,mUserSettings.getTeclaSaltarCancion()); // tecla 83 S
		textFieldSaltarCancion.setBounds(375, 219, 50, 20);
		textFieldSaltarCancion.setColumns(10);
		panel5.add(textFieldSaltarCancion);

		JLabel labelTeclaAgregarCredito = new JLabel("Tecla para agregar credito");
		labelTeclaAgregarCredito.setBounds(167,244,200,23);
		labelTeclaAgregarCredito.setHorizontalAlignment(SwingConstants.RIGHT);
		panel5.add(labelTeclaAgregarCredito);

		textFieldAgregarCreditos = new TextFieldKey(this,mUserSettings.getTeclaAgregarCredito()); // tecla 65 A
		textFieldAgregarCreditos.setColumns(10);
		textFieldAgregarCreditos.setBounds(375, 244, 50, 20);
		panel5.add(textFieldAgregarCreditos);

		JLabel labelTeclaParaBorrarCredito = new JLabel("Tecla para borrar credito"); // tecla 66 S
		labelTeclaParaBorrarCredito.setBounds(167,269,200,23);
		labelTeclaParaBorrarCredito.setHorizontalAlignment(SwingConstants.RIGHT);
		panel5.add(labelTeclaParaBorrarCredito);

		textFieldBorrarCredito = new TextFieldKey(this,mUserSettings.getTeclaBorrarCredito());
		textFieldBorrarCredito.setColumns(10);
		textFieldBorrarCredito.setBounds(375, 269, 50, 20);
		panel5.add(textFieldBorrarCredito);

		JLabel lblAgregarCredito = new JLabel("Agregar Credito");
		lblAgregarCredito.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAgregarCredito.setBounds(262, 294, 103, 14);
		panel5.add(lblAgregarCredito);

		final JRadioButton rdbtnClickIzquierdo = new JRadioButton("Click Izquierdo");


		rdbtnClickIzquierdo.setBackground(Color.WHITE);
		rdbtnClickIzquierdo.setBounds(375, 290, 95, 23);
		panel5.add(rdbtnClickIzquierdo);

		JRadioButton rdbtnClickDerecho = new JRadioButton("Click Derecho");
		rdbtnClickDerecho.setBackground(Color.WHITE);
		rdbtnClickDerecho.setBounds(475, 290, 89, 23);
		panel5.add(rdbtnClickDerecho);

		ButtonGroup grupoMouse = new ButtonGroup();

		rdbtnClickIzquierdo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				if (rdbtnClickIzquierdo.isSelected())
					clickCreditos = 0;
				else
					clickCreditos = 1;				}
		});

		grupoMouse.add(rdbtnClickIzquierdo);
		grupoMouse.add(rdbtnClickDerecho);

		JCheckBox checkBoxCancelMusic = new JCheckBox("Cancelar musica con click Derecho");
		checkBoxCancelMusic.setBounds(375,313,200, 23);
		checkBoxCancelMusic.setBackground(Color.WHITE);
		checkBoxCancelMusic.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (checkBoxCancelMusic.isSelected()) {
					cancelMusic = true;
					passwordField.setEnabled(true);
					passwordField.setText("");
					rdbtnClickIzquierdo.setSelected(true);
					lblAgregarCredito.setEnabled(false);
					rdbtnClickDerecho.setEnabled(false);
					rdbtnClickIzquierdo.setEnabled(false);
				} else {
					cancelMusic = false;
					passwordField.setEnabled(false);
					passwordField.setText("");
					lblAgregarCredito.setEnabled(true);
					rdbtnClickDerecho.setEnabled(true);
					rdbtnClickIzquierdo.setEnabled(true);
				}
			}
		});

		panel5.add(checkBoxCancelMusic);

		JLabel labelPassword = new JLabel("Contraseña para eliminar canciones");
		labelPassword.setBounds(165, 336, 200, 23);
		labelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		panel5.add(labelPassword);

		passwordField = new JPasswordField(5);
		passwordField.setBounds(375,336,200, 23);
		panel5.add(passwordField);

		JPanel panel6 = new JPanel();
		panel6.setBackground(Color.WHITE);
		fichas.addTab("Apariencia",null,panel6, "Apariencia general de MFRockola");
		panel6.setLayout(null);

		Icon apariencia = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/apariencia.png"));
		JLabel labelApariencia = new JLabel();
		labelApariencia.setIcon(apariencia);
		labelApariencia.setBounds(10, 11, 200, 152);
		panel6.add(labelApariencia);

		JLabel lblConfiguracinDeLa = new JLabel("Configuración de la Apariencia");
		lblConfiguracinDeLa.setFont(new Font("Calibri", Font.BOLD, 21));
		lblConfiguracinDeLa.setBounds(220, 11, 302, 27);
		panel6.add(lblConfiguracinDeLa);

		JTextPane txtpnEsteEsEl_2 = new JTextPane();
		txtpnEsteEsEl_2.setText("Este es el panel para configurar la apariencia de MFRockola. Se recomienda que el tamaño de la imagen de fondo, sea el mismo de la resolución de pantalla que vaya a usar.");
		txtpnEsteEsEl_2.setEditable(false);
		txtpnEsteEsEl_2.setFocusable(false);
		txtpnEsteEsEl_2.setBounds(220, 50, 367, 48);
		panel6.add(txtpnEsteEsEl_2);


		JButton buttonPathFunds = new JButton("...");
		buttonPathFunds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				textFieldDirFondos.setText(seleccionarArchivo(Utils.SELECT_IMAGE));
			}
		});
		buttonPathFunds.setBounds(532, 138, 30, 23);
		panel6.add(buttonPathFunds);

		checkBoxFoundDefaultBackground = new JCheckBox("Fondo Predeterminado");
		checkBoxFoundDefaultBackground.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				if(checkBoxFoundDefaultBackground.isSelected()) {
					defaultBackground = true;
					textFieldDirFondos.setEditable(false);
					buttonPathFunds.setEnabled(false);
					textFieldDirFondos.setText("");
				} else {
					defaultBackground = false;
					textFieldDirFondos.setEditable(true);
					buttonPathFunds.setEnabled(true);
					textFieldDirFondos.setText("");
				}
			}
		});

		checkBoxFoundDefaultBackground.setBackground(Color.WHITE);
		checkBoxFoundDefaultBackground.setHorizontalAlignment(SwingConstants.LEFT);
		checkBoxFoundDefaultBackground.setBounds(225, 112, 300, 14);
		panel6.add(checkBoxFoundDefaultBackground);

		JLabel lblDirectorioDeLos = new JLabel("Directorio de los Fondos");
		lblDirectorioDeLos.setBounds(230, 142, 115, 14);
		panel6.add(lblDirectorioDeLos);

		textFieldDirFondos = new JTextField();
		textFieldDirFondos.setBounds(355, 139, 167, 20);
		panel6.add(textFieldDirFondos);
		textFieldDirFondos.setColumns(10);

		JPanel panelCeldas = new JPanel();
		panelCeldas.setBorder(BorderFactory.createTitledBorder("Celdas de Canciones"));
		panelCeldas.setBounds(230,177,393,133);
		panelCeldas.setBackground(Color.WHITE);
		panelCeldas.setLayout(null);
		panel6.add(panelCeldas);

		JLabel labelCelda1 = new JLabel("Seleccionar Color #1");
		labelCelda1.setBounds(10, 20, 99, 14);
		panelCeldas.add(labelCelda1);

		JLabel labelCelda2 = new JLabel("Seleccionar Color #2");
		labelCelda2.setBounds(190, 20, 99, 14);
		panelCeldas.add(labelCelda2);

		labelColor1 = new JLabel("");
		labelColor1.setOpaque(true);
		labelColor1.setBorder(border);
		labelColor1.setBounds(144, 20, 20, 14);
		panelCeldas.add(labelColor1);

		labelColor2 = new JLabel("");
		labelColor2.setOpaque(true);
		labelColor2.setBorder(border);
		labelColor2.setBounds(324, 20, 20, 14);
		panelCeldas.add(labelColor2);

		botonColor1 = new JButton("...");
		botonColor1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				seleccionarColor(e);
			}
		});
		botonColor1.setBounds(112, 15, 30, 23);
		panelCeldas.add(botonColor1);

		botonColor2 = new JButton("...");
		botonColor2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				seleccionarColor(e);
			}
		});
		botonColor2.setBounds(292, 15, 30, 23);
		panelCeldas.add(botonColor2);

		// Busca las fuentes disponibles en el sistema

		String[] fuentes = null;
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fuentes = graphicsEnvironment.getAvailableFontFamilyNames();

		JLabel labelTipoDeLetra = new JLabel("Tipo de Letra");
		labelTipoDeLetra.setBounds(10,54,71,14);
		panelCeldas.add(labelTipoDeLetra);

		comboBoxSelectorDeFuente = new JComboBox<>(fuentes);
		comboBoxSelectorDeFuente.setBounds(86,50,250,23);
		panelCeldas.add(comboBoxSelectorDeFuente);

		comboBoxSelectorDeFuente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarResultadoDeFuente();
			}
		});

		comboBoxTamanioDeFuente = new JComboBox(tamanios);
		comboBoxTamanioDeFuente.setBounds(341,50,43,23);
		panelCeldas.add(comboBoxTamanioDeFuente);

		comboBoxTamanioDeFuente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarResultadoDeFuente();
			}
		});

		JLabel labelColorDeLetra = new JLabel("Color de Letra");
		labelColorDeLetra.setBounds(10,81,71,14);
		panelCeldas.add(labelColorDeLetra);

		botonColorDeFuente = new JButton("...");
		botonColorDeFuente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				seleccionarColor(e);
			}
		});
		botonColorDeFuente.setBounds(86, 77, 30, 23);
		panelCeldas.add(botonColorDeFuente);

		checkBoxFuenteCeldasNegrita = new JCheckBox("Negrita");
		checkBoxFuenteCeldasNegrita.setBackground(Color.WHITE);
		checkBoxFuenteCeldasNegrita.setOpaque(true);
		checkBoxFuenteCeldasNegrita.setHorizontalTextPosition(SwingConstants.LEFT);
		checkBoxFuenteCeldasNegrita.setHorizontalAlignment(SwingConstants.RIGHT);
		checkBoxFuenteCeldasNegrita.setFocusable(false);
		checkBoxFuenteCeldasNegrita.setBounds(20,100,99,23);
		panelCeldas.add(checkBoxFuenteCeldasNegrita);

		checkBoxFuenteCeldasNegrita.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				actualizarResultadoDeFuente();
			}
		});

		labelFuente = new JLabel("Click para cambiar color");
		labelFuente.setHorizontalAlignment(JLabel.CENTER);
		labelFuente.setBorder(border);
		labelFuente.setBounds(120,77,264,46);
		labelFuente.setOpaque(true);
		panelCeldas.add(labelFuente);

		labelFuente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (labelFuente.getBackground().equals(color1)) {
					labelFuente.setBackground(color2);
				} else {
					labelFuente.setBackground(color1);
				}
			}
		});

		JPanel panel7 = new JPanel();
		panel7.setBackground(Color.WHITE);
		fichas.addTab("Estadísticas",null,panel7, "Visualización de las estadísticas de uso");
		panel7.setLayout(null);

		Icon estadisticas = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/estadistica.png"));
		JLabel labelEstadisticas = new JLabel();
		labelEstadisticas.setIcon(estadisticas);
		labelEstadisticas.setBounds(10, 11, 200, 152);
		panel7.add(labelEstadisticas);

		JLabel lblConfiguracinEstadisticas = new JLabel("Configuración de Estadísticas");
		lblConfiguracinEstadisticas.setFont(new Font("Calibri", Font.BOLD, 21));
		lblConfiguracinEstadisticas.setBounds(220, 11, 302, 27);
		panel7.add(lblConfiguracinEstadisticas);

		JTextPane txtPaneEstadis = new JTextPane();
		txtPaneEstadis.setText("Este es el panel para configurar y visualizar las estadísticas de uso de MFRockola. " +
				"Encontrarás las monedas introducidas, un monto aproximado de las ganancias generadas y también cuantas " +
				"canciones aleatorias fueron reproducidas.");
		txtPaneEstadis.setEditable(false);
		txtPaneEstadis.setFocusable(false);
		txtPaneEstadis.setBounds(220, 50, 367, 62);
		panel7.add(txtPaneEstadis);

		JLabel lblCantidadDeCreditos = new JLabel("Cantidad de Creditos Usados");
		lblCantidadDeCreditos.setBounds(328, 124, 139, 14);
		panel7.add(lblCantidadDeCreditos);

		labelCreditosUsados = new JLabel("0");
		labelCreditosUsados.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelCreditosUsados.setBounds(475, 124, 47, 14);
		panel7.add(labelCreditosUsados);

		JLabel lblCantidadDeMonedas = new JLabel("Cantidad de Monedas Insertadas");
		lblCantidadDeMonedas.setBounds(308, 149, 159, 14);
		panel7.add(lblCantidadDeMonedas);

		labelMonedasInsertadas = new JLabel("0");
		labelMonedasInsertadas.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelMonedasInsertadas.setBounds(475, 149, 47, 14);
		panel7.add(labelMonedasInsertadas);

		JButton buttonMasPopulares = new JButton("Canciones más populares");
		buttonMasPopulares.setBounds(308,174,181,25);
		panel7.add(buttonMasPopulares);

		buttonMasPopulares.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SQLiteEditor();
			}
		});

		JPanel panel8 = new JPanel();
		panel8.setBackground(Color.WHITE);
		fichas.addTab("Promociones",null,panel8, "Configuración de las promociones de MFRockola");
		panel8.setLayout(null);

		Icon promociones = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/promociones.png"));
		JLabel labelPromociones = new JLabel();
		labelPromociones.setIcon(promociones);
		labelPromociones.setBounds(10, 11, 200, 205);
		panel8.add(labelPromociones);

		JLabel lblConfiguracinPromociones = new JLabel("Configuración de Promociones");
		lblConfiguracinPromociones.setFont(new Font("Calibri", Font.BOLD, 21));
		lblConfiguracinPromociones.setBounds(220, 11, 302, 27);
		panel8.add(lblConfiguracinPromociones);

		JTextPane txtPanePromos = new JTextPane();
		txtPanePromos.setText("Este es el panel para configurar las promociones de MFRockola.");
		txtPanePromos.setEditable(false);
		txtPanePromos.setFocusable(false);
		txtPanePromos.setBounds(220, 50, 367, 23);
		panel8.add(txtPanePromos);

		TitledBorder titledBorderCreditos;
		titledBorderCreditos = BorderFactory.createTitledBorder("Creditos");

		JPanel panelCreditosAdicionales = new JPanel();
		panelCreditosAdicionales.setBackground(Color.WHITE);
		panelCreditosAdicionales.setBorder(titledBorderCreditos);
		panelCreditosAdicionales.setBounds(220,78,300,90);
		panelCreditosAdicionales.setLayout(null);
		panel8.add(panelCreditosAdicionales);

		checkBoxCreditosAdicionales = new JCheckBox("Creditos adicionales");
		checkBoxCreditosAdicionales.setBounds(5,20, 139, 14);
		checkBoxCreditosAdicionales.setBackground(Color.WHITE);
		checkBoxCreditosAdicionales.setFocusable(false);
		panelCreditosAdicionales.add(checkBoxCreditosAdicionales);

		checkBoxCreditosAdicionales.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (checkBoxCreditosAdicionales.isSelected()) {
					labelNumeroDeCreditosAdicionales.setEnabled(true);
					textFieldNumeroDeCreditosAdicionales.setEnabled(true);
					labelCadaCantidadDeCreditos.setEnabled(true);
					textFieldCadaCantidadDeCreditos.setEnabled(true);
					checkBoxCreditosContinuos.setEnabled(true);
				} else {
					labelNumeroDeCreditosAdicionales.setEnabled(false);
					textFieldNumeroDeCreditosAdicionales.setEnabled(false);
					textFieldNumeroDeCreditosAdicionales.setText("0");
					labelCadaCantidadDeCreditos.setEnabled(false);
					textFieldCadaCantidadDeCreditos.setEnabled(false);
					textFieldCadaCantidadDeCreditos.setText("0");
					checkBoxCreditosContinuos.setEnabled(false);
					checkBoxCreditosContinuos.setSelected(false);
				}
			}
		});

		labelNumeroDeCreditosAdicionales = new JLabel("Nº de Creditos Adicionales");
		labelNumeroDeCreditosAdicionales.setBounds(8,43,139,14);
		labelNumeroDeCreditosAdicionales.setEnabled(false);
		panelCreditosAdicionales.add(labelNumeroDeCreditosAdicionales);

		textFieldNumeroDeCreditosAdicionales = new CustomTextField(2);
		textFieldNumeroDeCreditosAdicionales.setBounds(138,43,20,14);
		textFieldNumeroDeCreditosAdicionales.setEnabled(false);
		panelCreditosAdicionales.add(textFieldNumeroDeCreditosAdicionales);

		labelCadaCantidadDeCreditos = new JLabel("Cada Nº de creditos introducidos");
		labelCadaCantidadDeCreditos.setBounds(8,66,160,14);
		labelCadaCantidadDeCreditos.setEnabled(false);
		panelCreditosAdicionales.add(labelCadaCantidadDeCreditos);

		textFieldCadaCantidadDeCreditos = new CustomTextField(2);
		textFieldCadaCantidadDeCreditos.setBounds(168,66,20,14);
		textFieldCadaCantidadDeCreditos.setEnabled(false);
		panelCreditosAdicionales.add(textFieldCadaCantidadDeCreditos);

		checkBoxCreditosContinuos = new JCheckBox("Continuos");
		checkBoxCreditosContinuos.setBounds(188, 66,100,14);
		checkBoxCreditosContinuos.setEnabled(false);
		checkBoxCreditosContinuos.setBackground(Color.WHITE);
		panelCreditosAdicionales.add(checkBoxCreditosContinuos);

		TitledBorder titledBorderPremios;
		titledBorderPremios = BorderFactory.createTitledBorder("Premios");

		JPanel panelPremios = new JPanel();
		panelPremios.setBackground(Color.WHITE);
		panelPremios.setBorder(titledBorderPremios);
		panelPremios.setBounds(220,173,300,136);
		panelPremios.setLayout(null);
		panel8.add(panelPremios);

		checkBoxPremio = new JCheckBox("Ganar Premio");
		checkBoxPremio.setBounds(5, 20, 139, 14);
		checkBoxPremio.setBackground(Color.WHITE);
		checkBoxPremio.setFocusable(false);
		panelPremios.add(checkBoxPremio);

		checkBoxPremio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (checkBoxPremio.isSelected()) {
					labelNumeroDePremios.setEnabled(true);
					textFieldNumeroDePremios.setEnabled(true);
					labelPremioCadaCreditos.setEnabled(true);
					textFieldPremioCadaCreditos.setEnabled(true);
					labelTipoDePremio.setEnabled(true);
					textFieldTipoDePremio.setEnabled(true);
					textFieldTipoDePremio.setText("");
					labelTextoDelPremio.setEnabled(true);
					labelTextoDelPremio.setText("Inserte el tipo y cantidad del premio");
				} else {
					labelNumeroDePremios.setEnabled(false);
					textFieldNumeroDePremios.setEnabled(false);
					textFieldNumeroDePremios.setText("0");
					labelPremioCadaCreditos.setEnabled(false);
					textFieldPremioCadaCreditos.setEnabled(false);
					textFieldPremioCadaCreditos.setText("0");
					labelTipoDePremio.setEnabled(false);
					textFieldTipoDePremio.setEnabled(false);
					textFieldTipoDePremio.setText("");
					labelTextoDelPremio.setEnabled(false);
					labelTextoDelPremio.setText("No se entregarán premios");
				}
			}
		});

		labelNumeroDePremios = new JLabel("Nº de Premios");
		labelNumeroDePremios.setBounds(8,43,139,14);
		labelNumeroDePremios.setEnabled(false);
		panelPremios.add(labelNumeroDePremios);

		textFieldNumeroDePremios = new CustomTextField(2);
		textFieldNumeroDePremios.setBounds(83,43,20,14);
		textFieldNumeroDePremios.setEnabled(false);
		panelPremios.add(textFieldNumeroDePremios);

		labelPremioCadaCreditos = new JLabel("Nº de Creditos por premio");
		labelPremioCadaCreditos.setBounds(8,66,139,14);
		labelPremioCadaCreditos.setEnabled(false);
		panelPremios.add(labelPremioCadaCreditos);

		textFieldPremioCadaCreditos = new CustomTextField(3);
		textFieldPremioCadaCreditos.setBounds(138,66,30,14);
		textFieldPremioCadaCreditos.setEnabled(false);
		panelPremios.add(textFieldPremioCadaCreditos);

		labelTipoDePremio = new JLabel("Tipo de Premio");
		labelTipoDePremio.setBounds(8,89,139,14);
		labelTipoDePremio.setEnabled(false);
		panelPremios.add(labelTipoDePremio);

		textFieldTipoDePremio = new JTextField();
		textFieldTipoDePremio.setBounds(83,89,150,14);
		textFieldTipoDePremio.setEnabled(false);
		panelPremios.add(textFieldTipoDePremio);

		textFieldTipoDePremio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				labelTextoDelPremio.setText("Ganaste " + textFieldNumeroDePremios.getText() +" "+ textFieldTipoDePremio.getText());
			}
		});

		textFieldNumeroDePremios.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				labelTextoDelPremio.setText("Ganaste " + textFieldNumeroDePremios.getText() +" "+ textFieldTipoDePremio.getText());
			}
		});

		labelTextoDelPremio = new JLabel("Ganaste 1 Doritos");
		labelTextoDelPremio.setBounds(8,112,290,14);
		labelTextoDelPremio.setEnabled(false);
		labelTextoDelPremio.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelPremios.add(labelTextoDelPremio);

		panelPrincipal.add(fichas);

		getContentPane().add(panelPrincipal);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 642, 475); // posicion de la ventana

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width-getWidth())/2,(screenSize.height-getHeight())/2);

		setResizable(false);
		setVisible(true);

		textFieldMusicaAleatoria.setText(String.format("%s",mUserSettings.getMusicAleatoria()));
		textFieldReinicioMusicas.setText(String.format("%s",mUserSettings.getReinicioMusicas()));

		textFieldCantCreditos.setText(String.format("%s", mUserSettings.getCantidadCreditos()));
		textFieldVideoPromocional.setEnabled(mUserSettings.isVideoPromocional());
		textFieldVideoPromocional.setEditable(mUserSettings.isVideoPromocional());
		buttonPathPromotionalVideo.setEnabled(mUserSettings.isVideoPromocional());
		chckbxNewCheckBox.setSelected(mUserSettings.isVideoPromocional());
		textFieldVideos.setText(String.format("%s", mUserSettings.getPathSongs()));
		textFieldVlc.setText(String.format("%s", mUserSettings.getPathVlc()));
		textFieldVideosParaMp3.setText(String.format("%s", mUserSettings.getPathVideosMp3()));
		textFieldVideoPromocional.setText(String.format("%s", mUserSettings.getDireccionVideoPromocional()));
		textFieldSubirL.setText(Utils.printKeyCharCode(mUserSettings.getTeclaSubirLista()));
		textFieldBajarL.setText(Utils.printKeyCharCode(mUserSettings.getTeclaBajarLista()));
		textFieldSubirGenero.setText(Utils.printKeyCharCode(mUserSettings.getTeclaSubirGenero()));
		textFieldBajarGenero.setText(Utils.printKeyCharCode(mUserSettings.getTeclaBajarGenero()));
		textFieldPantallaCompleta.setText(Utils.printKeyCharCode(mUserSettings.getTeclaPantallaCompleta()));
		textFieldBorrar.setText(Utils.printKeyCharCode(mUserSettings.getTeclaBorrar()));
		textFieldSaltarCancion.setText(Utils.printKeyCharCode(mUserSettings.getTeclaSaltarCancion()));
		textFieldAgregarCreditos.setText(Utils.printKeyCharCode(mUserSettings.getTeclaAgregarCredito()));
		textFieldBorrarCredito.setText(Utils.printKeyCharCode(mUserSettings.getTeclaBorrarCredito()));
		labelCreditosUsados.setText(String.format("%s", mUserSettings.getCantidadCreditosUsados()));
		labelMonedasInsertadas.setText(String.format("%s", mUserSettings.getCantidadMonedasInsertadas()));
		checkBoxFoundDefaultBackground.setSelected(mUserSettings.isDefaultBackground());
        checkBoxDefualtPromotionalVideo.setSelected(mUserSettings.isDefaultPromotionalVideo());
		checkBoxCancelMusic.setSelected(mUserSettings.isCancelMusic());
		passwordField.setText(mUserSettings.getPassword());
		if (mUserSettings.isCancelMusic() == true) {
			passwordField.setEnabled(true);
		} else {
			passwordField.setEnabled(false);
		}

		if(mUserSettings.isLibre() == true)
			rdbtnSi.setSelected(true);
		else
			rdbtnNo.setSelected(true);

		if (mUserSettings.getClickCreditos() == 0)
		{
			rdbtnClickIzquierdo.setSelected(true);
			clickCreditos = 0;
		}
		else
		{
			rdbtnClickDerecho.setSelected(true);
			clickCreditos = 1;
		}

		labelColor1.setBackground(mUserSettings.getColor1());
		labelColor2.setBackground(mUserSettings.getColor2());

		color1 = mUserSettings.getColor1();
		color2 = mUserSettings.getColor2();

		textFieldDirFondos.setText(mUserSettings.getDireccionFondo().getFile());

		labelFuente.setBackground(mUserSettings.getColor1());
		labelFuente.setForeground(mUserSettings.getFontCeldasColor());

		labelSelector.setFont(new Font("Console",Font.BOLD,mUserSettings.getFontSelectorSize()));

		comboBoxSelectorDeFuente.setSelectedItem(mUserSettings.getFontCeldasName());

		comboBoxTamanioDeFuente.setSelectedItem(String.valueOf(mUserSettings.getFontCeldasSize()));

		comboBoxTamanioDeFuenteSelector.setSelectedItem(String.valueOf(mUserSettings.getFontSelectorSize()));

		colorDeFuente = mUserSettings.getFontCeldasColor();

		typeFont = mUserSettings.getFontCeldasNegrita();

		if (typeFont == Font.BOLD) {
			checkBoxFuenteCeldasNegrita.setSelected(true);
		} else {
			checkBoxFuenteCeldasNegrita.setSelected(false);
		}

		if (mUserSettings.isAgregarAdicional()) {
			checkBoxCreditosAdicionales.setSelected(true);
		} else {
			checkBoxCreditosAdicionales.setSelected(false);
		}

		textFieldNumeroDeCreditosAdicionales.setText(String.format("%s",mUserSettings.getNumeroDeCreditosAdicionales()));

		textFieldCadaCantidadDeCreditos.setText(String.format("%s", mUserSettings.getCadaCantidadDeCreditos()));

		checkBoxCreditosContinuos.setSelected(mUserSettings.isCreditosContinuos());

		if (mUserSettings.isEntregarPremio()) {
			checkBoxPremio.setSelected(true);
		} else {
			checkBoxPremio.setSelected(false);
		}

		textFieldNumeroDePremios.setText(String.valueOf(mUserSettings.getCantidadDePremios()));

		textFieldPremioCadaCreditos.setText(String.valueOf(mUserSettings.getCantidadDeCreditosPorPremio()));

		textFieldTipoDePremio.setText(String.valueOf(mUserSettings.getTipoDePremio()));

		if (mUserSettings.getTipoDePremio().equals("")) {
			labelTextoDelPremio.setText("No se entregarán premios");
		} else {
			labelTextoDelPremio.setText("Ganaste " + mUserSettings.getCantidadDePremios() +" "+ mUserSettings.getTipoDePremio());
		}

		creditosGuardados = mUserSettings.getCreditosGuardados();
	}

	private class manejadorRadioButtons implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			if (rdbtnSi.isSelected()==true)
				libre = true;
			else
				libre = false;
		}

	}

	private void actualizarResultadoDeFuente() {
		if (checkBoxFuenteCeldasNegrita.isSelected()) {
			typeFont = Font.BOLD;
			labelFuente.setFont(new Font(comboBoxSelectorDeFuente.getSelectedItem().toString(),typeFont,
					Integer.parseInt(comboBoxTamanioDeFuente.getSelectedItem().toString())));
		} else {
			typeFont = Font.PLAIN;
			labelFuente.setFont(new Font(comboBoxSelectorDeFuente.getSelectedItem().toString(),typeFont,
					Integer.parseInt(comboBoxTamanioDeFuente.getSelectedItem().toString())));
		}
	}

	private void actualizarResultadoDeFuenteSelector() {
			typeFont = Font.BOLD;
			labelSelector.setFont(new Font("Consolas",typeFont,
					Integer.parseInt(comboBoxTamanioDeFuenteSelector.getSelectedItem().toString())));
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

	public void agregarDatosRegConfig(boolean firstOpen)
	{
		UserSettings sUserSettings;

		if (firstOpen) {
			try
			{
				System.out.println(urlBackground);

				// + 107
				// - 109
				// / 106
				// * 111
				// F 70
				// C 67
				// N 78
				// A 65
				// B 66

				sUserSettings = new UserSettings(
						"C:\\videos", // path of videos
						"C:\\MFRockola\\Videos para MP3",
						"C:\\Program Files\\VideoLAN\\VLC", // path of VLC
						"C:\\MFRockola\\Videos para MP3\\promotional.mpg", // path promotional video
						1, // time in minutes of random music
						15, // time in minutes of reset music
						1, // credits per click
						false, // is credit free?
						true, // is promotional video?
						true, // is default promotional video?
						0, // click credits 1 = rigth click
						111, // key up list
						106, // key down list
						109, // key up gender
						107, // key down gender
						70, // key fullscreen
						67, // key delete number selector
						78, // key end song
						65, // key add credit
						66, // key delete credit
						false, // cancel music
						"",
						0, // cantidad de creditos usados
						0, // cantidad de monedas insertadas
						true, // is default background?
						urlBackground, // path background
						new Color(102,204,255), // color 1 of list
						new Color(255,255,255), // color 2 of list
						"Consolas", // tipo de fuente
						20, // tamaño de fuente
						50, // tamaño fuente selector
						Color.BLACK, // color de fuente
						Font.BOLD, // Fuente negrita
						false, // is agregarAdicional?
						0, // numero de creditos adicionales
						0, // cada cantidad de creditos
						false, // is creditos continuos
						false, // is entregarPremio?
						0,
						0,
						"",
						0
				);

				salida.writeObject(sUserSettings);
			} catch (Exception excepcion)
			{
				System.err.println("Error al escribir el archivo");
			}
		} else {
			try
			{
				URL url;
				if (defaultBackground) {
					url = this.getClass().getResource("/com/mfrockola/imagenes/fondo.jpg");
				} else {
					url = new URL("file:"+textFieldDirFondos.getText());
				}

				sUserSettings = new UserSettings(
						textFieldVideos.getText(),
						textFieldVideosParaMp3.getText(),
						textFieldVlc.getText(),
						textFieldVideoPromocional.getText(),
						Integer.parseInt(textFieldMusicaAleatoria.getText()),
						Integer.parseInt(textFieldReinicioMusicas.getText()),
						Integer.parseInt(textFieldCantCreditos.getText()),
						libre,
						videoPromocional,
                        defaultVideoPromotional,
						clickCreditos,
						textFieldSubirL.getKeyCode(),
						textFieldBajarL.getKeyCode(),
						textFieldSubirGenero.getKeyCode(),
						textFieldBajarGenero.getKeyCode(),
						textFieldPantallaCompleta.getKeyCode(),
						textFieldBorrar.getKeyCode(),
						textFieldSaltarCancion.getKeyCode(),
						textFieldAgregarCreditos.getKeyCode(),
						textFieldBorrarCredito.getKeyCode(),
						cancelMusic,
						new String(passwordField.getPassword()),
						Integer.parseInt(labelCreditosUsados.getText()),
						Integer.parseInt(labelMonedasInsertadas.getText()),
						defaultBackground,
						url,
						color1,
						color2,
						comboBoxSelectorDeFuente.getSelectedItem().toString(),
						Integer.parseInt(comboBoxTamanioDeFuente.getSelectedItem().toString()),
						Integer.parseInt(comboBoxTamanioDeFuenteSelector.getSelectedItem().toString()),
						colorDeFuente,
						typeFont,
						checkBoxCreditosAdicionales.isSelected(),
						Integer.parseInt(textFieldNumeroDeCreditosAdicionales.getText()),
						Integer.parseInt(textFieldCadaCantidadDeCreditos.getText()),
						checkBoxCreditosContinuos.isSelected(),
						checkBoxPremio.isSelected(),
						Integer.parseInt(textFieldNumeroDePremios.getText()),
						Integer.parseInt(textFieldPremioCadaCreditos.getText()),
						textFieldTipoDePremio.getText(),
						creditosGuardados
				);

				salida.writeObject(sUserSettings);
			} catch (IOException excepcion)
			{
				excepcion.printStackTrace();
			}
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

	public String seleccionarDirectorio ()
	{
		selectorArchivos.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int resultado = selectorArchivos.showOpenDialog(this);

		if (resultado == JFileChooser.CANCEL_OPTION)
		{
			JOptionPane.showMessageDialog(this, "Debe Seleccionar un Directorio");
		}

		File nombreDirectorio = selectorArchivos.getSelectedFile();

		if ((nombreDirectorio == null) || (nombreDirectorio.getName().equals("")))
			JOptionPane.showMessageDialog(this, "Directorio invalido");

		String directorio = nombreDirectorio.getAbsolutePath();

		return directorio;
	}

	public String seleccionarArchivo (int type)
	{

		String directorio = null;

		selectorArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);

		if (type == Utils.SELECT_IMAGE) {
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagen JPEG", "jpg");
			selectorArchivos.setFileFilter(filtro);
		}

		selectorArchivos.setDialogTitle("Seleccione el Archivo");

		int resultado = selectorArchivos.showOpenDialog(this);

		if (resultado == JFileChooser.APPROVE_OPTION)
		{
			File nombreArchivo = selectorArchivos.getSelectedFile();

			directorio = nombreArchivo.getAbsolutePath();
		}

		return directorio;
	}

	public void seleccionarColor(ActionEvent evento)
	{
		if (evento.getSource().equals(botonColor1))
		{
			color1 = JColorChooser.showDialog(null, "Seleccione el Color", Color.WHITE);
			labelColor1.setBackground(color1);
			labelFuente.setBackground(color1);
		}
		else if (evento.getSource().equals(botonColor2))
		{
			color2 = JColorChooser.showDialog(null, "Seleccione el Color", Color.WHITE);
			labelColor2.setBackground(color2);
			labelFuente.setBackground(color2);
		}
		else if (evento.getSource().equals(botonColorDeFuente)) {
			colorDeFuente = JColorChooser.showDialog(null, "Seleccionar el Color", Color.BLACK);
			labelFuente.setForeground(colorDeFuente);
		}
	}
}
