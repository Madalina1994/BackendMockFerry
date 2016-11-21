package dummyBackendTest;

import backendMock.DummyCustomerBackend;
import generalstuff.DepartureIdentifier;
import generalstuff.DepartureSummary;
import generalstuff.FerryDetail;
import generalstuff.FerrySummary;
import generalstuff.LineDetail;
import generalstuff.LineIdentifier;
import generalstuff.LineSummary;
import generalstuff.ReservationDetail;
import generalstuff.ReservationIdentifier;
import generalstuff.ReservationSummary;
import static java.lang.String.format;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utilities.DepartureDetailListManagement;
import utilities.LineSummaryListManagement;
import utilities.ReservationDetailListManagement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import static matchers.ObjectMatchers.matches;
import static org.hamcrest.CoreMatchers.is;
import static matchers.ObjectMatchers.matches;

public class DummyBackendTest {

    private static DummyCustomerBackend dummyCustomerBackend;
    private static ReservationDetailListManagement reservationDetailListManagement;
    private static DepartureIdentifier departureId;
    private static DepartureIdentifier departureId2;
    private static ReservationIdentifier reservationIdentifier;
    private static ReservationIdentifier reservationIdentifier2;
    private static LineSummaryListManagement linesList;
    private static LineSummary lineSummary;
    private static DepartureDetailListManagement departureDetailListManagement;
    private static DepartureSummary departureSummary;
    private static Date departureDate = null;
    private static DateFormat format;
    private static LineDetail lineDetail;
    private static FerrySummary ferrySummary;
    private static LineDetail lineDetail1;
    private static LineDetail lineDetail2;
    private static LineSummary lineSummary1;
    private static LineSummary lineSummary2;
    private static ReservationDetail expectedReservationDetail;
    private static ReservationDetail expectedNewReservationDetail;
    private static List<LineIdentifier> linesIdList;

    public DummyBackendTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        dummyCustomerBackend = new DummyCustomerBackend();
        reservationDetailListManagement = new ReservationDetailListManagement();
        departureId = new DepartureIdentifier( 1 );
        departureId2 = new DepartureIdentifier( 2 );
        reservationIdentifier = new ReservationIdentifier( 1 );
        reservationIdentifier2 = new ReservationIdentifier( 2 );
        linesList = new LineSummaryListManagement();
        lineSummary = linesList.getLineSummaries().get( Long.valueOf( "1" ) );
        departureDetailListManagement = new DepartureDetailListManagement();
        lineDetail = new LineDetail( "B", "A", 1, "1" );
        lineSummary = new LineSummary( "B", "A", 1, "1" );
        lineDetail2 = new LineDetail( "Malmo", "Copenhagen", 1, "2" );
        lineSummary2 = new LineSummary( "Malmo", "Copenhagen", 1, "2" );
        LineIdentifier lineIdentifier = new LineIdentifier( lineSummary.getId() );
        LineIdentifier lineIdentifier2 = new LineIdentifier( lineSummary2.getId() );
        linesIdList = new ArrayList();
        linesIdList.add( lineIdentifier );
        linesIdList.add( lineIdentifier2 );
        ferrySummary = new FerrySummary( "ferry1", linesIdList, "1" );
        format = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH );
        try {
            departureDate = format.parse( "Sun Nov 20 00:23:39 CET 2016" );
        } catch ( ParseException ex ) {
            Logger.getLogger( DummyBackendTest.class.getName() ).log( Level.SEVERE, null, ex );
        }
        lineSummary = new LineSummary( "B", "A", 1, "1" );
        departureSummary = new DepartureSummary( departureDate, lineDetail, ferrySummary, 1 );
        linesIdList = new ArrayList<>();
        expectedReservationDetail = new ReservationDetail( departureDate, departureSummary,
                                                           "Patrick Huston", departureSummary, 4, 0, 1, 0, 0, 80, 1 );
        expectedNewReservationDetail = new ReservationDetail( departureDate, departureSummary,
                                                              "Mark Johnson", departureSummary, 14, 14, 1,
                                                              0, 1, 1000, 2 );
    }

    @After
    public void tearDown() {
    }

    @Test
    public void seeReservationTest() {
        ReservationDetail reservationDetail = dummyCustomerBackend.getReservation( reservationIdentifier );
        assertThat( reservationDetail, matches( expectedReservationDetail ) );
    }

    @Test
    public void saveReservationTest() {
        ReservationDetail newReservationDetail = ( ReservationDetail ) dummyCustomerBackend.saveReservation( departureId, 14, 14, true, 1, 0 );
        assertThat( newReservationDetail, matches( expectedNewReservationDetail ) );
    }

    @Test( expected = AssertionError.class )
    public void updateReservation() {
        ReservationDetail initialReservation = dummyCustomerBackend.getReservation( reservationIdentifier );
        ReservationDetail editedReservationSummary = ( ReservationDetail ) dummyCustomerBackend.updateReservation(
                reservationIdentifier, departureId2, 20, 0, true );
        assertThat( editedReservationSummary, matches( initialReservation ) );
    }

    @Test
    public void deleteReservationTest() {
        dummyCustomerBackend.deleteReservation( reservationIdentifier );
        assertEquals( dummyCustomerBackend.getReservation( reservationIdentifier ), null );
    }
}
