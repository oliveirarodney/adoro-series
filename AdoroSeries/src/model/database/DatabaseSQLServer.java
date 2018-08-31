/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author maykh
 */
public class DatabaseSQLServer implements Database {

    private Connection connection;
    
    @Override
    public Connection conectar() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           String url = "jdbc:sqlserver://"
                    +"localhost:1433;"
                   +"database=bancodeseries;"
                    +"user=bancodeseries;"
                    +"password=bancodeseries;";
           
                    this.connection = DriverManager.getConnection(url);
            System.out.println("Conectado");
            return this.connection;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Desconectado: "+ex.getMessage());
            return null;
        }
    }

    @Override
    public void desconectar(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao desconectar banco");
        }
    }
}
