/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import DAO.AutenticacaoDAO;
import model.UsuarioSingleton;
import model.database.Database;
import model.database.DatabaseFactory;

/**
 *
 * @author maykh
 */
public class AutenticacaoController {
    
    private static UsuarioSingleton usuario;
    
    private Database database = DatabaseFactory.getDatabase("sqlserver");
    private Connection connection = database.conectar();
    
    
    public static boolean autenticarUsuario(String username, String senha) throws Exception{ 
        
        if((usuario = new AutenticacaoDAO().getUsuarioSingleton(username,senha))==null)
            return false;
        
        return true;
    }
    
    public static UsuarioSingleton getInstanciaUsuario(){
        return AutenticacaoController.usuario;
    }
}
