package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConsultorPage {
	
	
	@FindBy(id = "op-consultor-cabecalho")
	private WebElement consultorCabecalho;

	
	@FindBy(id = "btn-acessar-pitch-deck0")
	private WebElement consultoriaPitch;
	
	
	@FindBy(id = "textarea-criar-comentario-feedback-pitch")
	private WebElement feedbackTextArea;
	
	
	@FindBy(id = "btn-adicionar-feedback-pitch")
	private WebElement adicionarFeedBack;
	
	@FindBy(id = "btn-enviar-feedbacks-pitch")
	private WebElement enviarConsultoria;
	
	@FindBy(xpath ="/html/body/div[2]/div[3]/div/button[1]")
	private WebElement botaoConfirmarEnvioConsultoria;
	
	public void clicarNoBotaoConfirmarEnvioConsultoria() {
		botaoConfirmarEnvioConsultoria.click();
	}
	
	public void clicarNoBotaoConsultorCabecalho() {
		consultorCabecalho.click();
	}
	
	public void clicarNoBotaoConsultoriaPitch() {
		consultoriaPitch.click();
	}
	
	
	public void clicarNoBotaoAdicionarFeedBack() {
		adicionarFeedBack.click();
	}
	
	public void clicarNoBotaoEnviarConsultoria() {
		enviarConsultoria.click();
	}
	
	public void inserirTextoFeedBack(String text) {
		feedbackTextArea.sendKeys(text);
	}
	
}
