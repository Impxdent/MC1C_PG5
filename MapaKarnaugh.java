package karnaugh;

import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MapaKarnaugh {
    private int numVariables;
    private ArrayList<Integer> mapa;
    private String[] variables;

    public MapaKarnaugh(Element elementoMapa) {
        numVariables = Integer.parseInt(elementoMapa.getAttribute("v"));
        variables = new String[numVariables];

        for (int i = 0; i < numVariables; i++) {
            variables[i] = elementoMapa.getAttribute("val" + (i + 1));
        }

        mapa = new ArrayList<>();
        NodeList nodos = elementoMapa.getElementsByTagName("valor");

        for (int i = 0; i < nodos.getLength(); i++) {
            Element valor = (Element) nodos.item(i);
            mapa.add(Integer.parseInt(valor.getTextContent().trim()));
        }
    }

    public int getNumVariables() {
        return numVariables;
    }

    public int getFilas() {
        switch (numVariables) {
            case 3: return 2;
            case 4: return 4;
            case 5: return 8;
            default: return 1;
        }
    }

    public int getColumnas() {
        switch (numVariables) {
            case 3: return 4;
            case 4: return 4;
            case 5: return 4;
            default: return 1;
        }
    }

    public int getValor(int fila, int columna) {
        int index = obtenerIndiceDesdePosicion(fila, columna);
        return (index >= 0 && index < mapa.size()) ? mapa.get(index) : 0;
    }

    private int obtenerIndiceDesdePosicion(int fila, int columna) {
        int bitsFila = (numVariables == 3) ? 1 : (numVariables == 4 ? 2 : 3);
        int bitsColumna = numVariables - bitsFila;

        int grayFila = fila ^ (fila >> 1);
        int grayColumna = columna ^ (columna >> 1);

        int index = (grayFila << bitsColumna) | grayColumna;
        return index;
    }

    public String getEtiquetaFila(int i) {
        int bitsFila = (numVariables == 3) ? 1 : (numVariables == 4 ? 2 : 3);
        int gray = i ^ (i >> 1);
        return String.format("%" + bitsFila + "s", Integer.toBinaryString(gray)).replace(' ', '0');
    }

    public String getEtiquetaColumna(int j) {
        int bitsColumna = numVariables - ((numVariables == 3) ? 1 : (numVariables == 4 ? 2 : 3));
        int gray = j ^ (j >> 1);
        return String.format("%" + bitsColumna + "s", Integer.toBinaryString(gray)).replace(' ', '0');
    }


    public String[] getNombres() {
        return variables;
    }

    public ArrayList<Integer> getValores() {
        return mapa;
    }
}
