package functions.levy;

import v2.Ag;
import v2.Individuo;

public class MainLevy {
    public static void main(String[] args) {
        Ag algoritmoGenetico = new Ag();
        IndLevyFactory factory = new IndLevyFactory(3, false);
        Individuo melhor = algoritmoGenetico.executar(factory, 20, 4, 2000);
        System.out.println("\nMelhor Individuo da Levy:\n" + melhor.toString());
    }
}
