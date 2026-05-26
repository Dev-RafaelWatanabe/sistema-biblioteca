<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="${livro != null ? 'Editar Livro' : 'Novo Livro'}" scope="request"/>
<%@ include file="/layout/header.jsp" %>

<div class="page-header">
    <h2><i class="bi bi-journal-bookmark-fill me-2"></i>${livro != null ? 'Editar Livro' : 'Cadastrar Novo Livro'}</h2>
    <a href="${pageContext.request.contextPath}/admin/livros?action=listar" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left"></i> Voltar
    </a>
</div>

<div class="form-container">
    <form method="post" action="${pageContext.request.contextPath}/admin/livros">
        <c:if test="${livro != null}">
            <input type="hidden" name="id" value="${livro.id}"/>
        </c:if>

        <div class="mb-3">
            <label for="titulo" class="form-label">Título *</label>
            <input type="text" class="form-control" id="titulo" name="titulo"
                   value="${livro != null ? livro.titulo : ''}" required>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="anoPublicacao" class="form-label">Ano de Publicação</label>
                <input type="number" class="form-control" id="anoPublicacao" name="anoPublicacao"
                       value="${livro != null ? livro.anoPublicacao : ''}" min="1450" max="2100">
            </div>
            <div class="col-md-6 mb-3">
                <label for="genero" class="form-label">Gênero</label>
                <input type="text" class="form-control" id="genero" name="genero"
                       value="${livro != null ? livro.genero : ''}">
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="quantidadeTotal" class="form-label">Quantidade Total *</label>
                <input type="number" class="form-control" id="quantidadeTotal" name="quantidadeTotal"
                       value="${livro != null ? livro.quantidadeTotal : '1'}" min="1" required>
            </div>
            <div class="col-md-6 mb-3">
                <label for="autorId" class="form-label">Autor *</label>
                <select class="form-select" id="autorId" name="autorId" required>
                    <option value="">Selecione um autor...</option>
                    <c:forEach var="autor" items="${autores}">
                        <option value="${autor.id}" ${livro != null && livro.autorId == autor.id ? 'selected' : ''}>
                            ${autor.nome}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary-custom">
                <i class="bi bi-check-lg"></i> Salvar
            </button>
            <a href="${pageContext.request.contextPath}/admin/livros?action=listar" class="btn btn-outline-secondary">
                Cancelar
            </a>
        </div>
    </form>
</div>

<%@ include file="/layout/footer.jsp" %>
