package dummyBackendTest;

import backendMock.DummyCustomerBackend;
import generalstuff.DepartureIdentifier;
import generalstuff.DepartureSummary;
import generalstuff.FerrySummary;
import generalstuff.LineDetail;
import generalstuff.LineIdentifier;
import generalstuff.LineSummary;
import generalstuff.ReservationDetail;
import generalstuff.ReservationIdentifier;
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
    private static List<LineIdentifier> linesIdList;

    public DummyBackendTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        dummyCustomerBackend = new DummyCustomerBackend();
        reservationDetailListManagement = new ReservationDetailListManagement();
        departureId = new DepartureIdentifier( 0 );
        departureId2 = new DepartureIdentifier( 1 );
        reservationIdentifier = new ReservationIdentifier( 1 );
        reservationIdentifier2 = new ReservationIdentifier( 2 );
        linesList = new LineSummaryListManagement();
        lineSummary = linesList.getLineSummaries().get( Long.valueOf( "1" ) );
        departureDetailListManagement = new DepartureDetailListManagement();
        format = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH );
        try {
            departureDate = format.parse( "Sun Nov 20 00:23:39 CET 2016" );
        } catch ( ParseException ex ) {
            Logger.getLogger( DummyBackendTest.class.getName() ).log( Level.SEVERE, null, ex );
        }
        lineSummary = new LineSummary( "B", "A", 1, "1" );
        departureSummary = new DepartureSummary( departureDate, lineDetail, null, 0 );
        linesIdList = new ArrayList<>();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void seeReservationTest() {
        ReservationDetail reservationDetail = dummyCustomerBackend.getReservation( reservationIdentifier );//.getCustomerName();
        assertEquals( reservationDetail.getCustomerName(), "Patrick Huston" );
//        assertEquals( reservationDetail.getDepartureSummary().getDepartureTime(), departureSummary.getDepartureTime());
//        assertThat(reservationDetail.getDepartureSummary(), matches(departureSummary));
    }

    @Test
    public void compareLinesTest() {
        LineIdentifier li1 = new LineIdentifier( "1" );
        LineIdentifier li2 = new LineIdentifier( "1" );
        if ( li1.getId().equals( li2.getId() ) ) {
            System.out.println( "Trueeeeee" );
        }
        assertThat( li1, matches( li1 ) );
    }

}
