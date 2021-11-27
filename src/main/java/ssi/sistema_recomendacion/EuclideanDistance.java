package ssi.sistema_recomendacion;

import static java.lang.Math.sqrt;
import java.util.List;

/**
 *
 * @author Saúl
 */
public class EuclideanDistance extends SimMeasure {

  @Override
  public double measure(int[] u, int[] v) {
    List<Integer> P = getSuv(u, v);
    double sum = 0;    
    for(int i: P) {
      double rui = u[i];
      double rvi = v[i];
      double ruirvi = rui - rvi;
      sum += ruirvi * ruirvi;
    }
    return sqrt(sum);
  }
  
}
