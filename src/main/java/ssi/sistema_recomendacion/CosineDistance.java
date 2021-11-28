package ssi.sistema_recomendacion;

import static java.lang.Math.sqrt;
import java.util.List;

/**
 *
 * @author Saúl
 */
public class CosineDistance extends SimMeasure {

  @Override
  public double measure(int[] u, int[] v) {
    List<Integer> Suv = getSuv(u, v);
    double sumNum = 0, sumDen1 = 0, sumDen2 = 0;    
    for(int i: Suv) {
      double rui = u[i];
      double rvi = v[i];
      sumNum += rui * rvi;
      sumDen1 += rui * rui;
      sumDen2 += rvi * rvi;
    }
    double den = sqrt(sumDen1) * sqrt(sumDen2);
    return sumNum/den;
  }
 
}
