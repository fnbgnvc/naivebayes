public class Main {
    public static void main(String[] args){
        String trainFile = System.getProperty("user.dir") + "\\part2data\\" + args[0];
        String testFile = System.getProperty("user.dir") + "\\part2data\\" + args[1];
        new BayesClassifier(trainFile, testFile);
    }
}
