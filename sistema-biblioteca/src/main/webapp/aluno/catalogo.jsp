<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Livros Disponíveis" scope="request"/>
<%@ include file="/layout/header-aluno.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-journal-bookmark-fill me-2"></i>Livros Disponíveis</h2>
</div>

<div class="table-container">
    <c:choose>
        <c:when test="${not empty livros}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Título</th>
                        <th>Autor</th>
                        <th>Gênero</th>
                        <th>Ano</th>
                        <th>Status</th>
                        <th style="width: 180px;">Ação</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="livro" items="${livros}">
                        <tr>
                            <td><strong>${livro.titulo}</strong></td>
                            <td>${livro.autorNome != null ? livro.autorNome : '—'}</td>
                            <td>${livro.genero}</td>
                            <td>${livro.anoPublicacao}</td>
                            <td><span class="badge-status badge-ativo">${livro.status}</span></td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/aluno/alugueis"
                                      onsubmit="return confirm('Confirmar solicitação de aluguel deste livro?');">
                                    <input type="hidden" name="livroId" value="${livro.id}"/>
                                    <button type="submit" class="btn btn-primary-custom btn-sm">
                                        <i class="bi bi-bookmark-plus"></i> Solicitar Aluguel
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="empty-state">
                <i class="bi bi-journal-x d-block"></i>
                <h5>Nenhum livro disponível no momento</h5>
                <p>Volte mais tarde para ver as novidades.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/layout/footer.jsp" %>
