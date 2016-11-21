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
import java.util.ArrayList;
import java.util.List;
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
    Date departureDate, departureDate2;
    DepartureDetailListManagement departureDetailListManagement;
    FerryConfigListManagement ferryConfigListManagement;
    FerryDetailListManagement ferryDetailListManagement;
    LineSummaryListManagement lineSummarylListManagement;
    ReservationDetailListManagement reservationDetailListManagement;
    ReservationSummaryListManagement reservationSummaryListManagement;
    ReservationDetail dummyReservationDetail;
    ReservationSummary dummyReservationSummary;
    FerrySummary ferrySummary;
    private static Map<Long, DepartureDetail> departuresForLineAndDate;
    private static Map<Long, DepartureDetail> departuresForLineAndDateGeneralStuff;//needed just to return the same type as in the interface
    private static List<LineIdentifier> lineIdentifierList;

    public DummyCustomerBackend() {
        departureDetailListManagement = new DepartureDetailListManagement();
        ferryConfigListManagement = new FerryConfigListManagement();
        ferryDetailListManagement = new FerryDetailListManagement();
        lineSummarylListManagement = new LineSummaryListManagement();
        reservationDetailListManagement = new ReservationDetailListManagement();
        reservationSummaryListManagement = new ReservationSummaryListManagement();
        lineDetail = new LineDetail( "B", "A", 1, "1" );
        lineSummary = new LineSummary( "B", "A", 1, "1" );
        lineDetail2 = new LineDetail( "Malmo", "Copenhagen", 1, "2" );
        lineSummary2 = new LineSummary( "Malmo", "Copenhagen", 1, "2" );
        LineIdentifier lineIdentifier = new LineIdentifier( lineSummary.getId() );
        LineIdentifier lineIdentifier2 = new LineIdentifier( lineSummary2.getId() );
        lineIdentifierList = new ArrayList();
        lineIdentifierList.add( lineIdentifier );
        lineIdentifierList.add( lineIdentifier2 );
        ferrySummary = new FerrySummary( "ferry1", lineIdentifierList, "1" );
        lineSummarylListManagement.addLineSummary( lineSummary );
        lineSummarylListManagement.addLineSummary( lineSummary2 );
        reservationDetail = new ReservationDetail( null, null, "", null, 0, 0, 0, 0, 0, 0.0, 0 );
        DateFormat format = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH );
//        Date departureDate = null, departureDate2 = null;
        try {
            departureDate = format.parse( "Sun Nov 20 00:23:39 CET 2016" );
            departureDate2 = format.parse( "Sun Nov 20 00:10:39 CET 2016" );
        } catch ( ParseException ex ) {
            Logger.getLogger( DummyCustomerBackend.class.getName() ).log( Level.SEVERE, null, ex );
        }
        departureSummary = new DepartureSummary( departureDate, lineDetail, ferrySummary, 1 );
        departureSummary2 = new DepartureSummary( departureDate2, lineDetail2, ferrySummary, 2 );
        departuresForLineAndDate = new HashMap<>();
        departuresForLineAndDateGeneralStuff = new HashMap<>();
        departureDetail = new DepartureDetail( 50, 100, 120, 150, 10, 100, 20, 1, 1, departureDate, lineSummary, ferrySummary, 1 );
        departureDetail2 = new DepartureDetail( 50, 100, 120, 150, 10, 100, 20, 1, 1, departureDate2, lineSummary2, ferrySummary, 1 );
        departureDetailListManagement.addDeparture( departureDetail );
        departureDetailListManagement.addDeparture( departureDetail2 );
        dummyReservationDetail = new ReservationDetail( departureDate, departureSummary,
                                                        "Patrick Huston", departureSummary, 4, 0, 1, 0, 0, 80,
                                                        Math.toIntExact( reservationDetailListManagement.getNextIdReservationDetail() ) );
        reservationDetailListManagement.addReservationDetail( dummyReservationDetail );
        dummyReservationSummary = new ReservationSummary( 40.0, 1 );
        reservationSummaryListManagement.addReservationSummary( dummyReservationSummary );
    }

    @Override
    public Collection<LineSummary> getLines() {
        return lineSummarylListManagement.getLineSummaries().values();
    }

    //finds the departures for a specific line and date (Note: the time is not taken into consideration!)
    @Override
    public Collection<DepartureDetail> getDepartures( LineIdentifier lineIdentifier, Date departureDate ) {
        for ( DepartureDetail departure : departureDetailListManagement.getDepartures().values() ) {
            if ( departure.getLineSummary().getId().equals( lineIdentifier.getId() )
                    && !departure.getDepartureTime().after( departureDate )
                    && !departure.getDepartureTime().before( departureDate ) ) {
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
        int carsNumber = 0;
        if ( car == true ) {
            carsNumber = 1;
        }
        DepartureSummary depSummary= departureDetailListManagement.getDepartures().get( departureIdentifier.getId()); 
        ReservationDetail newReservationDetail = new ReservationDetail( depSummary.getDepartureTime(), depSummary,
                                                                        "Mark Johnson", depSummary,
                                                                        passengersNb, numberOfResidents, carsNumber, numberOfLorries, numberOfHeavyMachinery, 100, Math.toIntExact(
                                                                                reservationDetailListManagement.getNextIdReservationDetail() ) );
        reservationDetailListManagement.addReservationDetail( newReservationDetail );

        return newReservationDetail;
    }

    @Override
    public ReservationSummary updateReservation( ReservationIdentifier reservationIdentifier,
            DepartureIdentifier departureIdentifier, int passengersNb, int numberOfResidents, boolean car ) {
        Date departureDate = null;
//        String departurePort = "";
//        String destinationPort = "";
//        LineSummary lineSummary = null;
        DepartureSummary depSummary = null;

        for ( Long l : departureDetailListManagement.getDepartures().keySet() ) {
            if ( Math.toIntExact( l ) == departureIdentifier.getId() ) {
//                lineSummary = departureDetailListManagement.getDepartures().get( l ).getLineSummary();
//                departurePort = lineSummary.getDeparturePort();
//                destinationPort= lineSummary.getDestinationPort();
                departureDate = departureDetailListManagement.getDepartures().get( l ).getDepartureTime();
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
