package com.bernardo.loja.dao;

import com.bernardo.loja.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VendaDAO {

    private final ProdutoDAO pdao = new ProdutoDAO();

    public void realizarVenda(int produtoId, int quantidade) {

        int estoqueAtual = pdao.obterEstoque(produtoId);

        if (estoqueAtual < quantidade) {
            System.out.println("❌ Estoque insuficiente! Estoque atual: " + estoqueAtual);
            return;
        }

        int novoEstoque = estoqueAtual - quantidade;

        String sql = "INSERT INTO vendas (cliente_id, usuario_id) VALUES (NULL, NULL)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            pdao.atualizarEstoque(produtoId, novoEstoque);

            System.out.println("✅ Venda realizada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
