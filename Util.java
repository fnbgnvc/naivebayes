import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class Util {
    //reading the file and parsing it 
    public ArrayList<Instance> readFile(String filename){
        File f = new File(filename);
        ArrayList<Instance> insts = new ArrayList<Instance>();
        HashMap<String, HashMap<String,Integer>> hs = new HashMap<>();

        try{
            Scanner s = new Scanner(f);
            s.useDelimiter(",|\\n");
            s.next();
            HashMap<Integer,String> index = new HashMap<>();
            for(int i = 0; i< 9; i++){
                String featureName = s.next();
                hs.put(featureName,new HashMap<String,Integer>());
                index.put(i,featureName);
            }
            while(s.hasNext()){
                s.next();
                String clas = s.next();
                HashMap<String,String> hm = new HashMap<>();
                for(int i = 0; i<9;i++){
                    String featureValue = s.next();
                    if(!hs.get(index.get(i)).containsKey(featureValue)){
                        hs.get(index.get(i)).put(featureValue,1);
                    }
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
