/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author User
 */
public class EpisodioModel {
    private int codEpisodio;
    private String nomeEpisodio;
    private String exibicaoEpisodio;
    private int nota;

    public int getCodEpisodio() {
        return codEpisodio;
    }

    public void setCodEpisodio(int codEpisodio) {
        this.codEpisodio = codEpisodio;
    }

    public String getNomeEpisodio() {
        return nomeEpisodio;
    }

    public void setNomeEpisodio(String nomeEpisodio) {
        this.nomeEpisodio = nomeEpisodio;
    }

    public String getExibicaoEpisodio() {
        return exibicaoEpisodio;
    }

    public void setExibicaoEpisodio(String exibicaoEpisodio) {
        this.exibicaoEpisodio = exibicaoEpisodio;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
