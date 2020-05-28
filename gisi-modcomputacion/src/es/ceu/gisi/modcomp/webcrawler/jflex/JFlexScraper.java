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
    boolean malBalanceado = true;
    boolean auxCierre = true;
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
        int estado = 0; //Establecemos el estado inicial del autómata
        Token token = analizador.yylex();
        String aux = "";
      
        
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
                    
                    /*else{
                        estado = 0;
                        auxAperturaValida = false;
                    }*/
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
                    //vaciado de pila, if pila.peek() == token.getValor()
                    if(token.getTipo() == PALABRA){
                        aux = token.getValor();
                        if(aux.equals(pila.peek())){
                            pila.pop();
                            estado = 4;
                        }else malBalanceado = true;
                    }
                break;
            }
            token = analizador.yylex();
        }
    }

//----------------------------------------------------------------
    
    public ArrayList<String> obtenerHiperenlaces() {
        return arrayHref;
    }

    public ArrayList<String> obtenerHiperenlacesImagenes() {
        return arraySrc;
    }

    public boolean esDocumentoHTMLBienBalanceado() {
        return !this.malBalanceado && pila.isEmpty();
    }
}
