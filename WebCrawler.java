

public class WebCrawler {
    private WebExtractor webExtractor;

    public WebCrawler(String url) {
        webExtractor = new WebExtractor(url);
    }

    public void crawler(){
        webExtractor.crawl();
        WebCalculator.getPageRank();
        WebCalculator.idf();
        WebCalculator webCalculator;
        for (String url: WebExtractor.urlArray) {
            webCalculator = new WebCalculator(url);
            webCalculator.tfAndtfidf();
        }

    }
}
