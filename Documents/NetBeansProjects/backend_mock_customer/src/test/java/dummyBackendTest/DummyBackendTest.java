package dummyBackendTest;

import backendMock.DummyCustomerBackend;
import generalstuff.DepartureIdentifier;
import generalstuff.ReservationDetail;
import generalstuff.ReservationIdentifier;
import generalstuff.ReservationSummary;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static matchers.ObjectMatchers.matches;

public class DummyBackendTest {

    private DummyCustomerBackend dummyCustomerBackend;

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
    }

    @After
    public void tearDown() {
        dummyCustomerBackend = null;
    }

    @Test
    public void getReservationTest() {
        ReservationDetail reservationDetail = dummyCustomerBackend.getReservation(new ReservationIdentifier(dummyCustomerBackend.dummyReservationDetail.getId()));
        assertThat(reservationDetail, matches(dummyCustomerBackend.dummyReservationDetail));
//        so it's exactly the same java object, but we should probably check for their attributes to match
    }

    @Test
    public void saveReservationTest() {
        ReservationSummary newReservationSummary = dummyCustomerBackend.saveReservation(dummyCustomerBackend.departureDetail, 14, 14, true, 1, 0);
        assertTrue(newReservationSummary instanceof ReservationSummary);
//        and maybe another assertThat(newReservationSummary, matches(obj));
//        where obj = the ReservationSummary instance with id=newReservationSummary.getId() from DummyCustomerBackend.reservationDetailListManagement

//        instead of:
//        ReservationDetail newReservationDetail = (ReservationDetail) dummyCustomerBackend.saveReservation(departureId, 14, 14, true, 1, 0);
//        assertThat(newReservationDetail, matches(expectedNewReservationDetail));
//        which is comparing 2 totally different java objects that can't match anyway
    }

    @Test
    public void updateReservationTest() {
//        next line should not be used, as the test shouldn't depend on other tested methods (dummyCustomerBackend.getReservation)
//        ReservationDetail initialReservation = dummyCustomerBackend.getReservation(reservationIdentifier);
        
//        there are so many wrong things about this one that I can't even process (I am also tired)
//        dummyCustomerBackend.updateReservation asks for a lot of possible parameters to change, but its return is a ReservationSummary
//        so the next test should be enough, because we know it would be an exception otherwise:
        ReservationSummary updatedReservationSummary = dummyCustomerBackend.updateReservation(
                new ReservationIdentifier(dummyCustomerBackend.dummyReservationDetail.getId()), new DepartureIdentifier(dummyCustomerBackend.departureDetail2.getId()), 20, 0, true );
        assertTrue(updatedReservationSummary instanceof ReservationSummary);
        assertEquals(dummyCustomerBackend.dummyReservationDetail.getDepartureSummary().getId(), dummyCustomerBackend.departureDetail2.getId());
//        and so on to test every attribute value that we changed
    }

    @Test
    public void deleteReservationTest() {
        Boolean expected = dummyCustomerBackend.deleteReservation(new ReservationIdentifier(dummyCustomerBackend.dummyReservationDetail.getId()));
        assertTrue(expected);
//        another test would be to check for dummyCustomerBackend.dummyReservationDetail.getId() in DummyCustomerBackend.reservationDetailListManagement and not find it
    }
}
