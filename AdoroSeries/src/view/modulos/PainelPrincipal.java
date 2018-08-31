/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.modulos;

import DAO.CategoriaDAO;
import DAO.TipoDAO;
import controller.CategoriasController;
import controller.ProgramaController;
import controller.TipoController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.NovoUsuarioView;
import view.PesquisarView;
import view.ProgramaTVView;
import view.TemporadaView;

/**
 *
 * @author User
 */

public final class PainelPrincipal {
    /*************** TAMANHO DEFAULT DA TELA ****************/
    private static final int width = 1200;
    private static final int height = 800;
    
    /*************** CRIAÇÃO DOS MÓDULOS DA TELA ****************/
    private static BorderPane painelPrincipal;
    private static ModuloTopo topo;
    private static ModuloMenu menu;
    private static ModuloCentral central;
    
    private ProgramaController programaController;
    private PesquisarView pesquisar; 
    
    private TipoController tipoController;
    private TipoDAO tipoDAO;
    
    private CategoriasController categoriasController;
    private CategoriaDAO categoriaDAO;
    
    private static Scene mainScene;
    private static Stage mainStage;
    
    public PainelPrincipal() throws Exception{
        setBorderPane();
        mainScene = new Scene(painelPrincipal, width, height);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
    }
    
    public void setBorderPane() throws Exception{
        painelPrincipal = new BorderPane();
        
        /**SETANDO O MÓDULO DO TOPO COM O LOGOTIPO**/
        topo = new ModuloTopo();
        painelPrincipal.setTop(topo.getTopo());
        
        /**SETANDO A TELA DEFAULT DO MÓDULO CENTRAL**/
        central = new ModuloCentral();
        
        programaController = new ProgramaController();
        pesquisar = new PesquisarView(central, programaController);

        tipoDAO = new TipoDAO();
        tipoController = new TipoController(pesquisar, tipoDAO);
                
        categoriaDAO = new CategoriaDAO();
        categoriasController = new CategoriasController(pesquisar, categoriaDAO);
        
        painelPrincipal.setCenter(central.borderPaneCentral(pesquisar.getLayout()));
        
        /**SETANDO O MENU NO PAINEL ESQUERDO**/
        menu = new ModuloMenu(central, this);
        painelPrincipal.setLeft(menu.borderPaneMenu());
        
    }
    
    public static ModuloCentral getCentral() {
        return central;
    }

    public static void setCentral(ModuloCentral central) {
        PainelPrincipal.central = central;
    }
    
    public Stage getMainStage(){
        return mainStage;
    }
    
}
