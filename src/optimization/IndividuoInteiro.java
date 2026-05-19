package optimization;

import java.util.List;

public abstract class IndividuoInteiro extends Individuo{

    public abstract boolean isMaximizacao();
    public abstract int[] getGenes();
    public abstract List<IndividuoInteiro> recombinar(IndividuoInteiro pai2);
    public abstract IndividuoInteiro mutar();
}
