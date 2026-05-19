package optimization;

import java.util.List;

public abstract class IndividuoReal extends Individuo{

    public abstract boolean isMaximizacao();
    public abstract double[] getGenes();
    public abstract List<IndividuoReal> recombinar(IndividuoReal pai2);
    public abstract IndividuoReal mutar();
}