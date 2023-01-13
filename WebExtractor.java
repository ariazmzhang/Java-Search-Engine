
import java.util.*;

public class WebExtractor {
    public static HashMap<String, Integer> urlMap;
    public static ArrayList<String> urlArray;
    public static ArrayList<String> titleArray;
    public static ArrayList<String> uniqueWords;

    public String seedURL;

    public WebExtractor(String seedURL) {
        this.seedURL = seedURL;
        urlMap = new HashMap<>();
        urlArray = new ArrayList<>();
        titleArray = new ArrayList<>();
        uniqueWords = new ArrayList<>();
    }



    public int crawl() {
        WebFinder webFinder = new WebFinder();
        Queue<String> urlQueue = new LinkedList<>();
        int count = 0;
        urlQueue.add(seedURL);
        urlMap.put(seedURL,urlArray.size());
        urlArray.add(seedURL);

        int addURLLength = seedURL.split("/")[seedURL.split("/").length-1].length();
        String addURL = seedURL.substring(0,seedURL.length()-addURLLength);


        while (!urlQueue.isEmpty()) {
            count++;
            String curURL = urlQueue.peek();
            String s = WebRequester.readURL(curURL);
            List<String> pageContent = Arrays.asList(s.split("\n"));

            WebPage curPage = new WebPage();
            curPage.url = curURL;
            curPage.title = webFinder.findTitle(pageContent);
            curPage.index = urlMap.get(curURL);
            curPage.body = webFinder.findBody(pageContent);
            WebFileEditor.createObject(curPage.index,String.valueOf(curPage.index),curPage);
            titleArray.add(curPage.title);

            for (String w : pageContent) {
                if (w.contains("<a")) {
                    //create outgoingLinks, the current path is separate dir for each url
                    String urlName = webFinder.findURL(w);

                    String urlFull = addURL + urlName;

                    if (!urlArray.contains(urlFull)){
                        urlMap.put(urlFull,urlArray.size());
                        urlQueue.add(urlFull);
                        urlArray.add(urlFull);
                    }

                    WebFileEditor.createFile(urlMap.get(curURL), "outgoingLinks.txt", urlFull+"\n");
                    WebFileEditor.createFile(urlMap.get(urlFull), "incomingLinks.txt", curURL+"\n");

                }
            }
            urlQueue.remove();
        }

        return count;
    }
}