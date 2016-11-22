package utilities;

import generalstuff.DepartureDetail;
import java.util.HashMap;
import java.util.Map;

public class DepartureDetailListManagement {

    private static Map<Long, DepartureDetail> departuresMap;
    private static long nextIdDeparture;

    public DepartureDetailListManagement() {
        departuresMap = new HashMap<>();
        nextIdDeparture = 0;
    }

    public void addDeparture( DepartureDetail departureDetail ) {
        departuresMap.put( ++nextIdDeparture, departureDetail );
    }

    public Map<Long, DepartureDetail> getDepartures() {
        return departuresMap;
    }
}
