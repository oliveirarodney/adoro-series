/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.ProgramaDAO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import model.CategoriaModel;
import model.ProgramaModel;
import model.TipoModel;
import view.CadastrarEpisodioView;

/**
 *
 * @author User
 */
public class ProgramaController {

    private final ProgramaDAO programaDAO;
    private final List<ProgramaModel> programas = new ArrayList<>();
    private ResultSet rs;

    public ProgramaController() {
        programaDAO = new ProgramaDAO();
    }

    public void CadastrarProgramas(String nome, File caminho, TipoModel tipo, CategoriaModel categoria) throws Exception {
        programaDAO.cadastrarPrograma(nome, caminho, tipo, categoria, this);
    }
    
    public void inserirProgramasComboBox(CadastrarEpisodioView cadastrarEpisodioView) throws Exception {
        rs = programaDAO.carregarTodosOsProgramas(this);

        while (rs.next()) {
            ProgramaModel programa = new ProgramaModel();

            programa.setCodPrograma(rs.getInt(1));
            programa.setNomeCategoria(rs.getString(2));
            programa.setTipoPrograma(rs.getString(3));
            programa.setNomePrograma(rs.getString(4));
            try (InputStream is = rs.getBinaryStream(5)) {
                BufferedImage bg = ImageIO.read(is);
                if (bg != null) {
                    Image image = SwingFXUtils.toFXImage(bg, null);
                    programa.setImagemPrograma(image);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            adicionarProgramaLista(programa);

        }

        for (int i = 0; i < programas.size(); i++) {
            cadastrarEpisodioView.getSelecionarPrograma().getItems().add(programas.get(i));
        }
    }

    public void listarProgramas(TipoModel tipo, CategoriaModel categoria, String nome) throws Exception {
        rs = programaDAO.carregarProgramas(tipo, categoria, nome, this);

        while (rs.next()) {
            ProgramaModel programa = new ProgramaModel();

            programa.setCodPrograma(rs.getInt(1));
            programa.setNomeCategoria(rs.getString(2));
            programa.setTipoPrograma(rs.getString(3));
            programa.setNomePrograma(rs.getString(4));
            try (InputStream is = rs.getBinaryStream(5)) {
                BufferedImage bg = ImageIO.read(is);
                if (bg != null) {
                    Image image = SwingFXUtils.toFXImage(bg, null);
                    programa.setImagemPrograma(image);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            adicionarProgramaLista(programa);
        }
    }

    public void listarFavoritos() throws SQLException {
        rs = programaDAO.listarFavoritos(this);
        while (rs.next()) {
            ProgramaModel programa = new ProgramaModel();

            programa.setCodPrograma(rs.getInt(1));
            programa.setNomeCategoria(rs.getString(2));
            programa.setTipoPrograma(rs.getString(3));
            programa.setNomePrograma(rs.getString(4));
            try (InputStream is = rs.getBinaryStream(5)) {
                BufferedImage bg = ImageIO.read(is);
                if (bg != null) {
                    Image image = SwingFXUtils.toFXImage(bg, null);
                    programa.setImagemPrograma(image);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            adicionarProgramaLista(programa);
        }
    }
    
    public double getNotaGeral(ProgramaModel programa){
        double notaGeral = programaDAO.getNotaGeral(programa);
        return notaGeral;
    }
    
    public double getSuaNota(ProgramaModel programa){
        double suaNota = programaDAO.getSuaNota(programa);
        return suaNota;
    }

    public boolean verificarFavoritos(ProgramaModel programa) {
        return programaDAO.verificarFavoritos(programa) == true;
    }
    
    public void removerPrograma(ProgramaModel programa) {
        programaDAO.removerPrograma(programa);
    }

    public void assinarPrograma(ProgramaModel programa) {
        programaDAO.assinarPrograma(programa);
    }

    public void removerAssinatura(ProgramaModel programa) {
        programaDAO.removerAssinatura(programa);
    }

    public List<ProgramaModel> getProgramas() {
        return programas;
    }

    public void adicionarProgramaLista(ProgramaModel programaModel) {
        programas.add(programaModel);
    }
}
