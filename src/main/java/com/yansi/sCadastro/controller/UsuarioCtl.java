/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yansi.sCadastro.controller;

import com.yansi.sCadastro.Dao.UsuarioDAO;
import com.yansi.sCadastro.Modelo.ModUsuario;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yansi
 */
public class UsuarioCtl {

    ModUsuario validaLogin(ModUsuario modUsuario) {
        ModUsuario modUse = new ModUsuario(-1);
        UsuarioDAO daoUse = new UsuarioDAO();

        //Verifica se usuario não esta em branco
        if (modUsuario.getUsuario().equals(null)) {
            return modUse;
        }

        //Verifica se a senha não está em branco 
        if (modUsuario.getSenha().equals(null)) {
            return modUse;
        }

        try {
            modUse = daoUse.login(modUsuario);
        } catch (SQLException ex) {
            System.out.println("Falha ao validaLogin");
            Logger.getLogger(UsuarioCtl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modUse;

    }

    boolean cadastroUsuario(ModUsuario modUsuario) {
        boolean status = false;
        UsuarioDAO daoUse = new UsuarioDAO();
        
        //Verifica se usuario não esta em branco
        if (modUsuario.getUsuario().equals(null) || modUsuario.getUsuario().length() > 45) {
            return status;
        }

        //Verifica se a senha não está em branco 
        if (modUsuario.getSenha().equals(null)) {
            return status;
        }

        if (modUsuario.getNome().equals(null) || modUsuario.getNome().length() > 200) {
            return status;
        }
        
        if ( modUsuario.getEndereco().length() > 200 || modUsuario.getTelefone().length() > 20) {
            return status;
        }
        
        try {
            
            daoUse.adiciona(modUsuario);
            status = true;
                    
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioCtl.class.getName()).log(Level.SEVERE, null, ex);
            status = false;
        }

        return status;
    }
}
