package matchers;

import generalstuff.DepartureDetail;
import generalstuff.DepartureSummary;
import generalstuff.FerryIdentifier;
import generalstuff.FerrySummary;
import generalstuff.LineIdentifier;
import generalstuff.LineSummary;
import generalstuff.ReservationDetail;
import generalstuff.ReservationSummary;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class ObjectMatchers {

    public static Matcher matches( final Object expected ) {
        return new BaseMatcher() {

            protected Object theExpected = expected;
            protected String type = "none";

            @Override
            public boolean matches( Object o ) {
                boolean areMatching = false;
                if ( theExpected instanceof ReservationDetail && o instanceof ReservationDetail ) {
                    ReservationDetail matcherReservationDetail = ( ReservationDetail ) theExpected;
                    ReservationDetail expectedReservationDetail = ( ReservationDetail ) o;
                    if ( (expectedReservationDetail.getCustomerName().equals( matcherReservationDetail.getCustomerName() ))
                            && (compareDepartureSummaries( expectedReservationDetail.getDepartureSummary(), matcherReservationDetail.getDepartureSummary() ))
                            && (expectedReservationDetail.getNumberOfCars() == matcherReservationDetail.getNumberOfCars())
                            && (expectedReservationDetail.getNumberOfHeavyMachinery() == matcherReservationDetail.getNumberOfHeavyMachinery())
                            && (expectedReservationDetail.getNumberOfLorries() == matcherReservationDetail.getNumberOfLorries())
                            && (expectedReservationDetail.getNumberOfPeople() == matcherReservationDetail.getNumberOfPeople())
                            && (expectedReservationDetail.getNumberOfResidents() == matcherReservationDetail.getNumberOfResidents())
                            && (!expectedReservationDetail.getReservationMade().after( matcherReservationDetail.getReservationMade() ))
                            && (!expectedReservationDetail.getReservationMade().before( matcherReservationDetail.getReservationMade() )) 
                            ) {
                        return true;
                    }
                }

//                if ( theExpected instanceof ReservationSummary && o instanceof ReservationSummary ) {
//                    ReservationSummary matcherReservationSummary = ( ReservationSummary ) theExpected;
//                    ReservationSummary expectedReservationSummary = ( ReservationSummary ) o;
////                    type = "DepartureSummary";
//                    if ( expectedReservationSummary.getId() == matcherReservationSummary.getId() ) {
//                        return true;
//                    }
//                }
                return false;
            }

            public boolean compareDepartureSummaries( DepartureSummary expectedDepartureSummary, DepartureSummary matcherDepartureSummary ) {
                if ( (expectedDepartureSummary.getId() == matcherDepartureSummary.getId())
                        && (expectedDepartureSummary.getDepartureTime().equals( matcherDepartureSummary.getDepartureTime() ))
                        && (compareFerryIdentifiers( expectedDepartureSummary.getFerrySummary(), matcherDepartureSummary.getFerrySummary() )) // to use matchers
                        && (compareLineSummaries( expectedDepartureSummary.getLineSummary(), matcherDepartureSummary.getLineSummary() )) ) //to use matchers                     
                {
                    return true;
                }
                return false;
            }

            public boolean compareFerryIdentifiers( FerryIdentifier expectedFerrySummary, FerryIdentifier matcherFerrySummary ) {
                if ( expectedFerrySummary.getId() == matcherFerrySummary.getId() ) {
                    return true;
                }
                return false;
            }

            public boolean compareLineSummaries( LineSummary expectedLineSummary, LineSummary matcherLineSummary ) {
                if ( expectedLineSummary.getId().equals( matcherLineSummary.getId() )
                        && expectedLineSummary.getDeparturePort().equals( matcherLineSummary.getDeparturePort() )
                        && expectedLineSummary.getDestinationPort().equals( matcherLineSummary.getDestinationPort() )
                        && expectedLineSummary.getDuration() == matcherLineSummary.getDuration() ) {
                    return true;
                }
                return false;
            }

            public boolean not( Object o ) {
                return !matches( o );
            }

            @Override
            public void describeTo( Description description ) {
                description.appendText( theExpected.toString() + " /Type : " + type );
            }

        };
    }

}
