package nrainhas;

import optimization.Ag;
import optimization.interfaces.IndividuoInteiro;

public class AgMain {
    public static void main(String[] args) throws Exception {

        // testando a implementacao das heuristicas
        Ag algoritmoGenetico = new Ag();
        IndNRainhasFactory factory = new IndNRainhasFactory(30);
        IndividuoInteiro melhor = algoritmoGenetico.executar(factory, 150, 30, 10000);
        System.out.println("\nMelhor Individuo da N-Rainhas:\n" + melhor.toString());

    }
}
