# 🌊 OceanClean: Plataforma de Monitoramento Ambiental e Risco Espacial

![Java](https://img.shields.io/badge/Java-Blue?logo=openjdk&logoColor=white)
![Arquitetura](https://img.shields.io/badge/Arquitetura-Domain--Driven%20Design-success)
![Instituição](https://img.shields.io/badge/FIAP-Engenharia%20de%20Software-ed145b)

O **OceanClean** é uma plataforma inteligente concebida para atuar na intersecção da Sustentabilidade Espacial (*Space Sustainability*) e da Preservação Oceânica. O sistema cruza dados reais de telemetria orbital com simulações de sensores ambientais para criar um painel unificado de proteção marinha.

Projeto desenvolvido como parte da Global Solution (GS) do curso de Engenharia de Software.

---

## 🎯 O Problema & A Solução

### O Ponto Cego Ambiental
Com o advento do "New Space", o ritmo de lançamentos e reentradas atmosféricas de lixo espacial atingiu patamares históricos. Como 71% do planeta é coberto por água, a maioria dos detritos espaciais (contendo metais pesados como berílio e combustíveis tóxicos como hidrazina) cai nos oceanos. Atualmente, agências espaciais monitoram o espaço, e agências ambientais monitoram o mar — mas **ninguém cruza esses dados preventivamente**. Além disso, ameaças tradicionais (óleo, plásticos, pesca ilegal) demoram a ser consolidadas em painéis unificados.

### A Solução: OceanClean
O OceanClean preenche esta lacuna. Atuando como uma plataforma de inteligência preditiva, o sistema:
1. Analisa catálogos espaciais civis (CelesTrak/Space-Track).
2. Isola matematicamente os objetos com risco de reentrada iminente e alta toxicidade.
3. Combina estes dados com leituras de satélites ambientais (como NASA PACE/MODIS) que detectam poluição tradicional.
4. Gera um **Dashboard de Alertas Global** para ONGs, governos e pesquisadores.

---

## 🌱 Alinhamento aos ODS da ONU

O projeto suporta diretamente os seguintes Objetivos de Desenvolvimento Sustentável:
* **ODS 14 (Vida na Água):** Prevenção da contaminação oceânica por agentes químicos espaciais e monitoramento de plásticos, óleo e redes fantasmas.
* **ODS 13 (Ação Climática):** Observação de anomalias térmicas (branqueamento de corais) e proliferação de algas tóxicas derivadas de mudanças climáticas.
* **ODS 9 (Indústria, Inovação e Infraestrutura):** Fomento de uma economia espacial sustentável, responsabilizando o ciclo de vida completo dos satélites.

---

## ⚙️ Como Funciona a Plataforma

A aplicação foi desenvolvida com dois motores de dados operando em paralelo:

1. **Motor de Risco Espacial (Dados Reais 🛰️):**
   * O sistema lê e processa o arquivo `current_catalog.csv` contendo **mais de 15.000 registros reais** de objetos em órbita.
   * Filtra os objetos através de regras de domínio rigorosas: *Altitude < 300km*, *Expectativa de queda < 1 ano* e *Nível de Toxicidade* (Foguetes e Satélites).
2. **Motor de Monitoramento Marinho (Dados Simulados 🌍):**
   * Um algoritmo simula a varredura contínua de satélites ambientais.
   * Gera ocorrências dinâmicas de poluição (ex: Pesca Ilegal, Vazamento de Óleo, Microplásticos) em diferentes regiões globais (ex: Grande Barreira de Corais, Golfo do México).

Ao final, os eventos de domínio se comunicam, mesclando o lixo espacial com as anomalias oceânicas em um único painel interativo (CLI).

---

## 🏛️ Arquitetura e Modelagem (DDD)

O código foi rigorosamente estruturado utilizando **Domain-Driven Design (DDD)** para garantir baixo acoplamento e alta coesão, isolando as regras de negócio de frameworks e interfaces.

### Bounded Contexts
* **Space Tracking Context:** Vocabulário focado em `SpaceDebris`, `OrbitalTelemetry`, `ToxicityLevel`.
* **Marine Monitoring Context:** Vocabulário focado em `MarineAlert`, `PollutionType`, `OceanicRegion`.

### Elementos do Domínio Aplicados
* **Aggregates & Entities:** `SpaceDebris` protege suas invariantes e calcula sua própria toxicidade.
* **Value Objects:** `OrbitalTelemetry` (implementado via `record` do Java) garante que altitudes não sejam negativas e concentra a lógica de previsão de reentrada.
* **Domain Events:** `HighRiskReentryPredictedEvent` permite que o sistema espacial avise o sistema oceânico sem conhecer sua implementação (*Publisher/Subscriber*).
* **Domain Services:** `ImpactRiskAnalyzer` varre os 15 mil dados do repositório aplicando estatística e probabilidade.
* **Repositories:** Inversão de dependência (SOLID) utilizando interfaces no domínio e implementações em memória (`InMemorySpaceDebrisRepository`) na infraestrutura.

---

## 💻 Tecnologias Utilizadas

* **Linguagem:** Java 17 (Uso intenso de *Records*, *Switch Expressions* e *Streams API*).
* **Paradigma:** Orientação a Objetos + Arquitetura Orientada a Eventos de Domínio.
* **Processamento de Dados:** Leitura raw de arquivos CSV customizada sem bibliotecas de terceiros (Infraestrutura).
* **Interface:** CLI (Command Line Interface) interativa via `Scanner`.

---

## 📂 Estrutura do Projeto

```text
oceanclean/
├── current_catalog.csv                 # Base de dados orbital REAL (NASA/CelesTrak)
└── src/main/java/com/oceanclean/
    ├── domain/                         # 🧠 Regras de Negócio (Entities, VOs, Events)
    │   ├── marine/                     # Contexto Marinho
    │   └── ...                         # Contexto Espacial
    ├── infrastructure/                 # 🏗️ Adaptadores (CsvDataLoader)
    ├── repository/                     # 🗄️ Inversão de Dependência (Mock Database)
    ├── service/                        # ⚙️ Domain Services (ImpactRiskAnalyzer)
    └── Main.java                       # 🚀 Ponto de Entrada (Menu Interativo)
```
---

## ▶️ Como Executar

1. Certifique-se de possuir o **JDK 17 ou superior** instalado em sua máquina.
2. Clone o repositório para o seu ambiente local:

```bash
git clone https://github.com/MatheusFVieira/oceanclean-ddd.git
```

3. Abra o projeto na sua IDE de preferência (IntelliJ IDEA, Eclipse, VS Code).
4. Verifique se o arquivo `current_catalog.csv` encontra-se na raiz do projeto.
5. Execute a classe `Main.java`.
6. No menu interativo:
   - Pressione **1** para carregar os +15.000 satélites na memória.
   - Pressione **2** para cruzar a telemetria espacial com a varredura de sensores marinhos.
   - Pressione **3** para visualizar o dashboard de impacto gerado.

---

## 👥 Equipe Desenvolvedora

Projeto desenvolvido pelo grupo:

- Jose Rafael Tejeda Mantilla RM: 561849
- Laura Sousa Barreto RM: 561965
- Matheus Freitas Vieira RM: 566198
- Natália Camargo de Souza RM: 565769
