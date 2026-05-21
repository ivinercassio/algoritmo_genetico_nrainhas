package functions.levy;

import v2.Factory;
import v2.Individuo;

public class IndLevyFactory implements Factory {
    
    private int qtdGenes;
    private boolean blx;

    public IndLevyFactory(int qtdGenes, boolean blx) {
        this.blx = blx;
        this.qtdGenes = qtdGenes;
    }

    @Override
    public Individuo getInstance() {
        return new IndLevy(qtdGenes, blx);
    }
    
}
