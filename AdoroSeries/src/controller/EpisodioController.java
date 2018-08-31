/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.EpisodioDAO;
import java.sql.ResultSet;
import model.EpisodioModel;
import model.ProgramaModel;
import model.TemporadaModel;
import view.TemporadaView;

/**
 *
 * @author User
 */
public class EpisodioController {

    private final EpisodioDAO episodioDAO;
    private EpisodioModel episodioModel;

    public EpisodioController() {
        episodioDAO = new EpisodioDAO();
    }

    public void cadastrarEpisodio(String nomeEpisodio, ProgramaModel programa, TemporadaModel episodio, String dataExibicao) throws Exception {
        episodioDAO.inserirEpisodio(nomeEpisodio, programa, episodio, dataExibicao);
    }

    public void carregarEpisodios(TemporadaModel temporadaModel, ProgramaModel programaModel, TemporadaView temporadaView) throws Exception {
        ResultSet rs = episodioDAO.carregarEpisodios(temporadaModel, programaModel);
        while (rs.next()) {
            episodioModel = new EpisodioModel();

            episodioModel.setCodEpisodio(rs.getInt(1));
            episodioModel.setNomeEpisodio(rs.getString(2));
            episodioModel.setExibicaoEpisodio(rs.getString(3));

            temporadaModel.adicionarEpisodioLista(episodioModel);
            System.out.println("Episodio carregado");
        }

        rs = episodioDAO.carregarAvaliacaoEpisodios(temporadaModel, programaModel);
        while (rs.next()) {
            for (EpisodioModel episodio : temporadaModel.getEpisodios()) {
                System.out.println("Cod: "+episodio.getCodEpisodio());
                System.out.println("ResultSet: "+rs.getInt(2));
                if(episodio.getCodEpisodio() == rs.getInt(2)){
                    System.out.println("Entrou aqui");
                    episodio.setNota(rs.getInt(5));
                    break;
                }
                System.out.println("Cod: " + episodio.getCodEpisodio() + " - Nome: " + episodio.getNomeEpisodio() + " - Nota: " + episodio.getNota() + "");
                
            }
        }
    }
    
        public double getNotaGeral(ProgramaModel programa, TemporadaModel temporada){
        double notaGeral = episodioDAO.getNotaGeral(programa, temporada);
        return notaGeral;
    }
    
    public double getSuaNota(ProgramaModel programa, TemporadaModel temporada){
        double suaNota = episodioDAO.getSuaNota(programa, temporada);
        return suaNota;
    }


    public void avaliarEpisodio(ProgramaModel programa, TemporadaModel temporada, EpisodioModel episodio, Double notaEpisodio) {
        System.out.println("Nota: " + notaEpisodio);
        System.out.println("Episódio: " + episodio.getCodEpisodio());
        episodioDAO.avaliarEpisodio(programa, temporada, episodio, notaEpisodio);
    }
    
    public void editarAvaliacaoEpisodio(ProgramaModel programa, TemporadaModel temporada, EpisodioModel episodio, Double notaEpisodio) {
        System.out.println("Nota: " + notaEpisodio);
        System.out.println("Episódio: " + episodio.getCodEpisodio());
        episodioDAO.editarAvaliacaoEpisodio(programa, temporada, episodio, notaEpisodio);
    }
}
