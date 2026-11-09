package br.edu.ifpe.repositorio;

import br.edu.ifpe.dominio.Edital;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public final class EditalRepositorioMemoria {

    private static final List<Edital> EDITAIS = new ArrayList<>();
    private static final AtomicLong SEQUENCIA = new AtomicLong(1);

    private EditalRepositorioMemoria() {
    }

    public static Edital salvar(Edital edital) {
        EDITAIS.add(edital);
        return edital;
    }

    public static Edital criarComId(Edital editalSemId) {
        Long id = SEQUENCIA.getAndIncrement();
        Edital edital = new Edital(
                id,
                editalSemId.getTitulo(),
                editalSemId.getNumero(),
                editalSemId.getAno(),
                editalSemId.getDataInicioSubmissao(),
                editalSemId.getDataFimSubmissao(),
                editalSemId.getDataInicioAvaliacao(),
                editalSemId.getDataFimAvaliacao()
        );
        return salvar(edital);
    }

    public static List<Edital> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(EDITAIS));
    }

    public static Edital atualizar(Edital editalAtualizado) {
        for (int i = 0; i < EDITAIS.size(); i++) {
            if (EDITAIS.get(i).getId().equals(editalAtualizado.getId())) {
                EDITAIS.set(i, editalAtualizado);
                return editalAtualizado;
            }
        }
        throw new IllegalArgumentException("Edital não encontrado para atualização.");
    }
}
