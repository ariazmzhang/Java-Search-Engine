
import java.util.*;

public class WebCalculator {
    String url;
    public WebCalculator(String url) {
        this.url = url;
    }


    public static List<List<Double>> multMatrix(List<List<Double>> a, List<List<Double>> b){
        List<List<Double>> matrix = new ArrayList<>();
        List<Double> row = new ArrayList<>();
        for (int i = 0; i < b.get(0).size(); i++) {row.add(0.0);}
        for (int j = 0; j < a.size(); j++) {matrix.add(row);}

        if (a.get(0).size()!=b.size()){return null;}
        else{
            for (int i = 0; i < a.size(); i++) {
                for (int j = 0; j < b.get(0).size(); j++) {
                    for (int k = 0; k < b.size(); k++) {
                        double value = matrix.get(i).get(j);
                        value += a.get(i).get(k) * b.get(k).get(j);
                        matrix.get(i).set(j,value);
                    }
                }
            }
            return matrix;

        }
    }


    public static double euclideanDist(List<List<Double>> a, List<List<Double>> b){
        double sum = 0.0;
        double dist = 0.0;
        for (int i = 0; i < a.get(0).size(); i++) {
            sum += Math.pow((a.get(0).get(i) - b.get(0).get(i)),2);
            dist = Math.sqrt(sum);
        }
        return dist;
    }



    public static void getPageRank(){
        ArrayList<String> urlArray = WebExtractor.urlArray;
        int length = urlArray.size();

        ArrayList<Double> number = new ArrayList<>();
        ArrayList<Double> rowOfMatrix = new ArrayList<>();

        //initialize two matrix
        for (int i = 0; i < length; i++) {
            number.add(0.0);
            rowOfMatrix.add(0.0);
        }

        //initialize two adjacencyMatrix
        List<List<Double>> adjacencyMatrix = new ArrayList<>();
        for (String s : urlArray) {
            for (int j = 0; j < length; j++) {
                List<String> outgoingLinksList = WebFileEditor.readFile(j, "outgoingLinks.txt");
                if (outgoingLinksList.contains(s)) {
                    rowOfMatrix.set(j, 1.0);
                } else {
                    rowOfMatrix.set(j, 0.0);
                }

            }
            adjacencyMatrix.add(rowOfMatrix);

            rowOfMatrix = new ArrayList<>();
            for (int k = 0; k < length; k++) {
                rowOfMatrix.add(0.0);
            }
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                double value = number.get(i); // get value
                value = value + adjacencyMatrix.get(i).get(j); // increment value
                number.set(i, value);
            }
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (number.get(i) != 0){
                    double value = adjacencyMatrix.get(i).get(j);
                    double newValue = value;
                    if (number.get(i)!=0){
                        newValue = (value/number.get(i));
                    }
                    double a = 0.1;
                    newValue = (1-a) * newValue;
                    newValue =  (newValue + a/length);
                    adjacencyMatrix.get(i).set(j,newValue);
                }
            }
        }


        List<List<Double>> t0 = new ArrayList<>();
        List<Double> t = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            t.add(i,(1.0/length));
        }
        t0.add(t);


        List<List<Double>> t1 = WebCalculator.multMatrix(t0, adjacencyMatrix);
        double euc = WebCalculator.euclideanDist(t0, t1);
        while (euc > 0.0001){
            t0 = t1;
            t1 = WebCalculator.multMatrix(t0, adjacencyMatrix);
            euc = WebCalculator.euclideanDist(t0, t1);
        }

        for (int i = 0; i < t1.get(0).size(); i++) {
            double pagerank = t1.get(0).get(i);
            WebPage page = (WebPage) WebFileEditor.readObject(i,String.valueOf(i));
            page.pageRank = pagerank;
            WebFileEditor.createObject(i,String.valueOf(i),page);
        }
    }




    public static void idf(){
        ArrayList<String> urlSet =  WebExtractor.urlArray;
        List<String> uniqueWords = WebExtractor.uniqueWords;
        double totalDoc = urlSet.size();
        for (String word : uniqueWords) {
            double wordOccurrence = 0.0;
            double idfFrequency;
            for (String url : urlSet) {
                int index = WebExtractor.urlMap.get(url);
                WebPage page = (WebPage) WebFileEditor.readObject(index,String.valueOf(index));
                List<String> bodyList = page.body;

                if (bodyList.contains(word)) {wordOccurrence++;}
            }
            idfFrequency = Math.log(totalDoc / (wordOccurrence + 1))/Math.log(2);
            WebFileEditor.createFile(-1,word, String.valueOf(idfFrequency));
        }
    }




    public void tfAndtfidf(){
        double tf;
        double count,length;
        int index = WebExtractor.urlMap.get(url);
        HashMap<String,Double> tfs = new HashMap<>();
        HashMap<String,Double> tfidfs = new HashMap<>();

        WebPage page = (WebPage) WebFileEditor.readObject(index,String.valueOf(index));
        List<String> bodyList = page.body;

        for (String w: WebExtractor.uniqueWords){
            count = Collections.frequency(bodyList, w);
            length = bodyList.size();
            tf = count/length;
            tfs.put(w,tf);

            double idf = Double.parseDouble(WebFileEditor.readFile(-1,w).get(0));
            double tfidf;
            if (idf != 0){tfidf = (Math.log((1+tf))/Math.log(2))*idf;}
            else{tfidf = 0;}
            tfidfs.put(w,tfidf);
        }
        page.tf = tfs;
        page.tfidf = tfidfs;
        WebFileEditor.createObject(index,String.valueOf(index), page);
    }
}
