
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class WebRequester {
    public static String readURL(String url){
        URL page = null;
        try {
            page = new URL(url);
            StringBuilder response = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(page.openStream()));
            String currentLine = reader.readLine();
            while(currentLine != null){
                response.append(currentLine + "\n");
                currentLine = reader.readLine();
            }
            reader.close();
            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /*
    Below is an example of how you can read a URL with this class.
    If exceptions are thrown during your crawl, you should handle them gracefully.
    For example, add the URL to the queue again and retry later.
     */
    public static void main(String[] args){
        System.out.println(WebRequester.readURL("http://people.scs.carleton.ca/~davidmckenney/fruits/N-0.html"));
        String s = WebRequester.readURL("http://people.scs.carleton.ca/~davidmckenney/fruits/N-0.html");
        List<String> l = Arrays.asList(s.split("\n"));
        System.out.println(l.get(1));

    }
}
