package com.oceanclean.domain.marine;

import java.time.LocalDateTime;

public class MarineAlert {
    private final String region;
    private final PollutionType type;
    private final String severity;
    private final LocalDateTime alertTime;

    public MarineAlert(String region, PollutionType type, String severity) {
        this.region = region;
        this.type = type;
        this.severity = severity;
        this.alertTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] 🚨 ALERTA %s: %s na região %s",
                alertTime.toLocalTime(), severity, type.getDescription(), region);
    }
}