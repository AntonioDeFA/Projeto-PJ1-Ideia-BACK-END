package stepsDefinitions;

import static org.junit.Assert.assertNotNull;
import static utils.Utils.Na;
import static utils.Utils.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.LeanCanvasPage;

public class LeanCanvasStep {
	
	
	@Quando("clicar na aba Lean Canvas")
	public void clicarNaAbaLeanCanvas() {
	   Na(LeanCanvasPage.class).clicarNoBotaoAbaLeanCanvas();
	}

	@Quando("clicar no botao salvar lean canvas")
	public void clicarNoBotaoSalvarLeanCanvas() {
	   Na(LeanCanvasPage.class).clicarNoBotaoSalvarLeanCanvas();
	}
	
	@Entao("e verificado se realmente deu erro no salvar lean canvas")
	public void eVerificadoSeRealmenteDeuErroNoSalvarLeanCanvas() {
	    WebElement element =  driver.findElement(By.xpath("/html/body/div/div/div[3]/div/div/div[4]/div/div/div/div[3]/div/div[2]"));
	    
	    assertNotNull(element);
	
	}
	
	@Quando("preencher os campos do lean canvas")
	public void preencherOsCamposDoLeanCanvas() {
		Na(LeanCanvasPage.class).enviarTextoParaOsCampos("Teste Lean Canvas");
	}
	
	@Quando("clicar no botao enviar lean canvas consultoria")
	public void clicarNoBotaoEnviarLeanCanvasConsultoria() {
		Na(LeanCanvasPage.class).clicarNoBotaoEnviarConsultoria();
	}
	
	
	
}
