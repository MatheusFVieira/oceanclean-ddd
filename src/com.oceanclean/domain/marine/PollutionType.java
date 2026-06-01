package com.oceanclean.domain.marine;

public enum PollutionType {
    OIL_SPILL("Mancha de Óleo/Hidrocarbonetos"),
    PLASTIC_DEBRIS("Acúmulo de Plástico/Microplástico"),
    ALGAL_BLOOM("Proliferação de Algas Tóxicas (Maré Vermelha)"),
    SPACE_DEBRIS_REENTRY("Risco de Reentrada de Lixo Espacial"),
    CORAL_BLEACHING("Branqueamento de Corais (Anomalia Térmica)"),
    ILLEGAL_FISHING("Pesca Ilegal Detectada (Dark Vessels)"),
    CHEMICAL_RUNOFF("Despejo Químico Industrial/Agrícola"),
    GHOST_NETS("Aglomerado de Redes Fantasmas"),
    SEWAGE_DISCHARGE("Descarga de Esgoto Não Tratado");

    private final String description;

    PollutionType(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
}