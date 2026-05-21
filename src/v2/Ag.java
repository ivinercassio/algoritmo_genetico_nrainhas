package v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Ag {

    private static Random random = new Random();
    private boolean isMaximizacao;

    public Individuo executar(Factory factory, int numPopulacao, int numElite, int qtdGeracoes) {
        List<Individuo> populacaoInicial = new ArrayList<>(numPopulacao);
        for (int i = 0; i < numPopulacao; i++)
            populacaoInicial.add(factory.getInstance());

        isMaximizacao = populacaoInicial.get(0).isMaximizacao();

        for (int i = 0; i < qtdGeracoes; i++) {
            List<Individuo> filhos = aplicarRecombinacao(populacaoInicial);
            List<Individuo> mutantes = aplicarMutacao(populacaoInicial);

            List<Individuo> join = new ArrayList<>(numPopulacao * 3);
            join.addAll(populacaoInicial);
            join.addAll(filhos);
            join.addAll(mutantes);

            List<Individuo> novaPopulacao = new ArrayList<>(numPopulacao);
            novaPopulacao.addAll(aplicarElitismo(numElite, join));
            novaPopulacao.addAll(aplicarRoleta(join, (numPopulacao - numElite)));

            populacaoInicial.clear();
            populacaoInicial.addAll(novaPopulacao);

            // imprimir o numero da geracao e o melhor individuo (genes e getAvaliacao)
            Individuo melhor = melhorIndividuo(populacaoInicial);
            imprimirIndividuo(i, melhor);
            if (melhor.isOtimizado())
                return melhor;
        }
        return melhorIndividuo(populacaoInicial);
    }

    private List<Individuo> aplicarRecombinacao(List<Individuo> populacaoInicial) {
        List<Individuo> auxiliar = new ArrayList<>();
        auxiliar.addAll(populacaoInicial);

        List<Individuo> filhos = new ArrayList<>();
        while (auxiliar.size() > 0) {
            Individuo escolha1 = auxiliar.remove(random.nextInt(0, auxiliar.size()));
            Individuo escolha2 = auxiliar.remove(random.nextInt(0, auxiliar.size()));
            filhos.addAll(escolha1.recombinar(escolha2));
        }
        return filhos;
    }

    private List<Individuo> aplicarMutacao(List<Individuo> populacaoInicial) {
        return populacaoInicial.parallelStream().map(Individuo::mutar)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private List<Individuo> aplicarElitismo(int numElite, List<Individuo> join) {
        List<Individuo> eliteList = new ArrayList<>();
        if (isMaximizacao) { // maximizacao -> decrescente (maior primeiro)
            Collections.sort(join, Comparator.comparingDouble(Individuo::getAvaliacao).reversed());
        } else { // minimizacao -> crescente (menor primeiro)
            Collections.sort(join, Comparator.comparingDouble(Individuo::getAvaliacao));
        }
        // join precisa ser alterado para eles individuos nao disputarem na roleta
        for (int j = 0; j < numElite; j++)
            eliteList.add(join.remove(0)); // remove o melhor (primeiro)
        return eliteList;
    }

    private List<Individuo> aplicarRoleta(List<Individuo> join, int quantidade) {
        if (isMaximizacao)
            return aplicarRoletaMaximizacao(join, quantidade);
        return aplicarRoletaMinimizacao(join, quantidade);
    }

    private List<Individuo> aplicarRoletaMaximizacao(List<Individuo> join, int quantidade) {
        List<Individuo> selecionados = new ArrayList<>(quantidade);
        for (int j = 0; j < quantidade; j++) {
            double acrescimo = Math.abs(menorAvaliacao(join));
            double somaTotal = somatorioAvaliacao(join, acrescimo);
            double sorteado = random.nextDouble() * somaTotal;
            Individuo escolhido = null;
            double soma = 0;
            for (int k = 0; k < join.size(); k++) {
                soma += join.get(k).getAvaliacao() + acrescimo;
                if (soma >= sorteado) {
                    escolhido = join.get(k);
                    break;
                }
            }
            selecionados.add(escolhido);
            join.remove(escolhido);
        }
        return selecionados;
    }

    // em caso de resultado igual a infinito atribui-se um valor extremamente grande ao somatorio
    // entretanto, talvez o valor sorteado pela roleta tambem muito grande
    // superando a somatorio real dos getAvaliacao de todos os individuos

    // -20, 10, 5, -4 -> (+20)
    // 0, 30, 25, 29

    private List<Individuo> aplicarRoletaMinimizacao(List<Individuo> join, int quantidade) {
        List<Individuo> selecionados = new ArrayList<>(quantidade);
        for (int j = 0; j < quantidade; j++) {
            double acrescimo = Math.abs(menorAvaliacao(join));
            double somaTotal = somatorioAvaliacaoInvertido(join, acrescimo);
            double sorteado = random.nextDouble() * somaTotal;
            Individuo escolhido = null;
            double soma = 0;
            int posicao = 0;
            while (soma < sorteado) {
                double avaliacao = 1 / (join.get(posicao).getAvaliacao() + acrescimo);
                if (Double.isInfinite(avaliacao))
                    soma += Double.MAX_VALUE / quantidade;
                else 
                    soma += avaliacao;
                escolhido = join.get(posicao);
                posicao++;
            }
            selecionados.add(escolhido);
            join.remove(escolhido);
        }
        return selecionados;
    }

    private double menorAvaliacao(List<Individuo> list) {
        double menor = Double.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) 
            if (list.get(i).getAvaliacao() <= menor)
                menor = list.get(i).getAvaliacao();
        return menor;
    }

    private double somatorioAvaliacao(List<Individuo> list, double acrescimo) {
        double somaTotal = 0;
        for (int k = 0; k < list.size(); k++) 
            somaTotal += list.get(k).getAvaliacao() + acrescimo;
        return somaTotal;
    }

    private double somatorioAvaliacaoInvertido(List<Individuo> list, double acrescimo) {
        double somaTotal = 0;
        for (int k = 0; k < list.size(); k++) {
            double prob = 1.0 / (list.get(k).getAvaliacao() + acrescimo);
            if (Double.isInfinite(prob))
                somaTotal += Double.MAX_VALUE / list.size();
            else 
                somaTotal += prob;
        }
        return somaTotal;
    }

    private Individuo melhorIndividuo(List<Individuo> populacao) {
        Individuo melhor = null;
        if (isMaximizacao) { // maximizacao
            double avaliacao = Double.MIN_VALUE;
            for (int j = 0; j < populacao.size(); j++) {
                if (populacao.get(j).getAvaliacao() > avaliacao) {
                    melhor = populacao.get(j);
                    avaliacao = populacao.get(j).getAvaliacao();
                }
            }
        } else { // minimizacao
            double avaliacao = Double.MAX_VALUE;
            for (int j = 0; j < populacao.size(); j++) {
                if (populacao.get(j).getAvaliacao() < avaliacao) {
                    melhor = populacao.get(j);
                    avaliacao = populacao.get(j).getAvaliacao();
                }
            }
        }
        return melhor;
    }

    private void imprimirIndividuo(int geracao, Individuo individuo) {
        System.out.println(" Geracao: " + (geracao + 1) + "° .... " + individuo.toString());
    }
}
