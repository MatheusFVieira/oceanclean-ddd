package com.oceanclean.domain;

public class SpaceDebris {
    private final String noradId;
    private final String objectType;
    private OrbitalTelemetry currentTelemetry;
    private ToxicityLevel toxicityLevel;

    public SpaceDebris(String noradId, String objectType, OrbitalTelemetry initialTelemetry) {
        if (noradId == null || noradId.isBlank()) {
            throw new IllegalArgumentException("O ID do catálogo NORAD é obrigatório.");
        }
        this.noradId = noradId;
        this.objectType = objectType;
        this.currentTelemetry = initialTelemetry;
        this.toxicityLevel = calculateToxicity(objectType);
    }

    private ToxicityLevel calculateToxicity(String type) {
        if (type == null) return ToxicityLevel.LOW_FRAGMENT;

        return switch (type.toUpperCase()) {
            case "ROCKET BODY" -> ToxicityLevel.HIGH_ROCKET_BODY;
            case "PAYLOAD" -> ToxicityLevel.MEDIUM_PAYLOAD;
            case "NUCLEAR" -> ToxicityLevel.CRITICAL_NUCLEAR;
            default -> ToxicityLevel.LOW_FRAGMENT;
        };
    }

    public void updateTelemetry(OrbitalTelemetry newTelemetry) {
        this.currentTelemetry = newTelemetry;
    }

    public String getNoradId() { return noradId; }
    public String getObjectType() { return objectType; }
    public OrbitalTelemetry getCurrentTelemetry() { return currentTelemetry; }
    public ToxicityLevel getToxicityLevel() { return toxicityLevel; }
}