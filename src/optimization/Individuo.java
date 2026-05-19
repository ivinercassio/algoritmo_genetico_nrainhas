package optimization;

import java.util.List;

public abstract class Individuo {
    protected double avaliacao;
    protected boolean avaliado = false;

    public abstract List<Individuo> recombinar(Individuo outro);
    public abstract Individuo mutar();
    public abstract double avaliar();

    public double getAvaliacao(){
        if(!avaliado)
            avaliacao = avaliar();
        return avaliacao;
    }
}
