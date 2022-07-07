package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EquipePage {

	
	@FindBy(id = "simple-tab-1")
	private WebElement abaEquipe;
	
	@FindBy(id = "id-btn-salvar-nome-equipe")
	private WebElement botaoSalvarNomeEquipe;
	
	@FindBy(id = "filled-search-nome-equipe-edit")
	private WebElement inputNomeEquipe;
	
	@FindBy(id = "id-btn-copiar-token-equipe")
	private WebElement botaoCopiarToken;
	
	@FindBy(xpath = "//*[@id=\"id-tabela-membros\"]/div/table/tbody/tr[2]/td[3]/i")
	private WebElement deletarMembroEquipe;
	
	@FindBy(xpath = "//*[@id=\"opcoes-links\"]/p/a[2]")
	private WebElement linkLogarComTokenEquipe;
	
	@FindBy(xpath = "//*[@id=\"form-dados-usuario\"]/div[2]/button")
	private WebElement botaoEntrarComToken;
	
	@FindBy(id = "filled-search-token")
	private WebElement inputToken;
	
	
	public WebElement clicarInputToken() {
		return inputToken;
	}
	
	public void clicarBotaoEntrarComToken() {
		botaoEntrarComToken.click();
	}
	
	public void clicarLinkLogarComToken() {
		linkLogarComTokenEquipe.click();
	}
	
	
	public void clicarBotaoDeletarMembro() {
		deletarMembroEquipe.click();
	}
	
	public void inserirNomeEquipe(String value) {
		inputNomeEquipe.sendKeys(value);
	}
	
	public void clicarNaAbaEquipe() {
		abaEquipe.click();
	}
	
	public void clicarBotaoSalvarNomeEquipe() {
		botaoSalvarNomeEquipe.click();
	}
	
	public void clicarBotaoCopiarToken() {
		botaoCopiarToken.click();
	}
}
