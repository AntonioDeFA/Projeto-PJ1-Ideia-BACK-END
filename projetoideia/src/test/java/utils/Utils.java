package utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class Utils {
	
	public static WebDriver driver;

	
	private Utils() {}
	
	public static void acessarSistema() {
		System.setProperty("webdriver.chrome.driver","C://driver//chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);

		driver.get("http://localhost:3000");

	}
	
	
	
	public static <T> T Na(Class<T>classe){
		return PageFactory.initElements(driver, classe);
	}

}
