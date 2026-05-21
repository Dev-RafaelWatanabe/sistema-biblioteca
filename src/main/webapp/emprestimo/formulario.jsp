<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Novo Empréstimo" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-arrow-left-right me-2"></i>Registrar Novo Empréstimo</h2>
    <a href="${pageContext.request.contextPath}/emprestimos?action=listar" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left"></i> Voltar
    </a>
</div>

<div class="form-container">
    <form method="post" action="${pageContext.request.contextPath}/emprestimos">
        <div class="mb-3">
            <label for="livroId" class="form-label">Livro *</label>
            <select class="form-select" id="livroId" name="livroId" required>
                <option value="">Selecione um livro...</option>
                <c:forEach var="livro" items="${livros}">
                    <c:if test="${livro.quantidadeDisponivel > 0}">
                        <option value="${livro.id}">
                            ${livro.titulo} (${livro.quantidadeDisponivel} disponível)
                        </option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label for="pessoaId" class="form-label">Pessoa *</label>
            <select class="form-select" id="pessoaId" name="pessoaId" required>
                <option value="">Selecione uma pessoa...</option>
                <c:forEach var="pessoa" items="${pessoas}">
                    <option value="${pessoa.id}">${pessoa.nome} - ${pessoa.cpf}</option>
                </c:forEach>
            </select>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="dataEmprestimo" class="form-label">Data do Empréstimo *</label>
                <input type="date" class="form-control" id="dataEmprestimo" name="dataEmprestimo" required>
            </div>
            <div class="col-md-6 mb-3">
                <label for="dataPrevisaoDevolucao" class="form-label">Previsão de Devolução *</label>
                <input type="date" class="form-control" id="dataPrevisaoDevolucao" name="dataPrevisaoDevolucao" required>
            </div>
        </div>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary-custom">
                <i class="bi bi-check-lg"></i> Registrar Empréstimo
            </button>
            <a href="${pageContext.request.contextPath}/emprestimos?action=listar" class="btn btn-outline-secondary">
                Cancelar
            </a>
        </div>
    </form>
</div>

<script>
    // Set default dates
    document.addEventListener('DOMContentLoaded', function() {
        const today = new Date().toISOString().split('T')[0];
        const futureDate = new Date();
        futureDate.setDate(futureDate.getDate() + 14);
        const future = futureDate.toISOString().split('T')[0];

        document.getElementById('dataEmprestimo').value = today;
        document.getElementById('dataPrevisaoDevolucao').value = future;
    });
</script>

<%@ include file="/layout/footer.jsp" %>
