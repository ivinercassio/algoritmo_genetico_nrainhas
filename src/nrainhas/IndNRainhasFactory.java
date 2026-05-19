package nrainhas;

import optimization.interfaces.Factory;
import optimization.interfaces.IndividuoInteiro;

public class IndNRainhasFactory implements Factory {

    private int qtdGenes;

    public IndNRainhasFactory(int qtdGenes) {
        this.qtdGenes = qtdGenes;
    }

    @Override
    public IndividuoInteiro getInstance() {
        return new IndNRainhas(qtdGenes);
    }
    
}
