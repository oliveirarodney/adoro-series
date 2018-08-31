/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAO.CategoriaDAO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import controller.CategoriasController;
import controller.EpisodioController;
import controller.ProgramaController;
import controller.TemporadaController;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.ProgramaModel;
import model.TemporadaModel;
import view.components.BotaoMenu;
import view.modulos.ModuloCentral;

/**
 *
 * @author User
 */
public final class CadastrarEpisodioView {

    private final ModuloCentral central;
    private final ProgramaController programaController;
    private final TemporadaController temporadaController;
    private final EpisodioController episodioController;

    private static final String background = "-fx-background-color: #222222";
    private static final String txtfield = "-fx-prompt-text-fill: #A8CF45; "
            + "-fx-text-inner-color: #222222;"
            + "-fx-font-family: 'Century Gothic';"
            + "-fx-font-size: 16pt;"
            + "-jfx-unfocus-color: #A8CF45;"
            + "-jfx-focus-color: transparent";
    private static final String titulo = "-fx-font-family: 'Century Gothic';"
            + "-fx-font-size: 25pt;"
            + "-fx-text-fill: #A8CF45;";
    private static final String label = "-fx-font-family: 'Century Gothic';"
            + "-fx-font-size: 16pt;"
            + "-fx-text-fill: #222222;";

    private VBox layout, componentes, enviar;
    private HBox hbox, teste;
    private Label criarEpisodio, nomeEpisodioLabel, selecionarProgramaLabel, selecionarTemporadaLabel, selecionarDataExibicao;
    private JFXTextField nomeEpisodio;
    private JFXComboBox<ProgramaModel> selecionarPrograma;
    private JFXComboBox<TemporadaModel> selecionarTemporada;
    private JFXDatePicker dataExibicao;
    private BotaoMenu botaoConcluir, botaoAdicionarPrograma, botaoAdicionarTemporada;

    public CadastrarEpisodioView(ModuloCentral central, ProgramaController programaController, TemporadaController temporadaController) throws FileNotFoundException, Exception {
        this.central = central;
        this.programaController = programaController;
        this.temporadaController = temporadaController;
        episodioController = new EpisodioController();
        criarTela();
    }

    private VBox criarTela() throws FileNotFoundException, Exception {
        layout = new VBox();
        layout.setPadding(new Insets(50));
        layout.setStyle("-fx-background-color: white;");
        layout.alignmentProperty().set(Pos.CENTER);

        componentes = new VBox();
        componentes.setSpacing(30);
        
        criarEpisodio = new Label("Criar Episodio");
        criarEpisodio.setStyle(titulo);
        
        componentes.getChildren().addAll(criarEpisodio, addNomeEpisodio(), addSelecionarPrograma(), addSelecionarTemporada(), addDataExibicao());
        componentes.setAlignment(Pos.CENTER);

        enviar = new VBox();
        enviar.getChildren().add(addButtonConcluir());
        enviar.setAlignment(Pos.BOTTOM_RIGHT);

        layout.getChildren().addAll(componentes, enviar);

        VBox.setVgrow(enviar, Priority.ALWAYS);
        return layout;
    }

    private HBox addNomeEpisodio() {
        hbox = new HBox();
        hbox.setSpacing(10);

        nomeEpisodioLabel = new Label("Nome Episódio: ");
        nomeEpisodio = new JFXTextField();

        hbox.getChildren().addAll(nomeEpisodioLabel, nomeEpisodio);

        nomeEpisodioLabel.setStyle(label);
        nomeEpisodioLabel.setMinWidth(250);
        nomeEpisodio.setStyle(txtfield);
        nomeEpisodio.prefWidthProperty().bind(hbox.widthProperty());

        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);

        return hbox;
    }
    
    private HBox addSelecionarPrograma() throws Exception {

        hbox = new HBox();
        hbox.setSpacing(10);

        selecionarProgramaLabel = new Label("Selecionar Programa: ");
        selecionarPrograma = new JFXComboBox<>();
        selecionarPrograma.setStyle(label);
        
        programaController.inserirProgramasComboBox(this);
        botaoAdicionarPrograma = new BotaoMenu("+ Novo Programa");
        botaoAdicionarPrograma.setMinWidth(250);
        botaoAdicionarPrograma.setOnAction(Event -> {
            try {
                CategoriaDAO categoriaDAO = new CategoriaDAO();
                CategoriasController categoriasController = new CategoriasController(categoriaDAO);
                CadastrarProgramaView cadastrarProgramaView = new CadastrarProgramaView(central, programaController, categoriasController);
                central.mudarTelas(cadastrarProgramaView.getLayout());
            } catch (Exception ex) {
                Logger.getLogger(CadastrarEpisodioView.class.getName()).log(Level.SEVERE, null, ex);
            }           
        });

        hbox.getChildren().add(selecionarProgramaLabel);
        hbox.getChildren().addAll(selecionarPrograma, botaoAdicionarPrograma);

        selecionarProgramaLabel.setStyle(label);
        selecionarProgramaLabel.setMinWidth(250);
        selecionarPrograma.setStyle(txtfield);
        selecionarPrograma.prefWidthProperty().bind(hbox.widthProperty());

        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }
    
    private HBox addSelecionarTemporada() throws Exception {

        hbox = new HBox();
        hbox.setSpacing(10);

        selecionarTemporadaLabel = new Label("Selecionar Temporada: ");
        selecionarTemporada = new JFXComboBox<>();
        selecionarTemporada.setStyle(label);
        
        selecionarPrograma.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                for (ProgramaModel programa : programaController.getProgramas()) {
                    System.out.println("New Value: "+newValue);
                    System.out.println("Programa: "+programa.getNomePrograma());
                    if(newValue.equals(programa)){
                        selecionarTemporada.getItems().clear();
                        botaoAdicionarTemporada.setDisable(false);
                        botaoConcluir.setDisable(false);
                        temporadaController.carregarTemporadas(CadastrarEpisodioView.this, programa);
                    }
                }              
            } catch (Exception ex) {
                Logger.getLogger(CadastrarEpisodioView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        botaoAdicionarTemporada = new BotaoMenu("+ Nova Temporada");
        botaoAdicionarTemporada.setDisable(true);
        botaoAdicionarTemporada.setMinWidth(250);
        botaoAdicionarTemporada.setOnAction(Event -> {
            try {
                selecionarTemporada.getItems().clear();
                temporadaController.adicionarTemporada(CadastrarEpisodioView.this, selecionarPrograma.getSelectionModel().getSelectedItem());
            } catch (Exception ex) {
                Logger.getLogger(CadastrarEpisodioView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        hbox.getChildren().add(selecionarTemporadaLabel);
        hbox.getChildren().addAll(selecionarTemporada, botaoAdicionarTemporada);

        selecionarTemporadaLabel.setStyle(label);
        selecionarTemporadaLabel.setMinWidth(250);
        selecionarTemporada.setStyle(txtfield);
        selecionarTemporada.prefWidthProperty().bind(hbox.widthProperty());

        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }
    
    private HBox addDataExibicao() {
        hbox = new HBox();
        hbox.setSpacing(10);

        selecionarDataExibicao = new Label("Data Exibição: ");
        dataExibicao = new JFXDatePicker();
        
        hbox.getChildren().addAll(selecionarDataExibicao, dataExibicao);
        
        selecionarDataExibicao.setStyle(label);
        selecionarDataExibicao.setMinWidth(250);
        dataExibicao.setStyle(txtfield);
        dataExibicao.prefWidthProperty().bind(hbox.widthProperty());
        
        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }
    
    private HBox addButtonConcluir() {
        hbox = new HBox();
        hbox.setSpacing(50);
        botaoConcluir = new BotaoMenu("Concluir");
        botaoConcluir.setDisable(true);
        botaoConcluir.setOnAction(event -> {
            try {
                if(nomeEpisodio.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("ERRO");
                    alert.setTitle(null);
                    alert.setContentText("Insira um nome");
                    alert.show();
                } else if(selecionarPrograma.getSelectionModel().getSelectedItem().getNomePrograma().equals("")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("ERRO");
                    alert.setTitle(null);
                    alert.setContentText("Insira um nome");
                    alert.show();
                } else if(dataExibicao.getValue().toString().equals("")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("ERRO");
                    alert.setTitle(null);
                    alert.setContentText("Insira uma data");
                    alert.show();
                } else {
                    episodioController.cadastrarEpisodio(nomeEpisodio.getText(), selecionarPrograma.getSelectionModel().getSelectedItem(), selecionarTemporada.getSelectionModel().getSelectedItem(), dataExibicao.getValue().toString());
                    nomeEpisodio.clear();
                    selecionarTemporada.getSelectionModel().clearSelection();
                    selecionarPrograma.getSelectionModel().selectFirst();
                    dataExibicao.getEditor().clear();
                }
            } catch (Exception ex) {
                Logger.getLogger(NovoUsuarioView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        hbox.getChildren().addAll(botaoConcluir);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);

        botaoConcluir.setAlignment(Pos.CENTER);

        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }

    public JFXTextField getUsername() {
        return nomeEpisodio;
    }

    public JFXComboBox getSelecionarTemporada() {
        return selecionarTemporada;
    }

    public JFXComboBox getSelecionarPrograma() {
        return selecionarPrograma;
    }

    public BotaoMenu getBotaoConcluir() {
        return botaoConcluir;
    }

    public VBox getLayout() {
        return layout;
    }
}
