package com.bernardo.loja;

import com.bernardo.loja.model.Produto;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de integração para ProdutoDAO usando H2 em memória.
 * Não requer MySQL instalado — roda em qualquer ambiente (local, CI).
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdutoDAOTest {

    private static Connection conn;
    private static TestProdutoDAO dao;

    @BeforeAll
    static void setUp() throws Exception {
        // H2 em memória com modo de compatibilidade MySQL
        conn = DriverManager.getConnection(
            "jdbc:h2:mem:loja_test;MODE=MySQL;DB_CLOSE_DELAY=-1", "sa", ""
        );
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE produtos (
                    id       INT AUTO_INCREMENT PRIMARY KEY,
                    nome     VARCHAR(100) NOT NULL,
                    descricao TEXT,
                    preco    DOUBLE NOT NULL,
                    estoque  INT NOT NULL
                )
            """);
        }
        dao = new TestProdutoDAO(conn);
    }

    @AfterAll
    static void tearDown() throws Exception {
        conn.close();
    }

    @BeforeEach
    void limpar() throws Exception {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM produtos");
            stmt.execute("ALTER TABLE produtos ALTER COLUMN id RESTART WITH 1");
        }
    }

    // ── Testes ──────────────────────────────────────────────────

    @Test
    @Order(1)
    void salvarProduto_deveInserirNoBanco() {
        Produto p = produto("Notebook", "Dell i7", 3500.0, 10);
        dao.salvar(p);

        List<Produto> lista = dao.listar();
        assertEquals(1, lista.size());
        assertEquals("Notebook", lista.get(0).getNome());
    }

    @Test
    @Order(2)
    void listar_semProdutos_deveRetornarListaVazia() {
        List<Produto> lista = dao.listar();
        assertTrue(lista.isEmpty());
    }

    @Test
    @Order(3)
    void listar_comMultiplosProdutos_deveRetornarTodos() {
        dao.salvar(produto("Mouse", "Sem fio", 80.0, 20));
        dao.salvar(produto("Teclado", "Mecânico", 250.0, 5));
        dao.salvar(produto("Monitor", "27 polegadas", 1200.0, 3));

        List<Produto> lista = dao.listar();
        assertEquals(3, lista.size());
    }

    @Test
    @Order(4)
    void obterEstoque_deveRetornarEstoqueCorreto() {
        dao.salvar(produto("Headset", "Gamer", 150.0, 8));
        List<Produto> lista = dao.listar();
        int id = lista.get(0).getId();

        int estoque = dao.obterEstoque(id);
        assertEquals(8, estoque);
    }

    @Test
    @Order(5)
    void obterEstoque_idInexistente_deveRetornarZero() {
        int estoque = dao.obterEstoque(9999);
        assertEquals(0, estoque);
    }

    @Test
    @Order(6)
    void atualizarEstoque_deveAlterarEstoqueNoBanco() {
        dao.salvar(produto("Cadeira", "Ergonômica", 900.0, 5));
        List<Produto> lista = dao.listar();
        int id = lista.get(0).getId();

        dao.atualizarEstoque(id, 3);
        assertEquals(3, dao.obterEstoque(id));
    }

    @Test
    @Order(7)
    void salvarProduto_precoZero_deveSerAceito() {
        dao.salvar(produto("Brinde", "Grátis", 0.0, 100));
        List<Produto> lista = dao.listar();
        assertEquals(1, lista.size());
        assertEquals(0.0, lista.get(0).getPreco(), 0.001);
    }

    @Test
    @Order(8)
    void listar_deveMapearTodosOsCampos() {
        dao.salvar(produto("Mesa", "Escritório", 450.0, 7));
        Produto p = dao.listar().get(0);

        assertNotNull(p.getNome());
        assertEquals("Mesa", p.getNome());
        assertEquals("Escritório", p.getDescricao());
        assertEquals(450.0, p.getPreco(), 0.001);
        assertEquals(7, p.getEstoque());
        assertTrue(p.getId() > 0);
    }

    // ── Helper ──────────────────────────────────────────────────

    private static Produto produto(String nome, String desc, double preco, int estoque) {
        Produto p = new Produto();
        p.setNome(nome);
        p.setDescricao(desc);
        p.setPreco(preco);
        p.setEstoque(estoque);
        return p;
    }
}
