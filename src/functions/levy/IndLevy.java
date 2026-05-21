package functions.levy;

import functions.IndividuoFuncao;

public class IndLevy extends IndividuoFuncao {

    public IndLevy(int qtdGenes, boolean blx) {
        super(qtdGenes, blx);
    }

    public IndLevy(double[] genes, boolean blx) {
        super(genes, blx);
    }

    @Override
    public double avaliar() {
        double valor = Math.pow(Math.sin(Math.PI * w(0)), 2);
        for (int i = 1; i < qtdGenes-1; i++) 
            valor += ( Math.pow(w(i)-1, 2) * (1 + 10*Math.pow(Math.sin(Math.PI * w(i) + 1), 2)) + Math.pow((w(qtdGenes-1) - 1), 2) * (1 + Math.pow(Math.sin(2*Math.PI*w(qtdGenes-1)), 2)) );
        return valor;
    }

    private double w(int i) {
        return 1 + ((genes[i] - 1)/4);
    }
    
}
