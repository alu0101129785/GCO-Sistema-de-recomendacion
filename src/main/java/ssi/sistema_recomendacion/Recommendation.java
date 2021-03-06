package ssi.sistema_recomendacion;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Recommendation {

  public static List<int[]> readMatrix(String filename) {
    // Fichero del que queremos leer
    File matriz_entrada = new File (filename);
    Scanner s = null;
    LinkedList<int[]> users = new LinkedList<int[]> ();
    
    try {
      // Leemos el contenido del fichero
      //System.out.println("\nLeyendo el contenido del fichero ...\n");
      s = new Scanner(matriz_entrada);

      // Leemos linea a linea el fichero
      while (s.hasNextLine()) {
        String linea = s.nextLine(); 	// Guardamos la linea en un String
        String[] items = linea.split("\\s");
        int[] values = new int[items.length];
        for(int i = 0; i < items.length; i++) {
          if(items[i].equals("-")) {
            values[i] = -1;
          } else {
            values[i] = Integer.parseInt(items[i]);
          }
        }
        users.add(values); 
      }
    } catch (Exception ex) {
        System.out.println("Mensaje: " + ex.getMessage());
    }  finally {
      // Cerramos el fichero tanto si la lectura ha sido correcta o no
        try {
          if (s != null)
            s.close();
        } catch (Exception ex2) {
            System.out.println("Mensaje 2: " + ex2.getMessage());
        }
    }
    return users;
  }
  
  public static double predictRating(List<int[]> ratings, SimMeasure distance, int k, Prediction predType, int userindex, int itemindex) {
    List<Pair> distancias = new ArrayList<>();
    System.out.println("\n--------------------------------------------------");
    System.out.println("* Similaridad entre usuarios: ");
    for(int i = 0; i < ratings.size(); i++) {
      if(i != userindex) {
        double aux = distance.measure(ratings.get(userindex), ratings.get(i));
        distancias.add(new Pair(i, aux));
        System.out.println(" > Similitud del usuario " + userindex + " con el usuario " + i + ": " + aux);
      }
    }
    System.out.println("--------------------------------------------------\n");
    double p = predType.predict(distancias, ratings, itemindex, k, userindex);
    System.out.println("--------------------------------------------------");
    System.out.println("* Usuario: " + userindex + " item: " + itemindex + " --> valoraci??n estimada: " + p);
    System.out.println("--------------------------------------------------\n");
    return p; 
  }
  
  public static List<double[]> collaborativeFilter(List<int[]> ratings, SimMeasure distance, int k, Prediction predType){
    List<double[]> result = new ArrayList<>();
    for(int i = 0; i < ratings.size(); i++) {
      int[] tmp =  ratings.get(i);
      double[] copy = new double[tmp.length]; 
      for(int j = 0; j < tmp.length; j++) {
        if(tmp[j] == -1){
          copy[j] = predictRating(ratings, distance, k, predType, i, j);
        } else {
          copy[j] = tmp[j];
        }
      }
      result.add(copy);
    }
    System.out.println("* Matriz de utilidad: \n");
    return result;
  }
  
  public static void main(String[] args) {
    if(args.length != 4){
      System.out.println("N??mero err??neo de argumentos");
      System.exit(1);
    }
    List<int[]> userRatings = readMatrix(args[0]);
    /*for(int[] u: userRatings) {
      System.out.println(Arrays.toString(u));
    }
    */
    int metric = Integer.parseInt(args[1]);
    if (metric < 1 || metric > 3) {
      System.out.println("M??trica no v??lida. Posibles opciones: ");
      System.out.print("1. Correlaci??n de Pearson\n");
      System.out.print("2. Distancia Coseno\n");
      System.out.print("3. Distancia Eucl??dea\n");
      System.exit(1);
    }
    
    int nbs = Integer.parseInt(args[2]);
    if (nbs < 3 || nbs > userRatings.size() - 1) {
      System.out.println("N??mero de vecinos no v??lido. Se espera un valor igual o mayor a 3");
      System.exit(1);
    }
    
    int pred = Integer.parseInt(args[3]);
    if (pred < 1 || pred > 2) {
      System.out.println("Predicci??n no v??lida. Las opciones disponibles son: ");
      System.out.print("1. Predicci??n Simple\n");
      System.out.print("2. Diferencia con la media\n");
      System.exit(1);
    }
    
    SimMeasure measure;
    switch(metric) {
      case 1: 
        measure = new Pearson();
      break;
      
      case 2:
        measure = new CosineDistance();
      break;
      
      default:
        measure = new EuclideanDistance();
      break;
    }
    
    Prediction prediction;
    if (pred == 1) {
      prediction = new SimplePrediction();
    } else {
      prediction = new MeanDifference();
    }
    
    List<double[]> output = collaborativeFilter(userRatings, measure, nbs, prediction);
    for (double[] ds : output) {
      System.out.println(Arrays.toString(ds));
    }
  }
}

class Pair {
  public int index;
  public double d;
  public Pair(int i, double d) {
    index = i;
    this.d = d;
  }
}