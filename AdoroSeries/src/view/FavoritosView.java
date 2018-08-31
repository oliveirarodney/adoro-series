/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.ProgramaController;
import controller.TipoController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.CategoriaModel;
import model.ProgramaModel;
import model.TipoModel;
import view.components.BotaoMenu;
import view.modulos.ModuloCentral;

/**
 *
 * @author User
 */
public final class FavoritosView {
    private final ProgramaController programaController;
    private final ModuloCentral central;
    private ProgramaTVView programaTVView;
    
    private static final String label = ""
            + "-fx-font-family: 'Century Gothic'; "
            + "-fx-font-size: 12pt;";
    
    private static final String titulo = "-fx-font-family: 'Century Gothic';"
            + "-fx-font-size: 25pt;"
            + "-fx-text-fill: #A8CF45;";
    
    private VBox layout, programasTV, programa;
    private HBox hbox;
    private ImageView imagemPrograma;
    private Label nomePrograma, favoritosLabel;
    private ScrollPane scroll;
    private List<VBox> programas;
    
    private final int qtdeSeriesRow = 3;
    
    public FavoritosView (ModuloCentral central, ProgramaController programaController) throws Exception{
        this.central = central;
        this.programaController = programaController;
        IniciarLayout();
        
    }
    
    public VBox IniciarLayout() throws SQLException {
        layout = new VBox();
        layout.setPadding(new Insets(50));
        layout.setStyle("-fx-background-color: white;");
        layout.alignmentProperty().set(Pos.CENTER);
        
        favoritosLabel = new Label("Programas Favoritos");
        favoritosLabel.setStyle(titulo);
        
        scroll = new ScrollPane();
        programasTV = new VBox();
        scroll.setContent(programasTV);
        scroll.setStyle("-fx-background: white; -fx-border-color: white; -fx-focus-color: white; -fx-faint-focus-color: white;");
        scroll.prefWidthProperty().bind(layout.widthProperty());
        scroll.prefHeightProperty().bind(layout.heightProperty());
        criarListaProgramas();
        for (int i = 0; i < programas.size(); i++) {         
            if(i % qtdeSeriesRow == 0){
                hbox = new HBox();
                hbox.setSpacing(20);
                programasTV.getChildren().add(hbox);
                hbox.prefWidthProperty().bind(scroll.widthProperty().subtract(30));
            }
            
            programasTV.setSpacing(30);
            hbox.getChildren().add(programas.get(i));
            programas.get(i).prefWidthProperty().bind(scroll.widthProperty().divide(qtdeSeriesRow));
        }
        
        layout.getChildren().addAll(favoritosLabel, scroll);
        layout.setSpacing(30);
        return layout;
    }
    
    public void criarListaProgramas() throws SQLException {
        programasTV.getChildren().clear();
        programas = new ArrayList<>();
        
        programaController.listarFavoritos();
        
        for (int i = 0; i < programaController.getProgramas().size(); i++) {
            int indice = i;
            if (indice % qtdeSeriesRow == 0) {
                hbox = new HBox();
                hbox.setSpacing(20);
                programasTV.getChildren().add(hbox);
                hbox.prefWidthProperty().bind(scroll.widthProperty().subtract(30));
            }
            programa = new VBox();
            nomePrograma = new Label(programaController.getProgramas().get(indice).getNomePrograma());
            nomePrograma.setStyle(label);
            
            imagemPrograma = new ImageView(programaController.getProgramas().get(indice).getImagemPrograma());
            imagemPrograma.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                final Object source = event.getSource();
                final ProgramaModel programaModel = programaController.getProgramas().get(indice);
                for (VBox programaVBox : getProgramas()) {
                    if(source.equals(programaVBox.getChildren().get(0))){
                        try {
                            programaTVView = new ProgramaTVView(central, programaController, programaModel);
                            central.mudarTelas(programaTVView.getLayout());
                        } catch (Exception ex) {
                            Logger.getLogger(PesquisarView.class.getName()).log(Level.SEVERE, null, ex);
                        }                       
                        break;
                    }
                }
                event.consume();
            });
            imagemPrograma.setFitHeight(250);
            imagemPrograma.setFitWidth(200);

            programa.getChildren().addAll(imagemPrograma, nomePrograma);
            programa.setAlignment(Pos.TOP_CENTER);
            adicionarProgramas(programa);

            programasTV.setSpacing(30);
            hbox.getChildren().add(programas.get(i));
            programas.get(i).prefWidthProperty().bind(scroll.widthProperty().divide(qtdeSeriesRow));
        }
    }

    public VBox getLayout() {
        return layout;
    }

    public List<VBox> getProgramas() {
        return programas;
    }
    
    public void adicionarProgramas(VBox programa) {
        programas.add(programa);
    }

    public ImageView getImagemPrograma() {
        return imagemPrograma;
    }
}