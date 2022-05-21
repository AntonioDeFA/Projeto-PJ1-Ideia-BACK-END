package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EtapaImersaoPage {
	
	@FindBy(xpath = "/html/body/div/div/div[2]/div/div[4]/div/div[2]/div/div/div/div/div/div[3]/button")
	private WebElement salvar;
	
	@FindBy(id = "input-data-inicio-imersao")
	private WebElement dataInicio;
	
	@FindBy(xpath = "/html/body/div/div/div[2]/div/div[4]/div/div[2]/div/div/div/div/div/form/div/div[2]/div/div/input")
	private WebElement dataTermino;
	
	private WebElement 
	
	public void inserirNaDataInicioImersao(String value) {
		dataInicio.sendKeys(value);
	}
	
	public void inserirNaDataTermino(String value) {
		dataTermino.sendKeys(value);
	}
	
	public void clicarNoBotaoSavarEtapaImersao() {
		salvar.click();
	}

}
