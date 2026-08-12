package dev.elaf.metrics;

import org.objectweb.asm.tree.LineNumberNode;

import java.util.LinkedHashMap;
import java.util.Map;
import dev.elaf.model.MetricValue;
import dev.elaf.util.AsmBytecodeUtil;

/**
 * Berechnet die physischen Codezeilen einer Methode anhand
 * der Spannweite ihrer Zeilennummern.
 */
public final class LocphyMetric {

    public Map<String, MetricValue> analyze(byte[] bytecode) {
        var classNode = AsmBytecodeUtil.read(bytecode);

        Map<String, MetricValue> results = new LinkedHashMap<>();

        for (var method : classNode.methods) {
            int minimumLine = Integer.MAX_VALUE;
            int maximumLine = Integer.MIN_VALUE;

            for (var instruction : method.instructions) {
                if (instruction instanceof LineNumberNode lineNumber) {
                    minimumLine = Math.min(
                            minimumLine,
                            lineNumber.line
                    );

                    maximumLine = Math.max(
                            maximumLine,
                            lineNumber.line
                    );
                }
            }

            int locphy;

            if (minimumLine == Integer.MAX_VALUE) {
                locphy = 0;
            } else {
                locphy = maximumLine - minimumLine + 1;
            }

            String entity = AsmBytecodeUtil.methodName(
                    classNode.name,
                    method
            );

            results.put(
                    entity,
                    new MetricValue(entity, locphy)
            );
        }

        return Map.copyOf(results);
    }
}