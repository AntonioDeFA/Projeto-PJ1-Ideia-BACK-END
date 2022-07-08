package stepsDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import static utils.Utils.*;

public class Hooks {
	
	@Before(value = "not @Login-inicial")
	public void setUp() {
		acessarSistema();
	}
	
	@Before(value = "@Login-inicial")
	public void setUpLoginInicial() {
		acessarSistema("http://localhost:3000/cadastro-usuario");
	}
	
	@After
	public void tearDown() {
		driver.close();
	}

}
