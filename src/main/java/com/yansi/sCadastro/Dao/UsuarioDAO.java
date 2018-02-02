/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yansi.sCadastro.Dao;

import com.yansi.sCadastro.Modelo.ModUsuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author programador
 */
public class UsuarioDAO {

    public ModUsuario login(ModUsuario modUsuario) throws SQLException {

        ModUsuario modUse = new ModUsuario();

        Conexao conn = new Conexao();

        String sql = "SELECT * FROM tb_usuario "
                + "WHERE usuario = ? "
                + "AND senha = ?;";

        PreparedStatement ps;

        ps = conn.getConexao().prepareStatement(sql);
        ps.setString(1, modUsuario.getUsuario());
        ps.setString(2, modUsuario.getSenha());

        ResultSet rs;

        rs = ps.executeQuery();

        if (rs.next()) {
            modUse.setId(rs.getInt("id_user"));
            modUse.setNome(rs.getString("nome"));
            modUse.setEndereco(rs.getString("endereco"));
            modUse.setTelefone(rs.getString("telefone"));
            modUse.setIdade(rs.getInt("idade"));
            modUse.setUsuario(rs.getString("usuario"));
            modUse.setSenha(rs.getString("senha"));
            modUse.setStatus(rs.getString("status"));

        }

        return modUse;
    }

    public void adiciona(ModUsuario modUsuario) throws SQLException {
        Conexao conn = new Conexao();
        PreparedStatement ps;

        String sql = "insert into tb_usuario(nome, endereco, telefone, idade, usuario, senha, status) "
                + "values (?,?,?,?,?,?,?)";
        ps = conn.getConexao().prepareStatement(sql);

        ps.setString(1, modUsuario.getNome());
        ps.setString(2, modUsuario.getEndereco());
        ps.setString(3, modUsuario.getTelefone());
        ps.setInt(4, modUsuario.getIdade());
        ps.setString(5, modUsuario.getUsuario());
        ps.setString(6, modUsuario.getSenha());
        ps.setString(7, modUsuario.getStatus());

        ps.execute();
    }

    public ArrayList<ModUsuario> lista() throws SQLException {
        ArrayList<ModUsuario> lista = new ArrayList<ModUsuario>();

        Conexao conn = new Conexao();

        String sql = "select * from tb_usuario;";

        PreparedStatement ps;

        ps = conn.getConexao().prepareStatement(sql);

        ResultSet rs;

        rs = ps.executeQuery();

        while (rs.next()) {

            ModUsuario cadastro = new ModUsuario();

            cadastro.setId(rs.getInt("id_user"));
            cadastro.setNome(rs.getString("nome"));
            cadastro.setEndereco(rs.getString("endereco"));
            cadastro.setTelefone(rs.getString("telefone"));
            cadastro.setIdade(rs.getInt("idade"));
            cadastro.setUsuario(rs.getString("usuario"));
            cadastro.setSenha(rs.getString("senha"));
            cadastro.setStatus(rs.getString("status"));

            lista.add(cadastro);

        }

        return lista;
    }

    public void editarFunc(ModUsuario modUsuario) throws SQLException {
        Conexao conn = new Conexao();
        PreparedStatement ps;

        String sql = "UPDATE tb_usuario "
                + "SET endereco = ?, telefone = ? "
                + "WHERE id_user = ?";
        ps = conn.getConexao().prepareStatement(sql);

        ps.setString(1, modUsuario.getEndereco());
        ps.setString(2, modUsuario.getTelefone());
        ps.setInt(3, modUsuario.getId());

        ps.executeUpdate();
    }

    public void editar(ModUsuario modUsuario, String cargo) throws SQLException {
        Conexao conn = new Conexao();
        PreparedStatement ps;

        if (cargo.equals("Admiinitrador")) {
            String sql = "UPDATE tb_usuario "
                    + "SET nome = ?, endereco = ?, telefone = ?, idade = ?, usuario = ?, senha = ?, status = ? "
                    + "WHERE id_user = ?";
            ps = conn.getConexao().prepareStatement(sql);

            ps.setString(1, modUsuario.getNome());
            ps.setString(2, modUsuario.getEndereco());
            ps.setString(3, modUsuario.getTelefone());
            ps.setInt(4, modUsuario.getIdade());
            ps.setString(5, modUsuario.getUsuario());
            ps.setString(6, modUsuario.getSenha());
            ps.setString(7, modUsuario.getStatus());
            ps.setInt(8, modUsuario.getId());
        } else {
            String sql = "UPDATE tb_usuario "
                    + "SET endereco = ?, telefone = ? "
                    + "WHERE id_user = ?";
            ps = conn.getConexao().prepareStatement(sql);

            ps.setString(1, modUsuario.getEndereco());
            ps.setString(2, modUsuario.getTelefone());
            ps.setInt(3, modUsuario.getId());
        }

        ps.executeUpdate();
    }

    public boolean validaUsuario(ModUsuario modUsuario) throws SQLException {
        boolean status = false;

        Conexao conn = new Conexao();
        PreparedStatement ps;

        String sql = "SELECT * FROM tb_usuario "
                + "WHERE usuario = ?";

        ps = conn.getConexao().prepareStatement(sql);

        ps.setString(1, modUsuario.getUsuario());

        ResultSet rs;

        rs = ps.executeQuery();

        if (rs.next()) {
            status = true;
        }

        return status;
    }
}
