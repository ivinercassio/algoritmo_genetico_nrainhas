package functions.dixonprice;

import functions.IndividuoFuncao;

public class IndDixonPrice extends IndividuoFuncao {

    public IndDixonPrice(int qtdGenes, boolean blx) {
        super(qtdGenes, blx);
    }

    public IndDixonPrice(double[] genes, boolean blx) {
        super(genes, blx);
    }

    @Override
    public double avaliar() {
        double valor = Math.pow((genes[0] - 1), 2);
        for (int i = 1; i < qtdGenes; i++) 
            valor += i * Math.pow((2 * Math.pow(genes[i], 2) - genes[i-1]), 2);
        return valor;
    }
    
}
