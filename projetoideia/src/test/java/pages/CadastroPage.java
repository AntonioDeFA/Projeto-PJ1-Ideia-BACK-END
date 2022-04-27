package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CadastroPage {
	
	@FindBy(id = "filled-search-email")
	private WebElement campoEmail;
	
	@FindBy(id = "filled-search-Nome")
	private WebElement campoNomeUsuario;
	
	@FindBy(id = "filled-search-password")
	private WebElement campoSenha;
	
	@FindBy(id = "filled-search-confirm-password")
	private WebElement campoConfirmarSenha;
	
	@FindBy(id = "btn-salvar-usuario")
	private WebElement botaoSalvarUsuario;
	
	@FindBy(id = "btn-voltar")
	private WebElement botaoVoltar;
	
	
	
	public void clicarNoBotaoSalvar() {
		botaoSalvarUsuario.click();
	}
	
	public void clicarNoBotaoVoltar() {
		botaoVoltar.click();
	}
	
	public void preencherCampoNome(String valor) {
		campoNomeUsuario.sendKeys(valor);
	}
	
	public void preencherCampoEmail(String valor) {
		campoEmail.sendKeys(valor);
	}
	
	public void preencherCampoSenha(String valor) {
		campoSenha.sendKeys(valor);
	}
	public void preencherCampoConfirmarSenha(String valor) {
		campoConfirmarSenha.sendKeys(valor);
	}
	
}
