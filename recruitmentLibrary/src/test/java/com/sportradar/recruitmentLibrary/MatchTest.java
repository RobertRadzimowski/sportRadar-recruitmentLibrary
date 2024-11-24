package com.sportradar.recruitmentLibrary;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

public class MatchTest {
    public static String HOME_TEAM = "Polska";
    public static String AWAY_TEAM = "San Marino";

    public static Stream<Arguments> constructorTestScenarios() {
        String homeExceptionMessage = "Home team name must not be null nor empty.";
        String awayExceptionMessage = "Away team name must not be null nor empty.";
        return Stream.of(
                of("OK", HOME_TEAM, AWAY_TEAM, null),
                of("Arguments Null", null, null, new NullPointerException(homeExceptionMessage)),
                of("Arguments empty", "", "", new IllegalArgumentException(homeExceptionMessage)),
                of("\"Home\" null", null, AWAY_TEAM, new NullPointerException(homeExceptionMessage)),
                of("\"Home\" empty", "", AWAY_TEAM, new IllegalArgumentException(homeExceptionMessage)),
                of("\"Away\" Null", HOME_TEAM, null, new NullPointerException(awayExceptionMessage)),
                of("\"Away\" empty", HOME_TEAM, "", new IllegalArgumentException(awayExceptionMessage))
        );
    }

    @ParameterizedTest(name = "{0} scenario")
    @MethodSource("constructorTestScenarios")
    public void constructorTest(final String caseDescription,
                                final String home,
                                final String away,
                                final Exception expectedException) {
        try {
            Match match = new Match(home, away, 0);
        } catch (Exception catchedException) {
            assertEquals(expectedException.getClass(), catchedException.getClass());
            assertEquals(expectedException.getMessage(), catchedException.getMessage());
        }
    }

    public static Stream<Arguments> updateScoreTestScenarios() {
        String numberExceptionMessage = "Score can't be negative.";
        return Stream.of(
                of("OK", 1, 1, null),
                of("Idempotent operation", 0, 0, null),
                of("Arguments Null", null, null, new NullPointerException("Cannot invoke \"java.lang.Integer.intValue()\" because \"homeScore\" is null")),
                of("Home Null", null, 1, new NullPointerException("Cannot invoke \"java.lang.Integer.intValue()\" because \"homeScore\" is null")),
                of("Away Null", 1, null, new NullPointerException("Cannot invoke \"java.lang.Integer.intValue()\" because \"awayScore\" is null")),
                of("Negative arguments", -13, 5, new IllegalArgumentException(numberExceptionMessage)),
                of("Update finished match", 1, 1, new IllegalStateException("Can't update score for closed match."))
        );
    }

    @ParameterizedTest(name = "{0} scenario")
    @MethodSource("updateScoreTestScenarios")
    public void updateScoreTest(final String caseDescription,
                                final Integer home,
                                final Integer away,
                                final Exception expectedException) {
        Match match = new Match(HOME_TEAM, AWAY_TEAM, 0);
        try {
            match.updateScore(home, away);
            assertEquals(match.getHomeScore(), home);
            assertEquals(match.getAwayScore(), away);
        } catch (Exception catchedException) {
            assertEquals(expectedException.getClass(), catchedException.getClass());
            assertEquals(expectedException.getMessage(), catchedException.getMessage());
        }
    }
}
