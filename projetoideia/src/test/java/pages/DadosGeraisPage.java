package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DadosGeraisPage {
	
	@FindBy(id = "input-data-inicio-inscricoes")
	private WebElement dataInicioInscricoes;
	
	@FindBy(id = "input-data-termino-inscricoes")
	private WebElement dataTerminoInscricoes;
	
	@FindBy(id = "input-nome-competicao")
	private WebElement nomeCompeticao;

	@FindBy(id = "input-data-qntd-min-membros")
	private WebElement quantidadeMinimaMembros;
	
	@FindBy(id = "input-data-qntd-max-membros")
	private WebElement quantidadeMaximaMembros;
	
	@FindBy(xpath = "/html/body/div/div/div[2]/div/div[1]/div/div[2]/div/div/div/div/div/form/div[5]/label/input")
	private WebElement regulamentoCompeticao;
	
	@FindBy(xpath = "/html/body/div/div/div[2]/div/div[1]/div/div[2]/div/div/div/div/div/form/div[6]/div/div/input")
	private WebElement tempoMaximoPitch;
	
	@FindBy(id = "btn-salvar-dados-gerais-competicao")
	private WebElement botaoSalvar;
	
	public void inserirNaDataInicioInscricoes(String value) {
		dataInicioInscricoes.sendKeys(value);
	}
	
	public void inserirNaDataTerminoInscricoes(String value) {
		dataTerminoInscricoes.sendKeys(value);
	}
	
	public void inserirNomeCompeticao(String value) {
		nomeCompeticao.sendKeys(value);
	}
	
	public void inserirQuantidadeMinima(String value) {
		quantidadeMinimaMembros.sendKeys(value);
	}
	
	public void inserirQuantidadeMaxima(String value) {
		quantidadeMaximaMembros.sendKeys(value);
	}
	
	public void inserirTempoMaximoPitch(String value) {
		tempoMaximoPitch.sendKeys(value);
	}
	
	public void clicarNoBotaoRegulamentoCompeticao() {
		regulamentoCompeticao.click();
	}
	
	public void clicarNoBotaoSalvar() {
		botaoSalvar.click();
	}
}
