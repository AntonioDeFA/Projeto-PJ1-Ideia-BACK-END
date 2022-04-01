package stepsDefinitions;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import pages.TelaInicialPage;

import static org.junit.Assert.assertTrue;
import static utils.Utils.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PesquisaStep {
	

		@Dado("que eu acesse a pagina inicial do ideia")
		public void queEuAcesseAPaginaInicialDoIdeia() {
		 System.out.println("Entrei no sistema ");
		}


		@Dado("preencha o campo pesquisa com {string}")
		public void preenchaOCampoPesquisaCom(String string) {
			Na(TelaInicialPage.class).inserirNoCampoPesquisaNomeCompeticao(string);
		    
		}
		@Dado("clique no botao filtar")
		public void cliqueNoBotaoFiltar() {
		 Na(TelaInicialPage.class).clicarNoBotaoFiltrar();
		}
		@Entao("valide a busca {string}")
		public void valideABusca(String texto) {
		WebElement elemento  =  driver.findElements(By.xpath("//*[@id=\"minhas-competicoes\"]/div[2]/div[2]/div/ul")).get(0);
		System.out.println(elemento.getText());
		assertTrue(elemento.getText().contains(texto));
		}




}
