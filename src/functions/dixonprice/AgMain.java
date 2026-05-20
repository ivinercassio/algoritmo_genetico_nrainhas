package functions.dixonprice;

import v2.Ag;
import v2.Individuo;

public class AgMain {
    public static void main(String[] args) {
        Ag algoritmoGenetico = new Ag();
        IndDixonPriceFactory factory = new IndDixonPriceFactory(8, false);
        Individuo melhor = algoritmoGenetico.executar(factory, 20, 4, 2000);
        System.out.println("\nMelhor Individuo da Dixon-Price:\n" + melhor.toString());
    }
}
