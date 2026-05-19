package optimization.interfaces;

import java.util.List;

public interface IndividuoInteiro {
    public List<IndividuoInteiro> recombinar(IndividuoInteiro outro);

    public IndividuoInteiro mutar();

    public double getAvaliacao();

    public boolean isMaximizacao();

    public int[] getGenes();
}
