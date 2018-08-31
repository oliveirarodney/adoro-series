/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ProgramaController;
import controller.TemporadaController;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ProgramaModel;
import view.components.BotaoMenu;
import view.modulos.ModuloCentral;

/**
 *
 * @author User
 */
public final class ProgramaTVView {
    private final ModuloCentral central;
    private final ProgramaModel programaModel;
    private final ProgramaController programaController;
    private final TemporadaController temporadaController;
    private TemporadaView temporadaView;
    
    private static final String padding = ""
            + "-fx-padding: 80;";
    private static final String titulo = ""
            + "-fx-font-family: 'Century Gothic';"
            + "-fx-font-size: 25pt;"
            + "-fx-text-fill: #A8CF45;";
    
    private static final String notas = ""
            + "-fx-font-family: 'Century Gothic';"
            + "-fx-font-size: 18pt;"
            + "-fx-text-fill: #222222;";
    
    private Label tituloProgramaTV, notaMedia, suaNotaMedia;
    private VBox layout, infoProgramaTV, temporadas, favoritos;
    private ScrollPane scroll;
    private List<BotaoMenu> botoesTemporadas;
    private HBox buttonBar, programaTV;
    private ImageView imagemProgramaTV;
    private BotaoMenu botao, addFavoritos, removerFavoritos;
    private Double notaGeral, suaNota;

    public ProgramaTVView(ModuloCentral central, ProgramaController programaController, ProgramaModel programaModel) throws Exception{
        this.programaModel = programaModel;
        this.programaController = programaController;
        this.central = central;
        temporadaController = new TemporadaController();
        temporadaController.carregarTemporadas(this.programaModel);
        System.out.println(programaModel.getNomePrograma());
        iniciarLayout();
    }
    public VBox iniciarLayout() {
        criarBotoes();
        layout = new VBox();
        layout.setPadding(new Insets(50));
        layout.setSpacing(20);
        layout.setStyle("-fx-background-color: white;");
        layout.alignmentProperty().set(Pos.TOP_CENTER);
        
        programaTV = new HBox();
        programaTV.setSpacing(30);
        infoProgramaTV = new VBox();
        infoProgramaTV.setSpacing(24.5);
        
        criarImagemPrograma();
        tituloProgramaTV = new Label(programaModel.getNomePrograma());
        tituloProgramaTV.setStyle(titulo);
        
        DecimalFormat df = new DecimalFormat("###,##0.00");
        
        notaGeral = programaController.getNotaGeral(programaModel);
        suaNota = programaController.getSuaNota(programaModel);
        
        notaMedia = new Label("Nota geral: " + df.format(notaGeral));
        notaMedia.setStyle(notas);
        
        suaNotaMedia = new Label("Sua nota: " + df.format(suaNota));
        suaNotaMedia.setStyle(notas);
        
        favoritos = new VBox();
        
        addFavoritos = new BotaoMenu("Adicionar aos Favoritos");
        addFavoritos.setPrefWidth(250);
        
        removerFavoritos = new BotaoMenu("Remover dos Favoritos");
        removerFavoritos.setPrefWidth(250);
            
        addFavoritos.setOnAction((ActionEvent event) -> {
            programaController.assinarPrograma(programaModel);
            favoritos.getChildren().clear();
            favoritos.getChildren().add(removerFavoritos);
        });
        
        removerFavoritos.setOnAction((ActionEvent event) -> {
            programaController.removerAssinatura(programaModel);
            favoritos.getChildren().clear();
            favoritos.getChildren().add(addFavoritos);
        });
        
        if(programaController.verificarFavoritos(programaModel)){
            favoritos.getChildren().add(removerFavoritos);
        } else {
            favoritos.getChildren().add(addFavoritos);
        }
        
        
        infoProgramaTV.getChildren().addAll(tituloProgramaTV, notaMedia, suaNotaMedia, favoritos);
        programaTV.getChildren().addAll(imagemProgramaTV, infoProgramaTV);
        
        scroll = new ScrollPane();
        temporadas = new VBox();
        scroll.setContent(temporadas);
        scroll.setStyle("-fx-background: white; -fx-border-color: white; -fx-focus-color: white; -fx-faint-focus-color: white;");
        scroll.prefWidthProperty().bind(layout.widthProperty());
        scroll.prefHeightProperty().bind(layout.heightProperty());
        
        for (int i = 0; i < botoesTemporadas.size(); i++) {         
            buttonBar = new HBox();
            temporadas.getChildren().add(buttonBar);
            temporadas.setSpacing(10);
            buttonBar.getChildren().add(botoesTemporadas.get(i));
            botoesTemporadas.get(i).prefWidthProperty().bind(scroll.widthProperty().subtract(30));
        }
        
        layout.getChildren().addAll(programaTV, scroll);
        return layout;
    }
    
    public ImageView criarImagemPrograma(){
        imagemProgramaTV = new ImageView(programaModel.getImagemPrograma());
        imagemProgramaTV.setFitHeight(250);
        imagemProgramaTV.setFitWidth(200);
        return imagemProgramaTV;
    }

    public void criarBotoes() {
        botoesTemporadas = new ArrayList();
        for (int i = 0; i < programaModel.getTemporadas().size(); i++) {
            int indice = i;
            botao = new BotaoMenu(programaModel.getTemporadas().get(i).getNomeTemporada());
            botao.setOnAction(event -> {
                final Object source = event.getSource();
                for (BotaoMenu botoesTemporada : botoesTemporadas) {
                    if(source.equals(botoesTemporadas.get(indice))){
                        try {
                            temporadaView = new TemporadaView(programaModel, programaModel.getTemporadas().get(indice));
                            central.mudarTelas(temporadaView.getLayout());
                            break;
                        } catch (Exception ex) {
                            Logger.getLogger(ProgramaTVView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                event.consume();    
            });
            botao.setAlignment(Pos.BASELINE_LEFT);
            adicionarBotaoCategorias(botao);
        }       
    }
    
    public void adicionarBotaoCategorias(BotaoMenu botao) {
        botoesTemporadas.add(botao);
    }
    
    public HBox getButtonBar() {
        return buttonBar;
    }

    public VBox getLayout() {
        return layout;
    }
}
