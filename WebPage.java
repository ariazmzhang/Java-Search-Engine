
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebPage implements Serializable{
    int index;
    String title, url;
    double pageRank;
    double score;
    List<String> body;
    HashMap<String,Double> tf, tfidf;
    public WebPage() {
        this.url = null;
        title = null;
        body = new ArrayList<>();
        index = -1;
        pageRank = -1.0;
        score = -1.0;
        tf = new HashMap<>();
        tfidf = new HashMap<>();

    }

    public WebPage(String url) {
        this.url = url;
        index = WebExtractor.urlMap.get(url);
        WebPage page = (WebPage) WebFileEditor.readObject(index, String.valueOf(index));
        body = page.body;
        pageRank = page.pageRank;
        score = page.score;
        tf = page.tf;
        tfidf = page.tfidf;
    }
}
