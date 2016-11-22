package utilities;

import generalstuff.ReservationSummary;
import java.util.HashMap;
import java.util.Map;

public class ReservationSummaryListManagement {

    private static Map<Long, ReservationSummary> reservationSummaryMap;
    private static long nextIdReservationSummary;

    public ReservationSummaryListManagement() {
        reservationSummaryMap = new HashMap<>();
        nextIdReservationSummary = 0;
    }

    public void addReservationSummary( ReservationSummary reservationSummary ) {
        reservationSummaryMap.put( nextIdReservationSummary, reservationSummary );
    }

    public Map<Long, ReservationSummary> getReservationSummaries() {
        return reservationSummaryMap;
    }

    public static long getNextIdReservationSummary() {
        return ++nextIdReservationSummary;
    }
}
