package optimization;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AgReal{

    private static Random random = new Random();

    public IndividuoInteiro executar(Factory factory, int numPopulacao, int numElite, int qtdGeracoes) {
        List<IndividuoInteiro> populacaoInicial = new ArrayList<>(numPopulacao);
        for (int i = 0; i < numPopulacao; i++)
            populacaoInicial.add((IndividuoInteiro)factory.getInstance());

        for (int i = 0; i < qtdGeracoes; i++) {
            List<IndividuoInteiro> filhos = aplicarRecombinacao(populacaoInicial);
            List<IndividuoInteiro> mutantes = aplicarMutacao(populacaoInicial);

            List<IndividuoInteiro> join = new ArrayList<>(numPopulacao * 3);
            join.addAll(populacaoInicial);
            join.addAll(filhos);
            join.addAll(mutantes);

            List<IndividuoInteiro> novaPopulacao = new ArrayList<>(numPopulacao);
            novaPopulacao.addAll(aplicarElitismo(numElite, join));
            novaPopulacao.addAll(aplicarRoleta(join, (numPopulacao - numElite)));

            populacaoInicial.clear();
            populacaoInicial.addAll(novaPopulacao);

            // imprimir o numero da geracao e o melhor individuo (genes e getAvaliacao)
            IndividuoInteiro melhor = melhorIndividuo(populacaoInicial);
            imprimirIndividuo(i, melhor);
            if (estaOtimizado(melhor))
                break;
        }

        // possivelmente alterar quando houver parada antes do limite de geracoes
        // imprimirUltimaGeracao(populacaoInicial);
        return melhorIndividuo(populacaoInicial);
    }

    private List<IndividuoInteiro> aplicarRecombinacao(List<IndividuoInteiro> populacaoInicial) {
        List<IndividuoInteiro> auxiliar = new ArrayList<>();
        auxiliar.addAll(populacaoInicial);

        List<IndividuoInteiro> filhos = new ArrayList<>();
        while (auxiliar.size() > 0) {
            IndividuoInteiro escolha1 = auxiliar.remove(random.nextInt(0, auxiliar.size()));
            IndividuoInteiro escolha2 = auxiliar.remove(random.nextInt(0, auxiliar.size()));
            filhos.addAll(escolha1.recombinar(escolha2));
        }
        return filhos;
    }

    private List<IndividuoInteiro> aplicarMutacao(List<IndividuoInteiro> populacaoInicial) {
        return populacaoInicial.parallelStream().map(IndividuoInteiro::mutar)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private List<IndividuoInteiro> aplicarElitismo(int numElite, List<IndividuoInteiro> join) {
        List<IndividuoInteiro> eliteList = new ArrayList<>();
        if (join.get(0).isMaximizacao()) { // maximizacao -> decrescente (maior primeiro)
            Collections.sort(join, Comparator.comparingDouble(IndividuoInteiro::getAvaliacao).reversed());
        } else { // minimizacao -> crescente (menor primeiro)
            Collections.sort(join, Comparator.comparingDouble(IndividuoInteiro::getAvaliacao));
        }
        // join precisa ser alterado para eles individuos nao disputarem na roleta
        for (int j = 0; j < numElite; j++)
            eliteList.add(join.remove(0)); // remove o melhor (primeiro)
        return eliteList;
    }

    private List<IndividuoInteiro> aplicarRoleta(List<IndividuoInteiro> join, int quantidade) {
        if (join.get(0).isMaximizacao())
            return aplicarRoletaMaximizacao(join, quantidade);
        return aplicarRoletaMinimizacao(join, quantidade);
    }

    private List<IndividuoInteiro> aplicarRoletaMaximizacao(List<IndividuoInteiro> join, int quantidade) {
        List<IndividuoInteiro> selecionados = new ArrayList<>(quantidade);
        // Pré-calcular somatório e probabilidades cumulativas
        double[] cumulativas = new double[join.size()];
        double somaTotal = 0;
        for (int k = 0; k < join.size(); k++) {
            somaTotal += join.get(k).getAvaliacao();
            cumulativas[k] = somaTotal;
        }
        for (int j = 0; j < quantidade; j++) {
            double sorteado = random.nextDouble() * somaTotal;
            IndividuoInteiro escolhido = null;
            for (int k = 0; k < join.size(); k++) {
                if (cumulativas[k] >= sorteado) {
                    escolhido = join.get(k);
                    break;
                }
            }
            selecionados.add(escolhido);
            join.remove(escolhido);
            // Recalcular cumulativas após remoção
            somaTotal = 0;
            for (int k = 0; k < join.size(); k++) {
                somaTotal += join.get(k).getAvaliacao();
                cumulativas[k] = somaTotal;
            }
        }
        return selecionados;
    }

    private List<IndividuoInteiro> aplicarRoletaMinimizacao(List<IndividuoInteiro> join, int quantidade) {
        List<IndividuoInteiro> selecionados = new ArrayList<>(quantidade);
        // Pré-calcular somatório e probabilidades cumulativas
        double[] cumulativas = new double[join.size()];
        double somaTotal = 0;
        for (int k = 0; k < join.size(); k++) {
            double aval = join.get(k).getAvaliacao();
            double prob = (aval == 0) ? Double.MAX_VALUE / join.size() : 1.0 / aval;
            somaTotal += prob;
            cumulativas[k] = somaTotal;
        }
        if (Double.isInfinite(somaTotal)) {
            somaTotal = Double.MAX_VALUE / join.size() * join.size();
        }
        for (int j = 0; j < quantidade; j++) {
            double sorteado = random.nextDouble() * somaTotal;
            IndividuoInteiro escolhido = null;
            for (int k = 0; k < join.size(); k++) {
                if (cumulativas[k] >= sorteado) {
                    escolhido = join.get(k);
                    break;
                }
            }
            selecionados.add(escolhido);
            join.remove(escolhido);
            // Recalcular cumulativas após remoção
            somaTotal = 0;
            for (int k = 0; k < join.size(); k++) {
                double aval = join.get(k).getAvaliacao();
                double prob = (aval == 0) ? Double.MAX_VALUE / join.size() : 1.0 / aval;
                somaTotal += prob;
                cumulativas[k] = somaTotal;
            }
            if (Double.isInfinite(somaTotal)) {
                somaTotal = Double.MAX_VALUE / join.size() * join.size();
            }
        }
        return selecionados;
    }

    private boolean estaOtimizado(IndividuoInteiro melhor) {
        if (!melhor.isMaximizacao() && melhor.getAvaliacao() == 0)
            return true;
        if (melhor.isMaximizacao() && melhor.getAvaliacao() == Integer.MAX_VALUE)
            return true;
        return false;
    }

    private IndividuoInteiro melhorIndividuo(List<IndividuoInteiro> populacao) {
        IndividuoInteiro melhor = null;
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

    private void imprimirIndividuo(int geracao, IndividuoInteiro individuo) {
        System.out.println(" Geracao: " + (geracao + 1) + "° .... " + individuo.toString());
    }

}
