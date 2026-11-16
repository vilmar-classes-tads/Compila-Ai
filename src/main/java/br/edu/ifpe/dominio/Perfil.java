package br.edu.ifpe.dominio;

public enum Perfil {
    ADMIN_GERAL("ROLE_ADMIN_GERAL"),
    GESTOR("ROLE_GESTOR"),
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
