# 👑 Otimização com Algoritmo Genético

Este repositório contém uma estrutura em **Java** desenvolvida para resolver problemas de otimização combinatória e contínua utilizando **Algoritmos Genéticos (AG)**. O projeto foi expandido e reestruturado para suportar múltiplos cenários, abrangendo desde problemas clássicos até a otimização de funções matemáticas com genes reais.

O projeto foi desenvolvido originalmente como parte prática da disciplina de **Inteligência Artificial I** e aprimorado para uma arquitetura mais genérica e organizada (v2).

---

## 📂 Estrutura do Projeto

Abaixo está a organização dos pacotes e módulos do projeto:

* `v2/`: Core genérico do Algoritmo Genético reestruturado (`Ag.java`, `Factory.java`, `Individuo.java`).
* `nrainhas/`: Implementação específica para o clássico Desafio das N-Rainhas.
* `functions/`: Implementações focadas em otimização contínua de funções matemáticas (com representação por genes reais), utilizando os modelos de referência da [SFU Optimization Test Problems](https://www.sfu.ca/~ssurjano/optimization.html):
    * **Dixon-Price** (`dixonprice/`)
    * **Langermann** (`langermann/`)
    * **Levy** (`levy/`)

---

## 📝 Os Problemas Implementados

### 1. 🎯 O Problema das N-Rainhas
O objetivo é posicionar $N$ rainhas em um tabuleiro de xadrez de tamanho $N \times N$ de forma que nenhuma rainha ataque outra. Isso significa que não pode haver duas rainhas na mesma linha, coluna ou diagonal.

### 2. 📉 Otimização de Funções Matemáticas (Genes Reais)
Esta versão introduz o suporte a indivíduos com representação real para a minimização de funções matemáticas complexas de testes globais:

* **Função Dixon-Price:** Função multivalorada com forte dependência entre as variáveis.
* **Função Langermann:** Função multimodal com vários mínimos locais, desafiadora para algoritmos de busca local.
* **Função Levy:** Função com grande quantidade de mínimos locais, ideal para testar a capacidade de escape e convergência do AG.

---

## 🚀 Como Executar

Cada problema possui sua classe de inicialização (`Main`) com os parâmetros ajustados do algoritmo genético:

1.  **N-Rainhas:** Execute a classe `MainNRainhas.java` dentro de `src/nrainhas/`.
2.  **Dixon-Price:** Execute a classe `MainDixonPrice.java` dentro de `src/functions/dixonprice/`.
3.  **Langermann:** Execute a classe `MainLangermann.java` dentro de `src/functions/langermann/`.
4.  **Levy:** Execute a classe `MainLevy.java` dentro de `src/functions/levy/`.
