
import java.util.ArrayList;
import java.util.HashMap;
public class BayesClassifier{
    private HashMap<String, HashMap<String,String>> hmap;
    private int[] classLabels;
    //hmap is as follows: name of feature -> values of feature -> 
    public BayesClassifier(){

    }
    //let y= class label and x1....xm = features
    //counts: cause how i've done it is I make a hashmap (or Dictionary, if youre a python user) with a string as the key, and another hashmap as the value.
    //The first map uses the label on the left as the key, and then the nested map for that is using each possible value as a key in that, 
    //and uses an int as the value, and when setting up, every value is 1
    public double[][] bayesTrain(ArrayList<Instance> insts){
        //initialise counts to 1

        for(Instance i : insts){
            //count(y)++
            for(String clas : i.getFeatures().keySet()){
                //count (name of feature -> value of feature -> class) ++

            }
        }

        //calculate total/denominators
        int class_total = 0;
        for(int i = 0; i<classLabels.length;i++){
            class_total += classLabels[i];
            for(String clas: hmap.keySet()){
                //total (feature, class) = 0
                for(String val : hmap.get(clas).keySet()){
                    //total(feature,class) = total(feature,class) + count(feature, value, class)
                }
            }
        }
        //calculate probabilities
        for(int i =0; i<classLabels.length;i++){
            //probability(y) = count(y)/class_total
            for(String clas : hmap.keySet()){
                for(String val : hmap.get(clas).keySet()){
                    //prob(feature, value, class) = count(feature, value, class)/total(feature, class)
                }
            }
        }


        return null;
    }

    public double classScore(Instance inst, int clasLabel, double[][]prob){
        return 0;
    }
}