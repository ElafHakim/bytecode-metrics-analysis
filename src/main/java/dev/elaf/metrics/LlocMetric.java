package dev.elaf.metrics;

import org.objectweb.asm.tree.LineNumberNode;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import dev.elaf.model.MetricValue;
import dev.elaf.util.AsmBytecodeUtil;

/**
 * Berechnet eine Bytecode-basierte Annäherung der
 * Logical Lines of Code.
 *
 * Gezählt werden unterschiedliche Zeilennummern aus der
 * LineNumberTable einer Methode.
 */
public final class LlocMetric {

    public Map<String, MetricValue> analyze(byte[] bytecode) {
        var classNode = AsmBytecodeUtil.read(bytecode);

        Map<String, MetricValue> results = new LinkedHashMap<>();

        for (var method : classNode.methods) {
            Set<Integer> sourceLines = new LinkedHashSet<>();

            for (var instruction : method.instructions) {
                if (instruction instanceof LineNumberNode lineNumber) {
                    sourceLines.add(lineNumber.line);
                }
            }

            String entity = AsmBytecodeUtil.methodName(
                    classNode.name,
                    method
            );

            results.put(
                    entity,
                    new MetricValue(entity, sourceLines.size())
            );
        }

        return Map.copyOf(results);
    }
}