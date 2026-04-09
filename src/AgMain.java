public class AgMain {
    public static void main(String[] args) throws Exception {
        
        IndNRainhasFactory factory = new IndNRainhasFactory(8);
        // tem atributos aqui que nao copie do quadro
        // AgMain chama classe Ag

        Ag algoritmoGenetico = new Ag();
        algoritmoGenetico.executar(factory, 20, 4, 20);
    }
}
