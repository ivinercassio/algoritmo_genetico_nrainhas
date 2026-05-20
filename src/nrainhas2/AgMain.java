package nrainhas2;

import v2.Ag;
import v2.Individuo;

public class AgMain {
    public static void main(String[] args) {
        // testando a implementacao das heuristicas
        Ag algoritmoGenetico = new Ag();
        IndNRainhasFactory factory = new IndNRainhasFactory(8);
        Individuo melhor = algoritmoGenetico.executar(factory, 20, 4, 2000);
        System.out.println("\nMelhor Individuo da N-Rainhas:\n" + melhor.toString());
    }
}
