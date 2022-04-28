package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	
	@FindBy(id = "filled-search-email")
	private WebElement campoEmail;
	
	@FindBy(id = "filled-search")
	private WebElement campoSenha;
	
	
	@FindBy(id = "botao-login")
	private WebElement botaoLogin;
	
	
	public void clicarNoBotaoLogin() {
		botaoLogin.click();
	}
	
	public void preencherCampoEmail(String valor) {
		campoEmail.sendKeys(valor);
	}
	
	public void preencherCampoSenha(String valor){	
		campoSenha.sendKeys(valor);
	}
}
