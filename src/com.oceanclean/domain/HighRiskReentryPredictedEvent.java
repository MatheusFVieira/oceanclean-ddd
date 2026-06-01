package com.oceanclean.domain;

public record HighRiskReentryPredictedEvent(
        String noradId,
        double predictedAltitude,
        ToxicityLevel toxicity
) {}