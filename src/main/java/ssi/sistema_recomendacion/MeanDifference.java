package ssi.sistema_recomendacion;

import static java.lang.Math.abs;
import java.util.List;

/**
 *
 * @author Saúl
 */
public class MeanDifference extends Prediction {

  public static double meanWithNan(int[] v) {
    double m = 0;
    int count = 0;
    for (int i = 0; i < v.length; i++) {
      if(v[i] != -1) {
        m += v[i];
        count++;
      }
    }  
    m /= count;
    return m;
  }
  
  
 @Override
  public double predict(List<Pair> sim, List<int[]> r, int i, int k, int u) {
    double ru = MeanDifference.meanWithNan(r.get(u));
    double sumNum = 0, sumDen = 0;
    for(Pair v: nku(sim, k)){
      double rv = MeanDifference.meanWithNan(r.get(v.index));
      sumNum += v.d * (r.get(v.index)[i] - rv);
      sumDen += abs(v.d);
    }
    //System.out.println("" + ru + (sumNum / sumDen));
    return ru + (sumNum / sumDen);
  }
}
