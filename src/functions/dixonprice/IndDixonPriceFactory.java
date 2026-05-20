package functions.dixonprice;

import v2.Factory;
import v2.Individuo;

public class IndDixonPriceFactory implements Factory {
    private int qtdGenes;
    private boolean blx;

    public IndDixonPriceFactory(int qtdGenes, boolean blx) {
        this.qtdGenes = qtdGenes;
        this.blx = blx;
    }

    @Override
    public Individuo getInstance() {
        return new IndDixonPrice(qtdGenes, blx);
    }
    
}
