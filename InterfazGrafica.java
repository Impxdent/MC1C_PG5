package karnaugh;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Timer;

public class InterfazGrafica extends JFrame {
    private JPanel panelMapa;
    private JTextArea areaResultado;
    private JLabel lblHora;
    private JLabel lblBienvenida;

    public InterfazGrafica() {
        setTitle("Mapa de Karnaugh");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new BorderLayout());
        lblBienvenida = new JLabel("Este programa analiza archivos xml, y mostrará el mapa de Karnaugh junto a "
                + "su función booleana simplificada.", SwingConstants.CENTER);
        panelSuperior.add(lblBienvenida, BorderLayout.NORTH);

        lblHora = new JLabel("", SwingConstants.RIGHT);
        panelSuperior.add(lblHora, BorderLayout.SOUTH);

        Timer timer = new Timer(1000, e -> actualizarHora());
        timer.start();
        actualizarHora();

        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new BorderLayout());

        panelMapa = new JPanel();
        panelCentral.add(panelMapa, BorderLayout.CENTER);

        JButton btnCargar = new JButton("Seleccionar XML");
        btnCargar.addActionListener(e -> cargarArchivo());
        panelCentral.add(btnCargar, BorderLayout.NORTH);

        add(panelCentral, BorderLayout.CENTER);

        areaResultado = new JTextArea(3, 40);
        areaResultado.setEditable(false);
        add(new JScrollPane(areaResultado), BorderLayout.SOUTH);
    }

    private void cargarArchivo() {
        JFileChooser chooser = new JFileChooser();
        int opcion = chooser.showOpenDialog(this);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            MapaKarnaugh mapa = LectorXML.cargarMapaDesdeArchivo(archivo);

            if (mapa != null) {
                mostrarMapa(mapa);
                String resultado = Simplificador.simplificarMapa(mapa);
                areaResultado.setText(resultado);
            } else {
                areaResultado.setText("Error al cargar el archivo.");
            }
        }
    }

    private void mostrarMapa(MapaKarnaugh mapa) {
        panelMapa.removeAll();
        panelMapa.setLayout(new BorderLayout());

        JPanel panelTabla = new JPanel();
        int v = mapa.getNumVariables();
        java.util.ArrayList<Integer> listaValores = mapa.getValores();
        int[] valores = listaValores.stream().mapToInt(Integer::intValue).toArray();

        if (v == 3) {
            panelTabla.setLayout(new GridLayout(2, 4));
            int[][] orden = {
                {0, 1, 2, 3},
                {4, 5, 6, 7}
            };

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    panelTabla.add(celdaColoreada(valores[orden[i][j]]));
                }
            }
            System.out.println("Mostrando mapa de 3 variables");
        } else if (v == 4) {
            panelTabla.setLayout(new GridLayout(4, 4));
            int[][] orden = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11},
                {12, 13, 14, 15}
            };

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    panelTabla.add(celdaColoreada(valores[orden[i][j]]));
                }
            }
            System.out.println("Mostrando mapa de 4 variables");
        } else if (v == 5) {
            panelTabla.setLayout(new GridLayout(8, 4));
            int[][] orden = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11},
                {12, 13, 14, 15},
                {16, 17, 18, 19},
                {20, 21, 22, 23},
                {24, 25, 26, 27},
                {28, 29, 30, 31}
            };

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    panelTabla.add(celdaColoreada(valores[orden[i][j]]));
                }
            }
            System.out.println("Mostrando mapa de 5 variables");
        } else {
            panelTabla.add(new JLabel("Mapa no visualizable para " + v + " variables."));
        }

        panelMapa.add(panelTabla, BorderLayout.CENTER);
        panelMapa.revalidate();
        panelMapa.repaint();
        
    }

    private JLabel celdaColoreada(int valor) {
        JLabel celda = new JLabel(String.valueOf(valor), SwingConstants.CENTER);
        celda.setOpaque(true);
        celda.setPreferredSize(new Dimension(50, 50));
        celda.setBackground(valor == 1 ? Color.YELLOW : Color.LIGHT_GRAY);
        celda.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return celda;
    }
    
    private void actualizarHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        lblHora.setText("Hora actual: " + LocalTime.now().format(formatter));
    }
    
}
