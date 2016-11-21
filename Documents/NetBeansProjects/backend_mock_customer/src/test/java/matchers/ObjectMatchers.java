package matchers;

import generalstuff.DepartureDetail;
import generalstuff.DepartureSummary;
import generalstuff.FerryIdentifier;
import generalstuff.FerrySummary;
import generalstuff.LineIdentifier;
import generalstuff.LineSummary;
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
//                if ( theExpected instanceof DepartureSummary && o instanceof DepartureSummary ) {
//                    DepartureSummary expectedDepartureSummary = ( DepartureSummary ) theExpected;
//                    DepartureSummary matcherDepartureSummary = ( DepartureSummary ) o;
//                    type = "DepartureSummary";
//                    if ( (expectedDepartureSummary.getId() == matcherDepartureSummary.getId())
//                            && (expectedDepartureSummary.getDepartureTime().equals( matcherDepartureSummary.getDepartureTime() ))
//                            && (compareFerryIdentifiers( expectedDepartureSummary.getFerrySummary(), matcherDepartureSummary.getFerrySummary() )) // to use matchers
//                            && (compareLineSummaries( expectedDepartureSummary.getLineSummary(), matcherDepartureSummary.getLineSummary() )) ) { //to use matchers                     
//                        areMatching = true;
//                    }
//                }
//                if ( theExpected instanceof FerrySummary && o instanceof FerrySummary ) {
//                    FerrySummary expectedFerrySummary = ( FerrySummary ) theExpected;
//                    FerrySummary matcherFerrySummary = ( FerrySummary ) o;
//                    type = "FerrySummary";
//                    if ( (expectedFerrySummary.getId() == matcherFerrySummary.getId())
//                            && (expectedFerrySummary.getName().equals( matcherFerrySummary.getName() ))
//                            && (expectedFerrySummary.getSupportedLines().equals( matcherFerrySummary.getSupportedLines() )) );
//                    {
//                        areMatching = true;
//                    }
//                }
//                if ( theExpected instanceof LineIdentifier && o instanceof LineIdentifier ) {
//                    LineIdentifier expectedLineIdentifier = ( LineIdentifier ) theExpected;
//                    LineIdentifier matcherLineIdentifier = ( LineIdentifier ) o;
//                    type = "LineIdentifier";
//                    if ( expectedLineIdentifier.getId().equals( matcherLineIdentifier.getId() ) ) {
//                        areMatching = true;
//                    }
//                }
                return compareDepartureSummaries(( DepartureSummary ) theExpected, ( DepartureSummary ) o);
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
