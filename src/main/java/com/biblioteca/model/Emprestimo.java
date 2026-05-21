package com.biblioteca.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Emprestimo {
    private int id;
    private int livroId;
    private int pessoaId;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevisaoDevolucao;
    private LocalDate dataDevolucao;
    private String status; // ATIVO, DEVOLVIDO, ATRASADO
    private LocalDateTime createdAt;

    // Campos auxiliares para exibição
    private String livroTitulo;
    private String pessoaNome;

    public Emprestimo() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getLivroId() { return livroId; }
    public void setLivroId(int livroId) { this.livroId = livroId; }

    public int getPessoaId() { return pessoaId; }
    public void setPessoaId(int pessoaId) { this.pessoaId = pessoaId; }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDate getDataPrevisaoDevolucao() { return dataPrevisaoDevolucao; }
    public void setDataPrevisaoDevolucao(LocalDate dataPrevisaoDevolucao) { this.dataPrevisaoDevolucao = dataPrevisaoDevolucao; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getLivroTitulo() { return livroTitulo; }
    public void setLivroTitulo(String livroTitulo) { this.livroTitulo = livroTitulo; }

    public String getPessoaNome() { return pessoaNome; }
    public void setPessoaNome(String pessoaNome) { this.pessoaNome = pessoaNome; }
}
