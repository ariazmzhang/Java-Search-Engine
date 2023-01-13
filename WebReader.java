
import java.util.HashMap;
import java.util.List;

public class WebReader {
    String url;
    static HashMap<String,WebPage> webPages;


    public WebReader(String url) {
        this.url = url;
        if (webPages == null){
            webPages = new HashMap<>();
            WebPage webPage = new WebPage(url);
            webPages.put(url, webPage);
        }else if (!webPages.containsKey(url)){
            WebPage webPage = new WebPage(url);
            webPages.put(url, webPage);
        }
    }

    public static List<String> getOutgoingLinks(String url){
        if (!WebExtractor.urlMap.containsKey(url)){ return null;}
        else{
            int index = WebExtractor.urlMap.get(url);
            return WebFileEditor.readFile(index,"outgoingLinks.txt");
        }
    }

    public static List<String> getIncomingLinks(String url){

        if (!WebExtractor.urlMap.containsKey(url)){return null;}
        else {
            int index = WebExtractor.urlMap.get(url);
            return WebFileEditor.readFile(index,"incomingLinks.txt");
        }
    }

    public double getPageRank(){
        if (!WebExtractor.urlMap.containsKey(url)){return -1.0;}
        else {
            return webPages.get(url).pageRank;
        }
    }

    public static double getIDF(String word){
        if (!WebExtractor.uniqueWords.contains(word)){return 0.0;}
        else {
            return Double.parseDouble(WebFileEditor.readFile(-1,word).get(0));
        }
    }

    public double getTF(String word){
        if ((!WebExtractor.uniqueWords.contains(word)) || (!WebExtractor.urlMap.containsKey(url))){return 0.0;}
        else {
            return webPages.get(url).tf.get(word);
        }
    }

    public double getTFIDF(String word){
        if ((!WebExtractor.uniqueWords.contains(word)) || (!WebExtractor.urlMap.containsKey(url))){return 0.0;}
        else {
            return webPages.get(url).tfidf.get(word);
        }
    }
}
