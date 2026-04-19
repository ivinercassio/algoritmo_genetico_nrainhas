import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ag {

    private static Random random = new Random();

    public Individuo executar(Factory factory, int numPopulacao, int numElite, int qtdGeracoes) {
        List<Individuo> populacaoInicial = new ArrayList<>(numPopulacao);
        for (int i = 0; i < numPopulacao; i++)
            populacaoInicial.add(factory.getInstance());

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
            if (estaOtimizado(melhor))
                break;
        }

        // possivelmente alterar quando houver parada antes do limite de geracoes
        // imprimirUltimaGeracao(populacaoInicial);
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
        List<Individuo> mutantes = new ArrayList<>();
        for (int j = 0; j < populacaoInicial.size(); j++) 
            mutantes.add(populacaoInicial.get(j).mutar());
        return mutantes;
    }

    private List<Individuo> aplicarElitismo(int numElite, List<Individuo> join) {
        List<Individuo> eliteList = new ArrayList<>();
        if (join.get(0).isMaximizacao()) { // maximizacao -> crescente
            for (int j = 0; j < join.size() - 1; j++)
                for (int j2 = j; j2 < join.size(); j2++)
                    if (join.get(j).getAvaliacao() > join.get(j2).getAvaliacao()) {
                        Individuo aux = join.get(j);
                        join.set(j, join.get(j2));
                        join.set(j2, aux);
                    }
        } else { // minimizacao -> decrescente
            for (int j = 0; j < join.size() - 1; j++)
                for (int j2 = j; j2 < join.size(); j2++)
                    if (join.get(j).getAvaliacao() < join.get(j2).getAvaliacao()) {
                        Individuo aux = join.get(j);
                        join.set(j, join.get(j2));
                        join.set(j2, aux);
                    }
        }
        // join precisa ser alterado para eles individuos nao disputarem na roleta
        for (int j = 0; j < numElite; j++)
            eliteList.add(join.removeLast());
        return eliteList;
    }

    private List<Individuo> aplicarRoleta(List<Individuo> join, int quantidade) {
        if (join.get(0).isMaximizacao())
            return aplicarRoletaMaximizacao(join, quantidade);
        return aplicarRoletaMinimizacao(join, quantidade);
    }

    private List<Individuo> aplicarRoletaMaximizacao(List<Individuo> join, int quantidade) {
        List<Individuo> selecionados = new ArrayList<>(quantidade);
        for (int j = 0; j < quantidade; j++) {
            double somatorioAvaliacoes = 0;
            for (int k = 0; k < join.size(); k++)
                somatorioAvaliacoes += join.get(k).getAvaliacao();

            double soma = 0;
            double sorteado = random.nextDouble(somatorioAvaliacoes);
            Individuo escolhido = null;
            for (int k = 0; k < join.size(); k++) {
                soma += join.get(k).getAvaliacao();
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

    private List<Individuo> aplicarRoletaMinimizacao(List<Individuo> join, int quantidade) {
        // considera-se o inverso multiplicativo dos getAvaliacao para realizar as somas
        List<Individuo> selecionados = new ArrayList<>(quantidade);
        for (int j = 0; j < quantidade; j++) {
            double somatorioAvaliacoes = 0;
            for (int k = 0; k < join.size(); k++)
                somatorioAvaliacoes += (1 / join.get(k).getAvaliacao());

            double soma = 0;
            // valores muito pequenos podem ser interpretados como zero e/ou negativos
            // resultando em Infinito
            if (Double.isInfinite(somatorioAvaliacoes))
                somatorioAvaliacoes = Double.MIN_VALUE;
            double sorteado = random.nextDouble(somatorioAvaliacoes);
            Individuo escolhido = null;
            for (int k = 0; k < join.size(); k++) {
                soma += (1 / join.get(k).getAvaliacao());
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

    private boolean estaOtimizado(Individuo melhor) {
        if (!melhor.isMaximizacao() && melhor.getAvaliacao() == 0)
            return true;
        if (melhor.isMaximizacao() && melhor.getAvaliacao() == Integer.MAX_VALUE)
            return true;
        return false;
    }

    private Individuo melhorIndividuo(List<Individuo> populacao) {
        Individuo melhor = null;
        if (populacao.get(0).isMaximizacao()) { // maximizacao
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

    private void imprimirUltimaGeracao(List<Individuo> populacaoInicial) {
        System.out.println("\nÚltima geração inteira: ");
        for (int i = 0; i < populacaoInicial.size(); i++) {
            System.out.println(populacaoInicial.get(i).toString());
        }
        System.out.println();
    }
}
