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
            for (int i = 0; i < qtdGenes; i++) {
                double distance = Math.abs(this.genes[i] - outro.getGenes()[i]);
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
        boolean mutado = false;
        Individuo mutante = new IndDixonPrice(this.genes, this.blx);
        for (int i = 0; i < genes.length; i++)
            if (random.nextInt() < txMatacao) {
                mutante.getGenes()[i] += random.nextGaussian(0, 0.1);
                mutado = true;
            }
        if (!mutado) // garante que haja mutacao
            mutante.getGenes()[random.nextInt(qtdGenes)] += random.nextGaussian(0, 0.1);
        return mutante;
    }

    @Override
    public double avaliar() {
        double valor = Math.pow((genes[0] - 1), 2);
        for (int i = 1; i < qtdGenes; i++) 
            valor += i * Math.pow((2 * Math.pow(genes[i], 2) - genes[i-1]), 2);
        return valor;
    }

    @Override
    public boolean isMaximizacao() {
        return maximizacao;
    }

    @Override
    public boolean isOtimizado() {
        avaliacao = (int)(avaliacao * 100);
        avaliacao /= 100;
        return avaliacao == 0.00; // minimo global
    }

    @Override
    public double[] getGenes() {
        return this.genes;
    }

    @Override
    public double getAvaliacao() {
        if (!avaliado)
            avaliacao = avaliar();
        return avaliacao;
    }

    @Override
    public String toString() {
        String genes = "{ ";
        for (int i = 0; i < this.genes.length; i++)
            genes += String.format("%.2f ", this.genes[i]);
        genes += "}";
        return String.format("Individuo: { genes: %s, avaliacao: %.2f }", genes, this.getAvaliacao());
    }
    
}
