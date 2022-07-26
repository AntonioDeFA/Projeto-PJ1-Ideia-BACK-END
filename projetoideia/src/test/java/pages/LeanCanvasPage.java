package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeanCanvasPage {
	
	@FindBy(id = "simple-tab-2")
	private WebElement botaoAbaLeanCanvas;
	
	@FindBy(xpath = "/html/body/div/div/div[3]/div/div/div[4]/div/div/div/div[1]/button[2]")
	private WebElement botaoSalvarLeanCanvas;
	
	@FindBy(xpath = "/html/body/div/div/div[3]/div/div/div[4]/div/div/div/div[1]/button[3]")
	private WebElement botaoEnviarLeanCanvasConsultoria;
	
	@FindBy(id = "campo-problema")
	private WebElement campoProblema;
	
	@FindBy(id = "campo-solucao")
	private WebElement campoSolucao;

	@FindBy(id = "campo-metricas")
	private WebElement campoMetricasChave;
	
	@FindBy(id = "campo-proposta")
	private WebElement campoPropostaValor;
	
	@FindBy(id = "campo-vantagem")
	private WebElement campoVantagemCompetitiva;
	
	@FindBy(id = "campo-canais")
	private WebElement campoCanais;
	
	@FindBy(id = "campo-segmentos")
	private WebElement campoSegmentosClientes;
	
	@FindBy(id = "campo-estrutura")
	private WebElement campoEstruturaDeCustos;
	
	@FindBy(id = "campo-fonte")
	private WebElement campoFonteDeReceitas;
	
	
	
	public void clicarNoBotaoAbaLeanCanvas() {
		botaoAbaLeanCanvas.click();
	}
	
	public void clicarNoBotaoSalvarLeanCanvas() {
		botaoSalvarLeanCanvas.click();
	}
	
	public void clicarNoBotaoEnviarConsultoria() {
		botaoEnviarLeanCanvasConsultoria.click();
	}
	
	public void enviarTextoParaOsCampos(String texto) {
		campoProblema.sendKeys(texto);
		
		campoSolucao.click();
		
		campoSolucao.sendKeys(texto);
		
		campoMetricasChave.sendKeys(texto);
		
		campoPropostaValor.sendKeys(texto);
		
		campoVantagemCompetitiva.sendKeys(texto);
		
		campoCanais.sendKeys(texto);
		
		campoSegmentosClientes.sendKeys(texto);
		
		campoEstruturaDeCustos.sendKeys(texto);
		
		campoFonteDeReceitas.sendKeys(texto);
		
		
	}
	
}
