/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yansi.sCadastro.controller;

import com.yansi.sCadastro.Dao.AutorizadorDAO;
import com.yansi.sCadastro.Modelo.ModToken;
import com.yansi.sCadastro.Modelo.ModUsuario;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yansi
 */
public class AutorizadorCtl {

    boolean salvaToken(ModUsuario modUse, String token) {
        boolean status;
        AutorizadorDAO autDao = new AutorizadorDAO();

        try {

            autDao.adiciona(modUse, token);
            status = true;

        } catch (SQLException ex) {
            Logger.getLogger(AutorizadorCtl.class.getName()).log(Level.SEVERE, null, ex);
            status = false;
        }

        return status;
    }

    boolean validaToken(String token) {
        boolean status =false;
        ModToken modToken;
        AutorizadorDAO autDao = new AutorizadorDAO();

        try {

            modToken = autDao.autorizacao(token);
            System.out.println("LINHA 46 " + modToken.getToken() +" "+"null".equals(modToken.getToken()) +" "+modToken.getToken() );
            if (!"null".equals(modToken.getToken())) {
                if (modToken.getToken().equals(token)) {
                    status = true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(AutorizadorCtl.class.getName()).log(Level.SEVERE, null, ex);
            status = false;
        }

        return status;
    }

    boolean deleteToken(String token) {
        boolean status;
        AutorizadorDAO autDao = new AutorizadorDAO();

        try {

            status = autDao.deleteToken(token);
           
        } catch (SQLException ex) {
            Logger.getLogger(AutorizadorCtl.class.getName()).log(Level.SEVERE, null, ex);
            status = false;
        }

        return status;
    }

}
