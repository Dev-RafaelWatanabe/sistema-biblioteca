-- =============================================
-- Sistema Biblioteca - Script de Inicialização
-- =============================================

-- Tabela de Usuários (autenticação)
CREATE TABLE IF NOT EXISTS usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Autores
CREATE TABLE IF NOT EXISTS autor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    nacionalidade VARCHAR(100),
    data_nascimento DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Livros
CREATE TABLE IF NOT EXISTS livro (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    ano_publicacao INTEGER,
    genero VARCHAR(80),
    quantidade_total INTEGER NOT NULL DEFAULT 1,
    quantidade_disponivel INTEGER NOT NULL DEFAULT 1,
    autor_id INTEGER REFERENCES autor(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Pessoas (membros da biblioteca)
CREATE TABLE IF NOT EXISTS pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(100),
    telefone VARCHAR(20),
    cpf VARCHAR(14) UNIQUE,
    endereco VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Empréstimos
CREATE TABLE IF NOT EXISTS emprestimo (
    id SERIAL PRIMARY KEY,
    livro_id INTEGER NOT NULL REFERENCES livro(id) ON DELETE CASCADE,
    pessoa_id INTEGER NOT NULL REFERENCES pessoa(id) ON DELETE CASCADE,
    data_emprestimo DATE NOT NULL DEFAULT CURRENT_DATE,
    data_previsao_devolucao DATE NOT NULL,
    data_devolucao DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- Dados de Exemplo (Seed)
-- =============================================

-- Autores
INSERT INTO autor (nome, nacionalidade, data_nascimento) VALUES
('Machado de Assis', 'Brasileiro', '1839-06-21'),
('Clarice Lispector', 'Brasileira', '1920-12-10'),
('José Saramago', 'Português', '1922-11-16'),
('Gabriel García Márquez', 'Colombiano', '1927-03-06'),
('Jorge Amado', 'Brasileiro', '1912-08-10');

-- Livros
INSERT INTO livro (titulo, isbn, ano_publicacao, genero, quantidade_total, quantidade_disponivel, autor_id) VALUES
('Dom Casmurro', '978-85-359-0277-1', 1899, 'Romance', 5, 5, 1),
('Memórias Póstumas de Brás Cubas', '978-85-359-0278-8', 1881, 'Romance', 3, 3, 1),
('A Hora da Estrela', '978-85-325-0284-4', 1977, 'Romance', 4, 4, 2),
('Perto do Coração Selvagem', '978-85-325-0285-1', 1943, 'Romance', 2, 2, 2),
('Ensaio sobre a Cegueira', '978-972-20-1328-7', 1995, 'Ficção', 3, 3, 3),
('Cem Anos de Solidão', '978-85-01-01294-5', 1967, 'Realismo Mágico', 4, 4, 4),
('Capitães da Areia', '978-85-359-0279-5', 1937, 'Romance', 3, 3, 5),
('Gabriela, Cravo e Canela', '978-85-359-0280-1', 1958, 'Romance', 2, 2, 5);

-- Pessoas
INSERT INTO pessoa (nome, email, telefone, cpf, endereco) VALUES
('João Silva', 'joao@email.com', '(11) 99999-1111', '111.222.333-44', 'Rua A, 100 - São Paulo'),
('Maria Oliveira', 'maria@email.com', '(11) 99999-2222', '222.333.444-55', 'Rua B, 200 - São Paulo'),
('Pedro Santos', 'pedro@email.com', '(21) 99999-3333', '333.444.555-66', 'Rua C, 300 - Rio de Janeiro'),
('Ana Costa', 'ana@email.com', '(31) 99999-4444', '444.555.666-77', 'Rua D, 400 - Belo Horizonte');
