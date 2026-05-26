<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Livros" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-journal-bookmark-fill me-2"></i>Gerenciar Livros</h2>
    <a href="${pageContext.request.contextPath}/admin/livros?action=novo" class="btn btn-primary-custom">
        <i class="bi bi-plus-lg"></i> Novo Livro
    </a>
</div>

<div class="table-container">
    <c:choose>
        <c:when test="${not empty livros}">
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Título</th>
                        <th>Autor</th>
                        <th>Gênero</th>
                        <th>Ano</th>
                        <th>Status</th>
                        <th>Disponível</th>
                        <th style="width: 120px;">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="livro" items="${livros}">
                        <tr>
                            <td><strong>${livro.id}</strong></td>
                            <td>${livro.titulo}</td>
                            <td>${livro.autorNome != null ? livro.autorNome : '—'}</td>
                            <td>${livro.genero}</td>
                            <td>${livro.anoPublicacao}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${livro.status == 'Disponível'}">
                                        <span class="badge-status badge-ativo">Disponível</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge-status badge-atrasado">Alugado</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <span class="badge-status ${livro.quantidadeDisponivel > 0 ? 'badge-ativo' : 'badge-atrasado'}">
                                    ${livro.quantidadeDisponivel} / ${livro.quantidadeTotal}
                                </span>
                            </td>
                            <td>
                                <div class="d-flex gap-2">
                                    <a href="${pageContext.request.contextPath}/admin/livros?action=editar&id=${livro.id}"
                                       class="btn-action" title="Editar">
                                        <i class="bi bi-pencil"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/livros?action=deletar&id=${livro.id}"
                                       class="btn-action delete" title="Excluir"
                                       onclick="return confirm('Tem certeza que deseja excluir este livro?')">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="empty-state">
                <i class="bi bi-journal-bookmark d-block"></i>
                <h5>Nenhum livro cadastrado</h5>
                <p>Clique em "Novo Livro" para começar.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/layout/footer.jsp" %>
