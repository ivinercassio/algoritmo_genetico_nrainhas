import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ag {

    private static Random random = new Random();

    public Individuo executar(Factory factory, int num, int numElite, int qtdGeracoes) {
        List<Individuo> populacaoInicial = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            populacaoInicial.add(factory.getInstance());
        }

        for (int i = 0; i < qtdGeracoes; i++) {
            List<Individuo> filhos = aplicarRecombinacao(populacaoInicial);

            List<Individuo> mutantes = aplicarMutacao(populacaoInicial);

            // JUNCAO DE TODOS OS INDIVIDUOS
            List<Individuo> join = new ArrayList<>(num * 3);
            join.addAll(populacaoInicial);
            join.addAll(filhos);
            join.addAll(mutantes);

            // ELITISMO
            // ordenar a lista join pelo metodo getAvaliacao() de cada Individuo:
            // -> se problema de minimizacao, ordenar de forma decrescente
            // -> se problema de maximizacao, ordenar de forma crescente
            // para aplicar o eletismo, retirar os "numElite" últimos da lista join ordenada
            List<Individuo> eliteList = aplicarElitismo(numElite, join);

            List<Individuo> newPopulacao = new ArrayList<>(num);
            newPopulacao.addAll(eliteList);
            // ROLETA
            newPopulacao.addAll(this.aplicarRoleta(join, (num - numElite)));

            // imprimir o numero da geracao e o melhor individuo (genes e getAvaliacao)
            imprimirMelhorIndividuo(i, newPopulacao);

            populacaoInicial = newPopulacao;
        }

        // return o melhor individuo da ultima geracao (qtdGeracoes)
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
            eliteList.add(join.removeLast());
        return eliteList;
    }

    private List<Individuo> aplicarMutacao(List<Individuo> populacaoInicial) {
        List<Individuo> mutantes = new ArrayList<>();
        for (int j = 0; j < populacaoInicial.size(); j++)
            mutantes.add(populacaoInicial.get(j).mutar()); // gera 20 mutantes
        return mutantes;
    }

    private List<Individuo> aplicarRecombinacao(List<Individuo> populacaoInicial) {
        List<Individuo> auxiliar = new ArrayList<>();
        auxiliar.addAll(populacaoInicial); // retira as duplas de pais daqui aleatoriamente

        List<Individuo> filhos = new ArrayList<>();
        while (auxiliar.size() > 0) {
            // RECOMBINAR
            // escolho de 2 em 2 pais da lista populacaoInicial (aleatorio) e adiciono na
            // lista auxiliar
            // -> auxiliar eh a lista de historico de recombinacoes feitas
            // cada dupla de pais escolhidas deve recombinar e gerar dois filhos
            // colocar os 20 filhos em uma lista List<Individuo> filhos
            Individuo escolha1 = auxiliar.remove(random.nextInt(0, auxiliar.size()));
            Individuo escolha2 = auxiliar.remove(random.nextInt(0, auxiliar.size()));
            filhos.addAll(escolha1.recombinar(escolha2));
        }
        return filhos;
    }

    private Individuo imprimirMelhorIndividuo(int i, List<Individuo> newPopulacao) {
        Individuo melhor = null;
        int avaliacao = Integer.MIN_VALUE;
        for (int j = 0; j < newPopulacao.size(); j++)
            if (newPopulacao.get(j).getAvaliacao() > avaliacao) {
                melhor = newPopulacao.get(j);
                avaliacao = ((int) newPopulacao.get(j).getAvaliacao());
            }
        System.out.println(" Geracao: " + (i + 1) + " .... " + melhor.toString());
        return melhor;
    }

    private List<Individuo> aplicarRoleta(List<Individuo> join, int quantidade) {

        int somatorioAvaliacoes = 0;
        for (int i = 0; i < join.size(); i++)
            somatorioAvaliacoes += join.get(i).getAvaliacao();
        List<Individuo> selecionados = new ArrayList<>(quantidade);

        if (join.get(0).isMaximizacao()) {
            // maximazacao
            selecionados = aplicarRoletaMaximizacao(join, quantidade);
        } else {
            // minimizacao
            selecionados = aplicarRoletaMinimizacao(join, quantidade);

        }
        return selecionados;
    }

    private List<Individuo> aplicarRoletaMaximizacao(List<Individuo> join, int quantidade) {
        List<Individuo> selecionados = new ArrayList<>(quantidade);
        for (int i = 0; i < quantidade; i++) {
            int somatorioAvaliacoes = 0;
            for (int j = 0; i < join.size(); j++)
                somatorioAvaliacoes += join.get(j).getAvaliacao();

            int soma = 0;
            Individuo escolhido = null;
            while (soma < random.nextInt(0, somatorioAvaliacoes))
                for (int j = 0; j < join.size(); j++) {
                    soma += join.get(j).getAvaliacao();
                    escolhido = join.get(j);
                }
            selecionados.add(escolhido);
            join.remove(escolhido);
        }
        return selecionados;
    }

    private List<Individuo> aplicarRoletaMinimizacao(List<Individuo> join, int quantidade) {
        // ALTERAR ISSO
        List<Individuo> selecionados = new ArrayList<>(quantidade);
        for (int i = 0; i < quantidade; i++) {
            int somatorioAvaliacoes = 0;
            for (int j = 0; i < join.size(); j++)
                somatorioAvaliacoes += join.get(j).getAvaliacao();

            int soma = 0;
            Individuo escolhido = null;
            while (soma < random.nextInt(0, somatorioAvaliacoes))
                for (int j = 0; j < join.size(); j++) {
                    soma += join.get(j).getAvaliacao();
                    escolhido = join.get(j);
                }
            selecionados.add(escolhido);
            join.remove(escolhido);
        }
        return selecionados;
    }

}
