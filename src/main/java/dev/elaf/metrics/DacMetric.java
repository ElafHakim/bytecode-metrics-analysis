package dev.elaf.metrics;

import org.objectweb.asm.Type;

import java.util.HashSet;
import java.util.Set;
import dev.elaf.model.MetricValue;
import dev.elaf.util.AsmBytecodeUtil;

/**
 * Berechnet Data Abstraction Coupling.
 *
 * Gezählt werden unterschiedliche Referenztypen in den Feldern
 * einer Klasse. Primitive Feldtypen werden ignoriert.
 */
public final class DacMetric {

    public MetricValue analyze(byte[] bytecode) {
        var classNode = AsmBytecodeUtil.read(bytecode);

        Set<String> referenceTypes = new HashSet<>();

        for (var field : classNode.fields) {
            Type fieldType = Type.getType(field.desc);

            AsmBytecodeUtil.collectReferenceTypes(
                    fieldType,
                    referenceTypes
            );
        }

        String entity = AsmBytecodeUtil.className(classNode.name);

        return new MetricValue(
                entity,
                referenceTypes.size()
        );
    }
}