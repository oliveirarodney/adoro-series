/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.EpisodioController;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ProgramaModel;
import model.TemporadaModel;
import org.controlsfx.control.Rating;

/**
 *
 * @author User
 */
public final class TemporadaView {
    private final ProgramaModel programaModel;
    private final TemporadaModel temporadaModel;
    private final EpisodioController episodioController;

    private static final String padding = ""
            + "-fx-padding: 80;";
    private static final String ep = ""
            + "-fx-font-family: 'Century Gothic';"
            + "-fx-font-size: 12pt;"
            + "-fx-text-fill: #222222";
    private static final String titulo = ""
            + "-fx-font-family: 'Century Gothic';"
            + "-fx-font-size: 25pt;"
            + "-fx-text-fill: #A8CF45";
    private static final String separatorStyle = ""
            + "-fx-background-color: #A8CF45;";
    private static final String notas = ""
            + "-fx-font-family: 'Century Gothic';"
            + "-fx-font-size: 18pt;"
            + "-fx-text-fill: #222222;";
    
    private Label tituloProgramaTV, temporadaLabel, episodio, notaMedia, suaNotaMedia;
    private VBox layout, infoProgramaTV, temporada;
    private ScrollPane scroll;
    private List<Label> episodios;
    private HBox episodioBar, episodioRating, programaTV;
    private ImageView imagemProgramaTV;
    private Rating rating;
    private Separator separator;
    private Double notaGeral, suaNota;
    
    public TemporadaView (ProgramaModel programaModel, TemporadaModel temporadaModel) throws Exception{
        this.programaModel = programaModel;
        this.temporadaModel = temporadaModel;
        episodioController = new EpisodioController();
        episodioController.carregarEpisodios(temporadaModel, programaModel, this);
        iniciarLayout();
    }
    public VBox iniciarLayout() {
        layout = new VBox();
        layout.setPadding(new Insets(50));
        layout.setSpacing(20);
        layout.setStyle("-fx-background-color: white;");
        layout.alignmentProperty().set(Pos.TOP_CENTER);
        
        programaTV = new HBox();
        programaTV.setSpacing(30);
        infoProgramaTV = new VBox();
        infoProgramaTV.setSpacing(25);
        criarImagemPrograma();
        tituloProgramaTV = new Label(programaModel.getNomePrograma());
        tituloProgramaTV.setStyle(titulo);
        temporadaLabel = new Label(temporadaModel.getNomeTemporada());
        temporadaLabel.setStyle(titulo);
        DecimalFormat df = new DecimalFormat("###,##0.00");
        
        notaGeral = episodioController.getNotaGeral(programaModel, temporadaModel);
        suaNota = episodioController.getSuaNota(programaModel, temporadaModel);
        
        notaMedia = new Label("Nota geral: " + df.format(notaGeral));
        notaMedia.setStyle(notas);
        
        suaNotaMedia = new Label("Sua nota: " + df.format(suaNota));
        suaNotaMedia.setStyle(notas);
        
        infoProgramaTV.getChildren().addAll(tituloProgramaTV, temporadaLabel, notaMedia, suaNotaMedia);
        programaTV.getChildren().addAll(imagemProgramaTV, infoProgramaTV);
        
        scroll = new ScrollPane();
        temporada = new VBox();
        scroll.setContent(temporada);
        scroll.setStyle("-fx-background: white; -fx-border-color: white; -fx-focus-color: white; -fx-faint-focus-color: white;");
        scroll.prefWidthProperty().bind(layout.widthProperty());
        scroll.prefHeightProperty().bind(layout.heightProperty());
        
        episodios = new ArrayList();
        
        System.out.println(temporadaModel.getEpisodios().size());
        for (int i = 0; i < temporadaModel.getEpisodios().size(); i++) {         
            int indice = i;
            episodioBar = new HBox();
            episodioRating = new HBox();
            System.out.println(temporadaModel.getEpisodios().get(i).getCodEpisodio() + " - " + temporadaModel.getEpisodios().get(i).getNomeEpisodio() + " - " + temporadaModel.getEpisodios().get(i).getExibicaoEpisodio());
            episodio = new Label(temporadaModel.getEpisodios().get(i).getCodEpisodio() + " - " + temporadaModel.getEpisodios().get(i).getNomeEpisodio() + " - " + temporadaModel.getEpisodios().get(i).getExibicaoEpisodio());
            episodio.setStyle(ep);
            episodio.setAlignment(Pos.CENTER_LEFT);
            
            if(temporadaModel.getEpisodios().get(i).getNota() > 0){
                rating = new Rating(5, temporadaModel.getEpisodios().get(i).getNota());
            } else {
                rating = new Rating(5, 0);
            }
            
            rating.setScaleX(0.8); 
            rating.setScaleY(0.8);
            rating.ratingProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                System.out.println("Mudou valor: Novo valor: " + newValue);
                if((Double)oldValue == 0.0){
                    episodioController.avaliarEpisodio(programaModel, temporadaModel, temporadaModel.getEpisodios().get(indice), (Double) newValue);
                } else {
                    episodioController.editarAvaliacaoEpisodio(programaModel, temporadaModel, temporadaModel.getEpisodios().get(indice), (Double) newValue);
                }
            });
            adicionarEpisodiosTemporada(episodio);
            episodioBar.getChildren().add(episodio);
            episodioRating.getChildren().addAll(episodioBar, rating);
            separator = new Separator();
            separator.setOrientation(Orientation.HORIZONTAL);
            separator.setStyle(separatorStyle);
            separator.setPrefHeight(0.2);
            temporada.getChildren().addAll(episodioRating, separator);
            temporada.setSpacing(20);
            episodioBar.setAlignment(Pos.CENTER_LEFT);
            episodios.get(i).prefWidthProperty().bind(scroll.widthProperty().subtract(220));             
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

    public void adicionarEpisodiosTemporada(Label ep) {
        episodios.add(episodio);
    }

    public VBox getLayout() {
        return layout;
    }
}