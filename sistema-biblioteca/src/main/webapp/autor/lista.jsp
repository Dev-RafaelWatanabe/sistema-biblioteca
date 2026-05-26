<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Autores" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-pen-fill me-2"></i>Gerenciar Autores</h2>
    <a href="${pageContext.request.contextPath}/admin/autores?action=novo" class="btn btn-primary-custom">
        <i class="bi bi-plus-lg"></i> Novo Autor
    </a>
</div>

<div class="table-container">
    <c:choose>
        <c:when test="${not empty autores}">
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Nome</th>
                        <th>Nacionalidade</th>
                        <th>Data de Nascimento</th>
                        <th style="width: 120px;">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="autor" items="${autores}">
                        <tr>
                            <td><strong>${autor.id}</strong></td>
                            <td>${autor.nome}</td>
                            <td>${autor.nacionalidade}</td>
                            <td>${autor.dataNascimento}</td>
                            <td>
                                <div class="d-flex gap-2">
                                    <a href="${pageContext.request.contextPath}/admin/autores?action=editar&id=${autor.id}"
                                       class="btn-action" title="Editar">
                                        <i class="bi bi-pencil"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/autores?action=deletar&id=${autor.id}"
                                       class="btn-action delete" title="Excluir"
                                       onclick="return confirm('Tem certeza que deseja excluir este autor?')">
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
                <i class="bi bi-pen d-block"></i>
                <h5>Nenhum autor cadastrado</h5>
                <p>Clique em "Novo Autor" para começar.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/layout/footer.jsp" %>
