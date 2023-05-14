import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.*;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;


public class Util {
    //reading the file and parsing it 
    public ArrayList<Instance> readFile(String filename){
        File f = new File(filename);
        ArrayList<Instance> insts = new ArrayList<Instance>();

        try{
            Scanner s = new Scanner(f);

            //add names to 

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return insts;
    }

}
