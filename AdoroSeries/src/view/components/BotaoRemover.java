/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.components;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author User
 */

public class BotaoRemover extends Button {
    private final String estiloBotaoPress = "-fx-background-color: #931300;"
                                          + "-fx-text-fill: white;";
    
    private final String estiloBotao = "-fx-background-color: red;"
                                     + "-fx-text-fill: white;";
    
    private final String estiloBotaoEntered = "-fx-background-color: #E86855;"
                                            + "-fx-text-fill: white;";

    private Text icon;
    
    
    public BotaoRemover(){
        setText("X");
        fonteBotoes();
        setPrefHeight(30);
        setStyle(estiloBotao);
        inicializarBotao();
    }
    
    private void fonteBotoes(){
        setFont(Font.font("Century Gothic", 18));
    }
    
    private void botaoPressionado(){
        setStyle(estiloBotaoPress);
    }
    
    private void botaoNormal(){
        setStyle(estiloBotao);
    }
    
    private void botaoMouseEmCima(){
        setStyle(estiloBotaoEntered);
    }
    
    private void inicializarBotao(){
        
        setOnMousePressed((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                botaoPressionado();
            }
        });
        
        setOnMouseReleased((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                botaoMouseEmCima();
            }
        });
        
        setOnMouseEntered((MouseEvent event) -> {
            botaoMouseEmCima();
        });
        
        setOnMouseExited((MouseEvent event) -> {
            botaoNormal();
        });  
    }
}


