/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.TipoDAO;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.TipoModel;
import view.CadastrarProgramaView;
import view.PesquisarView;

/**
 *
 * @author User
 */
public final class TipoController {

    private PesquisarView pesquisarView;
    private CadastrarProgramaView cadastrarView;
    private List<TipoModel> tipos;
    private final TipoDAO tipoDAO;
    private ResultSet rs;

    public TipoController(PesquisarView pesquisarView, TipoDAO tipoDAO) throws Exception {
        this.tipoDAO = tipoDAO;
        this.pesquisarView = pesquisarView;
        inserirTipoComboBoxPesquisar();
    }

    public TipoController(CadastrarProgramaView cadastrarView, TipoDAO tipoDAO) throws Exception {
        this.tipoDAO = tipoDAO;
        this.cadastrarView = cadastrarView;
        inserirTipoComboBoxCadastrar();
    }

    public void inserirTipoComboBoxPesquisar() throws Exception {
        tipos = new ArrayList<>();

        rs = tipoDAO.carregarTipoPrograma(this);
        while (rs.next()) {
            TipoModel tipo = new TipoModel();
            tipo.setCodTipo(rs.getInt(1));
            tipo.setNomeTipo(rs.getString(2));

            adicionarTipo(tipo);
        }
        
        for (int i = 0; i < tipos.size(); i++) {
            pesquisarView.getSelecionarTipo().getItems().add(tipos.get(i));
        }
        pesquisarView.getSelecionarTipo().getSelectionModel().selectFirst();
    }

    public void inserirTipoComboBoxCadastrar() throws Exception {
        tipos = new ArrayList<>();

        rs = tipoDAO.carregarTipoPrograma(this);
        while (rs.next()) {
            TipoModel tipo = new TipoModel();
            tipo.setCodTipo(rs.getInt(1));
            tipo.setNomeTipo(rs.getString(2));

            adicionarTipo(tipo);
        }
        
        for (int i = 0; i < tipos.size(); i++) {
            cadastrarView.getSelecionarTipo().getItems().add(tipos.get(i));
        }
        cadastrarView.getSelecionarTipo().getSelectionModel().selectFirst();
    }

    public List<TipoModel> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoModel> tipos) {
        this.tipos = tipos;
    }

    public void adicionarTipo(TipoModel tipo) {
        tipos.add(tipo);
    }

    public TipoModel recuperarTipo(int indice) {
        return tipos.get(indice);
    }
}
