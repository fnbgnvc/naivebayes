
import java.util.ArrayList;
import java.util.HashMap;
public class BayesClassifier{
    private HashMap<String, HashMap<String,String>> hmap;
    private ArrayList<String> classLabels;
    //hmap is as follows: name of feature -> values of feature -> how many times it occurs
    public BayesClassifier(){

    }
    //let y= class label and x1....xm = features
    //counts: make a hashmap with a string as the key, and another hashmap as the value.
    //The first map uses the label on the left as the key, and then the nested map for that is using each possible value as a key in that, 
    //and uses an int as the value, and when setting up, every value is 1
    public double[][] bayesTrain(ArrayList<Instance> insts){
        //initialise counts to 1

        for(Instance i : insts){
            //count(y)++
            for(String clas : i.getFeatures().keySet()){
                //count (name of feature -> value of feature -> count) ++

            }
        }

        //calculate total/denominators
        int class_total = 0;
        for(String i : classLabels){
            class_total += classLabels.indexOf(i);
            for(String clas: hmap.keySet()){
                //total (feature, class) = 0
                for(String val : hmap.get(clas).keySet()){
                    //total(feature,class) += count(feature, value, class)
                }
            }
        }
        //calculate probabilities
        for(String i : classLabels){
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