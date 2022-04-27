package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CriarEquipePage {
	
	@FindBy(id = "filled-search-nomeEquipe")
	private WebElement campoNomeEquipe;
	
	@FindBy(id = "btn-confirmar-para-teste")
	private WebElement botaoConfirmar;
	
	@FindBy(id = "btn-add-membro-para-teste")
	private WebElement botaoAdicionarMembro;
	
	@FindBy(id = "filled-search-email")
	private WebElement campoEmailDoMembro;
	
	@FindBy(id = "filled-search-nome-membro")
	private WebElement campoNomeDoMembro;
	
	@FindBy(id = "btn-adicionar-membro")
	private WebElement botaoCadastrarMembro;
	
	
	
	
	public void clicarNoBotaoConfirmar() {
		botaoConfirmar.click();
	}
	
	public void clicarNoBotaoAdicionarMembro() {
		botaoAdicionarMembro.click();
	}
	
	public void clicarNoBotaoCadastrarMembro() {
		botaoCadastrarMembro.click();
	}
	
	public void preecherCampoNomeEquipe(String valor) {
		campoNomeEquipe.sendKeys(valor);
	}
	public void preencherCampoEmailDoMembro(String valor) {
		campoEmailDoMembro.sendKeys(valor);
	}
	public void preencherCampoNomeDoMembro(String valor) {
		campoNomeDoMembro.sendKeys(valor);
	}
	

}
