package com.bernardo.loja.dao;

import com.bernardo.loja.config.DatabaseConnection;
import com.bernardo.loja.model.Usuario;
import java.sql.*;

public class UsuarioDAO {
  public void salvar(Usuario u){
    String sql = "INSERT INTO usuarios (nome, email, senha, perfil) VALUES (?,?,?,?)";
    try(Connection c=DatabaseConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
      ps.setString(1,u.getNome()); ps.setString(2,u.getEmail()); ps.setString(3,u.getSenha()); ps.setString(4,u.getPerfil());
      ps.executeUpdate();
    } catch(Exception e){ throw new RuntimeException(e); }
  }
}
