import java.util.*;

public class SearchEngine {
    private int X;
    private boolean boost;
    private String query;
    private static SearchIndexer searchIndexer;


    public SearchEngine(String query, boolean boost, int X) {
        this.query = query;
        this.X = X;
        this.boost = boost;
        searchIndexer = new SearchIndexer(query,boost);
    }

    public List<SearchResult> search(){
        ArrayList<Double> urlCosineSimilarity = searchIndexer.URLCosineSimilarity();
        List<SearchResultImp> rankedArray = new ArrayList<>();

        for (int i = 0; i < WebExtractor.titleArray.size(); i++) {
            SearchResultImp searchResult = new SearchResultImp(WebExtractor.titleArray.get(i),urlCosineSimilarity.get(i));
            rankedArray.add(searchResult);
        }
        Collections.sort(rankedArray);
        return new ArrayList<>(rankedArray.subList(0,X));
    }
}
