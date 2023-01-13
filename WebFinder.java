
import java.util.ArrayList;
import java.util.List;

public class WebFinder {
    public String findTitle( List<String> pageContent){
        String title = null;
        for (String s: pageContent){
            if (s.contains("<title>")){
                title = s.substring(s.indexOf("<title>")+"<title>".length(),s.indexOf("</title>"));
                break;
            }
        }
        return title;
    }

    public String findURL(String pageLines){
        int urlStart = pageLines.indexOf('/')+1;
        int urlEnd = pageLines.indexOf('>')-1;

        return pageLines.substring(urlStart,urlEnd);//N-6.html
    }

    public List<String> findBody(List<String> pageContent){
        int start = 0;
        int end = 0;
        for (String s: pageContent){
            if (s.contains("<p>")){start = pageContent.indexOf(s)+1;}
            if (s.contains("</p>")){end = pageContent.indexOf(s);}
        }
        ArrayList<String> bodyList = new ArrayList<String>(pageContent.subList(start,end));
//        initialize the uniqueWords list
        for (String w: bodyList) {
            if (!WebExtractor.uniqueWords.contains(w)){
                WebExtractor.uniqueWords.add(w);
            }
        }
        return bodyList;
    }
}
