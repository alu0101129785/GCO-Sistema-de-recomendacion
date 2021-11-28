package ssi.sistema_recomendacion;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Saúl
 */
public abstract class Prediction {
  public abstract double predict(List<Pair> sim, List<int[]> r, int i, int k, int u);
  public List<Pair> nku(List<Pair> sim, int k) {
    List<Pair> simList = new ArrayList<>();
    ArrayList<Pair> nku = new ArrayList<>();
    simList.addAll(sim);
    for(int i = 0; i < k; i++) {
      double max = -2;
      int maxindex = -1;
      for(int j = 0; j < simList.size(); j++) {
        if(simList.get(j).d > max) {
          max = simList.get(j).d;
          maxindex = j;
        }
      }
      // índices de los usarios más cercanos
      nku.add(simList.get(maxindex));
      simList.remove(maxindex);
    }
    System.out.println("--------------------------------------------------");
    System.out.print("* Vecinos seleccionados: ");
    for (Pair pair : nku) {
      System.out.print(" " + pair.index);
    }
    System.out.println("\n--------------------------------------------------");
    return nku;
  } 
}