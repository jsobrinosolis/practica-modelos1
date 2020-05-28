package es.ceu.gisi.modcomp.webcrawler;

import es.ceu.gisi.modcomp.webcrawler.jflex.JFlexScraper;
import es.ceu.gisi.modcomp.webcrawler.jsoup.JsoupScraper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

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
        
        JFlexScraper af = new JFlexScraper(fichero);

        FileWriter writer;
        writer = new FileWriter("ficheroURLImagenes.txt");
        for(String s : af.obtenerHiperenlacesImagenes()){
            writer.write(s);
        }
        writer.close();
        
        FileWriter writer1;
        writer1 = new FileWriter("ficheroURLWeb.txt");
        for(String st : af.obtenerHiperenlaces()){
            writer1.write(st);
        }
        writer1.close();
        
        if(af.esDocumentoHTMLBienBalanceado() == true) System.out.println("El documento HTML está bien balanceado");
        
        else System.out.println("El documento HTML está mal balanceado");
        
//-----------------------------------------------------------------------
        
        URL direccion = new URL("https://www.expansion.com");
        JsoupScraper jsoup = new JsoupScraper(direccion);
        
        FileWriter writer2 = new FileWriter("ficheroURLImagenesJsoup.txt");
        for(String enlaceImagen : jsoup.obtenerHiperenlacesImagenes()){
            writer2.write(enlaceImagen);
        }
        writer2.close();
        
        FileWriter writer3 = new FileWriter("ficheroURLJsoup.txt");
        for(String enlaceWeb : jsoup.obtenerHiperenlaces()){
            writer3.write(enlaceWeb);
        }
        writer3.close();
        
        System.out.println("\nJsoup:\n");
        System.out.println("Etiquetas <a>: " + jsoup.estadisticasEtiqueta("a") + "\n");
        System.out.println("Etiquetas <br>: " + jsoup.estadisticasEtiqueta("br") + "\n");
        System.out.println("Etiquetas <div>: " + jsoup.estadisticasEtiqueta("div") + "\n");
        System.out.println("Etiquetas <li>: " + jsoup.estadisticasEtiqueta("li") + "\n");
        System.out.println("Etiquetas <ul>: " + jsoup.estadisticasEtiqueta("ul") + "\n");
        System.out.println("Etiquetas <p>: " + jsoup.estadisticasEtiqueta("p") + "\n");
        System.out.println("Etiquetas <table>: " + jsoup.estadisticasEtiqueta("table") + "\n");
        System.out.println("Etiquetas <td>: " + jsoup.estadisticasEtiqueta("td") + "\n");
        System.out.println("Etiquetas <tr>: " + jsoup.estadisticasEtiqueta("tr") + "\n");
       
    }
}
