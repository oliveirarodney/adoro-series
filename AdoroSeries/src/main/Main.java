/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.control.Alert;

import javafx.stage.Stage;
import model.database.Database;
import model.database.DatabaseFactory;
import view.LoginView;

/**
 *
 * @author User
 */

public class Main extends Application {
    private Database database;
    private Connection connection;
    
    private LoginView login;
    
    @Override
    public void start(Stage primaryStage) throws SQLException {
        
        try{
            database = DatabaseFactory.getDatabase("sqlserver");
            connection = database.conectar();
            
            if(connection == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("SUA CONEXÃO FALHOU");
                alert.setContentText("O PROGRAMA SERÁ FECHADO");
                alert.show();
                
            }else{
                System.out.println("instanciou login");
                login = new LoginView();
                
                primaryStage = login.getMainStage();
                primaryStage.setMaximized(true);
                primaryStage.setMinWidth(1200);
                primaryStage.setMinHeight(800);
                primaryStage.show();
            }
            
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
