/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author User
 */
public class ProgramaModel {
    private int codPrograma;
    private String nomePrograma;
    private String tipoPrograma;
    private String nomeCategoria;
    private Image imagemPrograma; 
    private final List<TemporadaModel> temporadas = new ArrayList<>();
    
    public int getCodPrograma() {
        return codPrograma;
    }

    public void setCodPrograma(int codPrograma) {
        this.codPrograma = codPrograma;
    }

    public String getNomePrograma() {
        return nomePrograma;
    }

    public void setNomePrograma(String nomePrograma) {
        this.nomePrograma = nomePrograma;
    }

    public String getTipoPrograma() {
        return tipoPrograma;
    }

    public void setTipoPrograma(String tipoPrograma) {
        this.tipoPrograma = tipoPrograma;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Image getImagemPrograma() {
        return imagemPrograma;
    }

    public void setImagemPrograma(Image imagemPrograma) {
        this.imagemPrograma = imagemPrograma;
    }

    @Override
    public String toString() {
        return nomePrograma;
    }
    
    public List<TemporadaModel> getTemporadas() {
        return temporadas;
    }

    public void adicionarTemporadasLista(TemporadaModel temporadaModel) {
        temporadas.add(temporadaModel);
        System.out.println("Lista temporadas: "+temporadas);
        System.out.println("Temporada: "+temporadaModel.getNomeTemporada());       
    }
}
