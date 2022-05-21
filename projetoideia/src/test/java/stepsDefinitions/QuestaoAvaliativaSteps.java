package stepsDefinitions;

import io.cucumber.java.pt.Quando;
import pages.QuestoesAvaliativasPage;

import static utils.Utils.*;

public class QuestaoAvaliativaSteps {

	@Quando("clicar no botao adicionar questao avaliativa")
	public void clicarNoBotaoAdicionarQuestaoAvaliativa() {
		Na(QuestoesAvaliativasPage.class).clicarNoBotaoAdicionarQuestao();
	}

	@Quando("passe o valor de {string} para a nota maxima")
	public void passeOValorDeParaANotaMaxima(String string) {
		Na(QuestoesAvaliativasPage.class).inserirQuantidadeMaximaPontos(string);
	}

	@Quando("passe o valor de {string}")
	public void passeOValorDe(String string) {
		Na(QuestoesAvaliativasPage.class).inserirQuestao(string);
	}

	@Quando("clique no botao salvar questao")
	public void cliqueNoBotaoSalvarQuestao() {
		Na(QuestoesAvaliativasPage.class).clicarNoBotaoSalvarQuestao();
	}

	@Quando("clique no botao salvar")
	public void cliqueNoBotaoSalvar() {
		Na(QuestoesAvaliativasPage.class).clicarNoBotaoSalvar();
	}

}
