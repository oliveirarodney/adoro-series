/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.TemporadaDAO;
import java.sql.ResultSet;
import model.ProgramaModel;
import model.TemporadaModel;
import view.CadastrarEpisodioView;

/**
 *
 * @author User
 */
public class TemporadaController {

    private final TemporadaDAO temporadaDAO;
    private ResultSet rs;

    public TemporadaController() {
        temporadaDAO = new TemporadaDAO();
    }

    public void adicionarTemporada(CadastrarEpisodioView cadastrarEpisodioView, ProgramaModel programa) throws Exception {
        temporadaDAO.adicionarTemporada(programa);
        carregarTemporadas(cadastrarEpisodioView, programa);
    }
    
    public void carregarTemporadas(ProgramaModel programa) throws Exception {
        rs = temporadaDAO.carregarTemporadas(programa);
        while (rs.next()) {
            TemporadaModel temporada = new TemporadaModel();

            temporada.setCodTemporada(rs.getInt(1));
            temporada.setNomePrograma(rs.getString(2));
            temporada.setNomeTemporada(rs.getString(3));

            programa.adicionarTemporadasLista(temporada);
        }
    }

    public void carregarTemporadas(CadastrarEpisodioView cadastrarEpisodioView, ProgramaModel programa) throws Exception {
        programa.getTemporadas().clear();
        rs = temporadaDAO.carregarTemporadas(programa);
        while (rs.next()) {
            TemporadaModel temporada = new TemporadaModel();

            temporada.setCodTemporada(rs.getInt(1));
            temporada.setNomePrograma(rs.getString(2));
            temporada.setNomeTemporada(rs.getString(3));

            programa.adicionarTemporadasLista(temporada);
        }
  
        System.out.println("Limpou");
        for (int i = 0; i < programa.getTemporadas().size(); i++) {
            System.out.println("Adicionou temporada " +1);
            cadastrarEpisodioView.getSelecionarTemporada().getItems().add(programa.getTemporadas().get(i));
        }
        cadastrarEpisodioView.getSelecionarTemporada().getSelectionModel().selectFirst();
    }
}
