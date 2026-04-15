import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IndNRainhas implements Individuo {

    private double txMatacao = 0.3;
    private int[] genes;
    private int qtdGenes;
    private static Random random;
    private static boolean maximizacao;

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
        this.genes = new int[qtdGenes];
        random = new Random();
        for (int i = 0; i < genes.length; i++)
            genes[i] = random.nextInt(0, qtdGenes);
        this.qtdGenes = qtdGenes;
        this.maximizacao = false;
    }

    public IndNRainhas(int qtdGenes, int[] genes) {
        this.genes = genes;
        this.qtdGenes = qtdGenes;
    }

    @Override
    public List<Individuo> recombinar(Individuo pai2) {
        // criar dois filhos com o crossover de um corte aleatorio entre pai1 e pai2
        List<Individuo> filhos = new ArrayList<>(2);
        int posicaoCorte = random.nextInt(1, qtdGenes - 1);
        int[] genesFilho1 = new int[qtdGenes];
        int[] genesFilho2 = new int[qtdGenes];
        for (int i = 0; i < qtdGenes; i++)
            if (i < posicaoCorte) {
                genesFilho1[i] = this.genes[i];
                genesFilho2[i] = pai2.getGenes()[i];
            } else {
                genesFilho1[i] = pai2.getGenes()[i];
                genesFilho2[i] = this.genes[i];
            }
        filhos.add(new IndNRainhas(qtdGenes, genesFilho1));
        filhos.add(new IndNRainhas(qtdGenes, genesFilho2));
        return filhos;
    }

    @Override
    public Individuo mutar() {
        // gera outro individuo com o conteudo do this.genes mutado, de acordo com a txMutacao
        IndNRainhas mutante = new IndNRainhas(qtdGenes, this.genes);
        for (int i = 0; i < genes.length; i++)
            if (random.nextInt() < txMatacao)
                mutante.genes[i] = random.nextInt(0, qtdGenes);
        return mutante;
    }

    @Override
    public double getAvaliacao() {
        // contabiliza as colisoes e retorna o valor
        int cont = 0;
        for (int i = 0; i < genes.length - 1; i++) {
            for (int j = i + 1; j < genes.length; j++) {
                if (genes[i] == genes[j])
                    cont++;
                if (genes[i] == genes[j] - (j - i))
                    cont++;
                if (genes[i] == genes[j] + (j - i))
                    cont++;
            }
        }
        return cont;
    }

    @Override
    public boolean isMaximizacao() {
        return this.maximizacao;
    }

    @Override
    public int[] getGenes() {
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
