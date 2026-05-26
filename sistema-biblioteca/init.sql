-- =============================================
-- Sistema Biblioteca - Script de Inicialização
-- =============================================

-- Tabela de Usuários (administradores)
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

-- Tabela de Livros (sem ISBN, com status Disponível/Alugado)
CREATE TABLE IF NOT EXISTS livro (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    ano_publicacao INTEGER,
    genero VARCHAR(80),
    quantidade_total INTEGER NOT NULL DEFAULT 1,
    quantidade_disponivel INTEGER NOT NULL DEFAULT 1,
    status VARCHAR(20) NOT NULL DEFAULT 'Disponível',
    autor_id INTEGER REFERENCES autor(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Alunos (antiga "pessoa", agora com senha)
CREATE TABLE IF NOT EXISTS aluno (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(100),
    telefone VARCHAR(20),
    cpf VARCHAR(14) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    endereco VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Aluguéis (antigo "emprestimo", novo workflow)
CREATE TABLE IF NOT EXISTS aluguel (
    id SERIAL PRIMARY KEY,
    aluno_id INTEGER NOT NULL REFERENCES aluno(id) ON DELETE CASCADE,
    livro_id INTEGER NOT NULL REFERENCES livro(id) ON DELETE CASCADE,
    data_solicitacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_aprovacao TIMESTAMP,
    data_finalizacao TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'Solicitado'
);

-- Tabela de Tokens (Manter Conectado)
CREATE TABLE IF NOT EXISTS tokens_login (
    id SERIAL PRIMARY KEY,
    token VARCHAR(128) UNIQUE NOT NULL,
    usuario_id INTEGER NOT NULL,
    tipo_usuario VARCHAR(10) NOT NULL,
    data_expiracao TIMESTAMP NOT NULL
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
INSERT INTO livro (titulo, ano_publicacao, genero, quantidade_total, quantidade_disponivel, status, autor_id) VALUES
('Dom Casmurro', 1899, 'Romance', 5, 5, 'Disponível', 1),
('Memórias Póstumas de Brás Cubas', 1881, 'Romance', 3, 3, 'Disponível', 1),
('A Hora da Estrela', 1977, 'Romance', 4, 4, 'Disponível', 2),
('Perto do Coração Selvagem', 1943, 'Romance', 2, 2, 'Disponível', 2),
('Ensaio sobre a Cegueira', 1995, 'Ficção', 3, 3, 'Disponível', 3),
('Cem Anos de Solidão', 1967, 'Realismo Mágico', 4, 4, 'Disponível', 4),
('Capitães da Areia', 1937, 'Romance', 3, 3, 'Disponível', 5),
('Gabriela, Cravo e Canela', 1958, 'Romance', 2, 2, 'Disponível', 5);

-- Os alunos seed são inseridos pelo StartupListener (Java) para garantir
-- que a senha seja hashed corretamente com BCrypt.
-- Credenciais padrão dos alunos seed: CPF / senha "aluno123".
