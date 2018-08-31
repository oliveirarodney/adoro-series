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

public class BotaoMenu extends Button {
    private final String estiloBotaoPress = "-fx-background-color: #708A2E;"
                                          + "-fx-text-fill: #222222;";
    
    private final String estiloBotao = "-fx-background-color: #A8CF45;"
                                     + "-fx-text-fill: #222222;";
    
    private final String estiloBotaoEntered = "-fx-background-color: #C5DF83;"
                                            + "-fx-text-fill: #222222;";

    private Text icon;
    
    public BotaoMenu(String icon, String texto){
        this.icon = new FontAwesomeIconView(FontAwesomeIcon.valueOf(icon));
        setGraphic(this.icon);
        this.icon.setFill(Color.valueOf("222222"));
        setText(" " + texto);
        fonteBotoes();
        setPrefWidth(300);
        setPrefHeight(70);
        setStyle(estiloBotao);
        inicializarBotao();
    }
    
    public BotaoMenu(String texto){
        setText(texto);
        fonteBotoes();
        setPrefWidth(150);
        setPrefHeight(50);
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
        
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    botaoPressionado();
                }
            }
        });
        
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    botaoMouseEmCima();
                }
            }
        });
        
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                botaoMouseEmCima();
            }
        });
        
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                botaoNormal();
            }
        });  
    }
}


