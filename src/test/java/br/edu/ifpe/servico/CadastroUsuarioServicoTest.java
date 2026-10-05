package br.edu.ifpe.servico;

import br.edu.ifpe.dominio.DadosCadastroUsuario;
import br.edu.ifpe.dominio.Perfil;
import br.edu.ifpe.dominio.Sexo;
import br.edu.ifpe.dominio.Usuario;
import br.edu.ifpe.dominio.excecao.CpfJaCadastradoException;
import br.edu.ifpe.dominio.excecao.EmailJaCadastradoException;
import br.edu.ifpe.dominio.excecao.ValidacaoCadastroException;
import br.edu.ifpe.repositorio.UsuarioRepositorioMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CadastroUsuarioServicoTest {

    private CadastroUsuarioServico servico;

    @BeforeEach
    void setUp() {
        UsuarioRepositorioMemoria.limpar();
        servico = new CadastroUsuarioServico();
    }

    @Test
    void deveCadastrarUsuarioComPerfisPadraoESenhaHash() {
        DadosCadastroUsuario dados = dadosValidos();

        Usuario usuario = servico.cadastrar(dados);

        assertEquals(1L, usuario.getId());
        assertEquals("12345678901", usuario.getCpf());
        assertEquals("maria@ifpe.edu.br", usuario.getEmailInstitucional());
        assertTrue(usuario.getPerfis().contains(Perfil.COORDENADOR));
        assertTrue(usuario.getPerfis().contains(Perfil.AVALIADOR));
        assertNotEquals("segredo", usuario.getSenhaHash());
    }

    @Test
    void naoDevePermitirCpfDuplicado() {
        servico.cadastrar(dadosValidos());

        DadosCadastroUsuario outro = new DadosCadastroUsuario(
                "Outro Nome",
                "123.456.789-01",
                "outro@ifpe.edu.br",
                "segredo",
                "Campus",
                "Computação",
                "Doutorado",
                null,
                null,
                null,
                null);

        assertThrows(CpfJaCadastradoException.class, () -> servico.cadastrar(outro));
    }

    @Test
    void naoDevePermitirEmailDuplicado() {
        servico.cadastrar(dadosValidos());

        DadosCadastroUsuario outro = new DadosCadastroUsuario(
                "Outro Nome",
                "98765432100",
                "Maria@ifpe.edu.br",
                "segredo",
                "Campus",
                "Computação",
                "Doutorado",
                null,
                null,
                null,
                null);

        assertThrows(EmailJaCadastradoException.class, () -> servico.cadastrar(outro));
    }

    @Test
    void deveRejeitarSenhaCurta() {
        DadosCadastroUsuario dados = new DadosCadastroUsuario(
                "Maria Silva",
                "12345678901",
                "maria@ifpe.edu.br",
                "123",
                "Campus",
                "Computação",
                "Doutorado",
                null,
                null,
                null,
                null);

        assertThrows(ValidacaoCadastroException.class, () -> servico.cadastrar(dados));
    }

    private DadosCadastroUsuario dadosValidos() {
        return new DadosCadastroUsuario(
                "Maria Silva",
                "123.456.789-01",
                "maria@ifpe.edu.br",
                "segredo",
                "Campus Recife",
                "Ciência da Computação",
                "Mestrado",
                "Maria",
                Sexo.FEMININO,
                "http://lattes.cnpq.br/123",
                "81999999999");
    }
}
