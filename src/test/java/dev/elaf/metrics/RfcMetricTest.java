package dev.elaf.metrics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import dev.elaf.model.MetricValue;

class RfcMetricTest {

    private final RfcMetric analyzer = new RfcMetric();

    @Test
    void classWithPrivateMethodsHasRfcFive() {
        byte[] bytecode = FixtureCompiler.bytecode(
                "RFCAnalysisTest1"
        );

        MetricValue result = analyzer.analyze(bytecode);

        assertEquals("fixtures.RFCAnalysisTest1", result.entity());
        assertEquals(5, result.value());
    }

    @Test
    void classWithPublicAndPrivateMethodsHasRfcNine() {
        byte[] bytecode = FixtureCompiler.bytecode(
                "RFCAnalysisTest2"
        );

        MetricValue result = analyzer.analyze(bytecode);

        assertEquals("fixtures.RFCAnalysisTest2", result.entity());
        assertEquals(9, result.value());
    }

    @Test
    void classWithoutExplicitMethodsHasRfcTwo() {
        byte[] bytecode = FixtureCompiler.bytecode(
                "RFCAnalysisTest3"
        );

        MetricValue result = analyzer.analyze(bytecode);

        assertEquals("fixtures.RFCAnalysisTest3", result.entity());
        assertEquals(2, result.value());
    }
}