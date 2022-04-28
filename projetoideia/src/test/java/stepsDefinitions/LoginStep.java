package stepsDefinitions;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Utils.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;



public class LoginStep {
	
	@Dado("que eu passe o email {string}")
	public void queEuPasseOEmail(String email) {
	   Na(LoginPage.class).preencherCampoEmail(email);
	}
	@Dado("passe a senha {string}")
	public void passeASenha(String senha) {
	  Na(LoginPage.class).preencherCampoSenha(senha);
	}
	@Quando("eu clicar no botao login")
	public void euClicarNoBotaoLogin() {
	   Na(LoginPage.class).clicarNoBotaoLogin();
	}
	@Entao("o sistema valida que entrou")
	public void oSistemaValidaQueEntrou() {
		WebElement nomeNaTela = driver.findElement(By.id("nome-usuario-para-teste"));
		assertEquals(nomeNaTela.getText(),NOME_USUARIO);
	}
	
	@Entao("o sistema valida que ocorreu um erro {string}")
	public void oSistemaValidaQueOcorreuUmErro(String string) {
		WebElement nomeNaTela = driver.findElement(By.id("titulo"));
		
		assertEquals(nomeNaTela.getText(),string);
	}

}
