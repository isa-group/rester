package es.us.isa.rester.generators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import es.us.isa.rester.configuration.TestConfigurationIO;
import es.us.isa.rester.configuration.pojos.TestConfigurationObject;
import es.us.isa.rester.specification.OpenAPISpecification;
import es.us.isa.rester.testcases.TestCase;
import es.us.isa.rester.testcases.writters.RESTAssuredWritter;
import es.us.isa.rester.util.TestConfigurationFilter;

public class PetstoreFullTestCaseGenerator {

    @Test
    public void petstoreFullTestCaseGenerator() {
        // Load specification
        String OAISpecPath = "src/main/resources/Petstore/swagger.yaml";
        OpenAPISpecification spec = new OpenAPISpecification(OAISpecPath);

        // Load configuration
        TestConfigurationObject conf = TestConfigurationIO.loadConfiguration("src/main/resources/Petstore/fullConf.yaml");

        // Set number of test cases to be generated on each path, on each operation (HTTP method)
        int numTestCases = 4;

        // Create generator and filter
        AbstractTestCaseGenerator generator = new RandomTestCaseGenerator(spec, conf, numTestCases);

        List<TestConfigurationFilter> filters = new ArrayList<>();
        TestConfigurationFilter filter = new TestConfigurationFilter();
        filter.setPath("/store/order/{orderId}");
        filter.addGetMethod();
        filters.add(filter);

        TestConfigurationFilter filter2 = new TestConfigurationFilter();
        filter2.setPath("/user/login");
        filter2.addGetMethod();
        filters.add(filter2);

        Collection<TestCase> testCases = generator.generate(filters);

        assertEquals("Incorrect number of test cases", numTestCases*filters.size(), testCases.size());

        // Write RESTAssured test cases
        RESTAssuredWritter writer = new RESTAssuredWritter();
        writer.setOAIValidation(true);
        writer.setLogging(true);
        String basePath = spec.getSpecification().getSchemes().get(0).name() + "://" + spec.getSpecification().getHost() + spec.getSpecification().getBasePath();
        writer.write(OAISpecPath, "src/generation/java", "Petstore", null, basePath.toLowerCase(), testCases);

    }
}
