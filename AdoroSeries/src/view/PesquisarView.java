/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.AutenticacaoController;
import controller.ProgramaController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.CategoriaModel;
import model.ProgramaModel;
import model.TipoModel;
import model.UsuarioSingleton;
import view.components.BotaoMenu;
import view.components.BotaoRemover;
import view.modulos.ModuloCentral;

/**
 *
 * @author User
 */
public final class PesquisarView {

    private final ModuloCentral central;
    private final ProgramaController programaController;
    private ProgramaTVView programaTVView;
    private final UsuarioSingleton usuario = AutenticacaoController.getInstanciaUsuario();

    private static final String label = ""
            + "-fx-font-family: 'Century Gothic'; "
            + "-fx-font-size: 12pt;"
            + "-fx-padding: 5px;";

    private VBox layout, pesquisa, programasTV, programa;
    private HBox hbox, barraPesquisa;
    private ImageView imagemPrograma;
    private Label nomePrograma;
    private ScrollPane scroll;
    private JFXTextField pesquisarTitulo;
    private JFXComboBox<TipoModel> selecionarTipo;
    private JFXComboBox<CategoriaModel> selecionarCategoria;
    private BotaoMenu botaoPesquisar;
    private final List<VBox> programas;
    private BotaoRemover botaoRemover;

    private final int qtdeSeriesRow = 3;

    public PesquisarView(ModuloCentral central, ProgramaController programaController) throws Exception {
        IniciarLayout();
        programas = new ArrayList<>();

        this.programaController = programaController;
        this.central = central;
    }

    public VBox IniciarLayout() {
        layout = new VBox();
        layout.setPadding(new Insets(50));
        layout.setStyle("-fx-background-color: white;");
        layout.alignmentProperty().set(Pos.CENTER);

        pesquisa = new VBox();
        barraPesquisa = new HBox();
        pesquisa.getChildren().add(barraPesquisa);

        selecionarCategoria = new JFXComboBox<>();
        selecionarCategoria.setStyle(label);

        selecionarTipo = new JFXComboBox<>();
        selecionarTipo.setStyle(label);

        pesquisarTitulo = new JFXTextField();
        pesquisarTitulo.setPromptText("Digite o nome do programa");
        pesquisarTitulo.setStyle(label);
        pesquisarTitulo.setPrefWidth(250);

        botaoPesquisar = new BotaoMenu("Pesquisar");
        botaoPesquisar.setOnAction(event -> {
            try {
                programaController.getProgramas().clear();
                programaController.listarProgramas(selecionarTipo.getSelectionModel().getSelectedItem(), selecionarCategoria.getSelectionModel().getSelectedItem(), pesquisarTitulo.getText());
                criarListaProgramas();
            } catch (Exception ex) {
                Logger.getLogger(PesquisarView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        barraPesquisa.getChildren().addAll(selecionarTipo, selecionarCategoria, pesquisarTitulo, botaoPesquisar);
        barraPesquisa.setSpacing(20);
        barraPesquisa.prefWidthProperty().bind(layout.prefWidthProperty());
        barraPesquisa.setAlignment(Pos.BASELINE_RIGHT);

        scroll = new ScrollPane();
        programasTV = new VBox();
        scroll.setContent(programasTV);
        scroll.setStyle("-fx-background: white; -fx-border-color: white; -fx-focus-color: white; -fx-faint-focus-color: white;");
        scroll.prefWidthProperty().bind(layout.widthProperty());
        scroll.prefHeightProperty().bind(layout.heightProperty());

        layout.getChildren().addAll(pesquisa, scroll);
        layout.setSpacing(30);
        return layout;
    }

    public void criarListaProgramas() {
        programas.clear();
        programasTV.getChildren().clear();
        for (int i = 0; i < programaController.getProgramas().size(); i++) {
            int indice = i;
            if (indice % qtdeSeriesRow == 0) {
                hbox = new HBox();
                hbox.setSpacing(20);
                programasTV.getChildren().add(hbox);
                hbox.prefWidthProperty().bind(scroll.widthProperty().subtract(30));
            }
            programa = new VBox();
            nomePrograma = new Label(programaController.getProgramas().get(indice).getNomePrograma());
            nomePrograma.setStyle(label);

            imagemPrograma = new ImageView(programaController.getProgramas().get(indice).getImagemPrograma());
            imagemPrograma.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                final Object source = event.getSource();
                final ProgramaModel programaModel = programaController.getProgramas().get(indice);
                for (VBox programaVBox : getProgramas()) {
                    if (source.equals(programaVBox.getChildren().get(0))) {
                        try {
                            programaTVView = new ProgramaTVView(central, programaController, programaModel);
                            central.mudarTelas(programaTVView.getLayout());
                        } catch (Exception ex) {
                            Logger.getLogger(PesquisarView.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        break;
                    }
                }
                event.consume();
            });
            imagemPrograma.setFitHeight(250);
            imagemPrograma.setFitWidth(200);

            if (usuario.getTipoUsuario() == 1) {
                botaoRemover = new BotaoRemover();
                botaoRemover.setPrefWidth(imagemPrograma.getFitWidth());
                botaoRemover.setOnAction(event -> {
                    final Object source = event.getSource();
                    final ProgramaModel programaModel = programaController.getProgramas().get(indice);
                    for (VBox programaVBox : getProgramas()) {
                        if (source.equals(programaVBox.getChildren().get(2))) {
                            try {
                                Alert alert = new Alert(Alert.AlertType.WARNING,
                                        "Remover série: "+ programaModel.getNomePrograma() +"\n\nVocê tem certeza disso?",
                                        ButtonType.YES, ButtonType.NO);

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.YES) {
                                    programaController.removerPrograma(programaModel);
                                    System.out.println("REMOVIDO");
                                } else {
                                    System.out.println("NÃO REMOVIDO");
                                }
                                
                            } catch (Exception ex) {
                                Logger.getLogger(PesquisarView.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            break;
                        }
                    }
                });
                programa.getChildren().addAll(imagemPrograma, nomePrograma, botaoRemover);
            } else {
                programa.getChildren().addAll(imagemPrograma, nomePrograma);
            }
            programa.setAlignment(Pos.TOP_CENTER);
            adicionarProgramas(programa);

            programasTV.setSpacing(30);
            hbox.getChildren().add(programas.get(i));
            programas.get(i).prefWidthProperty().bind(scroll.widthProperty().divide(qtdeSeriesRow));
        }
    }

    public VBox getLayout() {
        return layout;
    }

    public List<VBox> getProgramas() {
        return programas;
    }

    public void adicionarProgramas(VBox programa) {
        programas.add(programa);
    }

    public ImageView getImagemPrograma() {
        return imagemPrograma;
    }

    public JFXTextField getPesquisarTitulo() {
        return pesquisarTitulo;
    }

    public JFXComboBox getSelecionarTipo() {
        return selecionarTipo;
    }

    public JFXComboBox getSelecionarCategoria() {
        return selecionarCategoria;
    }

    public BotaoMenu getBotaoPesquisar() {
        return botaoPesquisar;
    }
}
