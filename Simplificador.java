package karnaugh;

import java.util.ArrayList;

public class Simplificador {

    public static String simplificarMapa(MapaKarnaugh mapa) {
        if (mapa.getNumVariables() == 5) {
            return "No se simplifica para 5 variables.";
        }

        StringBuilder funcion = new StringBuilder();
        ArrayList<Integer> valores = mapa.getValores();
        String[] nombres = mapa.getNombres();

        for (int i = 0; i < valores.size(); i++) {
            if (valores.get(i) == 1) {
                String mintermino = obtenerMintermino(i, nombres);
                if (funcion.length() > 0) {
                    funcion.append(" + ");
                }
                funcion.append(mintermino);
            }
        }

        return funcion.toString();
    }

    private static String obtenerMintermino(int index, String[] nombres) {
        StringBuilder min = new StringBuilder();
        String bin = String.format("%" + nombres.length + "s", Integer.toBinaryString(index)).replace(' ', '0');

        for (int i = 0; i < nombres.length; i++) {
            if (bin.charAt(i) == '1') {
                min.append(nombres[i]);
            } else {
                min.append(nombres[i] + "'");
            }
        }

        return min.toString();
    }
}
