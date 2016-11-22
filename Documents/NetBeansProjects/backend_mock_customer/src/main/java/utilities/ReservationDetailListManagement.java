package utilities;

import generalstuff.ReservationDetail;
import java.util.HashMap;
import java.util.Map;

public class ReservationDetailListManagement {

    private static Map<Long, ReservationDetail> reservationDetailMap;
    private static long nextIdreservationDetail;

    public ReservationDetailListManagement() {
        reservationDetailMap = new HashMap<>();
        nextIdreservationDetail = 0;
    }

    public void addReservationDetail( ReservationDetail reservationDetail ) {
        reservationDetailMap.put( nextIdreservationDetail, reservationDetail );
    }

    public Map<Long, ReservationDetail> getReservationDetails() {
        return reservationDetailMap;
    }

    public void removeReservationDetail( Long id ) {
        reservationDetailMap.remove( id );
    }

    public static long getNextIdReservationDetail() {
        return ++nextIdreservationDetail;
    }
}
