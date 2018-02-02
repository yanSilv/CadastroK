/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yansi.sCadastro.controller;

import com.yansi.sCadastro.dao.AutorizadorDAO;
import com.yansi.sCadastro.modelo.ModToken;
import com.yansi.sCadastro.modelo.ModUsuario;
import java.sql.SQLException;


/**
 *
 * @author yansi
 */
public class AutorizadorCtl {
    
    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AutorizadorCtl.class.getName());

    boolean salvaToken(ModUsuario modUse, String token) {
        boolean status;
        AutorizadorDAO autDao = new AutorizadorDAO();

        try {

            autDao.adiciona(modUse, token);
            status = true;

        } catch (SQLException ex) {
            logger.error(AutorizadorCtl.class.getName() + " "+ ex);
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
            logger.error(AutorizadorCtl.class.getName() + " "+ ex);
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
            logger.error(AutorizadorCtl.class.getName() + " "+ ex);
            status = false;
        }

        return status;
    }

}
