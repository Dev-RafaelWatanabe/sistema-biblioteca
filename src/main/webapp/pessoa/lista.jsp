<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Pessoas" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-people-fill me-2"></i>Gerenciar Pessoas</h2>
    <a href="${pageContext.request.contextPath}/pessoas?action=novo" class="btn btn-primary-custom">
        <i class="bi bi-plus-lg"></i> Nova Pessoa
    </a>
</div>

<div class="table-container">
    <c:choose>
        <c:when test="${not empty pessoas}">
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
                    <c:forEach var="pessoa" items="${pessoas}">
                        <tr>
                            <td><strong>${pessoa.id}</strong></td>
                            <td>${pessoa.nome}</td>
                            <td>${pessoa.email}</td>
                            <td>${pessoa.telefone}</td>
                            <td><code>${pessoa.cpf}</code></td>
                            <td>
                                <div class="d-flex gap-2">
                                    <a href="${pageContext.request.contextPath}/pessoas?action=editar&id=${pessoa.id}"
                                       class="btn-action" title="Editar">
                                        <i class="bi bi-pencil"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/pessoas?action=deletar&id=${pessoa.id}"
                                       class="btn-action delete" title="Excluir"
                                       onclick="return confirm('Tem certeza que deseja excluir esta pessoa?')">
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
                <h5>Nenhuma pessoa cadastrada</h5>
                <p>Clique em "Nova Pessoa" para começar.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/layout/footer.jsp" %>
