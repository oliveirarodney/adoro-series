/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author user
 */
public class UsuarioSingleton extends Usuario{
    
    private static UsuarioSingleton instancia;
    public static UsuarioSingleton getInstancia() {
        if (instancia == null)
            instancia = new UsuarioSingleton();
        return instancia;
    }
}
