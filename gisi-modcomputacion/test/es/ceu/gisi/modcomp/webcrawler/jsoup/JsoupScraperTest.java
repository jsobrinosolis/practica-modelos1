package es.ceu.gisi.modcomp.webcrawler.jsoup;

import es.ceu.gisi.modcomp.webcrawler.jsoup.JsoupScraper;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Clase que testea y muestra el uso del analizador de árboles DOM Jsoup.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 * @author Jorge Sobrino Solís
 */
public class JsoupScraperTest {

    private static final String HTML = ""
                                       + "<HTML>"
                                       + "    <HEAD>"
                                       + "        <TITLE>My first webpage</TITLE>"
                                       + "    </HEAD>"
                                       + "    <BODY>"
                                       + "        <P>"
                                       + "            This is"
                                       + "            some text<BR />"
                                       + "            and a"
                                       + "            <A href=\"http://www.bbc.co.uk\">link</A>"
                                       + "        </P>"
                                       + "        <IMG src=\"brushedsteel.jpg\"/>"
                                       + "    </BODY>"
                                       + "</HTML>";
    
    private static final String prueba4 = ""
                                       + "<HTML>"
                                       + "    <HEAD>"
                                       + "        <TITLE>My first webpage</TITLE>"
                                       + "    </HEAD>"
                                       + "    <BODY>"
                                       + "        <P>"
                                       +"             <IMG src=\"brushedsteel1234.jpg\"/>"
                                       + "            This is"
                                       + "            some text<BR />"
                                       + "            and a"
                                       + "            <A href=\"http://www.bbc.co.uk\">link</A>"
                                       + "            <A href=\"https://as.com\">link</A>"
                                       + "        </P>"
                                       + "        <IMG src=\"brushedsteel.jpg\"/>"
                                       + "        <A href=\"https://www.marca.com\">link</A>"
                                       + "        <IMG src=\"brushedsteel123334.jpg\"/>"
                                       + "    </BODY>"
                                       + "</HTML>";
    
    private final JsoupScraper scraper;
    private final JsoupScraper jsoup;

    /**
     * Se va a crear un analizador léxico, a partir de uno de los ficheros de
     * prueba.
     */
    public JsoupScraperTest() throws IOException {
        scraper = new JsoupScraper(HTML);
        this.jsoup = new JsoupScraper(prueba4);
    }

    /**
     * El test recupera el nombre de la primera imagen insertada con la etiqueta
     * IMG .
     */
    @Test
    public void recuperaNombrePrimeraImagen() {
        assertEquals(scraper.obtenerContenidoImg(), "brushedsteel.jpg");
    }
    
    /**
     * El test comprueba que el analizador léxico recupera el valor de la primera imagen.
     */
    @Test
    public void obtenerPrimeraImagen(){
        assertEquals(jsoup.obtenerContenidoImg(), "brushedsteel1234.jpg");
    }
    
    /**
     * El test comprueba que el analizador léxico recupera el valor los hiperenlaces.
     */
    @Test
    public void obtenerHiperenlaces(){
        assertEquals(jsoup.obtenerHiperenlaces().get(0),"http://www.bbc.co.uk");
        assertEquals(jsoup.obtenerHiperenlaces().get(1),"https://as.com");
        assertEquals(jsoup.obtenerHiperenlaces().get(2),"https://www.marca.com");
    }
    
    /**
     * El test verifica las estadísticas de etiquetas del documento html.
     */
    @Test
    public void estadisticasEtiqueta(){
        assertEquals(jsoup.estadisticasEtiqueta("html"), 1);
        assertEquals(jsoup.estadisticasEtiqueta("head"), 1);
        assertEquals(jsoup.estadisticasEtiqueta("title"), 1);
        assertEquals(jsoup.estadisticasEtiqueta("body"), 1);
        assertEquals(jsoup.estadisticasEtiqueta("p"), 1);
        assertEquals(jsoup.estadisticasEtiqueta("a"), 3);
        assertEquals(jsoup.estadisticasEtiqueta("br"), 1);
        assertEquals(jsoup.estadisticasEtiqueta("img"), 3);
        assertEquals(jsoup.estadisticasEtiqueta("div"), 0);
    }
}
