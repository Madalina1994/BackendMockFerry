package backendMock;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import interfaces.*;
import generalstuff.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import utilities.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DummyCustomerBackend implements CustomerInterface { //should implement the interface from the contract

    private DepartureDetail departureDetail, departureDetail2;
    private LineDetail lineDetail, lineDetail2;
    private LineSummary lineSummary, lineSummary2;
    ReservationDetail reservationDetail;
    DepartureSummary departureSummary, departureSummary2;
    Date departureDate;
    DepartureDetailListManagement departureDetailListManagement;
    FerryConfigListManagement ferryConfigListManagement;
    FerryDetailListManagement ferryDetailListManagement;
    LineSummaryListManagement lineSummarylListManagement;
    ReservationDetailListManagement reservationDetailListManagement;
    ReservationSummaryListManagement reservationSummaryListManagement;
    ReservationDetail dummyReservationDetail;
    ReservationSummary dummyReservationSummary;
    private static Map<Long, DepartureDetail> departuresForLineAndDate;
    private static Map<Long, DepartureDetail> departuresForLineAndDateGeneralStuff;//needed just to return the same type as in the interface

    public DummyCustomerBackend() {
        departureDetailListManagement = new DepartureDetailListManagement();
        ferryConfigListManagement = new FerryConfigListManagement();
        ferryDetailListManagement = new FerryDetailListManagement();
        lineSummarylListManagement = new LineSummaryListManagement();
        reservationDetailListManagement = new ReservationDetailListManagement();
        reservationSummaryListManagement = new ReservationSummaryListManagement();
        lineDetail = new LineDetail( "B", "A", 1, "1" );
        lineSummary = new LineSummary( "B", "A", 1, "1" );
        lineDetail2 = new LineDetail( "Malmo", "Copenhagen", 1, "1" );
        lineSummary2 = new LineSummary( "Malmo", "Copenhagen", 1, "1" );
        lineSummarylListManagement.addLineSummary( lineSummary );
        lineSummarylListManagement.addLineSummary( lineSummary2 );
        reservationDetail = new ReservationDetail( null, null, "", null, 0, 0, 0, 0, 0, 0.0, 0 );
        DateFormat format = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH );
        Date departureDate= null, departureDate2= null;
        try {
            departureDate = format.parse( "Sun Nov 20 00:23:39 CET 2016" );
            departureDate2 = format.parse( "Sun Nov 20 00:10:39 CET 2016" );
        } catch ( ParseException ex ) {
            Logger.getLogger( DummyCustomerBackend.class.getName() ).log( Level.SEVERE, null, ex );
        }
        departureSummary = new DepartureSummary( departureDate, lineDetail, null, 0 );
        departureSummary2 = new DepartureSummary( departureDate2, lineDetail2, null, 0 );
        departuresForLineAndDate = new HashMap<>();
        departuresForLineAndDateGeneralStuff = new HashMap<>();
        departureDetail = new DepartureDetail( 50, 100, 120, 150, 10, 100, 20, 1, 1, departureDate, lineSummary, null, 1 );
        departureDetail2 = new DepartureDetail( 50, 100, 120, 150, 10, 100, 20, 1, 1, departureDate2, lineSummary2, null, 1 );
        departureDetailListManagement.getDepartures().put(
                departureDetailListManagement.getNextIdDeparture(), departureDetail );
        departureDetailListManagement.getDepartures().put(
                departureDetailListManagement.getNextIdDeparture(), departureDetail2 );
        dummyReservationDetail = new ReservationDetail( departureDate, departureSummary,
                                                        "Patrick Huston", departureSummary, 4, 0, 1, 0, 0, 80,
                                                        Math.toIntExact( reservationDetailListManagement.getNextIdReservationDetail() ) );
        reservationDetailListManagement.addReservationDetail( dummyReservationDetail );
        dummyReservationSummary = new ReservationSummary( 40.0, 1 );
        reservationSummaryListManagement.addReservationSummary( dummyReservationSummary );
    }

    //to fix
    @Override
    public Collection<LineSummary> getLines() {
        return lineSummarylListManagement.getLineSummaries().values();
    }

    //finds the departures for a specific line and date (Note: the time is not taken into consideration!)
    @Override
    public Collection<DepartureDetail> getDepartures( LineIdentifier lineIdentifier, Date departureDate ) {
        Calendar calendardepartureDate = Calendar.getInstance();
        calendardepartureDate.setTime( departureDate );
        Calendar calendar = Calendar.getInstance();
        int departureDateDay = calendardepartureDate.get( Calendar.DAY_OF_YEAR );
        for ( DepartureDetail departure : departureDetailListManagement.getDepartures().values() ) {
            calendar.setTime( departure.getDepartureTime() );
            if ( departure.getLineSummary().getId().equals( lineIdentifier )
                    && departureDateDay == calendar.get( Calendar.DAY_OF_YEAR ) ) {
                departuresForLineAndDate.put( departure.getId(), departure );
            }
        }
        return departuresForLineAndDate.values();
    }

    @Override
    public ReservationDetail getReservation( ReservationIdentifier id ) {
        for ( Long l : reservationDetailListManagement.getReservationDetails().keySet() ) {
            if ( Math.toIntExact( l ) == id.getId() ) {
                return reservationDetailListManagement.getReservationDetails().get( l );
            }
        }
        return null;
    }

    @Override
    public ReservationSummary saveReservation( DepartureIdentifier departureIdentifier,
            int passengersNb, int numberOfResidents, boolean car, int numberOfHeavyMachinery, int numberOfLorries ) {

        ReservationDetail newReservationDetail = new ReservationDetail( departureDate, departureSummary,
                                                                        "Mark Johnson", departureSummary,
                                                                        passengersNb, 2, 1, 0, 40, 100, Math.toIntExact(
                                                                                reservationDetailListManagement.getNextIdReservationDetail() ) );
        reservationDetailListManagement.addReservationDetail( newReservationDetail );

        return newReservationDetail;
    }

    @Override
    public ReservationSummary updateReservation( ReservationIdentifier reservationIdentifier,
            DepartureIdentifier departureIdentifier, int passengersNb, int numberOfResidents, boolean car ) {
        Date departureDate = new Date();
//        String departurePort = "";
//        String destinationPort = "";
//        LineSummary lineSummary = null;
        DepartureSummary depSummary = null;

        for ( Long l : departureDetailListManagement.getDepartures().keySet() ) {
            if ( Math.toIntExact( l ) == departureIdentifier.getId() ) {
//                lineSummary = departureDetailListManagement.getDepartures().get( l ).getLineSummary();
//                departurePort = lineSummary.getDeparturePort();
//                destinationPort= lineSummary.getDestinationPort();
                depSummary = departureDetailListManagement.getDepartures().get( l );
            }
        }

        for ( Long l : reservationDetailListManagement.getReservationDetails().keySet() ) {
            if ( Math.toIntExact( l ) == reservationIdentifier.getId() ) {
                reservationDetailListManagement.getReservationDetails().replace(
                        l, new ReservationDetail( departureDate,
                                                  depSummary, "edited customer", depSummary,
                                                  passengersNb, numberOfResidents,
                                                  reservationDetailListManagement.getReservationDetails().get( l ).getNumberOfCars(),
                                                  reservationDetailListManagement.getReservationDetails().get( l ).getNumberOfLorries(), 40,
                                                  50 * passengersNb + 25 * numberOfResidents,
                                                  reservationIdentifier.getId() ) );
                return reservationSummaryListManagement.getReservationSummaries().get( l );
            }
        }
        return null;
    }

    @Override
    public Boolean deleteReservation( ReservationIdentifier reservationIdentifier ) {
        for ( Long l : reservationDetailListManagement.getReservationDetails().keySet() ) {
            if ( Math.toIntExact( 1 ) == reservationIdentifier.getId() ) {
                reservationDetailListManagement.getReservationDetails().remove( l );
                return true;
            }
        }
        return false;
    }
}
