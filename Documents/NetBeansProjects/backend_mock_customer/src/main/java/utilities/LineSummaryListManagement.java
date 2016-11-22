package utilities;

import generalstuff.LineSummary;
import java.util.HashMap;
import java.util.Map;

public class LineSummaryListManagement {

    private static Map<Long, LineSummary> lineSummaryMap;
    private static long nextIdLineSummary;

    public LineSummaryListManagement() {
        lineSummaryMap = new HashMap<>();
        nextIdLineSummary = 0;
    }
   
    public void addLineSummary( LineSummary lineSummary ) {
        lineSummaryMap.put( ++nextIdLineSummary, lineSummary );
    }

    public Map<Long, LineSummary> getLineSummaries() {
        return lineSummaryMap;
    }
}
