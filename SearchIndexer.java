import java.util.*;

public class SearchIndexer {
    private boolean boost;
    String query;

    public SearchIndexer(String query, boolean boost) {
        this.boost = boost;
        this.query = query;

    }

    public ArrayList<Double> URLCosineSimilarity(){
        SearchQuery searchQuery = new SearchQuery(query);

        HashMap<String, Double> queryTFIDF = searchQuery.getTFIDF();
        HashSet<String> searchPhraseSet = searchQuery.searchPhraseSet;

        double leftDenom = 0.0;
        for (String word: searchPhraseSet){
            leftDenom += Math.pow(queryTFIDF.get(word),2);
        }

        ArrayList<Double> cosineSimilarity = new ArrayList<>();
        for (String url: WebExtractor.urlArray){
            WebReader webReader = new WebReader(url);

            double numeratorDouble = 0.0;
            double right = 0.0;

            for (String word: searchPhraseSet){
                numeratorDouble += webReader.getTFIDF(word) * queryTFIDF.get(word);
                right += Math.pow(webReader.getTFIDF(word),2);
            }


            if (numeratorDouble == 0){cosineSimilarity.add(0.0);}
            else{ double i = numeratorDouble / (Math.sqrt(leftDenom) * Math.sqrt(right));
                if (!boost ) {cosineSimilarity.add(i);}
                else{
                    double pageRank = webReader.getPageRank();
                    cosineSimilarity.add(i*pageRank);
                }
            }
        }

        return cosineSimilarity;
    }
}


