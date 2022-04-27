package utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class Utils {
	
	public static WebDriver driver;
	
	public static final String EMAIL = "usuarioteste@gmail.com";
	
	public static final String SENHA = "1";
	
	public static final String NOME_USUARIO = "Usuario teste";
	
	public static final String NOME_COMPETICAO = "Competição de testes";
	
	public Utils() {}
	
	public static void acessarSistema() {
		System.setProperty("webdriver.chrome.driver","C://driver//chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);

		driver.get("http://localhost:3000");
	}
	
	public static void acessarSistema(String URL) {
		System.setProperty("webdriver.chrome.driver","C://driver//chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);

		driver.get(URL);

	}
	
	
	
	public static <T> T Na(Class<T>classe){
		return PageFactory.initElements(driver, classe);
	}


}
