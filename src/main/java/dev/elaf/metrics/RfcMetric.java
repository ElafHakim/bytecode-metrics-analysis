package dev.elaf.metrics;

import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

import java.util.HashSet;
import java.util.Set;
import dev.elaf.model.MetricValue;
import dev.elaf.util.AsmBytecodeUtil;

/**
 * Berechnet Response for a Class.
 *
 * Gezählt werden:
 *
 * - deklarierte Methoden der untersuchten Klasse
 * - unterschiedliche direkt aufgerufene Methoden
 */
public final class RfcMetric {

    public MetricValue analyze(byte[] bytecode) {
        var classNode = AsmBytecodeUtil.read(bytecode);

        Set<String> responseMethods = new HashSet<>();

        for (var method : classNode.methods) {
            // Die deklarierte Methode selbst wird gezählt.
            responseMethods.add(
                    AsmBytecodeUtil.methodReference(
                            classNode.name,
                            method.name,
                            method.desc
                    )
            );

            for (var instruction : method.instructions) {
                if (instruction instanceof MethodInsnNode methodCall) {
                    responseMethods.add(
                            AsmBytecodeUtil.methodReference(
                                    methodCall.owner,
                                    methodCall.name,
                                    methodCall.desc
                            )
                    );
                }

                if (instruction
                        instanceof InvokeDynamicInsnNode dynamicCall) {
                    responseMethods.add(
                            "invokedynamic#"
                                    + dynamicCall.name
                                    + dynamicCall.desc
                    );
                }
            }
        }

        String entity = AsmBytecodeUtil.className(classNode.name);

        return new MetricValue(
                entity,
                responseMethods.size()
        );
    }
}