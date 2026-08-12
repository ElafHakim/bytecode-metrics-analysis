package dev.elaf.metrics;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dev.elaf.model.MetricValue;

class LocphyMetricTest {

    private final LocphyMetric analyzer = new LocphyMetric();

    @Test
    void analysisFindsMethodsFromFixtureClass() {
        byte[] bytecode = FixtureCompiler.bytecode(
                "LOCphyAnalysisTest");

        Map<String, MetricValue> results = analyzer.analyze(bytecode);

        assertTrue(results.keySet().stream()
                .anyMatch(name -> name.contains("#method1()V")));

        assertTrue(results.keySet().stream()
                .anyMatch(name -> name.contains("#method2()V")));
    }

    @Test
    void method2HasPhysicalSpanOfSixLines() {
        byte[] bytecode = FixtureCompiler.bytecode(
                "LOCphyAnalysisTest");

        Map<String, MetricValue> results = analyzer.analyze(bytecode);

        assertEquals(
                6,
                valueOf(results, "#method2()V"));
    }

    private int valueOf(
            Map<String, MetricValue> results,
            String methodSignature) {
        return results.entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(methodSignature))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "Method not found: "
                                + methodSignature))
                .getValue()
                .value();
    }
}