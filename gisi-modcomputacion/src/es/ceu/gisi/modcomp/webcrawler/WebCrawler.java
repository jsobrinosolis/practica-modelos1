package es.ceu.gisi.modcomp.webcrawler;

import es.ceu.gisi.modcomp.webcrawler.jflex.JFlexScraper;
import java.io.File;
import java.io.IOException;

/**
 * Esta aplicación contiene el programa principal que ejecuta ambas partes del
 * proyecto de programación.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 * @author Jorge Sobrino Solís
 */
public class WebCrawler {
    
    private static String path = new java.io.File("").getAbsolutePath() + "/test/es/ceu/gisi/modcomp/webcrawler/jflex/test/";
    private static File fichero = new File(path + "prueba1.html");
    
    public static void main(String[] args) throws IOException {
        // Deberá inicializar JFlexScraper con el fichero HTML a analizar
        // Y creará un fichero con todos los hiperenlaces que encuentre.
        // También deberá indicar, mediante un mensaje en pantalla que
        // el fichero HTML que se ha pasado está bien balanceado.
        
        JFlexScraper af = new JFlexScraper(fichero);

        
        // Deberá inicializar JsoupScraper con la DIRECCIÓN HTTP de una página
        // web a analizar. Creará un fichero con todos los hiperenlaces que
        // encuentre en la página web. También obtendrá estadísticas de uso 
        // de las etiquetas HTML más comunes: a, br, div, li, ul, p, span, table, td, tr
        
       
    }
}
