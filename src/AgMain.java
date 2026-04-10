public class AgMain {
    public static void main(String[] args) throws Exception {
        
        IndNRainhasFactory factory = new IndNRainhasFactory(4);
        Ag algoritmoGenetico = new Ag();
        algoritmoGenetico.executar(factory, 20, 4, 20);
    }
}
