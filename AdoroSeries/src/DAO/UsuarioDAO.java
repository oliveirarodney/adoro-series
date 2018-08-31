/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import model.Usuario;
import model.database.Database;
import model.database.DatabaseFactory;


/**
 *
 * @author User
 */
public class UsuarioDAO {
    private String insertSql;
    private final Database database = DatabaseFactory.getDatabase("sqlserver");
    private final Connection connection = database.conectar();
    
    //CREATE
    public void save(Usuario usuario) throws Exception {
        try {
            insertSql = "INSERT INTO usuario VALUES(?, ?, ?, ?)";

            PreparedStatement stmt = connection.prepareStatement(insertSql);
            stmt.setInt(1, 2);
            stmt.setString(2, usuario.getUsername());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }
}
