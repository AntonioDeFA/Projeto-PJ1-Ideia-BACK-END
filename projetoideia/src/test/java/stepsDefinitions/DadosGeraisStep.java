package stepsDefinitions;

import static utils.Utils.Na;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import pages.DadosGeraisPage;
import pages.TelaInicialPage;

public class DadosGeraisStep {

	@Dado("clique no botao criar competicao")
	public void cliqueNoBotaoCriarCompeticao() {
		Na(TelaInicialPage.class).clicarNoBotaoCriarCompeticao();

	}

	@Dado("que eu passe a data de hoje para o inicio da etapa de inscricao")
	public void queEuPasseADataDeHojeParaOInicioDaEtapaDeInscricao() {
		LocalDate datainicio = LocalDate.now().plusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		Na(DadosGeraisPage.class).inserirNaDataInicioInscricoes(datainicio.format(formatter));
	}

	@Dado("passe a data de amanha para o termino das incricoes")
	public void passeADataDeAmanhaParaOTerminoDasIncricoes() {
		LocalDate datatermino = LocalDate.now().plusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Na(DadosGeraisPage.class).inserirNaDataTerminoInscricoes(datatermino.format(formatter));
	}

	@Dado("passe o nome da competicao {string}")
	public void passeONomeDaCompeticao(String string) {
		Na(DadosGeraisPage.class).inserirNomeCompeticao(string);
	}

	@Dado("passe a quantidade minima de membros {string}")
	public void passeAQuantidadeMinimaDeMembros(String string) {
		Na(DadosGeraisPage.class).inserirQuantidadeMinima(string);
	}

	@Dado("passe a quantidade maxima de membros {string}")
	public void passeAQuantidadeMaximaDeMembros(String string) {
		Na(DadosGeraisPage.class).inserirQuantidadeMaxima(string);
	}

	@Dado("clique no botao regulamento da competicao")
	public void cliqueNoBotaoRegulamentoDaCompeticao() throws InterruptedException {

		Thread.sleep(10000);
	}

	@Dado("passe o tempo maximo de pitch {string}")
	public void passeOTempoMaximoDePitch(String string) {
		Na(DadosGeraisPage.class).inserirTempoMaximoPitch(string);
	}

	@Quando("eu clicar no botao salvar dados gerais da competicao")
	public void euClicarNoBotaoSalvarDadosGeraisDaCompeticao() throws InterruptedException {
		Na(DadosGeraisPage.class).clicarNoBotaoSalvar();
		Thread.sleep(5000);
	}

}
