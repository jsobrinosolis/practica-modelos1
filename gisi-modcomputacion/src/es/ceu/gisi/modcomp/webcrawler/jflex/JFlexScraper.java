package es.ceu.gisi.modcomp.webcrawler.jflex;

import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Token;
import java.util.Stack;

/**
 * Esta clase encapsula toda la lógica de interacción con el parser HTML.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 * @author Jorge Sobrino Solís
 */
public abstract class JFlexScraper {

    private HTMLParser analizador;
    boolean auxApertura = true;
    boolean auxCierre = true;
    Stack pila = new Stack();
    

    public JFlexScraper(File fichero) throws FileNotFoundException {
        
        Reader reader = new BufferedReader(new FileReader(fichero));
        analizador = new HTMLParser(reader);
        int estado = 0; //Establecemos el estado inicial del autómata
        Token token = analizador.yylex();
        
        while(token != null){
            switch(estado){
                case 0:
                    if(token.getTipo() == Tipo.OPEN){//Se pasa al estado 1 cuando se devuelve un OPEN 
                        estado = 1;
                        token = analizador.yylex(); //Se pasa a analizar el siguiente token
                    }else token = analizador.yylex();
                break;
                
                case 1://Se comprueba si el siguiente token es palabra o /, para saber si es una etiqueta de apertura o cierre.
                    if(token.getTipo() == Tipo.PALABRA){
                        estado = 2;
                    }else if(token.getTipo() == Tipo.SLASH){
                        estado = 3;
                    }else{
                        estado = 0;
                        auxApertura = false;
                    }
                break;
                
                case 2:
                    
                                 
            }
        }
    }

//----------------------------------------------------------------
    
    public ArrayList<String> obtenerHiperenlaces() {
        // Habrá que programarlo..
        return new ArrayList<String>();
    }

    public ArrayList<String> obtenerHiperenlacesImagenes() {
        // Habrá que programarlo..
        return new ArrayList<String>();
    }

    public boolean esDocumentoHTMLBienBalanceado() {
        return aux;
    }
}
