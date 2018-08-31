/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.modulos;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author User
 */
public class ModuloCentral {
    private static final String backgroundCentral = "-fx-background-color: white;";
    private VBox vbox;
    private HBox hbox;
    
    public ModuloCentral(){
        vbox = new VBox();
        hbox = new HBox();
    }
    
    public VBox borderPaneCentral(VBox telaCarregada){       
        hbox.setPrefSize(2000, 2000);
        vbox.setStyle(backgroundCentral);
        vbox.getChildren().add(hbox);
        hbox.getChildren().clear();
        hbox.getChildren().add(telaCarregada);
        
        VBox.setVgrow(vbox, Priority.ALWAYS);
        HBox.setHgrow(telaCarregada, Priority.ALWAYS);
        
        return vbox;
    }
    
    public void mudarTelas(VBox telaCarregada){
        hbox.getChildren().clear();
        hbox.getChildren().add(telaCarregada);
        
        VBox.setVgrow(vbox, Priority.ALWAYS);
        HBox.setHgrow(telaCarregada, Priority.ALWAYS);
        
    }
}
