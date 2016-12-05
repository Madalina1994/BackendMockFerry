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
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

public class DummyBackendTest {

    private DummyCustomerBackend dummyCustomerBackend;
    private ReservationDetailListManagement reservationDetailListManagement;
    private DepartureIdentifier departureId;
    private DepartureIdentifier departureId2;
    private ReservationIdentifier reservationIdentifier;
    private ReservationIdentifier reservationIdentifier2;
    private LineSummaryListManagement linesList;
    private LineSummary lineSummary;
    private DepartureDetailListManagement departureDetailListManagement;
    private DepartureSummary departureSummary;
    private Date departureDate;
    private DateFormat format;
    private LineDetail lineDetail;
    private FerrySummary ferrySummary;
    private LineDetail lineDetail1;
    private LineDetail lineDetail2;
    private LineSummary lineSummary1;
    private LineSummary lineSummary2;
    private ReservationDetail expectedReservationDetail;
    private ReservationDetail expectedNewReservationDetail;
    private List<LineIdentifier> linesIdList;

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
        reservationDetailListManagement = new ReservationDetailListManagement();
        departureId = new DepartureIdentifier( 1 );
        departureId2 = new DepartureIdentifier( 2 );
        reservationIdentifier = new ReservationIdentifier( 1 );
        reservationIdentifier2 = new ReservationIdentifier( 2 );
        linesList = new LineSummaryListManagement();
        lineSummary = linesList.getLineSummaries().get( Long.valueOf( "1" ) );
        departureDetailListManagement = new DepartureDetailListManagement();
        lineDetail = new LineDetail( "B", "A", 1, 1 );
        lineSummary = new LineSummary( "B", "A", 1, 1 );
        lineDetail2 = new LineDetail( "Malmo", "Copenhagen", 1, 2 );
        lineSummary2 = new LineSummary( "Malmo", "Copenhagen", 1, 2 );
        LineIdentifier lineIdentifier = new LineIdentifier( lineSummary.getId() );
        LineIdentifier lineIdentifier2 = new LineIdentifier( lineSummary2.getId() );
        linesIdList = new ArrayList();
        linesIdList.add( lineIdentifier );
        linesIdList.add( lineIdentifier2 );
        ferrySummary = new FerrySummary( "ferry1", linesIdList, 1 );
        format = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH );
        try {
            departureDate = format.parse( "Sun Nov 20 00:23:39 CET 2016" );
        } catch ( ParseException ex ) {
            Logger.getLogger( DummyBackendTest.class.getName() ).log( Level.SEVERE, null, ex );
        }
        lineSummary = new LineSummary( "B", "A", 1, 1 );
        departureSummary = new DepartureSummary( departureDate, lineDetail, ferrySummary, 1 );
        linesIdList = new ArrayList<>();
        expectedReservationDetail = new ReservationDetail(departureSummary.getDepartureTime(), departureSummary, "Tomoe Murakami Petersen", 4, 0, 1, 0, 0, 80, Math.toIntExact(reservationDetailListManagement.getNextIdReservationDetail()));
        expectedNewReservationDetail = new ReservationDetail( departureDate, departureSummary,
                                                              "Mark Johnson", 14, 14, 1,
                                                              0, 1, 1000, 2 );
        dummyCustomerBackend = new DummyCustomerBackend();
    }

    @After
    public void tearDown() {        
    }

    @Test
    public void seeReservationTest() {
        ReservationDetail reservationDetail = dummyCustomerBackend.getReservation( reservationIdentifier );
//        assertThat( reservationDetail, matches( expectedReservationDetail ) );
    }

    @Test
    public void createReservationTest() {
        ReservationDetail newReservationDetail = ( ReservationDetail ) dummyCustomerBackend.saveReservation( departureId, 14, 14, true, 1, 0, "John Marker" );
//        assertThat( newReservationDetail, matches( expectedNewReservationDetail ) );
    }

    @Test( expected = AssertionError.class )
    public void editReservation() {
        ReservationDetail initialReservation = dummyCustomerBackend.getReservation( reservationIdentifier );
        ReservationDetail editedReservationSummary = ( ReservationDetail ) dummyCustomerBackend.updateReservation(
                reservationIdentifier, departureId2, 20, 0, true, 0, 0, "Daniel Petersen" );
        assertThat( editedReservationSummary, matches( initialReservation ) );
    }

    @Test
    public void removeReservationTest() {
        dummyCustomerBackend.deleteReservation( reservationIdentifier );
        assertEquals( dummyCustomerBackend.getReservation( reservationIdentifier ), null );
    }
}
