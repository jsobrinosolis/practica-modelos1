package es.ceu.gisi.modcomp.webcrawler.jflex;

import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Token;
import java.io.IOException;
import java.util.Stack;

/**
 * Esta clase encapsula toda la lógica de interacción con el parser HTML.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 * @author Jorge Sobrino Solís
 */
public class JFlexScraper {

    private HTMLParser analizador;
    boolean balanceado;
    Stack pila = new Stack();
    ArrayList<String> arraySrc = new ArrayList<>();
    ArrayList<String> arrayHref = new ArrayList<>();
    

    public JFlexScraper(File fichero) throws IOException {
        
       try{
        Reader reader = new BufferedReader(new FileReader(fichero));
        analizador = new HTMLParser(reader);
       }catch(IOException e){
           System.out.println("Error en inicialización.");
       }
        int estado = 0;
        Token token = analizador.yylex();
        String aux = "";
        this.balanceado = true;
        
        while(token != null){
            switch(estado){
                case 0:
                    if(token.getTipo() == Tipo.OPEN) estado = 1;
                break;
                
                case 1:
                    if(token.getTipo() == PALABRA){
                        aux = token.getValor();
                        pila.add(aux);
                        
                        if(token.getValor().equalsIgnoreCase("A")){
                            estado = 2;
                            
                        }else if(token.getValor().equalsIgnoreCase("IMG")){
                            estado = 3;
                            
                        }else estado = 4;
                        
                    }else if(token.getTipo() == SLASH) estado = 5;
                    
                break;
                
                case 2:
                    //guardar link
                    if("href".equals(token.getValor())){
                        token = analizador.yylex();
                        if(token.getTipo() == IGUAL){
                            token = analizador.yylex();
                            if(token.getTipo() == VALOR){
                                 arrayHref.add(token.getValor());
                                 estado = 4;
                            }
                        }
                    }
                break;
                    
                case 3:
                  //guardar imagen
                    if("src".equals(token.getValor())){
                        token = analizador.yylex();
                        if(token.getTipo() == IGUAL){
                            token = analizador.yylex();
                            if(token.getTipo() == VALOR){
                                 arraySrc.add(token.getValor());
                                 estado = 4;
                            }
                        }
                    }
                break;
                    
                case 4:
                    if(token.getTipo() == SLASH){
                        pila.pop();
                        estado = 4;
                    }else if(token.getTipo() == CLOSE){
                        aux = "";
                        estado = 0;
                    } 
                    
                break;
                
                case 5:
                    
                    if(token.getTipo() == PALABRA){
                        aux = token.getValor();
                        if(aux.equals(pila.peek())){
                            pila.pop();
                            estado = 4;
                        }else balanceado = true;
                    }
                break;
            }
            token = analizador.yylex();
        }
    }

//----------------------------------------------------------------
    
    /**
     * @return El ArrayList con los hiperenlaces del documento html 
     * que se han ido guardando en cada una de las iteraciones del autómata.
     */
    public ArrayList<String> obtenerHiperenlaces() {
        return arrayHref;
    }
    
    /**
     * @return El ArrayList con los hiperenlaces de las imágenes insertadas en el documento html 
     * que se han ido guardando en cada una de las iteraciones del autómata.
     */
    public ArrayList<String> obtenerHiperenlacesImagenes() {
        return arraySrc;
    }
    
    /**
     * @return boolean, true si el documento html está bien balanceado.
     */
    public boolean esDocumentoHTMLBienBalanceado() {
        return this.balanceado && pila.isEmpty();
    }
}
