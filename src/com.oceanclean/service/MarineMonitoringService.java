package com.oceanclean.service;

import com.oceanclean.domain.HighRiskReentryPredictedEvent;
import com.oceanclean.domain.marine.MarineAlert;
import com.oceanclean.domain.marine.PollutionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarineMonitoringService {
    private final List<MarineAlert> activeAlerts = new ArrayList<>();
    private final Random random = new Random();

    private final String[] REGIOES_OCEANICAS = {
            "Oceano Pacífico Centro-Sul", "Oceano Atlântico Norte", "Oceano Índico",
            "Mar Mediterrâneo", "Costa Brasileira (Atlântico Sul)", "Mar da China Meridional",
            "Golfo do México", "Oceano Ártico", "Costa da Califórnia", "Mar do Norte",
            "Golfo Pérsico", "Grande Barreira de Corais (Austrália)", "Mar do Caribe",
            "Costa Oeste Africana", "Estreito de Malaca"
    };

    public void loadEnvironmentalData() {
        activeAlerts.clear();

        int quantidadeDeAnomalias = random.nextInt(4) + 4;

        PollutionType[] tiposDePoluicao = {
                PollutionType.OIL_SPILL, PollutionType.PLASTIC_DEBRIS, PollutionType.ALGAL_BLOOM,
                PollutionType.CORAL_BLEACHING, PollutionType.ILLEGAL_FISHING,
                PollutionType.CHEMICAL_RUNOFF, PollutionType.GHOST_NETS, PollutionType.SEWAGE_DISCHARGE
        };

        for (int i = 0; i < quantidadeDeAnomalias; i++) {
            String regiao = REGIOES_OCEANICAS[random.nextInt(REGIOES_OCEANICAS.length)];
            PollutionType tipo = tiposDePoluicao[random.nextInt(tiposDePoluicao.length)];
            String severidade;

            switch (tipo) {
                case OIL_SPILL -> severidade = random.nextBoolean() ? "ALTA (Vazamento Detectado via Radar)" : "CRÍTICA (Desastre Iminente)";
                case PLASTIC_DEBRIS -> severidade = random.nextBoolean() ? "ALTA (Acúmulo de Microplástico)" : "MÉDIA (Ilha de Lixo Flutuante)";
                case CORAL_BLEACHING -> severidade = "CRÍTICA (Pico Térmico Detectado via Sensor PACE/MODIS)";
                case ILLEGAL_FISHING -> severidade = "ALTA (Sinal AIS Desligado - Embarcação Suspeita)";
                case CHEMICAL_RUNOFF -> severidade = random.nextBoolean() ? "ALTA (Alteração na Cor da Água)" : "MÉDIA (Efluentes Suspeitos)";
                case GHOST_NETS -> severidade = "MÉDIA (Risco de Enredamento da Fauna)";
                case SEWAGE_DISCHARGE -> severidade = "ALTA (Alerta Sanitário em Zona Costeira)";
                case ALGAL_BLOOM -> severidade = "MÉDIA (Redução de Oxigênio na Água)";
                default -> severidade = "MÉDIA";
            }

            activeAlerts.add(new MarineAlert(regiao, tipo, severidade));
        }
    }

    public void handleSpaceDebrisAlert(HighRiskReentryPredictedEvent event) {
        String severity = switch (event.toxicity()) {
            case MEDIUM_PAYLOAD -> "ALTA (Contaminação por Metais Pesados: Berílio/Chumbo)";
            case HIGH_ROCKET_BODY -> "CRÍTICA (Risco de Vazamento de Combustível/Hidrazina)";
            case CRITICAL_NUCLEAR -> "CATASTRÓFICA (Alerta Máximo: Risco Radiológico)";
            default -> "MÉDIA (Queda de Fragmento Comum)";
        };

        String regiaoSorteada = REGIOES_OCEANICAS[random.nextInt(REGIOES_OCEANICAS.length)];

        MarineAlert alert = new MarineAlert(
                regiaoSorteada + " - Objeto NORAD: " + event.noradId(),
                PollutionType.SPACE_DEBRIS_REENTRY,
                severity
        );
        activeAlerts.add(alert);
    }

    public void showDashboard() {
        System.out.println("\n=========================================================================================================");
        System.out.println(" 🌍 PAINEL INTEGRADO OCEANCLEAN: MONITORAMENTO AMBIENTAL E MECÂNICA ORBITAL 🌍 ");
        System.out.println("=========================================================================================================");
        if (activeAlerts.isEmpty()) {
            System.out.println("Nenhum alerta ativo no momento. Oceanos limpos!");
        } else {
            for (MarineAlert alert : activeAlerts) {
                System.out.println(alert.toString());
            }
        }
        System.out.println("=========================================================================================================\n");
    }
}