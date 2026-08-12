package dev.elaf.metrics;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

import java.util.LinkedHashMap;
import java.util.Map;
import dev.elaf.model.MetricValue;
import dev.elaf.util.AsmBytecodeUtil;

/**
 * Berechnet die zyklomatische Komplexität nach McCabe.
 *
 * Bytecode-basierte Regel:
 *
 * 1 + Anzahl bedingter Sprünge + Anzahl der Switch-Alternativen
 */
public final class McccMetric {

    public Map<String, MetricValue> analyze(byte[] bytecode) {
        var classNode = AsmBytecodeUtil.read(bytecode);

        Map<String, MetricValue> results = new LinkedHashMap<>();

        for (var method : classNode.methods) {
            int complexity = 1;

            for (var instruction : method.instructions) {
                if (instruction instanceof JumpInsnNode jumpInstruction
                        && isConditionalJump(
                                jumpInstruction.getOpcode()
                        )) {
                    complexity++;
                }

                if (instruction instanceof TableSwitchInsnNode tableSwitch) {
                    complexity += tableSwitch.labels.size();
                }

                if (instruction instanceof LookupSwitchInsnNode lookupSwitch) {
                    complexity += lookupSwitch.labels.size();
                }
            }

            String entity = AsmBytecodeUtil.methodName(
                    classNode.name,
                    method
            );

            results.put(
                    entity,
                    new MetricValue(entity, complexity)
            );
        }

        return Map.copyOf(results);
    }

    /**
     * Prüft, ob ein Opcode ein bedingter Sprung ist.
     */
    private boolean isConditionalJump(int opcode) {
        return (opcode >= Opcodes.IFEQ
                && opcode <= Opcodes.IF_ACMPNE)
                || opcode == Opcodes.IFNULL
                || opcode == Opcodes.IFNONNULL;
    }
}