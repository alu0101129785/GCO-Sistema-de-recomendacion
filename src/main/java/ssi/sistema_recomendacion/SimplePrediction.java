package ssi.sistema_recomendacion;

import static java.lang.Math.abs;
import java.util.List;

/**
 *
 * @author Saúl
 */
public class SimplePrediction extends Prediction{

  @Override
  public double predict(List<Pair> sim, List<int[]> r, int i, int k, int u) {
    double sumNum = 0, sumDen = 0;
    for(Pair v: nku(sim, k)){
      sumNum += v.d * r.get(v.index)[i];
      sumDen += abs(v.d);
    }
    return sumNum / sumDen;
  }
}
