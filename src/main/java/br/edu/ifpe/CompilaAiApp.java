package br.edu.ifpe;

import br.edu.ifpe.dominio.DadosCadastroUsuario;
import br.edu.ifpe.dominio.DadosProjeto;
import br.edu.ifpe.dominio.Edital;
import br.edu.ifpe.dominio.Ods;
import br.edu.ifpe.dominio.Perfil;
import br.edu.ifpe.dominio.Projeto;
import br.edu.ifpe.dominio.Sexo;
import br.edu.ifpe.dominio.Usuario;
import br.edu.ifpe.dominio.excecao.ProjetoNaoEditavelException;
import br.edu.ifpe.repositorio.UsuarioRepositorioMemoria;
import br.edu.ifpe.servico.CadastroEditalServico;
import br.edu.ifpe.servico.CadastroUsuarioServico;
import br.edu.ifpe.servico.SubmissaoProjetoServico;
import br.edu.ifpe.util.SenhaUtil;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public final class CompilaAiApp {

    private CompilaAiApp() {
    }

    public static void main(String[] args) {
        CadastroUsuarioServico cadastroUsuario = new CadastroUsuarioServico();
        CadastroEditalServico cadastroEdital = new CadastroEditalServico();
        SubmissaoProjetoServico submissaoProjeto = new SubmissaoProjetoServico();

        System.out.println("Compila-Ai iniciado.");
        System.out.println();

        Usuario coordenador = cadastrarUsuario(cadastroUsuario);
        Edital edital = cadastrarEdital(cadastroEdital);
        Projeto rascunho = salvarEEditarRascunho(submissaoProjeto);
        Projeto submetido = submeterProjeto(submissaoProjeto);
        demonstrarBloqueioDeEdicao(submissaoProjeto, submetido);
        listarProjetosComoAdmin(submissaoProjeto, rascunho, submetido);

        System.out.println();
        System.out.println("Fluxo concluido: cadastro, edital e submissao disparados.");
        System.out.println("Edital de referencia: " + edital.getNumero() + "/" + edital.getAno());
        System.out.println("Coordenador: " + coordenador.getEmailInstitucional());
    }

    private static Usuario cadastrarUsuario(CadastroUsuarioServico servico) {
        DadosCadastroUsuario dados = new DadosCadastroUsuario(
                "Maria Silva",
                "123.456.789-01",
                "maria.silva@ifpe.edu.br",
                "segredo",
                "Campus Recife",
                "Ciencia da Computacao",
                "Mestrado",
                "Maria",
                Sexo.FEMININO,
                "http://lattes.cnpq.br/123",
                "81999999999");

        Usuario usuario = servico.cadastrar(dados);
        System.out.println("[Cadastro] Usuario #" + usuario.getId()
                + " - " + usuario.getNomeCompleto()
                + " | perfis: " + usuario.getPerfis());
        return usuario;
    }

    private static Edital cadastrarEdital(CadastroEditalServico servico) {
        Edital edital = servico.cadastrar(
                "Edital de Extensao 2026",
                1,
                2026,
                LocalDate.of(2026, 1, 10),
                LocalDate.of(2026, 3, 31),
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 5, 31));

        System.out.println("[Edital] #" + edital.getId()
                + " - " + edital.getTitulo()
                + " | submissao: " + edital.getDataInicioSubmissao()
                + " a " + edital.getDataFimSubmissao());
        servico.listarTodos().forEach(item ->
                System.out.println("  listado: " + item.getNumero() + "/" + item.getAno()));
        return edital;
    }

    private static Projeto salvarEEditarRascunho(SubmissaoProjetoServico servico) {
        DadosProjeto rascunhoDados = new DadosProjeto(
                "Oficinas de programacao na escola",
                "Projeto de extensao para ensino de logica de programacao.",
                List.of("educacao", "programacao"),
                "Estudantes do ensino medio",
                "Educacao",
                "Campus Recife",
                Set.of(Ods.ODS_4),
                false);

        Projeto rascunho = servico.salvarRascunho(rascunhoDados);
        System.out.println("[Projeto] Rascunho #" + rascunho.getId()
                + " - " + rascunho.getTitulo()
                + " | status: " + rascunho.getStatus());

        DadosProjeto rascunhoAtualizado = new DadosProjeto(
                "Oficinas de programacao na escola publica",
                rascunhoDados.resumo(),
                rascunhoDados.palavrasChave(),
                rascunhoDados.publicoAlvo(),
                rascunhoDados.areaTematica(),
                rascunhoDados.campus(),
                rascunhoDados.ods(),
                rascunhoDados.aceiteTermoCompromisso());

        Projeto editado = servico.editar(rascunho.getId(), rascunhoAtualizado);
        System.out.println("[Projeto] Rascunho editado: " + editado.getTitulo());
        return editado;
    }

    private static Projeto submeterProjeto(SubmissaoProjetoServico servico) {
        DadosProjeto dados = new DadosProjeto(
                "Horta comunitaria no campus",
                "Implantacao de horta educativa com a comunidade.",
                List.of("sustentabilidade", "horta"),
                "Comunidade do entorno",
                "Meio ambiente",
                "Campus Recife",
                Set.of(Ods.ODS_2, Ods.ODS_11),
                true);

        Projeto projeto = servico.submeter(dados);
        System.out.println("[Projeto] Submetido #" + projeto.getId()
                + " - " + projeto.getTitulo()
                + " | status: " + projeto.getStatus()
                + " | ODS: " + projeto.getOds());
        return projeto;
    }

    private static void demonstrarBloqueioDeEdicao(SubmissaoProjetoServico servico, Projeto submetido) {
        DadosProjeto tentativa = new DadosProjeto(
                "Titulo alterado apos submissao",
                "Resumo",
                List.of("teste"),
                "Publico",
                "Area",
                "Campus Recife",
                Set.of(Ods.ODS_4),
                true);
        try {
            servico.editar(submetido.getId(), tentativa);
            System.out.println("[Projeto] ERRO: edicao de submetido deveria ter sido bloqueada.");
        } catch (ProjetoNaoEditavelException e) {
            System.out.println("[Projeto] Edicao bloqueada apos submissao: " + e.getMessage());
        }
    }

    private static void listarProjetosComoAdmin(
            SubmissaoProjetoServico servico,
            Projeto rascunho,
            Projeto submetido) {
        Usuario admin = UsuarioRepositorioMemoria.criarComId(new Usuario(
                null,
                "Admin Geral",
                "11122233344",
                "admin@ifpe.edu.br",
                SenhaUtil.hash("admin123"),
                "Campus Recife",
                "Gestao",
                "Especializacao",
                null,
                Sexo.OUTRO,
                null,
                null,
                EnumSet.of(Perfil.ADMIN_GERAL)));

        System.out.println("[Admin] Listagem de projetos (" + admin.getNomeCompleto() + "):");
        servico.listarProjetos(admin, null, null, null).forEach(projeto ->
                System.out.println("  #" + projeto.getId()
                        + " " + projeto.getTitulo()
                        + " [" + projeto.getStatus() + "]"));
        System.out.println("  rascunho visivel: #" + rascunho.getId()
                + " | submetido visivel: #" + submetido.getId());
        servico.visualizar(submetido.getId());
        System.out.println("[Projeto] Visualizacao do submetido permitida (somente leitura).");
    }
}
