<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Solicitações de Aluguel" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-arrow-left-right me-2"></i>Gerenciar Solicitações</h2>
    <a href="${pageContext.request.contextPath}/admin/alugueis?action=novo" class="btn btn-primary-custom">
        <i class="bi bi-plus-lg"></i> Nova Solicitação
    </a>
</div>

<div class="table-container">
    <c:choose>
        <c:when test="${not empty alugueis}">
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Aluno</th>
                        <th>Livro</th>
                        <th>Solicitação</th>
                        <th>Status</th>
                        <th style="width: 220px;">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="al" items="${alugueis}">
                        <tr>
                            <td><strong>${al.id}</strong></td>
                            <td>${al.alunoNome}</td>
                            <td>${al.livroTitulo}</td>
                            <td>${al.dataSolicitacaoFormatada}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${al.status == 'Solicitado'}">
                                        <span class="badge-status badge-ativo">Solicitado</span>
                                    </c:when>
                                    <c:when test="${al.status == 'Concluído'}">
                                        <span class="badge-status badge-devolvido">Concluído</span>
                                    </c:when>
                                    <c:when test="${al.status == 'Finalizado'}">
                                        <span class="badge-status badge-devolvido">Finalizado</span>
                                    </c:when>
                                    <c:when test="${al.status == 'Recusada'}">
                                        <span class="badge-status badge-atrasado">Recusada</span>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${al.status == 'Solicitado'}">
                                    <form method="post" action="${pageContext.request.contextPath}/admin/alugueis" class="d-inline"
                                          onsubmit="return confirm('Aprovar esta solicitação?');">
                                        <input type="hidden" name="acao" value="aprovar"/>
                                        <input type="hidden" name="id" value="${al.id}"/>
                                        <button class="btn btn-sm btn-primary-custom" title="Aprovar">
                                            <i class="bi bi-check2-circle"></i> Aprovar
                                        </button>
                                    </form>
                                    <form method="post" action="${pageContext.request.contextPath}/admin/alugueis" class="d-inline"
                                          onsubmit="return confirm('Recusar esta solicitação?');">
                                        <input type="hidden" name="acao" value="recusar"/>
                                        <input type="hidden" name="id" value="${al.id}"/>
                                        <button class="btn btn-sm btn-outline-danger" title="Recusar">
                                            <i class="bi bi-x-circle"></i> Recusar
                                        </button>
                                    </form>
                                </c:if>
                                <c:if test="${al.status == 'Concluído'}">
                                    <form method="post" action="${pageContext.request.contextPath}/admin/alugueis" class="d-inline"
                                          onsubmit="return confirm('Finalizar este aluguel?');">
                                        <input type="hidden" name="acao" value="finalizar"/>
                                        <input type="hidden" name="id" value="${al.id}"/>
                                        <button class="btn btn-sm btn-primary-custom" title="Finalizar">
                                            <i class="bi bi-flag-fill"></i> Finalizar
                                        </button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="empty-state">
                <i class="bi bi-arrow-left-right d-block"></i>
                <h5>Nenhuma solicitação registrada</h5>
                <p>Aluguéis serão exibidos aqui quando alunos solicitarem ou você criar uma solicitação manualmente.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/layout/footer.jsp" %>
