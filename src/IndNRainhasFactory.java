public class IndNRainhasFactory implements Factory {

    private int qtdGenes;

    public IndNRainhasFactory(int qtdGenes) {
        this.qtdGenes = qtdGenes;
    }

    @Override
    public Individuo getInstance() {
        return new IndNRainhas(qtdGenes);
    }
    
}
