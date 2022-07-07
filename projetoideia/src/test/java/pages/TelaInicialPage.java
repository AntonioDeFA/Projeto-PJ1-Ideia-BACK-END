package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TelaInicialPage {

	@FindBy(id = "filled-search")
	private WebElement campoPesquisaNomeCompeticao;

	@FindBy(xpath = "//*[@id=\"minhas-competicoes\"]/div[2]/div[1]/div/div[3]/div[1]/form/div[4]/button")
	private WebElement botaoFiltrar;
	
	@FindBy(xpath = "//*[@id=\"aside-filtragem\"]/div/div[2]/button")
	private WebElement botaoCriarCompeticao;

	@FindBy(id = "op-convites-avaliador-cabecalho")
	private WebElement botaoConvitesAvaliador;
	
	@FindBy(id = "op-convites-consultor-cabecalho")
	private WebElement botaoConvitesConsultor;
	
	@FindBy(id = "op-trofeu-cabecalho")
	private WebElement botaoTrofeuCompeticao;
	
	@FindBy(xpath = "//*[@id=\"radio-buttons-competicoes\"]/fieldset/div/label[2]")
	private WebElement botaoMinhasCompeticoes;
	
	@FindBy(xpath = "/html/body/div/div/div[2]/div[2]/div/ul/li/div/div/div[2]/div[2]/div/i[1]")
	private WebElement botaoEntrarCompeticao;
	
	
	public void clicarNoBotaoEntrarNaCompeticao() {
		botaoEntrarCompeticao.click();
	}
	
	public void clicarNoBotaoMinhasCompeticoes() {
		botaoMinhasCompeticoes.click();
	}
	public void clicarNoBotaoFiltrar() {
		botaoFiltrar.click();
	}
	
	public void clicarNoBotaoCriarCompeticao() {
		botaoCriarCompeticao.click();
	}
	
	public void clicarNoBotaoConvitesAvaliador() {
		botaoConvitesAvaliador.click();
	}
	public void clicarNoBotaoConvitesConsultor() {
		botaoConvitesConsultor.click();
	}
	
	public void clicarNoBotaoTrofeu() {
		botaoTrofeuCompeticao.click();
	}

	public void inserirNoCampoPesquisaNomeCompeticao(String text) {
		campoPesquisaNomeCompeticao.sendKeys(text);
	}
	

}
