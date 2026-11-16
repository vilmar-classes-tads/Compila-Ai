package br.edu.ifpe.repositorio;

import br.edu.ifpe.dominio.Anexo;
import br.edu.ifpe.dominio.Projeto;
import br.edu.ifpe.dominio.StatusProjeto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public final class ProjetoRepositorioMemoria {

    private static final List<Projeto> PROJETOS = new ArrayList<>();
    private static final AtomicLong SEQUENCIA = new AtomicLong(1);

    private ProjetoRepositorioMemoria() {}

    public static Projeto criarComId(Projeto projetoSemId) {
        Long id = SEQUENCIA.getAndIncrement();
        Projeto projeto = copiarComId(id, projetoSemId);
        PROJETOS.add(projeto);
        return projeto;
    }

    public static Projeto atualizar(Projeto projetoAtualizado) {
        for (int i = 0; i < PROJETOS.size(); i++) {
            if (PROJETOS.get(i).getId().equals(projetoAtualizado.getId())) {
                PROJETOS.set(i, projetoAtualizado);
                return projetoAtualizado;
            }
        }
        throw new IllegalArgumentException("Projeto não encontrado para atualização.");
    }

    public static Optional<Projeto> buscarPorId(Long id) {
        return PROJETOS.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public static List<Projeto> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(PROJETOS));
    }

    // NOVO MÉTODO: Filtros combinados
    public static List<Projeto> buscarComFiltros(Long editalId, String campus, String area, StatusProjeto status) {
        return PROJETOS.stream()
                // Nota: Como o Projeto não tem editalId na sua estrutura atual, o filtro de edital foi omitido aqui.
                // Se você adicionar o campo editalId no Projeto, basta descomentar a linha abaixo:
                // .filter(p -> editalId == null || p.getEditalId().equals(editalId))
                .filter(p -> campus == null || campus.isBlank() || p.getCampus().equalsIgnoreCase(campus))
                .filter(p -> area == null || area.isBlank() || p.getAreaTematica().equalsIgnoreCase(area))
                .filter(p -> status == null || p.getStatus() == status)
                .collect(Collectors.toList());
    }

    // NOVO MÉTODO: Adicionar anexo
    public static Projeto adicionarAnexo(Long projetoId, Anexo anexo) {
        Projeto projeto = buscarPorId(projetoId).orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado."));
        List<Anexo> novosAnexos = new ArrayList<>(projeto.getAnexos());
        novosAnexos.add(anexo);
        
        Projeto projetoAtualizado = new Projeto(
            projeto.getId(), projeto.getTitulo(), projeto.getResumo(), projeto.getPalavrasChave(),
            projeto.getPublicoAlvo(), projeto.getAreaTematica(), projeto.getCampus(),
            projeto.getOds(), projeto.isAceiteTermoCompromisso(), projeto.getStatus(), novosAnexos
        );
        return atualizar(projetoAtualizado);
    }

    private static Projeto copiarComId(Long id, Projeto origem) {
        return new Projeto(
                id, origem.getTitulo(), origem.getResumo(), origem.getPalavrasChave(),
                origem.getPublicoAlvo(), origem.getAreaTematica(), origem.getCampus(),
                origem.getOds(), origem.isAceiteTermoCompromisso(), origem.getStatus(), origem.getAnexos());
    }
}
