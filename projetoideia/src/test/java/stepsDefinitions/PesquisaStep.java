package stepsDefinitions;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import pages.TelaInicialPage;

import static utils.Utils.*;

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
		@Entao("valide a busca")
		public void valideABusca() {
		 
		}




}
