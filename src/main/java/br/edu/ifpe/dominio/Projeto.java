package br.edu.ifpe.dominio;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Projeto {

    private final Long id;
    private final String titulo;
    private final String resumo;
    private final List<String> palavrasChave;
    private final String publicoAlvo;
    private final String areaTematica;
    private final String campus;
    private final Set<Ods> ods;
    private final boolean aceiteTermoCompromisso;
    private final StatusProjeto status;

    public Projeto(
            Long id,
            String titulo,
            String resumo,
            List<String> palavrasChave,
            String publicoAlvo,
            String areaTematica,
            String campus,
            Set<Ods> ods,
            boolean aceiteTermoCompromisso,
            StatusProjeto status) {
        this.id = id;
        this.titulo = titulo;
        this.resumo = resumo;
        this.palavrasChave = palavrasChave == null ? List.of() : List.copyOf(palavrasChave);
        this.publicoAlvo = publicoAlvo;
        this.areaTematica = areaTematica;
        this.campus = campus;
        this.ods = ods == null ? Set.of() : Set.copyOf(ods);
        this.aceiteTermoCompromisso = aceiteTermoCompromisso;
        this.status = status;
    }

    public boolean isEditavel() {
        return status == StatusProjeto.RASCUNHO || status == StatusProjeto.CORRECAO;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public List<String> getPalavrasChave() {
        return Collections.unmodifiableList(palavrasChave);
    }

    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    public String getAreaTematica() {
        return areaTematica;
    }

    public String getCampus() {
        return campus;
    }

    public Set<Ods> getOds() {
        return Collections.unmodifiableSet(ods);
    }

    public boolean isAceiteTermoCompromisso() {
        return aceiteTermoCompromisso;
    }

    public StatusProjeto getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Projeto projeto = (Projeto) o;
        return Objects.equals(id, projeto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
