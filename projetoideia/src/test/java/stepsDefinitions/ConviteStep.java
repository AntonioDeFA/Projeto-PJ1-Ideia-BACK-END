package stepsDefinitions;

import io.cucumber.java.pt.Quando;
import pages.ConvitePage;
import pages.TelaInicialPage;

import static utils.Utils.*;

public class ConviteStep {

	@Quando("clicar no botao convites consultor")
	public void clicarNoBotaoConvitesConsultor() {
		Na(TelaInicialPage.class).clicarNoBotaoConvitesConsultor();
	}

	@Quando("clicar no botao aceitar convite consultor")
	public void clicarNoBotaoAceitarConviteConsultor() {
		Na(ConvitePage.class).aceitarConviteConsultor();
	}

	@Quando("clicar no botao trofeu competicao")
	public void clicarNoBotaoTrofeuCompeticao() {
		Na(TelaInicialPage.class).clicarNoBotaoTrofeu();
	}

	@Quando("clicar no botao convites avaliador")
	public void clicarNoBotaoConvitesAvaliador() {
		Na(TelaInicialPage.class).clicarNoBotaoConvitesAvaliador();
	}


	@Quando("clicar no botao recusar convite avaliador")
	public void clicarNoBotaoRecusarConviteAvaliador() {
		Na(ConvitePage.class).recusarConviteAvaliador();
	}

}
