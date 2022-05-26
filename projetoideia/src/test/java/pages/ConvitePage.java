package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConvitePage {
	
	@FindBy(id = "btn-aceitar-convite-consultor0")
	private WebElement botaoAceitarConviteConsultor;
	
	@FindBy(id = "btn-recusar-convite-avaliador0")
	private WebElement botaoRecusarConviteAvaliador;
	
	
	public void aceitarConviteConsultor() {
		botaoAceitarConviteConsultor.click();
	}
	
	public void recusarConviteAvaliador() {
		botaoRecusarConviteAvaliador.click();
	}
	
}
