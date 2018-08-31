/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import model.UsuarioSingleton;
import model.database.Database;
import model.database.DatabaseFactory;

/**
 *
 * @author maykh
 */
public class AutenticacaoDAO {
    
    private final UsuarioSingleton usuario = UsuarioSingleton.getInstancia();
    private final Database database = DatabaseFactory.getDatabase("sqlserver");
    private final Connection connection = database.conectar();
    private String selectSql;
    
    //AUTENTICAR USUARIO
    public UsuarioSingleton getUsuarioSingleton(String login, String senha) throws Exception {
        try {
            selectSql = "SELECT codUsuario FROM Usuario WHERE userName = '" + login
                    + "' AND senha = '" + senha + "'";

            PreparedStatement stmt = connection.prepareStatement(selectSql);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return carregarUsuario(rs.getInt(1));
            }
            
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
        return null;
    }
    
    //CARREGAR USUARIO AUTENTICADO
    public UsuarioSingleton carregarUsuario(int codigo) throws Exception {
        try {
            selectSql = "SELECT codUsuario, tipoUsuario, userName,email,senha FROM Usuario WHERE Usuario.codUsuario =" + codigo;
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            usuario.setCodUsuario(rs.getInt(1));    
            usuario.setTipoUsuario(rs.getInt(2));
            usuario.setUsername(rs.getString(3));
            usuario.setEmail(rs.getString(4));
            usuario.setSenha(rs.getString(5));

            return usuario;
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
