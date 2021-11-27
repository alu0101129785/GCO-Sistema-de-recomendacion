package ssi.sistema_recomendacion;

import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;

/**
 *
 * @author Saúl
 */
public abstract class SimMeasure {
  public abstract double measure(int[] u, int[] v);
  public List<Integer> getSuv(int[] u, int[] v) {
    List<Integer> Suv = new ArrayList<>();
    for (int i = 0; i < u.length; i++) {
      if(u[i] != -1 && v[i] != -1) {
        Suv.add(i);
      }
    }
    return Suv;
  }
}
