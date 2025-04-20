package karnaugh;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class LectorXML {
    public static MapaKarnaugh cargarMapaDesdeArchivo(File archivo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList nodosMapa = doc.getElementsByTagName("mapa");
            Element elementoMapa = (Element) nodosMapa.item(0);

            return new MapaKarnaugh(elementoMapa);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
