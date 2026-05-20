package v2;

import java.util.List;

public abstract class Individuo {
    public abstract List<Individuo> recombinar(Individuo outro);
    public abstract Individuo mutar();
    public abstract double avaliar();
    public abstract boolean isMaximizacao();
    public abstract boolean isOtimizado();
    public abstract double[] getGenes();
    public abstract double getAvaliacao();

}
