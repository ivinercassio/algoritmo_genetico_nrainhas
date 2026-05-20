package nrainhas2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import v2.Individuo;

public class IndNRainhas extends Individuo {

    private static Random random;
    private static boolean maximizacao;

    private double txMatacao = 0.3;
    private int qtdGenes;
    private double[] genes;
    private double avaliacao;
    private boolean avaliado;

    // genes = {2, 2, 0, 1};
    //   -----------------
    // 0 |   |   | x |   |
    //   -----------------
    // 1 |   |   |   | x |
    //   -----------------
    // 2 | x | x |   |   |
    //   -----------------
    // 3 |   |   |   |   |
    //   -----------------

    public IndNRainhas(int qtdGenes) {
        this.genes = new double[qtdGenes];
        random = new Random();
        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i < genes.length; i++) 
            lines.add(i);
        for (int i = 0; i < genes.length; i++)
            genes[i] = lines.remove(random.nextInt(lines.size()));
        this.qtdGenes = qtdGenes;
        maximizacao = false;
        avaliado = false;
    }

    public IndNRainhas(int qtdGenes, double[] genes) {
        this.genes = genes;
        this.qtdGenes = qtdGenes;
        avaliado = false;
    }

    @Override
    public List<Individuo> recombinar(Individuo outro) {
        // criar dois filhos com o crossover de um corte aleatorio entre pai1 e pai2
        List<Individuo> filhos = new ArrayList<>(2);
        int posicaoCorte = random.nextInt(1, qtdGenes - 1);
        double[] genesFilho1 = new double[qtdGenes];
        double[] genesFilho2 = new double[qtdGenes];
        for (int i = 0; i < qtdGenes; i++)
            if (i < posicaoCorte) {
                genesFilho1[i] = this.genes[i];
                genesFilho2[i] = outro.getGenes()[i];
            } else {
                genesFilho1[i] = outro.getGenes()[i];
                genesFilho2[i] = this.genes[i];
            }

        // tratamento para genes filho 1
        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i < qtdGenes; i++) 
            lines.add(i);
        for (int i = 0; i < qtdGenes; i++) 
            try {
                lines.remove(genesFilho1[i]);
            } catch (Exception e) {
                genesFilho1[i] = Integer.MIN_VALUE;
            }
        for (int i = 0; i < qtdGenes; i++) 
            if (genesFilho1[i] == Integer.MIN_VALUE) 
                genesFilho1[i] = lines.remove(random.nextInt(lines.size()));

        // tratamento para genes filho 2
        for (int i = 0; i < qtdGenes; i++) 
            lines.add(i);
        for (int i = 0; i < qtdGenes; i++) 
            try {
                lines.remove(genesFilho2[i]);
            } catch (Exception e) {
                genesFilho2[i] = Integer.MIN_VALUE;
            }
        for (int i = 0; i < qtdGenes; i++) 
            if (genesFilho2[i] == Integer.MIN_VALUE) 
                genesFilho2[i] = lines.remove(random.nextInt(lines.size()));

        filhos.add(new IndNRainhas(qtdGenes, genesFilho1));
        filhos.add(new IndNRainhas(qtdGenes, genesFilho2));
        return filhos;
    }

    @Override
    public Individuo mutar() {
        // gera outro individuo com o conteudo do this.genes mutado, de acordo com a txMutacao
        IndNRainhas mutante = new IndNRainhas(qtdGenes, this.genes.clone());
        for (int i = 0; i < genes.length; i++)
            if (random.nextInt() < txMatacao)
                mutante.genes[i] = random.nextInt(0, qtdGenes);
        return mutante;
    }

    @Override
    public double avaliar() {
        // Otimizado para O(N) usando contadores de conflitos
        int[] diagPrincipal = new int[2 * qtdGenes - 1];
        int[] diagSecundaria = new int[2 * qtdGenes - 1];
        int conflitos = 0;

        for (int i = 0; i < qtdGenes; i++) {
            int dp = (int) (genes[i] - i + (qtdGenes - 1));
            int ds = (int) (genes[i] + i);
            conflitos += diagPrincipal[dp]++;
            conflitos += diagSecundaria[ds]++;
        }

        avaliado = true;
        avaliacao = conflitos;
        return conflitos;
    }

    @Override
    public boolean isMaximizacao() {
        return maximizacao;
    }

    @Override
    public double getAvaliacao() {
        if(!avaliado)
            avaliacao = avaliar();
        return avaliacao;
    }

    @Override
    public boolean isOtimizado() {
        return (getAvaliacao() == 0);
    }

    @Override
    public double[] getGenes() {
        return genes;
    }

    @Override
    public String toString() {
        String genes = "{ ";
        for (int i = 0; i < this.genes.length; i++)
            genes += this.genes[i] + " ";
        genes += "}";
        return "Individuo: { genes: " + genes + ", avaliacao: " + this.getAvaliacao() + " }";
    }
    
}
