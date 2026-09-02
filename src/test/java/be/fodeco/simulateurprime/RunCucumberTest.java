package be.fodeco.simulateurprime;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

/**
 * Point d'entree qui execute tous les fichiers .feature de src/test/resources/features
 * via "mvn test" (JUnit 5 Platform Suite + moteur Cucumber).
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "be.fodeco.simulateurprime.steps")
public class RunCucumberTest {
}
