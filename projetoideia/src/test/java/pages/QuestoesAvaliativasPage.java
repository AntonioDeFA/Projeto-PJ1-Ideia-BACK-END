package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class QuestoesAvaliativasPage {

	
	
	@FindBy(id = "btn-adicionar-questao-avaliativa")
	private WebElement adicionarQuestao;
	
	@FindBy(id = "input-quantidade-max-pontos")
	private WebElement quantidadeMaximaDePontos;
	
	@FindBy(id = "textarea-questao-avaliativa")
	private WebElement questaoAvaliativaTexto;
	
	@FindBy(id = "btn-salvar-questao-avaliativa")
	private WebElement salvarQuestaoAvaliativa;
	
	@FindBy(id = "btn-confirmar-questoes-avaliativas")
	private WebElement salvar;
	
	
	
	public void inserirQuestao(String value) {
		questaoAvaliativaTexto.sendKeys(value);
	}
	
	public void inserirQuantidadeMaximaPontos(String value) {
		quantidadeMaximaDePontos.sendKeys(value);
	}
	
	public void clicarNoBotaoAdicionarQuestao() {
		adicionarQuestao.click();
	}
	
	public void clicarNoBotaoSalvarQuestao(){
		salvarQuestaoAvaliativa.click();
	}
	
	public void clicarNoBotaoSalvar() {
		salvar.click();
	}
}
