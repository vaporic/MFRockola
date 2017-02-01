package com.mfrockola.classes;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Angel C on 18/07/2016.
 */
public class SQLiteEditor extends JFrame {

    public static final int COLUMN_ID = 0;
    public static final int COLUMN_NAME = 1;
    public static final int COLUMN_TIMES = 2;
    public static final int COLUMN_LAST_DATE = 3;

    SQLiteConsultor sQLiteConsultor;

    ArrayList<SongDataBase> cancionesDB;

    Object[] data;

    // objetos de la interfaz

    private JLabel labelGenero;
    private JLabel labelArtista;
    private JLabel labelNumero;
    private JLabel labelVecesReproducida;
    private JLabel labelFecha;
    private JLabel labelLogo;

    private JList<Object> list;

    private JButton buttonDeleteDB;

    private Date date = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public SQLiteEditor () {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Canciones más populares");
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/mfrockola/imagenes/icono.png")));
        initComponents();
    }

    private void initComponents() {
        setBounds(0,0,500,340);

        JPanel mainContainer = new JPanel(null);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width-getWidth())/2,(screenSize.height-getHeight())/2);

        sQLiteConsultor = new SQLiteConsultor();

        list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);

        refreshList();

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setBounds(5,5,300,300);

        mainContainer.add(listScroller);

        labelNumero = new JLabel("Numero: ");
        labelGenero = new JLabel("Genero: ");
        labelArtista = new JLabel("Artista: ");
        labelVecesReproducida = new JLabel("Nº Reproducciones: ");
        labelFecha = new JLabel("Fecha: ");
        buttonDeleteDB = new JButton("Limpiar base de datos");

        labelNumero.setBounds(310,5,300,25);
        labelGenero.setBounds(310,30,300,25);
        labelArtista.setBounds(310,55,300,25);
        labelVecesReproducida.setBounds(310,80,300,25);
        labelFecha.setBounds(310,105,300,25);
        buttonDeleteDB.setBounds(310,130,180,25);

        Icon log = new ImageIcon(this.getClass().getResource("/com/mfrockola/imagenes/nombre.png"));
        labelLogo = new JLabel(log);
        labelLogo.setBounds(320,335-85,160,50);
        labelLogo.setHorizontalAlignment(SwingConstants.CENTER);

        mainContainer.add(labelNumero);
        mainContainer.add(labelGenero);
        mainContainer.add(labelArtista);
        mainContainer.add(labelVecesReproducida);
        mainContainer.add(labelFecha);
        mainContainer.add(buttonDeleteDB);
        mainContainer.add(labelLogo);

        add(mainContainer);
        setResizable(false);
        setVisible(true);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = list.getSelectedIndex();

                if (index != -1) {
                    SongDataBase cancionSeleccionada = cancionesDB.get(index);

                    date.setTime(cancionSeleccionada.getDate());

                    labelNumero.setText(String.format("Numero: %04d", cancionSeleccionada.getNumber()));
                    labelFecha.setText("Fecha: " + dateFormat.format(date));
                    labelVecesReproducida.setText("Nº Reproducciones: " + cancionSeleccionada.getTimes());
                    labelArtista.setText("Artista: " + cancionSeleccionada.getSinger());
                    labelGenero.setText("Genero: " + cancionSeleccionada.getGenre());
                } else {
                    labelNumero.setText("Numero: ");
                    labelFecha.setText("Fecha: ");
                    labelVecesReproducida.setText("Nº Reproducciones: ");
                    labelArtista.setText("Artista: ");
                    labelGenero.setText("Genero: ");
                }
            }
        });

        buttonDeleteDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null,"¿Desea borrar la base de datos?","Borrar base de datos", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    sQLiteConsultor.delete();
                    data = new Object[0];
                    list.setListData(data);
                    list.updateUI();
                    JOptionPane.showMessageDialog(null,"Base de datos borrada","Borrar base de datos",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void refreshList() {
        cancionesDB = new ArrayList<SongDataBase>();
        try {
            ResultSet resultSet = sQLiteConsultor.query("SELECT * FROM most_popular ORDER BY times DESC, number ASC");
            while (resultSet.next()) {
                cancionesDB.add(new SongDataBase(
                        resultSet.getInt("_ID"),
                        resultSet.getInt("number"),
                        resultSet.getString("name"),
                        resultSet.getInt("times"),
                        resultSet.getLong("last_date"),
                        resultSet.getString("artist"),
                        resultSet.getString("genre")
                ));
            }

            resultSet.close();
            sQLiteConsultor.closeConnection();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        data = new Object[cancionesDB.size()];

        for (int i = 0; i < data.length; i++) {
            data[i] = cancionesDB.get(i).getName();
        }

        list.setListData(data);

        list.updateUI();
    }

    public static void main (String [] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new SQLiteEditor().setVisible(true);
    }
}