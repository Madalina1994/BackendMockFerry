package backendMock;

import backendMock.DummyCustomerBackend;
import generalstuff.DepartureIdentifier;
import generalstuff.FerryIdentifier;
import generalstuff.ReservationIdentifier;
import java.util.Date;
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
    private static DepartureDetailListManagement departureDetailListManagement = new DepartureDetailListManagement();

    public static void main( String[] args ) {
//        tests the seeReservation method
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier ).getCustomerName() );
//
//        //tests the saveReservation method
        dummyCustomerBackend.saveReservation( null, 14, 14, true, 1, 0 );
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier2 ).getNumberOfPeople() );
//
        System.out.println( "Departures keyset: " + departureDetailListManagement.getDepartures().keySet() );

//        //tests the updateReservation method
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier ).getDepartureSummary().getLineSummary().getDeparturePort() );
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier ).getDepartureSummary().getLineSummary().getDestinationPort() );
        dummyCustomerBackend.updateReservation( reservationIdentifier, departureId2, 4, 0, true );
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier ).getDepartureSummary().getLineSummary().getDeparturePort() );
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier ).getDepartureSummary().getLineSummary().getDestinationPort() );
//        //tests the deleteReservation method
//        System.out.println( "Initially, the list size is "
//                + reservationDetailListManagement.getReservationDetails().size() );
//        dummyCustomerBackend.deleteReservation( reservationIdentifier );
//        System.out.println( "After deletion, the list size is "
//                + reservationDetailListManagement.getReservationDetails().size() );
//
//        System.out.println( "There are " + linesList.getLineSummaries().values().size() + " lines" );
//        System.out.println( "There are " + departureDetailListManagement.getDepartures().size() + " departures" );
    }

}
