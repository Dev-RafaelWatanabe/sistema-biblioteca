<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Meus Aluguéis" scope="request"/>
<%@ include file="/layout/header-aluno.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-bookmark-check-fill me-2"></i>Meus Aluguéis</h2>
    <a href="${pageContext.request.contextPath}/aluno/catalogo" class="btn btn-primary-custom">
        <i class="bi bi-plus-lg"></i> Solicitar novo
    </a>
</div>

<div class="table-container">
    <c:choose>
        <c:when test="${not empty alugueis}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Livro</th>
                        <th>Autor</th>
                        <th>Data da Solicitação</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="al" items="${alugueis}">
                        <tr>
                            <td><strong>${al.livroTitulo}</strong></td>
                            <td>${al.autorNome != null ? al.autorNome : '—'}</td>
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
                                    <c:otherwise>
                                        <span class="badge-status">${al.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="empty-state">
                <i class="bi bi-bookmark d-block"></i>
                <h5>Você ainda não solicitou nenhum aluguel</h5>
                <p>Vá para <a href="${pageContext.request.contextPath}/aluno/catalogo">Livros Disponíveis</a> e solicite o primeiro.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/layout/footer.jsp" %>
