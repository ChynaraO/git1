package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "@api",
        features = "src/test/resources/features",
        glue = "steps",
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        dryRun = false
)
public class TestRunner {

}
