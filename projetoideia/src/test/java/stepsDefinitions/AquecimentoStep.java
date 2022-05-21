package stepsDefinitions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static utils.Utils.*;

import io.cucumber.java.pt.Quando;
import pages.EtapaAquecimentoPage;

public class AquecimentoStep {

	@Quando("passe a data de inicio da etapa aquecimento")
	public void passeADataDeInicioDaEtapaAquecimento() {
		LocalDate datainicio = LocalDate.now().plusDays(2);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		Na(EtapaAquecimentoPage.class).inserirDataInicio(datainicio.format(formatter));
		
	}

	@Quando("passe a data de termino da etapa aquecimento")
	public void passeADataDeTerminoDaEtapaAquecimento() {
		LocalDate datatermino = LocalDate.now().plusDays(2);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Na(EtapaAquecimentoPage.class).inserirDataTermino(datatermino.format(formatter));
	}

	@Quando("passe o link {string}")
	public void passeOLink(String string) {
		Na(EtapaAquecimentoPage.class).inserirLink(string);
		
		
	}

	@Quando("clique no botao de cadastrar link")
	public void cliqueNoBotaoDeCadastrarLink() {
		Na(EtapaAquecimentoPage.class).clicarNoBotaoAdicionarLink();
	}

	@Quando("clique no botao salvar etapa aquecimento")
	public void cliqueNoBotaoSalvarEtapaAquecimento() {
		Na(EtapaAquecimentoPage.class).clicarNoBotaoSalvarEtapaAquecimento();
	}

}
