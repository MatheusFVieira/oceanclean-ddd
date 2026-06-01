package com.oceanclean.service;

import com.oceanclean.domain.HighRiskReentryPredictedEvent;
import com.oceanclean.domain.SpaceDebris;
import com.oceanclean.domain.ToxicityLevel;

import java.util.ArrayList;
import java.util.List;

public class ImpactRiskAnalyzer {

    private final List<HighRiskReentryPredictedEvent> emittedEvents = new ArrayList<>();

    public void analyzeCatalogRisk(List<SpaceDebris> catalog) {

        emittedEvents.clear();

        for (SpaceDebris debris : catalog) {
            boolean isReenteringSoon = debris.getCurrentTelemetry().isImminentReentry();

            boolean isToxic = debris.getToxicityLevel() != ToxicityLevel.LOW_FRAGMENT;

            if (isReenteringSoon && isToxic) {
                triggerEnvironmentalAlert(debris);
            }
        }
    }

    private void triggerEnvironmentalAlert(SpaceDebris debris) {
        HighRiskReentryPredictedEvent event = new HighRiskReentryPredictedEvent(
                debris.getNoradId(),
                debris.getCurrentTelemetry().altitudeKm(),
                debris.getToxicityLevel()
        );
        emittedEvents.add(event);
    }

    public List<HighRiskReentryPredictedEvent> getEmittedEvents() {
        return emittedEvents;
    }
}