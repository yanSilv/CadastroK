/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yansi.sCadastro.Dao;

import com.yansi.sCadastro.Modelo.ModToken;
import com.yansi.sCadastro.Modelo.ModUsuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author yansi
 */
public class AutorizadorDAO {
    
    public void adiciona(ModUsuario modUsuario, String token) throws SQLException {
        Conexao conn = new Conexao();
        PreparedStatement ps;
        
        String sql = "insert into tb_token(id_user, token) "
                + "values (?,?)";
        ps =  conn.getConexao().prepareStatement(sql);
        
        ps.setInt(1, modUsuario.getId());
        ps.setString(2, token);
        
        ps.execute();
    }

    public ModToken autorizacao(String token) throws SQLException {
        
        ModToken modUse = new ModToken();
        
        Conexao conn = new Conexao();
        
        String sql = "SELECT * FROM tb_token "
                + "WHERE token = ? ;";
        
        PreparedStatement ps;
        
        ps = conn.getConexao().prepareStatement(sql);
        ps.setString(1, token);
        
        ResultSet rs;
        
        rs = ps.executeQuery();
        
        if(rs.next()) {
            modUse.setUsuario(rs.getString("id_user"));
            modUse.setToken(rs.getString("token"));
        } else {
            modUse.setUsuario("null");
            modUse.setToken("null");
        }
        
        return modUse;
    }

    public boolean deleteToken(String token) throws SQLException {
        
        Conexao conn = new Conexao();
        
        String sql = "DELETE FROM tb_token "
                + "WHERE token = ? ;";
        System.out.println("LINHA 69 "+token);
        PreparedStatement ps;
        
        ps = conn.getConexao().prepareStatement(sql);
        ps.setString(1, token);
        
        ps.executeUpdate();
        
        return true;
    }
    
}
