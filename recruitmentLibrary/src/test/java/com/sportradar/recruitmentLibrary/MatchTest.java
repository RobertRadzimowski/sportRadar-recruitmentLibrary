package com.sportradar.recruitmentLibrary;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

public class MatchTest {

    private static Stream<Arguments> constructorTestScenarios() {
        NullPointerException homeException = new NullPointerException("Home team name must not be null nor empty.");
        NullPointerException awayException = new NullPointerException("Home team name must not be null nor empty.");
        return Stream.of(
                of("OK", "Polska", "San Marino", null),
                of("Arguments Null", null, null, homeException),
                of("Arguments empty", "","", homeException),
                of("\"Home\" null", null, "San Marino",homeException),
                of("\"Home\" empty", "","San Marino", homeException),
                of("\"Away\" Null", "Polska", null, awayException),
                of("\"Away\" empty", "Polska","", awayException)
                );
    }

    @ParameterizedTest(name = "{0} scenario")
    @MethodSource ("com.sportradar.recruitmentLibrary.MatchTest#constructorTestScenarios")
    public void constructorTest (final String caseDescription,
                                 final String home,
                                 final String away,
                                 final Exception expectedException) {
        try {
            Match match = new Match(home, away);
        } catch (Exception catchedException) {
            assertThat (catchedException.getClass().equals(expectedException.getClass()));
            assertThat (catchedException.getMessage().equals(expectedException.getMessage()));
        }
    }
}
