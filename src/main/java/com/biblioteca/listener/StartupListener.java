package com.biblioteca.listener;

import com.biblioteca.dao.AlunoDAO;
import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.model.Aluno;
import com.biblioteca.model.Usuario;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        for (int tentativa = 1; tentativa <= 3; tentativa++) {
            try {
                criarAdminPadrao();
                criarAlunosSeed();
                return;
            } catch (Throwable e) {
                System.err.println("Tentativa " + tentativa + " falhou: " + e.getMessage());
                if (tentativa < 3) {
                    try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                }
            }
        }
        System.err.println(">>> AVISO: Falha ao executar seed inicial.");
    }

    private void criarAdminPadrao() throws Exception {
        UsuarioDAO dao = new UsuarioDAO();
        if (!dao.existeAlgum()) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@biblioteca.com");
            admin.setSenha("admin123");
            dao.inserir(admin);
            System.out.println(">>> Admin padrão criado: admin@biblioteca.com / admin123");
        }
    }

    private void criarAlunosSeed() throws Exception {
        AlunoDAO dao = new AlunoDAO();
        if (dao.contarTotal() > 0) return;

        String[][] seed = {
            {"João Silva",     "joao@email.com",  "(11) 99999-1111", "111.222.333-44", "Rua A, 100 - São Paulo"},
            {"Maria Oliveira", "maria@email.com", "(11) 99999-2222", "222.333.444-55", "Rua B, 200 - São Paulo"},
            {"Pedro Santos",   "pedro@email.com", "(21) 99999-3333", "333.444.555-66", "Rua C, 300 - Rio de Janeiro"},
            {"Ana Costa",      "ana@email.com",   "(31) 99999-4444", "444.555.666-77", "Rua D, 400 - Belo Horizonte"}
        };
        for (String[] s : seed) {
            Aluno a = new Aluno();
            a.setNome(s[0]);
            a.setEmail(s[1]);
            a.setTelefone(s[2]);
            a.setCpf(s[3]);
            a.setEndereco(s[4]);
            a.setSenha("aluno123");
            dao.inserir(a);
        }
        System.out.println(">>> Alunos seed criados (senha padrão: aluno123).");
    }
}
