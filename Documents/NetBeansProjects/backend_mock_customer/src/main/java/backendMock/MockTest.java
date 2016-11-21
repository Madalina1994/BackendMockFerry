package backendMock;

import backendMock.DummyCustomerBackend;
import generalstuff.DepartureIdentifier;
import generalstuff.FerryIdentifier;
import generalstuff.LineDetail;
import generalstuff.LineIdentifier;
import generalstuff.LineSummary;
import generalstuff.ReservationIdentifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import utilities.DepartureDetailListManagement;
import utilities.LineSummaryListManagement;
import utilities.ReservationDetailListManagement;

public class MockTest {

    private static DummyCustomerBackend dummyCustomerBackend = new DummyCustomerBackend();
    private static ReservationDetailListManagement reservationDetailListManagement = new ReservationDetailListManagement();
    private static DepartureIdentifier departureId = new DepartureIdentifier( 0 );
    private static DepartureIdentifier departureId2 = new DepartureIdentifier( 1 );
    private static ReservationIdentifier reservationIdentifier = new ReservationIdentifier( 1 );
    private static ReservationIdentifier reservationIdentifier2 = new ReservationIdentifier( 2 );
    private static LineSummaryListManagement linesList = new LineSummaryListManagement();
    private static LineSummary lineSummary = linesList.getLineSummaries().get( Long.valueOf( "1" ) );
    private static DepartureDetailListManagement departureDetailListManagement = new DepartureDetailListManagement();

    public static void main( String[] args ) throws ParseException {
//        tests the seeReservation method
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier ).getCustomerName() );
//
//        //tests the saveReservation method
        dummyCustomerBackend.saveReservation( null, 14, 14, true, 1, 0 );
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier2 ).getNumberOfPeople() );

        System.out.println( "Departures keyset: " + departureDetailListManagement.getDepartures().keySet() );

//        //tests the updateReservation method
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier ).getDepartureSummary().getLineSummary().getDeparturePort() );
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier ).getDepartureSummary().getLineSummary().getDestinationPort() );
        dummyCustomerBackend.updateReservation( reservationIdentifier, departureId2, 4, 0, true );
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier ).getDepartureSummary().getLineSummary().getDeparturePort() );
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier ).getDepartureSummary().getLineSummary().getDestinationPort() );

        System.out.println( "" + dummyCustomerBackend.getDepartures( new LineIdentifier( lineSummary.getId() ), new Date() ) );

        String departureDate = "Sun Nov 20 00:23:39 CET 2016";

        DateFormat format = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH );
        Date depDate = format.parse( departureDate );

        for ( LineSummary ls : dummyCustomerBackend.getLines() ) {
            System.out.println( dummyCustomerBackend.getDepartures( lineSummary, depDate ).size() );
        }
        System.out.println( departureDetailListManagement.getDepartures().keySet() );
        System.out.println( "Departure list size: " + departureDetailListManagement.getDepartures().keySet() );
        System.out.println( "Dep 1: " + departureDetailListManagement.getDepartures().containsKey( Long.valueOf( "1" ) ) );
        System.out.println( "Dep 1: " + departureDetailListManagement.getDepartures().get( Long.valueOf( "1" ) ) );
        System.out.println( "Dep 1: " + departureDetailListManagement.getDepartures().values() );
        //tests the deleteReservation method
        System.out.println( "Initially, the list size is "
                + reservationDetailListManagement.getReservationDetails().size() );
        dummyCustomerBackend.deleteReservation( reservationIdentifier );
        System.out.println( "After deletion, the list size is "
                + reservationDetailListManagement.getReservationDetails().size() );

        System.out.println( "There are " + linesList.getLineSummaries().values().size() + " lines" );
        System.out.println( "There are " + departureDetailListManagement.getDepartures().size() + " departures" );
    }

}
