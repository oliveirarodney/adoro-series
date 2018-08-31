/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.UsuarioDAO;
import javafx.scene.control.Alert;
import model.Usuario;
import view.LoginView;
import view.NovoUsuarioView;

/**
 *
 * @author User
 */
public class UsuarioController {

    private final LoginView login;
    private final UsuarioDAO usuarioDAO;
    private Usuario usuario;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView login) {
        this.login = login;
        this.usuarioDAO = usuarioDAO;
    }

    public void cadastrarUsuario(String username, String email, String confirmarEmail, String password, String confirmarPassword, NovoUsuarioView novoUsuarioView) throws Exception {
        usuario = new Usuario();
        try {
            usuario.setUsername(username);

            if (email.equals(confirmarEmail)) {
                usuario.setEmail(email);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Cadastro não concluído");
                alert.setTitle("Erro");
                alert.setContentText("Emails diferem");
                alert.showAndWait();
            }

            if (password.equals(confirmarPassword)) {
                usuario.setSenha(password);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Cadastro não concluído");
                alert.setTitle("Erro");
                alert.setContentText("Senhas diferem");
                alert.showAndWait();
            }
            
            usuarioDAO.save(usuario);
            System.out.println("Cadastro realizado!");
            login.getMainStage().show();
            novoUsuarioView.getMainStage().close();
            
            
        } catch (Exception erro) {
            System.out.println("Falha no cadastro");
            System.out.println(erro.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Cadastro não concluído");
            alert.setTitle("Erro");
            alert.setContentText("Verifique se preencheu todos os campos obrigatórios.");
            alert.showAndWait();
        }
    }
}
