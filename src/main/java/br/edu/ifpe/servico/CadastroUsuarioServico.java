package br.edu.ifpe.servico;

import br.edu.ifpe.dominio.DadosCadastroUsuario;
import br.edu.ifpe.dominio.Perfil;
import br.edu.ifpe.dominio.Usuario;
import br.edu.ifpe.dominio.excecao.CpfJaCadastradoException;
import br.edu.ifpe.dominio.excecao.EmailJaCadastradoException;
import br.edu.ifpe.dominio.excecao.ValidacaoCadastroException;
import br.edu.ifpe.repositorio.UsuarioRepositorioMemoria;
import br.edu.ifpe.util.CpfUtil;
import br.edu.ifpe.util.SenhaUtil;

import java.util.EnumSet;
import java.util.Set;

public class CadastroUsuarioServico {

    private static final int TAMANHO_MINIMO_SENHA = 6;
    private static final Set<Perfil> PERFIS_PADRAO =
            EnumSet.of(Perfil.COORDENADOR, Perfil.AVALIADOR);

    public Usuario cadastrar(DadosCadastroUsuario dados) {
        validarCamposObrigatorios(dados);
        validarSenha(dados.senha());
        validarUnicidade(dados.cpf(), dados.emailInstitucional());

        String senhaHash = SenhaUtil.hash(dados.senha());
        Usuario usuario = new Usuario(
                null,
                dados.nomeCompleto().trim(),
                CpfUtil.normalizar(dados.cpf()),
                dados.emailInstitucional().trim().toLowerCase(),
                senhaHash,
                dados.campus().trim(),
                dados.areaFormacao().trim(),
                dados.titulacao().trim(),
                dados.nomeSocial() != null ? dados.nomeSocial().trim() : null,
                dados.sexo(),
                dados.linkLattes() != null ? dados.linkLattes().trim() : null,
                dados.telefone() != null ? dados.telefone().trim() : null,
                PERFIS_PADRAO);

        return UsuarioRepositorioMemoria.criarComId(usuario);
    }

    private void validarCamposObrigatorios(DadosCadastroUsuario dados) {
        if (isBlank(dados.nomeCompleto())
                || isBlank(dados.cpf())
                || isBlank(dados.emailInstitucional())
                || isBlank(dados.senha())
                || isBlank(dados.campus())
                || isBlank(dados.areaFormacao())
                || isBlank(dados.titulacao())) {
            throw new ValidacaoCadastroException(
                    "Preencha todos os campos obrigatórios do cadastro.");
        }
    }

    private void validarSenha(String senha) {
        if (senha.length() < TAMANHO_MINIMO_SENHA) {
            throw new ValidacaoCadastroException(
                    "A senha deve ter no mínimo " + TAMANHO_MINIMO_SENHA + " caracteres.");
        }
    }

    private void validarUnicidade(String cpf, String email) {
        if (UsuarioRepositorioMemoria.buscarPorCpf(cpf).isPresent()) {
            throw new CpfJaCadastradoException(cpf);
        }
        if (UsuarioRepositorioMemoria.buscarPorEmail(email).isPresent()) {
            throw new EmailJaCadastradoException(email);
        }
    }

    private boolean isBlank(String valor) {
        return valor == null || valor.isBlank();
    }
}
