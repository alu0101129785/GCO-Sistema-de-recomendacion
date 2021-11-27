package ssi.sistema_recomendacion;

import static java.lang.Math.sqrt;
import java.util.List;

/**
 *
 * @author Saúl
 */
public class Pearson extends SimMeasure {

  public static double meanWithNan(int[] v, List<Integer> Suv) {
    double m = 0;
    for (Integer i : Suv) {
      m += v[i];
    }  
    m /= Suv.size();
    return m;
  }
  
  @Override
  public double measure(int[] u, int[] v) {
    List<Integer> Suv = getSuv(u, v);
    double ru = Pearson.meanWithNan(u, Suv);
    double rv = Pearson.meanWithNan(v, Suv);
    double sumNum = 0, sumDen1 = 0, sumDen2 = 0;    
    for(int i: Suv) {
      double ruiru = u[i] - ru;
      double rvirv = v[i] - rv;
      sumNum += ruiru * rvirv;
      sumDen1 += ruiru * ruiru;
      sumDen2 += rvirv * rvirv;
    }
    double den = sqrt(sumDen1) * sqrt(sumDen2);
    //System.out.println("" + sumNum/den);
    return sumNum/den;
  }
}
