package br.edu.ifpe.servico;

import br.edu.ifpe.dominio.*;
import br.edu.ifpe.dominio.excecao.ProjetoNaoEditavelException;
import br.edu.ifpe.repositorio.ProjetoRepositorioMemoria;

import java.util.List;

public class SubmissaoProjetoServico {

    public Projeto salvarRascunho(DadosProjeto dados) {
        validarDadosBasicos(dados);
        return ProjetoRepositorioMemoria.criarComId(montar(null, dados, StatusProjeto.RASCUNHO, null));
    }

    public Projeto submeter(DadosProjeto dados) {
        validarDadosBasicos(dados);
        validarSubmissao(dados);
        return ProjetoRepositorioMemoria.criarComId(montar(null, dados, StatusProjeto.SUBMETIDO, null));
    }

    public Projeto editar(Long id, DadosProjeto dados) {
        Projeto existente = visualizar(id);
        garantirEditavel(existente);
        validarDadosBasicos(dados);
        return ProjetoRepositorioMemoria.atualizar(montar(id, dados, existente.getStatus(), existente.getAnexos()));
    }

    public Projeto visualizar(Long id) {
        return ProjetoRepositorioMemoria.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado."));
    }

    // REGRA NOVA: Admin vs Gestor
    public List<Projeto> listarProjetos(Usuario usuarioLogado, Long editalId, String area, StatusProjeto status) {
        if (usuarioLogado.getPerfis().contains(Perfil.ADMIN_GERAL)) {
            // Admin vê tudo. Filtros são opcionais (passados como parâmetro)
            return ProjetoRepositorioMemoria.buscarComFiltros(editalId, null, area, status);
        } 
        else if (usuarioLogado.getPerfis().contains(Perfil.GESTOR)) {
            // Gestor vê apenas do próprio campus
            return ProjetoRepositorioMemoria.buscarComFiltros(editalId, usuarioLogado.getCampus(), area, status);
        }
        else {
            throw new IllegalArgumentException("Usuário sem permissão para listar projetos.");
        }
    }

    // REGRA NOVA: Download sem ser o dono
    public void adicionarAnexo(Long projetoId, Anexo anexo) {
        ProjetoRepositorioMemoria.adicionarAnexo(projetoId, anexo);
    }

    public byte[] baixarAnexo(Long projetoId, Long anexoId) {
        Projeto projeto = visualizar(projetoId);
        // Regra: Qualquer usuário pode baixar, não há verificação de dono aqui.
        return projeto.getAnexos().stream()
                .filter(a -> a.getId().equals(anexoId))
                .findFirst()
                .map(Anexo::getConteudo)
                .orElseThrow(() -> new IllegalArgumentException("Anexo não encontrado."));
    }

    private void garantirEditavel(Projeto projeto) {
        if (!projeto.isEditavel()) {
            throw new ProjetoNaoEditavelException();
        }
    }

    private void validarDadosBasicos(DadosProjeto dados) {
        if (isBlank(dados.titulo()) || isBlank(dados.resumo()) || dados.palavrasChave() == null 
            || dados.palavrasChave().isEmpty() || isBlank(dados.publicoAlvo()) 
            || isBlank(dados.areaTematica()) || isBlank(dados.campus())) {
            throw new IllegalArgumentException("Preencha os dados básicos obrigatórios.");
        }
    }

    private void validarSubmissao(DadosProjeto dados) {
        if (dados.ods() == null || dados.ods().isEmpty()) throw new IllegalArgumentException("Selecione ao menos um ODS.");
        if (!dados.aceiteTermoCompromisso()) throw new IllegalArgumentException("É necessário aceitar o Termo.");
    }

    private Projeto montar(Long id, DadosProjeto dados, StatusProjeto status, List<Anexo> anexos) {
        return new Projeto(
                id, dados.titulo().trim(), dados.resumo().trim(), dados.palavrasChave(),
                dados.publicoAlvo().trim(), dados.areaTematica().trim(), dados.campus().trim(),
                dados.ods(), dados.aceiteTermoCompromisso(), status, anexos);
    }

    private boolean isBlank(String valor) { return valor == null || valor.isBlank(); }
}
