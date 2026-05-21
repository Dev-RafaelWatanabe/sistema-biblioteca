package com.biblioteca.listener;

import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.model.Usuario;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Retry a few times in case DB is still starting up
        for (int tentativa = 1; tentativa <= 3; tentativa++) {
            try {
                UsuarioDAO dao = new UsuarioDAO();
                if (!dao.existeAlgum()) {
                    Usuario admin = new Usuario();
                    admin.setNome("Administrador");
                    admin.setEmail("admin@biblioteca.com");
                    admin.setSenha("admin123");
                    dao.inserir(admin);
                    System.out.println(">>> Usuário admin padrão criado: admin@biblioteca.com / admin123");
                } else {
                    System.out.println(">>> Usuário admin já existe.");
                }
                return; // success
            } catch (Throwable e) {
                System.err.println("Tentativa " + tentativa + " falhou ao criar admin: " + e.getMessage());
                if (tentativa < 3) {
                    try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                }
            }
        }
        System.err.println(">>> AVISO: Não foi possível criar o usuário admin. O sistema iniciará sem ele.");
    }
}
