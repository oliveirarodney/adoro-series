/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.modulos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author User
 */
public final class ModuloTopo {
    /*************** ESTILO DOS COMPONENTES ****************/
    private static final String backgroundTopo = "-fx-background-color: #222222;";
    private HBox topo;
    private VBox vbox;
    private ImageView logo;
    
    public ModuloTopo(){
        iniciarBorderPaneTopo();
    }
    
    public HBox iniciarBorderPaneTopo(){
        topo = new HBox();
        
        topo.setStyle(backgroundTopo);
        addVBox(topo);
        
        return topo;
    }
    
    public void addVBox(HBox bpTopo) {
        vbox = new VBox();
        criarLogo();
        vbox.getChildren().addAll(logo);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        bpTopo.getChildren().add(vbox);
        HBox.setHgrow(vbox, Priority.ALWAYS);
    }
    
    public ImageView criarLogo(){
        logo = new ImageView("/view/resources/adoroseries.png");
        logo.setFitHeight(49);
        logo.setFitWidth(407);
        return logo;
    }
    
    public HBox getTopo(){
        return topo;
    }
    
}
