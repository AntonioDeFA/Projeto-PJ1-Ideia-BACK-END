package stepsDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.CriarEquipePage;

import static org.junit.Assert.assertNotNull;
import static utils.Utils.*;

import java.util.List;


public class CadastroEquipeStep {
		
	

		@Dado("que eu selecione uma competicao na fase de inscricao")
		public void queEuSelecioneUmaCompeticaoNaFaseDeInscricao() {
		    WebElement botao = driver.findElement(By.xpath("//*[@id=\"component-cardCompeticao\"]/div/div[2]/div[2]/button"));
		    botao.click();
		}



		@Dado("coloque o nome da equipe {string}")
		public void coloqueONomeDaEquipe(String string) {
		 Na(CriarEquipePage.class).preecherCampoNomeEquipe(string);
		}
		
		
		@Dado("clicar no botao adicionar membro")
		public void clicarNoBotaoAdicionarMembro() {
		  Na(CriarEquipePage.class).clicarNoBotaoAdicionarMembro();
		}


		@Quando("eu passar o nome do membro {string}")
		public void euPassarONomeDoMembro(String string) {
		  Na(CriarEquipePage.class).preencherCampoNomeDoMembro(string);
		}
		
		@Quando("passar o email do membro {string}")
		public void passarOEmailDoMembro(String string) {
		    Na(CriarEquipePage.class).preencherCampoEmailDoMembro(string);
		}
		
		@Quando("clicar no botao adicionar")
		public void clicarNoBotaoAdicionar() {
		   Na(CriarEquipePage.class).clicarNoBotaoCadastrarMembro();
		}
		
		@Quando("clicar no botao confirmar")
		public void clicarNoBotaoConfirmar() {
		   Na(CriarEquipePage.class).clicarNoBotaoConfirmar();
		}
		
		@Quando("clicar no botao confirmar inscricao")
		public void clicarNoBotaoConfirmarInscricao() {
			Na(CriarEquipePage.class).clicarNoBotaoConfirmarInscricao();
		}
		
		@Entao("o programa valida que a equipe foi criada")
		public void oProgramaValidaQueAEquipeFoiCriada() {
			WebElement botaoMinhasCompeticoes = driver.findElement(By.xpath("//*[@id=\"radio-buttons-competicoes\"]/fieldset/div/label[2]/span[1]"));
			botaoMinhasCompeticoes.click();
			List<WebElement> listaCompeticoes = driver.findElements(By.id("lista-minhas-competicoes"));
			
			assertNotNull(listaCompeticoes);
		}
		



}
