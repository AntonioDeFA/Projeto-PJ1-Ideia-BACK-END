package stepsDefinitions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static utils.Utils.*;
import io.cucumber.java.pt.Quando;
import pages.EtapaImersaoPage;

public class ImersaoStep {
	
	@Quando("passe a data de inicio da etapa imersao")
	public void passeADataDeInicioDaEtapaImersao() {
		LocalDate datainicio = LocalDate.now().plusDays(3);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Na(EtapaImersaoPage.class).inserirNaDataInicioImersao(datainicio.format(formatter));
	}


	@Quando("passe a data de termino da etapa imersao")
	public void passeADataDeTerminoDaEtapaImersao() {
		LocalDate datatermino = LocalDate.now().plusDays(3);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Na(EtapaImersaoPage.class).inserirNaDataTermino(datatermino.format(formatter));
	}
	@Quando("clique no botao salvar etapa imersao")
	public void cliqueNoBotaoSalvarEtapaImersao() {
		Na(EtapaImersaoPage.class).clicarNoBotaoSavarEtapaImersao();
	}
	
	
	@Quando("espere {string}")
	public void espere(String string) throws InterruptedException {
	    long tempo = Long.parseLong(string);
		
		Thread.sleep(tempo);
	}


	@Quando("clique no botao convidar")
	public void cliqueNoBotaoConvidar() {
	    Na(EtapaImersaoPage.class).clicarNoBotaoConvidar();
	}



}
