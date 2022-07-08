package stepsDefinitions;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.EquipePage;
import pages.*;

import static org.junit.Assert.assertNotNull;
import static utils.Utils.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
public class EquipeStep {
	
	
	@Quando("clicar no botao de minhas competicoes")
	public void clicarNoBotaoDeMinhasCompeticoes() {
	    Na(TelaInicialPage.class).clicarNoBotaoMinhasCompeticoes();
	}
	@Quando("clicar no botao de entrar na competicao")
	public void clicarNoBotaoDeEntrarNaCompeticao() {
		Na(TelaInicialPage.class).clicarNoBotaoEntrarNaCompeticao();
	}
	@Quando("clicar no botao de aba de equipe")
	public void clicarNoBotaoDeAbaDeEquipe() {
	   Na(EquipePage.class).clicarNaAbaEquipe();
	}
	@Quando("passar um novo nome para a equipe {string}")
	public void passarUmNovoNomeParaAEquipe(String string) {
		Na(EquipePage.class).inserirNomeEquipe(string);
	}
	@Quando("clicar no botao atualizar nome equipe")
	public void clicarNoBotaoAtualizarNomeEquipe() {
		//Na(EquipePage.class).clicarBotaoSalvarNomeEquipe();
	}
	@Quando("clicar no botao copiar token")
	public void clicarNoBotaoCopiarToken() {
		Na(EquipePage.class).clicarBotaoCopiarToken();
	}
	@Quando("clicar no botao deletar membro")
	public void clicarNoBotaoDeletarMembro() {
	    //Na(EquipePage.class).clicarBotaoDeletarMembro();
	}
	
	@Dado("que eu clique no link logar com token")
	public void queEuCliqueNoLinkLogarComToken() {
		Na(EquipePage.class).clicarLinkLogarComToken();
	}
	@Dado("passe um token de acesso valido")
	public void passeUmTokenDeAcessoValido() throws InterruptedException {  
	
	WebElement el = Na(EquipePage.class).clicarInputToken();
	
	el.click();
	el.sendKeys(Keys.CONTROL+"v");
	
	Na(EquipePage.class).clicarBotaoEntrarComToken();
	}
	@Entao("o programa verifica que logou")
	public void oProgramaVerificaQueLogou() {
	   WebElement elemento =  driver.findElement(By.xpath("/html/body/div/div/div[3]/div/div/div[2]/div/div/div/div[1]/h5"));
	   
	   assertNotNull(elemento);
	
	}

}
