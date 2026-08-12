package dev.elaf.metrics;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import dev.elaf.model.MetricValue;

class McccMetricTest {

    private final McccMetric analyzer = new McccMetric();

    @Test
    void methodWithIfHasComplexityTwo() {
        Map<String, MetricValue> results = analyzeFixture();

        assertEquals(
                2,
                valueOf(results, "#method1()V")
        );
    }

    @Test
    void methodWithSwitchHasComplexityFive() {
        Map<String, MetricValue> results = analyzeFixture();

        assertEquals(
                5,
                valueOf(results, "#method2()V")
        );
    }

    @Test
    void methodWithLoopAndConditionsHasComplexityFive() {
        Map<String, MetricValue> results = analyzeFixture();

        assertEquals(
                5,
                valueOf(results, "#method3(I)V")
        );
    }

    private Map<String, MetricValue> analyzeFixture() {
        byte[] bytecode = FixtureCompiler.bytecode(
                "MCCCAnalysisTest"
        );

        return analyzer.analyze(bytecode);
    }

    private int valueOf(
            Map<String, MetricValue> results,
            String methodSignature
    ) {
        return results.entrySet()
                .stream()
                .filter(entry ->
                        entry.getKey().contains(methodSignature)
                )
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError(
                                "Method not found: " + methodSignature
                        )
                )
                .getValue()
                .value();
    }
}