<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Empréstimos" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-arrow-left-right me-2"></i>Gerenciar Empréstimos</h2>
    <a href="${pageContext.request.contextPath}/emprestimos?action=novo" class="btn btn-primary-custom">
        <i class="bi bi-plus-lg"></i> Novo Empréstimo
    </a>
</div>

<div class="table-container">
    <c:choose>
        <c:when test="${not empty emprestimos}">
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Livro</th>
                        <th>Pessoa</th>
                        <th>Empréstimo</th>
                        <th>Previsão</th>
                        <th>Devolução</th>
                        <th>Status</th>
                        <th style="width: 140px;">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="emp" items="${emprestimos}">
                        <tr>
                            <td><strong>${emp.id}</strong></td>
                            <td>${emp.livroTitulo}</td>
                            <td>${emp.pessoaNome}</td>
                            <td>${emp.dataEmprestimo}</td>
                            <td>${emp.dataPrevisaoDevolucao}</td>
                            <td>${emp.dataDevolucao != null ? emp.dataDevolucao : '-'}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${emp.status == 'ATIVO'}">
                                        <span class="badge-status badge-ativo">Ativo</span>
                                    </c:when>
                                    <c:when test="${emp.status == 'ATRASADO'}">
                                        <span class="badge-status badge-atrasado">Atrasado</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge-status badge-devolvido">Devolvido</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div class="d-flex gap-2">
                                    <c:if test="${emp.status != 'DEVOLVIDO'}">
                                        <a href="${pageContext.request.contextPath}/emprestimos?action=devolver&id=${emp.id}"
                                           class="btn-action success" title="Devolver"
                                           onclick="return confirm('Confirmar devolução?')">
                                            <i class="bi bi-check2-circle"></i>
                                        </a>
                                    </c:if>
                                    <a href="${pageContext.request.contextPath}/emprestimos?action=deletar&id=${emp.id}"
                                       class="btn-action delete" title="Excluir"
                                       onclick="return confirm('Excluir este empréstimo?')">
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
                <i class="bi bi-arrow-left-right d-block"></i>
                <h5>Nenhum empréstimo registrado</h5>
                <p>Clique em "Novo Empréstimo" para começar.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/layout/footer.jsp" %>
