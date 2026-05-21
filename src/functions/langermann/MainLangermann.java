package functions.langermann;

import v2.Ag;
import v2.Individuo;

public class MainLangermann {
    public static void main(String[] args) {
        Ag algoritmoGenetico = new Ag();
        // qtdGenes deve ser 2 por causa do exemplo indicado 
        IndLangermannFactory factory = new IndLangermannFactory(2, false);
        Individuo melhor = algoritmoGenetico.executar(factory, 20, 4, 2000);
        System.out.println("\nMelhor Individuo da Dixon-Price:\n" + melhor.toString());
    }
}
