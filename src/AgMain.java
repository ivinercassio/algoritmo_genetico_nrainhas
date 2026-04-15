public class AgMain {
    public static void main(String[] args) throws Exception {

        IndNRainhasFactory factory = new IndNRainhasFactory(4);
        Ag algoritmoGenetico = new Ag();
        Individuo melhor = algoritmoGenetico.executar(factory, 20, 4, 500);
        System.out.println("\nMelhor Individuo da N-Rainhas:\n" + melhor.toString());
    }

    // testando a implementacao das heuristicas
    // melhor minimizacao alcancada foi 1 colisao
}
