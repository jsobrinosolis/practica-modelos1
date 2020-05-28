/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ceu.gisi.modcomp.webcrawler.jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Esta clase encapsula toda la lógica de interacción con el analizador Jsoup.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 * @author Jorge Sobrino Solís
 */
public class JsoupScraper {

    private final Document doc;

    /**
     * Este constructor crea un documento a partir de la URL de la página web a
     * analizar.
     *
     * @param url Una URL que apunte a un documento HTML (p.e.
     *            http://www.servidor.com/index.html)
     */
    public JsoupScraper(URL url) throws IOException {
        doc = Jsoup.connect(url.toString()).get();
    }

    /**
     * Este constructor crea un documento a partir del contenido HTML del String
     * que se pasa como parámetro.
     *
     * @param html Un documento HTML contenido en un String.
     */
    public JsoupScraper(String html) throws IOException {
        doc = Jsoup.parse(html);
    }

    /**
     * Realiza estadísticas sobre el número de etiquetas de un cierto tipo.
     *
     * @param etiqueta La etiqueta sobre la que se quieren estadísticas
     *
     * @return El número de etiquetas de ese tipo que hay en el documento HTML
     */
    public int estadisticasEtiqueta(String etiqueta) {
        Elements e = doc.select(etiqueta);
        return e.size();
    }

    /**
     * Obtiene todos los hiperenlaces que se encuentran en el documento creado.
     *
     * @return Una lista con todas las URLs de los hiperenlaces
     */
    public List<String> obtenerHiperenlaces() {
        ArrayList<String> arrayHiperenlaces = new ArrayList<>();
        Elements hiperenlacesLinks = doc.getElementsByTag("a");
        
        hiperenlacesLinks.forEach((e) -> {
            arrayHiperenlaces.add(e.attr("href"));
        });
        
        return arrayHiperenlaces;
    }

    /**
     * Obtiene todos los hiperenlaces de las imágenes incrustadas.
     *
     * @return Una lista con todas las URLs de los hiperenlaces
     */
    public List<String> obtenerHiperenlacesImagenes() {
        ArrayList<String> arrayHiperenlacesImagenes = new ArrayList<>();
        Elements imagenesLinks = doc.getElementsByTag("img");
        
        imagenesLinks.forEach((e) -> {
            arrayHiperenlacesImagenes.add(e.attr("src"));
        });
        
        return arrayHiperenlacesImagenes;
    }


    /**
     * Obtiene la imagen a la que hace referencia LA PRIMERA etiqueta IMG que
     * encontramos.
     *
     * @return El nombre (o ruta) de la primera imagen insertada en el documento
     *         HTML.
     */
    public String obtenerContenidoImg() {
        Element elemento = doc.select("IMG").first();
        String imagen = elemento.attr("src");
        return imagen;
    }
}
