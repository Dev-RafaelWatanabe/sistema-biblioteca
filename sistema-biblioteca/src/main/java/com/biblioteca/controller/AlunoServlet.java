package com.biblioteca.controller;

import com.biblioteca.dao.AlunoDAO;
import com.biblioteca.model.Aluno;
import com.biblioteca.model.regra.RegraAluno;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/alunos")
public class AlunoServlet extends HttpServlet {

    private final AlunoDAO dao = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "listar";

        try {
            switch (action) {
                case "novo":
                    request.setAttribute("pagina", "alunos");
                    request.getRequestDispatcher("/aluno-admin/formulario.jsp").forward(request, response);
                    break;
                case "editar":
                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("aluno", dao.buscarPorId(id));
                    request.setAttribute("pagina", "alunos");
                    request.getRequestDispatcher("/aluno-admin/formulario.jsp").forward(request, response);
                    break;
                case "deletar":
                    dao.deletar(Integer.parseInt(request.getParameter("id")));
                    request.getSession().setAttribute("mensagem", "Aluno removido com sucesso!");
                    request.getSession().setAttribute("tipoMensagem", "success");
                    response.sendRedirect(request.getContextPath() + "/admin/alunos?action=listar");
                    break;
                default:
                    request.setAttribute("alunos", dao.listarTodos());
                    request.setAttribute("pagina", "alunos");
                    request.getRequestDispatcher("/aluno-admin/lista.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
            response.sendRedirect(request.getContextPath() + "/admin/alunos?action=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Aluno aluno = new Aluno();
            String idStr = request.getParameter("id");
            boolean editar = idStr != null && !idStr.isEmpty();
            if (editar) aluno.setId(Integer.parseInt(idStr));
            aluno.setNome(request.getParameter("nome"));
            aluno.setEmail(request.getParameter("email"));
            aluno.setTelefone(request.getParameter("telefone"));
            aluno.setCpf(request.getParameter("cpf"));
            aluno.setSenha(request.getParameter("senha"));
            aluno.setEndereco(request.getParameter("endereco"));

            // Regras de negócio. Em edição, senha é opcional.
            String erro = RegraAluno.validarCampos(aluno, !editar);
            if (erro == null) erro = RegraAluno.existeCpfDuplicado(aluno, dao);
            if (erro != null) {
                request.getSession().setAttribute("mensagem", erro);
                request.getSession().setAttribute("tipoMensagem", "danger");
                response.sendRedirect(request.getContextPath() + "/admin/alunos?action=" +
                        (editar ? "editar&id=" + aluno.getId() : "novo"));
                return;
            }

            if (editar) {
                dao.atualizar(aluno);
                request.getSession().setAttribute("mensagem", "Aluno atualizado com sucesso!");
            } else {
                dao.inserir(aluno);
                request.getSession().setAttribute("mensagem", "Aluno cadastrado com sucesso!");
            }
            request.getSession().setAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro ao salvar aluno: " + e.getMessage());
            request.getSession().setAttribute("tipoMensagem", "danger");
        }
        response.sendRedirect(request.getContextPath() + "/admin/alunos?action=listar");
    }
}
