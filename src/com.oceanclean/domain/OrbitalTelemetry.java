package com.oceanclean.domain;

public record OrbitalTelemetry(double altitudeKm, double meanMotion, String lifetimeCategory) {

    public OrbitalTelemetry {
        if (altitudeKm < 0) {
            throw new IllegalArgumentException("A altitude não pode ser negativa.");
        }
        if (meanMotion <= 0) {
            throw new IllegalArgumentException("O Movimento Médio (mean motion) deve ser positivo.");
        }
    }

    public boolean isImminentReentry() {
        return this.altitudeKm < 300.0 && "<1yr".equals(this.lifetimeCategory);
    }
}