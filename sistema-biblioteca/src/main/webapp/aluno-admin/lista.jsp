<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Alunos" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-people-fill me-2"></i>Gerenciar Alunos</h2>
    <a href="${pageContext.request.contextPath}/admin/alunos?action=novo" class="btn btn-primary-custom">
        <i class="bi bi-plus-lg"></i> Novo Aluno
    </a>
</div>

<div class="table-container">
    <c:choose>
        <c:when test="${not empty alunos}">
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Nome</th>
                        <th>E-mail</th>
                        <th>Telefone</th>
                        <th>CPF</th>
                        <th style="width: 120px;">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="aluno" items="${alunos}">
                        <tr>
                            <td><strong>${aluno.id}</strong></td>
                            <td>${aluno.nome}</td>
                            <td>${aluno.email}</td>
                            <td>${aluno.telefone}</td>
                            <td><code>${aluno.cpf}</code></td>
                            <td>
                                <div class="d-flex gap-2">
                                    <a href="${pageContext.request.contextPath}/admin/alunos?action=editar&id=${aluno.id}"
                                       class="btn-action" title="Editar">
                                        <i class="bi bi-pencil"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/alunos?action=deletar&id=${aluno.id}"
                                       class="btn-action delete" title="Excluir"
                                       onclick="return confirm('Tem certeza que deseja excluir este aluno?')">
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
                <i class="bi bi-people d-block"></i>
                <h5>Nenhum aluno cadastrado</h5>
                <p>Clique em "Novo Aluno" para começar.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/layout/footer.jsp" %>
