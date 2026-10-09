package br.edu.ifpe.dominio;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class Usuario {

    private final Long id;
    private final String nomeCompleto;
    private final String cpf;
    private final String emailInstitucional;
    private final String senhaHash;
    private final String campus;
    private final String areaFormacao;
    private final String titulacao;
    private final String nomeSocial;
    private final Sexo sexo;
    private final String linkLattes;
    private final String telefone;
    private final Set<Perfil> perfis;

    public Usuario(
            Long id,
            String nomeCompleto,
            String cpf,
            String emailInstitucional,
            String senhaHash,
            String campus,
            String areaFormacao,
            String titulacao,
            String nomeSocial,
            Sexo sexo,
            String linkLattes,
            String telefone,
            Set<Perfil> perfis) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.emailInstitucional = emailInstitucional;
        this.senhaHash = senhaHash;
        this.campus = campus;
        this.areaFormacao = areaFormacao;
        this.titulacao = titulacao;
        this.nomeSocial = nomeSocial;
        this.sexo = sexo;
        this.linkLattes = linkLattes;
        this.telefone = telefone;
        this.perfis = Set.copyOf(perfis);
    }

    public Long getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmailInstitucional() {
        return emailInstitucional;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public String getCampus() {
        return campus;
    }

    public String getAreaFormacao() {
        return areaFormacao;
    }

    public String getTitulacao() {
        return titulacao;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public String getLinkLattes() {
        return linkLattes;
    }

    public String getTelefone() {
        return telefone;
    }

    public Set<Perfil> getPerfis() {
        return Collections.unmodifiableSet(perfis);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
