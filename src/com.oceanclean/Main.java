package com.oceanclean;

import com.oceanclean.domain.HighRiskReentryPredictedEvent;
import com.oceanclean.domain.SpaceDebris;
import com.oceanclean.domain.ToxicityLevel;
import com.oceanclean.infrastructure.CsvDataLoader;
import com.oceanclean.repository.InMemorySpaceDebrisRepository;
import com.oceanclean.repository.SpaceDebrisRepository;
import com.oceanclean.service.ImpactRiskAnalyzer;
import com.oceanclean.service.MarineMonitoringService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        exibirMensagemDeBoasVindas();

        Scanner scanner = new Scanner(System.in);
        SpaceDebrisRepository repository = new InMemorySpaceDebrisRepository();
        ImpactRiskAnalyzer spaceRiskAnalyzer = new ImpactRiskAnalyzer();
        MarineMonitoringService marineService = new MarineMonitoringService();

        boolean running = true;

        while (running) {
            System.out.println("🌊 OCEAN CLEAN - MENU PRINCIPAL 🌊");
            System.out.println("1. Carregar Base de Dados Orbital (current_catalog.csv)");
            System.out.println("2. Analisar Risco de Impacto Espacial (Gera dashboard diversificado)");
            System.out.println("3. Visualizar Painel de Monitoramento Marinho (Completo)");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n⏳ Lendo a base de dados completa...");
                    List<SpaceDebris> data = CsvDataLoader.loadFromCsv("current_catalog.csv");
                    data.forEach(repository::save);
                    System.out.println("✅ " + data.size() + " objetos carregados com sucesso!\n");
                    break;

                case 2:
                    List<SpaceDebris> catalog = repository.findAll();
                    if (catalog.isEmpty()) {
                        System.out.println("\n⚠️ Erro: Carregue a base de dados primeiro (Opção 1).\n");
                        break;
                    }

                    System.out.println("\n🛰️ Cruzando telemetria e calculando zonas de perigo...");
                    spaceRiskAnalyzer.analyzeCatalogRisk(catalog);
                    List<HighRiskReentryPredictedEvent> allEvents = spaceRiskAnalyzer.getEmittedEvents();

                    Collections.shuffle(allEvents);

                    List<HighRiskReentryPredictedEvent> diverseEvents = new ArrayList<>();

                    allEvents.stream()
                            .filter(e -> e.toxicity() == ToxicityLevel.HIGH_ROCKET_BODY)
                            .limit(2)
                            .forEach(diverseEvents::add);
                    allEvents.stream()
                            .filter(e -> e.toxicity() == ToxicityLevel.MEDIUM_PAYLOAD)
                            .limit(2)
                            .forEach(diverseEvents::add);

                    for (HighRiskReentryPredictedEvent event : allEvents) {
                        if (diverseEvents.size() >= 5) break;
                        if (!diverseEvents.contains(event)) {
                            diverseEvents.add(event);
                        }
                    }

                    marineService.loadEnvironmentalData();
                    diverseEvents.forEach(marineService::handleSpaceDebrisAlert);

                    System.out.println("✅ Análise concluída! " + allEvents.size() + " ameaças de reentrada iminente na base.");
                    System.out.println("🎲 Alertas combinados gerados com sucesso para o painel.\n");
                    break;

                case 3:
                    marineService.showDashboard();
                    break;

                case 4:
                    System.out.println("\nEncerrando o sistema OceanClean. Proteja nossos oceanos!");
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.\n");
            }
        }
        scanner.close();
    }

    private static void exibirMensagemDeBoasVindas() {
        System.out.println("=========================================================================================");
        System.out.println(" 🌊 BEM-VINDO À PLATAFORMA OCEANCLEAN 🌊");
        System.out.println("=========================================================================================");
        System.out.println("O OceanClean é um sistema inteligente de proteção oceânica, alinhado aos ODS 13 e 14.");
        System.out.println("\nCOMO FUNCIONA O SISTEMA:");
        System.out.println(" 🛰️  Risco Espacial: A previsão de queda de lixo espacial e contaminação por metais/hidrazina");
        System.out.println("     é calculada de forma REAL cruzando os dados orbitais do arquivo 'current_catalog.csv', disponível em 'https://www.kaggle.com/datasets/karnikakapoor/satellite-orbital-catalog'.");
        System.out.println(" 🌍  Ameaças Marinhas: Os demais alertas (manchas de óleo, microplásticos, algas, etc.)");
        System.out.println("     vêm de dados SIMULADOS, representando a leitura de satélites ambientais (como MODIS e PACE)");
        System.out.println("     que varrem e monitoram a qualidade dos oceanos continuamente.");
        System.out.println("=========================================================================================\n");
    }
}