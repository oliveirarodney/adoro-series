/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.ProgramaController;
import controller.CategoriasController;
import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CategoriaModel;
import model.TipoModel;
import view.components.BotaoMenu;
import view.modulos.ModuloCentral;

/**
 *
 * @author User
 */
public final class CadastrarProgramaView {

    private final ModuloCentral central;
    private final ProgramaController programaController;
    private final CategoriasController categoriasController;

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
    private Label criarCadastro, nomeProgramaLabel, selecionarTipoLabel, selecionarCategoriaLabel, addCategoriaLabel, imagemLabel;
    private JFXTextField nomePrograma, addCategoria, caminhoImagem;
    private JFXComboBox<TipoModel> selecionarTipo;
    private JFXComboBox<CategoriaModel> selecionarCategoria;
    private BotaoMenu botaoConcluir, botaoAdicionarCategoria, botaoEscolherImagem, botaoConfirmar;
    private File imagemSelecionada;

    public CadastrarProgramaView(ModuloCentral central, ProgramaController programaController, CategoriasController categoriasController) throws FileNotFoundException, Exception {
        this.central = central;
        this.programaController = programaController;
        this.categoriasController = categoriasController;
        criarTela();
    }

    private VBox criarTela() throws FileNotFoundException, Exception {
        layout = new VBox();
        layout.setPadding(new Insets(50));
        layout.setStyle("-fx-background-color: white;");
        layout.alignmentProperty().set(Pos.CENTER);

        componentes = new VBox();
        componentes.setSpacing(30);
        criarCadastro = new Label("Criar Programa");
        criarCadastro.setStyle(titulo);
        teste = new HBox();
        componentes.getChildren().addAll(criarCadastro, addNomePrograma(), addImagem(), addSelecionarTipo(), addSelecionarCategoria(), teste);
        componentes.setAlignment(Pos.CENTER);

        enviar = new VBox();
        enviar.getChildren().add(addButtonConcluir());
        enviar.setAlignment(Pos.BOTTOM_RIGHT);

        layout.getChildren().addAll(componentes, enviar);

        VBox.setVgrow(enviar, Priority.ALWAYS);
        return layout;
    }

    private HBox addNomePrograma() {
        hbox = new HBox();
        hbox.setSpacing(10);

        nomeProgramaLabel = new Label("Nome Programa: ");
        nomePrograma = new JFXTextField();

        hbox.getChildren().addAll(nomeProgramaLabel, nomePrograma);

        nomeProgramaLabel.setStyle(label);
        nomeProgramaLabel.setMinWidth(250);
        nomePrograma.setStyle(txtfield);
        nomePrograma.prefWidthProperty().bind(hbox.widthProperty());

        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);

        return hbox;
    }

    private HBox addSelecionarTipo() {

        hbox = new HBox();
        hbox.setSpacing(10);

        selecionarTipoLabel = new Label("Selecionar Tipo: ");
        selecionarTipo = new JFXComboBox<>();
        selecionarTipo.setStyle(label);

        hbox.getChildren().add(selecionarTipoLabel);
        hbox.getChildren().add(selecionarTipo);

        selecionarTipoLabel.setStyle(label);
        selecionarTipoLabel.setMinWidth(250);
        selecionarTipo.setStyle(txtfield);
        selecionarTipo.prefWidthProperty().bind(hbox.widthProperty());

        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }

    private HBox addSelecionarCategoria() throws Exception {

        hbox = new HBox();
        hbox.setSpacing(10);

        selecionarCategoriaLabel = new Label("Selecionar Categoria: ");
        selecionarCategoria = new JFXComboBox<>();
        selecionarCategoria.setStyle(label);
        
        categoriasController.inserirCategoriaComboBoxCadastrar(this);
        botaoAdicionarCategoria = new BotaoMenu("+ Nova Categoria");
        botaoAdicionarCategoria.setMinWidth(250);
        botaoAdicionarCategoria.setOnAction(Event -> {
            addNovaCategoria();
            teste.maxHeight(50);
            botaoAdicionarCategoria.setDisable(true);
            nomePrograma.setDisable(true);
            botaoEscolherImagem.setDisable(true);
            selecionarTipo.setDisable(true);
            selecionarCategoria.setDisable(true);
            botaoConcluir.setDisable(true);           
        });

        hbox.getChildren().add(selecionarCategoriaLabel);
        hbox.getChildren().addAll(selecionarCategoria, botaoAdicionarCategoria);

        selecionarCategoriaLabel.setStyle(label);
        selecionarCategoriaLabel.setMinWidth(250);
        selecionarCategoria.setStyle(txtfield);
        selecionarCategoria.prefWidthProperty().bind(hbox.widthProperty());

        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }

    private HBox addNovaCategoria() {
        teste.setSpacing(10);

        addCategoriaLabel = new Label("Adicionar Categoria: ");
        addCategoria = new JFXTextField();
        
        botaoConfirmar = new BotaoMenu("Confirmar");
        botaoConfirmar.setMinWidth(250);
        botaoConfirmar.setOnAction(Event -> {  
            if(addCategoria.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("ERRO");
                alert.setTitle("Teste");
                alert.setContentText("Verifique se o campo está preenchido");
                alert.show();
            } else {
                try {
                    selecionarCategoria.getItems().clear();
                    categoriasController.criarCategoria(addCategoria.getText(), this);
                    //selecionarCategoria.valueProperty().set(null);                 
                    categoriasController.inserirCategoriaComboBoxCadastrar(this);
                } catch (Exception ex) {
                    Logger.getLogger(CadastrarProgramaView.class.getName()).log(Level.SEVERE, null, ex);
                }
                teste.getChildren().clear();
                botaoAdicionarCategoria.setDisable(false);
                nomePrograma.setDisable(false);
                botaoEscolherImagem.setDisable(false);
                selecionarTipo.setDisable(false);
                selecionarCategoria.setDisable(false);
                botaoConcluir.setDisable(false); 
                teste.maxHeight(0);            
            }       
        });
        teste.getChildren().add(addCategoriaLabel);
        teste.getChildren().addAll(addCategoria, botaoConfirmar);

        addCategoriaLabel.setStyle(label);
        addCategoriaLabel.setMinWidth(250);
        addCategoria.setStyle(txtfield);
        addCategoria.prefWidthProperty().bind(hbox.widthProperty());

        teste.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(teste, Priority.ALWAYS);
        return teste;
    }
    
    private HBox addImagem() throws FileNotFoundException {
        hbox = new HBox();
        hbox.setSpacing(10);

        imagemLabel = new Label("Imagem: ");
        caminhoImagem = new JFXTextField();
        caminhoImagem.setEditable(false);
        caminhoImagem.setText("");
        botaoEscolherImagem = new BotaoMenu("Pesquisar");
        botaoEscolherImagem.setMinWidth(250);
        botaoEscolherImagem.setOnAction(Event -> {
            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Escolher imagem");
            imagemSelecionada = fileChooser.showOpenDialog(new Stage());
            if(imagemSelecionada != null) {
                caminhoImagem.setText(imagemSelecionada.getPath());
                System.out.println(caminhoImagem.getText());
            }
        });

        hbox.getChildren().add(imagemLabel);
        hbox.getChildren().addAll(caminhoImagem, botaoEscolherImagem);

        imagemLabel.setStyle(label);
        imagemLabel.setMinWidth(250);
        caminhoImagem.setStyle(txtfield);
        caminhoImagem.prefWidthProperty().bind(hbox.widthProperty());

        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }

    private HBox addButtonConcluir() {
        hbox = new HBox();
        hbox.setSpacing(50);
        botaoConcluir = new BotaoMenu("Concluir");
        botaoConcluir.setOnAction(event -> {
            System.out.println("Tentando cadastrar");
            try {
                if(nomePrograma.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("ERRO");
                    alert.setTitle("Teste");
                    alert.setContentText("Insira um nome");
                    alert.show();
                } else if(caminhoImagem.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("ERRO");
                    alert.setTitle("Teste");
                    alert.setContentText("Insira uma imagem");
                    alert.show();
                } else {
                    System.out.println("Programa cadastrado!");
                    programaController.CadastrarProgramas(nomePrograma.getText(), imagemSelecionada, selecionarTipo.getSelectionModel().getSelectedItem(), selecionarCategoria.getSelectionModel().getSelectedItem());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("");
                    alert.setTitle("");
                    alert.setContentText("Programa cadastrado com sucesso!");
                    alert.showAndWait();
                    nomePrograma.clear();
                    caminhoImagem.clear();
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
        return nomePrograma;
    }

    public JFXTextField getNovaCategoria() {
        return addCategoria;
    }

    public JFXComboBox getSelecionarTipo() {
        return selecionarTipo;
    }

    public JFXComboBox getSelecionarCategoria() {
        return selecionarCategoria;
    }

    public JFXComboBox getConfirmarPassword() {
        return selecionarCategoria;
    }

    public BotaoMenu getBotaoConcluir() {
        return botaoConcluir;
    }


    public VBox getLayout() {
        return layout;
    }
}
