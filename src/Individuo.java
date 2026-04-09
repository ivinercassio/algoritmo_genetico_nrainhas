import java.util.List;

public interface Individuo {
    public List<Individuo> recombinar(Individuo outro);

    public Individuo mutar();

    public double getAvaliacao();

    public boolean isMaximizacao();

    public int[] getGenes();
}
