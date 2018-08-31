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
import model.ProgramaModel;
import model.database.Database;
import model.database.DatabaseFactory;

/**
 *
 * @author User
 */
public class TemporadaDAO {
    private final Database database = DatabaseFactory.getDatabase("sqlserver");
    private final Connection connection = database.conectar();
    private String selectSql, insertSql, deleteSql;
    
    //CREATE
    public void adicionarTemporada(ProgramaModel programa){
        try {
            selectSql = "select codTemporada from programatv inner join temporadas " +
                    "on programatv.codPrograma = temporadas.codPrograma " +
                    "where temporadas.codPrograma = " + programa.getCodPrograma();
            
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            ResultSet rs = stmt.executeQuery();

            int codTemporada = 0;
            
            while (rs.next()) {
                codTemporada = rs.getInt(1);
            }
            
            codTemporada++;
            
            System.out.println(codTemporada);
            insertSql = "insert into temporadas (codTemporada, codPrograma, nomeTemporada) values (?, ?, ?)";
            stmt = connection.prepareStatement(insertSql);
            stmt.setInt(1, codTemporada);
            stmt.setInt(2, programa.getCodPrograma());
            stmt.setString(3, codTemporada + "ª Temporada");
            System.out.println("Temporada cadastrada!");
            stmt.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle(null);
            alert.setContentText("Temporada cadastrada com sucesso!");
            alert.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }
    //READ
    public ResultSet carregarTemporadas(ProgramaModel programa) throws Exception {
        System.out.println("Carregando temporada do programa " + programa.getNomePrograma());
        try{
            selectSql = "select distinct codTemporada, nomePrograma, nomeTemporada from programatv inner join temporadas " +
                    "on programatv.codPrograma = temporadas.codPrograma " +
                    "where programatv.codPrograma = " + programa.getCodPrograma();
            
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            ResultSet rs = stmt.executeQuery();

            return rs;
            
        } catch(SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }  
        return null;
    }
    
    //DELETE
    public void removerTemporadas (ProgramaModel programa) {
        try { 
            EpisodioDAO episodioDAO = new EpisodioDAO();
            episodioDAO.removerEpisodios(programa);
            
            deleteSql = "delete from temporadas where codPrograma = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteSql);
            stmt.setInt(1, programa.getCodPrograma());
            System.out.println("temporadas removidas");
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
