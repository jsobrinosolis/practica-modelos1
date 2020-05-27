package es.ceu.gisi.modcomp.webcrawler.jflex;

import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo;
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
    boolean auxAperturaValida = true;
    boolean auxCierre = true;
    Stack pila = new Stack();
    ArrayList<String> arraySrc = new ArrayList<>();
    ArrayList<String> arrayHref = new ArrayList<>();
    

    public JFlexScraper(File fichero) throws IOException {
        
       try{
        Reader reader = new BufferedReader(new FileReader(fichero));
        analizador = new HTMLParser(reader);
       }catch(IOException e){
           System.out.println("Error...");
       }
        int estado = 0; //Establecemos el estado inicial del autómata
        Token token = analizador.yylex();
      
        
        while(token != null){
            switch(estado){
                case 0:
                    if(token.getTipo() == Tipo.OPEN){//Se pasa al estado 1 cuando se devuelve un OPEN 
                        estado = 1;
                    }
                break;
                
                case 1://Se comprueba si el siguiente token es palabra o /, para saber si es una etiqueta de apertura o cierre.
                    if(token.getTipo() == Tipo.PALABRA){
                        pila.add(token.getValor());
                        
                        if("a".equals(token.getValor())){
                            estado = 2;
                            
                        }else if("img".equals(token.getValor())){
                            estado = 3;
                            
                        }else estado = 4;
                        
                    }else if(token.getTipo() == Tipo.SLASH){
                        estado = 5;
                        
                    }/*else{
                        estado = 0;
                        auxAperturaValida = false;
                    }*/
                break;
                
                case 2:
                    //guardar link
                    if("href".equals(token.getValor())){
                        token = analizador.yylex();
                        if(token.getTipo() == Tipo.IGUAL){
                            token = analizador.yylex();
                            if(token.getTipo() == Tipo.VALOR){
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
                        if(token.getTipo() == Tipo.IGUAL){
                            token = analizador.yylex();
                            if(token.getTipo() == Tipo.VALOR){
                                 arraySrc.add(token.getValor());
                                 estado = 4;
                            }
                        }
                    }
                break;
                    
                case 4:
                    if(token.getTipo() == Tipo.SLASH){
                        estado = 5;
                    }else estado = 4;
                    
                break;
                
                case 5:
                    //vaciado de pila, if pila.peek() == token.getValor()
                    if(pila.peek() == token.getValor()){
                        pila.pop();
                    }
                    estado = 0;
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
        return auxAperturaValida && pila.isEmpty();
    }
}
