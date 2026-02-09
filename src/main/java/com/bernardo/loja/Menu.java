package com.bernardo.loja;

import com.bernardo.loja.dao.ProdutoDAO;
import com.bernardo.loja.dao.VendaDAO;
import com.bernardo.loja.model.Produto;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProdutoDAO produtoDAO = new ProdutoDAO();
    private static final VendaDAO vendaDAO = new VendaDAO();

    public static void exibir() {
        while (true) {
            System.out.println("\n=== SISTEMA DE GESTÃO DE LOJA ===");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Registrar venda");
            System.out.println("4 - Sair");
            System.out.print("Escolha: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarProduto();
                case 2 -> listarProdutos();
                case 3 -> registrarVenda();
                case 4 -> {
                    System.out.println("Encerrando sistema...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarProduto() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Preço: ");
        double preco = scanner.nextDouble();

        System.out.print("Estoque inicial: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setEstoque(estoque);

        produtoDAO.salvar(produto);
        System.out.println("✅ Produto cadastrado!");
    }

    private static void listarProdutos() {
        List<Produto> produtos = produtoDAO.listar();

        System.out.println("\n=== PRODUTOS CADASTRADOS ===");
        for (Produto p : produtos) {
            System.out.printf("ID: %d | %s | R$ %.2f | Estoque: %d%n",
                    p.getId(), p.getNome(), p.getPreco(), p.getEstoque());
        }
    }

    private static void registrarVenda() {
        System.out.print("ID do produto: ");
        int produtoId = scanner.nextInt();

        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        vendaDAO.realizarVenda(produtoId, quantidade);
    }
}
