package stepsDefinitions;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.ConsultorPage;

import static org.junit.Assert.assertTrue;
import static utils.Utils.*;

import org.openqa.selenium.By;

public class ConsultorStep {

	@Quando("clicar no icone de consultor")
	public void clicarNoIconeDeConsultor() {
		Na(ConsultorPage.class).clicarNoBotaoConsultorCabecalho();
	}

	@Quando("clicar no icone de consultar pitch")
	public void clicarNoIconeDeConsultarPitch() {
		Na(ConsultorPage.class).clicarNoBotaoConsultoriaPitch();
	}

	@Quando("digite o feedback sem salvar {string}")
	public void digiteOFeedbackSemSalvar(String string) {
		Na(ConsultorPage.class).inserirTextoFeedBack(string);
	}

	@Quando("clique no botao salvar feedback de consultoria")
	public void cliqueNoBotaoSalvarFeedbackDeConsultoria() throws InterruptedException {
		Na(ConsultorPage.class).clicarNoBotaoEnviarConsultoria();
		Thread.sleep(1000);
	}

	@Entao("o sistema recebe o erro {string}")
	public void oSistemaRecebeOErro(String string) {
		String elemento = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/div/div[2]")).getText();

		assertTrue(elemento.contains(string));
	}

	@Quando("clicar no botao adicionar feedback")
	public void clicarNoBotaoAdicionarFeedback() throws InterruptedException {
		Na(ConsultorPage.class).clicarNoBotaoAdicionarFeedBack();
		Thread.sleep(1000);
	}

	@Entao("o sistema valida que o feedback foi enviado com sucesso")
	public void oSistemaValidaQueOFeedbackFoiEnviadoComSucesso() {

		String elemento = driver.findElement(By.xpath("/html/body/div/div/div[3]/div[1]/h2")).getText();

		assertTrue(elemento.contains("Olá, consultor!"));
	}

	@Quando("clique no botao de confirmacao de envio de consultoria")
	public void cliqueNoBotaoDeConfirmacaoDeEnvioDeConsultoria() {
		Na(ConsultorPage.class).clicarNoBotaoConfirmarEnvioConsultoria();
	}

}
