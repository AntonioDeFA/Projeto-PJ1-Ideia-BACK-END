package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TelaInicialPage {
	
	@FindBy(id = "filled-search")
	private WebElement campoPesquisaNomeCompeticao;
	
	@FindBy(xpath = "//*[@id=\"minhas-competicoes\"]/div[2]/div[1]/div/div[3]/div[1]/form/div[4]/button")
	private WebElement botaoFiltrar;
	
	
	
	
	public void clicarNoBotaoFiltrar() {
		botaoFiltrar.click();
	}
	
	public void inserirNoCampoPesquisaNomeCompeticao(String text) {
		campoPesquisaNomeCompeticao.sendKeys(text);
	}
	
	

}
