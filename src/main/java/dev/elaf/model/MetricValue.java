package dev.elaf.model;

/**
 * Ergebnis einer einzelnen Metrikberechnung.
 *
 * @param entity Name der analysierten Klasse oder Methode
 * @param value  berechneter Metrikwert
 */
public record MetricValue(String entity, int value) {

    public MetricValue {
        if (entity == null || entity.isBlank()) {
            throw new IllegalArgumentException(
                    "entity must not be null or blank"
            );
        }

        if (value < 0) {
            throw new IllegalArgumentException(
                    "value must not be negative"
            );
        }
    }
}