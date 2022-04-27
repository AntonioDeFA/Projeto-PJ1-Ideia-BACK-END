package stepsDefinitions;

import io.cucumber.java.pt.Dado;
import pages.CadastroPage;

import static utils.Utils.*;

public class CadastroStep {
	
	
	@Dado("que o usuario exista")
	public void queOUsuarioExista() {
		Na(CadastroPage.class).preencherCampoEmail(EMAIL);
		Na(CadastroPage.class).preencherCampoNome(NOME_USUARIO);
		Na(CadastroPage.class).preencherCampoSenha(SENHA);
		Na(CadastroPage.class).preencherCampoConfirmarSenha(SENHA);
		Na(CadastroPage.class).clicarNoBotaoSalvar();
	}

}
