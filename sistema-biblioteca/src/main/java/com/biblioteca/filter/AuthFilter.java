package com.biblioteca.filter;

import com.biblioteca.dao.AlunoDAO;
import com.biblioteca.dao.TokenDAO;
import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.model.Aluno;
import com.biblioteca.model.TokenLogin;
import com.biblioteca.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private final TokenDAO tokenDAO = new TokenDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        // Rotas públicas
        if (path.startsWith("/css/") ||
            path.startsWith("/js/") ||
            path.startsWith("/images/") ||
            path.equals("/") ||
            path.equals("/index.jsp") ||
            path.equals("/loginAdmin") ||
            path.equals("/loginAluno") ||
            path.equals("/logout")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        Object usuario = session != null ? session.getAttribute("usuarioLogado") : null;
        String tipo = session != null ? (String) session.getAttribute("tipoUsuario") : null;

        // Tentar restaurar via remember_token
        if (usuario == null) {
            if (tryRememberToken(request)) {
                session = request.getSession(false);
                usuario = session != null ? session.getAttribute("usuarioLogado") : null;
                tipo = session != null ? (String) session.getAttribute("tipoUsuario") : null;
            }
        }

        if (usuario == null) {
            if (path.startsWith("/admin/")) {
                response.sendRedirect(contextPath + "/loginAdmin");
            } else if (path.startsWith("/aluno/")) {
                response.sendRedirect(contextPath + "/loginAluno");
            } else {
                response.sendRedirect(contextPath + "/");
            }
            return;
        }

        // Verificar perfil
        if (path.startsWith("/admin/") && !"admin".equals(tipo)) {
            response.sendRedirect(contextPath + "/loginAdmin");
            return;
        }
        if (path.startsWith("/aluno/") && !"aluno".equals(tipo)) {
            response.sendRedirect(contextPath + "/loginAluno");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean tryRememberToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return false;
        String token = null;
        for (Cookie c : cookies) {
            if ("remember_token".equals(c.getName())) {
                token = c.getValue();
                break;
            }
        }
        if (token == null || token.isEmpty()) return false;

        try {
            TokenLogin t = tokenDAO.buscarPorToken(token);
            if (t == null) return false;
            if (t.getDataExpiracao() != null && t.getDataExpiracao().isBefore(LocalDateTime.now())) {
                tokenDAO.excluirPorToken(token);
                return false;
            }

            HttpSession session = request.getSession(true);
            if ("admin".equals(t.getTipoUsuario())) {
                Usuario u = usuarioDAO.buscarPorId(t.getUsuarioId());
                if (u == null) return false;
                session.setAttribute("usuarioLogado", u);
                session.setAttribute("tipoUsuario", "admin");
            } else if ("aluno".equals(t.getTipoUsuario())) {
                Aluno a = alunoDAO.buscarPorId(t.getUsuarioId());
                if (a == null) return false;
                session.setAttribute("usuarioLogado", a);
                session.setAttribute("tipoUsuario", "aluno");
            } else {
                return false;
            }
            session.setMaxInactiveInterval(30 * 60);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
