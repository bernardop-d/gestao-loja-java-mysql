package com.bernardo.loja.dao;

import com.bernardo.loja.config.DatabaseConnection;
import com.bernardo.loja.model.Cliente;
import java.sql.*;

public class ClienteDAO {
  public void salvar(Cliente cte){
    String sql = "INSERT INTO clientes (nome, email, telefone) VALUES (?,?,?)";
    try(Connection c=DatabaseConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
      ps.setString(1, cte.getNome()); ps.setString(2, cte.getEmail()); ps.setString(3, cte.getTelefone());
      ps.executeUpdate();
    } catch(Exception e){ throw new RuntimeException(e); }
  }
}
