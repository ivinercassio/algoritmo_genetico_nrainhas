package nrainhas;

import optimization.AgInteiro;
import optimization.IndividuoInteiro;

public class AgMain {
    public static void main(String[] args) throws Exception {

        // testando a implementacao das heuristicas
        AgInteiro algoritmoGenetico = new AgInteiro();
        IndNRainhasFactory factory = new IndNRainhasFactory(8);
        IndividuoInteiro melhor = algoritmoGenetico.executar(factory, 20, 4, 2000);
        System.out.println("\nMelhor Individuo da N-Rainhas:\n" + melhor.toString());

    }
}
