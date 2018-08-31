/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class TemporadaModel {
    private int codTemporada;
    private String nomeTemporada;   
    private String nomePrograma;

    private final List<EpisodioModel> episodios = new ArrayList<>();

    public int getCodTemporada() {
        return codTemporada;
    }

    public void setCodTemporada(int codTemporada) {
        this.codTemporada = codTemporada;
    }

    public String getNomeTemporada() {
        return nomeTemporada;
    }

    public void setNomeTemporada(String nomeTemporada) {
        this.nomeTemporada = nomeTemporada;
    }

    public String getNomePrograma() {
        return nomePrograma;
    }

    public void setNomePrograma(String nomePrograma) {
        this.nomePrograma = nomePrograma;
    }

    public List<EpisodioModel> getEpisodios() {
        return episodios;
    }

    public void adicionarEpisodioLista(EpisodioModel episodioModel) {
        episodios.add(episodioModel);
    }

    @Override
    public String toString() {
        return nomeTemporada;
    }

    
}
