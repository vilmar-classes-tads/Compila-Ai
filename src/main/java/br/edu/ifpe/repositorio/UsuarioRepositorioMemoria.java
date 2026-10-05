package br.edu.ifpe.repositorio;

import br.edu.ifpe.dominio.Usuario;
import br.edu.ifpe.util.CpfUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public final class UsuarioRepositorioMemoria {

    private static final Map<String, Usuario> POR_CPF = new HashMap<>();
    private static final Map<String, Usuario> POR_EMAIL = new HashMap<>();
    private static final AtomicLong SEQUENCIA = new AtomicLong(1);

    private UsuarioRepositorioMemoria() {
    }

    public static Usuario salvar(Usuario usuario) {
        POR_CPF.put(CpfUtil.normalizar(usuario.getCpf()), usuario);
        POR_EMAIL.put(normalizarEmail(usuario.getEmailInstitucional()), usuario);
        return usuario;
    }

    public static Usuario criarComId(Usuario usuarioSemId) {
        Long id = SEQUENCIA.getAndIncrement();
        Usuario usuario = new Usuario(
                id,
                usuarioSemId.getNomeCompleto(),
                usuarioSemId.getCpf(),
                usuarioSemId.getEmailInstitucional(),
                usuarioSemId.getSenhaHash(),
                usuarioSemId.getCampus(),
                usuarioSemId.getAreaFormacao(),
                usuarioSemId.getTitulacao(),
                usuarioSemId.getNomeSocial(),
                usuarioSemId.getSexo(),
                usuarioSemId.getLinkLattes(),
                usuarioSemId.getTelefone(),
                usuarioSemId.getPerfis());
        return salvar(usuario);
    }

    public static Optional<Usuario> buscarPorCpf(String cpf) {
        return Optional.ofNullable(POR_CPF.get(CpfUtil.normalizar(cpf)));
    }

    public static Optional<Usuario> buscarPorEmail(String email) {
        return Optional.ofNullable(POR_EMAIL.get(normalizarEmail(email)));
    }

    public static List<Usuario> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(POR_CPF.values()));
    }

    private static String normalizarEmail(String email) {
        if (email == null) {
            return "";
        }
        return email.trim().toLowerCase();
    }
}
