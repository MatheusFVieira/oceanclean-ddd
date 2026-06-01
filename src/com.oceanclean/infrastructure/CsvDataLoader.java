package com.oceanclean.infrastructure;

import com.oceanclean.domain.OrbitalTelemetry;
import com.oceanclean.domain.SpaceDebris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvDataLoader {

    public static List<SpaceDebris> loadFromCsv(String filePath) {
        List<SpaceDebris> debrisList = new ArrayList<>();
        String line;
        boolean isFirstLine = true;

        System.out.println("⏳ Lendo arquivo CSV e convertendo em Entidades de Domínio...");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(",");

                try {
                    String noradId = values[0];
                    String objectType = values[2];
                    double altitudeKm = Double.parseDouble(values[4]);
                    String lifetimeCategory = values[12];
                    double meanMotion = Double.parseDouble(values[13]);

                    OrbitalTelemetry telemetry = new OrbitalTelemetry(altitudeKm, meanMotion, lifetimeCategory);
                    SpaceDebris debris = new SpaceDebris(noradId, objectType, telemetry);

                    debrisList.add(debris);

                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Erro ao ler o arquivo CSV: " + e.getMessage());
        }

        return debrisList;
    }
}