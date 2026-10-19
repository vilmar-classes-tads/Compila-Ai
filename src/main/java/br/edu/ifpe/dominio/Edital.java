package br.edu.ifpe.dominio;

import java.time.LocalDate;
import java.util.Objects;

public class Edital {

    private final Long id;
    private final String titulo;
    private final int numero;
    private final int ano;
    private final LocalDate dataInicioSubmissao;
    private final LocalDate dataFimSubmissao;
    private final LocalDate dataInicioAvaliacao;
    private final LocalDate dataFimAvaliacao;

    public Edital(
            Long id,
            String titulo,
            int numero,
            int ano,
            LocalDate dataInicioSubmissao,
            LocalDate dataFimSubmissao,
            LocalDate dataInicioAvaliacao,
            LocalDate dataFimAvaliacao) {
        this.id = id;
        this.titulo = titulo;
        this.numero = numero;
        this.ano = ano;
        this.dataInicioSubmissao = dataInicioSubmissao;
        this.dataFimSubmissao = dataFimSubmissao;
        this.dataInicioAvaliacao = dataInicioAvaliacao;
        this.dataFimAvaliacao = dataFimAvaliacao;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getNumero() {
        return numero;
    }

    public int getAno() {
        return ano;
    }

    public LocalDate getDataInicioSubmissao() {
        return dataInicioSubmissao;
    }

    public LocalDate getDataFimSubmissao() {
        return dataFimSubmissao;
    }

    public LocalDate getDataInicioAvaliacao() {
        return dataInicioAvaliacao;
    }

    public LocalDate getDataFimAvaliacao() {
        return dataFimAvaliacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edital edital = (Edital) o;
        return Objects.equals(id, edital.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}