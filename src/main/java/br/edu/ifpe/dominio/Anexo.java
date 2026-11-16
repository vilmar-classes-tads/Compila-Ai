package br.edu.ifpe.dominio;

import java.util.Objects;

public class Anexo {
    private final Long id;
    private final String nomeArquivo;
    private final byte[] conteudo; // Simula o arquivo em memória

    public Anexo(Long id, String nomeArquivo, byte[] conteudo) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.conteudo = conteudo;
    }

    public Long getId() { return id; }
    public String getNomeArquivo() { return nomeArquivo; }
    public byte[] getConteudo() { return conteudo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anexo anexo = (Anexo) o;
        return Objects.equals(id, anexo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
