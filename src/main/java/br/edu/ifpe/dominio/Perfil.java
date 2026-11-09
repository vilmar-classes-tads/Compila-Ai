package br.edu.ifpe.dominio;

public enum Perfil {
    COORDENADOR("ROLE_COORDENADOR"),
    AVALIADOR("ROLE_AVALIADOR");

    private final String role;

    Perfil(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
