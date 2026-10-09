package br.edu.ifpe.dominio;

public record DadosCadastroUsuario(
        String nomeCompleto,
        String cpf,
        String emailInstitucional,
        String senha,
        String campus,
        String areaFormacao,
        String titulacao,
        String nomeSocial,
        Sexo sexo,
        String linkLattes,
        String telefone) {
}
