/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yansi.sCadastro.controller;

import com.yansi.sCadastro.dao.UsuarioDAO;
import com.yansi.sCadastro.modelo.ModUsuario;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author yansi
 */
public class UsuarioCtl {

    final static Logger logger = Logger.getLogger(UsuarioCtl.class.getName());

    ModUsuario validaLogin(ModUsuario modUsuario) {
        ModUsuario modUse = new ModUsuario();
        UsuarioDAO daoUse = new UsuarioDAO();

        //Verifica se usuario não esta em branco
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
            logger.error(UsuarioCtl.class.getName() + " " + ex);
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

        if (modUsuario.getNome().length() > 200) {
            modUsuario.setNome(modUsuario.getNome().substring(0, 199));
        }
        if (modUsuario.getEndereco().length() > 200) {
            modUsuario.setEndereco(modUsuario.getEndereco().substring(0, 199));
        }
        if (modUsuario.getTelefone().length() > 20) {
            modUsuario.setTelefone(modUsuario.getTelefone().substring(0, 19));
        }
        if (modUsuario.getUsuario().length() > 45) {
            modUsuario.setUsuario(modUsuario.getUsuario().substring(0, 44));
        }
        if (modUsuario.getSenha().length() > 60) {
            modUsuario.setSenha(modUsuario.getSenha().substring(0, 59));
        }
        if (modUsuario.getStatus().length() > 45) {
            modUsuario.setStatus(modUsuario.getStatus().substring(0, 44));
        }

        try {
            if (daoUse.validaUsuario(modUsuario)) {
                status = "C";
            } else {
                daoUse.adiciona(modUsuario);
                status = "A";
            }

        } catch (SQLException ex) {
            logger.error(UsuarioCtl.class.getName() + " " + ex);
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
            System.out.println("Erro ao retornar a lista de usuario");
            logger.error(UsuarioCtl.class.getName() + " " + ex);
        }

        return modUser;
    }

    String editarUsuario(ModUsuario modUsuario, String cargo) {
        String status = null;
        UsuarioDAO daoUse = new UsuarioDAO();

        if (modUsuario.getNome().length() > 200) {
            modUsuario.setNome(modUsuario.getNome().substring(0, 199));
        }
        if (modUsuario.getEndereco().length() > 200) {
            modUsuario.setEndereco(modUsuario.getEndereco().substring(0, 199));
        }
        if (modUsuario.getTelefone().length() > 20) {
            modUsuario.setTelefone(modUsuario.getTelefone().substring(0, 19));
        }
        if (modUsuario.getUsuario().length() > 45) {
            modUsuario.setUsuario(modUsuario.getUsuario().substring(0, 44));
        }
        if (modUsuario.getSenha().length() > 60) {
            modUsuario.setSenha(modUsuario.getSenha().substring(0, 59));
        }
        if (modUsuario.getStatus().length() > 45) {
            modUsuario.setStatus(modUsuario.getStatus().substring(0, 44));
        }

        try {

            daoUse.editar(modUsuario, cargo);
            status = "A";

        } catch (SQLException ex) {
            logger.error(UsuarioCtl.class.getName() + " " + ex);
            status = "S";
        }
        return status;
    }
}
