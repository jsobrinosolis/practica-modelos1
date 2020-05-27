package es.ceu.gisi.modcomp.webcrawler;

import es.ceu.gisi.modcomp.webcrawler.jflex.JFlexScraper;
import java.io.File;
import java.io.FileWriter;
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
    private static File fichero = new File(path + "prueba2.html");
    
    public static void main(String[] args) throws IOException {
        // Deberá inicializar JFlexScraper con el fichero HTML a analizar
        // Y creará un fichero con todos los hiperenlaces que encuentre.
        // También deberá indicar, mediante un mensaje en pantalla que
        // el fichero HTML que se ha pasado está bien balanceado.
        
        JFlexScraper af = new JFlexScraper(fichero);

        FileWriter writer;
        writer = new FileWriter("ficheroURLImagenes.txt");
        for(String s : af.obtenerHiperenlacesImagenes()){
            writer.write(s);
        }
        writer.close();
        
        FileWriter writer1;
        writer1 = new FileWriter("ficheroURLWeb.txt");
        for(String s : af.obtenerHiperenlaces()){
            writer1.write(s);
        }
        writer1.close();
        
        if(af.esDocumentoHTMLBienBalanceado() == true) System.out.println("El documento HTML está bien balanceado");
        
        else System.out.println("El documento HTML está mal balanceado");
        
        // Deberá inicializar JsoupScraper con la DIRECCIÓN HTTP de una página
        // web a analizar. Creará un fichero con todos los hiperenlaces que
        // encuentre en la página web. También obtendrá estadísticas de uso 
        // de las etiquetas HTML más comunes: a, br, div, li, ul, p, span, table, td, tr
        
       
    }
}
