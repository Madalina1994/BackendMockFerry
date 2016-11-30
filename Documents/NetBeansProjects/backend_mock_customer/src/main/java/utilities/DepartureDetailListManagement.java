package utilities;

import generalstuff.DepartureDetail;
import java.util.HashMap;
import java.util.Map;

public class DepartureDetailListManagement {

    private static Map<Integer, DepartureDetail> departuresMap;
    private static int nextIdDeparture;

    public DepartureDetailListManagement() {
        departuresMap = new HashMap<>();
        nextIdDeparture = 0;
    }

    public void addDeparture( DepartureDetail departureDetail ) {
        departuresMap.put( ++nextIdDeparture, departureDetail );
    }

    public Map<Integer, DepartureDetail> getDepartures() {
        return departuresMap;
    }
}
