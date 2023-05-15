
import java.util.HashMap;

public class Instance {
    HashMap<String, String>features;
    String clas;
    public Instance(HashMap<String,String> feats, String clas){
        this.features = feats;
        this.clas=clas;
    }
    public HashMap<String,String> getFeatures(){
        return this.features;
    }
    public String getClassification(){
        return this.clas;
    }
    //the instances: 
    /*Each instance is described by 9 categorical attributes (features). The name and domain of each
attribute is described as follows:
1. age (9 values): 10-19, 20-29, 30-39, 40-49, 50-59, 60-69, 70-79, 80-89, 90-99
2. menopause (3 values): lt40, ge40, premeno
3. tumor-size (12 values): 0-4, 5-9, 10-14, 15-19, 20-24, 25-29, 30-34, 35-39, 40-44, 45-49, 50-54,
55-59
4. inv-nodes (13 values): 0-2, 3-5, 6-8, 9-11, 12-14, 15-17, 18-20, 21-23, 24-26, 27-29, 30-32, 33-35,
36-39
5. node-caps (2 values): yes, no
6. deg-malig (3 values): 1, 2, 3
7. breast (2 values): left, right
8. breast-quad (5 values): left up, left low, right up, right low, central
9. irradiat (2 values): yes, no*/

    //each value can be represented as int? 
    //ie array of integers: first is between 0-8, next 0-2, etc.
    //features can be represented as ?? hashmap?
    // in an instance, the features are a simple hashmap string -> string (name of feature -> value)
    //in the classifier, features are a nested hashmap name of feature -> hashmap (value -> count)
    //
}
