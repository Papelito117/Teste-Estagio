package org.Teste1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FaturamentoDistribuidora {
    public static void main(String[] args) {
        try {
            // Carregar dados do JSON
            ClassLoader classLoader = FaturamentoDistribuidora.class.getClassLoader();
            File file = new File(classLoader.getResource("dados.json").getFile());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(file);

            List<Double> faturamentos = new ArrayList<>();
            for (JsonNode node : rootNode) {
                double valor = node.path("valor").asDouble();
                if (valor > 0) {
                    faturamentos.add(valor);
                }
            }

            // Calcular menor e maior valor de faturamento
            double menorFaturamento = faturamentos.stream().min(Double::compare).orElse(0.0);
            double maiorFaturamento = faturamentos.stream().max(Double::compare).orElse(0.0);

            // Calcular média mensal
            double soma = faturamentos.stream().mapToDouble(Double::doubleValue).sum();
            double mediaMensal = soma / faturamentos.size();

            // Contar dias com faturamento acima da média
            long diasAcimaDaMedia = faturamentos.stream().filter(valor -> valor > mediaMensal).count();

            // Exibir resultados
            System.out.println("Menor valor de faturamento: " + menorFaturamento);
            System.out.println("Maior valor de faturamento: " + maiorFaturamento);
            System.out.println("Número de dias com faturamento acima da média: " + diasAcimaDaMedia);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
