public class Main {
    public static void main(String[] args){
        String filename = System.getProperty("user.dir") + "\\part2data\\" + "breast-cancer-training.csv";
        new Util().readFile(filename);
    }
}
