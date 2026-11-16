package br.edu.ifpe.repositorio;

import br.edu.ifpe.dominio.Projeto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public final class ProjetoRepositorioMemoria {

    private static final List<Projeto> PROJETOS = new ArrayList<>();
    private static final AtomicLong SEQUENCIA = new AtomicLong(1);

    private ProjetoRepositorioMemoria() {
    }

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
        return PROJETOS.stream()
                .filter(projeto -> projeto.getId().equals(id))
                .findFirst();
    }

    public static List<Projeto> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(PROJETOS));
    }

    private static Projeto copiarComId(Long id, Projeto origem) {
        return new Projeto(
                id,
                origem.getTitulo(),
                origem.getResumo(),
                origem.getPalavrasChave(),
                origem.getPublicoAlvo(),
                origem.getAreaTematica(),
                origem.getCampus(),
                origem.getOds(),
                origem.isAceiteTermoCompromisso(),
                origem.getStatus());
    }
}
