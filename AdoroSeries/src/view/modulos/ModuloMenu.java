/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.modulos;

import DAO.CategoriaDAO;
import DAO.TipoDAO;
import controller.AutenticacaoController;
import controller.CategoriasController;
import controller.ProgramaController;
import controller.TemporadaController;
import controller.TipoController;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.UsuarioSingleton;
import view.components.BotaoMenu;
import org.controlsfx.glyphfont.FontAwesome;
import view.CadastrarEpisodioView;
import view.CadastrarProgramaView;
import view.FavoritosView;
import view.PesquisarView;
import view.TemporadaView;

/**
 *
 * @author User
 */
public final class ModuloMenu {
    /*************** ESTILO DOS COMPONENTES ****************/    
    private static final String backgroundMenu = "-fx-background-color: #A8CF45;";
    
    private VBox vbox, menu, bottom;
    private BotaoMenu pesquisarBotao, cadastrarProgramaBotao, cadastrarTemporadaBotao, cadastrarEpisodioBotao, favoritosBotao, sair;
    private List<BotaoMenu> menuBotoes;
    
    private final ModuloCentral central;
    private final PainelPrincipal painelPrincipal;
    
    private ProgramaController programaController;
    private TemporadaController temporadaController;
    private TipoController tipoController;
    private CategoriasController categoriasController;
    
    private PesquisarView pesquisar;
    private CadastrarProgramaView cadastrarProgramaView;
    private CadastrarEpisodioView cadastrarEpisodioView;
    private TemporadaView temporadaView;
    private FavoritosView favoritosView;
    
    private TipoDAO tipoDAO;   
    private CategoriaDAO categoriaDAO;
    
    private final UsuarioSingleton usuario;
       
    public ModuloMenu(ModuloCentral central, PainelPrincipal painelPrincipal){
        this.central = central;
        this.painelPrincipal = painelPrincipal;
        usuario = AutenticacaoController.getInstanciaUsuario();
    }
    
    public VBox borderPaneMenu(){
        vbox = new VBox();
        menu = new VBox();
        vbox.setStyle(backgroundMenu);
        
        criarBotoes(central);
        for(int i = 0; i < menuBotoes.size(); i++){
            menu.getChildren().add(menuBotoes.get(i));
        }
      
        sair = new BotaoMenu(FontAwesome.Glyph.SIGN_OUT.name(), "Sair");
        sair.setOnAction((ActionEvent event) -> {
            painelPrincipal.getMainStage().close();
        });
        sair.setAlignment(Pos.BASELINE_LEFT);
        bottom = new VBox(sair);
        bottom.setAlignment(Pos.BOTTOM_RIGHT);

        VBox.setVgrow(bottom, Priority.ALWAYS);

        vbox.getChildren().addAll(menu, bottom);
        
        return vbox;
    }
    public void criarBotoes(ModuloCentral central){
        menuBotoes = new ArrayList();
        Pesquisar(central);
        Favoritos(central);
        System.out.println(usuario.getTipoUsuario());
        if(usuario.getTipoUsuario() == 1){
            CadastrarPrograma(central);
            CadastrarEpisodio(central);
        }     
    }
     
    public void Pesquisar(ModuloCentral central) {
        pesquisarBotao = new BotaoMenu(FontAwesome.Glyph.SEARCH.name(), "Pesquisar");
        pesquisarBotao.setOnAction((ActionEvent event) -> {
            try {
                programaController = new ProgramaController();
                pesquisar = new PesquisarView(central, programaController);
                
                tipoDAO = new TipoDAO();
                tipoController = new TipoController(pesquisar, tipoDAO);
                
                categoriaDAO = new CategoriaDAO();
                categoriasController = new CategoriasController(pesquisar, categoriaDAO);
            } catch (Exception ex) {
                Logger.getLogger(ModuloMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            central.mudarTelas(pesquisar.getLayout());
        });
        pesquisarBotao.setAlignment(Pos.BASELINE_LEFT);
        adicionarBotaoMenu(pesquisarBotao);
    }
    
    public void Favoritos(ModuloCentral central){
        favoritosBotao = new BotaoMenu(FontAwesome.Glyph.STAR.name(), "Favoritos");
        favoritosBotao.setOnAction((ActionEvent event) -> {
            try {
                programaController = new ProgramaController();
                favoritosView = new FavoritosView(central, programaController);
                central.mudarTelas(favoritosView.getLayout());
            } catch (Exception ex) {
                Logger.getLogger(ModuloMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        favoritosBotao.setAlignment(Pos.BASELINE_LEFT);
        adicionarBotaoMenu(favoritosBotao);
    }
    
    public void CadastrarPrograma(ModuloCentral central){
        cadastrarProgramaBotao = new BotaoMenu(FontAwesome.Glyph.PENCIL.name(), "Cadastrar Programa");
        cadastrarProgramaBotao.setOnAction((ActionEvent event) -> {
            try {
                programaController = new ProgramaController();
                categoriaDAO = new CategoriaDAO();
                categoriasController = new CategoriasController(categoriaDAO);
                cadastrarProgramaView = new CadastrarProgramaView(central, programaController, categoriasController);  
                
                tipoDAO = new TipoDAO();
                tipoController = new TipoController(cadastrarProgramaView, tipoDAO);
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ModuloMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ModuloMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            central.mudarTelas(cadastrarProgramaView.getLayout());
        });
        cadastrarProgramaBotao.setAlignment(Pos.BASELINE_LEFT);
        adicionarBotaoMenu(cadastrarProgramaBotao);
    }
    
    public void CadastrarEpisodio(ModuloCentral central){
        cadastrarEpisodioBotao = new BotaoMenu(FontAwesome.Glyph.PENCIL.name(), "Cadastrar Episódio");
        cadastrarEpisodioBotao.setOnAction((ActionEvent event) -> {
            try {
                programaController = new ProgramaController();
                temporadaController = new TemporadaController();
                cadastrarEpisodioView = new CadastrarEpisodioView(central, programaController, temporadaController);                 
            } catch (Exception ex) {
                Logger.getLogger(ModuloMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            central.mudarTelas(cadastrarEpisodioView.getLayout());
        });
        cadastrarEpisodioBotao.setAlignment(Pos.BASELINE_LEFT);
        adicionarBotaoMenu(cadastrarEpisodioBotao);
    }
    
    public void adicionarBotaoMenu(BotaoMenu botao){
        menuBotoes.add(botao);
    }

    public List<BotaoMenu> getMenuBotoes() {
        return menuBotoes;
    }
}
