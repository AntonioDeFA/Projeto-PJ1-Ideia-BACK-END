package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features" , dryRun = false, glue = "stepsDefinitions",snippets = SnippetType.CAMELCASE,
monochrome = true,plugin = {"pretty","html:target/cucumber/report"})
public class RunnerTest {
}
