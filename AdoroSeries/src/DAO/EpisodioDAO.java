/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import controller.AutenticacaoController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import model.EpisodioModel;
import model.ProgramaModel;
import model.TemporadaModel;
import model.UsuarioSingleton;
import model.database.Database;
import model.database.DatabaseFactory;

/**
 *
 * @author User
 */
public class EpisodioDAO {
    private final Database database = DatabaseFactory.getDatabase("sqlserver");
    private final Connection connection = database.conectar();
    private String selectSql, insertSql, updateSql, deleteSql;
    private final UsuarioSingleton usuario = AutenticacaoController.getInstanciaUsuario();
    
    //CREATE
    public void inserirEpisodio(String nomeEpisodio, ProgramaModel programa, TemporadaModel temporada, String dataExibicao) throws Exception {
        try {
            selectSql = "select distinct codEpisodio, nomeEpisodio, dataExibicao from programatv inner join temporadas " +
                    "on programatv.codPrograma = temporadas.codPrograma inner join episodios " +
                    "on episodios.codPrograma = temporadas.codPrograma " +
                    "where episodios.codPrograma = " + programa.getCodPrograma() + " "+
                    "and episodios.codTemporada = " + temporada.getCodTemporada();
            
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            ResultSet rs = stmt.executeQuery();

            int codEpisodio = 0;
            
            while (rs.next()) {
                codEpisodio = rs.getInt(1);
            }
            
            codEpisodio++;
            
            System.out.println(codEpisodio);
            insertSql = "insert into episodios (codEpisodio, codTemporada, codPrograma, nomeEpisodio, dataExibicao) values (?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(insertSql);
            stmt.setInt(1, codEpisodio);
            stmt.setInt(2, temporada.getCodTemporada());
            stmt.setInt(3, programa.getCodPrograma());
            stmt.setString(4, nomeEpisodio);
            stmt.setString(5, dataExibicao);
            System.out.println("Episódio cadastrado!");
            stmt.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle(null);
            alert.setContentText("Episódio cadastrado com sucesso!");
            alert.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }  
    public void avaliarEpisodio(ProgramaModel programa, TemporadaModel temporada, EpisodioModel episodio, Double notaEpisodio) {
        try {
            insertSql = "insert into episodiosAssistidos (codUsuario, codEpisodio, codTemporada, codPrograma, notaEpisodio) values (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertSql);
            stmt.setInt(1, usuario.getCodUsuario());
            stmt.setInt(2, episodio.getCodEpisodio());
            stmt.setInt(3, temporada.getCodTemporada());
            stmt.setInt(4, programa.getCodPrograma());
            stmt.setDouble(5, notaEpisodio);
            System.out.println("Episódio avaliado!");
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
    public ResultSet carregarEpisodios(TemporadaModel temporada, ProgramaModel programa) throws Exception {
        try{
            selectSql = "select distinct codEpisodio, nomeEpisodio, dataExibicao from programatv inner join temporadas " 
                    + "on programatv.codPrograma = temporadas.codPrograma inner join episodios " 
                    + "on episodios.codPrograma = temporadas.codPrograma " 
                    + "where episodios.codPrograma = " + programa.getCodPrograma() + " "
                    + "and episodios.codTemporada = " + temporada.getCodTemporada();
            
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
    
        
    public double getNotaGeral(ProgramaModel programa, TemporadaModel temporada){
        try{
            selectSql = "SELECT avg(convert(float, notaEpisodio)) FROM episodiosAssistidos WHERE codPrograma = " + programa.getCodPrograma() + 
                    " AND codTemporada = " + temporada.getCodTemporada();
            
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            ResultSet rs = stmt.executeQuery();
            double notaGeral = 0;
            while(rs.next()){
                notaGeral = rs.getDouble(1);
            }
            return notaGeral * 2;
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
        return 0;
    }
    
    public double getSuaNota(ProgramaModel programa, TemporadaModel temporada){
        try{
            selectSql = "SELECT avg(convert(float, notaEpisodio)) FROM episodiosAssistidos WHERE codPrograma = " + programa.getCodPrograma() + 
                    " AND codUsuario = " + usuario.getCodUsuario() + " AND codTemporada = " + temporada.getCodTemporada();
            
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            ResultSet rs = stmt.executeQuery();
            double suaNota = 0;
            while(rs.next()){
                suaNota = rs.getDouble(1);
            }
            return suaNota * 2;
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
        return 0;
    }
    

    public ResultSet carregarAvaliacaoEpisodios(TemporadaModel temporada, ProgramaModel programa) throws Exception {
        try{
            selectSql = "select * from episodiosAssistidos " 
                    + "where codPrograma = " + programa.getCodPrograma() + " "
                    + "and codTemporada = " + temporada.getCodTemporada()+ " "
                    + "and codUsuario = " + usuario.getCodUsuario();
            
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
    
    //UPDATE
    public void editarAvaliacaoEpisodio(ProgramaModel programa, TemporadaModel temporada, EpisodioModel episodio, Double notaEpisodio) {
        try {
            updateSql = "update episodiosAssistidos set notaEpisodio = ? "
                    + "where codPrograma = " + programa.getCodPrograma() + " "
                    + "and codTemporada = " + temporada.getCodTemporada()+ " "
                    + "and codEpisodio = " + episodio.getCodEpisodio()+ " "
                    + "and codUsuario = " + usuario.getCodUsuario();
            PreparedStatement stmt = connection.prepareStatement(updateSql);
            stmt.setDouble(1, notaEpisodio);
            System.out.println("Nota atualizada!");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }    
    
    //DELETE
    public void removerEpisodiosAssistidos (ProgramaModel programa) {
        try {           
            deleteSql = "delete from episodiosAssistidos where codPrograma = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteSql);
            stmt.setInt(1, programa.getCodPrograma());
            System.out.println("episodios assistidos removidos!");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }  
    public void removerEpisodios (ProgramaModel programa) {
        try {   
            removerEpisodiosAssistidos(programa);
            
            deleteSql = "delete from episodios where codPrograma = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteSql);
            stmt.setInt(1, programa.getCodPrograma());
            System.out.println("episodios assistidos removidos!");
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
