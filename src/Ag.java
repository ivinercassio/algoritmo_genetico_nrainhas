import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ag {

    private static Random random = new Random();

    // IMPLEMENTAR PARADA QUANDO ATINGIR A OTIMIZACAO ANTES DO LIMITE DE GERACOES

    public Individuo executar(Factory factory, int numPopulacao, int numElite, int qtdGeracoes) {
        List<Individuo> populacaoInicial = new ArrayList<>(numPopulacao);
        for (int i = 0; i < numPopulacao; i++) {
            // Individuo individuo = null;
            // while (individuo == null) 
            //     individuo = factory.getInstance();
            populacaoInicial.add(factory.getInstance());
        }

        for (int i = 0; i < qtdGeracoes; i++) {
            List<Individuo> filhos = aplicarRecombinacao(populacaoInicial);

            List<Individuo> mutantes = aplicarMutacao(populacaoInicial);

            List<Individuo> join = new ArrayList<>(numPopulacao * 3);
            join.addAll(populacaoInicial);
            join.addAll(filhos);
            join.addAll(mutantes);

            List<Individuo> newPopulacao = new ArrayList<>(numPopulacao);
            newPopulacao.addAll(aplicarElitismo(numElite, join));
            newPopulacao.addAll(aplicarRoleta(join, (numPopulacao - numElite)));

            // imprimir o numero da geracao e o melhor individuo (genes e getAvaliacao)
            imprimirMelhorIndividuo(i, newPopulacao);

            populacaoInicial = newPopulacao;
        }

        // return o melhor individuo da ultima geracao
        // possivelmente alterar quando houver parada antes do limite de geracoes
        return imprimirMelhorIndividuo(qtdGeracoes, populacaoInicial);
    }

    private List<Individuo> aplicarElitismo(int numElite, List<Individuo> join) {
        List<Individuo> eliteList = new ArrayList<>();
        for (int j = 0; j < join.size() - 1; j++)
            for (int j2 = j; j2 < join.size(); j2++)
                if (join.get(j).getAvaliacao() > join.get(j2).getAvaliacao()) {
                    Individuo aux = join.get(j);
                    join.set(j, join.get(j2));
                    join.set(j2, aux);
                }
        for (int j = 0; j < numElite; j++)
            eliteList.add(join.remove(join.size() - 1));
        return eliteList;
    }

    private List<Individuo> aplicarMutacao(List<Individuo> populacaoInicial) {
        List<Individuo> mutantes = new ArrayList<>();
        for (int j = 0; j < populacaoInicial.size(); j++)
            mutantes.add(populacaoInicial.get(j).mutar());
        return mutantes;
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

    private Individuo imprimirMelhorIndividuo(int numGeracao, List<Individuo> populacao) {
        Individuo melhor = null;
        int avaliacao = Integer.MIN_VALUE;
        for (int j = 0; j < populacao.size(); j++)
            if (populacao.get(j).getAvaliacao() > avaliacao) {
                melhor = populacao.get(j);
                avaliacao = ((int) populacao.get(j).getAvaliacao());
            }
        System.out.println(" Geracao: " + (numGeracao + 1) + "° .... " + melhor.toString());
        return melhor;
    }

    private List<Individuo> aplicarRoleta(List<Individuo> join, int quantidade) {
        if (join.get(0).isMaximizacao())
            return aplicarRoletaMaximizacao(join, quantidade);
        return aplicarRoletaMinimizacao(join, quantidade);
    }

    private List<Individuo> aplicarRoletaMaximizacao(List<Individuo> join, int quantidade) {
        List<Individuo> selecionados = new ArrayList<>(quantidade);
        for (int i = 0; i < quantidade; i++) {
            int somatorioAvaliacoes = 0;
            for (int j = 0; i < join.size(); j++)
                somatorioAvaliacoes += join.get(j).getAvaliacao();

            int soma = 0;
            int sorteado = random.nextInt(0, somatorioAvaliacoes);
            Individuo escolhido = null;
            for (int j = 0; j < join.size(); j++) {
                if (soma < sorteado)
                    break;
                soma += join.get(j).getAvaliacao();
                escolhido = join.get(j);
            }
            selecionados.add(escolhido);
            join.remove(escolhido);
        }
        return selecionados;
    }

    private List<Individuo> aplicarRoletaMinimizacao(List<Individuo> join, int quantidade) {
        // considera-se o inverso multiplicativo dos getAvaliacao para realizar as somas
        List<Individuo> selecionados = new ArrayList<>(quantidade);
        for (int i = 0; i < quantidade; i++) {
            int somatorioAvaliacoes = 0;
            for (int j = 0; j < join.size(); j++)
                somatorioAvaliacoes += (1/join.get(j).getAvaliacao());

            int soma = 0;
            int sorteado = random.nextInt(0, somatorioAvaliacoes);
            Individuo escolhido = null;
            for (int j = 0; j < join.size(); j++) {
                if (soma < sorteado)
                    break;
                soma += (1/join.get(j).getAvaliacao());
                escolhido = join.get(j);
            }
            selecionados.add(escolhido);
            join.remove(escolhido);
        }
        return selecionados;
    }

}
