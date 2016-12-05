package backendMock;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap; 
import interfaces.*;
import generalstuff.*;
import utilities.*;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DummyCustomerBackend implements CustomerInterface { //should implement the interface from the contract

    public DepartureDetailListManagement departureDetailListManagement;
    public LineSummaryListManagement lineSummarylListManagement;
    public static ReservationDetailListManagement reservationDetailListManagement;

    public DummyCustomerBackend() {
        departureDetailListManagement = new DepartureDetailListManagement();
        lineSummarylListManagement = new LineSummaryListManagement();
        reservationDetailListManagement = new ReservationDetailListManagement();

        populateLineSummarylListManagement();
        populateDepartureDetailListManagement();
        populateReservationDetailListManagement();
    }

    private void populateLineSummarylListManagement() {
        lineSummarylListManagement.addLineSummary(new LineSummary("København", "Malmø", 40, 1));
        lineSummarylListManagement.addLineSummary(new LineSummary("Malmø", "København", 40, 2));
        lineSummarylListManagement.addLineSummary(new LineSummary("Kalundborg", "Samsø", 20, 3));
        lineSummarylListManagement.addLineSummary(new LineSummary("Samsø", "Kalundborg", 20, 4));
//        lineDetail4 = new LineDetail(lineSummary4.getDeparturePort(), lineSummary4.getDeparturePort(), lineSummary4.getDuration(), lineSummary4.getId());
    }

    private void populateDepartureDetailListManagement() {
        DateTime tomorrow = new DateTime(DateTimeZone.forID("Europe/Copenhagen")).plusDays(1);
        Long tomorrowInMilis = tomorrow.getMillis() - tomorrow.getMillisOfDay();
        FerrySummary ferrySummary = null;

        LineSummary ls = lineSummarylListManagement.getLineSummaries().get(new Long(1));
        departureDetailListManagement.addDeparture(new DepartureDetail(50, 100, 120, 150, 10, 100, 20, 1, 1, new Date(tomorrowInMilis + 8 * 3600000), ls, ferrySummary, 1));
        departureDetailListManagement.addDeparture(new DepartureDetail(50, 100, 120, 150, 10, 100, 20, 1, 1, new Date(tomorrowInMilis + 12 * 3600000), ls, ferrySummary, 2));
    }

    private void populateReservationDetailListManagement() {
        DepartureDetail departureDetail;
        DepartureSummary ds;

        departureDetail = departureDetailListManagement.getDepartures().get(1);
        ds = new DepartureSummary(departureDetail.getDepartureTime(), departureDetail.getLineSummary(), departureDetail.getFerrySummary(), departureDetail.getId());
        reservationDetailListManagement.addReservationDetail(new ReservationDetail(ds.getDepartureTime(), ds, "Tomoe Murakami Petersen", 4, 0, 1, 0, 0, 80, Math.toIntExact(reservationDetailListManagement.getNextIdReservationDetail())));

        departureDetail = departureDetailListManagement.getDepartures().get(2);
        ds = new DepartureSummary(departureDetail.getDepartureTime(), departureDetail.getLineSummary(), departureDetail.getFerrySummary(), departureDetail.getId());
        reservationDetailListManagement.addReservationDetail(new ReservationDetail(ds.getDepartureTime(), ds, "Madalina Dragan", 4, 0, 1, 0, 0, 80, Math.toIntExact(reservationDetailListManagement.getNextIdReservationDetail())));
    }

    @Override
    public Collection<LineSummary> getLines() {
        return lineSummarylListManagement.getLineSummaries().values();
    }

    // finds the departures for a specific line and date (Note: the time is not taken into consideration!)
    @Override
    public Collection<DepartureDetail> getDepartures(LineIdentifier lineIdentifier, Date departureDate) {
        Map<Integer, DepartureDetail> departuresForLineAndDate = new HashMap<>();

        for (DepartureDetail thisD : departureDetailListManagement.getDepartures().values()) {
            if (thisD.getLineSummary().getId() == lineIdentifier.getId() && thisD.getDepartureTime().getYear() == departureDate.getYear()
                    && thisD.getDepartureTime().getMonth() == departureDate.getMonth() && thisD.getDepartureTime().getDate() == departureDate.getDate()) {
                departuresForLineAndDate.put(thisD.getId(), thisD);
            }
        }
        return departuresForLineAndDate.values();
    }

    @Override
    public ReservationDetail getReservation(ReservationIdentifier id) {
        return reservationDetailListManagement.getReservationDetails().get(new Long(id.getId()));
    }

    @Override
    public ReservationSummary saveReservation(DepartureIdentifier departureIdentifier, int passengersNb,
            int numberOfResidents, boolean car, int numberOfHeavyMachinery, int numberOfLorries, String customerName) {
        int carsNumber = 0;
        if (car == true) {
            carsNumber = 1;
        }
//        long totalPrice= find departureDetail and then say the total price is the departure detail price * (passengerNb + residentsNb)
        DepartureDetail depDetail = departureDetailListManagement.getDepartures().get(departureIdentifier.getId());
        long price = depDetail.getPricePerCar() * carsNumber + depDetail.getPricePerHeavy() * numberOfHeavyMachinery
                + depDetail.getPricePerLorry() * numberOfLorries + depDetail.getPricePerPerson() * passengersNb
                + depDetail.getPricePerResident() * numberOfResidents;
        DepartureSummary depSummary = departureDetailListManagement.getDepartures().get(departureIdentifier.getId());
        ReservationDetail newReservationDetail = new ReservationDetail(depSummary.getDepartureTime(), depSummary,
                "Mark Johnson", passengersNb, numberOfResidents,
                carsNumber, numberOfLorries, numberOfHeavyMachinery,
                price, Math.toIntExact(reservationDetailListManagement.getNextIdReservationDetail()));
        reservationDetailListManagement.addReservationDetail(newReservationDetail);

        return newReservationDetail;
    }

    @Override
    public ReservationSummary updateReservation(ReservationIdentifier reservationIdentifier,
            DepartureIdentifier departureIdentifier, int passengersNb, int numberOfResidents, boolean car,
            int numberOfHeavyMachinery, int numberOfLorries, String customerName) {
        Date departureDate = null;
//        String departurePort = "";
//        String destinationPort = "";
//        LineSummary lineSummary = null;
        DepartureSummary depSummary = null;

        for (int l : departureDetailListManagement.getDepartures().keySet()) {
            if (Math.toIntExact(l) == departureIdentifier.getId()) {
//                lineSummary = departureDetailListManagement.getDepartures().get( l ).getLineSummary();
//                departurePort = lineSummary.getDeparturePort();
//                destinationPort= lineSummary.getDestinationPort();
                departureDate = departureDetailListManagement.getDepartures().get(l).getDepartureTime();
                depSummary = departureDetailListManagement.getDepartures().get(l);
            }
        }

        for (Long l : reservationDetailListManagement.getReservationDetails().keySet()) {
            if (Math.toIntExact(l) == reservationIdentifier.getId()) {
                reservationDetailListManagement.getReservationDetails().replace(
                        l, new ReservationDetail(departureDate,
                                depSummary, "edited customer",
                                passengersNb, numberOfResidents,
                                reservationDetailListManagement.getReservationDetails().get(l).getNumberOfCars(),
                                reservationDetailListManagement.getReservationDetails().get(l).getNumberOfLorries(), 40,
                                50 * passengersNb + 25 * numberOfResidents,
                                reservationIdentifier.getId()));
                return reservationDetailListManagement.getReservationDetails().get(l);
            }
        }
        return null;
    }

    @Override
    public Boolean deleteReservation(ReservationIdentifier reservationIdentifier) {
        for (Long l : reservationDetailListManagement.getReservationDetails().keySet()) {
            if (Math.toIntExact(l) == reservationIdentifier.getId()) {
                reservationDetailListManagement.removeReservationDetail(l);
                return true;
            }
        }
        return false;
    }
}
