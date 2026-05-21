package functions.langermann;

import functions.IndividuoFuncao;

public class IndLangermann extends IndividuoFuncao {

    // exemplo indicado pelo exercicio para testes
    private double[][] A = {{3, 5}, {5, 2}, {2, 1}, {1, 4}, {7, 9}};
    private double[] c = {1.0, 2.0, 5.0, 2.0, 3.0};
    private int m = 5;
    private int qtdGenes = 2;

    public IndLangermann(int qtdGenes, boolean blx) {
        super(qtdGenes, blx);
    }

    public IndLangermann(double[] genes, boolean blx) {
        super(genes, blx);
    }

    @Override
    public double avaliar() {
        double valor = 0;
        for (int i = 0; i < m; i++) {
            double somatorio = somatorio(i);
            valor += c[i] * Math.exp((-1/Math.PI) * somatorio) * Math.cos(Math.PI * somatorio);
        }
        return valor;
    }

    private double somatorio(int i) {
        double total = 0;
        for (int j = 0; j < qtdGenes; j++) 
            total += Math.pow(genes[j] - A[i][j], 2);
        return total;
    }
    
}
