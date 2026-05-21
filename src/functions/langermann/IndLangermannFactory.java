package functions.langermann;

import v2.Factory;
import v2.Individuo;

public class IndLangermannFactory implements Factory {

    private int qtdGenes;
    private boolean blx;

    public IndLangermannFactory(int qtdGenes, boolean blx) {
        this.qtdGenes = qtdGenes;
        this.blx = blx;
    }
    @Override
    public Individuo getInstance() {
        return new IndLangermann(qtdGenes, blx);
    }
    
}
