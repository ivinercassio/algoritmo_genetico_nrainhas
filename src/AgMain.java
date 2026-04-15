public class AgMain {
    public static void main(String[] args) throws Exception {
        
        IndNRainhasFactory factory = new IndNRainhasFactory(4);
        Ag algoritmoGenetico = new Ag();
        Individuo melhor = algoritmoGenetico.executar(factory, 20, 4, 100000);
        System.out.println("\nMelhor Individuo da N-Rainhas:\n" + melhor.toString());
    }
}
