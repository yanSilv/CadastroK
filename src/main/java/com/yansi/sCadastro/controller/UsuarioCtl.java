/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yansi.sCadastro.controller;

import com.yansi.sCadastro.Dao.UsuarioDAO;
import com.yansi.sCadastro.Modelo.ModUsuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yansi
 */
public class UsuarioCtl {

    ModUsuario validaLogin(ModUsuario modUsuario) {
        ModUsuario modUse = new ModUsuario();
        UsuarioDAO daoUse = new UsuarioDAO();

        //Verifica se usuario não esta em branco
        System.out.println("Linha 25" + modUsuario.getUsuario());
        if (modUsuario.getUsuario().equals(null) || modUsuario.getUsuario().equals("")) {
            return modUse;
        }

        //Verifica se a senha não está em branco 
        if (modUsuario.getSenha().equals(null) || modUsuario.getSenha().equals("")) {
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

    String cadastroUsuario(ModUsuario modUsuario) {
        String status = "S";
        UsuarioDAO daoUse = new UsuarioDAO();

        //Verifica se usuario não esta em branco
        if (modUsuario.getUsuario().equals(null) || modUsuario.getUsuario().length() > 45) {
            return "V";
        }

        //Verifica se a senha não está em branco 
        if (modUsuario.getSenha().equals(null)) {
            return "V";
        }

        if (modUsuario.getNome().equals(null) || modUsuario.getNome().length() > 200) {
            return "V";
        }

        if (modUsuario.getEndereco().length() > 200 || modUsuario.getTelefone().length() > 20) {
            return "V";
        }

        try {
            if (daoUse.validaUsuario(modUsuario)) {
                status = "C";
            } else {
                daoUse.adiciona(modUsuario);
                status = "A";
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioCtl.class.getName()).log(Level.SEVERE, null, ex);
            status = "S";
        }

        return status;
    }

    ArrayList<ModUsuario> exibicaoTotal() {
        ArrayList<ModUsuario> modUser = new ArrayList<ModUsuario>();
        UsuarioDAO userDao = new UsuarioDAO();

        try {
            modUser = userDao.lista();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioCtl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro ao retornar a lista de usuario");
        }

        return modUser;
    }

    String editarUsuario(ModUsuario modUsuario, String cargo) {
        String status = null;
        UsuarioDAO daoUse = new UsuarioDAO();
        try {

            daoUse.editar(modUsuario, cargo);
            status = "A";

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioCtl.class.getName()).log(Level.SEVERE, null, ex);
            status = "S";
        }
        return status;
    }
}
