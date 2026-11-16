package br.edu.ifpe.dominio.excecao;

public class ProjetoNaoEditavelException extends RuntimeException {

    public ProjetoNaoEditavelException() {
        super("Projeto já submetido. Apenas visualização é permitida, exceto em status de correção.");
    }
}
