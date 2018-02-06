/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yansi.sCadastro.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author programador
 */
public class Conexao {

    private Connection conn;
    private Statement stm;

    private void conectar() {
        System.out.println("Conectando ao banco...");
        
        String linkConexao = "jdbc:mysql://172.20.0.2/usuarioDb";
        String usuario = "root";
        String senha   = "123456";
        String driverDB  = "com.mysql.jdbc.Driver";

        try {
            Class.forName(driverDB);
            if (conn == null) {
                conn = DriverManager.getConnection(linkConexao,usuario,senha);
            }

            System.out.println("Conexao realizada com sucesso");
        } catch (ClassNotFoundException e) {

            System.out.println("Classe não encontrada, adicione o driver nas bibliotecas");
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, e);

        } catch (SQLException e) {

            System.out.println(e);

        }

    }

    public Connection getConexao() {
        boolean status = false;

        try {
            conectar();
            System.out.println("Arquivo criado com sucesso");

            this.stm = this.conn.createStatement();
            

        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.conn;
    }

}
