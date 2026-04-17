public class AgMain {
    public static void main(String[] args) throws Exception {

        // testando a implementacao das heuristicas
        IndNRainhasFactory factory = new IndNRainhasFactory(6);
        Ag algoritmoGenetico = new Ag();
        Individuo melhor = algoritmoGenetico.executar(factory, 20, 4, 500);
        System.out.println("\nMelhor Individuo da N-Rainhas:\n" + melhor.toString());

        // AS VEZES O ALGORITMO TRAVA EM UM INDIVIDUO SEM EVOLUIR
        // GERACOES INTEIRAS COM PRATICAMENTE 1 CONJUNTO DE GENES
    }
}
