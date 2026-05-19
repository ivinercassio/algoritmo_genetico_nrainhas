package v2;

import java.util.List;

public abstract class Individuo {
    private double avaliacao;
    private boolean avaliado = false;

    public abstract List<Individuo> recombinar(Individuo outro);
    public abstract Individuo mutar();
    public abstract double avaliar();

    public double getAvaliacao(){
        if(!avaliado)
            avaliacao = avaliar();
        return avaliacao;
    }

}
