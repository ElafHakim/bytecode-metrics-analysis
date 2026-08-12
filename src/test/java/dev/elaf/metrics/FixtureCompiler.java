package dev.elaf.metrics;

import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Kompiliert die Java-Beispielklassen aus src/test/resources.
 *
 * Die erzeugten Class-Dateien werden unter
 * target/fixture-classes gespeichert.
 */
final class FixtureCompiler {

    private static final Path SOURCE_DIRECTORY = Path.of(
            "src",
            "test",
            "resources",
            "fixtures"
    );

    private static final Path OUTPUT_DIRECTORY = Path.of(
            "target",
            "fixture-classes"
    );

    private static boolean compiled;

    private FixtureCompiler() {
    }

    /**
     * Liefert den Bytecode einer kompilierten Beispielklasse.
     */
    static synchronized byte[] bytecode(String simpleClassName) {
        compileOnce();

        Path classFile = OUTPUT_DIRECTORY.resolve(
                "fixtures/" + simpleClassName + ".class"
        );

        try {
            return Files.readAllBytes(classFile);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Fixture bytecode not found: " + classFile,
                    exception
            );
        }
    }

    /**
     * Kompiliert alle Beispielklassen genau einmal.
     */
    private static void compileOnce() {
        if (compiled) {
            return;
        }

        var compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            throw new IllegalStateException(
                    "A complete JDK 18 is required. "
                            + "A JRE alone is not sufficient."
            );
        }

        try {
            Files.createDirectories(OUTPUT_DIRECTORY);

            List<String> sourceFiles;

            try (var paths = Files.list(SOURCE_DIRECTORY)) {
                sourceFiles = paths
                        .filter(path ->
                                path.toString().endsWith(".java")
                        )
                        .map(Path::toString)
                        .sorted()
                        .toList();
            }

            if (sourceFiles.isEmpty()) {
                throw new IllegalStateException(
                        "No Java fixtures found in "
                                + SOURCE_DIRECTORY
                );
            }

            List<String> arguments = new ArrayList<>();

            arguments.add("--release");
            arguments.add("18");

            // Debuginformationen einschließlich LineNumberTable erzeugen.
            arguments.add("-g");

            arguments.add("-d");
            arguments.add(OUTPUT_DIRECTORY.toString());

            arguments.addAll(sourceFiles);

            int exitCode = compiler.run(
                    null,
                    null,
                    null,
                    arguments.toArray(String[]::new)
            );

            if (exitCode != 0) {
                throw new IllegalStateException(
                        "Compiling the fixtures failed. Exit code: "
                                + exitCode
                );
            }

            compiled = true;

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Could not prepare test fixtures",
                    exception
            );
        }
    }
}