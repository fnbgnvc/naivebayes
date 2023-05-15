
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class BayesClassifier{
    public ArrayList<String> classLabels;
    public ArrayList<Instance> instances;
    public HashMap<String,Integer> class_count;
    public HashMap<String, HashMap<String,HashMap<String,Integer>>> value_count;
    public HashMap<String,Integer> total_class;
    public HashMap<String, HashMap<String,Integer>> total_by_feature_class;
    public HashMap<String, HashMap<String, HashMap<String, Double>>> probtable;
    public double[] class_probs;
    
    //hmap is as follows: name of feature -> values of feature -> how many times it occurs
    public BayesClassifier(String trainFile, String testFile){
        
        this.classLabels=new ArrayList<String>();
        this.instances = readFile(trainFile);
        this.class_probs = new double[classLabels.size()];
        this.probtable = bayesTrain(this.instances);
        System.out.println("hi");

    }
    //let y= class label and x1....xm = features
    //counts: make a hashmap with a string as the key, and another hashmap as the value.
    //The first map uses the label on the left as the key, and then the nested map for that is using each possible value as a key in that, 
    //and uses an int as the value, and when setting up, every value is 1
    public HashMap<String,HashMap<String,HashMap<String,Double>>> bayesTrain(ArrayList<Instance> insts){
        //initialise counts to 1
        //calculate amounts of: class 1/2, 
        int[] class_count = new int[classLabels.size()];
        for(int i = 0; i<class_count.length;i++){
            class_count[i]=1;
        }
        for(Instance i : insts){
            class_count[classLabels.indexOf(i.getClassification())] ++;//counts how many in each class
            //count per feature value & class is implemented in readFile
        }

        //calculate total/denominators
        int class_total = 0;
        this.total_by_feature_class = new HashMap<>();
        for(int i = 0; i<class_count.length;i++){
            String iclas = classLabels.get(i);
            class_total+=class_count[i];
            for(String feature : value_count.keySet()){
                total_by_feature_class.put(feature,new HashMap<>());
                for(String s : classLabels){
                    total_by_feature_class.get(feature).put(s, 0);
                }
                //for every possible value of x
                for(String value : value_count.get(feature).keySet()){
                    int ad = 0;
                    if(value_count.get(feature).get(value).containsKey(iclas)){
                        ad = value_count.get(feature).get(value).get(iclas);
                    }
                    else{
                        value_count.get(feature).get(value).put(iclas, 0);
                    }
                    if(total_by_feature_class.get(feature).containsKey(iclas)){
                        int org = total_by_feature_class.get(feature).get(iclas);
                        total_by_feature_class.get(feature).put(iclas, ad+org);
                    }
                    else{
                        total_by_feature_class.get(feature).put(iclas, ad);
                    }
                    
                    //get class:
                    //total(feature, class) += count(feature, value, class)
                }
            }
        }




        //calculate probabilities
        probtable = new HashMap<>();
        for(String i : classLabels){
            probtable.put(i,new HashMap<>());
            //probability(y) = count(y)/class_total
            class_probs[classLabels.indexOf(i)] = class_count[classLabels.indexOf(i)]/class_total;
            
            for(String feature : value_count.keySet()){
                probtable.get(i).put(feature, new HashMap<>());
                for(String val : value_count.get(feature).keySet()){
                    double denom = total_by_feature_class.get(feature).get(i);
                    double prob = value_count.get(feature).get(val).get(i);
                    probtable.get(i).get(feature).put(val, prob/denom);
                    //prob(feature, value, class) = count(feature, value, class)/total(feature, class)
                }
            }
        }


        return probtable;
    }

    public double classScore(Instance inst, int clasLabel, double[][]prob){
        return 0;
    }

    public ArrayList<Instance> readFile(String filename){
        File f = new File(filename);
        ArrayList<Instance> insts = new ArrayList<Instance>();
        HashMap<String, HashMap<String,HashMap<String,Integer>>> hs = new HashMap<>();

        try{
            Scanner s = new Scanner(f);
            s.useDelimiter(",|\\n");
            s.next();
            HashMap<Integer,String> index = new HashMap<>();
            for(int i = 0; i< 9; i++){
                String featureName = s.next();
                hs.put(featureName,new HashMap<String,HashMap<String,Integer>>());
                index.put(i,featureName);
            }
            this.value_count = hs;
            while(s.hasNext()){
                s.next();
                String clas = s.next().toString();
                if(!classLabels.contains(clas)){
                    classLabels.add(clas);
                }
                HashMap<String,String> hm = new HashMap<>();
                for(int i = 0; i<9;i++){
                    String featureValue = s.next();
                    if(!hs.get(index.get(i)).containsKey(featureValue)){
                        hs.get(index.get(i)).put(featureValue,new HashMap<>());
                        
                    }
                    if(hs.get(index.get(i)).get(featureValue).containsKey(clas)){
                        int temp = hs.get(index.get(i)).get(featureValue).get(clas);
                        hs.get(index.get(i)).get(featureValue).put(clas,temp+1);
                    }
                    else{hs.get(index.get(i)).get(featureValue).put(clas,1);}
                    
                    hm.put(index.get(i),featureValue);
                }
                insts.add(new Instance(hm,clas));
            }
            //add names to 
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return insts;
    }
}