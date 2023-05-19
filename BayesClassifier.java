
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
        int acc = 0;
        //printConditionalProbs();
        for(Instance i : readFile(testFile)){
            double c1 = classScore(i, classLabels.get(0), probtable, class_probs);
            double c2 = classScore(i, classLabels.get(1),probtable,class_probs);
            String predicted_label = "";
            if(c1>c2){predicted_label=classLabels.get(0);}
            else if (c2>c1){predicted_label=classLabels.get(0);}
            if(predicted_label.equals(i.getClassification())){acc++;}
            System.out.println("no-rec:" + c1 + " rec: " + c2);
            System.out.println("Predicted: " + predicted_label + " Actual: " + i.getClassification());
        }
        System.out.println("Accuracy: " + 1.0*acc/1.0*readFile(testFile).size());
        

    }
    //let y= class label and x1....xm = features
    //counts: make a hashmap with a string as the key, and another hashmap as the value.
    //The first map uses the label on the left as the key, and then the nested map for that is using each possible value as a key in that, 
    //and uses an int as the value, and when setting up, every value is 1

    public void printConditionalProbs(){
        for(String clas : probtable.keySet()){
            System.out.println(clas + ":");
            for(String feat : probtable.get(clas).keySet()){
                System.out.println("  " + feat + ":");
                for(String val : probtable.get(clas).get(feat).keySet()){
                    System.out.println("    " + val + ":");
                    System.out.println("      " + probtable.get(clas).get(feat).get(val));
                }
            }
        }
    }
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
                total_by_feature_class.get(feature).put(iclas, 0);
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
            class_probs[classLabels.indexOf(i)] = (1.0*class_count[classLabels.indexOf(i)])/(1.0*class_total);
            
            for(String feature : value_count.keySet()){
                probtable.get(i).put(feature, new HashMap<>());
                for(String val : value_count.get(feature).keySet()){
                    
                    double prob = 1.0*value_count.get(feature).get(val).get(i);
                    double denom = 0;
                    if(!total_by_feature_class.get(feature).containsKey(i)){
                        denom = prob;
                    }
                    else{
                        denom = 1.0*total_by_feature_class.get(feature).get(i);
                    }
                    probtable.get(i).get(feature).put(val, prob/denom);
                    //prob(feature, value, class) = count(feature, value, class)/total(feature, class)
                }
            }
        }


        return probtable;
    }

    public double classScore(Instance inst, String clas, HashMap<String, HashMap<String,HashMap<String,Double>>>prob, double[] class_probs){
        double score = class_probs[classLabels.indexOf(clas)];
        for(String feature : inst.getFeatures().keySet()){
            score = score * prob.get(clas).get(feature).get(inst.getFeatures().get(feature));
        }
        return score;
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
            this.value_count = hardcodeLabels(hs);
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return insts;
    }

    public HashMap<String, HashMap<String,HashMap<String,Integer>>> hardcodeLabels(HashMap<String, HashMap<String,HashMap<String,Integer>>> vals){
        HashMap<String,Integer> simple = new HashMap<>();
        simple.put("no-recurrence-events",1);
        simple.put("recurrence-events",1);

        vals.get("age").put("10-19",simple);
        vals.get("age").put("80-89",simple);
        vals.get("age").put("90-99",simple);
        vals.get("tumor-size").put("55-59",simple);
        vals.get("inv-nodes").put("18-20",simple);
        vals.get("inv-nodes").put("21-23",simple);
        vals.get("inv-nodes").put("27-29",simple);
        vals.get("inv-nodes").put("30-32",simple);
        vals.get("inv-nodes").put("33-35",simple);
        vals.get("inv-nodes").put("36-39",simple);

        

        return vals;
    }
}