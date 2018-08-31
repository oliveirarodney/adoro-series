/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import controller.AutenticacaoController;
import controller.ProgramaController;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import model.CategoriaModel;
import model.ProgramaModel;
import model.TipoModel;
import model.UsuarioSingleton;
import model.database.Database;
import model.database.DatabaseFactory;

/**
 *
 * @author User
 */
public class ProgramaDAO {
    private final Database database = DatabaseFactory.getDatabase("sqlserver");
    private final Connection connection = database.conectar();
    private String selectSql, insertSql, deleteSql;
    private final UsuarioSingleton usuario = AutenticacaoController.getInstanciaUsuario();
    
    //CREATE
    public void cadastrarPrograma(String nome, File imagem, TipoModel tipo, CategoriaModel categoria, ProgramaController programaController) throws Exception{
        try {
            insertSql = "insert into programatv (codTipoPrograma, codCategoria, nomePrograma, imagemPrograma) values (?, ?, ?, ?)";
            FileInputStream fis = new FileInputStream(imagem);
            int len = (int) imagem.length();
            PreparedStatement stmt = connection.prepareStatement(insertSql);
            stmt.setInt(1, tipo.getCodTipo());
            stmt.setInt(2, categoria.getCodCategoria());
            stmt.setString(3, nome);
            stmt.setBinaryStream(4, fis, len);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }
    public void assinarPrograma(ProgramaModel programa){
        try {
            insertSql = "insert into favoritos (codPrograma, codUsuario) values (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertSql);
            stmt.setInt(1, programa.getCodPrograma());
            stmt.setInt(2, usuario.getCodUsuario());
            System.out.println("Adicionado aos favoritos!");
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
    public ResultSet carregarProgramas(TipoModel tipo, CategoriaModel categoria, String nome, ProgramaController programaController) throws Exception {   
        try {
            selectSql = "SELECT programatv.codPrograma, categorias.nomeCategoria, tipoPrograma.nomeTipoPrograma, programatv.nomePrograma, programatv.imagemPrograma "
                    + "FROM programatv INNER JOIN tipoPrograma "
                    + "ON programatv.codTipoPrograma = tipoPrograma.codTipoPrograma INNER JOIN categorias "
                    + "ON categorias.codCategoria = programatv.codCategoria "
                    + "WHERE tipoPrograma.nomeTipoPrograma = '" + tipo.getNomeTipo()+ "' "
                    + "AND categorias.nomeCategoria = '" + categoria.getNomeCategoria() + "' "
                    + "AND programatv.nomePrograma LIKE '%" + nome + "%'";

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
    
    public ResultSet carregarTodosOsProgramas(ProgramaController programaController) throws Exception {   
        try {
            selectSql = "SELECT programatv.codPrograma, categorias.nomeCategoria, tipoPrograma.nomeTipoPrograma, programatv.nomePrograma, programatv.imagemPrograma "
                    + "FROM programatv INNER JOIN tipoPrograma "
                    + "ON programatv.codTipoPrograma = tipoPrograma.codTipoPrograma INNER JOIN categorias "
                    + "ON categorias.codCategoria = programatv.codCategoria "
                    + "ORDER BY programatv.nomePrograma ASC";

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
    
    public boolean verificarFavoritos(ProgramaModel programa) {
        try {
            selectSql = "SELECT codUsuario, codPrograma FROM favoritos "
                    + " where codUsuario = " + usuario.getCodUsuario()
                    + " and codPrograma = " + programa.getCodPrograma();

            PreparedStatement stmt = connection.prepareStatement(selectSql);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next() == true;
            
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
        return false;
    }
    
    public ResultSet listarFavoritos(ProgramaController programaController){
        try {
            selectSql = "SELECT programatv.codPrograma, categorias.nomeCategoria, tipoPrograma.nomeTipoPrograma, programatv.nomePrograma, programatv.imagemPrograma "
                    + "FROM programatv INNER JOIN tipoPrograma "
                    + "ON programatv.codTipoPrograma = tipoPrograma.codTipoPrograma INNER JOIN categorias "
                    + "ON categorias.codCategoria = programatv.codCategoria INNER JOIN favoritos "
                    + "ON programatv.codPrograma = favoritos.codPrograma "
                    + "WHERE favoritos.codUsuario = " + usuario.getCodUsuario();

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
    
    public double getNotaGeral(ProgramaModel programa){
        try{
            selectSql = "SELECT avg(convert(float, notaEpisodio)) FROM episodiosAssistidos WHERE" + programa.getCodPrograma();
            
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
    
    public double getSuaNota(ProgramaModel programa){
        try{
            selectSql = "SELECT avg(convert(float, notaEpisodio)) FROM episodiosAssistidos WHERE codPrograma = " + programa.getCodPrograma() + 
                    " AND codUsuario = " + usuario.getCodUsuario();
            
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
    
    //DELETE
    public void removerAssinatura(ProgramaModel programa){
        try {
            deleteSql = "delete from favoritos where codUsuario = ? and codPrograma = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteSql);
            stmt.setInt(1, usuario.getCodUsuario());
            stmt.setInt(2, programa.getCodPrograma());
            System.out.println("Removido dos favoritos!");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }
    public void removerTodasAsAssinaturas(ProgramaModel programa){
        try {
            deleteSql = "delete from favoritos where codPrograma = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteSql);
            stmt.setInt(1, programa.getCodPrograma());
            System.out.println("Assinaturas removidas!");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("ERRO DE BANCO DE DADOS");
            alert.setTitle("SQL SERVER");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }
    public void removerPrograma(ProgramaModel programa){
        try {                    
            removerTodasAsAssinaturas(programa);
            
            TemporadaDAO temporadaDAO = new TemporadaDAO();  
            temporadaDAO.removerTemporadas(programa);
            
            deleteSql = "delete from programatv where codPrograma = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteSql);
            stmt.setInt(1, programa.getCodPrograma());
            System.out.println("Programa removido!");
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