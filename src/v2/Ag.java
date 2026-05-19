package v2;

import java.util.List;

import optimization.Individuo;

public class Ag {

    public void executar(Factory factory, int numPopulacao, int numElite, int qtdGeracoes){}

    private List<Individuo> aplicarRecombinacao(List<Individuo> populacaoInicial){ return null; }

    private List<Individuo> aplicarMutacao(List<Individuo> populacaoInicial) { return null; }

    private List<Individuo> aplicarElitismo(int numElite, List<Individuo> join) { return null; }

    private List<Individuo> aplicarRoleta(List<Individuo> join, int quantidade) { return null; }

    private List<Individuo> aplicarRoletaMaximizacao(List<Individuo> join, int quantidade) { return null; }

    private List<Individuo> aplicarRoletaMinimizacao(List<Individuo> join, int quantidade) { return null; }

    private boolean estaOtimizado(Individuo melhor) { return false; }

    private Individuo melhorIndividuo(List<Individuo> populacao) { return null; }

    private void imprimirIndividuo(int geracao, Individuo individuo){}
}
