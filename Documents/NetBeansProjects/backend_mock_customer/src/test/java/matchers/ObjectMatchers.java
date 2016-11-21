package matchers;

import generalstuff.DepartureDetail;
import generalstuff.DepartureSummary;
import generalstuff.FerrySummary;
import generalstuff.LineIdentifier;
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
                if ( theExpected instanceof DepartureSummary && o instanceof DepartureSummary ) {
                    DepartureSummary expectedDepartureSummary = ( DepartureSummary ) theExpected;
                    DepartureSummary matcherDepartureSummary = ( DepartureSummary ) o;
                    type = "DepartureSummary";
                    if ( (expectedDepartureSummary.getId() == matcherDepartureSummary.getId())
                            && (expectedDepartureSummary.getDepartureTime().equals( matcherDepartureSummary.getDepartureTime() ))
                            && (expectedDepartureSummary.getFerrySummary().getId().equals( matcherDepartureSummary.getFerrySummary().getId() ) // to use matches
                            && (expectedDepartureSummary.getLineSummary().getId().equals( matcherDepartureSummary.getLineSummary().getId() ))) ) { //to use matchers
                        areMatching = true;
                    }
                }
                if ( theExpected instanceof FerrySummary && o instanceof FerrySummary ) {
                    FerrySummary expectedFerrySummary = ( FerrySummary ) theExpected;
                    FerrySummary matcherFerrySummary = ( FerrySummary ) o;
                    type = "FerrySummary";
                    if ( (expectedFerrySummary.getId() == matcherFerrySummary.getId())
                            && (expectedFerrySummary.getName().equals( matcherFerrySummary.getName() ))
                            && (expectedFerrySummary.getSupportedLines().equals( matcherFerrySummary.getSupportedLines() )) );
                    {
                        areMatching = true;
                    }
                }
                if ( theExpected instanceof LineIdentifier && o instanceof LineIdentifier ) {
                    LineIdentifier expectedLineIdentifier = ( LineIdentifier ) theExpected;
                    LineIdentifier matcherLineIdentifier = ( LineIdentifier ) o;
                    type = "LineIdentifier";
                    System.out.println( "Lalalala" );
                    System.out.println( expectedLineIdentifier.getId() );
                    System.out.println( matcherLineIdentifier.getId() );
                    if ( expectedLineIdentifier.getId().equals( matcherLineIdentifier.getId() ) ) {
                        areMatching = true;
                    }
                }
                return areMatching;
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
