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

	@FindBy(xpath = "/html/body/div/div/div[1]/div/div/ul/li[2]/a/img")
	private WebElement botaoConvitesAvaliador;
	
	@FindBy(xpath = "/html/body/div/div/div[1]/div/div/ul/li[4]/a/img")
	private WebElement botaoConvitesConsultor;
	
	@FindBy(xpath = "/html/body/div/div/div[1]/div/div/ul/li[1]/a/img")
	private WebElement botaoTrofeuCompeticao;
	
	
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
