package functions.dixonprice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import v2.Individuo;

public class IndDixonPrice extends Individuo {

    private static Random random;
    private static boolean maximizacao;

    private double txMatacao = 0.3;
    private int qtdGenes;
    private double[] genes;
    private double avaliacao;
    private boolean avaliado;
    private boolean blx;

    public IndDixonPrice(int qtdGenes, boolean blx) {
        random = new Random();
        maximizacao = false;
        this.qtdGenes = qtdGenes;
        this.genes = new double[qtdGenes];
        // dominio da funcao pertence a [-10,10]
        for (int i = 0; i < genes.length; i++) 
            genes[i] = -10 + (20 * random.nextDouble());
        avaliado = false;
        this.blx = blx;
    }

    public IndDixonPrice(double[] genes, boolean blx) {
        this.genes = genes;
        this.qtdGenes = genes.length;
        avaliado = false;
        maximizacao = false;
        this.blx = blx;
    }

    @Override
    public List<Individuo> recombinar(Individuo outro) {
        double[] filho1 = new double[qtdGenes];
        double[] filho2 = new double[qtdGenes];
        
        if (blx) { // crossover blx-alpha
            double alpha = random.nextGaussian(0, 0.1);
            double distance = 0;
            for (int i = 0; i < qtdGenes; i++) 
                distance += Math.pow((this.genes[i] + outro.getGenes()[i]), 2);
            distance = Math.sqrt(distance);
            for (int i = 0; i < qtdGenes; i++) {
                filho1[i] = this.genes[i] + alpha * distance;
                filho2[i] = outro.getGenes()[i] + alpha * distance;
            }
            
        } else { // recombinacao aritmetica
            double alpha = 1.0/3.0;
            for (int i = 0; i < qtdGenes; i++) {
                filho2[i] = alpha * this.genes[i] + (1 - alpha) * outro.getGenes()[i];
                filho1[i] = alpha * outro.getGenes()[i] + (1 - alpha) * this.genes[i];
            }
        }
        List<Individuo> filhos = new ArrayList<>();
        filhos.add(new IndDixonPrice(filho1, blx));
        filhos.add(new IndDixonPrice(filho2, blx));
        return filhos;
    }

    @Override
    public Individuo mutar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mutar'");
    }

    @Override
    public double avaliar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'avaliar'");
    }

    @Override
    public boolean isMaximizacao() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isMaximizacao'");
    }

    @Override
    public boolean isOtimizado() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOtimizado'");
    }

    @Override
    public double[] getGenes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGenes'");
    }

    @Override
    public double getAvaliacao() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAvaliacao'");
    }
    
}
