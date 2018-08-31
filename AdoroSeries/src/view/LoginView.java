/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAO.UsuarioDAO;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.AutenticacaoController;
import controller.UsuarioController;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.UsuarioSingleton;
import view.components.BotaoMenu;
import view.modulos.PainelPrincipal;

/**
 *
 * @author User
 */
public final class LoginView {
    private NovoUsuarioView telaCadastrar;
    private UsuarioController usuarioController;
    private UsuarioDAO usuarioDAO;
    
    private PainelPrincipal painelPrincipal;
    private UsuarioSingleton usuario;
    
    private static final String background = ""
            + "-fx-background-color: #222222";
    private static final String txtfield = ""
            + "-fx-prompt-text-fill: #A8CF45; "
            + "-fx-text-inner-color: #A8CF45; "
            + "-fx-font-family: 'Century Gothic';"
            + "-fx-font-size: 16pt;"
            + "-jfx-unfocus-color: #A8CF45;"
            + "-jfx-focus-color: transparent";
    private static final String label = ""
            + "-fx-font-family: 'Century Gothic';"
            + "-fx-font-size: 16pt;"
            + "-fx-text-fill: #A8CF45;"
            + "-fx-border-color: transparent;";
 
    private VBox layout, logotipo, autenticar, enviar, cadastro;
    private HBox hbox;
    private ImageView logo;
    private JFXTextField username;
    private FontAwesomeIconView usernameicon;
    private JFXPasswordField password;
    private FontAwesomeIconView passwordicon;
    private BotaoMenu login;
    private Hyperlink cadastrar;
    
    private final Scene mainScene;
    private final Stage mainStage;
    
    public LoginView(){
        boxLogin();
        
        mainScene = new Scene(layout, 1200, 800);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
    }
    
    private VBox boxLogin(){
        layout = new VBox();
        layout.setSpacing(30);
        layout.alignmentProperty().set(Pos.CENTER);
        layout.setStyle(background);
        
        logotipo = new VBox();
        logotipo.getChildren().add(addLogo());
        
        autenticar = new VBox();
        autenticar.getChildren().addAll(addUsernameField(), addPasswordField());
        
        enviar = new VBox();
        enviar.getChildren().add(addButtonLogin());
        
        cadastro = new VBox();
        cadastro.getChildren().add(addLabelCadastrar());
        
        layout.getChildren().addAll(logotipo, autenticar, enviar, cadastro);
        
        return layout;
    }
    
    private HBox addLogo(){
        hbox = new HBox();
        hbox.getChildren().add(criarLogo());
        hbox.setAlignment(Pos.CENTER);

        HBox.setHgrow(hbox, Priority.ALWAYS);
        
        return hbox;
    }
    
    private HBox addUsernameField(){
        hbox = new HBox();
        hbox.setSpacing(10);
        
        usernameicon = new FontAwesomeIconView(FontAwesomeIcon.USER);
        username = new JFXTextField();
        
        hbox.getChildren().add(usernameicon);
        hbox.getChildren().add(username);
        hbox.setAlignment(Pos.CENTER);
        
        usernameicon.setFill(Color.valueOf("A8CF45"));
        usernameicon.setSize("30");
        
        username.setMaxWidth(200);
        username.setAlignment(Pos.CENTER);
        username.setStyle(txtfield);
        
        HBox.setHgrow(hbox, Priority.ALWAYS);
        
        return hbox;
    }
    
    private HBox addPasswordField(){
        hbox = new HBox();
        hbox.setSpacing(10);
        
        passwordicon = new FontAwesomeIconView(FontAwesomeIcon.LOCK);
        password = new JFXPasswordField();
        
        hbox.getChildren().add(passwordicon);
        hbox.getChildren().add(password);
        hbox.setAlignment(Pos.CENTER);

        passwordicon.setFill(Color.valueOf("A8CF45"));
        passwordicon.setSize("30");
        
        password.setMaxWidth(200);
        password.setAlignment(Pos.CENTER);
        password.setStyle(txtfield);
        
        HBox.setHgrow(hbox, Priority.ALWAYS);
        
        return hbox;
    }
    
    private HBox addButtonLogin(){
        hbox = new HBox();
        login = new BotaoMenu("Login");
        login.setOnAction(event -> {
            try {
                if (AutenticacaoController.autenticarUsuario(username.getText(), password.getText())) {
                    usuario = AutenticacaoController.getInstanciaUsuario();

                    System.out.println("Login realizado");
                    System.out.println("Usuario logado: "+usuario.getUsername());
                    painelPrincipal = new PainelPrincipal();
                    painelPrincipal.getMainStage().setMaximized(true);
                    painelPrincipal.getMainStage().setMinWidth(1200);
                    painelPrincipal.getMainStage().setMinHeight(800);
                    painelPrincipal.getMainStage().show();
                    getMainStage().close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERRO");
                    alert.setHeaderText("Erro de autenticação");
                    alert.setContentText("Usuário e/ou senha incorretos.");
                    alert.show();
                    System.out.println("erro");
                }
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        });
        hbox.getChildren().add(login);
        hbox.setAlignment(Pos.CENTER);

        login.setAlignment(Pos.CENTER);
        layout.getChildren().add(hbox);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        
        return hbox;
    }
    
    private HBox addLabelCadastrar(){
        hbox = new HBox();
        cadastrar = new Hyperlink();
        cadastrar.setText("Ainda não é membro? Cadastre-se aqui");
        cadastrar.setStyle(label);
        cadastrar.setOnAction((ActionEvent event) -> {
            usuarioDAO = new UsuarioDAO();
            usuarioController = new UsuarioController(usuarioDAO, this);
            telaCadastrar = new NovoUsuarioView(this, usuarioController);
            
            telaCadastrar.getMainStage().setMaximized(true);
            telaCadastrar.getMainStage().setMinWidth(1200);
            telaCadastrar.getMainStage().setMinHeight(800);
            telaCadastrar.getMainStage().show();
            getMainStage().hide();
        });
        hbox.getChildren().add(cadastrar);
        hbox.setAlignment(Pos.CENTER);
        
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }
    
    private ImageView criarLogo(){
        logo = new ImageView("/view/resources/adoroseries.png");
        logo.setFitHeight(49);
        logo.setFitWidth(407);
        return logo;
    }
    
    public Stage getMainStage(){
        return mainStage;
    }
   
}
