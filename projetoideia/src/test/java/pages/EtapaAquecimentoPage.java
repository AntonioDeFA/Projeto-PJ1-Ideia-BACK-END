package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EtapaAquecimentoPage {
	
	@FindBy(id = "input-data-inicio-Aquecimento")
	private WebElement dataInicio;
	
	
	@FindBy(id = "input-data-termino-Aquecimento")
	private WebElement dataTermino;
	
	@FindBy(xpath = "/html/body/div/div/div[2]/div/div[3]/div/div[2]/div/div/div/div/div/div[2]/div[1]/div/div/input")
	private WebElement link;
	
	@FindBy(xpath = "/html/body/div/div/div[2]/div/div[3]/div/div[2]/div/div/div/div/div/div[2]/div[1]/span")
	private WebElement adicionarLink;

	@FindBy(id = "btn-salvar-dados-etapa-aquecimento")
	private WebElement salvar;
	
	
	public void inserirDataInicio(String value) {
		dataInicio.sendKeys(value);	
	}
	
	public void inserirDataTermino(String value) {
		dataTermino.sendKeys(value);
	}
	
	public void inserirLink(String value) {
		link.sendKeys(value);
	}
	
	public void clicarNoBotaoAdicionarLink() {
		adicionarLink.click();
	}
	
	public void clicarNoBotaoSalvarEtapaAquecimento() {
		salvar.click();
	}
}
