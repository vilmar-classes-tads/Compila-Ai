package br.edu.ifpe.servico;

import br.edu.ifpe.dominio.DadosProjeto;
import br.edu.ifpe.dominio.Ods;
import br.edu.ifpe.dominio.Projeto;
import br.edu.ifpe.dominio.StatusProjeto;
import br.edu.ifpe.dominio.excecao.ProjetoNaoEditavelException;
import br.edu.ifpe.repositorio.ProjetoRepositorioMemoria;

import java.util.List;

public class SubmissaoProjetoServico {

    public Projeto salvarRascunho(DadosProjeto dados) {
        validarDadosBasicos(dados);
        return ProjetoRepositorioMemoria.criarComId(montar(null, dados, StatusProjeto.RASCUNHO));
    }

    public Projeto submeter(DadosProjeto dados) {
        validarDadosBasicos(dados);
        validarSubmissao(dados);
        return ProjetoRepositorioMemoria.criarComId(montar(null, dados, StatusProjeto.SUBMETIDO));
    }

    public Projeto editar(Long id, DadosProjeto dados) {
        Projeto existente = visualizar(id);
        garantirEditavel(existente);
        validarDadosBasicos(dados);

        StatusProjeto status = existente.getStatus();
        return ProjetoRepositorioMemoria.atualizar(montar(id, dados, status));
    }

    public Projeto submeterRascunho(Long id, DadosProjeto dados) {
        Projeto existente = visualizar(id);
        garantirEditavel(existente);
        validarDadosBasicos(dados);
        validarSubmissao(dados);
        return ProjetoRepositorioMemoria.atualizar(montar(id, dados, StatusProjeto.SUBMETIDO));
    }

    public Projeto visualizar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID do projeto é obrigatório.");
        }
        return ProjetoRepositorioMemoria.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado."));
    }

    public List<Projeto> listarTodos() {
        return ProjetoRepositorioMemoria.listarTodos();
    }

    private void garantirEditavel(Projeto projeto) {
        if (!projeto.isEditavel()) {
            throw new ProjetoNaoEditavelException();
        }
    }

    private void validarDadosBasicos(DadosProjeto dados) {
        if (isBlank(dados.titulo())
                || isBlank(dados.resumo())
                || dados.palavrasChave() == null || dados.palavrasChave().isEmpty()
                || isBlank(dados.publicoAlvo())
                || isBlank(dados.areaTematica())
                || isBlank(dados.campus())) {
            throw new IllegalArgumentException(
                    "Preencha os dados básicos: título, resumo, palavras-chave, público-alvo, área temática e campus.");
        }
    }

    private void validarSubmissao(DadosProjeto dados) {
        if (dados.ods() == null || dados.ods().isEmpty()) {
            throw new IllegalArgumentException("Selecione ao menos um ODS.");
        }
        for (Ods ods : dados.ods()) {
            if (ods == null) {
                throw new IllegalArgumentException("ODS inválido.");
            }
        }
        if (!dados.aceiteTermoCompromisso()) {
            throw new IllegalArgumentException("É necessário aceitar o Termo de Compromisso para submeter o projeto.");
        }
    }

    private Projeto montar(Long id, DadosProjeto dados, StatusProjeto status) {
        return new Projeto(
                id,
                dados.titulo().trim(),
                dados.resumo().trim(),
                dados.palavrasChave(),
                dados.publicoAlvo().trim(),
                dados.areaTematica().trim(),
                dados.campus().trim(),
                dados.ods(),
                dados.aceiteTermoCompromisso(),
                status);
    }

    private boolean isBlank(String valor) {
        return valor == null || valor.isBlank();
    }
}
