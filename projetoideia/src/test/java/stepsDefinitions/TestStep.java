package stepsDefinitions;

import io.cucumber.java.pt.Dado;

public class TestStep extends SpringIntegrationTest {

	@Dado("que eu printe {string}")
	public void DadoQueEuPrint(String nome) {
		for (int i = 10; i > 0; i--) {
			System.out.println(nome);
		}
	}

}
