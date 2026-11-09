package br.edu.ifpe.servico;

import br.edu.ifpe.dominio.Edital;
import br.edu.ifpe.repositorio.EditalRepositorioMemoria;

import java.time.LocalDate;
import java.util.List;

public class CadastroEditalServico {

    public Edital cadastrar(
            String titulo,
            int numero,
            int ano,
            LocalDate dataInicioSubmissao,
            LocalDate dataFimSubmissao,
            LocalDate dataInicioAvaliacao,
            LocalDate dataFimAvaliacao) {

        validarCampos(titulo, numero, ano, dataInicioSubmissao, dataFimSubmissao, dataInicioAvaliacao, dataFimAvaliacao);

        Edital edital = new Edital(
                null,
                titulo.trim(),
                numero,
                ano,
                dataInicioSubmissao,
                dataFimSubmissao,
                dataInicioAvaliacao,
                dataFimAvaliacao
        );

        return EditalRepositorioMemoria.criarComId(edital);
    }

    public List<Edital> listarTodos() {
        return EditalRepositorioMemoria.listarTodos();
    }

    public Edital editar(
            Long id,
            String titulo,
            int numero,
            int ano,
            LocalDate dataInicioSubmissao,
            LocalDate dataFimSubmissao,
            LocalDate dataInicioAvaliacao,
            LocalDate dataFimAvaliacao) {

        if (id == null) {
            throw new IllegalArgumentException("O ID é obrigatório para edição.");
        }

        validarCampos(titulo, numero, ano, dataInicioSubmissao, dataFimSubmissao, dataInicioAvaliacao, dataFimAvaliacao);

        Edital editalAtualizado = new Edital(
                id,
                titulo.trim(),
                numero,
                ano,
                dataInicioSubmissao,
                dataFimSubmissao,
                dataInicioAvaliacao,
                dataFimAvaliacao
        );

        return EditalRepositorioMemoria.atualizar(editalAtualizado);
    }

    private void validarCampos(
            String titulo,
            int numero,
            int ano,
            LocalDate inicioSub,
            LocalDate fimSub,
            LocalDate inicioAva,
            LocalDate fimAva) {

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título é obrigatório.");
        }

        if (numero <= 0 || ano <= 0) {
            throw new IllegalArgumentException("Número e ano devem ser positivos.");
        }

        if (inicioSub == null || fimSub == null || inicioAva == null || fimAva == null) {
            throw new IllegalArgumentException("Todas as datas são obrigatórias.");
        }

        if (inicioSub.isAfter(fimSub)) {
            throw new IllegalArgumentException("Início da submissão posterior ao fim da submissão.");
        }

        if (inicioAva.isAfter(fimAva)) {
            throw new IllegalArgumentException("Início da avaliação posterior ao fim da avaliação.");
        }
    }
}
