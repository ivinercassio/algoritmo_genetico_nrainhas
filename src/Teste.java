import java.util.ArrayList;
import java.util.List;

public class Teste {

    public Teste(Factory factory) {
        List<Individuo> populacaoInicial = new ArrayList<>(20);
        for (int i = 0; i < 20; i++)
            populacaoInicial.add(factory.getInstance());

        for (int j = 0; j < populacaoInicial.size() - 1; j++)
            for (int j2 = j; j2 < populacaoInicial.size(); j2++)
                if (populacaoInicial.get(j).getAvaliacao() < populacaoInicial.get(j2).getAvaliacao()) {
                    Individuo aux = populacaoInicial.get(j);
                    populacaoInicial.set(j, populacaoInicial.get(j2));
                    populacaoInicial.set(j2, aux);
                }

        System.out.println("\nÚltima geração TESTE: ");
        for (int i = 0; i < populacaoInicial.size(); i++) {
            System.out.println(populacaoInicial.get(i).toString());
        }
        System.out.println();

        Individuo melhor = null;
        double avaliacao = Double.MAX_VALUE;
        for (int j = 0; j < populacaoInicial.size(); j++) {
            if (populacaoInicial.get(j).getAvaliacao() < avaliacao) {
                melhor = populacaoInicial.get(j);
                avaliacao = populacaoInicial.get(j).getAvaliacao();
            }
        }

        Individuo ultimo = populacaoInicial.removeLast();
        System.out.println("Removendo o ultimo: " + ultimo.toString());
        populacaoInicial.add(ultimo);
        System.out.println("Removendo o melhor: " + melhor.toString());
    }

}
