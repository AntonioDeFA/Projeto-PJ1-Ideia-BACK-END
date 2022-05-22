package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EtapaPitchPage {
	
	@FindBy(id = "input-data-inicio-pitch")
	private WebElement dataInicio;
	
	@FindBy(id = "input-data-termino-pitch")
	private WebElement dataTermino;
	
	@FindBy(id = "btn-salvar-dados-etapa-pitch")
	private WebElement salvar;
	
	@FindBy(id = "btn-confirmar-competicao-para-teste")
	private WebElement finalizarCriacaoCompeticao;	
	
	public void inserirDataInicioPitch(String value) {
		dataInicio.sendKeys(value);
	}
	
	public void inserirDataTerminoPitch(String value) {
		dataTermino.sendKeys(value);
	}
	
	public void clicarNoBotaoSalvar() {
		salvar.click();
	}
	
	public void clicarNoBotaoFinalizarCriacaoCompeticao() {
		finalizarCriacaoCompeticao.click();
	}
}

