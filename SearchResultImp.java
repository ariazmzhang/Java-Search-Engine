
public class SearchResultImp implements SearchResult, Comparable<SearchResultImp> {
    private final String title;
    private final double score;

    public SearchResultImp(String title, double score){
        this.title = title;
        this.score = score;
    }


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public double getScore() {
        return score;
    }


    @Override
    public int compareTo(SearchResultImp s) {
        double sScore = (double) Math.round(s.score * 1000d) / 1000d;
        double thisScore = (double) Math.round(this.score * 1000d) / 1000d;

        int result = 0;
        if (sScore > thisScore) {
            result = 1;
        } else if (sScore < thisScore) {
            result = -1;
        } else if (sScore == thisScore){
            for (int i = 0; i < Math.min(this.title.length(),s.title.length()); i++) {
                result = String.valueOf(this.title.charAt(i)).compareTo(String.valueOf(s.title.charAt(i)));
                if (result != 0){break;}
            }
            if (result==0){result = this.title.length()-s.title.length();}

        }
        return result;
    }
}
