package stepsDefinitions;

import io.cucumber.java.pt.Quando;
import pages.EtapaPitchPage;

import static utils.Utils.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PitchStep {

	@Quando("passe a data de inicio da etapa pitch")
	public void passeADataDeInicioDaEtapaPitch() {
		LocalDate datainicio = LocalDate.now().plusDays(4);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		Na(EtapaPitchPage.class).inserirDataInicioPitch(datainicio.format(formatter));
	}

	@Quando("passe a data de termino da etapa pitch")
	public void passeADataDeTerminoDaEtapaPitch() {
		LocalDate datatermino = LocalDate.now().plusDays(4);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Na(EtapaPitchPage.class).inserirDataTerminoPitch(datatermino.format(formatter));
	}

	@Quando("clique no botao salvar etapa pitch")
	public void cliqueNoBotaoSalvarEtapaPitch() {
		Na(EtapaPitchPage.class).clicarNoBotaoSalvar();
	}

	@Quando("clique no botao salvar criacao competicao")
	public void cliqueNoBotaoSalvarCriacaoCompeticao() {
		Na(EtapaPitchPage.class).clicarNoBotaoFinalizarCriacaoCompeticao();
	}
}
