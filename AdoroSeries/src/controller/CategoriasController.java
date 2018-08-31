/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.CategoriaDAO;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CategoriaModel;
import view.CadastrarProgramaView;
import view.PesquisarView;

/**
 *
 * @author User
 */
public final class CategoriasController {

    private PesquisarView pesquisarView;
    private CadastrarProgramaView cadastrarView;
    private CategoriaDAO categoriaDAO;
    private CategoriaModel categoriaModel;
    private List<CategoriaModel> categorias;
    private ResultSet rs;

    public CategoriasController(PesquisarView pesquisarView, CategoriaDAO categoriaDAO) throws Exception {
        System.out.println("recebendo os parâmetros da view pesquisar e do DAO");
        this.pesquisarView = pesquisarView;
        this.categoriaDAO = categoriaDAO;
        inserirCategoriaComboBoxPesquisar();
    }

    public CategoriasController(CategoriaDAO categoriaDAO) throws Exception {
        System.out.println("recebendo os parâmetros da view cadastrar e do DAO");
        this.categoriaDAO = categoriaDAO;
    }

    public void inserirCategoriaComboBoxPesquisar() throws Exception {
        categorias = new ArrayList<>();

        rs = categoriaDAO.carregarCategoriasPrograma(this);
        while (rs.next()) {
            CategoriaModel categoria = new CategoriaModel();
            categoria.setCodCategoria(rs.getInt(1));
            categoria.setNomeCategoria(rs.getString(2));

            adicionarCategoria(categoria);
        }
        System.out.println(pesquisarView.getSelecionarCategoria());
        for (int i = 0; i < categorias.size(); i++) {
            pesquisarView.getSelecionarCategoria().getItems().add(categorias.get(i));
        }
        pesquisarView.getSelecionarCategoria().getSelectionModel().selectFirst();

    }

    public void inserirCategoriaComboBoxCadastrar(CadastrarProgramaView cadastrarView) throws Exception {
        categorias = new ArrayList<>();

        rs = categoriaDAO.carregarCategoriasPrograma(this);
        while (rs.next()) {
            categoriaModel = new CategoriaModel();
            categoriaModel.setCodCategoria(rs.getInt(1));
            categoriaModel.setNomeCategoria(rs.getString(2));

            adicionarCategoria(categoriaModel);
        }
        System.out.println(cadastrarView.getSelecionarCategoria());
        for (int i = 0; i < categorias.size(); i++) {
            cadastrarView.getSelecionarCategoria().getItems().add(categorias.get(i));
        }
        cadastrarView.getSelecionarCategoria().getSelectionModel().selectFirst();
    }

    public void criarCategoria(String categoria, CadastrarProgramaView cadastrarView) throws Exception {
        categoriaDAO = new CategoriaDAO();
        categoriaDAO.inserirCategoriaPrograma(this, categoria);
    }

    public List<CategoriaModel> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaModel> categorias) {
        this.categorias = categorias;
    }

    public void adicionarCategoria(CategoriaModel tipo) {
        categorias.add(tipo);
    }

    public CategoriaModel recuperarCategoria(int indice) {
        return categorias.get(indice);
    }
}
