package dev.elaf.metrics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import dev.elaf.model.MetricValue;

class DacMetricTest {

    private final DacMetric analyzer = new DacMetric();

    @Test
    void classWithReferenceFieldsHasDacFour() {
        byte[] bytecode = FixtureCompiler.bytecode(
                "DACAnalysisTest1"
        );

        MetricValue result = analyzer.analyze(bytecode);

        assertEquals("fixtures.DACAnalysisTest1", result.entity());
        assertEquals(4, result.value());
    }

    @Test
    void classWithPrimitiveFieldsHasDacZero() {
        byte[] bytecode = FixtureCompiler.bytecode(
                "DACAnalysisTest2"
        );

        MetricValue result = analyzer.analyze(bytecode);

        assertEquals("fixtures.DACAnalysisTest2", result.entity());
        assertEquals(0, result.value());
    }

    @Test
    void classWithPrimitiveAndReferenceFieldsHasDacFour() {
        byte[] bytecode = FixtureCompiler.bytecode(
                "DACAnalysisTest3"
        );

        MetricValue result = analyzer.analyze(bytecode);

        assertEquals("fixtures.DACAnalysisTest3", result.entity());
        assertEquals(4, result.value());
    }
}