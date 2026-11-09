package br.edu.ifpe.dominio.excecao;

public class ValidacaoCadastroException extends RuntimeException {

    public ValidacaoCadastroException(String mensagem) {
        super(mensagem);
    }
}
