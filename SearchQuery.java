
import java.util.*;

public class SearchQuery {
    String words;
    int size;
    List<String> searchPhraseList;
    HashSet<String> searchPhraseSet;
    List<String> topTenTitle;
    List<Double> topTenScore;


    public SearchQuery(String words) {
        this.words = words;
        this.searchPhraseList = Arrays.asList(words.toLowerCase().split(" "));
        this.size = searchPhraseList.size();
        this.searchPhraseSet = new HashSet<>(searchPhraseList);
        topTenTitle = new ArrayList<>();
        topTenScore = new ArrayList<>();
    }

    public HashMap<String, Double> getTFIDF(){
        HashMap<String, Double> TFIDF = new HashMap<>();
        for (String word: searchPhraseSet){
            double queryFreq = Collections.frequency(searchPhraseList, word);
            double tfOfPhrase = Math.log((queryFreq/size) + 1)/Math.log(2);
            double idfOfPhrase = WebReader.getIDF(word);
            double tfidfOfPhrase = tfOfPhrase * idfOfPhrase;
            TFIDF.put(word, tfidfOfPhrase);
        }
        return TFIDF;
    }


}


