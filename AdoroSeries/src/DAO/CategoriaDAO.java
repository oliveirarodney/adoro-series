/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import controller.CategoriasController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import model.database.Database;
import model.database.DatabaseFactory;

/**
 *
 * @author User
 */
public class CategoriaDAO {
    private String selectSql, insertSql;
    private final Database database = DatabaseFactory.getDatabase("sqlserver");
    private final Connection connection = database.conectar();
        
    //CREATE
    public void inserirCategoriaPrograma(CategoriasController controller, String categoria) throws Exception {
        try {
            insertSql = "insert into categorias (nomeCategoria) values (?)";
            PreparedStatement stmt = connection.prepareStatement(insertSql);
            stmt.setString(1, categoria);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }
    
    //READ
    public ResultSet carregarCategoriasPrograma(CategoriasController controller) throws Exception { 
        try {
            selectSql = "SELECT * FROM categorias";

            PreparedStatement stmt = connection.prepareStatement(selectSql);
            ResultSet rs = stmt.executeQuery();
            
            return rs;
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
        return null;
    }
}
