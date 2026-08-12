package dev.elaf.util;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Set;

/**
 * Gemeinsame Hilfsmethoden für die Bytecodeanalysen.
 */
public class AsmBytecodeUtil {

    private AsmBytecodeUtil() {
    }

    /**
     * Liest eine Class-Datei und erzeugt daraus einen ASM-ClassNode.
     */
    public static ClassNode read(byte[] bytecode) {
        if (bytecode == null || bytecode.length == 0) {
            throw new IllegalArgumentException(
                    "bytecode must not be null or empty"
            );
        }

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytecode);
        classReader.accept(classNode, 0);

        return classNode;
    }

    /**
     * Wandelt einen internen JVM-Klassennamen in einen Java-Klassennamen um.
     *
     * Beispiel:
     * fixtures/DACAnalysisTest1
     * wird zu
     * fixtures.DACAnalysisTest1
     */
    public static String className(String internalName) {
        return internalName.replace('/', '.');
    }

    /**
     * Erstellt einen eindeutigen Namen für eine Methode.
     */
    public static String methodName(String owner, MethodNode method) {
        return className(owner)
                + "#"
                + method.name
                + method.desc;
    }

    /**
     * Erstellt eine eindeutige Referenz für einen Methodenaufruf.
     */
    public static String methodReference(
            String owner,
            String methodName,
            String descriptor
    ) {
        return className(owner)
                + "#"
                + methodName
                + descriptor;
    }

    /**
     * Fügt Referenztypen einer Menge hinzu.
     *
     * Primitive Typen werden nicht gezählt.
     * Bei Arrays wird der Elementtyp untersucht.
     */
    public static void collectReferenceTypes(
            Type type,
            Set<String> target
    ) {
        if (type.getSort() == Type.ARRAY) {
            collectReferenceTypes(type.getElementType(), target);
            return;
        }

        if (type.getSort() == Type.OBJECT) {
            target.add(type.getClassName());
        }
    }
}