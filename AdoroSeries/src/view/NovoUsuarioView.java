/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.UsuarioController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.components.BotaoCancelar;
import view.components.BotaoMenu;
import view.modulos.ModuloTopo;

/**
 *
 * @author User
 */
public final class NovoUsuarioView {
    private final LoginView login;
    private final UsuarioController usuarioController;
    
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
    private HBox hbox;
    private Label criarCadastro, usernameLabel, passwordLabel, confirmarPasswordLabel, emailLabel, confirmarEmailLabel;
    private JFXTextField username, email, confirmarEmail;
    private JFXPasswordField password, confirmarPassword;
    private BotaoMenu botaoConcluir; 
    private BotaoCancelar botaoCancelar;
    
    private BorderPane telaCadastro;
    private ModuloTopo moduloTopo;
    
    private final Scene mainScene;
    private final Stage mainStage;

    public NovoUsuarioView(LoginView login, UsuarioController usuarioController) {
        this.login = login;
        this.usuarioController = usuarioController;
        telaCadastrar();
        mainScene = new Scene(telaCadastro, 1200, 800);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
    }
    
    private BorderPane telaCadastrar(){
        boxLogin();
        telaCadastro = new BorderPane();
        moduloTopo = new ModuloTopo();
        telaCadastro.setTop(moduloTopo.getTopo());
        
        telaCadastro.setCenter(layout);
        
        return telaCadastro;  
    } 

    private VBox boxLogin() {
        layout = new VBox();
        layout.setPadding(new Insets(50));
        layout.setStyle("-fx-background-color: white;");
        layout.alignmentProperty().set(Pos.CENTER);
        
        componentes = new VBox();
        componentes.setSpacing(30);
        criarCadastro = new Label("Criar Cadastro");
        criarCadastro.setStyle(titulo);
        componentes.getChildren().addAll(criarCadastro, addUsernameField(), addPasswordField(), addConfirmarPasswordField(), addEmailField(), addConfirmarEmailField());
        componentes.setAlignment(Pos.CENTER);
        
        enviar = new VBox();
        enviar.getChildren().add(addButtonConcluir());
        enviar.setAlignment(Pos.BOTTOM_RIGHT);
        
        layout.getChildren().addAll(componentes, enviar);
        
        VBox.setVgrow(enviar, Priority.ALWAYS);
        return layout;
    }

    private HBox addUsernameField() {
        hbox = new HBox();
        hbox.setSpacing(10);
        
        usernameLabel = new Label("Usuário: ");
        username = new JFXTextField();
        
        hbox.getChildren().addAll(usernameLabel, username);
        
        usernameLabel.setStyle(label);
        usernameLabel.setMinWidth(100);
        username.setStyle(txtfield);
        username.prefWidthProperty().bind(hbox.widthProperty());

        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        
        return hbox;
    }
    
    private HBox addPasswordField() {

        hbox = new HBox();
        hbox.setSpacing(10);
        
        passwordLabel = new Label("Senha: ");
        password = new JFXPasswordField();
        
        hbox.getChildren().add(passwordLabel);
        hbox.getChildren().add(password);
        
        passwordLabel.setStyle(label);
        passwordLabel.setMinWidth(100);
        password.setStyle(txtfield);
        password.prefWidthProperty().bind(hbox.widthProperty());

        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }
    
    private HBox addConfirmarPasswordField() {

        hbox = new HBox();
        hbox.setSpacing(10);
        
        confirmarPasswordLabel = new Label("Confirmar sua senha: ");
        confirmarPassword = new JFXPasswordField();
        
        hbox.getChildren().add(confirmarPasswordLabel);
        hbox.getChildren().add(confirmarPassword);
        
        confirmarPasswordLabel.setStyle(label);
        confirmarPasswordLabel.setMinWidth(250);
        confirmarPassword.setStyle(txtfield);
        confirmarPassword.prefWidthProperty().bind(hbox.widthProperty());

        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }
    
    private HBox addEmailField() {
        hbox = new HBox();
        hbox.setSpacing(10);
        
        emailLabel = new Label("E-mail: ");
        email = new JFXTextField();
        
        hbox.getChildren().add(emailLabel);
        hbox.getChildren().add(email);
        
        emailLabel.setStyle(label);
        emailLabel.setMinWidth(100);
        email.setStyle(txtfield);
        email.prefWidthProperty().bind(hbox.widthProperty());

        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }
    
    private HBox addConfirmarEmailField() {
        hbox = new HBox();
        hbox.setSpacing(10);
        
        confirmarEmailLabel = new Label("Confirmar seu e-mail: ");
        confirmarEmail = new JFXTextField();
        
        hbox.getChildren().addAll(confirmarEmailLabel, confirmarEmail);
        
        confirmarEmailLabel.setStyle(label);
        confirmarEmail.setStyle(txtfield);
        confirmarEmailLabel.setMinWidth(250);
        confirmarEmail.prefWidthProperty().bind(hbox.widthProperty());

        hbox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }
        
    private HBox addButtonConcluir() {
        hbox = new HBox();
        hbox.setSpacing(50);
        botaoConcluir = new BotaoMenu("Concluir");
        botaoConcluir.setOnAction(event -> {
            try {
                usuarioController.cadastrarUsuario(username.getText(), email.getText(), confirmarEmail.getText(), password.getText(), confirmarPassword.getText(), this);
                
            } catch (Exception ex) {
                Logger.getLogger(NovoUsuarioView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        botaoCancelar = new BotaoCancelar("Cancelar");
        botaoCancelar.setOnAction(event -> {
            System.out.println("Cadastrar clicado");
            login.getMainStage().show();
            mainStage.close();
        }); 
        hbox.getChildren().addAll(botaoCancelar, botaoConcluir);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);

        botaoConcluir.setAlignment(Pos.CENTER);

        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }

    public JFXTextField getUsername() {
        return username;
    }

    public void setUsername(JFXTextField username) {
        this.username = username;
    }

    public JFXTextField getEmail() {
        return email;
    }

    public void setEmail(JFXTextField email) {
        this.email = email;
    }

    public JFXTextField getConfirmarEmail() {
        return confirmarEmail;
    }

    public void setConfirmarEmail(JFXTextField confirmarEmail) {
        this.confirmarEmail = confirmarEmail;
    }

    public JFXPasswordField getPassword() {
        return password;
    }

    public void setPassword(JFXPasswordField password) {
        this.password = password;
    }

    public JFXPasswordField getConfirmarPassword() {
        return confirmarPassword;
    }

    public void setConfirmarPassword(JFXPasswordField confirmarPassword) {
        this.confirmarPassword = confirmarPassword;
    }

    public BotaoMenu getBotaoConcluir() {
        return botaoConcluir;
    }

    public void setBotaoConcluir(BotaoMenu botaoConcluir) {
        this.botaoConcluir = botaoConcluir;
    }

    public BotaoCancelar getBotaoCancelar() {
        return botaoCancelar;
    }

    public void setBotaoCancelar(BotaoCancelar botaoCancelar) {
        this.botaoCancelar = botaoCancelar;
    }
    
    public Stage getMainStage(){
        return mainStage;
    }
}
