package br.edu.ifpe.dominio;

import java.util.List;
import java.util.Set;

public record DadosProjeto(
        String titulo,
        String resumo,
        List<String> palavrasChave,
        String publicoAlvo,
        String areaTematica,
        String campus,
        Set<Ods> ods,
        boolean aceiteTermoCompromisso) {
}
